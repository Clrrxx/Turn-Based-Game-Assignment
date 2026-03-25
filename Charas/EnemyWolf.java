package Charas;

public class EnemyWolf extends MainEnemy{
    private static final int BASE_HEALTH = 40;
    private static final int BASE_ATTACK = 45;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_SPEED = 35;
    private static final String NAME = "Wolf";
    private int stunTurn = 0;

    public EnemyWolf(){
        super(BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.ENE_WOLF;
    }
    
    public int basicAttack(MainEntity defender){
        if (stunStatus()){
            System.out.println(NAME + " is stunned, unable to take action.");
            return 0;
        }
        return Math.max(0, this.attack - defender.effectiveDefense());
    }

    //warrior skill cooldown will be longer than the stunwindow for mobs
    //pass player.getStunWindow() through setstun
    public int setStun(int duration){stunTurn = duration; return stunTurn;}
    public boolean stunStatus(){return stunTurn>0;}
    private void stunTick(){if (stunTurn>0) stunTurn--;}

    public void onTurnEnd(){stunTick();}

    public int effectiveDefense(){return this.defense;}

    public int takeDamage(int damage){
        if (this.health <= 0){ 
            System.out.println(NAME+" is already dead.");
            return 0;
        }
        //damage taken is strictly basic attack damage only
        this.health = Math.max(0, this.health - damage);
        if (this.health == 0){
            System.out.println(NAME+" has been slain");
        }
        return damage;
    }

    public void resetStats(){
        this.health = BASE_HEALTH;
        this.attack = BASE_ATTACK;
        this.defense = BASE_DEFENSE;
        this.speed = BASE_SPEED;
    }

    @Override
    public void showStats(){
        System.out.println(NAME);
        System.out.println("HP: "+this.health);
        System.out.println("ATK: "+this.attack);
        System.out.println("DEF: "+this.defense);
        System.out.println("SPD: "+this.speed);
    }
}