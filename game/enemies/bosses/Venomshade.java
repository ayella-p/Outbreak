package game.enemies.bosses;
import game.base.Character;
import game.base.Boss;
import java.util.Random;

public class Venomshade extends Boss {
    public Venomshade() {
        super("General Venomshade", 215, 20, "Boss monster.", 115, 10, 20);
    }

    @Override
    public void useSkill(Character target) {
        int healAmount = 5;
        this.currentHP += healAmount;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }

    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}