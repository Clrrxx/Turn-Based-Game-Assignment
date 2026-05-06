package Items;

import Characters.MainEntity;
import Game.BattleUI;
import StatusEffects.HealEffect;

import java.util.List;

public class Potion extends Item{
    public Potion(int healAmt){
        super("Health Pot");
    }

    @Override
    public void useItem(MainEntity player, List<MainEntity> enemies){
        BattleUI UI = new BattleUI();
        
        if (player.getHealth() == player.getbaseHP()){
            System.out.println("Health is full already.");
            return;
        }

        new HealEffect(100).applyEffect(player);
        System.out.println("Health potion used." + player.getName() + " new health is " + player.getHealth() + " HP.");
        UI.displayPlayerHealth(null);
    }
    public String getName(){return this.name;}
}
