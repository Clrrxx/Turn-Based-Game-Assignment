package Items;

public class Inventory {
    private Item[] items;
    private int count;

    public Inventory(int size){
        this.items = new Item[size];
        this.count = 0;
    }

    
    public void printInventory(){
        if (items.length == 0){
            System.out.println("Inventory is Empty");
        }else{
            System.out.println("======Inventory======");
            for (int i = 0; i<items.length; i++){
                if (items[i] != null){System.out.println(i+1 +". "+ items[i].getName());}
            }
        }
    }

    public Item getiItem(int index){return items[index];}
    public int getCount(){return count;}


    public void addToInventory(Item item){
        if (count<items.length){
            items[count] = item;
            count++;
        }else{
            System.out.println("Inventory is full");
        }
    }

    public void removeFromInventory(int target){
        //if target to be removed is first element => shift left
        //if target is last element => just reduce array size
        if (target == 0){
            for (int i = 0; i < count - 1; i++){
            items[i] = items[i+1];
            }
            items[count-1] = null;
            count --;
        }else{
            items[count - 1] = null;
            count --;
        }
        
        
        
        
    }
}
