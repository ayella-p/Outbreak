package game.enemies.bosses;
import game.core.Character;
import game.core.Boss;
import java.util.Random;

public class IronMaw extends Boss {
    public IronMaw() {
        super("General Iron Maw", 200, 15, "Boss monster.", 100, 10, 20);
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