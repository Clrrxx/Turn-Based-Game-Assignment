package Characters;

import java.util.List;

public interface Skill {
    int execute(MainEntity player, List<MainEntity> targets);
    boolean onCooldown(MainEntity player);
    boolean usedPowerstone();
}
