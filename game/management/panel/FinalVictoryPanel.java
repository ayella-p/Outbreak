package game.management.panel;

import game.Main;
import game.management.GameVisuals;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FinalVictoryPanel extends JPanel {

    private GameVisuals gui;

    public FinalVictoryPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(175, 50, 50, 50));

        JPanel centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("OUTBREAK ELIMINATED!", SwingConstants.CENTER);
        titleLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 70f));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("You have defeated Dr. Alcaraz and acquired the antidote!\n", SwingConstants.CENTER);
        subLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 30f));
        subLabel.setForeground(new Color(0, 255, 0));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton finishButton = new JButton("RETURN TO TITLE");
        finishButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        finishButton.setBackground(new Color(150, 0, 0));
        finishButton.setForeground(Color.WHITE);
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(400, 70));
        finishButton.setPreferredSize(new Dimension(400, 70));

        finishButton.addActionListener(e -> {
            gui.window.dispose();
            Main.main(new String[]{}); // Restart
        });

        centerContentPanel.add(titleLabel);
        centerContentPanel.add(Box.createVerticalStrut(30));
        centerContentPanel.add(subLabel);
        centerContentPanel.add(Box.createVerticalStrut(50));
        centerContentPanel.add(finishButton);

        add(centerContentPanel, BorderLayout.CENTER);
    }
}