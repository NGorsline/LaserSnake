/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import java.util.Random;

public class SnakeBody extends LaserSnake{
    private Random rand = new Random();
    private int xLeft;
    private int yTop;
    private String playerID;
    private float opacity;
    
    /**
     * Constructor that  initializes
     * @param x x coordinate of cell 
     * @param y y coordinate of cell
     * @param player Which snake is being drawn
     * @param opacityValue opacity of cell
     */
    public SnakeBody(int x, int y, String player, float opacityValue){
        xLeft = x;
        yTop = y;
        playerID = player;
        opacity = opacityValue;
    }
    
    /**
     * Draws the cell at the given location with the given opacity
     * @param g2 the graphics component
     */
    public void draw(Graphics2D g2){
        // Setting opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        // Use player 1 snake image
        if(playerID.equals("p1")){
            g2.drawImage(ResourceLoader.BlueSnakeCell, xLeft, yTop, null);
        }
        
        // Use player 2 snake image
        else{
            g2.drawImage(ResourceLoader.GreenSnakeCell, xLeft, yTop, null);
        }
    }
}
