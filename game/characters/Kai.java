package game.characters;

import game.core.Character;
import game.core.Skill;
public class Kai extends Character {
    public Kai() {
        super("Kai", 100, 70, "Focus", "A former bio-hacker, " +
                "Kai developed a unique neural interface that allows him to manipulate the mutated " +
                "creatures' own biology. He has a complicated past, having been involved in the very " +
                "corporation that created the vaccine, and seeks redemption by using his knowledge to " +
                "undo the damage.");
        skills.add(new Skill("Neural Shock", 10, 8));
        skills.add(new Skill("Bio-Scan", 5, 3));
        skills.add(new Skill("Mutagenic Surge", 20, 15));
    }
}
