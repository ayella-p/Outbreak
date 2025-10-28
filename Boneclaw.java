public class Boneclaw extends Enemy {
    public Boneclaw() {
        super("Boneclaw", 75, 14, "Fast and lethal ambusher.");
    }

    @Override
    public void attack(Character target) { 
        target.takeDamage(damage); 
    }
}