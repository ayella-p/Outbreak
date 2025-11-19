package game.battle;

import game.core.Character;
import game.core.Enemy;
import game.core.Skill;
import game.enemies.Carrier;
import game.enemies.Howler;
import game.enemies.bosses.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Battle {

    private final GameVisuals gui;
    public final List<Character> playerParty;

    private Enemy currentEnemy;
    private Character activeCharacter;

    //manager
    public final Attack combatResolver;
    public final Floor progressionManager;

    public Battle(GameVisuals gui, Enemy initialEnemy) {
        this.gui = gui;
        this.playerParty = gui.playerParty;
        this.currentEnemy = initialEnemy;

        //initialize manager
        this.combatResolver = new Attack(gui, this);
        this.progressionManager = new Floor(gui, this);
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }



    public void startGame(Enemy enemy) {
        // to remove dead characters
        List<Character> aliveParty = new ArrayList<>();
        for (Character c : playerParty) {
            if (c.currentHP > 0) {
                aliveParty.add(c);
            }
        }
        playerParty.clear();
        playerParty.addAll(aliveParty);

        if (playerParty.isEmpty()) {
            progressionManager.endBattle(false);
            return;
        }


        this.currentEnemy = enemy;
        gui.mainTitlePanel.setVisible(false);
        gui.selectionButtonsPanel.setVisible(false);
        gui.showCard(GameVisuals.BATTLE_PANEL); // the battle panel

        //the upper ui
        gui.enemyNameLabel.setText("Enemy: " + currentEnemy.name.toUpperCase());
        gui.enemyHPLabel.setText("HP: " + currentEnemy.currentHP + " / " + currentEnemy.maxHP);

        //location label
        progressionManager.updateLocationTitle();

        // player status on the right side
        updatePlayerStatusUI();


        String enemyType = (currentEnemy instanceof Carrier || currentEnemy instanceof Howler) ? "Enemy" : "Boss";
        String logMsg = (currentEnemy instanceof DrAlcaraz) ? "A " + currentEnemy.name + " approaches! (FINAL BOSS)\n" :
                "A " + currentEnemy.name + " approaches! (" + enemyType + " - Floor " + gui.currentFloor + ")\n";
        gui.battleLogArea.setText(logMsg);


        this.activeCharacter = null;
        setupCharacterActionButtons(null);
    }

    public void performSkill(Character character, Skill skill) {
        try {
            if (combatResolver == null || progressionManager == null) {
                gui.battleLogArea.append("\n[ERROR] Game managers not initialized. Cannot perform action.\n");
                return;
            }

            boolean enemyAlive = combatResolver.resolvePlayerAction(character, skill, currentEnemy);

            updateBattleUI(); // update enemy hp
            updatePlayerStatusUI(); // update character hp/resources

            if (!enemyAlive) {
                progressionManager.endBattle(true);
                return;
            }

            // enemy turn after player's
            enemyTurn();

            // Check for party wipe after enemy turn
            List<Character> aliveCharacters = new ArrayList<>();
            for (Character c : playerParty) {
                if (c.currentHP > 0) {
                    aliveCharacters.add(c);
                }
            }

            if (aliveCharacters.isEmpty()) {
                gui.battleLogArea.append("\nAll remaining squad members are defeated!\n");
                progressionManager.endBattle(false);
                return;
            }

            // If active character was defeated, clear turn and prompt new selection
            if (activeCharacter != null && activeCharacter.currentHP <= 0) {
                this.activeCharacter = null;
            }
            setupCharacterActionButtons(this.activeCharacter);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Specific Error: Index out of bounds during skill performance or list iteration: " + e.getLocalizedMessage());
            e.printStackTrace();
            gui.battleLogArea.append("\n[ERROR] Data access error encountered. Selecting new action.\n");
            setupCharacterActionButtons(null);
        }
    }

    public void enemyTurn() {
        // go to Attack
        combatResolver.resolveEnemyAction(currentEnemy, playerParty);
        // update player info
        updatePlayerStatusUI();
    }

    public void updateBattleUI() {
        gui.enemyNameLabel.setText("Enemy: " + currentEnemy.name.toUpperCase());
        gui.enemyHPLabel.setText("HP: " + currentEnemy.currentHP + " / " + currentEnemy.maxHP);
    }

    public void updatePlayerStatusUI() {
        gui.playerStatusPanel.removeAll();
        for (Character character : gui.playerParty) {
            JPanel charPanel = new JPanel();
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.Y_AXIS));
            charPanel.setBackground(Color.BLACK);
            charPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            charPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel nameLabel = new JLabel(character.name.toUpperCase());
            nameLabel.setFont(gui.normalFont.deriveFont(java.awt.Font.BOLD));
            nameLabel.setForeground(Color.CYAN);

            JLabel hpLabel = new JLabel("HP: " + character.currentHP + " / " + character.maxHP);
            hpLabel.setFont(gui.normalFont);
            hpLabel.setForeground(Color.GREEN);
            if (character.currentHP <= character.maxHP * 0.25 && character.currentHP > 0) {
                hpLabel.setForeground(Color.RED);
            } else if (character.currentHP <= 0) {
                hpLabel.setText("DEFEATED");
                hpLabel.setForeground(Color.GRAY);
            }

            JLabel resourceLabel = new JLabel(character.resourceName + ": " + character.currentResource + " / " + character.maxResource);
            resourceLabel.setFont(gui.normalFont);
            resourceLabel.setForeground(Color.YELLOW);

            charPanel.add(nameLabel);
            charPanel.add(hpLabel);
            charPanel.add(resourceLabel);

            gui.playerStatusPanel.add(charPanel);
            gui.playerStatusPanel.add(javax.swing.Box.createVerticalStrut(5));
        }

        JButton switchButton = new JButton("CHOOSE CHARACTER");
        switchButton.setFont(gui.normalFont.deriveFont(java.awt.Font.BOLD));
        switchButton.setBackground(Color.BLACK);
        switchButton.setForeground(Color.WHITE);
        switchButton.addActionListener(e -> {
            gui.battleLogArea.append("\nChoose a character to switch to:\n");
            gui.playerStatusPanel.remove(switchButton); // remove choose character button

            for (Character character : gui.playerParty) {
                if (character != activeCharacter && character.currentHP > 0) { // added a chatacter that !activecharacter and alive
                    JButton charSwitchButton = new JButton(character.name);
                    charSwitchButton.setFont(gui.normalFont);
                    charSwitchButton.setBackground(new Color(60, 60, 60));
                    charSwitchButton.setForeground(Color.WHITE);
                    charSwitchButton.setFocusPainted(false);
                    charSwitchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                    charSwitchButton.setMaximumSize(new Dimension(100, 50));

                    charSwitchButton.addActionListener(ev -> {
                        switchToCharacterTurn(character);
                    });

                    gui.playerStatusPanel.add(charSwitchButton);
                }
            }

            gui.playerStatusPanel.revalidate();
            gui.playerStatusPanel.repaint();
        });

        // add choose character button here
        gui.playerStatusPanel.add(switchButton);
        gui.playerStatusPanel.revalidate();
        gui.playerStatusPanel.repaint();
    }

    public void switchToCharacterTurn(Character nextCharacter) {
        updatePlayerStatusUI();
        this.activeCharacter = nextCharacter;
        gui.battleLogArea.append("\n" + activeCharacter.name + "'s turn to act.\n");
        setupCharacterActionButtons(this.activeCharacter);
    }

    public void setupCharacterActionButtons(Character character) {
        gui.battleActionPanel.removeAll();
        boolean isDefeatedOrExhausted = character == null || character.currentHP <= 0 || (character != null && character.currentResource < 2);// the minimum resources

        if (!isDefeatedOrExhausted) {
            JLabel turnLabel = new JLabel(character.name.toUpperCase() + "'s Turn");
            turnLabel.setForeground(Color.YELLOW);
            turnLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 18f));
            gui.battleActionPanel.add(turnLabel);

            for (Skill skill : character.skills) {
                JButton skillButton = new JButton(skill.name + " (" + skill.cost + " " + character.resourceName + ")");
                skillButton.setFont(gui.normalFont);
                skillButton.setEnabled(character.currentResource >= skill.cost);
                skillButton.addActionListener(e -> performSkill(character, skill));
                gui.battleActionPanel.add(skillButton);
            }
        } else {
            JLabel promptLabel;
            if(character == null){
                if(currentEnemy instanceof IronMaw){
                    promptLabel = new JLabel("You came across the Boss of the First Floor, select a character to take action.");
                } else if(currentEnemy instanceof Boneclaw){
                    promptLabel = new JLabel("Boneclaw is coming your way, quick select a character.");
                } else if(currentEnemy instanceof DrAlcaraz){
                    promptLabel = new JLabel("THE FINAL BOSS IS HERE, DEFEAT HIM!!!");
                } else if(currentEnemy instanceof Venomshade){
                    promptLabel = new JLabel("Venomshade the third fLoor Boss is coming your way!!!");
                } else {
                    promptLabel = new JLabel("Select a character to take action.");
                }
            } else if (character.currentHP <= 0) {
                promptLabel = new JLabel(character.name + " is knocked out! Choose another character.");
            } else {
                promptLabel = new JLabel(character.name + " is low on " + character.resourceName + "! Choose another character.");
            }
            promptLabel.setForeground(Color.RED);
            promptLabel.setFont(gui.normalFont.deriveFont(java.awt.Font.BOLD, 18f));
            gui.battleActionPanel.add(promptLabel);
        }

        gui.battleActionPanel.revalidate();
        gui.battleActionPanel.repaint();
    }
}