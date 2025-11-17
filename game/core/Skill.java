package game.core;
public class Skill {
    public String name = "Unnamed Skill";
    public int damage = 5;
    public int cost = 2;

    public Skill(String name, int damage, int cost) {
        if (name == null) {
            throw new IllegalArgumentException("Skill name cannot be null");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Skill damage cannot be negative");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Skill cost cannot be negative");
        }
        this.name = name;
        this.damage = damage;
        this.cost = cost;
    }
}