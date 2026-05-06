import java.io.*;
import java.util.Scanner;

import Items.Inventory;
import Items.Item;
import Items.Potion;
import Items.PowerStone;
import Items.SmokeBomb;
import Strategies.AVTurnOrderStrat;
import Strategies.AoeTarget;
import Strategies.BasicAtkStrat;
import Strategies.DefendSkill;
import Strategies.SingleTarget;
import Strategies.UniSkill;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Difficulty.DifficultyEasy;
import Difficulty.DifficultyMedium;
import Game.GameSessionEasy;
import Game.GameSessionHard;
import Game.GameSessionMedium;
import Difficulty.DifficultyHard;
import Characters.PlayerWizard;
import Characters.PlayerWarrior;


//This is the main file that will run the game itself.
public class GameApp {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    BasicAtkStrat BAttack = new BasicAtkStrat();
    AVTurnOrderStrat turn = new AVTurnOrderStrat();
    DefendSkill defenseSkill = new DefendSkill(10, 3);
    UniSkill aoeTarget = new AoeTarget(3);
    UniSkill singleTarget = new SingleTarget(3);
    

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

    MainPlayer player = null;
    MainPlayer test = null;
    MainPlayer test2 = null;

    while(true){
      System.out.println("Select your class...");
      System.out.println();

      test = new PlayerWarrior("Warrior", null);
      test.showStats();
      System.out.println("Press 1 to lock in your character. ");
      System.out.println();

      test2 = new PlayerWizard("Wizard", null);
      test2.showStats();
      System.out.println("Press 2 to lock in your character. ");

     
      if (scanner.hasNextInt()){
        int userInput = scanner.nextInt();
        scanner.nextLine();
        if (userInput != 1 && userInput != 2){
          System.out.println("Invalid choice, Enter 1 or 2");
        }else if (userInput == 1){
          System.out.println("Selected Warrior!\n");
          player = new PlayerWarrior(userName, BAttack); 
          System.out.println("Your stats/attributes are:\n");
          //print out attributes
          player.showStats();
          break;
        }else if (userInput == 2){
          System.out.println("Selected Wizard!\n");
          player = new PlayerWizard(userName, BAttack);
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

    System.out.println();
    System.out.println("Select 2 items to aid your adventure, " + userName);
  
    Item[] allItems = {
      new Potion(0),
      new PowerStone(),  
      new SmokeBomb()
    };
    int count = 0;

    Inventory playerInv = new Inventory();



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

          Item selected = allItems[userSelect -1];
          if (selected instanceof Potion) playerInv.addItem(selected, 1);
          else if (selected instanceof PowerStone) playerInv.addItem(selected, 1);
          else if (selected instanceof SmokeBomb) playerInv.addItem(selected, 1);
          
          System.out.println("You selected - " + allItems[userSelect - 1].getName()+"\n");
          count++;
        }
      }else {
        System.out.println("Invalid input, please enter a number!");
        scanner.nextLine();
      }
    }

    playerInv.printInv();
    System.out.println();
    

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
        if (userChoice == 'E'){
          selectedDifficulty = easy; 
          GameSessionEasy gameEasy = new GameSessionEasy(selectedDifficulty, player, playerInv, turn, BAttack, defenseSkill, aoeTarget, singleTarget);
          gameEasy.runCombatEasy();
        }else if (userChoice == 'M'){
          selectedDifficulty = medium; 
          GameSessionMedium gameMed = new GameSessionMedium(selectedDifficulty, player, playerInv, turn, BAttack, defenseSkill, aoeTarget, singleTarget);
          gameMed.runCombatMedium();
        }else{
          selectedDifficulty = hard; 
          GameSessionHard gameHard = new GameSessionHard(selectedDifficulty, player, playerInv, turn, BAttack, defenseSkill, aoeTarget, singleTarget);
          gameHard.runCombatHard();
        }
    
        System.out.println("You selected: " + selectedDifficulty.getTier() + " difficulty.");
        break;
      }
      scanner.close();
    }
  }
}





