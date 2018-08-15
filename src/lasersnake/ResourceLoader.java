/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Nicholas
 * 
 * Loads all pictures and audio files
 */
public class ResourceLoader {
    public static Image BlueSnakeCell;
    public static Image GreenSnakeCell;
    public static Image GameBackground;
    public static Image Paused;
    public static Image Cherry;
    
    public static File EatTone;
    public static File DeathTone;
    
    public static Clip menuMusic;
    public static Clip gameMusic;
    public static Clip aboutMusic;
    
    
    public ResourceLoader(){        
        // Images
        BlueSnakeCell = Toolkit.getDefaultToolkit().getImage(LaserSnake.class.getResource("/resources/BLUESNEK.png"));
        GreenSnakeCell = Toolkit.getDefaultToolkit().getImage(LaserSnake.class.getResource("/resources/GREENSNEK.png"));
        GameBackground = Toolkit.getDefaultToolkit().getImage(LaserSnake.class.getResource("/resources/ONEDOWN.jpg"));
        Paused = Toolkit.getDefaultToolkit().getImage(LaserSnake.class.getResource("/resources/paused.png"));
        
        // Animations
        Cherry = Toolkit.getDefaultToolkit().getImage(LaserSnake.class.getResource("/resources/ball_lightning.gif"));
        
        // Audio
        try{
            EatTone = new File(LaserSnake.class.getResource("/resources/EatTone.wav").getFile());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            DeathTone = new File(LaserSnake.class.getResource("/resources/DeathTone.wav").getFile());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        // Menu music
        try{
            File mmLocation = new File(LaserSnake.class.getResource("/resources/BloodDragonTheme.wav").getFile());
            menuMusic = AudioSystem.getClip();
            menuMusic.open(AudioSystem.getAudioInputStream(mmLocation));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        // Game music
        try{
            File location = new File(LaserSnake.class.getResource("/resources/Resurrection.wav").getFile());
            gameMusic = AudioSystem.getClip();
            gameMusic.open(AudioSystem.getAudioInputStream(location));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        // About music
        try{
            File mmLocation = new File(LaserSnake.class.getResource("/resources/AnEnd.wav").getFile());
            aboutMusic = AudioSystem.getClip();
            aboutMusic.open(AudioSystem.getAudioInputStream(mmLocation));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
