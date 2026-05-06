package Strategies;

import java.util.List;

import Characters.MainEntity;

public interface TargetSkill {
    int execute(MainEntity player, List<MainEntity> targets);
    boolean onCooldown();
    void skillTickDown();
}
