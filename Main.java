import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

abstract class Character {
    String name;
    int maxHP;
    int currentHP;
    int maxResource;
    int currentResource;
    String resourceName;
    String backstory;
    List<Skill> skills = new ArrayList<>();

    public Character(String name, int hp, int resource, String resourceName, String backstory) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxResource = resource;
        this.currentResource = resource;
        this.resourceName = resourceName;
        this.backstory = backstory;
    }
    public abstract void displaySkills();
}
class Zor extends Character {
    public Zor() {
        super("Zor", 100, 70, "Energy", "A former deep-cover operative, Zor's training focused on infiltration and espionage. He was one of the few who, due to a rare genetic anomaly, was naturally immune to the vaccine's effects. He uses his enhanced skills to fight for a government that abandoned its people, his only motivation a desire to see justice served.");
        skills.add(new Skill("Sword Slash", 10, 0));
        skills.add(new Skill("High-Jump", 0, 5));
        skills.add(new Skill("Stealth", 0, 3));
    }
    
    @Override
    public void displaySkills() {
        System.out.println("Zor's Skiils:");
        System.out.println("1. Sword Slash: Deals 100 damage. No energy cost" + resourceName + " cost.");
        System.out.println("2. High-Jump: Utility skill. Costs 5 " + resourceName + ".");
        System.out.println("3. Stealth: Utility skill. Costs 3 " + resourceName + " per turn.");
    }
}

class Leo extends Character {
    public Leo() {
        super("Leo", 150, 80, "Stamina", "Leo was a combat medic on the front lines when the pandemic hit. He was among the first to be administered the vaccine, but it had no effect on him due to his unique genetics. The trauma of losing his entire unit when the Reavers first emerged fuels his relentless drive to protect others.");
        skills.add(new Skill("Overhead Strike", 15, 10));
        skills.add(new Skill("Basic Block", 0, 5));
        skills.add(new Skill("Crowd Control", 10, 3));
    }

    @Override
    public void displaySkills() {
        System.out.println("Leo's Skiils:");
        System.out.println("1. Overhead Strike: Deals 15 damage. Costs 105  " + resourceName + ".");
        System.out.println("2. Basic Block: Reduces incoming damage. Costs 5 " + resourceName + ".");
        System.out.println("3. Crowd Control (Roar/Stomp): Deals 10 damage to all enemies and stuns them for 3s. Costs 3" + resourceName + " per turn.");
    }
}

class Elara extends Character {
    public Elara() {
        super("Elara", 95, 85, "Battery", "A brilliant but reclusive software engineer, Elara was immune to the vaccine due to a rare blood type. She was forced to watch as her entire family, who were not immune, turned into Reavers.");
        skills.add(new Skill("Piercing Arrow", 17, 5));
        skills.add(new Skill("Precision Aim", 0, 5));
        skills.add(new Skill("Scout", 0, 3));
    }

    @Override 
    public void displaySkills() {
        System.out.println("Elara's Skills:");
        System.out.println("1. Piercing Arrow: Deals 17 damage. Costs 5 " + resourceName + ".");
        System.out.println("2. Precision Aim: Utility skill. Costs 5 " + resourceName + " per turn.");
        System.out.println("3. Scout: Utility skill. Costs 3 " + resourceName + " to deploy.");
    }
}

class Kai extends Character {
    public Kai() {
        super("Kai", 100, 70, "Focus", "A former bio-hacker, Kai developed a unique neural interface that allows him to manipulate the mutated creatures' own biology. He has a complicated past, having been involved in the very corporation that created the vaccine, and seeks redemption by using his knowledge to undo the damage.");
        skills.add(new Skill("Neural Shock", 15, 11));
        skills.add(new Skill("Bio-Scan", 0, 5));
        skills.add(new Skill("Mutagenic Surge", 20, 15));
    }

