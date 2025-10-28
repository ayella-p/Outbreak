import java.util.Random;

public class DrAlcaraz extends Boss {
    public DrAlcaraz() {
        super("Dr. Severino Alcaraz", 300, 25, "Boss monster.", 200, 15, 25);
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