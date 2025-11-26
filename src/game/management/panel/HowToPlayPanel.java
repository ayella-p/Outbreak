package game.management.panel;

import game.management.GameVisuals;
import javax.swing.*;
import java.awt.*;

public class HowToPlayPanel extends JPanel {

    private GameVisuals gui;

    public HowToPlayPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("How to Play", SwingConstants.CENTER);
        title.setFont(gui.titleFont.deriveFont(Font.BOLD, 40f));
        title.setForeground(new Color(0, 100, 150));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JTextArea instructionsArea = new JTextArea(
                " SETUP \n\n" +
                        "Form Your Squad: Choose 3 unique characters.\n" +
                        "Manage Resources: Use your character skills wisely — they cost resources!\n\n" +
                        " GAME FLOW \n\n" +
                        "1. Fight: Complete battles in the current level. You may face up to two enemies per level.\n" +
                        "2. Decide: Choose a path wisely; one will lead you to the next level, and the other will lead you to the boss of the current level.\n" +
                        "3. Win: Defeat the level enemies to advance. Win all levels to fight the Boss!\n\n" +
                        " VICTORY CONDITION \n\n" +
                        "WIN: Defeat the enemy squad.\n" +
                        "LOSE: If all 3 of your squad members are defeated, it's Game Over!\n"

        );
        instructionsArea.setFont(gui.normalFont.deriveFont(Font.PLAIN, 20f));
        instructionsArea.setForeground(Color.WHITE);
        instructionsArea.setOpaque(false);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setLineWrap(true);
        instructionsArea.setEditable(false);
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(instructionsArea, BorderLayout.CENTER);

        JButton backButton = new JButton("BACK TO TITLE");
        backButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 25f));
        backButton.setBackground(new Color(0, 100, 150));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> gui.showCard(GameVisuals.TITLE_SCREEN_PANEL));

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(backButton);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        add(southPanel, BorderLayout.SOUTH);
    }
}