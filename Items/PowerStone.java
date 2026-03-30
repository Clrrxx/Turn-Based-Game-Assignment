package Items;

import Characters.MainPlayer;
import Game.MainGameSession;
import Characters.MainEnemy;
import java.util.Scanner;

public class PowerStone extends Item{
    Scanner newScan = new Scanner(System.in);

    public PowerStone(int quantity){
        super("PowerStone", quantity);
    }

    public String getName(){return this.name;}
    
    //since power stone gives a free use of skill, dont need to think so hard

    public void ApplyEffect(MainGameSession session){
        MainPlayer player = session.getPlayer();
        MainEnemy[] enemies = session.getEnemies();
        if (isAvailable()){
            activate();
            System.out.println("You used a Power Stone, Free Skill Usage");
            System.out.println("Choose who to attack: [1, 2, 3]");
            int target = newScan.nextInt();

            int special = player.specialSkill(enemies, target-1, true);
            System.out.println("You did a total of "+special+" damage because of the power stone.");
            deactivate();
        }else{
            System.out.println("You ran out of items to use!");
        }
    }
}
