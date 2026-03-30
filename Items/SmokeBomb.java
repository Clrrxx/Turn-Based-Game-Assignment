package Items;
import Characters.TickCooldown;
import Game.MainGameSession;

public class SmokeBomb extends Item implements TickCooldown{
    private int ItemDuration = 0; //how many turns the smoke bomb will last for, can be changed for balancing
    
    
    public SmokeBomb(int quantity){
        super("SmokeBomb", quantity);
    }

    private void ItemTick(){if (ItemDuration > 0) ItemDuration--;}

    public boolean getUsedSmokebomb(){return ItemDuration>0;}
    public void useSmokebomb(){ItemDuration = 2;}

    public void tickAll(){ItemTick();}

    public String getName(){return this.name;}
    public void ApplyEffect(MainGameSession session){
        if (isAvailable()){
            useSmokebomb();
            System.out.println("\nA SmokeBomb was used.\n");
        }
    }

}
