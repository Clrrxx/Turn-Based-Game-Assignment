package Characters;

import java.util.ArrayList;

import StatusEffects.TickingStatusEffect;
import Strategies.ActionStrat;

public class EnemyGoblin extends MainEnemy{
    private static final int BASE_HEALTH = 100;
    private static final int BASE_ATTACK = 35;
    private static final int BASE_DEFENSE = 15;
    private static final int BASE_SPEED = 25;
    private static final String NAME = "Goblin";

    private final ActionStrat actionStrat;
    private ArrayList<TickingStatusEffect> effectList = new ArrayList<TickingStatusEffect>();

    public EnemyGoblin(ActionStrat actionStrat){
        super(BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.ENE_GOB;
        this.actionStrat = actionStrat;
    }

    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }


    public void addStatusEffect(TickingStatusEffect effect){effectList.add(effect);}
    public ArrayList<TickingStatusEffect> getStatusEffects(){return effectList;}


    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage); 
        return damage;
    }

    public int getActionValue(){return 1000/this.speed;}
    public boolean isDead(){return this.health <= 0;}

    //resetting for level (in case)
    public void gameReset(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    //filler commands
    public String getName(){return NAME;}
    public void addDefense(int defense){}
    public void removeDefense(int defense){}
    public void addHealth(int health){}
    public void addAttack(int attack){}
    public void removeAttack(int attack){}
    public int effectiveDefense(){return this.defense;}
    public int effectiveAttack(){return this.attack;}
    public void printName(){System.out.print(NAME);}
    public boolean isAoE(){return false;}
    public int getbaseHP(){return BASE_HEALTH;}
    
    @Override
    public void showStats(){
        System.out.println(NAME);
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }
}