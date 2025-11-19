package game.battle;

import game.core.Character;
import game.core.Enemy;
import game.core.Skill;
import game.core.Boss;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attack {

    private final GameVisuals gui;

    public Attack(GameVisuals gui, Battle manager) {
        this.gui = gui;
    }

    public boolean resolvePlayerAction(Character character, Skill skill, Enemy currentEnemy) {
        try {
            if (character == null || skill == null || currentEnemy == null) {
                gui.battleLogArea.append("[ERROR] Invalid action: Character, skill, or enemy is missing.\n");
                return currentEnemy != null && currentEnemy.isAlive();
            }
            if (character.currentHP <= 0) {
                gui.battleLogArea.append(character.name + " is knocked out and cannot act!\n");
                character.currentResource = 0;
                return true;
            }
            if (character.currentResource < skill.cost) {
                gui.battleLogArea.append("[ERROR] " + character.name + " attempted to use " + skill.name + " but lacked the resources!\n");
                return true;
            }

            character.currentResource -= skill.cost;
            int damageDealt = skill.damage;
            currentEnemy.takeDamage(damageDealt);
            gui.battleLogArea.append(character.name + " uses " + skill.name + ", dealing " + damageDealt + " damage to " + currentEnemy.name + ".\n");
            return currentEnemy.isAlive();
        } catch (Exception e) {
            System.err.println("Error resolving player action: " + e.getMessage());
            e.printStackTrace();
            gui.battleLogArea.append("[ERROR] An unexpected error stopped the player's action.\n");
            return currentEnemy != null && currentEnemy.isAlive();
        }
    }


    public void resolveEnemyAction(Enemy currentEnemy, List<Character> playerParty) {
        List<Character> aliveCharacters = getAliveCharacters(playerParty);

        if (aliveCharacters.isEmpty()) {
            return;
        }

        Random rand = new Random();
        Character target = aliveCharacters.get(rand.nextInt(aliveCharacters.size()));

        int bonusdamage = 0;
        if(currentEnemy instanceof Boss) {
            bonusdamage = 3;
        }

        int damage = currentEnemy.damage + bonusdamage;
        target.takeDamage(damage);
        gui.battleLogArea.append(currentEnemy.name + " attacks " + target.name + ", dealing " + damage + " damage.\n");

        if (target.currentHP <= 0) {
            gui.battleLogArea.append(target.name + " is dead!\n");
        }
    }

    private List<Character> getAliveCharacters(List<Character> party) {
        List<Character> alive = new ArrayList<>();
        try {
            for (Character c : party) {
                if (c.currentHP > 0) {
                    alive.add(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while filtering alive characters: " + e.getMessage());
            e.printStackTrace();
        }
        return alive;
    }
}