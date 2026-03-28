package Items;
import Characters.MainPlayer;


public class Potion extends Item{
    public Potion(String name){
        super(name);
    }
    public void printName(){System.out.println(name);}
    public String getName(){return this.name;}

    public int HealHealth(MainPlayer player){
        int currentHP = player.getHealth();
        int healed = Math.max(currentHP + 100, player.getmaxHP());
        return healed;
    }
}
