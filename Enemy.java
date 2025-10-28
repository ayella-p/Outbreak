
public abstract class Enemy {
    String name;
    int maxHP;
    int currentHP;
    int damage;
    String description;
    
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