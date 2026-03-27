
//The playthrough itself.

import Difficulty.Difficulty;

public class GameSession {


  
  //need to bring the Item[] array that contains the list (2 items) that user has chosen here.

  //private static 
  


  //Constructor
  public GameSession(Difficulty difficulty){
    startGame(difficulty);
  }
  
  //if game over, we print game over screen. 
  //see how we want to implement user select after game over.
  private boolean isGameOver = false;

  public void startGame(Difficulty difficulty){
    System.out.println("New Game Start!");
    boolean gameWon = false;
    String Name = difficulty.getTier();

    while(!isGameOver){
      if (Name.equals("Easy")){
      //easy logic 
      }else if (Name.equals("Medium")){
        //medium logic
      }else{
        //hard logic
      }














      //main game logic
      
      //use action value to determine turn count
      //need to figure out when multiple characters are at 0 AV -> AV minus needs to halt for a bit 
      //and let the characters finish their turns before continuing


      //set gameWon.
      gameWon = true;
    }

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
