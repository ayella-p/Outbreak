import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;
import java.util.Scanner;

public class Main {
    JFrame window;
    Container con;
    JPanel mainTitlePanel, charDisplayPanel, selectionButtonsPanel, detailsButtonPanel;
    JLabel mainTitleLabel, charNameLabel, charStatsLabel;
    JTextArea charBackstoryArea; 

    Font titleFont = new Font("Times New Roman", Font.BOLD, 50);
    Font normalFont = new Font("Arial", Font.PLAIN, 16);
    Font smallFont = new Font("Arial", Font.ITALIC, 12);
    
    List<Character> availableCharacters = new ArrayList<>();
    List<Character> playerParty = new ArrayList<>();
    int selectionCount = 0;
    
    private AtomicReference<Character> currentViewedCharacter = new AtomicReference<>(null);

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
        window.setTitle("OUTBREAK: Character Selection");
        window.getContentPane().setBackground(Color.DARK_GRAY);
        window.setLayout(new BorderLayout());
        con = window.getContentPane();
        
        mainTitlePanel = new JPanel();
        mainTitlePanel.setBackground(Color.BLACK);
        mainTitlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        mainTitleLabel = new JLabel("CHOOSE YOUR SQUAD (0/3)");
        mainTitleLabel.setForeground(Color.WHITE);
        mainTitleLabel.setFont(titleFont);
        mainTitlePanel.add(mainTitleLabel);
        
        con.add(mainTitlePanel, BorderLayout.NORTH);

        charDisplayPanel = new JPanel();
        charDisplayPanel.setLayout(new BoxLayout(charDisplayPanel, BoxLayout.Y_AXIS));
        charDisplayPanel.setBackground(new Color(30, 30, 30));
        charDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        charNameLabel = new JLabel(" ");
        charNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        charNameLabel.setForeground(Color.RED);
        charNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charStatsLabel = new JLabel(" ");
        charStatsLabel.setFont(normalFont);
        charStatsLabel.setForeground(Color.LIGHT_GRAY);
        charStatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charBackstoryArea = new JTextArea(" ");
        charBackstoryArea.setFont(normalFont);
        charBackstoryArea.setForeground(Color.LIGHT_GRAY);
        charBackstoryArea.setBackground(new Color(30, 30, 30));
        
