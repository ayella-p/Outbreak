package game.enemies;
import game.base.Enemy;
import game.base.Character;
public class Howler extends Enemy {
    public Howler() {
        super("Howler", 100, 10 , "Disorients enemies and summons more Carriers.");
    }

    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}