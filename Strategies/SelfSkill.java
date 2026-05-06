package Strategies;



import Characters.MainEntity;

public interface SelfSkill {
    int execute(MainEntity player);
    boolean onCooldown();
    
    void skillTickDown();
}
