package game.management;

import game.base.Boss;
import game.base.Character;
import game.base.Enemy;
import game.enemies.bosses.*;
import game.enemies.Howler;
import game.enemies.Carrier;
import javax.swing.*;
import java.awt.*;

public class Floor {

    private final GameVisuals gui;
    private final Battle manager;
    private static final String EAST_DIRECTION = "A";
    private static final String WEST_DIRECTION = "B";

    public Floor(GameVisuals gui, Battle manager) {
        this.gui = gui;
        this.manager = manager;
    }

    public void endBattle(boolean win) {

        if(!win){
            gui.showCard(GameVisuals.GAME_OVER_PANEL);
            return;
        }
        try {
                // apply healing
                for (Character c : gui.playerParty) {
                    if (c.currentHP >= 0) {
                        int healAmount = (int) (c.maxHP * 0.35);
                        int resourceRestore = (int) (c.maxResource * 0.35);
                        c.currentHP = Math.min(c.currentHP + healAmount, c.maxHP); // returns the min, and returns equals if over the maxHP
                        c.currentResource = Math.min(c.currentResource + resourceRestore, c.maxResource); // same here
                    }
                }
                manager.updatePlayerStatusUI(); // update player status


                if (gui.currentFloor == 1) gui.floorChoice = "Second Floor";
                else if (gui.currentFloor == 2) gui.floorChoice = "Third Floor";
                else if (gui.currentFloor >= 3) gui.floorChoice = "Final Floor";

                // boss transition (special for the Final Boss)
                Enemy currentEnemy = manager.getCurrentEnemy();
                if (currentEnemy instanceof DrAlcaraz) {
                    gui.showCard(GameVisuals.FINAL_VICTORY_PANEL); // victory
                } else if (currentEnemy instanceof Venomshade) {
                    gui.currentFloor = 4; // to go to final Lebel
                    gui.battleLogArea.append("\nVenomshade defeated! The true threat, Dr. Alcaraz, emerges!\n");
                    manager.startGame(new DrAlcaraz());
                } else if (currentEnemy instanceof Boss) {
                    gui.showCard(GameVisuals.MISSION_COMPLETE_PANEL);
                } else {
                    setupDirectionPanelUI();
                    gui.showCard(GameVisuals.DIRECTIONAL_PANEL); // regular enemy
                }


        } catch (Exception e) {
            System.err.println("General Error during endBattle execution: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void setupDirectionPanelUI() {
        gui.directionPanel.removeAll(); // Clear old content
        gui.directionPanel.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));

        JLabel directionLabel = new JLabel("You have defeated the enemy.", SwingConstants.CENTER);
        directionLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 35f));
        directionLabel.setForeground(Color.GREEN);
        directionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel choicePromptLabel = new JLabel("Which direction will you proceed?", SwingConstants.CENTER);
        choicePromptLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 25f));
        choicePromptLabel.setForeground(Color.WHITE);
        choicePromptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(directionLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(choicePromptLabel);

        JPanel choicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        choicesPanel.setOpaque(false);

        // --- BUTTONS (No Spoilers) ---
        JButton choiceAButton = new JButton("EAST");
        choiceAButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceAButton.setBackground(new Color(40, 40, 100));
        choiceAButton.setForeground(Color.WHITE);
        choiceAButton.setPreferredSize(new Dimension(200, 80));
        choiceAButton.addActionListener(e -> chooseDirection("A"));

        JButton choiceBButton = new JButton("WEST");
        choiceBButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceBButton.setBackground(new Color(100, 40, 40));
        choiceBButton.setForeground(Color.WHITE);
        choiceBButton.setPreferredSize(new Dimension(200, 80));
        choiceBButton.addActionListener(e -> chooseDirection("B"));

        choicesPanel.add(choiceAButton);
        choicesPanel.add(choiceBButton);

        gui.directionPanel.add(textPanel, BorderLayout.NORTH);
        gui.directionPanel.add(choicesPanel, BorderLayout.CENTER);

        gui.directionPanel.revalidate();
        gui.directionPanel.repaint();
    }
    public void chooseDirection(String choice) {
        if (choice.equals(EAST_DIRECTION)) { // East (Boss)
            Enemy boss = null;
            if (gui.currentFloor == 1) {
                boss = new IronMaw();
            } else if (gui.currentFloor == 2) {
                boss = new Boneclaw();
            } else if (gui.currentFloor == 3) {
                boss = new Venomshade();
            }

            if (boss != null) {
                manager.startGame(boss);
            }
        } else if (choice.equals(WEST_DIRECTION)) { // west
            handleWestHealing();
        }
    }


    public void handleWestHealing() {
        for (Character character : gui.playerParty) {
            character.currentHP = character.maxHP;
            character.currentResource = character.maxResource;
        }
        manager.updatePlayerStatusUI();
        gui.directionPanel.removeAll();
        gui.directionPanel.setLayout(new BorderLayout());

        JPanel messageContainer = new JPanel(new GridLayout(3, 1));
        messageContainer.setBackground(Color.BLACK);
        messageContainer.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        JLabel titleLabel = new JLabel("HEALING INJECTION RECEIVED", SwingConstants.CENTER);
        titleLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 40f));
        titleLabel.setForeground(Color.YELLOW);

        JLabel messageLabel = new JLabel("HP and Resources are fully restored!", SwingConstants.CENTER);
        messageLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 35f));
        messageLabel.setForeground(Color.GREEN);

        messageContainer.add(titleLabel);
        messageContainer.add(Box.createVerticalStrut(15));
        messageContainer.add(messageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        buttonPanel.setBackground(Color.BLACK);

        JButton nextFloorButton = new JButton("NEXT FLOOR");
        nextFloorButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        nextFloorButton.setBackground(new Color(0, 150, 0));
        nextFloorButton.setForeground(Color.WHITE);
        nextFloorButton.setPreferredSize(new Dimension(400, 70));
        nextFloorButton.setFocusPainted(false);

        // update for final level
        if (gui.currentFloor == 3) {
            nextFloorButton.setText("FINAL CONFRONTATION");
            nextFloorButton.addActionListener(e -> {
                gui.currentFloor = 4;
                manager.startGame(new DrAlcaraz());
            });
        } else {
            nextFloorButton.addActionListener(e -> newFloor());
        }

        buttonPanel.add(nextFloorButton);
        gui.directionPanel.add(messageContainer, BorderLayout.CENTER);
        gui.directionPanel.add(buttonPanel, BorderLayout.SOUTH);
        gui.directionPanel.revalidate();
        gui.directionPanel.repaint();
    }


    public void newFloor() {
        try {
            gui.currentFloor++;

            if (gui.currentFloor > 4) {
                gui.showCard(GameVisuals.FINAL_VICTORY_PANEL); //victory
                return;
            }

            Enemy nextEnemy = null;
            if (gui.currentFloor == 2) {
                nextEnemy = new Howler();
            } else if (gui.currentFloor == 3) {
                nextEnemy = new Carrier();
            } else if (gui.currentFloor == 4) {
                nextEnemy = new DrAlcaraz();
            }

            if (nextEnemy != null) {
                manager.startGame(nextEnemy);
            } else {
                gui.showCard(GameVisuals.MISSION_COMPLETE_PANEL);
            }

            updateLocationTitle();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLocationTitle() {
        if (gui.currentFloor == 1) {
            gui.locationLabel.setText("Level 1: Abandoned Emergency Room");
            gui.locationLabel.setForeground(gui.oceanBlue);
        } else if (gui.currentFloor == 2) {
            gui.locationLabel.setText("Level 2: Infectious ICU");
            gui.locationLabel.setForeground(gui.oceanBlue);
        } else if (gui.currentFloor == 3) {
            gui.locationLabel.setText("Level 3: Underground Research Lab");
            gui.locationLabel.setForeground(gui.oceanBlue);
        } else if (gui.currentFloor == 4) {
            gui.locationLabel.setText("Level 4: Final Confrontation Chamber");
            gui.locationLabel.setForeground(Color.RED);
        }
    }
}