package Charas;

import java.util.ArrayList;
import java.util.List;

public class PlayerWizard extends MainPlayer{
    private static final int BASE_HEALTH = 200;
    private static final int BASE_ATTACK = 50;
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

    private void defendTick(){if (this.defendTurnRemaining>0) this.defendTurnRemaining--;}
    private void activateDefend(){this.defendTurnRemaining = 2;}
    public int defendSkill(){
        //hard code turn count so it becomes easier
        activateDefend();
        return effectiveDefense();
    }

    public int specialskill(List<MainEnemy> enemies){
        int totaldamage = 0;
        if (this.skillcooldown > 0){
            System.out.println("Skill on cooldown");
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

    public void onTurnEnd(){defendTick(); tickCooldown();}
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

    private List<Inventory> inventory = new ArrayList<>();
    public void getInventory(){accessInventory(inventory);}
    protected void accessInventory(List <Inventory> inventory){
        for (Inventory item: inventory){
            System.out.println(item);
        }
    }
}