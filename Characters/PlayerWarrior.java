package Characters;

import java.util.ArrayList;

import StatusEffects.DefenseBuffEffect;
import StatusEffects.TickingStatusEffect;
import Strategies.ActionStrat;

public class PlayerWarrior extends MainPlayer{
    //use of static vars because we want the changes to reflect as the game continues
    private static final int BASE_HEALTH = 620;
    private static final int BASE_ATTACK = 60;
    private static final int BASE_DEFENSE = 30;
    private static final int BASE_SPEED = 30;
    
    private final ActionStrat actionStrat;

    private ArrayList<TickingStatusEffect> effectList = new ArrayList<TickingStatusEffect>();

    public PlayerWarrior(String name, ActionStrat actionStrat){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WAR;
        this.actionStrat = actionStrat;
    }

    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage); 
        return damage;
    }

    public void addDefense(int defense){this.defense = this.defense + defense;}
    public void removeDefense(int defense){this.defense = this.defense - defense;}
    public void addStatusEffect(TickingStatusEffect effect){effectList.add(effect);}
    public ArrayList<TickingStatusEffect> getStatusEffects(){return effectList;}

    public void addHealth(int health){
        if (this.health == BASE_HEALTH){
            System.out.println("Full health!");
        }else{
            this.health = Math.min(this.health + health, BASE_HEALTH);
            
        }
    }

    public int effectiveDefense(){
        return effectList.stream()
        .filter(e->e instanceof DefenseBuffEffect)
        .map(e->(DefenseBuffEffect) e)
        .mapToInt(DefenseBuffEffect::getDefenseBonus)
        .sum() + this.defense;
    }

    public int effectiveAttack(){return this.attack;}
  
    public void showStats(){
        System.out.println("Warrior: ");
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }

    //for resetting of all base stats at the end of the game
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
    public void addAttack(int attack){}
    public void removeAttack(int attack){}
}