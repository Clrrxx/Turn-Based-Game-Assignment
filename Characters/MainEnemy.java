package Characters;

public abstract class MainEnemy extends MainEntity{
    public MainEnemy(int health, int attack, int defense, int speed){
        super(health, attack, defense, speed);
    }
    public abstract int setStun(int duration);
    public abstract String getName();
    public abstract void printName();
    public abstract boolean stunStatus();
}