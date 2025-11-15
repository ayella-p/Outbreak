import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


import game.core.Skill;
import game.core.Character;
import game.core.Enemy;
import game.core.Boss;
import game.characters.*;
import game.enemies.*;
import game.enemies.bosses.*;
public class Main {
    JFrame window;
    Container con;
    JPanel mainTitlePanel, charDisplayPanel, selectionButtonsPanel, detailsButtonPanel;
    JLabel mainTitleLabel, charNameLabel, charStatsLabel;
    JTextArea charBackstoryArea; 


    JPanel cardPanel;
    CardLayout cardLayout;
    final static String TITLE_SCREEN_PANEL = "TITLE_SCREEN";
    final static String HOW_TO_PLAY_PANEL = "HOW_TO_PLAY";
    final static String CHARACTER_SELECT_PANEL = "CHARACTER_SELECT";
    final static String BATTLE_PANEL = "BATTLE";
    final static String DIRECTIONAL_PANEL = "DIRECTION_CHOICE";
    final static String MISSION_COMPLETE_PANEL = "MISSION_COMPLETE";
    final static String FINAL_VICTORY_PANEL = "FINAL_VICTORY";
    final static String GAME_OVER_PANEL = "GAME_OVER";
    JPanel titleScreenPanel;
    JPanel howToPlayPanel;

    JPanel battlePanel, enemyPanel, battleActionPanel;
    JLabel enemyNameLabel, enemyHPLabel;
    JTextArea battleLogArea;

    JPanel playerStatusPanel;
    JPanel directionPanel;
    JLabel directionLabel;
    JLabel locationLabel;
    JPanel gameOverPanel;

    Font titleFont = new Font("Courier New", Font.BOLD, 50);
    Font normalFont = new Font("Arial", Font.PLAIN, 16);
    
    Color oceanBlue = new Color(0, 119, 190);

