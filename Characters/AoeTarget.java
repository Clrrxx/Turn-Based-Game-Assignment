package Characters;

import java.util.List;

public class AoeTarget implements Skill{
    public int execute(MainEntity player, List<MainEntity> targets){
        int total = 0;
        for (MainEntity t: targets){
            total += t.takeDamage(Math.max(0, player.effectiveAttack() - t.effectiveDefense()));
        }
        return total;
    }    
    
    public boolean onCooldown(MainEntity player){
        return (player.getSkillCooldown() > 0);
    }

    public boolean usedPowerstone(){
        return true;
    }
}
