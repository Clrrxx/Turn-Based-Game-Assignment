package Strategies;
import java.util.List;

import Characters.MainEntity;

public interface TurnOrder {
    MainEntity getNextPlayer(MainEntity player, List<MainEntity> targets);
    void reset(MainEntity player);
    void initialize(MainEntity player, List<MainEntity> targets);
}
