/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

import java.awt.Graphics2D;


/**
 *
 * @author Nicholas
 */
public class Fruit {
    private int xLeft;
    private int yTop;
    
    public Fruit(int x, int y){
        xLeft = x;
        yTop = y;
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(ResourceLoader.Cherry, xLeft, yTop, null);
    }
}