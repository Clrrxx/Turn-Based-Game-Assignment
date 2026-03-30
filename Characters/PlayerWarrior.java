package Characters;


public class PlayerWarrior extends MainPlayer{
    //use of static vars because we want the changes to reflect as the game continues
    private static final int BASE_HEALTH = 520;
    private static final int BASE_ATTACK = 60;
    private static final int BASE_DEFENSE = 20;
    private static final int BASE_SPEED = 30;
    private int defendTurnRemaining = 0;
    private int skillcooldown = 0;
    private int stunWindow = 2;


    public PlayerWarrior(String name){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WAR;
    }

    public int basicAttack(MainEntity defender){return Math.max(0, effectiveAttack() - defender.effectiveDefense());}
    
    private void defendTick(){if (defendTurnRemaining>0) defendTurnRemaining--;}
    private void activateDefend(){defendTurnRemaining = 2;}
    
    public int getStunWindow(){return stunWindow;}
    public void defendSkill(){activateDefend();}

    //if defendturnremaining > 0 return this.def + 10
    public int effectiveDefense(){return defendTurnRemaining>0 ? this.defense + 10 : this.defense;}
    public int effectiveAttack(){return this.attack;}
    public int getbaseHP(){return BASE_HEALTH;}
    public void healHealth(int heal){this.health = heal;}

    //to allow for abstraction, pass a list of enemies for special skill but warrior will attack the first enemy of the list
    public int specialSkill(MainEnemy[] enemies, int targetIndex, boolean usedPowerstone){
        if (skillcooldown > 0 && !usedPowerstone){
            System.out.println("Skill on cooldown, unable to act try again in "+ getskillcooldown() + " turns");
            return 0;
        }
        activateSkill();
        enemies[targetIndex].setStun(stunWindow);
        int damage = basicAttack(enemies[targetIndex]);
        enemies[targetIndex].takeDamage(damage);
        return damage;
    }

    public int getskillcooldown(){return skillcooldown;}
    private void tickCooldown(){if (skillcooldown > 0) skillcooldown--;}
    private void activateSkill(){skillcooldown = 3;}

    public int takeDamage(int damage){
        if (this.health <= 0){ 
            System.out.println(name+" is already dead. Your attack misses.");
            return 0;
        }
        //damage taken is strictly basic attack damage only
        this.health = Math.max(0, this.health - damage);
        if (this.health == 0){
            System.out.println("You have been slain");
        }
        return damage;
    }

    @Override
    public void tickAll(){defendTick(); tickCooldown();}

    @Override
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
    

  
}