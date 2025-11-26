package game.management;
import game.management.panel.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;
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


    public JPanel battlePanel, enemyPanel, battleActionPanel;
    public JLabel enemyNameLabel, enemyHPLabel;
    public JTextArea battleLogArea;
    public JPanel playerStatusPanel;
    public JPanel directionPanel;
    public JLabel locationLabel;
    public JPanel gameOverPanel;

    // card panel identifiers
    public final static String INTRO_STORY_PANEL = "INTRO_STORY";
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
    public Battle battleManager;


    public GameVisuals() {
        try {
            titleFont = loadPixelFont(50f);
            normalFont = loadPixelFont(20f); // this = referencing to gamevisuals gui
            this.battleManager = new Battle(this, new Carrier()); // first floor enemy carrier

            initializeCharacters();

            //window
            window = new JFrame();
            window.setSize(1035, 725);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setTitle("OUTBREAK");
            window.setLayout(new BorderLayout());
            con = window.getContentPane();

            BackgroundPanel bgPanel = new BackgroundPanel("res/bg.png");
            bgPanel.setLayout(new GridBagLayout());
            window.setContentPane(bgPanel);
            con = window.getContentPane();

            cardLayout = new CardLayout();
            cardPanel = new TranslucentPanel();
            cardPanel.setLayout(cardLayout);
            cardPanel.setPreferredSize(new Dimension(950, 650));
            cardPanel.setBorder(new LineBorder(new Color(255, 255, 255, 100), 2));
            bgPanel.add(cardPanel);

            cardPanel.add(new IntroStoryPanel(this), INTRO_STORY_PANEL);
            cardPanel.add(new TitleScreenPanel(this), TITLE_SCREEN_PANEL);
            cardPanel.add(new HowToPlayPanel(this), HOW_TO_PLAY_PANEL);
            cardPanel.add(new CharacterSelectionPanel(this), CHARACTER_SELECT_PANEL);
            battlePanel = new BattlePanel(this);
            cardPanel.add(battlePanel, BATTLE_PANEL);
            directionPanel = new DirectionPanel();
            cardPanel.add(directionPanel, DIRECTIONAL_PANEL);
            cardPanel.add(new MissionCompletePanel(this), MISSION_COMPLETE_PANEL);
            cardPanel.add(new FinalVictoryPanel(this), FINAL_VICTORY_PANEL);
            gameOverPanel = new GameOverPanel(this);
            cardPanel.add(gameOverPanel, GAME_OVER_PANEL);

            // start screen
            cardLayout.show(cardPanel, INTRO_STORY_PANEL);
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
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
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

}