    @Override 
    public void displaySkills() {
        System.out.println("Kai's Skills:");
        System.out.println("1. Neutral Shock: Deals 15 damage. Has a chance to stun. Costs 11 " + resourceName + ".");
        System.out.println("2. Bio-Scan: Utility skill. Reveals enemy weak points. Costs 5 " + resourceName + ".");
        System.out.println("3. Mutagenic Surge: Deals 20 damage and debuffs enemy defenses. Costs 15 " + resourceName + ".");
    }
}

class Anya extends Character {
    public Anya() {
        super("Anya", 100, 90, "Resolve", "An ex-special forces sniper, Anya is a master of stealth and long-range combat. She was on a classified mission when the vaccine was distributed, and her unique circumstances shielded her from its effects. Driven by the loss of her entire unit, she now fights to prevent anyone else from experiencing the same horror.");
        skills.add(new Skill("Headshot", 20, 15));
        skills.add(new Skill("Suppressive Fire", 10, 5));
        skills.add(new Skill("Camouflage", 0, 2));
    }

    @Override
    public void displaySkills() {
        System.out.println("Anya's Skills:");
        System.out.println("1. Headshot: Deals 20 damage. High single-target damage. Costs 15 " + resourceName + ".");
        System.out.println("2. Suppressive Fire: Deals 10 damage to multiple enemies. Costs 5 " + resourceName + ".");
        System.out.println("3. Camouflage: Utility skill. Hides from enemies. Costs 2 " + resourceName + ".");
    }
}
class Skill {
    String name;
    int damage;
    int cost;

    public Skill(String name, int damage, int cost) {
        this.name = name;
        this.damage = damage;
        this.cost = cost;
    }
}


abstract class Enemy {
    String name;
    int maxHP;
    int currentHP;
    int damage;
    String description;
    
    public Enemy(String name, int hp, int damage, String description) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.damage = damage;
        this.description = description;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public abstract void attack(Character target);
}

class Carrier extends Enemy {
    public Carrier() {
        super("Carrier", 50, 10, "Slow but spreads the virus.");
    }

    @Override
    public void attack(Character target) {
        System.out.println("The Carrier lunges at " + target.name + "!");
        target.takeDamage(damage);
    }
}

class Boneclaw extends Enemy {
    public Boneclaw() {
        super("Boneclaw", 75, 14, "Fast and lethal ambusher.");
    }

    @Override
    public void attack(Character target) {
        System.out.println("The Boneclaw strikes " + target.name + " with its bone blades!");
        target.takeDamage(damage);
    }
}

class Howler extends Enemy {
    public Howler() {
        super("Howler", 100, 10 , "Disorients enemies and summons more Carriers.");
    }

    @Override
    public void attack(Character target) {
        System.out.println("The Howler unleashes a piercing scream!");
        target.takeDamage(damage);
    }
}


public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to OUTBBREAK!");
        System.out.println("Choose your party of 3");

        List<Character> availableCharacters = new ArrayList<>();
        availableCharacters.add(new Zor());
        availableCharacters.add(new Leo());
        availableCharacters.add(new Elara());
        availableCharacters.add(new Kai());
        availableCharacters.add(new Anya());

        List<Character> playerParty = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            System.out.println("\nAvailable Characters:");
            for (int j = 0; j < availableCharacters.size(); j++) {
                System.out.println((j + 1) + ". " + availableCharacters.get(j).name + " - " + availableCharacters.get(j).backstory);
            }

            System.out.print("Choose character " + (i + 1) + " (by number): ");
            int choice = scanner.nextInt() - 1;
            if (choice >= 0 && choice < availableCharacters.size()) {
                playerParty.add(availableCharacters.get(choice));
                availableCharacters.remove(choice);
            } else {
                System.out.println("Invalid choice. Please try again.");
                i--;
            }
        }

        System.out.println("\nYour Party:");
        for (Character member : playerParty) {
            System.out.println("- " + member.name);
        }
    }
}
