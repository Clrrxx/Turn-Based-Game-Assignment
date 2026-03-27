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


  public abstract MainEnemy[] getInitialSpawn();
  //need to account for null
  public abstract MainEnemy[] getBackupSpawn();
  //the function to check if there is backupSpawn
  public abstract boolean hasBackupSpawn();

  public abstract String getTier();
  public abstract void printWaveInfo();
  public abstract void printEnemyCounts(MainEnemy[] wave);
}
