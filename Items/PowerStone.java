package Items;

public class PowerStone extends Item{
    public PowerStone(String name){
        super(name);
    }
    public void printName(){System.out.println(name);}
    public String getName(){return this.name;}
    //since power stone gives a free use of skill, just call in main function dont need to think so much about this
}
