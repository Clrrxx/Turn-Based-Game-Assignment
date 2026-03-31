package Items;
import java.util.ArrayList;
import java.util.List;


public class Inventory {
    private List <Item> items;
    private int size;
    protected boolean invenStatus = true;

    public Inventory(int size){
        this.items = new ArrayList<>();
        this.size = size;
    }

    public boolean getInvenStatus(){return invenStatus;}
    
    public void printInventory(){
        if (items.isEmpty()){
            System.out.println("Inventory is Empty\n");
            invenStatus = false;
        }else{
            System.out.println("======Inventory======");
            for (int i = 0; i<items.size(); i++){
                if (items != null){System.out.println(i+1 +". "+ items.get(i).getName());}
            }
        }
    }

    public Item getiItem(int index){return items.get(index);}
    public int getSize(){return items.size();}


    public void addToInventory(Item item){
        if (items.size()<size){
            items.add(item);
        }else{
            System.out.println("Inventory is full");
        }
    }

    public void removeFromInventory(int target){
        if (target > -1 && target < items.size()){
            items.remove(target);
        }else{
            System.out.println("Invalid Index, Try again");
        }
    }
}
