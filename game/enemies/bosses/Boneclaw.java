package game.enemies.bosses;
import game.base.Character;
import game.base.Boss;
import java.util.Random;

public class Boneclaw extends Boss {
    public Boneclaw() {
        super("General Boneclaw", 175, 20, "Boss monster.", 115, 10, 20);
    }

    @Override
    public void useSkill(Character target) {
        int drainAmount = 10;
        target.takeDamage(drainAmount);
        this.currentHP += drainAmount;
    }

    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}