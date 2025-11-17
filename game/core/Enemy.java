package game.core;
public abstract class Enemy {
    public String name = "Carrier";
    public int maxHP = 100;
    public int currentHP = 50;
    public String description = "A creature affected by the virus";
    public int damage = 5;
    
    public Enemy(String name, int hp, int damage, String description) {
        if(name == null) {
            throw new NullPointerException("Name is null");
        }
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.damage = damage;
        this.description = description;
    }
    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }
    public boolean isAlive() {
        return currentHP > 0;
    }
    public abstract void attack(Character target); 
}