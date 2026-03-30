package Characters;

public abstract class MainPlayer extends MainEntity implements PlayerBuff{
    public MainPlayer(String name, int health, int attack, int defense, int speed){
        super(name, health, attack, defense, speed);
    }
    
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public abstract void gameReset();
    public abstract int getbaseHP();
    public abstract int specialSkill(MainEnemy[] enemy, int targetIndex, boolean usedPowerstone);
    public abstract int getskillcooldown();
     
}   