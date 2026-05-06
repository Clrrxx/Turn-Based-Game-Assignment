package StatusEffects;

import Characters.MainEntity;

public class DefenseBuffEffect implements TickingStatusEffect{
    private int defenseTurnRemaining;
    private final int defenseBonus;

    public DefenseBuffEffect(int defenseBonus, int duration){
        this.defenseBonus = defenseBonus;
        this.defenseTurnRemaining = duration;
    }

    public void applyEffect(MainEntity player){
        player.addDefense(defenseBonus);
    }

    public void onTurnEnd(MainEntity player){
        defenseTurnRemaining--;
        if (isExpired()) player.removeDefense(defenseBonus);
    }

    public boolean isExpired(){return defenseTurnRemaining <= 0;}

    public int getDefenseBonus(){
        return defenseBonus;
    }
}
