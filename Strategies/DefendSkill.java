package Strategies;

import Characters.MainEntity;
import StatusEffects.DefenseBuffEffect;


public class DefendSkill implements SelfSkill{
    private final int defenseBonus;
    private final int duration;

    public DefendSkill(int defenseBonus, int duration){
        this.defenseBonus = defenseBonus;
        this.duration = duration;
    }

    public int execute(MainEntity player){
        DefenseBuffEffect effect = new DefenseBuffEffect(defenseBonus, duration);
        effect.applyEffect(player);
        player.addStatusEffect(effect);
        return 0;
    }

    public boolean onCooldown() {return false;}
    public void skillTickDown(){}
}
