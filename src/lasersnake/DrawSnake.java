/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicholas
 */

public class DrawSnake extends JComponent {
    public static String highscore = "";
// Location of fruit
    private static Coordinate fruitLocation;
    // Boolean to see if there is a fruit drawn
    private static boolean fruitDrawn = false;
    // Arrays that hold the coordinates of each snake
    private static final ArrayList<Coordinate> snakePlayer1 = new ArrayList<>();
    private static final ArrayList<Coordinate> snakePlayer2 = new ArrayList<>();
    
    // Current and previous coordinates, use for moving the snake
    private static int currentX;
    private static int currentY;
    private static int previousX;
    private static int previousY;
    
    private static int scoreP1 = 0;
    private static int scoreP2 = 0;
    private static int scoreToAdd;
    /**
     * Constructor that makes sure the board is reset
     */
    public DrawSnake(){
        // Reset all fields to their initial state
        resetBoard();
    }
    
    /**
     * This method draws everything to the screen, along with doing some
     * checks to make sure the game should not be over
     * @param g The graphics component to draw to
     */
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        // Set background image
        g2.drawImage(ResourceLoader.GameBackground, 0, 0, null);
        
        //Drawing the score to the game screen
        
        g2.setFont(g2.getFont().deriveFont(24f));
        g2.setColor(new Color(3, 163, 255));
        g2.drawString(Integer.toString(scoreP1), 150, 30);
        g2.drawString(highscore, 635 ,30);
        
        if(LaserSnake.twoPlayer == true){
            g2.setColor(new Color(3, 255, 3));
            g2.drawString(Integer.toString(scoreP2), 200, 30);
        }
        
        // The number of cells that are added per each eaten fruit
        int cellsPerFruit = 2; 
        
        // Checks if there is a fruit on the screen, draws if false
        if(fruitDrawn == false){
            newFruit();
        }
        
        // Draws the fruit at its location, to the graphics component
        drawFruit(fruitLocation, g2); //
        
        //Calls the move snake method, which moves the snake each time the method is called
        moveSnake(LaserSnake.directionP1, snakePlayer1, "p1", g2);
        
        // Moves player 2, if it exists
        if(LaserSnake.twoPlayer == true){
            moveSnake(LaserSnake.directionP2, snakePlayer2, "p2", g2);
            // checks to see if the snakes have hit each other
            snakeCollision();
        }
        
