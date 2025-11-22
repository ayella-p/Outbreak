package game.management;
import java.awt.*;
import javax.swing.*;
import game.base.Character;
import game.enemies.*;

public class CharacterSelection {

    private final GameVisuals gui;

    public CharacterSelection(GameVisuals gui) {
        this.gui = gui;
    }
    // use gui to access GameVisuals which is already public

    public JPanel createCharacterSelectContainer() {
        gui.charDisplayPanel = new JPanel();
        gui.charDisplayPanel.setLayout(new BoxLayout(gui.charDisplayPanel, BoxLayout.Y_AXIS));
        gui.charDisplayPanel.setOpaque(false);
        gui.charDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gui.mainTitlePanel = new JPanel();
        gui.mainTitlePanel.setOpaque(false);
        gui.mainTitlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 2));
        gui.mainTitleLabel = new JLabel("CHOOSE YOUR SQUAD (0/3)");
        gui.mainTitleLabel.setForeground(Color.WHITE);
        gui.mainTitleLabel.setFont(gui.titleFont);
        gui.mainTitlePanel.add(gui.mainTitleLabel);

        gui.charNameLabel = new JLabel(" ");
        gui.charNameLabel.setFont(gui.titleFont.deriveFont(Font.BOLD, 36f));
        gui.charNameLabel.setForeground(Color.YELLOW);
        gui.charNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gui.charStatsLabel = new JLabel(" ");
        gui.charStatsLabel.setFont(gui.normalFont);
        gui.charStatsLabel.setForeground(Color.CYAN);
        gui.charStatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gui.charBackstoryArea = new JTextArea(" ");
        gui.charBackstoryArea.setFont(gui.normalFont);
        gui.charBackstoryArea.setForeground(Color.LIGHT_GRAY);
        gui.charBackstoryArea.setOpaque(false);
        gui.charBackstoryArea.setWrapStyleWord(true);
        gui.charBackstoryArea.setLineWrap(true);
        gui.charBackstoryArea.setEditable(false);
        gui.charBackstoryArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        gui.charBackstoryArea.setMaximumSize(new Dimension(500, 100));
        gui.charBackstoryArea.setPreferredSize(new Dimension(500, 100));

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.setLayout(new GridBagLayout());
        textAreaWrapper.setOpaque(false);
        textAreaWrapper.add(gui.charBackstoryArea);
        textAreaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        gui.detailsButtonPanel = new JPanel();
        gui.detailsButtonPanel.setOpaque(false);
        gui.detailsButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        gui.charDisplayPanel.add(gui.charNameLabel);
        gui.charDisplayPanel.add(Box.createVerticalStrut(10));
        gui.charDisplayPanel.add(gui.charStatsLabel);
        gui.charDisplayPanel.add(Box.createVerticalStrut(20));
        gui.charDisplayPanel.add(textAreaWrapper);
        gui.charDisplayPanel.add(Box.createVerticalStrut(10));
        gui.charDisplayPanel.add(gui.detailsButtonPanel);

        // Container Panel
        JPanel charSelectContainer = new JPanel(new BorderLayout());
        charSelectContainer.setOpaque(false);
        charSelectContainer.add(gui.mainTitlePanel, BorderLayout.NORTH);
        charSelectContainer.add(gui.charDisplayPanel, BorderLayout.CENTER);

        gui.selectionButtonsPanel = new JPanel();
        gui.selectionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        gui.selectionButtonsPanel.setOpaque(false);
        createCharacterSelectionButtons();
        charSelectContainer.add(gui.selectionButtonsPanel, BorderLayout.SOUTH);

        return charSelectContainer;
    }

    public void createCharacterSelectionButtons() {
        try {
        JButton startButton = new JButton("START MISSION");
        startButton.setFont(gui.titleFont.deriveFont(Font.BOLD, 24f));
        startButton.setBackground(new Color(150, 0, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setEnabled(false);
        startButton.setName("START_BUTTON");
        // This button initiates the game loop with the first enemy
        startButton.addActionListener(e -> {
            try {
                gui.battleManager.startGame(new Carrier());
            } catch (Exception ex) {
                System.err.println("Error starting game: " + ex.getMessage());
            }
        });
        gui.selectionButtonsPanel.add(startButton);

        for (Character character : gui.availableCharacters) {
            JButton charButton = new JButton(character.name);
            charButton.setFont(gui.normalFont);
            charButton.setBackground(new Color(60, 60, 60));
            charButton.setForeground(Color.WHITE);
            charButton.setFocusPainted(false);

            charButton.addActionListener(e -> {
                try {
                    displayCharacterDetails(character, charButton); // display character is here lang
                } catch (Exception ex) {
                    System.err.println("Error displaying details for " + character.name + ": " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            gui.selectionButtonsPanel.add(charButton, 0); // 0 to put the Start Mission further
        }
    }catch (Exception e) {
        System.err.println("Error creating character selection buttons: " + e.getMessage());
        e.printStackTrace();
    }
}

    public void displayCharacterDetails(Character c, JButton sourceButton) {
        gui.charNameLabel.setText(c.name.toUpperCase());
        gui.charStatsLabel.setText("HP: " + c.maxHP + " | " + c.resourceName + ": " + c.maxResource);
        gui.charBackstoryArea.setText(c.backstory);
        gui.currentViewedCharacter.set(c);

        updateDetailsButtonPanel(c, sourceButton);
    }

    void updateDetailsButtonPanel(Character c, JButton sourceButton) {
        try {
            gui.detailsButtonPanel.removeAll();
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
                gui.detailsButtonPanel.add(selectButton);
            } else {
                gui.detailsButtonPanel.removeAll();
            }
            gui.detailsButtonPanel.revalidate();
            gui.detailsButtonPanel.repaint();
        } catch (Exception e) {
            System.err.println("Error updating details button panel: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void selectCharacter(JButton button, Character c) {
        if (gui.selectionCount < 3 && !gui.playerParty.contains(c)) {
            gui.playerParty.add(c);
            gui.selectionCount++;
            gui.mainTitleLabel.setText("CHOOSE YOUR SQUAD (" + gui.selectionCount + "/3)");

            button.setEnabled(false);
            button.setBackground(Color.BLACK);

            displayCharacterDetails(c, button);

            if (gui.selectionCount == 3) {
                // Find and enable the START_BUTTON
                for (Component comp : gui.selectionButtonsPanel.getComponents()) {
                    if (comp instanceof JButton && "START_BUTTON".equals(comp.getName())) { // to locate the Start Button
                        JButton btn = (JButton) comp; // cast from generic component to a JButton, it allows to modify properties
                        btn.setEnabled(true);
                        btn.setBackground(new Color(0, 150, 0)); // the modified property
                        break;
                    }
                }
            }
        }
    }
}