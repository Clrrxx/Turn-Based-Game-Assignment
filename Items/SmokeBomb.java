package Items;
import Characters.TickCooldown;

public class SmokeBomb extends Item implements TickCooldown{
    private int ItemDuration = 2; //how many turns the smoke bomb will last for, can be changed for balancing
    private boolean usedSmokebomb = false;
    
    
    public SmokeBomb(String name){
        super(name);
    }

    private void ItemTick(){if (ItemDuration > 0) ItemDuration--;}
    public boolean getUsedSmokebomb(){return usedSmokebomb;}
    public void useSmokebomb(){usedSmokebomb = true;}

    public void tickAll(){ItemTick();}
    public void printName(){System.out.println(name);}
    public String getName(){return this.name;}
}
