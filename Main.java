 
 
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