public class Zor extends Character {
    public Zor() {
        super("Zor", 100, 70, "Energy", "A former deep-cover operative, Zor's training focused on infiltration and espionage. He was one of the few who, due to a rare genetic anomaly, was naturally immune to the vaccine's effects. He uses his enhanced skills to fight for a government that abandoned its people, his only motivation a desire to see justice served.");
        skills.add(new Skill("Sword Slash", 10, 0));
        skills.add(new Skill("High-Jump", 0, 5));
        skills.add(new Skill("Stealth", 0, 3));
    }
}