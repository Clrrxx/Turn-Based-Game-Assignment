package Strategies;

import java.util.List;

import Characters.MainEntity;

public abstract class UniSkill {
    protected final int maxCooldown;
    protected int cooldown;

    public UniSkill(int maxCoolDown){
        this.maxCooldown = maxCoolDown;
    }

    public abstract boolean onCooldown();
    public abstract int execute(MainEntity player, List<MainEntity>targets);
    public abstract void skillTickDown();
}
