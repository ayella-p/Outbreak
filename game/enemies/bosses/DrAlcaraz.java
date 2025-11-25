package game.enemies.bosses;
import game.base.Character;
import game.base.Boss;
import java.util.Random;

public class DrAlcaraz extends Boss {
    public DrAlcaraz() {
        super("Dr. Severino Alcaraz", 250, 18, "Boss monster.", 200, 10, 25);
    }

    @Override
    public void useSkill(Character target) {
        Random rand = new Random();
        damage = rand.nextInt(skillDamageMax - skillDamageMin + 1) + skillDamageMin;

        target.takeDamage(damage);
    }
    
    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }

}
