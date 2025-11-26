package game.enemies.bosses;
import game.base.Character;
import game.base.Boss;

public class IronMaw extends Boss {
    public IronMaw() {
        super("General Iron Maw", 200, 15, "Boss monster.", 100, 10, 20);
    }

    @Override
    public void useSkill(Character target) {
        int healAmount = 20;
        this.currentHP += healAmount;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }
    
    @Override
    public void attack(Character target) {
        target.takeDamage(damage);
    }
}