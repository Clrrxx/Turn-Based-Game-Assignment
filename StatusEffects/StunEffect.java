package StatusEffects;

import Characters.MainEntity;

public class StunEffect implements TickingStatusEffect{
    private int stunTurnRemaining;

    public StunEffect(int duration){
        this.stunTurnRemaining = duration;
    }

    public void applyEffect(MainEntity enemy){
        enemy.setStun(true);
        enemy.addStatusEffect(this);
    }

    public void onTurnEnd(MainEntity enemy){
        stunTurnRemaining--;
        if (isExpired()) enemy.setStun(false);
    }

    public boolean isExpired(){return stunTurnRemaining <= 0;}
}
