package game.management;

import game.base.Character;
import game.base.Enemy;
import game.base.Skill;
import game.base.Boss;
import game.enemies.bosses.Boneclaw;
import game.enemies.bosses.IronMaw;
import game.enemies.bosses.Venomshade;

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
            gui.battleLogArea.append(character.name + " channels their energy and unleashes " + skill.name + "!\n");
            gui.battleLogArea.append("The attack strikes " + currentEnemy.name + " dealing "+ skill.damage+" damage.\n\n");

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

        if (currentEnemy instanceof Boss) {
            performBossMove((Boss)currentEnemy, target);
        } else {
            performNormalEnemyMove(currentEnemy, target);
        }
    }

    private void performNormalEnemyMove(Enemy enemy, Character target) {
        int damage = enemy.damage;
        enemy.attack(target);

        gui.battleLogArea.append("The " + enemy.name + " lunges forward violently to "+ target.name + "!\n");
        gui.battleLogArea.append("                                       >>> " + target.name + " takes "
                + damage + " damage!\n");
    }

    private void performBossMove(Boss boss, Character target) {
        Random rand = new Random();

        boolean canUseSkill = boss.currentMana >= 20;
        boolean wantsToUseSkill = rand.nextInt(100) < 40; // 40% chance
        //boss special attack
        if (canUseSkill && wantsToUseSkill) {

            boss.currentMana -= 20; // 20 mana cost per skill
            boss.useSkill(target);

            gui.battleLogArea.append(boss.name + " unleashes a DEVASTATING attack on " + target.name + "!\n");
            if(boss instanceof Boneclaw){
                gui.battleLogArea.append("Boneclaw drains 10 HP from " + target.name + " and absorbs it!\n");
            } else if(boss instanceof IronMaw || boss instanceof Venomshade){
                gui.battleLogArea.append(boss.name + " heals himself by 20 HP!");
            } else {

                gui.battleLogArea.append("DR. ALCAZAR USES HIS SKILL: HIT " + target.name + " by "+ boss.damage +"!\n" );
            }
            gui.battleLogArea.append("(Boss Mana Remaining: " + boss.currentMana + ")\n\n");

        } else {
            // boss normal attack
            int damage = boss.damage;
            boss.attack(target);

            gui.battleLogArea.append("\nBOSS: " + boss.name + " strikes " + target.name + ".\n");
            gui.battleLogArea.append("                                          -> " + target.name + " took " + damage +
                    " damage.\n");
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