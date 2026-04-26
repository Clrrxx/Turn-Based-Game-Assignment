package Characters;


public class PlayerWizard extends MainPlayer{
    private static final int BASE_HEALTH = 500;
    private static final int BASE_ATTACK = 80;
    private static final int BASE_DEFENSE = 20;
    private static final int BASE_SPEED = 20;

    private int defendTurnRemaining = 0;
    private int skillcooldown = 0;
    private int killcount = 0;
    private int attackBuff = 0;

    private final ActionStrat actionStrat;

    public PlayerWizard(String name, ActionStrat actionStrat){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WIZ;
        this.actionStrat = actionStrat;
    }

    public int takeTurn(MainEntity target){
        return actionStrat.execute(this, target);
    }

    private void defendTick(){if (defendTurnRemaining>0) defendTurnRemaining--;}
    public void activateDefend(int turns){defendTurnRemaining = turns;}
    

    public void healHealth(int heal){this.health = heal;}


    //all wizard attack buffs are only active for one round
    public void skillbuff(){attackBuff += 10 * killcount;}
    public int effectiveAttack(){return this.attack + attackBuff;}
    private void resetAttackBuff(){attackBuff = 0;}
    public int getbaseHP(){return BASE_HEALTH;}
    
    //call reset after each use of skill, want to check eveyrtime whether wizard kills or not
    private void resetKillCount(){killcount = 0;}
    private void registerKill(){killcount++;}

    public int getSkillCooldown(){return skillcooldown;}
    private void activateSkill(){skillcooldown = 3;}
    private void tickCooldown(){if (skillcooldown > 0) skillcooldown--;}

    public int effectiveDefense(){return defendTurnRemaining>0 ? this.defense + 10 : this.defense;}

    @Override
    public void tickAll(){defendTick(); tickCooldown();}

    public void onLevelEnd(){resetAttackBuff();}

    public int takeDamage(int damage){
        this.health = Math.max(0, this.health - damage);
        return damage;
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


}