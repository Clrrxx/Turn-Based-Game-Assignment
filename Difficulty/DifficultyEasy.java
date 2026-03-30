package Difficulty;

import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.MainEnemy;

public class DifficultyEasy extends Difficulty{
    public DifficultyEasy(){
        super(createInitial(), createBackup());
        this.tier = DifficultyTier.EASY;
    }

    private static MainEnemy[] createInitial(){
        return new MainEnemy[]{new EnemyGoblin(), new EnemyGoblin(), new EnemyGoblin()};
    }

    private static MainEnemy[] createBackup(){
        return new MainEnemy[]{null};
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


    public void printEnemyCounts(MainEnemy[] wave) {
        int goblins = 0, wolves = 0;

        for (MainEnemy e : wave) {
            if (e instanceof EnemyGoblin) goblins++;
            if (e instanceof EnemyWolf) wolves++;
        }

        if (goblins > 0) System.out.println("    - " + goblins + "x Goblin");
        if (wolves > 0)  System.out.println("    - " + wolves + "x Wolf");
    }

    public void printEnemy(MainEnemy[] wave){
        int count = 1;
        for (MainEnemy e: wave){
            System.out.print(" "+ count+ " ");
            e.printName();
            System.out.print("  Health: (" + e.getHealth()+ ") | ");
            count++;
        }
    }
}
