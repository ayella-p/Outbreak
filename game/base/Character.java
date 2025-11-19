package game.base;
import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    public String name = "Ashlee";
    public int maxHP = 100;
    public int currentHP = 100;
    public int maxResource = 10;
    public int currentResource = 10;
    public String resourceName = "Stamina";
    public String backstory = "A wandering character";
    public List<Skill> skills = new ArrayList<>();
    public Character(String name, int hp, int resource, String resourceName, String backstory) {
        if(name == null || resourceName == null || backstory == null) {
            throw new NullPointerException("Name/Resource Name/Backstory is null");
        }
        if(hp <= 0 || resource <= 0) {
            throw new NullPointerException("HP or Resource must be positive value");
        }
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