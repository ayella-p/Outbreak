package game.management.panel;

import game.management.GameVisuals;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MissionCompletePanel extends JPanel {

    private GameVisuals gui;

    public MissionCompletePanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(175, 50, 50, 50));

        JPanel centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("MISSION SUCCESSFUL!");
        titleLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 60f));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("You Cleared this level! Prepare for the next challenge.\n");
        subLabel.setFont(gui.normalFont.deriveFont(Font.BOLD, 24f));
        subLabel.setForeground(Color.YELLOW);
        subLabel.setName("SUB_LABEL");
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String nextButtonText = (gui.currentFloor == 4) ? "FINAL BOSS" : "NEXT LEVEL";
        JButton nextFloorButton = new JButton(nextButtonText);
        nextFloorButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        nextFloorButton.setBackground(new Color(0, 150, 0));
        nextFloorButton.setForeground(Color.WHITE);
        nextFloorButton.setName("FLOOR_BUTTON");
        nextFloorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextFloorButton.setPreferredSize(new Dimension(400, 70));

        // call back to progression manager
        nextFloorButton.addActionListener(e -> gui.battleManager.progressionManager.newFloor());

        centerContentPanel.add(titleLabel);
        centerContentPanel.add(Box.createVerticalStrut(20));
        centerContentPanel.add(subLabel);
        centerContentPanel.add(Box.createVerticalStrut(40));
        centerContentPanel.add(nextFloorButton);

        add(centerContentPanel, BorderLayout.CENTER);
    }
}