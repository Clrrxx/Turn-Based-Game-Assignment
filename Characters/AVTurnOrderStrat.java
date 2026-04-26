package Characters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AVTurnOrderStrat implements TurnOrder{
    private Map<MainEntity, Integer> actionvals;

    public void initialize(MainEntity player, List<MainEntity> targets){
        actionvals = new HashMap<>();
        actionvals.put(player, player.getActionValue());
        for (MainEntity e: targets){
            actionvals.put(e, e.getActionValue());
        }
    }

    public MainEntity getNextPlayer(MainEntity player, List<MainEntity> targets){
        actionvals.replaceAll((combatant, av) -> av-1);

        return actionvals.entrySet().stream().filter(e ->e.getValue() <= 0).map(Map.Entry::getKey).findFirst().orElse(null);
    }
    
    public void reset(MainEntity player){
        actionvals.put(player, player.getActionValue());
    }

}
