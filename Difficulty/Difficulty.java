package Difficulty;
import java.util.List;


import Characters.MainEntity;

public abstract class Difficulty {
  public enum DifficultyTier{EASY, MEDIUM, HARD};
  protected List<MainEntity> initialSpawn;
  protected List<MainEntity> backupSpawn; //just make this null for easy level where there is no backupSpawn`

  protected DifficultyTier tier; 

  public Difficulty(List<MainEntity> initialSpawn, List<MainEntity> backupSpawn){
    this.initialSpawn = initialSpawn;
    this.backupSpawn = backupSpawn;
  }


  public List<MainEntity> getInitialSpawn() { return initialSpawn; }
    
    //need to account for null
  public List<MainEntity> getBackupSpawn() {return backupSpawn; }

    //the function to check if there is backupSpawn
  public boolean hasBackupSpawn() {return backupSpawn != null;} 

  public abstract DifficultyTier getTier();
  public abstract void printWaveInfo();
  public abstract void printEnemyCounts(List<MainEntity> wave);
  public abstract void printEnemy(List<MainEntity> wave);
}
