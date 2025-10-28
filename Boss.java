import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Boss extends Enemy { 
    int maxMana;
    int currentMana;
    int skillDamageMin;
    int skillDamageMax;
    List<Skill> skills = new ArrayList<>(); 
    public int lastSkillUsed = -1; 

    public Boss(String name, int hp, int damage, String description, int mana, int skillDmgMin, int skillDmgMax) {
        super(name, hp, damage, description);
        this.maxMana = mana;
        this.currentMana = mana;
        this.skillDamageMin = skillDmgMin;
        this.skillDamageMax = skillDmgMax;
    }

    public abstract void useSkill(Character target);
}