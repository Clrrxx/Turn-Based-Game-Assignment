package StatusEffects;

import Characters.MainEntity;

public class AttackBuffEffect implements TickingStatusEffect{
    private boolean permanent;
    private int attackBuff;

    public AttackBuffEffect(int attackBuff, boolean permanent){
        this.attackBuff = attackBuff;
        this.permanent = permanent;
    }

    public void applyEffect(MainEntity player){
        player.addAttack(attackBuff);
    }

    public void onTurnEnd(MainEntity player){
        if (isExpired()) player.removeAttack(attackBuff);
    }

    public boolean isExpired(){return !permanent;}
    public int getAttackBonus(){return attackBuff;}
}
