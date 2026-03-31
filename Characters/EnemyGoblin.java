package Characters;

public class EnemyGoblin extends MainEnemy{
    private static final int BASE_HEALTH = 100;
    private static final int BASE_ATTACK = 35;
    private static final int BASE_DEFENSE = 15;
    private static final int BASE_SPEED = 25;
    private static final String NAME = "Goblin";
    private int stunTurn = 0;
    private int smokeTurn = 0;

    public EnemyGoblin(){
        super(BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.ENE_GOB;
    }

    public int setStun(int duration){
        stunTurn = duration; 
        if (duration > 0){
            System.out.println(NAME + " has been stunned for "+duration + " turns.\n");
        }
        return stunTurn;
    }
    
    public int setSmoke(int duration){
        smokeTurn = duration;
        if (duration>0){
            System.out.println("SmokeBomb was used, attack did not hit.");
        }
        return smokeTurn;
    }
    public boolean stunStatus(){return stunTurn>0;}
    private void stunTick(){if (stunTurn>0) stunTurn--;}

    public boolean smokeStatus(){return smokeTurn>0;}
    private void smokeTick(){if (smokeTurn>0) smokeTurn--;}

    public int basicAttack(MainEntity defender){
        if (stunStatus()){
            System.out.println(NAME + " is stunned, unable to take action.");
            return 0;
        }
        else if (smokeStatus()){
            System.out.println("SmokeBomb was used, unable to take action.");
            return 0;
        }
        return Math.max(0, effectiveAttack() - defender.effectiveDefense());
    }

    public void tickAll(){stunTick();smokeTick();}

    public int getActionValue(){return 1000/this.speed;}

    public int effectiveDefense(){return this.defense;}
    public int effectiveAttack(){return this.attack;}
    
    public int takeDamage(int damage){
        if (this.health <= 0){ 
            System.out.println(NAME+" is already dead. Your attack misses.");
            return 0;
        }
        //damage taken is strictly basic attack damage only
        this.health = Math.max(0, this.health - damage);
        if (this.health == 0){
            System.out.println(NAME+" has been slain");
        }
        return damage;
    }

    //resetting for level (in case)
    public void gameReset(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    public String getName(){return NAME;}

    public void printName(){System.out.print(NAME);}
    
    @Override
    public void showStats(){
        System.out.println(NAME);
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }
}