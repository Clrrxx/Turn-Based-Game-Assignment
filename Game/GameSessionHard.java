package Game;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
//The playthrough itself.
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map;

import Characters.MainEntity;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Items.Inventory;
import StatusEffects.TickingStatusEffect;
import Strategies.BasicAtkStrat;
import Strategies.SelfSkill;
import Strategies.TurnOrder;
import Strategies.UniSkill;


public class GameSessionHard extends MainGameSession{
  protected boolean gameWon = false;
  protected boolean usedPowerstone = false;
  protected boolean usedSmokebomb;
  protected List<MainEntity> enemies;
  protected int target;
  protected boolean playerDead = false;
  protected static int count = 0;
  Scanner newscan = new Scanner(System.in);
  
  private final TurnOrder turnOrder;
  private final BasicAtkStrat basicAtk;
  private final SelfSkill selfSkill;
  private final UniSkill aoeSkill;
  private final UniSkill singleSkill;
  
  BattleUI BUI = new BattleUI();

  public GameSessionHard(Difficulty difficulty, MainPlayer player, 
    Inventory inventory, TurnOrder turnOrder, 
    BasicAtkStrat basicAtk,SelfSkill selfSkill, 
    UniSkill aoeSkill, UniSkill singleSkill){

    super(difficulty, player, inventory);
    enemies = difficulty.getInitialSpawn();
    this.turnOrder = turnOrder;
    this.turnOrder.initialize(player, enemies);
    this.basicAtk = basicAtk;
    this.selfSkill = selfSkill;
    this.aoeSkill = aoeSkill;
    this.singleSkill = singleSkill;
  }

  public MainPlayer getPlayer(){return player;}
  public Difficulty getDifficulty(){return difficulty;}
  public Inventory getInven(){return inventory;}
  public int getTarget(){return target;}
  
  public void runWave(List<MainEntity> waveEnemies){
    turnOrder.initialize(player, waveEnemies);
    gameWon = false;
    UniSkill skill = player.isAoE() ? aoeSkill:singleSkill;

    while(!gameWon && !playerDead){
      MainEntity current = turnOrder.getNextPlayer(player, waveEnemies);
      if (current == null) continue;

      if (current instanceof MainPlayer){ 
        count++;
        
        if (current.getHealth() <= 0){
          playerDead = true;
        }else{
          System.out.println("Turn Count: "+count);
          handlePlayerTurn(skill);
          skill.skillTickDown();
        }
      }else{ 
        handleEnemyTurn(current);
      }
      gameWon = allDead();      
      turnOrder.reset(current);
      tickStatus(current);
    }
  }

  public void runCombatHard(){
    System.out.println("\n\n\nGame Start! Wave 1/2\n\n\n");
    BUI.displayPlayerBattleStats(player);
    runWave(difficulty.getInitialSpawn());

    if (!playerDead){
        System.out.println("\n\nWave 2/2 has spawned\n\n");
        runWave(difficulty.getBackupSpawn());
    }
    gameStatus(gameWon);
  }

  protected boolean allDead(){
    for (MainEntity enemy: enemies){
        if (enemy.getHealth() > 0) {return false;}
      }
    return true;
  }

  private void handlePlayerTurn(UniSkill skill){
    boolean validInput = false;

    while (!validInput){
      System.out.println("\nWhat shall you do? \nBasic Attack [B] / Special Attack [S] / Defense Buff [D] / Use Item [U]");
      char attack = newscan.next().toUpperCase().charAt(0);

      switch (attack){
        case 'B' ->{
          MainEntity target = selectTarget();
          int damage = basicAtk.execute(player, target);
          System.out.println("\nYou did " + damage + " damage to " + target.getName());
          
          target.takeDamage(damage);
          if (target.getHealth() == 0){System.out.println(target.getName() + " has been slain. \n");}
          
          validInput = true;
        }

        case 'S' ->{
          if (!skill.onCooldown()){
            System.out.println("Special Skill used: ");
            List<MainEntity> targets = selectTargets(player);
            skill.execute(player, targets);
            validInput = true;
          }else{
            System.out.println("Skill on cooldown");
          }
        }

        case 'D' ->{
          selfSkill.execute(player);
          int def = player.effectiveDefense();
          System.out.println("Defense UP! You have " +def +" defense now");
          validInput = true;
        }

        case 'U' ->{
          handleItems();
          validInput = true;
        }

        default ->{
          System.out.println("Invalid, try again.");
        }
      }
    }
    difficulty.printEnemy(enemies);
    System.out.println("\n");
  }

  private void handleEnemyTurn(MainEntity current){
    if (current.getHealth() <= 0) return;

    if (current.isStunned()){
      System.out.println(current.getName() + " is stunned, unable to act.\n");

    }else if (current.isSmoked()){
      System.out.println("Battlefield is covered in Smoke, " + current.getName() + "is unable to act.");
    
    }else{
      System.out.println(current.getName() + "(" + current.getHealth() + "HP) takes their turn!");
      int damage = basicAtk.execute(current, player);

      System.out.println(current.getName() + " does " + damage + " damage to you.\n");
      player.takeDamage(damage);

      BUI.displayPlayerHealth(player);
      System.out.println("\n");
    }
  }

  private MainEntity selectTarget(){
    List<MainEntity> living = enemies.stream().filter(e->e.getHealth()>0).collect(Collectors.toList());
    
    difficulty.printEnemy(living);
    System.out.print("\nChoose who to attack: [");
    for (int i = 0; i<living.size(); i++){System.out.print(i+1); if (i<living.size()-1){System.out.print(", ");}}
    System.out.print("]\n");

    while (true){
      try{
        int target = newscan.nextInt();
        if (target < 1 || target > living.size()){System.out.println("Out of range, try again."); continue;}

        return living.get(target-1);
        
      }catch(InputMismatchException e){
        System.out.println("Invalid input, try again.");
        newscan.nextLine();
      }
    }
  }

  private List<MainEntity> selectTargets(MainEntity player){
    if (player.isAoE()){
      return enemies.stream().filter(e->e.getHealth()>0).collect(Collectors.toList());
    }else{
      return List.of(selectTarget());
    }
  }

  private void tickStatus(MainEntity player){
    player.getStatusEffects().forEach(e -> e.onTurnEnd(player));
    player.getStatusEffects().removeIf(TickingStatusEffect::isExpired);
  }

  private void handleItems(){
    Map<String, Integer> available = inventory.getQuantities();
    if (available.isEmpty()) {
      System.out.println("Inventory is Empty");
      return;
    }

    boolean validItemUse = false;
    while(!validItemUse){
      System.out.println("What shall you use?");
      List<String> itemNames = new ArrayList<>(available.keySet());
      for (int i = 0; i<itemNames.size(); i++){
        System.out.println(" ["+ (i+1) + "] " + itemNames.get(i) + " x" + available.get(itemNames.get(i)));
      }

      int choice = Integer.parseInt(newscan.next().trim()) - 1;

      if (choice < 0 || choice >= itemNames.size()){
        System.out.println("Invalid choice, try again.");
        continue;
      }

      String chosen = itemNames.get(choice);
      inventory.getItem(chosen).ifPresent(item -> {
        item.useItem(player, enemies);
        inventory.consumeItem(chosen);
      });
      validItemUse = true;
    }
  }
  
  protected void gameStatus(boolean gameWon){
    if (gameWon == true){
      System.out.println("You have conquered the dungeon, ");
      System.out.println("You Win!!");
    }else {
      System.out.println("YOU DIED");
      System.out.println("Game Over!!");
    }
  }
}

