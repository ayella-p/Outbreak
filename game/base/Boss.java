package game.base;
public abstract class Boss extends Enemy {
    public int maxMana = 100;
    public int currentMana = 100;
    public int skillDamageMin = 10;
    public int skillDamageMax = 20;
    public int lastSkillUsed = -1;

    public Boss(String name, int hp, int damage, String description, int mana, int skillDmgMin, int skillDmgMax) {
        super(name, hp, damage, description);
        if (mana <= 0 || skillDmgMin <= 0 || skillDmgMax <= 0 || skillDmgMin > skillDmgMax) {
            throw new IllegalArgumentException(
                    "Invalid Boss parameters: Mana, min skill damage, and max skill damage must be positive. " +
                            "Also, skillDmgMin cannot be greater than skillDmgMax."
            );
        }
        this.maxMana = mana;
        this.currentMana = mana;
        this.skillDamageMin = skillDmgMin;
        this.skillDamageMax = skillDmgMax;
    }

    public abstract void useSkill(Character target);
}