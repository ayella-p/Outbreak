package game.core;
public class Skill {
    public String name;
    public int damage;
    public int cost;

    public Skill(String name, int damage, int cost) {
        this.name = name;
        this.damage = damage;
        this.cost = cost;
    }
}