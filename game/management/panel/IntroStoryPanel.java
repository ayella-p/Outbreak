package game.management.panel;

import game.management.GameVisuals;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IntroStoryPanel extends JPanel {

    private GameVisuals gui;
    private JTextArea backstoryArea;
    private Timer typingTimer;
    private String fullBackstoryText;
    private int charIndex = 0;

    public IntroStoryPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(70, 50, 50, 50));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("OUTBREAK: The Story Begins", SwingConstants.CENTER);
        titleLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 50f));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(30, 0, 30, 0));

        fullBackstoryText =
                "   For three years, humanity lived in a golden age. Dr. Alcaraz’s miracle vaccine had eradicated all " +
                        "pain and sickness, just as promised. Until it turned into a curse, mutating patients into" +
                        " deadly creatures just as the doctor vanished. Intelligence has located his hideout, but" +
                        " it is overrun by these monsters. Because the infection targets the vaccinated, " +
                        "only the naturally immune can survive the zone.\n" +
                        "\n" +
                        "   You are tasked to assemble a squad of these immune survivors to infiltrate the hideout " +
                        "and retrieve the antidote.";

        backstoryArea = new JTextArea();
        backstoryArea.setText("");
        backstoryArea.setFont(gui.normalFont.deriveFont(Font.PLAIN, 22f));
        backstoryArea.setForeground(Color.LIGHT_GRAY);
        backstoryArea.setOpaque(false);
        backstoryArea.setWrapStyleWord(true);
        backstoryArea.setLineWrap(true);
        backstoryArea.setEditable(false);
        backstoryArea.setBorder(BorderFactory.createEmptyBorder(50, 30, 30, 30));

        JButton proceedButton = new JButton("PROCEED TO MISSION");
        proceedButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 30f));
        proceedButton.setBackground(new Color(0, 100, 150));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setPreferredSize(new Dimension(400, 70));

        proceedButton.addActionListener(e -> {
            if (charIndex < fullBackstoryText.length()) {
                if (typingTimer != null) {
                    typingTimer.stop();
                }
                backstoryArea.setText(fullBackstoryText);
                charIndex = fullBackstoryText.length();
            } else {
                gui.showCard(GameVisuals.TITLE_SCREEN_PANEL);
            }
        });

        contentPanel.add(titleLabel);
        contentPanel.add(backstoryArea);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(proceedButton);

        add(contentPanel, BorderLayout.CENTER);

        startTypingAnimation();
    }

    public void startTypingAnimation() {
        charIndex = 0;
        if (backstoryArea != null) {
            backstoryArea.setText("");
        }

        int delay = 50;
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
        }

        typingTimer = new Timer(delay, e -> {
            if (charIndex < fullBackstoryText.length()) {
                backstoryArea.append(String.valueOf(fullBackstoryText.charAt(charIndex)));
                charIndex++;
            } else {
                typingTimer.stop();
            }
        });
        typingTimer.start();
    }
}