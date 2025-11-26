package game.base;
public abstract class Enemy extends Character{
    public String description = "A creature affected by the virus";
    public int damage = 5;
    
    public Enemy(String name, int hp, int damage, String description) {
        if(name == null) {
            throw new NullPointerException("Name is null");
        }
        super(name, hp, 100, "Stamina", description);
        this.damage = damage;
        this.description = description;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }
    public abstract void attack(Character target); 
}