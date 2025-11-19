package game;

import javax.swing.SwingUtilities;
import game.management.GameVisuals;

public class Main {
    public static void main(String[] args) {
        // to run the gui
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameVisuals(); // start with the titlePanel
            }
        });
    }
}