package Items;
import Characters.MainPlayer;
import Game.MainGameSession;


public class Potion extends Item{
    private int HealAmt = 100;

    public Potion(int quantity){
        super("Health Pot", quantity);
    }

    public String getName(){return this.name;}

    public void ApplyEffect(MainGameSession session){
        MainPlayer player = session.getPlayer();
        int currentHP = player.getHealth();
        if (currentHP == player.getbaseHP()){
            System.out.println("You are already at full health!");
        }
        else if (isAvailable()){
            int healed = Math.min(currentHP + HealAmt, player.getbaseHP());
            player.healHealth(healed);

            activate();
            System.out.println("A Health Potion was used, "+healed+" HP was healed. You have "+player.getHealth()+ " HP now.");
            deactivate();
        }else{
            System.out.println("You ran out of items to use!");
        }
    }
}
