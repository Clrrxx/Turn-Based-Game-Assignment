package Strategies;

import java.util.List;

import Characters.MainEntity;
import StatusEffects.AttackBuffEffect;

public class AoeTargetPS extends UniSkill{
    
    public AoeTargetPS(int maxCoolDown){
        super(maxCoolDown);
    }
     
    public int execute(MainEntity player, List<MainEntity> targets){
        int killCount = 0;
        int total = 0;
        
        for (MainEntity t: targets){
            total += t.takeDamage(Math.max(0, player.effectiveAttack() - t.effectiveDefense()));
            if (t.isDead()) killCount++;
        }
        System.out.println("FireBall used. A total of " + total + " was done to all enemies.");


        if (killCount > 0){
            int attackBuff = 10 * killCount;
            AttackBuffEffect buff = new AttackBuffEffect(attackBuff, true);
            buff.applyEffect(player);
            player.addStatusEffect(buff);
        }
        return killCount;
    }    

    public void skillTickDown(){}
    public boolean onCooldown(){return false;}
}
