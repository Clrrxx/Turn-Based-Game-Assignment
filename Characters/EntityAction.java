package Characters;

public interface EntityAction {
    int basicAttack(MainEntity defender);
    int takeDamage(int damage);
    int effectiveAttack();
    int effectiveDefense();
    void showStats();
    void gameReset();
}