package Charas;

public abstract class MainPlayer extends MainEntity {
    public MainPlayer(String name, int health, int attack, int defense, int speed){
        super(name, health, attack, defense, speed);
    }
    
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public abstract int defendSkill();
    public abstract void gameReset();
    public abstract void getInventory();
     
}   
