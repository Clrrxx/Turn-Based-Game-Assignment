package StatusEffects;

import Characters.MainEntity;

public interface TickingStatusEffect {
    void applyEffect(MainEntity entity);
    void onTurnEnd(MainEntity entity);
    boolean isExpired();
}
