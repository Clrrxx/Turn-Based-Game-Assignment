package StatusEffects;

import Characters.MainEntity;

public class SmokeBombEffect implements TickingStatusEffect{
    private int smokeBombDuration;

    public SmokeBombEffect(int duration){
        this.smokeBombDuration = duration;
    }

    public void applyEffect(MainEntity enemy){
        enemy.setSmoke(true);
        enemy.addStatusEffect(this);
    }

    public void onTurnEnd(MainEntity enemy){
        smokeBombDuration--;
        if(isExpired())enemy.setSmoke(false);
    }

    public boolean isExpired(){return smokeBombDuration <= 0;}
}
