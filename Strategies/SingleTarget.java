package Strategies;
import java.util.List;

import Characters.MainEntity;
import StatusEffects.StunEffect;

public class SingleTarget extends UniSkill{
    public SingleTarget(int maxCooldown){
        super(maxCooldown);
    }

    public int execute(MainEntity player, List<MainEntity>targets){
        cooldown = maxCooldown;

        int damage = Math.max(0, player.effectiveAttack() - targets.get(0).effectiveDefense());
        targets.get(0).takeDamage(damage);
        System.out.println("You shield bashed " + targets.get(0).getName() +". "+ targets.get(0).getName() + " takes " + damage + " damage.");


        StunEffect stun = new StunEffect(2);
        stun.applyEffect(targets.get(0));
        
        return damage;
    }

    public boolean onCooldown(){
        return cooldown > 0;
    }

    public void skillTickDown(){
        if (cooldown>0) cooldown--;
    }
}


