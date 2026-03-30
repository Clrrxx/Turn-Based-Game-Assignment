package Game;
//The playthrough itself.
import java.util.InputMismatchException;
import java.util.Scanner;

import Characters.MainEnemy;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.PlayerWizard;
import Characters.PlayerWarrior;
import Difficulty.DifficultyEasy;
import Difficulty.Difficulty.DifficultyTier;
import Items.Inventory;
import Items.SmokeBomb;
import Items.Item;


public class GameSessionEasy extends MainGameSession{
  private MainEnemy[] enemies;
  private boolean gameWon = false;
  private boolean usedPowerstone = false;
  private int target;
  
  //Constructor
  public GameSessionEasy(Difficulty difficulty, MainPlayer player, Inventory inventory){
    super(difficulty, player, inventory);
    startGameEasy(difficulty, player, inventory);
  }

  private SmokeBomb bomb = new SmokeBomb(99);
  public MainPlayer getPlayer(){return player;}
  public MainEnemy[] getEnemies(){return enemies;}
  public Difficulty getDifficulty(){return difficulty;}
  public Inventory getInven(){return inventory;}
  public int getTarget(){return target;}
  
  //if game over, we print game over screen. 
  //see how we want to implement user select after game over.

  protected void startGameEasy(Difficulty difficulty, MainPlayer player, Inventory inventory){
    System.out.println("\nNew Game Start!\n");
    char attack = ' ';
  

    Scanner newscan = new Scanner(System.in);
    //get action value for player
    int playerAV = player.getActionValue();

    //get the action value for the enemy
    this.enemies = difficulty.getInitialSpawn();
    int[] enemiesAV = new int[enemies.length];
    
    for (int i = 0; i<enemies.length; i++){
      enemiesAV[i] = enemies[i].getActionValue();
    }
    //this sets all the actionvalue for everyone first
    System.out.println();
    difficulty.printEnemy(enemies);
    System.out.println();
  
    while (!gameWon){
      //every loop redeclare player loop false-> that way only when exception occurs it will skip enemy turn and reset
      boolean playerValidTurn = false;

      //every loop ticks player and enemies av down like for star rail and final fantasy
      playerAV --;
      for (int i = 0; i<enemiesAV.length; i++) enemiesAV[i]--;
      
      //check both player and enemy AV
      if (playerAV == 0){
        //player takes turn
        //depends on UI player chooses who to attack
        while(!playerValidTurn){
          System.out.println("\nWhat shall you do? \nBasic Attack [B] / Special Attack [S] / Defense Buff[D] / Use Item [U]");
          attack = newscan.next().toUpperCase().charAt(0);

          switch (attack){
            case 'B':
              System.out.println("Choose who to attack: [1, 2, 3]");
              target = newscan.nextInt();
              if (enemies[target-1].getHealth() > 0){
                int damage = player.basicAttack(enemies[target-1]);
                enemies[target-1].takeDamage(damage);
                
                System.out.print("\nYou did "+damage+" damage to ");
                enemies[target-1].printName();
              } 
              System.out.println();
              playerValidTurn = true;
              break;

            case 'S':
              System.out.println("Choose who to attack: [1, 2, 3]");
              target = newscan.nextInt();
              
              if (player.getskillcooldown()>0){
                player.specialSkill(enemies, target-1, usedPowerstone);
              }else{
                //if not use Powerstone -> skill on cooldown, if use powerstone -> skill not on cooldown
                int special = player.specialSkill(enemies, target-1, usedPowerstone);
                System.out.println("You did a total of "+special+" damage.");
                playerValidTurn = true;
              }
              break;
            
            case 'D':
              player.defendSkill();
              int def = player.effectiveDefense();
              System.out.println("Defense UP! You have " +def +" now");
              playerValidTurn = true;
              break;
            
            case 'U':
              //use item
                inventory.printInventory();
                System.out.println("What shall you use? Pick your item: ");
                int itemchoice = newscan.nextInt();
                Item selected = inventory.getiItem(itemchoice-1);
                if (selected.isAvailable()){
                  selected.ApplyEffect(this);
                  inventory.removeFromInventory(itemchoice-1);
                  inventory.printInventory();
                }
                playerValidTurn = true;
              break;
            
            default:
              System.out.println("Invalid Input. Try again.");
          }
        }
        System.out.println("\nThe health of the enemies remaining: ");
        difficulty.printEnemy(enemies);
        System.out.println("\n");

        playerAV = player.getActionValue();
        player.tickAll();
      }
      
      //for enemy
      //core logic is simple => player inputs are invalid, skip enemy turn 
      
      for (int j = 0; j<enemiesAV.length; j++){
        if (enemiesAV[j] == 0 && bomb.getUsedSmokebomb()){
          //punish the player => smoke bomb cooldown will take effect at the same time as special skill cooldown; both will tick down simultaneously
          player.takeDamage(0);
          System.out.println("Smoke bomb was used, you evaded the attack!");
          bomb.tickAll();
          
          enemiesAV[j] = enemies[j].getActionValue();
          enemies[j].tickAll();
        }
        else if (enemiesAV[j] == 0 && !(enemies[j].stunStatus()) && enemies[j].getHealth()>0){
          //enemies take turn
          int damage = enemies[j].basicAttack(player);
          int damageTaken = player.takeDamage(damage);
          System.out.print("You took " + damageTaken +" damage. ");
          System.out.println("Health remaining: " + player.getHealth());

          enemiesAV[j] = enemies[j].getActionValue();
          enemies[j].tickAll();
        }
        
      }
      //check if either player health == 0 or ALL enemy health == 0
      //later stages will have if ALL enemies health == 0 summons backup layer
      //if player health == 0 break the loop -> exit
      boolean allDead = true;

      for (MainEnemy enemy: enemies){
        if (enemy.getHealth() > 0) {allDead = false;}
      }
      if (allDead == true){gameWon = true;}
      if (player.getHealth() == 0){break;}
    }

    //main game logic
  
    //use action value to determine turn count

    //Game Over
    if (gameWon == true){
      System.out.println("You have conquered the dungeon, ");//add playerName
      System.out.println("You Win!!");
    }else {
      System.out.println("YOU DIED");
      System.out.println("Game Over!!");
      //see if need to let user to retry, to redirect to loading screen
      }
  }
}



