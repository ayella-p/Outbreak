package game.characters;

import game.core.Character;
import game.core.Skill;
public class Elara extends Character {
    public Elara() {
        super("Elara", 95, 85, "Battery", "A brilliant but " +
                "reclusive software engineer, Elara was immune to the vaccine due to a rare blood type. " +
                "She was forced to watch as her entire family, who were not immune, turned into Reavers.");
        skills.add(new Skill("Piercing Arrow", 17, 8));
        skills.add(new Skill("Precision Aim", 10, 5));
        skills.add(new Skill("Scout", 5, 3));
    }
}
