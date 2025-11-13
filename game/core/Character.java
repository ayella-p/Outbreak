package game.core;
import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    public String name;
    public int maxHP;
    public int currentHP;
    public int maxResource;
    public int currentResource;
    public String resourceName;
    public String backstory;
    public List<Skill> skills = new ArrayList<>();

    public Character(String name, int hp, int resource, String resourceName, String backstory) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxResource = resource;
        this.currentResource = resource;
        this.resourceName = resourceName;
        this.backstory = backstory;
    }
    
    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }
}