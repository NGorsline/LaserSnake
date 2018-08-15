package lasersnake;

import java.awt.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.Clip;

/**
 *
 * @author Nicholas, Muhaamed, Aditya Jonany, Dawit Abera
 * 12/8/15
 * 
 * This is our take on the classic game, "Snake".
 * Final project for CS 141, Fall 15
 */


public class LaserSnake {
    // Instantiates a class that loads and holds all the resources
    public static final ResourceLoader ResourceLoader = new ResourceLoader();
    public static Clip currentMusic;
    // Boolean for whether or not there is a player 2
    public static boolean twoPlayer = false;
    // Declaration of the enums used for the game states
    public static enum gameState{
        mainMenu,
        play,
        pause,
        endScreen
    }
    // Declaration of the enums used for level of difficulty
    public static enum difficulty{
        easy, 
        normal, 
        hard, 
        insane
    }
    // Use of the enum to keep track of the current state, initialize at main menu
    public static gameState currentState = gameState.mainMenu;
    
    // Difficulty state, initialize to normal
    public static difficulty gameDifficulty = difficulty.normal;
    
    /* A boolean to check if the direction of the user has changed this turn
    so they cannot turn 180 degrees into their own snake
    */
    private static boolean dirChangedP1 = false;
    private static boolean dirChangedP2 = false;
    
    // Starting direction of each snake
    public static String directionP1 = "w"; // starts going east
    public static String directionP2 = "e"; // starts going west
    
    public static final Dimension DIMENSION = new Dimension(800, 600);
    // The x and y coordinates of the "kill box" (out of bounds coordinates)
    public static final int dieX = DIMENSION.width;
    public static final int dieY = DIMENSION.height;
    
    /*
    Game speed (diffucult)
    easy = 160
    normal = 90
    hard = 50
    insane = 25
    */
    public static final long EASY = 160;
    public static final long NORMAL = 90;
    public static final long HARD = 50;
    public static final long INSANE = 25;
    // Start at normal
    public static long speed = NORMAL;
    
    public static CardLayout cardLayout;
    public static JPanel rootPanel;
    public static JPanel gamePanel;
    
    /**
     * This is the main method that creates the panels and their components
     * and starts the game
     * @param args 
     */
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Laser Snake!"); 
        // Dictate action upon window close 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Getting the frames content pane, so we can add to it
        Container content = frame.getContentPane();
        
        // Create all the panels for each game state
        // Root that holds all the other panels
        rootPanel = new JPanel(new CardLayout()); 
        gamePanel = new JPanel(new BorderLayout());
        JPanel pauseMenuPanel = new JPanel(new FlowLayout());
        
        // Instantiating the board
        DrawSnake snakeBoard = new DrawSnake();
        
        // Getting the root panels cardLayout
        cardLayout = (CardLayout)(rootPanel.getLayout());
        // Set prefered dimension
        gamePanel.setPreferredSize(DIMENSION);
        
        // Adding game board to the game panel
        gamePanel.add(snakeBoard);
        
        // Creating the elements of the pause menu
        JLabel pausedLabel = new JLabel();
        // Display paused image when game is paused
        pausedLabel.setIcon(new ImageIcon(ResourceLoader.Paused));
        // Partially transparent background for the pause menu
        pauseMenuPanel.setBackground(new Color(0, 0, 0, 123));
        

        // Add all panels to a root panel, with their names to be referenced when using the card panel
        
        rootPanel.add(new MainMenu(), "main");
        rootPanel.add(pauseMenuPanel, "pause");
        rootPanel.add(gamePanel, "game");
        rootPanel.add(new AboutPanel(), "about");
        rootPanel.add(new GameOverPanel(), "end");
        
        // Adding root panel to frame 
        content.add(rootPanel);
        // Set focusable so the keyListener can work on the frame
        content.setFocusable(true);
        // Can not resize frame
        frame.setResizable(false);
        // Packing the frame, so setPreferedSize uses the correct dimensions
        frame.pack();
        // Now we can see!
        frame.setVisible(true);
        
        // Adding this label has to be done after pack() or else an the frame is extended
        pauseMenuPanel.add(pausedLabel);
        
