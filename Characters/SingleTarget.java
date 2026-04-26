package Characters;
import java.util.List;

public class SingleTarget implements Skill{

    public int execute(MainEntity player, List<MainEntity>targets){
        return Math.max(0, player.effectiveAttack() - targets.get(0).effectiveDefense());
    }

    public boolean onCooldown(MainEntity player){
        return (player.getSkillCooldown() > 0);
    }

    public boolean usedPowerstone(){
        return true;    //placeholder
    }
}


