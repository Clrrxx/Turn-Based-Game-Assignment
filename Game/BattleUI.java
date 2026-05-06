package Game;
import Characters.MainPlayer;

public class BattleUI {
    public void displayPlayerHealth(MainPlayer player){
        int barLength = 20;
        int filled = (int)((double) player.getHealth() / player.getbaseHP() * barLength);
        String bar = "[" + "=".repeat(filled) + "-".repeat(barLength - filled) + "]";
        System.out.println("HP: " + bar + " " + player.getHealth() + "/" + player.getbaseHP());
    }

    public void displayPlayerBattleStats(MainPlayer player){
        System.out.println(player.getName());
        int barLength = 20;
        int filled = (int)((double) player.getHealth() / player.getbaseHP() * barLength);
        String bar = "[" + "=".repeat(filled) + "-".repeat(barLength - filled) + "]";

        System.out.println("HP: " + bar + " " + player.getHealth() + "/" + player.getbaseHP());
        
        System.out.println("ATK: " + player.effectiveAttack());

        System.out.println("DEF: " + player.effectiveDefense());

        System.out.println("SPD: " + player.getSpeed());
    }
}
