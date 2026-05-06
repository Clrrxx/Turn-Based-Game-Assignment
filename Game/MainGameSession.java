package Game;

import java.util.List;

import Characters.MainEntity;
import Characters.MainPlayer;
import Difficulty.Difficulty;
import Items.Inventory;

public abstract class MainGameSession {
    protected MainPlayer player;
    protected Difficulty difficulty;
    protected Inventory inventory;
    protected List<MainEntity> enemies;

    public MainGameSession(Difficulty difficulty, MainPlayer player, Inventory inventory){
        this.player = player;
        this.difficulty = difficulty;
        this.inventory = inventory;
    }
    public abstract MainPlayer getPlayer();
    public abstract Inventory getInven();
    public abstract Difficulty getDifficulty();
    public abstract int getTarget();

    protected abstract void gameStatus(boolean gameWon);

}
