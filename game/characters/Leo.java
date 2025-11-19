package game.characters;

import game.base.Character;
import game.base.Skill;
public class Leo extends Character {
    public Leo() {
        super("Leo", 150, 80, "Stamina", "Leo was a combat medic" +
                " on the front lines when the pandemic hit. He was among the first to be administered the" +
                " vaccine, but it had no effect on him due to his unique genetics. The trauma of losing his" +
                " entire unit when the Reavers first emerged fuels his relentless drive to protect others.");
        skills.add(new Skill("Overhead Strike", 15, 10));
        skills.add(new Skill("Basic Block", 8, 4));
        skills.add(new Skill("Crowd Control", 10, 3));
    }
}
