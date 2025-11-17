package game.battle;

import game.core.Boss;
import game.core.Character;
import game.core.Enemy;
import game.enemies.bosses.*;
import game.enemies.Howler;
import game.enemies.Carrier;
import javax.swing.*;
import java.awt.*;

public class Floor {

    private final GameVisuals gui;
    private final Battle manager;

    public Floor(GameVisuals gui, Battle manager) {
        this.gui = gui;
        this.manager = manager;
    }

    public void endBattle(boolean win) {
        try {
            if (win) {
                // apply healing
                int floor = gui.currentFloor + 1;
                for (Character c : gui.playerParty) {
                    if (c.currentHP >= 0) {
                        int healAmount = (int) (c.maxHP * 0.25);
                        int resourceRestore = (int) (c.maxResource * 0.25);
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
                    gui.showCard(GameVisuals.DIRECTIONAL_PANEL); // regular enemy
                }
            } else {
                gui.showCard(GameVisuals.GAME_OVER_PANEL);
            }
        } catch (NullPointerException e) {
            System.err.println("Specific Error: Null reference encountered during endBattle (UI or Manager access): " + e.getMessage());
            e.printStackTrace();

            if (gui != null) gui.showCard(GameVisuals.GAME_OVER_PANEL);
        } catch (Exception e) {
            System.err.println("General Error during endBattle execution: " + e.getMessage());
            e.printStackTrace();
            if (gui != null) gui.showCard(GameVisuals.GAME_OVER_PANEL);
        }
    }



    public void chooseDirection(String choice) {
        if (choice.equals("A")) { // East (Boss)
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
        } else if (choice.equals("B")) { // west
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
        messageLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 20f));
        messageLabel.setForeground(Color.GREEN);

        messageContainer.add(titleLabel);
        messageContainer.add(Box.createVerticalStrut(20));
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
            nextFloorButton.addActionListener(e -> startNextFloorTransition());
        }

        buttonPanel.add(nextFloorButton);
        gui.directionPanel.add(messageContainer, BorderLayout.CENTER);
        gui.directionPanel.add(buttonPanel, BorderLayout.SOUTH);
        gui.directionPanel.revalidate();
        gui.directionPanel.repaint();
    }


    public void startNextFloorTransition() {
        try {
            gui.directionPanel.removeAll();
            gui.directionPanel.setLayout(new BorderLayout());
            newFloor();
        } catch (Exception e) {
            System.err.println("Error in startNExtFloorTransition: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void newFloor() {
        try {
            gui.currentFloor++;
            gui.cardPanel.remove(gui.directionPanel);
            gui.directionPanel = gui.createDirectionPanel();
            gui.cardPanel.add(gui.directionPanel, GameVisuals.DIRECTIONAL_PANEL);

            if (gui.currentFloor > 4) {
                gui.showCard(GameVisuals.FINAL_VICTORY_PANEL); //victory
                return;
            }

            Enemy nextEnemy = null;
            if (gui.currentFloor == 2) {
                nextEnemy = new Howler();
            } else if (gui.currentFloor == 3) {
                nextEnemy = new Carrier();
            }

            if (nextEnemy != null) {
                manager.startGame(nextEnemy);
            } else {
                gui.showCard(GameVisuals.MISSION_COMPLETE_PANEL);
            }
        } catch (NullPointerException e) {
            System.err.println("Specific Error: Null reference during newFloor setup (UI component access): " + e.getMessage());
            e.printStackTrace();
            gui.showCard(GameVisuals.GAME_OVER_PANEL);
        } catch (Exception e) {
            System.err.println("General Error during newFloor: " + e.getMessage());
            e.printStackTrace();
            gui.showCard(GameVisuals.GAME_OVER_PANEL);
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