package Characters;

public class EnemyWolf extends MainEnemy{
    private static final int BASE_HEALTH = 300;
    private static final int BASE_ATTACK = 15;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_SPEED = 35;
    private static final String NAME = "Wolf";
    private int stunTurn = 0;
    private int smokeTurn = 0;

    private final ActionStrat actionStrat;

    public EnemyWolf(ActionStrat actionStrat){
        super(BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.ENE_WOLF;
        this.actionStrat = actionStrat;
    }
    
    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    public int setStun(int duration){stunTurn = duration; return stunTurn;}
    public boolean stunStatus(){return stunTurn>0;}
    private void stunTick(){if (stunTurn>0) stunTurn--;}

    public int setSmoke(int duration){
        smokeTurn = duration;
        if (duration>0){
            System.out.println("SmokeBomb was used, attack did not hit.");
        }
        return smokeTurn;
    }

    public int effectiveDefense(){return this.defense;}
    public int effectiveAttack(){return this.attack;}
    public boolean smokeStatus(){return smokeTurn>0;}
    private void smokeTick(){if (smokeTurn>0) smokeTurn--;}
    public int getSkillCooldown(){return 0;}

    public void tickAll(){stunTick();smokeTick();}
    
    public int getActionValue(){return 1000/this.speed;}
    
    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage); 
        return damage;
    }

    public void gameReset(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    public String getName(){return NAME;}
    public void printName(){System.out.print(NAME);}

    public void showStats(){
        System.out.println(NAME);
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }
}