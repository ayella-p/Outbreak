 
 
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
        super("Zor", 800, 120, "Energy", "A former deep-cover operative, Zor's training focused on infiltration and espionage. He was one of the few who, due to a rare genetic anomaly, was naturally immune to the vaccine's effects. He uses his enhanced skills to fight for a government that abandoned its people, his only motivation a desire to see justice served.");

    }
    
    @Override
    public void displaySkills() {
        System.out.println("Zor's Skiils:");
        System.out.println("1. Sword Slash: Deals 50 damage. No " + resourceName + " cost.");
        System.out.println("2. High-Jump: Utility skill. Costs 20 " + resourceName + ".");
        System.out.println("3. Stealth: Utility skill. Costs 5 " + resourceName + " per turn.");
    }
}

class Leo extends Character {
    public Leo() {
        super("Leo", 1500, 100, "Stamina", "Leo was a combat medic on the front lines when the pandemic hit. He was among the first to be administered the vaccine, but it had no effect on him due to his unique genetics. The trauma of losing his entire unit when the Reavers first emerged fuels his relentless drive to protect others.");

    }

    @Override
    public void displaySkills() {
        System.out.println("Leo's Skiils:");
        System.out.println("1. Overhead Strike: Deals 300 damage. Costs 25  " + resourceName + ".");
        System.out.println("2. Basic Block: Reduces incoming damage. Costs 10 " + resourceName + ".");
        System.out.println("3. Crowd Control (Roar/Stomp): Deals 300 damage to all enemies and stuns them for 3s. Costs 60  " + resourceName + " per turn.");
    }
}

class Elara extends Character {
    public Elara() {
        super("Elara", 700, 200, "Battery", "A brilliant but reclusive software engineer, Elara was immune to the vaccine due to a rare blood type. She was forced to watch as her entire family, who were not immune, turned into Reavers.");

    }

    @Override 
    public void displaySkills() {
        System.out.println("Elara's Skills:");
        System.out.println("1. Piercing Arrow: Deals 250 damage. Costs 50 " + resourceName + ".");
        System.out.println("2. Precision Aim: Utility skill. Costs 15 " + resourceName + " per turn.");
        System.out.println("3. Scout: Utility skill. Costs 30 " + resourceName + " to deploy.");
    }
}

class Kai extends Character {
    public Kai() {
        super("Kai", 950, 150, "Focus", "A former bio-hacker, Kai developed a unique neural interface that allows him to manipulate the mutated creatures' own biology. He has a complicated past, having been involved in the very corporation that created the vaccine, and seeks redemption by using his knowledge to undo the damage.");

    }

    @Override 
    public void displaySkills() {
        System.out.println("Kai's Skills:");
        System.out.println("1. Neutral Shock: Deals 150 damage. Has a chance to stun. Costs 20 " + resourceName + ".");
        System.out.println("2. Bio-Scan: Utility skill. Reveals enemy weak points. Costs 10 " + resourceName + ".");
        System.out.println("3. Mutagenic Surge: Deals 300 damage and debuffs enemy defenses. Costs 45 " + resourceName + ".");
    }
}

class Anya extends Character {
    public Anya() {
        super("Anya", 1000, 100, "Resolve", "A former bio-hacker, Kai developed a unique neural interface that allows him to manipulate the mutated creatures' own biology. He has a complicated past, having been involved in the very corporation that created the vaccine, and seeks redemption by using his knowledge to undo the damage.");

    }

    @Override
    public void displaySkills() {
        System.out.println("Anya's Skills:");
        System.out.println("1. Headshot: Deals 400 damage. High single-target damage. Costs 50 " + resourceName + ".");
        System.out.println("2. Suppressive Fire: Deals 120 damage to multiple enemies. Costs 30 " + resourceName + ".");
        System.out.println("3. Camouflage: Utility skill. Hides from enemies. Costs 20 " + resourceName + ".");
    }
}
class Skill {
    String name;
    int damage;
    int cost;

    public Skill(String name; int damage, int cost) {
        this.name = name;
        this.damage = damage;
        this.cost = cost;
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
