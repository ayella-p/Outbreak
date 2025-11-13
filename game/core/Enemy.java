package game.core;
public abstract class Enemy {
    public String name;
    public int maxHP;
    public int currentHP;
    public String description;
    public int damage;
    
    public Enemy(String name, int hp, int damage, String description) {
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