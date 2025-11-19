package game.enemies.bosses;
import game.base.Character;
import game.base.Boss;
import java.util.Random;

public class DrAlcaraz extends Boss {
    public DrAlcaraz() {
        super("Dr. Severino Alcaraz", 250, 18, "Boss monster.", 200, 13, 20);
    }

    @Override
    public void useSkill(Character target) {
        Random rand = new Random();
        lastSkillUsed = rand.nextInt(3); 
        
        // No damage or immediate effect for the boss in this logic block
    }
    
    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}
