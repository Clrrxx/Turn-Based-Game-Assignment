//The playthrough itself.
import java.util.InputMismatchException;
import java.util.Scanner;
import Characters.MainEnemy;
import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.PlayerWarrior;
import Characters.PlayerWizard;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Difficulty.DifficultyEasy;
import Difficulty.DifficultyMedium;
import Difficulty.DifficultyHard;
import Difficulty.Difficulty.DifficultyTier;
import Characters.TickCooldown;

public class GameSession {
  //Constructor
  public GameSession(Difficulty difficulty, MainPlayer player){
    startGame(difficulty, player);
  }
  
  //if game over, we print game over screen. 
  //see how we want to implement user select after game over.
  private boolean isGameOver = false;

  public void startGame(Difficulty difficulty, MainPlayer player){
    System.out.println("\nNew Game Start!\n");
    boolean gameWon = false;
    char attack = ' ';

    Scanner newscan = new Scanner(System.in);
    //get action value for player
    int playerAV = player.getActionValue();

    if (difficulty.getTier() == DifficultyTier.EASY){
      //get the action value for the enemy
      MainEnemy[] enemies = difficulty.getInitialSpawn();
      int[] enemiesAV = new int[enemies.length];
      for (int i = 0; i<enemies.length; i++){
        enemiesAV[i] = enemies[i].getActionValue();
      }
      //this sets all the actionvalue for everyone first

      //another while loop to enact loop logic 
    
      while (!gameWon){
        //every loop redeclare player loop false-> that way only when exception occurs it will skip enemy turn and reset
        boolean playerSkipTurn = false;

        //every loop ticks player and enemies av down like for star rail and final fantasy
        playerAV --;
        for (int i = 0; i<enemiesAV.length; i++) enemiesAV[i]--;
        
        //check both player and enemy AV
        if (playerAV == 0){
          //player takes turn
          //depends on UI player chooses who to attack
          System.out.println();
          difficulty.printEnemy(enemies);
          System.out.println();
          try{
            System.out.println("\nWhat shall you do? \nBasic Attack [B] / Special Attack [S] / Defense Buff[D] / Use Item [U]");
            attack = newscan.next().toUpperCase().charAt(0);
          }catch (InputMismatchException e){
            System.out.println("Invalid input! Enter one valid Input.");
          }
          System.out.println("Choose who to attack: [1, 2, 3]");
          int target = newscan.nextInt();

          switch (attack){
            case 'B':
              int damage = player.basicAttack(enemies[target-1]);
              enemies[target-1].takeDamage(damage);
              System.out.print("\nYou did "+damage+" damage to ");
              enemies[target-1].printName();
              System.out.println();
              break;

            case 'S':
              int special = player.specialSkill(enemies, target-1);
              System.out.println("You did a total of "+special+" damage.");
              break;
            
            case 'D':
              player.defendSkill();
              break;
            
            case 'U':
              //use item
              break;
            
            default:
              System.out.println("Invalid Input Try again.");
              playerSkipTurn = true;
          }

          if (!playerSkipTurn){
            System.out.println("\nThe health of the enemies remaining: ");
            difficulty.printEnemy(enemies);
            System.out.println();
          }
          playerAV = player.getActionValue();
          player.tickAll();
        }
        
        //for enemy
        //core logic is simple => player inputs are invalid, skip enemy turn 
        if (!playerSkipTurn){          
          for (int j = 0; j<enemiesAV.length; j++){
            if (enemiesAV[j] == 0 && !(enemies[j].stunStatus()) && enemies[j].getHealth()>0){
              //enemies take turn
              int damage = enemies[j].basicAttack(player);
              player.takeDamage(damage);
              System.out.print("You took " + player.takeDamage(damage)+" damage. ");
              System.out.println("Health remaining: " + player.getHealth());

              //reset enemy av
              enemiesAV[j] = enemies[j].getActionValue();
              enemies[j].tickAll();
            }
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
      isGameOver = true;
    }else if (difficulty.getTier() == DifficultyTier.MEDIUM){
      //medium logic
      
    }else{
      //hard logic
      
    }














    //main game logic
    
    //use action value to determine turn count
    //need to figure out when multiple characters are at 0 AV -> AV minus needs to halt for a bit 
    //and let the characters finish their turns before continuing

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



