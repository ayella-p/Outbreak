import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

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

    JPanel titleScreenPanel;
    JPanel howToPlayPanel;

    JPanel battlePanel, battleInfoPanel, enemyPanel, battleActionPanel;
    JLabel enemyNameLabel, enemyHPLabel;
    JTextArea battleLogArea;

    JPanel playerStatusPanel;
    JPanel directionPanel;
    JLabel directionLabel;

    Font titleFont = new Font("Courier New", Font.BOLD, 50);
    Font normalFont = new Font("Arial", Font.PLAIN, 16);
    Font smallFont = new Font("Arial", Font.ITALIC, 12);
    
    List<Character> availableCharacters = new ArrayList<>();
    List<Character> playerParty = new ArrayList<>();
    int selectionCount = 0;
    
    private AtomicReference<Character> currentViewedCharacter = new AtomicReference<>(null);
    Character activeCharacter;
    Enemy currentEnemy;
    public static void main(String[] args){
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
    public Main() {
        initializeCharacters();

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

        titleScreenPanel = createTitleScreenPanel();
        cardPanel.add(titleScreenPanel, TITLE_SCREEN_PANEL);

        howToPlayPanel = createHowToPlayPanel();
        cardPanel.add(howToPlayPanel, HOW_TO_PLAY_PANEL);

        JPanel charSelectContainer = createCharacterSelectContainer();
        cardPanel.add(charSelectContainer, CHARACTER_SELECT_PANEL);

        battlePanel = createBattlePanel(); 
        cardPanel.add(battlePanel, BATTLE_PANEL);

        directionPanel = createDirectionPanel();
        cardPanel.add(directionPanel, DIRECTIONAL_PANEL);


        cardLayout.show(cardPanel, TITLE_SCREEN_PANEL);

        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("How to Play", SwingConstants.CENTER);
        title.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        title.setForeground(new Color(0, 100, 150));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        panel.add(title, BorderLayout.NORTH);

        JTextArea instructionsArea = new JTextArea(
                "1. Choose Your Squad: Select 3 characters to form your team. Each character has unique stats and skills.\n\n" +
                "2. Combat: You will face enemies in turn-based combat. Your goal is to defeat the enemy without losing your entire squad.\n\n" +
                "3. Actions: During a character's turn, click a skill button to perform an action. Skills cost resources, so manage them wisely!\n\n" +
                "4. Enemy Turn: After your character acts, the enemy will counter-attack one of your squad members.\n\n" +
                "5. Victory/Defeat: Defeat the enemy to advance to the next stage. If all your squad members are defeated, it's game over!\n\n"
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
        backButton.addActionListener(e -> cardLayout.show(cardPanel, TITLE_SCREEN_PANEL));
        
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        southPanel.add(backButton);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    

    public JPanel createCharacterSelectContainer() {
        JPanel charSelectContainer = new JPanel(new BorderLayout());
        charSelectContainer.setBackground(new Color(240, 240, 240));

        
        mainTitlePanel = new JPanel();
        mainTitlePanel.setBackground(new Color(240, 240, 240));
        mainTitlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 2));
        mainTitleLabel = new JLabel("CHOOSE YOUR SQUAD (0/3)");
        mainTitleLabel.setForeground(new Color(50, 50, 50));
        mainTitleLabel.setFont(titleFont);
        mainTitlePanel.add(mainTitleLabel);
        charSelectContainer.add(mainTitlePanel, BorderLayout.NORTH);

       
        charDisplayPanel = new JPanel();
        charDisplayPanel.setLayout(new BoxLayout(charDisplayPanel, BoxLayout.Y_AXIS));
        charDisplayPanel.setBackground(new Color(240, 240, 240));
        charDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        charNameLabel = new JLabel(" ");
        charNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        charNameLabel.setForeground(new Color(50, 50, 50));
        charNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charStatsLabel = new JLabel(" ");
        charStatsLabel.setFont(normalFont);
        charStatsLabel.setForeground(new Color(50, 50, 50));
        charStatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charBackstoryArea = new JTextArea(" ");
        charBackstoryArea.setFont(normalFont);
        charBackstoryArea.setForeground(new Color(50, 50, 50));
        charBackstoryArea.setBackground(new Color(240, 240, 240));
        charBackstoryArea.setWrapStyleWord(true);
        charBackstoryArea.setLineWrap(true);
        charBackstoryArea.setEditable(false);
        charBackstoryArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        charBackstoryArea.setMaximumSize(new Dimension(500, 100)); 
        charBackstoryArea.setPreferredSize(new Dimension(500, 100)); 

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.setLayout(new GridBagLayout());
        textAreaWrapper.setBackground(new Color(240, 240, 240));
        textAreaWrapper.add(charBackstoryArea);
        textAreaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setBackground(new Color(240, 240, 240));
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
        selectionButtonsPanel.setBackground(new Color(240, 240, 240));
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


    public JPanel createDirectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        
        
        directionLabel = new JLabel("You've now faced two direction choose one.", SwingConstants.CENTER);
        directionLabel.setFont(titleFont);
        directionLabel.setForeground(Color.GREEN);
        panel.add(directionLabel, BorderLayout.NORTH);

        JPanel choicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        choicesPanel.setBackground(Color.BLACK);

        JButton choiceAButton = new JButton("EAST");
        choiceAButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceAButton.setBackground(new Color(40, 40, 100)); 
        choiceAButton.setForeground(Color.WHITE);
        choiceAButton.addActionListener(e -> chooseDirection("A")); 
        choicesPanel.add(choiceAButton);

        JButton choiceBButton = new JButton("WEST");
        choiceBButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        choiceBButton.setBackground(new Color(100, 40, 40)); 
        choiceBButton.setForeground(Color.WHITE);
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
    	mainTitlePanel.setVisible(false); 
        selectionButtonsPanel.setVisible(false); 
        cardLayout.show(cardPanel, BATTLE_PANEL);

        currentEnemy = enemy; 
    
        updateBattleUI();
        updatePlayerStatusUI(); 
    
    if (!playerParty.isEmpty()) {
        battleLogArea.setText("A " + currentEnemy.name + " approaches!\n\n");
        
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

        boolean isDefeated = character.currentHP <= 0 || character.currentResource <= 10;

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
        JLabel promptLabel = new JLabel(character.name.toUpperCase() + " is defeated. Choose next squad member");
        promptLabel.setForeground(Color.RED);
        promptLabel.setFont(normalFont.deriveFont(Font.BOLD, 18f));
        battleActionPanel.add(promptLabel);
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
        setupCharacterActionButtons(this.activeCharacter);
        int deadCount = 0;
        for (Character c : playerParty) {
            if (c.currentHP <= 0 || c.currentResource <= c.maxResource * 0.25) {
                deadCount++;
            }
        }
        if (deadCount == playerParty.size()) {
            battleLogArea.append("\nAll squad members have been defeated!\n");
            endBattle(false);
            return;
        }

    }

    void switchToCharacterTurn(Character nextCharacter) {
    
        this.activeCharacter = nextCharacter; 
        battleLogArea.append("\n" + activeCharacter.name + "'s turn to act.\n");
        setupCharacterActionButtons(this.activeCharacter);
}
    
    void enemyTurn() {
        Random rand = new Random();
        Character target = playerParty.get(rand.nextInt(playerParty.size())); 
        currentEnemy.attack(target);
        
        battleLogArea.append(currentEnemy.name + " attacks " + target.name + ", dealing " + currentEnemy.damage + " damage.\n");
        
        if (target.currentHP <= 0) {
            battleLogArea.append(target.name + " is knocked out!\n");
        }

        setupCharacterActionButtons(playerParty.get(0)); 
    }

    void endBattle(boolean win) {
        if (win) {
            cardLayout.show(cardPanel, DIRECTIONAL_PANEL);
            mainTitlePanel.setVisible(true); 
            mainTitleLabel.setText("MISSION SUCCESSFUL!");
        } else {
            battlePanel.setVisible(false);
            JPanel gameOverPanel = new JPanel();
            gameOverPanel.setBackground(Color.BLACK);
            JLabel gameOverLabel = new JLabel("GAME OVER");
            gameOverLabel.setFont(titleFont);
            gameOverLabel.setForeground(Color.RED);
            gameOverPanel.add(gameOverLabel);
            con.add(gameOverPanel, BorderLayout.CENTER);
            
        }
    }
    
    public void chooseDirection(String choice) {
        if (choice.equals("Health")) {
            directionLabel.setText("Healed up! Preparing for next encounter...");
        } else if (choice.equals("Boss")) {
            directionLabel.setText("Proceeding to Boss Station! Good luck!");
        }
        
        JPanel choicesPanel = (JPanel) directionPanel.getComponent(1);  
        choicesPanel.setVisible(false);
    }

}