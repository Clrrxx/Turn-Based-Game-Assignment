package Strategies;

import java.util.List;

import Characters.MainEntity;
import StatusEffects.StunEffect;

public class SingleTargetPS extends UniSkill{

    public SingleTargetPS(int maxCoolDown){
        super(maxCoolDown);
    }

    public int execute(MainEntity player, List<MainEntity>targets){
        int damage = Math.max(0, player.effectiveAttack() - targets.get(0).effectiveDefense());
        targets.get(0).takeDamage(damage);
        System.out.println("You shield bashed " + targets.get(0).getName() +". "+ targets.get(0).getName() + " takes " + damage + " damage.");

        StunEffect stun = new StunEffect(2);
        stun.applyEffect(targets.get(0));
        
        return damage;
    }

    public void skillTickDown(){}
    public boolean onCooldown(){return false;}
}
