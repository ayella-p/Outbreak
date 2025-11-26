package game.management.panel;

import game.management.GameVisuals;
import javax.swing.*;
import java.awt.*;

public class TitleScreenPanel extends JPanel {

    private GameVisuals gui;

    public TitleScreenPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("OUTBREAK", SwingConstants.CENTER);
        title.setFont(gui.titleFont.deriveFont(Font.BOLD, 80f));
        title.setForeground(new Color(200, 0, 0));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JButton startButton = new JButton("START MISSION");
        startButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusPainted(false);
        startButton.setBackground(new Color(150, 0, 0));
        startButton.setMaximumSize(new Dimension(300, 70));
        startButton.addActionListener(e -> gui.showCard(GameVisuals.CHARACTER_SELECT_PANEL));

        JButton howToPlayButton = new JButton("HOW TO PLAY");
        howToPlayButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        howToPlayButton.setForeground(Color.WHITE);
        howToPlayButton.setBackground(new Color(0, 100, 150));
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setMaximumSize(new Dimension(300, 70));
        howToPlayButton.addActionListener(e -> gui.showCard(GameVisuals.HOW_TO_PLAY_PANEL));

        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        quitButton.setFocusPainted(false);
        quitButton.setForeground(Color.WHITE);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setMaximumSize(new Dimension(300, 70));
        quitButton.setBackground(Color.DARK_GRAY);
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}