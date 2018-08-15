/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

/**
 *
 * @author Nicholas
 */
public class Coordinate {
    public int x;
    public int y;
    
    public Coordinate(int xC, int yC){
        x = xC;
        y = yC;
    }
    
    public Coordinate(){
        x = -20;
        y = -20;
    }
    
}
