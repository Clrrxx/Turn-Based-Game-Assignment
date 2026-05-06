package Items;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {
    private final Map<String, Item> items = new LinkedHashMap<>();
    private final Map<String, Integer> quantities = new HashMap<>();

    public void addItem(Item item, int count){
        items.put(item.getName(), item);
        quantities.merge(item.getName(), count, Integer::sum);
    }

    public boolean hasItem(String name){
        return quantities.getOrDefault(name, 0) > 0;
    }

    public Optional<Item> getItem(String name){
        return Optional.ofNullable(items.get(name));
    }

    public void consumeItem(String item){
        quantities.compute(item, (k, v) -> v > 1 ? v - 1 : null);
    }

    public Map<String, Integer> getQuantities(){
        return Collections.unmodifiableMap(quantities);
    }

    public void printInv(){
        System.out.println("=======Inventory=======");
        items.forEach((name, item) -> 
        System.out.println(name + " x" + quantities.getOrDefault(name, 0)));
        System.out.println("=======================");
    }
}
