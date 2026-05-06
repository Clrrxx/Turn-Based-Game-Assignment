package Difficulty;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.MainEntity;
import Strategies.BasicAtkStrat;

public class DifficultyEasy extends Difficulty{
    
    public DifficultyEasy(){
        super(createInitial(), createBackup());
        this.tier = DifficultyTier.EASY;
    }

    private static List<MainEntity> createInitial(){
        return new ArrayList<>(Arrays.asList(
            new EnemyGoblin(new BasicAtkStrat()), 
            new EnemyGoblin(new BasicAtkStrat()), 
            new EnemyGoblin(new BasicAtkStrat())
        ));
    }

    private static List<MainEntity> createBackup(){
        return null;
    }


    public DifficultyTier getTier(){return tier;}
    public List<MainEntity> getInitial(){
        return createInitial();
    }

    public void printWaveInfo() {
    System.out.println("  Initial Spawn:");
    printEnemyCounts(initialSpawn);

    if (hasBackupSpawn()) {
        System.out.println("  Backup Spawn:");
        printEnemyCounts(backupSpawn);
    } else {
        System.out.println("  Backup Spawn: None");
    }
  }


    public void printEnemyCounts(List<MainEntity> wave) {
        int goblins = 0, wolves = 0;

        for (MainEntity e : wave) {
            if (e instanceof EnemyGoblin) goblins++;
            if (e instanceof EnemyWolf) wolves++;
        }

        if (goblins > 0) System.out.println("    - " + goblins + "x Goblin");
        if (wolves > 0)  System.out.println("    - " + wolves + "x Wolf");
    }

    public void printEnemy(List<MainEntity> wave){
        int count = 1;
        for (MainEntity e: wave){
            System.out.print(" "+ count+ " ");
            e.printName();
            System.out.print("  Health: (" + e.getHealth()+ ") | ");
            count++;
        }
    }
}
