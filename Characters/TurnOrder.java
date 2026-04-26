package Characters;
import java.util.List;

public interface TurnOrder {
    MainEntity getNextPlayer(MainEntity player, List<MainEntity> targets);
    void reset(MainEntity player);
    void initialize(MainEntity player, List<MainEntity> targets);
}
