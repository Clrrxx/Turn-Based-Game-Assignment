import java.io.*;
import java.util.Scanner;

import Items.Inventory;
import Items.Item;
import Items.Potion;
import Items.PowerStone;
import Items.SmokeBomb;
import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.MainEnemy;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Difficulty.DifficultyEasy;
import Difficulty.DifficultyMedium;
import Game.MainGameSession;
import Game.GameSessionEasy;
import Difficulty.DifficultyHard;
import Characters.PlayerWizard;
import Characters.PlayerWarrior;

//This is the main file that will run the game itself.
public class GameApp {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    //print game title
    try (BufferedReader reader = new BufferedReader(new FileReader("gametitle.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading title file.");
        }

    System.out.println("Welcome to Generic Text-Based Game 1111!");
    System.out.println("Will you conquer the dungeon? Or fall like the rest of the previous adventurers?");

    //from this point in GameApp, this is counted as the loading screen. A GameSession will only be instantiated AFTER loading screen.
    String userName;

    while (true){
      System.out.println("Enter your name, stranger...");

      userName = scanner.nextLine();

      if (userName.trim().isEmpty() ){
        System.out.println("Name cannot be empty.");
      }else if (userName.length() > 20){
        System.err.println("Name too long! 20 characers max.");
      }else {
        System.out.println("Welcome to the game, " + userName);
        break;
      }
    }

    //pass it to Warrior/Wizard object, not Main Player
    MainPlayer player = null;
    MainPlayer test = null;
    MainPlayer test2 = null;

    while(true){
      System.out.println("Select your class...");
      System.out.println();

      test = new PlayerWarrior("Warrior");
      test.showStats();
      System.out.println("Press 1 to lock in your character. ");
      System.out.println();

      test2 = new PlayerWizard("Wizard");
      test2.showStats();
      System.out.println("Press 2 to lock in your character. ");

     
      if (scanner.hasNextInt()){
        int userInput = scanner.nextInt();
        scanner.nextLine();
        if (userInput != 1 && userInput != 2){
              System.out.println("Invalid choice, Enter 1 or 2");
            }else if (userInput == 1){
              System.out.println("Selected Warrior!\n");
              player = new PlayerWarrior(userName); 
              System.out.println("Your stats/attributes are:\n");
              //print out attributes
              player.showStats();
              break;
            }else if (userInput == 2){
              System.out.println("Selected Wizard!\n");
              player = new PlayerWizard(userName);
              System.out.println("Your stats/attributes are:\n");
              //print out attributes
              player.showStats();
              break;
            }
      }else {
        System.out.println("Invalid input, please enter a number!");
        scanner.nextLine();
      }
    
    }

    //option to pick items, so we need to show the list of items available.
    System.out.println();
    System.out.println("Select 2 items to aid your adventure, " + userName);

    //BASED ON PROJECT SPECS, we HAVE to let user pick 2 items, not one or other number, but 2.
    //we also have to print the number for each item.
    //there are a total of 3 items in stated in the proj specs.

    Item[] allItems = {
      //new Potion("Potion"),
      new Potion( 1),
      //new Smokebomb("smokebomb")
      new PowerStone(1),  
      //new PowerStone("Power Stone"),
      new SmokeBomb(1)
    };
    int count = 0;

    //stores the user's choice for whichever items they want
    Inventory playerInv = new Inventory(2);

    //System.out.println("Type in the item number and press enter for each item, you can select 2 items before your run");

    //prints out the menu for easier item viewing. 
    while (count < 2){
      for (int i = 0; i < allItems.length; i++){
        System.out.println((i + 1) + ". " + allItems[i].getName());
      }
      System.out.println("Pick Item " + (count + 1) + ":");

      if (scanner.hasNextInt()){
        int userSelect = scanner.nextInt();
        scanner.nextLine();

        //1->Potion
        //2->PowerStone
        //3->SmokeBomb
        
        if (userSelect < 1 || userSelect > allItems.length){
          System.out.println("Invalid number, enter a number from 1 to 3.");
        }else{
          //duplicate items ARE allowed.
          Item selected = allItems[userSelect -1];
          if (selected instanceof Potion) playerInv.addToInventory(new Potion(1));
          else if (selected instanceof PowerStone) playerInv.addToInventory(new PowerStone(1));
          else if (selected instanceof SmokeBomb) playerInv.addToInventory(new SmokeBomb(1));
          
          //Need to implement printing of item name
          System.out.println("You selected - " + allItems[userSelect - 1].getName()+"\n");
          count++;
        }
      }else {
        System.out.println("Invalid input, please enter a number!");
        scanner.nextLine(); //clears the bad input from buffer.
      }
    }
    playerInv.printInventory();
    System.out.println();
    

    //user now selects Difficulty, Easy Medium or Hard, enemy needs to show their attributes too.
    //this is the difficulty we will pass into GameSession
    Difficulty selectedDifficulty = null;

    DifficultyEasy easy = new DifficultyEasy();
    DifficultyMedium medium = new DifficultyMedium();
    DifficultyHard hard = new DifficultyHard();

    while (true){
      System.out.println("Choose your difficulty");

      System.out.println("===== E. Easy =====");
      easy.printWaveInfo();
      
      System.out.println("===== M. Medium =====");
      medium.printWaveInfo();

      System.out.println("===== H. Hard =====");
      hard.printWaveInfo();

      System.out.println("Enter your choice: (E, M, H):");
      char userChoice = scanner.next().toUpperCase().charAt(0);

      if (userChoice != 'E' && userChoice != 'M' && userChoice != 'H'){
        System.out.println("Please enter a valid choice: E, M or H and press enter.");
      }else {
        // match char to difficulty object
        if (userChoice == 'E'){selectedDifficulty = easy; new GameSessionEasy(selectedDifficulty, player, playerInv);}
        else if (userChoice == 'M') selectedDifficulty = medium;
        else                        selectedDifficulty = hard;

    
        System.out.println("You selected: " + selectedDifficulty.getTier() + " difficulty.");
        break;
      }
    }
    //with these userSelected fields, we will construct a new GameSession.
  }
}
