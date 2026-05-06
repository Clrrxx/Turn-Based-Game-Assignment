package Characters;

import java.util.ArrayList;


import StatusEffects.TickingStatusEffect;
import Strategies.ActionValue;

public abstract class MainEntity implements ActionValue{
    public enum TypeofEntity{PLAY_ENTI, PLAY_WAR, PLAY_WIZ, ENE_WOLF, ENE_GOB};
    protected int health, defense, attack, speed;
    protected String name;
    protected TypeofEntity entitytype;
    private boolean isSmoked = false;
    private boolean isStunned = false;
    private boolean isCoolDown = false;  

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

    public void setStun(boolean stun){this.isStunned = stun;}
    public boolean isStunned(){return isStunned;}
    public void setSmoke(boolean smoke){this.isSmoked = smoke;}
    public boolean isSmoked(){return isSmoked;}
    public void setCoolDown(boolean cooldown){this.isCoolDown = cooldown;}
    public boolean isCoolDown(){return isCoolDown;}

    
    public int getHealth(){return health;}
    public int getAttack(){return attack;}
    public int getDefense(){return defense;}
    public int getSpeed(){return speed;}
    public abstract int getbaseHP();
    
    public abstract int effectiveAttack();
    public abstract int effectiveDefense();
    public abstract void showStats();
    public abstract int takeDamage(int damage);
    public abstract void printName();
    public abstract String getName();

    public abstract void addDefense(int defense);
    public abstract void removeDefense(int defense);
    public abstract void addAttack(int attack);
    public abstract void removeAttack(int attack);
    public abstract void addHealth(int health);
    public abstract boolean isDead();

    
    public abstract boolean isAoE();


    public abstract void addStatusEffect(TickingStatusEffect effect);
    public abstract ArrayList<TickingStatusEffect> getStatusEffects();

}