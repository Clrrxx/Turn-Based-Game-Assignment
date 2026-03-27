package Difficulty;

import Characters.EnemyGoblin;
import Characters.EnemyWolf;
import Characters.MainEnemy;

public class DifficultyMedium extends Difficulty{
    public DifficultyMedium(){
        super(createInitial(), createBackup());
        this.tier = DifficultyTier.MEDIUM;
    }

    //define the methods as static, allows for them to be called by the super without issues
    private static MainEnemy[] createInitial(){
        return new MainEnemy[]{ new EnemyGoblin(), new EnemyWolf() };
    }

    private static MainEnemy[] createBackup(){
        return  new MainEnemy[]{ new EnemyWolf(), new EnemyWolf() };
    }

    public MainEnemy[] getInitialSpawn() { return createInitial(); }
    
    //need to account for null
    public MainEnemy[] getBackupSpawn() {return createBackup(); }

    //the function to check if there is backupSpawn
    public boolean hasBackupSpawn() {return backupSpawn != null;} 

    public String getTier(){return "Medium";}

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

}