        // Checks to see if player one ate the fruit
        if (snakePlayer1.get(0).x == fruitLocation.x && snakePlayer1.get(0).y == fruitLocation.y){
            // Add cellsPerFruit number of fruit to the snake that ate the friut
            for(int i = 0; i < cellsPerFruit; i++){
                addCell(snakePlayer1);
            }
            playEffect(ResourceLoader.EatTone);
            System.out.println("P1 Ate fruit");
            // Fruit was eaten, so it is no longer on the screen
            fruitDrawn = false;
            
            scoreP1 += scoreToAdd;//Add score to player1
        }
        // Checks if player two ate the fruit
        if (LaserSnake.twoPlayer == true){
            if (snakePlayer2.get(0).x == fruitLocation.x && snakePlayer2.get(0).y == fruitLocation.y){
                for(int i = 0; i < cellsPerFruit; i++){
                    addCell(snakePlayer2);
                }
                playEffect(ResourceLoader.EatTone);
                System.out.println("P2 Ate fruit");
                fruitDrawn = false;
                
                scoreP2 += scoreToAdd;//Add score to player2
            }
        }
        // If the highscore file is empty
        if(highscore.equals(""))
	{
            try {
                highscore = this.gethighscorevalue();
            } catch (IOException ex) {
                Logger.getLogger(DrawSnake.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
    }
    
     /**
     * This method plays an audio file whenever the method is called, used
     * for playing game effects
     * @param effect The file to play
     */
    public static void playEffect(File effect){
        try{
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(effect));
            audioClip.start();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * This method moves the snake one cell in its given direction
     * @param direction The direction in which the snake will move
     * @param snakeArray Which snake is to be moved
     * @param player Identification of which player, to be sent to SnakeBody to determine color
     * @param g2 The graphics component
     */
    public static void moveSnake(String direction, ArrayList<Coordinate> snakeArray, String player, Graphics2D g2){
        // How many pixels to move per turn
        int moveBy = 20;
        // Depending on direction, these will hold the number of pixels to add or subtract
        int xToAdd = 0;
        int yToAdd = 0;
        
        // If going East
        if(direction.equals("e")){
            xToAdd = moveBy;
            yToAdd = 0;
        }
        // If going North
        if(direction.equals("n")){
            xToAdd = 0;
            yToAdd = -moveBy;
        }
        // If going West
        if(direction.equals("w")){
            xToAdd = -moveBy;
            yToAdd = 0;
        }
        // If going South
        if(direction.equals("s")){
            xToAdd = 0;
            yToAdd = moveBy;
        }
        
        /* Getting the current coordinates of the head of the snake and saving
           them so set as the cell behind the head
        */
        previousX = snakeArray.get(0).x;
        previousY = snakeArray.get(0).y;
        
        // Move head of snake
        snakeArray.get(0).x += xToAdd;
        snakeArray.get(0).y += yToAdd;
        
        // Update the rest of the snake body
        for(int i = 1; i < snakeArray.size(); i++){
            // Getting current cell
            Coordinate currentCell = snakeArray.get(i);
            
            // Save location of current cell
            currentX = currentCell.x;
            currentY = currentCell.y;
            
            // Setting current cell to the previous location of the cell in front of it
            currentCell.x = previousX;
            currentCell.y = previousY;
            
            // Setting previous to the old location of current cell, for next pass through
            previousX = currentX;
            previousY = currentY;
        }
        
        // The max and current opacity of the snake(head)
        float currentOpacity = 1f;
        // Each cell's opacity will decrease by a ratio of the minimum opacity to the snake size
        float toChangeBy = (float)0.7/snakeArray.size();
        
        /* Loops through the snake array and draws it at its coordinates, and with
           the proper opacity, depending on its location in the array.
           i.e. closer to the head = greater opacity
        */
        for(int i = 0; i < snakeArray.size(); i++){
            Coordinate currentCell = snakeArray.get(i);
            new SnakeBody(currentCell.x, currentCell.y, player, currentOpacity).draw(g2);
            // Decrament the current opacity by a ratio of the snake size
            currentOpacity -= toChangeBy;
        }
        
        // Checks to see if the snake ran into itself if the head's coordinates equal any of the body
        for (int i = 1; i < snakeArray.size();i++){
            if ((snakeArray.get(0).x == snakeArray.get(i).x) &&(snakeArray.get(0).y == snakeArray.get(i).y )){
                System.out.println("Run though self; game over");
                checkscore();
                // Change the current game state because the game is over
                LaserSnake.currentState = LaserSnake.gameState.endScreen;
            }
        }
        
        // Checks to see if the snake went out of bounds
        if(snakeArray.get(0).x < LaserSnake.dieX-LaserSnake.dieX || 
                snakeArray.get(0).x >= LaserSnake.dieX || 
                snakeArray.get(0).y < 40 ||snakeArray.get(0).y == LaserSnake.dieY){
            System.out.println("Out of bounds; game over: " + snakeArray.get(0).x + "," + snakeArray.get(0).y);
            checkscore();
            // Change game state to game over (endScreen)
            LaserSnake.currentState = LaserSnake.gameState.endScreen;
        }
    }
    
    /**
     * This method checks to see if the two snakes collided
     */
    public static void snakeCollision(){
        // Checks if player 2 ran into player 1
        for(int i = 0; i < snakePlayer1.size(); i++){
            if(snakePlayer2.get(0).x == snakePlayer1.get(i).x && snakePlayer2.get(0).y == snakePlayer1.get(i).y){
                checkscore();
                LaserSnake.currentState = LaserSnake.gameState.endScreen;
                System.out.println("Green (p2) hit Blue(p1)");
            }
        }

        // Checks if player 1 ran into player 2
        for(int i = 0; i < snakePlayer2.size(); i++){
            if(snakePlayer1.get(0).x == snakePlayer2.get(i).x && snakePlayer1.get(0).y == snakePlayer2.get(i).y){
                checkscore();
                LaserSnake.currentState = LaserSnake.gameState.endScreen;
                System.out.println("blue (p2) hit green(p1)");
            }
        }
    }
    
    /**
     * This method adds cell(s) to the given snake
     * @param snakeArray The array to add the cell
     */
    public static void addCell(ArrayList<Coordinate> snakeArray){
        snakeArray.add(new Coordinate()); 
    }
    
    /**
     * Creates new fruit at random coordinate on the screen, checks to make
     * sure it doesn't appear on either snake body
     */
    public static void newFruit(){
        // Creates a new random object to generate the numbers
        Random rand = new Random();
        // Integers to be set and checked
        int xCoord = 0;
        int yCoord = 0;
        
        // A boolean set to false, gets changed to true if passes valid checks
        boolean valid = false;
        
        // Largest coordinates for fruit
        int maxX = (LaserSnake.DIMENSION.width-20)/20;  // Minus 20 and 60 so its not off the screen and then divide
        int maxY = (LaserSnake.DIMENSION.height-60)/20; // by 20 so the random number is a multiple of 20
        
        //check for valid coordiantes, loops until valid coordinate is generated
        while (valid == false){
            // Changed to true, stays true unless it fails check
            valid = true;
            // Get random coordinates
            xCoord = rand.nextInt(maxX)*20;
            yCoord = rand.nextInt(maxY)*20 + 40; // Add 40 to make sure it can't draw in the menu
            
            // Checks if coordinates lie on player one snake
            for(int i = 0; i < snakePlayer1.size(); i++){
                if(snakePlayer1.get(i).x == xCoord && snakePlayer1.get(i).y == yCoord){
                    // Not valid
                    valid = false;
                    System.err.println("failed coordinate check, generating new point");
                }
            }
            // Checks if coordinates lie on player two snake
            for(int i = 0; i < snakePlayer2.size(); i++){
                if(snakePlayer2.get(i).x == xCoord && snakePlayer2.get(i).y == yCoord){
                    // Not valid
                    valid = false;
                    System.err.println("failed coordinate check, generating new point");
                }
            }
        }
        
        // Sets fruitLocation to new randomly generated points
        fruitLocation = new Coordinate(xCoord, yCoord);
        // Changes fruit drawn boolean to true
        fruitDrawn = true;
    }
    
    /**
     * Draws the fruit at the given location, to the graphics component
     * @param fLocation Fruit location coordinates
     * @param g2 Graphics component to draw to
     */
    public static void drawFruit(Coordinate fLocation, Graphics2D g2){
        //Greate and draw fruit at the given location
        Fruit fruit = new Fruit(fLocation.x, fLocation.y);
        fruit.draw(g2);   
    }
    
    /**
     * Resets all fields to their initial state, so a new game can start
    */
    public static void resetBoard(){
        switch(LaserSnake.gameDifficulty){
            case easy:
                scoreToAdd = 5;
                System.out.println("easy(5)");
                break;
            case normal:
                scoreToAdd = 10;
                System.out.println("normal(10)");
                break;
            case hard:
                scoreToAdd = 15;
                System.out.println("hard(15)");
                break;
            case insane:
                scoreToAdd = 30;
                System.out.println("insane(30)");
                break;
        }
        
        fruitDrawn = false;
        currentX = 0;
        currentY = 0;
        previousX = 0;
        previousY = 0;
        
        LaserSnake.directionP1 = "w";
        LaserSnake.directionP2 = "e";
        
        scoreP1 = 0;
        scoreP2 = 0;
        
        snakePlayer1.clear();
        snakePlayer2.clear();
        
        /* Add the initial cells to each snake.
           Only the first cell requires a location because each cell that follows
           depend on the location of the one infront of it.
        */
        snakePlayer1.add(new Coordinate(760, 40));
        snakePlayer1.add(new Coordinate());
        snakePlayer1.add(new Coordinate());
        
        snakePlayer2.add(new Coordinate(40, 40));
        snakePlayer2.add(new Coordinate());
        snakePlayer2.add(new Coordinate());
    }
    
    /**
     * Gets the current high score by reading from a .dat file, checks
     * if it is there, else return string " : 0"
     * @return String to return
     * @throws IOException 
     */
    public String gethighscorevalue() throws IOException{
	FileReader readFile = null;
	BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
            reader = new  BufferedReader(readFile);
                return reader.readLine();

        } catch (FileNotFoundException e) {
            return ":0";
        }
        finally{
            try{
                if(reader!=null){
                reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public static void checkscore(){
        playEffect(ResourceLoader.DeathTone); // A death hath occureth
        
        if(scoreP1 >= scoreP2){
            GameOverPanel.WinnerAndScore.setText("Player 1 won with " + scoreP1 + " points!");
        }
        else{
            GameOverPanel.WinnerAndScore.setText("Player 2 won with " + scoreP2 + " points!");
        }
        
        
        if((scoreP1)> Integer.parseInt(highscore.split(":")[1]) || (scoreP2)> Integer.parseInt(highscore.split(":")[1]) ){
            String name = JOptionPane.showInputDialog("You set a new highscore, what is your name?");
            if(scoreP1>scoreP2){
                highscore = name+":"+scoreP1;
            }
            else{
                highscore = name+":"+scoreP2;
            }
            File scoreFile = new File("highscore.dat");
            if(!scoreFile.exists()){
		try{
                    scoreFile.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
		}
            }
			
            FileWriter writeFile = null;
            BufferedWriter writer = null;
            try{
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                writer.write(highscore);
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(writer!=null){writer.close();}
                }catch(Exception e){
                    e.printStackTrace();
                }
            }	
        }
    }
}
