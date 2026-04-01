package Game;

import Characters.MainEnemy;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Items.Inventory;
import Items.SmokeBomb;

public abstract class MainGameSession {
    protected MainPlayer player;
    protected Difficulty difficulty;
    protected Inventory inventory;
    protected MainEnemy[] enemies;

    public MainGameSession(Difficulty difficulty, MainPlayer player, Inventory inventory){
        this.player = player;
        this.difficulty = difficulty;
        this.inventory = inventory;
    }
    public abstract MainPlayer getPlayer();
    public abstract MainEnemy[] getEnemies();
    public abstract Inventory getInven();
    public abstract Difficulty getDifficulty();
    public abstract int getTarget();

    protected abstract SmokeBomb getSmokeBomb();
    protected abstract void playerDoBasicAttack(int target);
    protected abstract boolean playerDoSKill(int target);
    protected abstract boolean playerUseItem(int itemchoice);
    protected abstract void gameStatus(boolean gameWon);
    protected abstract void enemyDoDamage(int num);

}
