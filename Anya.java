public class Anya extends Character {
    public Anya() {
        super("Anya", 100, 90, "Resolve", "An ex-special forces sniper, Anya is a master of stealth and long-range combat. She was on a classified mission when the vaccine was distributed, and her unique circumstances shielded her from its effects. Driven by the loss of her entire unit, she now fights to prevent anyone else from experiencing the same horror.");
        skills.add(new Skill("Headshot", 20, 15));
        skills.add(new Skill("Suppressive Fire", 10, 5));
        skills.add(new Skill("Camouflage", 0, 2));
    }
}