        charBackstoryArea.setWrapStyleWord(true);
        charBackstoryArea.setLineWrap(true);
        charBackstoryArea.setEditable(false);
        charBackstoryArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        charBackstoryArea.setMaximumSize(new Dimension(500, 100)); 
        charBackstoryArea.setPreferredSize(new Dimension(500, 100)); 

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.setLayout(new GridBagLayout());
        textAreaWrapper.setBackground(new Color(30, 30, 30));
        textAreaWrapper.add(charBackstoryArea);
        textAreaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setBackground(new Color(30, 30, 30));
        detailsButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));


        charDisplayPanel.add(charNameLabel);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(charStatsLabel);
        charDisplayPanel.add(Box.createVerticalStrut(20));
        charDisplayPanel.add(textAreaWrapper);
        charDisplayPanel.add(Box.createVerticalStrut(10));
        charDisplayPanel.add(detailsButtonPanel); 
        
        con.add(charDisplayPanel, BorderLayout.CENTER);
        
        
        selectionButtonsPanel = new JPanel();
        selectionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        selectionButtonsPanel.setBackground(Color.DARK_GRAY);
        
        createCharacterSelectionButtons();
        
        con.add(selectionButtonsPanel, BorderLayout.SOUTH);
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
            selectButton.setBackground(new Color(0, 150, 0));
            selectButton.setForeground(Color.WHITE);
            
            selectButton.addActionListener(e -> selectCharacter(sourceButton, c));
            detailsButtonPanel.add(selectButton);
            
        } else {
             detailsButtonPanel.removeAll();
        }

        detailsButtonPanel.revalidate();
        detailsButtonPanel.repaint();
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
        startButton.setFont(titleFont.deriveFont(Font.BOLD, 24));
        startButton.setBackground(new Color(150, 0, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setEnabled(false);
        startButton.addActionListener(e -> startGame());
        startButton.setName("START_BUTTON"); 
        selectionButtonsPanel.add(startButton);

        for (Character character : availableCharacters) {
            JButton charButton = new JButton(character.name);
            charButton.setFont(normalFont);
            charButton.setBackground(new Color(60, 60, 60));
            charButton.setForeground(Color.WHITE);
            charButton.setFocusPainted(false);
            
            charButton.addActionListener(e -> {
                for(Component comp : selectionButtonsPanel.getComponents()) {
                    if (comp instanceof JButton && !"START_BUTTON".equals(comp.getName())) {
                        if (comp.isEnabled()) comp.setBackground(new Color(60, 60, 60)); 
                    }
                }
                if(charButton.isEnabled()) {
                    charButton.setBackground(new Color(0, 120, 180)); 
                }
                
                displayCharacterDetails(character, charButton);
            });
            
            selectionButtonsPanel.add(charButton, selectionButtonsPanel.getComponentCount() - 1); // Insert before START
        }
    }
    
    public void selectCharacter(JButton button, Character c) {
        if (selectionCount < 3 && !playerParty.contains(c)) {
            playerParty.add(c);
            selectionCount++;
            mainTitleLabel.setText("CHOOSE YOUR SQUAD (" + selectionCount + "/3)");
           
            button.setEnabled(false);
            button.setBackground(Color.GRAY);
            button.setText(c.name + " (SELECTED)");
  
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
    
    public void startGame() {
    	charNameLabel.setText("START GAME!");
    	charStatsLabel.setText(" ");
        charBackstoryArea.setText(" ");
    }



static abstract class Character {
    String name;
    int maxHP;
    int currentHP;
    int maxResource;
    int currentResource;
    String resourceName;
    String backstory;
    List<Skill> skills = new ArrayList<>();

    public Character(String name, int hp, int resource, String resourceName, String backstory) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxResource = resource;
        this.currentResource = resource;
        this.resourceName = resourceName;
        this.backstory = backstory;
    }
    public void takeDamage(int damage) {
            this.currentHP -= damage;
            if (this.currentHP < 0) {
                this.currentHP = 0;
            }
        }
}
static class Zor extends Character {
    public Zor() {
        super("Zor", 100, 70, "Energy", "A former deep-cover operative, Zor's training focused on infiltration and espionage. He was one of the few who, due to a rare genetic anomaly, was naturally immune to the vaccine's effects. He uses his enhanced skills to fight for a government that abandoned its people, his only motivation a desire to see justice served.");
        skills.add(new Skill("Sword Slash", 10, 0));
        skills.add(new Skill("High-Jump", 0, 5));
        skills.add(new Skill("Stealth", 0, 3));
    }
    
   
}

static class Leo extends Character {
    public Leo() {
        super("Leo", 150, 80, "Stamina", "Leo was a combat medic on the front lines when the pandemic hit. He was among the first to be administered the vaccine, but it had no effect on him due to his unique genetics. The trauma of losing his entire unit when the Reavers first emerged fuels his relentless drive to protect others.");
        skills.add(new Skill("Overhead Strike", 15, 10));
        skills.add(new Skill("Basic Block", 0, 5));
        skills.add(new Skill("Crowd Control", 10, 3));
    }

    
}

static class Elara extends Character {
    public Elara() {
        super("Elara", 95, 85, "Battery", "A brilliant but reclusive software engineer, Elara was immune to the vaccine due to a rare blood type. She was forced to watch as her entire family, who were not immune, turned into Reavers.");
        skills.add(new Skill("Piercing Arrow", 17, 5));
        skills.add(new Skill("Precision Aim", 0, 5));
        skills.add(new Skill("Scout", 0, 3));
    }

}

static class Kai extends Character {
    public Kai() {
        super("Kai", 100, 70, "Focus", "A former bio-hacker, Kai developed a unique neural interface that allows him to manipulate the mutated creatures' own biology. He has a complicated past, having been involved in the very corporation that created the vaccine, and seeks redemption by using his knowledge to undo the damage.");
        skills.add(new Skill("Neural Shock", 15, 11));
        skills.add(new Skill("Bio-Scan", 0, 5));
        skills.add(new Skill("Mutagenic Surge", 20, 15));
    }

    
}

static class Anya extends Character {
    public Anya() {
        super("Anya", 100, 90, "Resolve", "An ex-special forces sniper, Anya is a master of stealth and long-range combat. She was on a classified mission when the vaccine was distributed, and her unique circumstances shielded her from its effects. Driven by the loss of her entire unit, she now fights to prevent anyone else from experiencing the same horror.");
        skills.add(new Skill("Headshot", 20, 15));
        skills.add(new Skill("Suppressive Fire", 10, 5));
        skills.add(new Skill("Camouflage", 0, 2));
    }

    
}
static class Skill {
    String name;
    int damage;
    int cost;

    public Skill(String name, int damage, int cost) {
        this.name = name;
        this.damage = damage;
        this.cost = cost;
    }
}


static abstract class Enemy {
    String name;
    int maxHP;
    int currentHP;
    int damage;
    String description;
    
    public Enemy(String name, int hp, int damage, String description) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.damage = damage;
        this.description = description;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public abstract void attack(Character target);
}

static class Carrier extends Enemy {
    public Carrier() {
        super("Carrier", 50, 10, "Slow but spreads the virus.");
    }

    @Override
        public void attack(Character target) { target.takeDamage(damage); }
}

static class Boneclaw extends Enemy {
    public Boneclaw() {
        super("Boneclaw", 75, 14, "Fast and lethal ambusher.");
    }

    @Override
        public void attack(Character target) { target.takeDamage(damage); }
}

static class Howler extends Enemy {
    public Howler() {
        super("Howler", 100, 10 , "Disorients enemies and summons more Carriers.");
    }

    @Override
    public void attack(Character target) {target.takeDamage(damage);}

}

}