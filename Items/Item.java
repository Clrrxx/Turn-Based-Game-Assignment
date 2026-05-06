package Items;

import java.util.List;
import Characters.MainEntity;

public abstract class Item{
    protected final String name;
    protected boolean isActive;

    public Item(String name){
        this.name = name;
    }

    public abstract void useItem(MainEntity player, List<MainEntity> enemies);
    public abstract String getName();
}
