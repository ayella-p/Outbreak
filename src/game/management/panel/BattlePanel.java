package game.management.panel;

import game.management.GameVisuals;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BattlePanel extends JPanel{
    private GameVisuals gui;

    public BattlePanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);

        gui.enemyPanel = new JPanel();
        gui.enemyPanel.setLayout(new BoxLayout(gui.enemyPanel, BoxLayout.Y_AXIS));
        gui.enemyPanel.setBackground(Color.BLACK);
        gui.enemyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gui.locationLabel = new JLabel("Level 1: Abandoned Emergency Room", SwingConstants.CENTER);
        gui.locationLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 18f));
        gui.locationLabel.setForeground(gui.oceanBlue);
        gui.locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gui.enemyPanel.add(gui.locationLabel);

        gui.enemyNameLabel = new JLabel("Enemy: ???");
        gui.enemyNameLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 40f));
        gui.enemyNameLabel.setForeground(Color.WHITE);
        gui.enemyNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gui.enemyHPLabel = new JLabel("HP: ???");
        gui.enemyHPLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 20f));
        gui.enemyHPLabel.setForeground(Color.RED);
        gui.enemyHPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gui.enemyPanel.add(gui.enemyNameLabel);
        gui.enemyPanel.add(gui.enemyHPLabel);

        add(gui.enemyPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize Shared Variable: battleLogArea
        gui.battleLogArea = new JTextArea(" ");
        gui.battleLogArea.setFont(gui.normalFont.deriveFont(Font.PLAIN, 20f));
        gui.battleLogArea.setForeground(Color.LIGHT_GRAY);
        gui.battleLogArea.setOpaque(false);
        gui.battleLogArea.setEditable(false);
        gui.battleLogArea.setLineWrap(true);
        gui.battleLogArea.setWrapStyleWord(true);
        gui.battleLogArea.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 20));

        JScrollPane scrollPane = new JScrollPane(gui.battleLogArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(255, 255, 255, 50)));
        scrollPane.setPreferredSize(new Dimension(0, 0));
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.65; // wider
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 10);
        centerPanel.add(scrollPane, gbc);

        gui.playerStatusPanel = new JPanel();
        gui.playerStatusPanel.setLayout((new GridLayout(3, 1, 0, 10)));
        gui.playerStatusPanel.setOpaque(false);
        gui.playerStatusPanel.setPreferredSize(new Dimension(0, 0));

        gbc.gridx = 1;
        gbc.weightx = 0.35; // thinner
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(gui.playerStatusPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);


        gui.battleActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        gui.battleActionPanel.setOpaque(false);
        add(gui.battleActionPanel, BorderLayout.SOUTH);

        // link this panel back to the main GUI variable
        gui.battlePanel = this;

    }
}
