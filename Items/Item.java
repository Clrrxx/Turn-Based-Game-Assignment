package Items;

public abstract class Item implements ItemEffect{
    protected String name;
    protected boolean isActive;
    protected int quantity = 2;

    public Item(String name){
        this.name = name;
    }

    public int getQuantity(){return quantity;}
    private void useItem(){if (quantity>0) quantity--;}
    public boolean isAvailable(){return quantity>0;}
    public void activate(){isActive = true; useItem();}
    public void deactivate(){isActive = false;}
    public boolean status(){return isActive;}
    
    public abstract String getName();
}
