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
        Random rand = new Random();
        lastSkillUsed = rand.nextInt(3); 
        
        if (lastSkillUsed == 0) {
            int damage = rand.nextInt(skillDamageMax - skillDamageMin + 1) + skillDamageMin;
            target.takeDamage(damage);
        }
    }

    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}