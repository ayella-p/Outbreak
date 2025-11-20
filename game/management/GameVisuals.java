package game.management;
import game.Main;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import game.base.Character;
import game.characters.*;
import game.enemies.*;

public class GameVisuals {
    public JFrame window;
    public Container con;

    // cardlayout like album
    public JPanel cardPanel;
    public CardLayout cardLayout;
    public JPanel mainTitlePanel, charDisplayPanel, selectionButtonsPanel, detailsButtonPanel;
    public JLabel mainTitleLabel, charNameLabel, charStatsLabel;
    public JTextArea charBackstoryArea;
    public JPanel battlePanel, enemyPanel, battleActionPanel;
    public JLabel enemyNameLabel, enemyHPLabel;
    public JTextArea battleLogArea;
    public JPanel playerStatusPanel;
    public JPanel directionPanel;
    public JLabel locationLabel;
    public JPanel gameOverPanel;

    // card panel identifiers
    public final static String TITLE_SCREEN_PANEL = "TITLE_SCREEN";
    public final static String HOW_TO_PLAY_PANEL = "HOW_TO_PLAY";
    public final static String CHARACTER_SELECT_PANEL = "CHARACTER_SELECT";
    public final static String BATTLE_PANEL = "BATTLE";
    public final static String DIRECTIONAL_PANEL = "DIRECTION_CHOICE";
    public final static String MISSION_COMPLETE_PANEL = "MISSION_COMPLETE";
    public final static String FINAL_VICTORY_PANEL = "FINAL_VICTORY";
    public final static String GAME_OVER_PANEL = "GAME_OVER";

    public Font titleFont;
    public Font normalFont;
    public Color oceanBlue = new Color(0, 119, 190);


    public List<Character> availableCharacters = new ArrayList<>();
    public List<Character> playerParty = new ArrayList<>();
    public int selectionCount = 0;
    public int currentFloor = 1;
    public String floorChoice = "First Floor";
    public final AtomicReference<Character> currentViewedCharacter = new AtomicReference<>(null);

    // managers
    private CharacterSelection charSelectionManager;
    public Battle battleManager;

