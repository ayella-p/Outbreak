package game.characters;

import game.base.Character;
import game.base.Skill;
public class Zor extends Character {
    public Zor() {
        super("Zor", 100, 70, "Energy", "A former deep-cover" +
                " operative, Zor's training focused on infiltration and espionage. He was one of the" +
                " few who, due to a rare genetic anomaly, was naturally immune to the vaccine's effects." +
                " He uses his enhanced skills to fight for a government that abandoned its people," +
                " his only motivation a desire to see justice served.");
        skills.add(new Skill("Sword Slash", 20, 10));
        skills.add(new Skill("High-Jump", 5, 3));
        skills.add(new Skill("Stealth", 10, 3));
    }
}
