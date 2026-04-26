package Characters;


public class PlayerWarrior extends MainPlayer{
    //use of static vars because we want the changes to reflect as the game continues
    private static final int BASE_HEALTH = 620;
    private static final int BASE_ATTACK = 60;
    private static final int BASE_DEFENSE = 30;
    private static final int BASE_SPEED = 30;
    private int defendTurnRemaining = 0;
    private int skillcooldown = 0;
    private int stunWindow = 2;

    private final ActionStrat actionStrat;


    public PlayerWarrior(String name, ActionStrat actionStrat){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WAR;
        this.actionStrat = actionStrat;
    }

    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    private void defendTick(){if (defendTurnRemaining>0) defendTurnRemaining--;}
    public void activateDefend(int turns){defendTurnRemaining = turns;}
    
    public int getStunWindow(){return stunWindow;}

    //if defendturnremaining > 0 return this.def + 10
    public int effectiveDefense(){return defendTurnRemaining>0 ? this.defense + 10 : this.defense;}
    public int effectiveAttack(){return this.attack;}
    public int getbaseHP(){return BASE_HEALTH;}
    public void healHealth(int heal){this.health = heal;}


    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage); 
        return damage;
    }


    public int getSkillCooldown(){return skillcooldown;}
    private void tickCooldown(){if (skillcooldown > 0) skillcooldown--;}
    private void activateSkill(){skillcooldown = 3;}
    public void onLevelEnd(){}

    @Override
    public void tickAll(){defendTick(); tickCooldown();}

    
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