package Difficulty;
import Characters.MainEnemy;

public abstract class Difficulty {
  public enum DifficultyTier{EASY, MEDIUM, HARD};
  protected MainEnemy[] initialSpawn;
  protected MainEnemy[] backupSpawn; //just make this null for easy level where there is no backupSpawn`

  protected DifficultyTier tier; 

  public Difficulty(MainEnemy[] initialSpawn, MainEnemy[] backupSpawn){
    this.initialSpawn = initialSpawn;
    this.backupSpawn = backupSpawn;
  }


  public MainEnemy[] getInitialSpawn() { return initialSpawn; }
    
    //need to account for null
  public MainEnemy[] getBackupSpawn() {return backupSpawn; }

    //the function to check if there is backupSpawn
  public boolean hasBackupSpawn() {return backupSpawn != null;} 

  public abstract DifficultyTier getTier();
  public abstract void printWaveInfo();
  public abstract void printEnemyCounts(MainEnemy[] wave);
  public abstract void printEnemy(MainEnemy[] wave);
}
