package Strategies;

import Characters.MainEntity;

public class BasicAtkStrat implements ActionStrat{
    public int execute(MainEntity player, MainEntity target){
        return Math.max(0, player.effectiveAttack() - target.effectiveDefense());
    }
}
