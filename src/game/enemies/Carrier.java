package game.enemies;
import game.base.Enemy;
import game.base.Character;
public class Carrier extends Enemy {
    public Carrier() {
        super("Carrier", 50, 10, "Slow but spreads the virus.");
    }

    @Override
    public void attack(Character target) { 
        target.takeDamage(damage); 
    }
}