    List<Character> availableCharacters = new ArrayList<>();
    List<Character> playerParty = new ArrayList<>();
    int selectionCount = 0;
    int currentFloor = 1; 
    String floorChoice = "";

    
    final AtomicReference<Character> currentViewedCharacter = new AtomicReference<>(null);
    Character activeCharacter;
    Enemy currentEnemy;
    public static void main(String[] args){
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main();
                } catch (Exception e) {
                    System.err.println("Fatal error during application startup: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
}
    public Main() {
        initializeCharacters();

        window = new JFrame();
        window.setSize(1000, 700); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("OUTBREAK");
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(new BorderLayout());
        con = window.getContentPane();
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.BLACK);
        con.add(cardPanel, BorderLayout.CENTER);

        titleScreenPanel = createTitleScreenPanel();
        titleScreenPanel.setBackground(Color.BLACK);
        cardPanel.add(titleScreenPanel, TITLE_SCREEN_PANEL);

        howToPlayPanel = createHowToPlayPanel();
        howToPlayPanel.setBackground(Color.BLACK);
        cardPanel.add(howToPlayPanel, HOW_TO_PLAY_PANEL);

        JPanel charSelectContainer = createCharacterSelectContainer();
        charSelectContainer.setBackground(Color.BLACK);
        cardPanel.add(charSelectContainer, CHARACTER_SELECT_PANEL);

        battlePanel = createBattlePanel(); 
        battlePanel.setBackground(Color.BLACK);
        cardPanel.add(battlePanel, BATTLE_PANEL);

        directionPanel = createDirectionPanel();
        directionPanel.setBackground(Color.BLACK);
        cardPanel.add(directionPanel, DIRECTIONAL_PANEL);

        JPanel missionCompletePanel = createMissionCompletePanel();
        missionCompletePanel.setBackground(Color.BLACK);
        cardPanel.add(missionCompletePanel, MISSION_COMPLETE_PANEL);

        JPanel finalVictoryPanel = createFinalVictoryPanel(); 
        finalVictoryPanel.setBackground(Color.BLACK);
        cardPanel.add(finalVictoryPanel, FINAL_VICTORY_PANEL);
        
        gameOverPanel = createGameOverPanel();
        gameOverPanel.setBackground(Color.BLACK);
        cardPanel.add(gameOverPanel, GAME_OVER_PANEL);

        cardLayout.show(cardPanel, TITLE_SCREEN_PANEL);
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
            new Main(); 
        });
        
        panel.add(gameOverLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(backToTitleButton);
        
        return panel;
    }

    public JPanel createTitleScreenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel("OUTBREAK", SwingConstants.CENTER);
        title.setFont(titleFont.deriveFont(Font.BOLD, 80f));
        title.setForeground(new Color(200, 0, 0));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JButton startButton = new JButton("START MISSION");
        startButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        startButton.setBackground(new Color(0, 150, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusPainted(false);
        startButton.setMaximumSize(new Dimension(300, 70));
        startButton.addActionListener(e -> cardLayout.show(cardPanel, CHARACTER_SELECT_PANEL));

        JButton howToPlayButton = new JButton("HOW TO PLAY");
        howToPlayButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        howToPlayButton.setBackground(new Color(0, 100, 150));
        howToPlayButton.setForeground(Color.WHITE);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setMaximumSize(new Dimension(300, 70));
        howToPlayButton.addActionListener(e -> cardLayout.show(cardPanel, HOW_TO_PLAY_PANEL));

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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

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
        instructionsArea.setForeground(Color.LIGHT_GRAY);
        instructionsArea.setBackground(Color.DARK_GRAY);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setLineWrap(true);
        instructionsArea.setEditable(false);
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(instructionsArea, BorderLayout.CENTER);

        JButton backButton = new JButton("BACK TO TITLE");
        backButton.setFont(titleFont.deriveFont(Font.BOLD, 25f));
        backButton.setBackground(new Color(0, 100, 150));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, TITLE_SCREEN_PANEL));
        
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);
        southPanel.add(backButton);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    

    public JPanel createCharacterSelectContainer() {
        JPanel charSelectContainer = new JPanel(new BorderLayout());
        charSelectContainer.setBackground(new Color(240, 240, 240));

        
        mainTitlePanel = new JPanel();
        mainTitlePanel.setBackground(Color.BLACK);
        mainTitlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 2));
        mainTitleLabel = new JLabel("CHOOSE YOUR SQUAD (0/3)");
        mainTitleLabel.setForeground(Color.WHITE);
        mainTitleLabel.setFont(titleFont);
        mainTitlePanel.add(mainTitleLabel);
        charSelectContainer.add(mainTitlePanel, BorderLayout.NORTH);

       
        charDisplayPanel = new JPanel();
        charDisplayPanel.setLayout(new BoxLayout(charDisplayPanel, BoxLayout.Y_AXIS));
        charDisplayPanel.setBackground(Color.BLACK);
        charDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        charNameLabel = new JLabel(" ");
        charNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        charNameLabel.setForeground(Color.WHITE);
        charNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charStatsLabel = new JLabel(" ");
        charStatsLabel.setFont(normalFont);
        charStatsLabel.setForeground(Color.GREEN);
        charStatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charBackstoryArea = new JTextArea(" ");
        charBackstoryArea.setFont(normalFont);
        charBackstoryArea.setForeground(Color.LIGHT_GRAY);
        charBackstoryArea.setBackground(Color.DARK_GRAY);
        charBackstoryArea.setWrapStyleWord(true);
        charBackstoryArea.setLineWrap(true);
        charBackstoryArea.setEditable(false);
        charBackstoryArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        charBackstoryArea.setMaximumSize(new Dimension(500, 100)); 
        charBackstoryArea.setPreferredSize(new Dimension(500, 100)); 

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.setLayout(new GridBagLayout());
        textAreaWrapper.setBackground(Color.DARK_GRAY);
        textAreaWrapper.add(charBackstoryArea);
        textAreaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setBackground(Color.DARK_GRAY);
        detailsButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        charDisplayPanel.add(charNameLabel);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(charStatsLabel);
        charDisplayPanel.add(Box.createVerticalStrut(20));
        charDisplayPanel.add(textAreaWrapper);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(detailsButtonPanel); 

        charSelectContainer.add(charDisplayPanel, BorderLayout.CENTER);

       
        selectionButtonsPanel = new JPanel();
        selectionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        selectionButtonsPanel.setBackground(Color.DARK_GRAY);
        createCharacterSelectionButtons(); 
        charSelectContainer.add(selectionButtonsPanel, BorderLayout.SOUTH);
        
        return charSelectContainer;
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
        battleLogArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(battleLogArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        EmptyBorder logBorder = new EmptyBorder(20, 20, 20, 20);
        battleLogArea.setBorder(logBorder);

        centerPanel.add(scrollPane);

        playerStatusPanel = new JPanel();
        playerStatusPanel.setLayout(new BoxLayout(playerStatusPanel, BoxLayout.Y_AXIS));
        playerStatusPanel.setBackground(Color.BLACK);
        EmptyBorder playerStatusBorder = new EmptyBorder(10, 10, 10, 10);
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
        nextFloorButton.addActionListener(e -> newFloor());

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

        // Big, celebratory title
        JLabel titleLabel = new JLabel("OUTBREAK ELIMINATED!", SwingConstants.CENTER);
        titleLabel.setFont(titleFont.deriveFont(Font.BOLD, 70f));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("You have defeated Dr. Alcaraz and secured the facility!", SwingConstants.CENTER);
        subLabel.setFont(normalFont.deriveFont(Font.BOLD, 30f));
        subLabel.setForeground(new Color(0, 255, 0)); // Bright Green
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton finishButton = new JButton("RETURN TO TITLE");
        finishButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        finishButton.setBackground(new Color(150, 0, 0)); // Red button
        finishButton.setForeground(Color.WHITE);
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finishButton.setMaximumSize(new Dimension(400, 70));
        finishButton.setPreferredSize(new Dimension(400, 70));

        finishButton.addActionListener(e -> {
            window.dispose();
            new Main(); 
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

        directionLabel = new JLabel("You have defeated the " + floorChoice + " enemy.", SwingConstants.CENTER);
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
        choiceAButton.addActionListener(e -> chooseDirection("A")); 
        choicesPanel.add(choiceAButton);

        JButton choiceBButton = new JButton("WEST");
        choiceBButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceBButton.setBackground(new Color(100, 40, 40)); 
        choiceBButton.setForeground(Color.WHITE);
        choiceBButton.setPreferredSize(new Dimension(200, 80));
        choiceBButton.addActionListener(e -> chooseDirection("B")); 
        choicesPanel.add(choiceBButton);

        panel.add(choicesPanel, BorderLayout.CENTER);
        return panel;
    }


    public void displayCharacterDetails(Character c, JButton sourceButton) {
        charNameLabel.setText(c.name.toUpperCase());
        charStatsLabel.setText("HP: " + c.maxHP + " | " + c.resourceName + ": " + c.maxResource);
        charBackstoryArea.setText(c.backstory);
        currentViewedCharacter.set(c); 
        
        updateDetailsButtonPanel(c, sourceButton);
    }

    void updateDetailsButtonPanel(Character c, JButton sourceButton) {
        detailsButtonPanel.removeAll();
        JButton selectButton = new JButton("SELECT " + c.name.toUpperCase());
        selectButton.setFont(normalFont.deriveFont(Font.BOLD));

      if (selectionCount < 3) {
            selectButton.setForeground(Color.BLACK);
            
            selectButton.addActionListener(e -> selectCharacter(sourceButton, c));
            detailsButtonPanel.add(selectButton);
            
        } else {
             detailsButtonPanel.removeAll();
        }

    }

    public void initializeCharacters() {
        availableCharacters.add(new Zor());
        availableCharacters.add(new Leo());
        availableCharacters.add(new Elara());
        availableCharacters.add(new Kai());
        availableCharacters.add(new Anya());
    }

    public void createCharacterSelectionButtons() {
        JButton startButton = new JButton("START MISSION");
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        startButton.setBackground(new Color(150, 0, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setEnabled(false);
        startButton.addActionListener(e -> startGame(new Carrier()));
        startButton.setName("START_BUTTON"); 
        selectionButtonsPanel.add(startButton);

        for (Character character : availableCharacters) {
            JButton charButton = new JButton(character.name);
            charButton.setFont(normalFont);
            charButton.setBackground(new Color(60, 60, 60));
            charButton.setForeground(Color.WHITE);
            charButton.setFocusPainted(false);
            
            charButton.addActionListener(e -> {
                displayCharacterDetails(character, charButton);
            });
            
            selectionButtonsPanel.add(charButton, selectionButtonsPanel.getComponentCount() - 1); 
        }
    }
    
    public void selectCharacter(JButton button, Character c) {
        if (selectionCount < 3 && !playerParty.contains(c)) {
            playerParty.add(c);
            selectionCount++;
            mainTitleLabel.setText("CHOOSE YOUR SQUAD (" + selectionCount + "/3)");
           
            button.setEnabled(false);
            button.setBackground(Color.BLACK);
            button.setForeground(Color.GREEN);
  
            displayCharacterDetails(c, button);

            if (selectionCount == 3) {
                for (Component comp : selectionButtonsPanel.getComponents()) {
                   
                    if (comp instanceof JButton) {
                        JButton btn = (JButton) comp;
                        
                        if ("START_BUTTON".equals(btn.getName())) { 
                            btn.setEnabled(true);
                            btn.setBackground(new Color(0, 150, 0));
                            break; 
                        }
                    }
                }
            }
        }
    }
    
    public void startGame(Enemy enemy) {
    	List<Character> aliveParty = new ArrayList<>();
        for (Character c : playerParty) {
            if (c.currentHP > 0) { 
                aliveParty.add(c);
            }
        }
        playerParty.clear();
        playerParty.addAll(aliveParty);
    
    if (playerParty.isEmpty()) {
        endBattle(false);
             return;
        }

        if (enemy instanceof DrAlcaraz) {
            mainTitleLabel.setText("OUTBREAK - FINAL BOSS: DR. ALCARAZ");
            currentFloor = 4; 
        } else if (enemy instanceof Venomshade) {
             mainTitleLabel.setText("OUTBREAK - FLOOR 3 BOSS: VENOMSHADE");
        } else {
             mainTitleLabel.setText("OUTBREAK - FLOOR " + currentFloor);
        }

        mainTitlePanel.setVisible(false); 
        selectionButtonsPanel.setVisible(false); 
        cardLayout.show(cardPanel, BATTLE_PANEL);

        currentEnemy = enemy; 
        Color oceanBlue = new Color(0, 119, 190);

        if (currentFloor == 1) {
            locationLabel.setText("Level 1: Abandoned Emergency Room");
            locationLabel.setForeground(oceanBlue); 
        } else if (currentFloor == 2) {
            locationLabel.setText("Level 2: Infectious ICU");
            locationLabel.setForeground(oceanBlue); 
        } else if (currentFloor == 3) {
            locationLabel.setText("Final Level: Underground Research Lab");
            locationLabel.setForeground(oceanBlue); 
        }

        updateBattleUI();
        updatePlayerStatusUI(); 

        String enemyType = (currentEnemy instanceof Boss) ? "Boss" : "Enemy";

        if (currentEnemy instanceof DrAlcaraz) {
             battleLogArea.setText("A " + currentEnemy.name + " approaches! (FINAL BOSS)\n");
        } else {
             battleLogArea.setText("A " + currentEnemy.name + " approaches! (" + enemyType + " - Floor " + currentFloor + ")\n");
        }
    }

    void updateBattleUI() {
        enemyNameLabel.setText("Enemy: " + currentEnemy.name.toUpperCase());
        enemyHPLabel.setText("HP: " + currentEnemy.currentHP + " / " + currentEnemy.maxHP);
    }

    void updatePlayerStatusUI() {
        playerStatusPanel.removeAll();
        
        for (Character character : playerParty) {
            JPanel charPanel = new JPanel();
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.Y_AXIS));
            charPanel.setBackground(Color.BLACK);
            charPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            charPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel nameLabel = new JLabel(character.name.toUpperCase()); // hero name 
            nameLabel.setFont(normalFont.deriveFont(Font.BOLD));
            nameLabel.setForeground(Color.CYAN); 

            JLabel hpLabel = new JLabel("HP: " + character.currentHP + " / " + character.maxHP); // hero HP
            hpLabel.setFont(normalFont);
            hpLabel.setForeground(Color.GREEN);
            if (character.currentHP <= character.maxHP * 0.25 && character.currentHP > 0) { 
                 hpLabel.setForeground(Color.RED); // warning 
            } else if (character.currentHP <= 0 || character.currentResource <= 0) {
                 hpLabel.setText("DEFEATED");
                 hpLabel.setForeground(Color.GRAY); // defeated 
            }
            
            JLabel resourceLabel = new JLabel(character.resourceName + ": " + character.currentResource + " / " + character.maxResource);
            resourceLabel.setFont(normalFont); // hewr
            resourceLabel.setForeground(Color.YELLOW);

            charPanel.add(nameLabel);
            charPanel.add(hpLabel);
            charPanel.add(resourceLabel);

            
            playerStatusPanel.add(charPanel);
            playerStatusPanel.add(Box.createVerticalStrut(5));  
        }
        
        JButton switchButton = new JButton("CHOOSE CHARACTER");
        switchButton.setFont(normalFont.deriveFont(Font.BOLD));
        switchButton.setBackground(Color.BLACK);
        switchButton.setForeground(Color.WHITE);
        switchButton.addActionListener(e -> {
            battleLogArea.append("\nChoose a character to switch to:\n");
            for (Character character : playerParty) {
                if (character != activeCharacter && character.currentHP > 0) { 
                    JButton charSwitchButton = new JButton(character.name);
                    charSwitchButton.setFont(normalFont);
                    charSwitchButton.setBackground(new Color(60, 60, 60));
                    charSwitchButton.setForeground(Color.WHITE);
                    charSwitchButton.setFocusPainted(false);
                    charSwitchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                    charSwitchButton.setMaximumSize(new Dimension(100, 50));
                    
                    charSwitchButton.addActionListener(ev -> {
                        switchToCharacterTurn(character);
                    });
                    
                    playerStatusPanel.add(charSwitchButton); 
                    playerStatusPanel.revalidate();
                    playerStatusPanel.repaint();
                }
            }
        });


        

        playerStatusPanel.add(switchButton);
        playerStatusPanel.revalidate();
        playerStatusPanel.repaint();
    }
    
    
    void setupCharacterActionButtons(Character character) {
        battleActionPanel.removeAll();

        try {
        boolean isDefeated = character == null || character.currentHP <= 0 || character.currentResource <= 2;
        if (!isDefeated) {
                JLabel turnLabel = new JLabel(character.name.toUpperCase() + "'s Turn");
                turnLabel.setForeground(Color.YELLOW);
                turnLabel.setFont(normalFont.deriveFont(Font.BOLD, 18f));
                battleActionPanel.add(turnLabel);

                for (Skill skill : character.skills) {
                    JButton skillButton = new JButton(skill.name + " (" + skill.cost + " " + character.resourceName + ")");
                    skillButton.setFont(normalFont);
                    skillButton.setEnabled(character.currentResource >= skill.cost); 

                    skillButton.addActionListener(e -> performSkill(character, skill)); 
                    battleActionPanel.add(skillButton);
                }
            } else {
                JLabel promptLabel;
                if(character == null){
                    promptLabel = new JLabel("Select a character to take action.");
                } else if (character.currentHP <= 0) {
                    promptLabel = new JLabel(character.name + " is knocked out! Choose another character.");
                } else {
                    promptLabel = new JLabel(character.name + " is low on " + character.resourceName + "! Choose another character.");
                }
                promptLabel.setForeground(Color.RED);
                promptLabel.setFont(normalFont.deriveFont(Font.BOLD, 18f));
                battleActionPanel.add(promptLabel);
        }
        } catch (Exception e) {
            System.err.println("Error setting up action buttons: " + e.getMessage());
        }

    
        battleActionPanel.revalidate();
        battleActionPanel.repaint();
    }
    
    void performSkill(Character character, Skill skill) {
       
        if (character.currentResource < skill.cost) {
            battleLogArea.append(character.name + " tried to use " + skill.name + " but is low on " + character.resourceName + "!\n");
            return;
         }
    
        if (character.currentHP <= 0) {
            battleLogArea.append(character.name + " is knocked out and cannot act!\n");
            return;
        }

        character.currentResource -= skill.cost;

        int damageDealt = skill.damage;
        currentEnemy.takeDamage(damageDealt);
    
        battleLogArea.append(character.name + " uses " + skill.name + ", dealing " + damageDealt + " damage to " + currentEnemy.name + ".\n");
    
        updateBattleUI();
        updatePlayerStatusUI(); 

        if (!currentEnemy.isAlive()) {
            battleLogArea.append("\n" + currentEnemy.name + " has been defeated!\n");
            endBattle(true);
                return;
        }
        enemyTurn();
         List<Character> aliveCharacters = new ArrayList<>();
         for (Character c : playerParty) {
            if (c.currentHP > 0 && c.currentResource > 2) {
                aliveCharacters.add(c);
            }
        }
    

    if (aliveCharacters.isEmpty()) {
        battleLogArea.append("\nAll remaining squad members are unable to fight!\n");
        endBattle(false);
        return;
    }
    if (activeCharacter != null && activeCharacter.currentHP <= 0) {
         this.activeCharacter = null;
    } 
        setupCharacterActionButtons(this.activeCharacter);

    }

    void switchToCharacterTurn(Character nextCharacter) {
        playerStatusPanel.removeAll();
        updatePlayerStatusUI();
        this.activeCharacter = nextCharacter; 
        battleLogArea.append("\n" + activeCharacter.name + "'s turn to act.\n");
        setupCharacterActionButtons(this.activeCharacter);
}
    
    void enemyTurn() {
        List<Character> aliveCharacters = new ArrayList<>();
        for (Character c : playerParty) {
            if (c.currentHP > 0) {
                aliveCharacters.add(c);
            }
        }

        if (aliveCharacters.isEmpty()) {
            return;
        }
        Random rand = new Random();
        Character target = aliveCharacters.get(rand.nextInt(aliveCharacters.size())); 
        currentEnemy.attack(target);
        int damage = currentEnemy instanceof Boss ? currentEnemy.damage + 5 : currentEnemy.damage;
        battleLogArea.append(currentEnemy.name + " attacks " + target.name + ", dealing " + damage + " damage.\n");
        
        if (target.currentHP <= 0) {
            battleLogArea.append(target.name + " is knocked out!\n");
        }

         updatePlayerStatusUI();

         List<Character> postAttackAliveCharacters = new ArrayList<>();
         for (Character c : playerParty) {
            if (c.currentHP > 0) { 
                postAttackAliveCharacters.add(c);
            }
        }

        if (postAttackAliveCharacters.isEmpty()) {
            endBattle(false);
            return;
        }
        if (activeCharacter != null && activeCharacter.currentHP <= 0) {
            this.activeCharacter = null; // Force user to choose next character
        } 
        setupCharacterActionButtons(this.activeCharacter);
    }

    void endBattle(boolean win) {
        if (win) {
            mainTitlePanel.setVisible(true); 
            mainTitleLabel.setText("MISSION SUCCESSFUL!");
            
            int completedFloor = currentFloor; 
            if(completedFloor == 1){
                floorChoice = "First Floor";
            } else if (completedFloor == 2){
                floorChoice = "Second Floor";
            } else if (completedFloor == 3){
                floorChoice = "Third Floor";
            } else if (completedFloor == 4){
                floorChoice = "Final Floor";
            }

            if (currentEnemy instanceof Boss || currentEnemy instanceof Enemy) {
                for (Character c : playerParty) {
                    if (c.currentHP > 0) {
                        int healAmount = (int) (c.maxHP * 0.25);
                        int resourceRestore = (int) (c.maxResource * 0.25);

                        c.currentHP += healAmount;
                        if (c.currentHP > c.maxHP) {
                        c.currentHP = c.maxHP;
                        }

                        c.currentResource += resourceRestore;
                        if (c.currentResource > c.maxResource) {
                        c.currentResource = c.maxResource;
                        }
                    }
                }
                 if (currentEnemy instanceof DrAlcaraz) {
                    missionComplete();
                    return;
                 } 
                 
                 if (currentEnemy instanceof Venomshade) {
                    currentFloor = 4;
                    battleLogArea.append("\nVenomshade defeated! The true threat, Dr. Alcaraz, emerges!\n");
                    startGame(new DrAlcaraz()); 
                    return; 
                }
                 
                 } 
                 if (currentEnemy instanceof Boss) {
                    cardLayout.show(cardPanel, MISSION_COMPLETE_PANEL); // Standard Floor Cleared screen
                    return; 
                }

                if (currentEnemy instanceof Enemy) {
                cardLayout.show(cardPanel, DIRECTIONAL_PANEL);
                try {
                    JPanel textPanel = (JPanel)((BorderLayout)directionPanel.getLayout()).getLayoutComponent(directionPanel, BorderLayout.NORTH);
                    if (textPanel != null && textPanel.getComponentCount() > 0 && textPanel.getComponent(0) instanceof JLabel) {
                        ((JLabel)textPanel.getComponent(0)).setText("You have defeated the " + floorChoice + " enemy.");
                    }
                    JPanel choicesPanel = (JPanel) directionPanel.getComponent(1);
                    for (Component comp : choicesPanel.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setEnabled(true);
                        }
                    }
                } catch (ClassCastException | NullPointerException e) {
                    System.err.println("Error updating Direction Panel components: " + e.getMessage());
                    
                }
            } 
        } else {
            cardLayout.show(cardPanel, GAME_OVER_PANEL);
        }
    }

    public void handleWestHealing() {
        for (Character character : playerParty) {
            character.currentHP = character.maxHP;
            character.currentResource = character.maxResource;
        }
        updatePlayerStatusUI();
        directionPanel.removeAll();
        directionPanel.setLayout(new BorderLayout());

        JPanel messageContainer = new JPanel(new GridLayout(3, 1));
        messageContainer.setBackground(Color.BLACK);
        messageContainer.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        JLabel titleLabel = new JLabel("HEALING INJECTION RECEIVED", SwingConstants.CENTER);
        titleLabel.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        titleLabel.setForeground(Color.YELLOW);

        JLabel messageLabel = new JLabel("HP and Resources are fully restored!", SwingConstants.CENTER);
        messageLabel.setFont(normalFont.deriveFont(Font.BOLD, 20f));
        messageLabel.setForeground(Color.GREEN);

        messageContainer.add(titleLabel);
        messageContainer.add(Box.createVerticalStrut(20)); 
        messageContainer.add(messageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        buttonPanel.setBackground(Color.BLACK);

        JButton nextFloorButton = new JButton("NEXT FLOOR");
        nextFloorButton.setFont(titleFont.deriveFont(Font.BOLD, 30f));
        nextFloorButton.setBackground(new Color(0, 150, 0));
        nextFloorButton.setForeground(Color.WHITE);
        nextFloorButton.setPreferredSize(new Dimension(400, 70));
        nextFloorButton.setFocusPainted(false);

        nextFloorButton.addActionListener(e -> startNextFloorTransition());

        if (currentFloor == 3) {
             nextFloorButton.setText("FINAL CONFRONTATION");
             for (java.awt.event.ActionListener al : nextFloorButton.getActionListeners()) {
                 nextFloorButton.removeActionListener(al);
             }
             nextFloorButton.addActionListener(e -> {
                 currentFloor = 4;
                 startGame(new DrAlcaraz()); 
             });
        }
        buttonPanel.add(nextFloorButton);
        directionPanel.add(messageContainer, BorderLayout.CENTER);
        directionPanel.add(buttonPanel, BorderLayout.SOUTH);
        directionPanel.revalidate();
        directionPanel.repaint();
    }

    public void startNextFloorTransition() {
       
        directionPanel.removeAll();
        directionPanel.setLayout(new BorderLayout());
        newFloor();
    }

     public void newFloor() {
        currentFloor++;

        cardPanel.remove(directionPanel);
        directionPanel = createDirectionPanel();
        cardPanel.add(directionPanel, DIRECTIONAL_PANEL);

        Enemy nextEnemy = null;

        if(currentFloor > 4){
            missionComplete();
            return;
        }
         if (currentFloor == 2) {
            // Floor 2: Start with Howler
            nextEnemy = new Howler();
            mainTitleLabel.setText("OUTBREAK - FLOOR 2");
        } else if (currentFloor == 3) {
            nextEnemy = new Carrier();
            mainTitleLabel.setText("OUTBREAK - FLOOR 3");
        } 

       if(nextEnemy != null){
           startGame(nextEnemy);
       }
    }

    public void missionComplete() {
        cardLayout.show(cardPanel, FINAL_VICTORY_PANEL);
    }
    
    public void chooseDirection(String choice) {
        JPanel choicesPanel = (JPanel) directionPanel.getComponent(1);

        for (Component comp : choicesPanel.getComponents()) {
            comp.setEnabled(false);
        }

        if (choice.equals("A")) { 
            Enemy boss = null;
            if (currentFloor == 1) {
                directionLabel.setText("You proceed EAST and encounter the Floor 1 Boss: IronMaw!");
                boss = new IronMaw(); 
            } else if (currentFloor == 2) {
                directionLabel.setText("You proceed EAST and encounter the Floor 2 Boss: Boneclaw!");
                boss = new Boneclaw();
            } else if (currentFloor == 3) {
                directionLabel.setText("You proceed EAST and encounter the Floor 3 Boss: Venomshade!");
                boss = new Venomshade(); 
            }

            if (boss != null) {
                startGame(boss);
            }
        } else if (choice.equals("B")) { 
            directionLabel.setText("You cautiously proceed WEST and discover a hidden supply drop...");
            handleWestHealing();
        } 
    }
}

   