    public GameVisuals() {
        try {
            titleFont = loadPixelFont(50f);
            normalFont = loadPixelFont(20f);
            this.charSelectionManager = new CharacterSelection(this); // this = referencing to gamevisuals gui
            this.battleManager = new Battle(this, new Carrier()); // first floor enemy carrier

            initializeCharacters();

            //window
            window = new JFrame();
            window.setSize(1000, 700);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setTitle("OUTBREAK");
            window.getContentPane().setBackground(new Color(240, 240, 240));
            window.setLayout(new BorderLayout());
            con = window.getContentPane();


            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);
            cardPanel.setBackground(Color.WHITE);
            con.add(cardPanel, BorderLayout.CENTER);


            cardPanel.add(createTitleScreenPanel(), TITLE_SCREEN_PANEL);
            cardPanel.add(createHowToPlayPanel(), HOW_TO_PLAY_PANEL);
            cardPanel.add(charSelectionManager.createCharacterSelectContainer(), CHARACTER_SELECT_PANEL);
            battlePanel = createBattlePanel();
            cardPanel.add(battlePanel, BATTLE_PANEL);
            directionPanel = createDirectionPanel();
            cardPanel.add(directionPanel, DIRECTIONAL_PANEL);
            cardPanel.add(createMissionCompletePanel(), MISSION_COMPLETE_PANEL);
            cardPanel.add(createFinalVictoryPanel(), FINAL_VICTORY_PANEL);
            gameOverPanel = createGameOverPanel();
            cardPanel.add(gameOverPanel, GAME_OVER_PANEL);

            // start screen
            cardLayout.show(cardPanel, TITLE_SCREEN_PANEL);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR during GameVisuals initialization: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Font loadPixelFont(float size) {
        try {
            File fontFile = new File("res/pixelfont.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return customFont.deriveFont(size);

        } catch (IOException | FontFormatException e) {
            System.out.println("Could not load pixel font. Using default.");
            return new Font("Monospaced", Font.BOLD, (int)size);
        }
    }

    public void initializeCharacters() {
        try {
            availableCharacters.add(new Zor());
            availableCharacters.add(new Leo());
            availableCharacters.add(new Elara());
            availableCharacters.add(new Kai());
            availableCharacters.add(new Anya());
        } catch (Exception e) {
            System.err.println("Error initializing characters: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // card switching
    public void showCard(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }


    public JPanel createGameOverPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(100, 0, 0, 0));

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(titleFont.deriveFont(Font.BOLD, 80f));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backToTitleButton = new JButton("BACK TO TITLE");
        backToTitleButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        backToTitleButton.setBackground(new Color(50, 50, 50));
        backToTitleButton.setForeground(Color.WHITE);
        backToTitleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToTitleButton.addActionListener(e -> {
            window.dispose();
            Main.main(new String[]{}); // restart
        });

        panel.add(gameOverLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(backToTitleButton);
        return panel;
    }


    public JPanel createTitleScreenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("OUTBREAK", SwingConstants.CENTER);
        title.setFont(titleFont.deriveFont(Font.BOLD, 80f));
        title.setForeground(new Color(200, 0, 0));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JButton startButton = new JButton("START MISSION");
        startButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        startButton.setBackground(new Color(200, 0, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusPainted(false);
        startButton.setMaximumSize(new Dimension(300, 70));
        startButton.addActionListener(e -> showCard(CHARACTER_SELECT_PANEL)); // Use card switch utility

        JButton howToPlayButton = new JButton("HOW TO PLAY");
        howToPlayButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        howToPlayButton.setBackground(new Color(0, 100, 150));
        howToPlayButton.setForeground(Color.WHITE);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setMaximumSize(new Dimension(300, 70));
        howToPlayButton.addActionListener(e -> showCard(HOW_TO_PLAY_PANEL));

        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        quitButton.setBackground(new Color(50, 50, 50));
        quitButton.setForeground(Color.WHITE);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setMaximumSize(new Dimension(300, 70));
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(quitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }


    public JPanel createHowToPlayPanel() {
        // body of how to play
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("How to Play", SwingConstants.CENTER);
        title.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        title.setForeground(new Color(0, 100, 150));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        panel.add(title, BorderLayout.NORTH);

        JTextArea instructionsArea = new JTextArea(
                " SETUP \n\n" +
                        "Form Your Squad: Choose 3 unique characters.\n" +
                        "Manage Resources: Use your character skills wisely — they cost resources!\n\n" +
                        " GAME FLOW \n\n" +
                        "1. Fight: Complete battles in the current level. You may face up to two enemies per level.\n" +
                        "2. Decide: Choose a path wisely; one will lead you to the next level, and the other will lead you to the boss of the current level.\n" +
                        "3. Win: Defeat the level enemies to advance. Win all levels to fight the Boss!\n\n" +
                        " VICTORY CONDITION \n\n" +
                        "WIN: Defeat the enemy squad.\n" +
                        "LOSE: If all 3 of your squad members are defeated, it's Game Over!\n"

        );
        instructionsArea.setFont(normalFont.deriveFont(Font.PLAIN, 20f));
        instructionsArea.setForeground(new Color(50, 50, 50));
        instructionsArea.setBackground(new Color(240, 240, 240));
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setLineWrap(true);
        instructionsArea.setEditable(false);
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(instructionsArea, BorderLayout.CENTER);

        JButton backButton = new JButton("BACK TO TITLE");
        backButton.setFont(titleFont.deriveFont(Font.BOLD, 25f));
        backButton.setBackground(new Color(0, 100, 150));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showCard(TITLE_SCREEN_PANEL)); // call showcard to switch back using identifier

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        southPanel.add(backButton);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    public JPanel createBattlePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);

        enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setBackground(Color.BLACK);
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        locationLabel = new JLabel("Level 1: Abandoned Emergency Room", SwingConstants.CENTER);
        locationLabel.setFont(normalFont.deriveFont(Font.BOLD, 18f));
        locationLabel.setForeground(oceanBlue);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyPanel.add(locationLabel);

        enemyNameLabel = new JLabel("Enemy: ???");
        enemyNameLabel.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        enemyNameLabel.setForeground(Color.WHITE);
        enemyNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        enemyHPLabel = new JLabel("HP: ???");
        enemyHPLabel.setFont(normalFont.deriveFont(Font.BOLD, 20f));
        enemyHPLabel.setForeground(Color.RED);
        enemyHPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        enemyPanel.add(enemyNameLabel);
        enemyPanel.add(enemyHPLabel);

        panel.add(enemyPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        battleLogArea = new JTextArea(" ");
        battleLogArea.setFont(normalFont);
        battleLogArea.setForeground(Color.LIGHT_GRAY);
        battleLogArea.setBackground(Color.BLACK);
        battleLogArea.setEditable(false);
        battleLogArea.setLineWrap(true);
        battleLogArea.setWrapStyleWord(true); //
        JScrollPane scrollPane = new JScrollPane(battleLogArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        EmptyBorder logBorder = new EmptyBorder(20, 20, 20, 20);
        battleLogArea.setBorder(logBorder);

        centerPanel.add(scrollPane);

        playerStatusPanel = new JPanel();
        playerStatusPanel.setLayout(new BoxLayout(playerStatusPanel, BoxLayout.Y_AXIS));
        playerStatusPanel.setBackground(Color.BLACK);
        EmptyBorder playerStatusBorder = new EmptyBorder(10, 10, 10, 10); // for padding/margin
        playerStatusPanel.setBorder(playerStatusBorder);

        centerPanel.add(playerStatusPanel);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.setBackground(Color.BLACK);

        battleActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        battleActionPanel.setBackground(Color.BLACK);
        panel.add(battleActionPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createMissionCompletePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("MISSION SUCCESSFUL!");
        titleLabel.setFont(titleFont.deriveFont(Font.BOLD, 60f));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Floor " + (currentFloor) + " Cleared! Prepare for the next challenge.");
        subLabel.setFont(normalFont.deriveFont(Font.BOLD, 24f));
        subLabel.setForeground(Color.YELLOW);
        subLabel.setName("SUB_LABEL");
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String nextButtonText = (currentFloor == 4) ? "FINAL BOSS" : "FLOOR " + (currentFloor + 1);
        JButton nextFloorButton = new JButton(nextButtonText);
        nextFloorButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        nextFloorButton.setBackground(new Color(0, 150, 0));
        nextFloorButton.setForeground(Color.WHITE);
        nextFloorButton.setName("FLOOR_BUTTON");
        nextFloorButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextFloorButton.setPreferredSize(new Dimension(400, 70));
        nextFloorButton.addActionListener(e -> battleManager.progressionManager.newFloor()); // Call ProgressionManager

        centerContentPanel.add(titleLabel);
        centerContentPanel.add(Box.createVerticalStrut(20));
        centerContentPanel.add(subLabel);
        centerContentPanel.add(Box.createVerticalStrut(40));
        centerContentPanel.add(nextFloorButton);

        panel.add(centerContentPanel, BorderLayout.CENTER);

        return panel;
    }

    public JPanel createFinalVictoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 40));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setBackground(new Color(20, 20, 40));

        JLabel titleLabel = new JLabel("OUTBREAK ELIMINATED!", SwingConstants.CENTER);
        titleLabel.setFont(titleFont.deriveFont(Font.BOLD, 70f));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("You have defeated Dr. Alcaraz and secured the facility!", SwingConstants.CENTER);
        subLabel.setFont(normalFont.deriveFont(Font.BOLD, 30f));
        subLabel.setForeground(new Color(0, 255, 0));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton finishButton = new JButton("RETURN TO TITLE");
        finishButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        finishButton.setBackground(new Color(150, 0, 0));
        finishButton.setForeground(Color.WHITE);
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(400, 70));
        finishButton.setPreferredSize(new Dimension(400, 70));

        finishButton.addActionListener(e -> {
            window.dispose();
            Main.main(new String[]{}); // restart ulit
        });

        centerContentPanel.add(titleLabel);
        centerContentPanel.add(Box.createVerticalStrut(30));
        centerContentPanel.add(subLabel);
        centerContentPanel.add(Box.createVerticalStrut(50));
        centerContentPanel.add(finishButton);

        panel.add(centerContentPanel, BorderLayout.CENTER);

        return panel;
    }

    public JPanel createDirectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.BLACK);
        textPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));

