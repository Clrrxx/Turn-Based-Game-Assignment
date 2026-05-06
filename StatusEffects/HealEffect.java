package StatusEffects;

import Characters.MainEntity;

public class HealEffect implements SingleStatusEffect{
    private final int healthBonus;

    public HealEffect(int health){
        this.healthBonus = health;
    }
    public void applyEffect(MainEntity player){
        player.addHealth(healthBonus);
    }
}
