package game.enemies;
import game.core.Enemy;
import game.core.Character;
public class Carrier extends Enemy {
    public Carrier() {
        super("Carrier", 50, 10, "Slow but spreads the virus.");
    }

    @Override
    public void attack(Character target) { 
        target.takeDamage(damage); 
    }
}