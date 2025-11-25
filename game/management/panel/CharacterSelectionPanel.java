package game.management.panel;

import game.base.Character;
import game.enemies.Carrier;
import game.management.GameVisuals;
import javax.swing.*;
import java.awt.*;

// 1. Extend JPanel so this class IS the visual panel
public class CharacterSelectionPanel extends JPanel {

    private final GameVisuals gui;

    // 2. Move specific UI components HERE.
    // They don't need to be in GameVisuals anymore because only this screen uses them.
    private JLabel mainTitleLabel;
    private JLabel charNameLabel;
    private JLabel charStatsLabel;
    private JTextArea charBackstoryArea;
    private JPanel detailsButtonPanel;
    private JPanel selectionButtonsPanel;

    public CharacterSelectionPanel(GameVisuals gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);

        // 3. The logic from 'createCharacterSelectContainer' goes into the constructor
        initUI();
    }

    private void initUI() {
        // --- Center Panel (Character Display) ---
        JPanel charDisplayPanel = new JPanel();
        charDisplayPanel.setLayout(new BoxLayout(charDisplayPanel, BoxLayout.Y_AXIS));
        charDisplayPanel.setOpaque(false);
        charDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JPanel mainTitlePanel = new JPanel();
        mainTitlePanel.setOpaque(false);
        mainTitlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 2));

        mainTitleLabel = new JLabel("CHOOSE YOUR SQUAD (0/3)");
        mainTitleLabel.setForeground(Color.WHITE);
        mainTitleLabel.setFont(gui.titleFont);
        mainTitlePanel.add(mainTitleLabel);

        // Character Info Labels (Now using local variables)
        charNameLabel = new JLabel(" ");
        charNameLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 36f));
        charNameLabel.setForeground(Color.YELLOW);
        charNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        charStatsLabel = new JLabel(" ");
        charStatsLabel.setFont(gui.normalFont);
        charStatsLabel.setForeground(Color.CYAN);
        charStatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        charBackstoryArea = new JTextArea(" ");
        charBackstoryArea.setFont(gui.normalFont);
        charBackstoryArea.setForeground(Color.LIGHT_GRAY);
        charBackstoryArea.setOpaque(false);
        charBackstoryArea.setWrapStyleWord(true);
        charBackstoryArea.setLineWrap(true);
        charBackstoryArea.setEditable(false);
        charBackstoryArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        charBackstoryArea.setMaximumSize(new Dimension(500, 100));
        charBackstoryArea.setPreferredSize(new Dimension(500, 100));

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.setLayout(new GridBagLayout());
        textAreaWrapper.setOpaque(false);
        textAreaWrapper.add(charBackstoryArea);
        textAreaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setOpaque(false);
        detailsButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        charDisplayPanel.add(charNameLabel);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(charStatsLabel);
        charDisplayPanel.add(Box.createVerticalStrut(20));
        charDisplayPanel.add(textAreaWrapper);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(detailsButtonPanel);

        // Add to THIS panel (Since this class extends JPanel)
        add(mainTitlePanel, BorderLayout.NORTH);
        add(charDisplayPanel, BorderLayout.CENTER);

        // --- Bottom Panel (Selection Buttons) ---
        selectionButtonsPanel = new JPanel();
        selectionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        selectionButtonsPanel.setOpaque(false);

        createCharacterSelectionButtons();

        add(selectionButtonsPanel, BorderLayout.SOUTH);
    }

    public void createCharacterSelectionButtons() {
        try {
            JButton startButton = new JButton("START MISSION");
            startButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 24f));
            startButton.setBackground(new Color(150, 0, 0));
            startButton.setForeground(Color.WHITE);
            startButton.setEnabled(false);
            startButton.setName("START_BUTTON");

            startButton.addActionListener(e -> {
                try {
                    gui.battleManager.startGame(new Carrier());
                } catch (Exception ex) {
                    System.err.println("Error starting game: " + ex.getMessage());
                }
            });
            selectionButtonsPanel.add(startButton);

            // Access availableCharacters from GameVisuals (Shared Data)
            for (Character character : gui.availableCharacters) {
                JButton charButton = new JButton(character.name);
                charButton.setFont(gui.normalFont);
                charButton.setBackground(new Color(60, 60, 60));
                charButton.setForeground(Color.WHITE);
                charButton.setFocusPainted(false);

                charButton.addActionListener(e -> {
                    try {
                        displayCharacterDetails(character, charButton);
                    } catch (Exception ex) {
                        System.err.println("Error displaying details: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                });

                selectionButtonsPanel.add(charButton, 0);
            }
        } catch (Exception e) {
            System.err.println("Error creating character selection buttons: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayCharacterDetails(Character c, JButton sourceButton) {
        // We use the local variables now, not 'gui.charNameLabel'
        charNameLabel.setText(c.name.toUpperCase());
        charStatsLabel.setText("HP: " + c.maxHP + " | " + c.resourceName + ": " + c.maxResource);
        charBackstoryArea.setText(c.backstory);

        // This is still shared data, so we keep it in gui
        gui.currentViewedCharacter.set(c);

        updateDetailsButtonPanel(c, sourceButton);
    }

    void updateDetailsButtonPanel(Character c, JButton sourceButton) {
        try {
            detailsButtonPanel.removeAll();
            JButton selectButton = new JButton("SELECT " + c.name.toUpperCase());
            selectButton.setFont(gui.normalFont.deriveFont(Font.BOLD));
            selectButton.setBackground(new Color(0, 100, 150));
            selectButton.setForeground(Color.WHITE);

            if (gui.selectionCount < 3) {
                selectButton.addActionListener(e -> {
                    try {
                        selectCharacter(sourceButton, c);
                    } catch (Exception ex) {
                        System.err.println("Error in selectCharacter logic: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                });
                detailsButtonPanel.add(selectButton);
            } else {
                detailsButtonPanel.removeAll();
            }
            detailsButtonPanel.revalidate();
            detailsButtonPanel.repaint();
        } catch (Exception e) {
            System.err.println("Error updating details button panel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void selectCharacter(JButton button, Character c) {
        if (gui.selectionCount < 3 && !gui.playerParty.contains(c)) {
            gui.playerParty.add(c);
            gui.selectionCount++;
            mainTitleLabel.setText("CHOOSE YOUR SQUAD (" + gui.selectionCount + "/3)");

            button.setEnabled(false);
            button.setBackground(Color.BLACK);

            displayCharacterDetails(c, button);

            if (gui.selectionCount == 3) {
                for (Component comp : selectionButtonsPanel.getComponents()) {
                    if (comp instanceof JButton && "START_BUTTON".equals(comp.getName())) {
                        JButton btn = (JButton) comp;
                        btn.setEnabled(true);
                        btn.setBackground(new Color(0, 150, 0));
                        break;
                    }
                }
            }
        }
    }
}