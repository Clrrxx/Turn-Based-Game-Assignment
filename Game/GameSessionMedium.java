package Game;
//The playthrough itself.
import java.util.Scanner;

import Characters.MainEnemy;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Items.Inventory;
import Items.SmokeBomb;
import Items.Item;


public class GameSessionMedium extends MainGameSession{
  protected MainEnemy[] enemies;
  protected boolean gameWon = false;
  protected boolean usedPowerstone = false;
  protected boolean usedSmokebomb;
  protected int target;
  
  //Constructor
  public GameSessionMedium(Difficulty difficulty, MainPlayer player, Inventory inventory){
    super(difficulty, player, inventory);
    startGameEasy(difficulty, player, inventory);
  }

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
    int itemchoice = 1; //garbage value first to allow the loop to run 
    int level = 1;

    Scanner newscan = new Scanner(System.in);
    //get action value for player
    int playerAV = player.getActionValue();

    SmokeBomb bomb = getSmokeBomb();

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
                System.out.println("Choose who to attack: ");
                System.out.print("[");
                for (int i = 0; i<enemies.length; i++) System.out.print(i + ", ");
                System.out.print("]");

                target = newscan.nextInt();
                if (enemies[target-1].getHealth() > 0){
                    int damage = player.basicAttack(enemies[target-1]);
                    enemies[target-1].takeDamage(damage);
                    
                    System.out.print("\nYou did "+damage+" damage to ");
                    enemies[target-1].printName();
                }else{
                    int damage = player.basicAttack(enemies[target-1]);
                    enemies[target-1].takeDamage(damage);
                } 
                System.out.println();
                playerValidTurn = true;
                break;

                case 'S':
                System.out.println("Choose who to attack: ");
                System.out.print("[");
                for (int i = 0; i<enemies.length; i++) System.out.print(i + ", ");
                System.out.print("]");
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
                player.activateDefend(3);
                int def = player.effectiveDefense();
                System.out.println("Defense UP! You have " +def +" defense now");
                playerValidTurn = true;
                break;
                
                case 'U':
                //use item
                try{
                    inventory.printInventory();
                    if (inventory.getInvenStatus()){
                    System.out.println("What shall you use? Pick your item: ");
                    itemchoice = newscan.nextInt();
                    Item selected = inventory.getiItem(itemchoice-1);
                
                    selected.ApplyEffect(this);
                    inventory.removeFromInventory(itemchoice-1);
                    inventory.printInventory();
        
                    playerValidTurn = true;
                    
                    }else{
                    System.out.println("Unable to use item, no more items to use\n");
                    }
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Invalid Index. Try Again\n");
                }
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
        for (int j = 0; j<enemiesAV.length; j++){
            if (enemiesAV[j] == 0 && enemies[j].getHealth()>0){
            if (bomb != null && bomb.getUsedSmokebomb()&& enemies[j].smokeStatus()){
                //punish the player => smoke bomb cooldown will take effect at the same time as special skill cooldown; both will tick down simultaneously
                System.out.println("You evaded the attack!");
            }
            else{
                //enemies take turn
                int damage = enemies[j].basicAttack(player);
                int damageTaken = player.takeDamage(damage);
                System.out.print("You took " + damageTaken +" damage. ");
                System.out.println("Health remaining: " + player.getHealth()+"\n");

            }
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
        if (allDead == true && level < 2){    //2 waves for medium
            System.out.printf("Wave %d dead, Wave %d spawning in", level, ++level);
            this.enemies = difficulty.getBackupSpawn();
            player.onLevelEnd();
            System.out.println();
            difficulty.printEnemy(enemies);
            System.out.println();
        }
        if(allDead == true && level>1){
            gameWon = true;
        }
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
    newscan.close();
  }

  //to preserve polymorphism, need to find smokebomb from inventory, since i need to use smokebomb functions.
  //if i declare a new instance of smokebomb, code will look at that smokebomb instead of the smokebomb in inventory, => resulting in when 
  //smoke bomb is used not correctly activated.

  private SmokeBomb getSmokeBomb(){
    for (int i = 0; i<inventory.getSize(); i++){
      if (inventory.getiItem(i) instanceof SmokeBomb){
        return (SmokeBomb) inventory.getiItem(i);
      }
    }
    return null;
  }

}


//inventory call should reject player when it is empty fix that pls tmr
