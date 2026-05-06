package Characters;

import java.util.ArrayList;

import StatusEffects.TickingStatusEffect;
import Strategies.ActionStrat;

public class EnemyWolf extends MainEnemy{
    private static final int BASE_HEALTH = 300;
    private static final int BASE_ATTACK = 45;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_SPEED = 35;
    private static final String NAME = "Wolf";

    private final ActionStrat actionStrat;
    private ArrayList<TickingStatusEffect> effectList = new ArrayList<TickingStatusEffect>();

    public EnemyWolf(ActionStrat actionStrat){
        super(BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.ENE_WOLF;
        this.actionStrat = actionStrat;
    }
    
    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage); 
        return damage;
    }


    public void addStatusEffect(TickingStatusEffect effect){effectList.add(effect);}
    public ArrayList<TickingStatusEffect> getStatusEffects(){return effectList;}

    
    public int getActionValue(){return 1000/this.speed;}
    public boolean isDead(){return this.health <= 0;}
    
    public void gameReset(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    public String getName(){return NAME;}
    public void printName(){System.out.print(NAME);}
    public int effectiveDefense(){return this.defense;}
    public int effectiveAttack(){return this.attack;}
    public void addDefense(int defense){}
    public void removeDefense(int defense){}
    public void addHealth(int health){}
    public void addAttack(int attack){}
    public void removeAttack(int attack){}
    public boolean isAoE(){return false;}
    public int getbaseHP(){return BASE_HEALTH;}

    public void showStats(){
        System.out.println(NAME);
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }
}