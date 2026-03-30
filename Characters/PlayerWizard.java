package Characters;


public class PlayerWizard extends MainPlayer{
    private static final int BASE_HEALTH = 400;
    private static final int BASE_ATTACK = 80;
    private static final int BASE_DEFENSE = 10;
    private static final int BASE_SPEED = 20;

    private int defendTurnRemaining = 0;
    private int skillcooldown = 0;
    private int killcount = 0;
    private int attackBuff = 0;

    public PlayerWizard(String name){
        super(name, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
        this.entitytype = TypeofEntity.PLAY_WIZ;
    }

    public int basicAttack(MainEntity defender){
        int damage = Math.max(0, effectiveAttack() - defender.effectiveDefense());
        return damage;
    }

    private void defendTick(){if (defendTurnRemaining>0) defendTurnRemaining--;}
    private void activateDefend(){defendTurnRemaining = 2;}
    public void defendSkill(){
        //hard code turn count so it becomes easier
        activateDefend();
    }

    public void healHealth(int heal){this.health = heal;}

    public int specialSkill(MainEnemy[] enemies, int targetIndex, boolean usedPowerstone){
        int totaldamage = 0;
        if (this.skillcooldown > 0 && !usedPowerstone){
            System.out.println("Skill on cooldown, unable to act try again in "+ getskillcooldown() + " turns");
            return 0;
        }
        activateSkill();
        for (MainEnemy enemy : enemies){
            int damage = basicAttack(enemy);
            totaldamage += enemy.takeDamage(damage);
            if (enemy.getHealth() == 0){
                registerKill();
            }
        }
        skillbuff();
        resetKillCount();
        return totaldamage;
    }

    //all wizard attack buffs are only active for one round
    public int skillbuff(){return attackBuff + 10 * killcount;}
    public int effectiveAttack(){return this.attack + attackBuff;}
    private void resetAttackBuff(){attackBuff = 0;}
    public int getbaseHP(){return BASE_HEALTH;}
    
    //call reset after each use of skill, want to check eveyrtime whether wizard kills or not
    private void resetKillCount(){killcount = 0;}
    private void registerKill(){killcount++;}

    public int getskillcooldown(){return skillcooldown;}
    private void activateSkill(){skillcooldown = 3;}
    private void tickCooldown(){if (skillcooldown > 0) skillcooldown--;}

    public int effectiveDefense(){return defendTurnRemaining>0 ? this.defense + 10 : this.defense;}

    public int takeDamage(int damage){
        if (this.health <= 0){ 
            System.out.println(name+" is already dead.");
            return 0;
        }
        //damage taken is strictly basic attack damage only
        //everyone has effective defense added to their basic attack (defense is already accounted for)
        this.health = Math.max(0, this.health - damage);
        if (this.health == 0){
            System.out.println("You have been slain");
        }
        return damage;
    }

    @Override
    public void tickAll(){defendTick(); tickCooldown();}

    public void onLevelEnd(){resetAttackBuff();}

    @Override
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