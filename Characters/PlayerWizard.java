package Characters;

import java.util.ArrayList;

import StatusEffects.AttackBuffEffect;
import StatusEffects.DefenseBuffEffect;
import StatusEffects.TickingStatusEffect;
import Strategies.ActionStrat;

public class PlayerWizard extends MainPlayer{
    private static final int BASE_HEALTH = 500;
    private static final int BASE_ATTACK = 80;
    private static final int BASE_DEFENSE = 35;
    private static final int BASE_SPEED = 20;

    private ArrayList<TickingStatusEffect> effectList = new ArrayList<TickingStatusEffect>();;

    private final ActionStrat actionStrat;

    public PlayerWizard(String name, ActionStrat actionStrat){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WIZ;
        this.actionStrat = actionStrat;
    }

    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    public void addAttack(int attack){this.attack = this.attack + attack;}
    public void removeAttack(int attack){this.attack = this.attack - attack;}
    public void addDefense(int defense){this.defense = this.defense + defense;}
    public void removeDefense(int defense){this.defense = this.defense - defense;}
    public void addStatusEffect(TickingStatusEffect effect){effectList.add(effect);}
    public ArrayList<TickingStatusEffect> getStatusEffects(){return effectList;}
    

    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage);
        return damage;
    }

    public int effectiveDefense(){
        return effectList.stream()
        .filter(e->e instanceof DefenseBuffEffect)
        .map(e->(DefenseBuffEffect) e)
        .mapToInt(DefenseBuffEffect::getDefenseBonus)
        .sum() + this.defense;
    }

    public int effectiveAttack(){
        return effectList.stream()
        .filter(e->e instanceof AttackBuffEffect)
        .map(e -> (AttackBuffEffect) e)
        .mapToInt(AttackBuffEffect::getAttackBonus)
        .sum() + this.attack;
    }

    public void addHealth(int health){
        if (this.health == BASE_HEALTH){
            System.out.println("Full health!");
        }else{
            this.health = Math.min(this.health + health, BASE_HEALTH);
            
        }
    }

    public void showStats(){
        System.out.println("Wizard: ");
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }

    public void gameReset(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    public int getActionValue(){return 1000/this.speed;}
    public boolean isDead(){return this.health <= 0;}
    public void printName(){System.out.println(name);}
    public int getbaseHP(){return BASE_HEALTH;}
    public boolean isAoE(){return false;}
}