        JLabel directionLabel = new JLabel("You have defeated the " + floorChoice + " enemy.", SwingConstants.CENTER);
        directionLabel.setFont(titleFont.deriveFont(Font.BOLD, 35f));
        directionLabel.setForeground(Color.GREEN);
        directionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel choicePromptLabel = new JLabel("Which direction will you proceed?", SwingConstants.CENTER);
        choicePromptLabel.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        choicePromptLabel.setForeground(Color.GREEN);
        choicePromptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(directionLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(choicePromptLabel);

        panel.add(textPanel, BorderLayout.NORTH);

        JPanel choicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        choicesPanel.setBackground(Color.BLACK);

        JButton choiceAButton = new JButton("EAST");
        choiceAButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceAButton.setBackground(new Color(40, 40, 100));
        choiceAButton.setForeground(Color.WHITE);
        choiceAButton.setPreferredSize(new Dimension(200, 80));
        choiceAButton.addActionListener(e -> battleManager.progressionManager.chooseDirection("A")); //Battle.java -> Floor.java -> choosedirection method
        choicesPanel.add(choiceAButton);

        JButton choiceBButton = new JButton("WEST");
        choiceBButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceBButton.setBackground(new Color(100, 40, 40));
        choiceBButton.setForeground(Color.WHITE);
        choiceBButton.setPreferredSize(new Dimension(200, 80));
        choiceBButton.addActionListener(e -> battleManager.progressionManager.chooseDirection("B"));
        choicesPanel.add(choiceBButton);

        panel.add(choicesPanel, BorderLayout.CENTER);
        return panel;
    }
}