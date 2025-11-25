package game.management.panel;

import game.Main;
import game.management.GameVisuals;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private GameVisuals gui;

    public GameOverPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(200, 0, 0, 0));

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 80f));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backToTitleButton = new JButton("BACK TO TITLE");
        backToTitleButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        backToTitleButton.setBackground(Color.DARK_GRAY);
        backToTitleButton.setForeground(Color.WHITE);
        backToTitleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backToTitleButton.addActionListener(e -> {
            gui.window.dispose();
            Main.main(new String[]{}); // Restart
        });

        add(gameOverLabel);
        add(Box.createVerticalStrut(50));
        add(backToTitleButton);
    }
}