package Characters;

public abstract class MainEntity implements TickCooldown, EntityAction, ActionValue{
    public enum TypeofEntity{PLAY_ENTI, PLAY_WAR, PLAY_WIZ, ENE_WOLF, ENE_GOB};
    protected int health, defense, attack, speed;
    protected String name;
    protected TypeofEntity entitytype;

    public MainEntity(String name, int health, int attack, int defense, int speed){
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.speed = speed;
        this.name = name;
        this.entitytype =  TypeofEntity.PLAY_ENTI;
    }

    public MainEntity(int health, int attack, int defense, int speed){
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.speed = speed;
        this.entitytype = TypeofEntity.PLAY_ENTI;
    }
    
    public int getHealth(){return health;}
    public int getAttack(){return attack;}
    public int getDefense(){return defense;}
    public int getSpeed(){return speed;}

    public int takeDamage(int damage){
        if (this.health <= 0){ 
            System.out.println(name+" is already dead.");
            return 0;
        }
        //damage taken is strictly basic attack damage only
        this.health = Math.max(0, this.health - damage);
        if (this.health == 0){
            System.out.println(name+" has been slain");
        }
        return damage;
    }



}