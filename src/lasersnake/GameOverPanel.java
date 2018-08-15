/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasersnake;

import static lasersnake.LaserSnake.ResourceLoader;
import static lasersnake.LaserSnake.cardLayout;
import static lasersnake.LaserSnake.currentMusic;
import static lasersnake.LaserSnake.currentState;
import static lasersnake.LaserSnake.gamePanel;
import static lasersnake.LaserSnake.playMusic;
import static lasersnake.LaserSnake.rewindMusic;
import static lasersnake.LaserSnake.rootPanel;
import static lasersnake.LaserSnake.runGame;
import static lasersnake.LaserSnake.stopMusic;

/**
 *
 * @author Muhaamed
 */
public class GameOverPanel extends javax.swing.JPanel {

    /**
     * Creates new form GameOverPanel
     */
    public GameOverPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RestartButton = new javax.swing.JButton();
        MainMenu = new javax.swing.JButton();
        WinnerAndScore = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RestartButton.setText("Restart");
        RestartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestartButtonActionPerformed(evt);
            }
        });
        add(RestartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 430, 100, -1));

        MainMenu.setText("Main Menu");
        MainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainMenuActionPerformed(evt);
            }
        });
        add(MainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 480, 100, -1));

        WinnerAndScore.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        WinnerAndScore.setForeground(new java.awt.Color(0, 255, 255));
        WinnerAndScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WinnerAndScore.setText("jLabel2");
        add(WinnerAndScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 380, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gameOver.jpg"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void MainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainMenuActionPerformed
        DrawSnake.resetBoard();
        cardLayout.show(rootPanel, "main");
        currentState = LaserSnake.gameState.mainMenu;
        stopMusic();
        rewindMusic(currentMusic);
        playMusic(ResourceLoader.menuMusic);
        
    }//GEN-LAST:event_MainMenuActionPerformed

    private void RestartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestartButtonActionPerformed
        DrawSnake.resetBoard();
        runGame(rootPanel, gamePanel);
        currentState = LaserSnake.gameState.play;
    }//GEN-LAST:event_RestartButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MainMenu;
    private javax.swing.JButton RestartButton;
    public static javax.swing.JLabel WinnerAndScore;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}