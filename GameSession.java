
//The playthrough itself.

import Characters.MainEnemy;
import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.PlayerWarrior;
import Characters.PlayerWizard;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Difficulty.Difficulty.DifficultyTier;

public class GameSession {
  //Constructor
  public GameSession(Difficulty difficulty, MainPlayer player){
    startGame(difficulty, player);
  }
  
  //if game over, we print game over screen. 
  //see how we want to implement user select after game over.
  private boolean isGameOver = false;
  private boolean turn = false;

  public void startGame(Difficulty difficulty, MainPlayer player){
    System.out.println("New Game Start!");
    boolean gameWon = false;
    
    //get action value for player
    int playerAV = player.getActionValue();

    if (difficulty.getTier() == DifficultyTier.EASY){
      //another while loop to enact loop logic 
      while (!isGameOver){
        //get the action value for the enemy
        MainEnemy[] enemies = difficulty.getInitialSpawn();
        int[] enemiesAV = new int[enemies.length];
        for (int i = 0; i<enemies.length; i++){
          enemiesAV[i] = enemies[i].getActionValue();
        }
        //this sets all the actionvalue for everyone first
        
        while (!gameWon){
          //check both player and enemy AV
          if (PlayertakeTurn(playerAV)){
            //player takes turn
          }

          if (EnemyTakeTurn(enemiesAV)){
            enemies.
          }




        }
        





      }
    //easy logic 
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

  //when AV == 0 => take Turn 
  private boolean PlayertakeTurn(int playerAV){
    if (playerAV == 0){
      playerAV = 
      return true;}
    return false;
  }

  private boolean EnemyTakeTurn(int[] enemyAv){
    for (int av: enemyAv){
      if (av == 0) return true;
    }
    return false;
  }
}