        // Creating a keyListener and adding it to the frame so we can use keyboard input
        content.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e) {
                // Key pressed code
                int key = e.getKeyCode();
                
                //Only change direction if playing
                if(currentState == gameState.play){
                    // Left arrow
                    if (key == KeyEvent.VK_LEFT && !directionP1.equals("e") && dirChangedP1 == false) {
                        directionP1 = "w";
                        dirChangedP1 = true;
                    }
                    // Right arrow
                    else if (key == KeyEvent.VK_RIGHT && !directionP1.equals("w") && dirChangedP1 == false) {
                        directionP1 = "e";
                        dirChangedP1 = true;
                    }
                    // Up arrow
                    else if (key == KeyEvent.VK_UP&& !directionP1.equals("s") && dirChangedP1 == false) {
                        directionP1 = "n";
                        dirChangedP1 = true;
                    }
                    // Down arrow
                    else if (key == KeyEvent.VK_DOWN && !directionP1.equals("n") && dirChangedP1 == false) {
                        directionP1 = "s";
                        dirChangedP1 = true;
                    }
                    // "w" key
                    else if (key == KeyEvent.VK_W && !directionP2.equals("s") && dirChangedP2 == false) {
                        directionP2 = "n";
                        dirChangedP2 = true;
                    }
                    // "s" key
                    else if (key == KeyEvent.VK_S && !directionP2.equals("n") && dirChangedP2 == false) {
                        directionP2 = "s";
                        dirChangedP2 = true;
                    }
                    // "a" key
                    else if (key == KeyEvent.VK_A && !directionP2.equals("e") && dirChangedP2 == false) {
                        directionP2 = "w";
                        dirChangedP2 = true;
                    }
                    // "d" key
                    else if (key == KeyEvent.VK_D && !directionP2.equals("w") && dirChangedP2 == false) {
                        directionP2 = "e";
                        dirChangedP2 = true;
                    }
                }
                // Space bar
                if (key == KeyEvent.VK_SPACE ){ //&& (currentState != gameState.mainMenu || currentState != gameState.endScreen)
                    if(currentState == gameState.play){
                        currentState = gameState.pause;
                    }
                    else{
                        currentState = gameState.play;
                    }
                    System.out.println("Space pressed: game paused = " + currentState);
                    
                }
                
                System.out.println("key = " + key + "(P1, P2): (" + directionP1 + "," + directionP2 + ")");
            }
            public void keyReleased(KeyEvent e) { }
            public void keyTyped(KeyEvent e) { }
            });
        
        // Starts Main menu music
        playMusic(ResourceLoader.menuMusic);
    }
    
    /**
     * This method runs the game, changes which panel is being displayed and 
     * repaints the game board to play the game
     * 
     * @param rootPanel main panel
     * @param gamePanel panel in which the game is played
     */
    public static void runGame(JPanel rootPanel, JPanel gamePanel){
        stopMusic();
        playMusic(ResourceLoader.gameMusic);
        
        // Create a new timer that will run the game, and dictate the panel shown
        Timer gameTimer = new Timer();

        // The task to run every interval period
        TimerTask gameTask = new TimerTask() {
        @Override
            public void run() {
                // New frame, so the check if the direction has been changed gets reset
                dirChangedP1 = false;
                dirChangedP2 = false;
                
                // Show the game panel and refresh the screen
                if(currentState == gameState.play){
                    cardLayout.show(rootPanel, "game");
                    gamePanel.repaint();
                }
                
                // Show the pause panel
                else if(currentState == gameState.pause){
                    cardLayout.show(rootPanel, "pause");
                }
                
                // Show end screen and end timer
                else if(currentState == gameState.endScreen){
                    System.out.println("GAME OVER!");
                    gameTimer.cancel();
                    gameTimer.purge();
                    cardLayout.show(rootPanel, "end");
                }
            }
        };

        // schedules the task to be run in an interval
        gameTimer.scheduleAtFixedRate(gameTask, 0, speed);
    }
    
    /**
     * This method starts any clip given, loops until it runs 5 times or 
     * is told to stop
     * @param tune The music to play
     */
    public static void playMusic(Clip tune){
        LaserSnake.currentMusic = tune;
        tune.loop(5);
    }
    
    /**
     * This method stops the current music playing
     */
    public static void stopMusic(){
        currentMusic.stop();
    }
    
    /**
     * This method rewinds the given clip to the start
     * @param tune to rewind
     */
    public static void rewindMusic(Clip tune){
        tune.setFramePosition(0);
    }
}