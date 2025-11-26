package game.management.panel;

import java.awt.*;
import javax.swing.*;

public class TranslucentPanel extends JPanel {

    public TranslucentPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }
}