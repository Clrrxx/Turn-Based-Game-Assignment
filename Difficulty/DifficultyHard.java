package Difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.MainEntity;
import Strategies.BasicAtkStrat;

public class DifficultyHard extends Difficulty{
    public DifficultyHard(){
        super(createInitial(), createBackup());
        this.tier = DifficultyTier.HARD;
    }

    //define the methods as static, allows for them to be called by the super without issues
    private static List<MainEntity> createInitial(){
        return new ArrayList<>(Arrays.asList(
            new EnemyGoblin(new BasicAtkStrat()), 
            new EnemyGoblin(new BasicAtkStrat())
        ));
    }

    private static List<MainEntity> createBackup(){
        return  new ArrayList<>(Arrays.asList( 
            new EnemyGoblin(new BasicAtkStrat()), 
            new EnemyWolf(new BasicAtkStrat()), 
            new EnemyWolf(new BasicAtkStrat())
        ));
    }

    public DifficultyTier getTier(){return tier;}

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
