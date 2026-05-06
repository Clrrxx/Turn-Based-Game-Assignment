package Items;

import java.util.List;

import Characters.MainEntity;
import StatusEffects.SmokeBombEffect;

public class SmokeBomb extends Item{
    public SmokeBomb(){
        super("SmokeBomb");
    }

    @Override
    public void useItem(MainEntity player, List<MainEntity> enemies){
        SmokeBombEffect smoke = new SmokeBombEffect(3);
        enemies.forEach(smoke::applyEffect);

        System.out.println("Smoke Used, the battlefield is covered.");
    }
    
    public String getName(){return this.name;}
}
