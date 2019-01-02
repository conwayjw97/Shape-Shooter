package ShapeShooter;

/**
 *
 * @author Cigol
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class GamePanel extends JPanel{
    
    public static final int UPDATE_INTERVAL = 10; // milliseconds
    public static final int MAX_SHAPES = 20;
    public static final int SHAPE_INTERVAL = 400;
    public static final int STARTUP_INTERVAL = 1000;
    
    public int score, xSize, ySize;
    public boolean startFlag, restartFlag;
    
    public Crosshair crosshair;
    public Shape shape;
    public Vector shapes;
    public String counter;
    
    public Random r = new Random();
    
    public File shootSound, hitSound, gameoverSound, musicSound, appearSound;
    
    GamePanel(int xSize, int ySize){
        // Part 1 - Declarations
        this.xSize = xSize;
        this.ySize = ySize;
        crosshair = new Crosshair(xSize, ySize);
        shapes = new Vector();
        score = 0;
        startFlag = true; 
        restartFlag = false;
        counter = Integer.toString(shapes.size()) + "/" + MAX_SHAPES;
        
        shootSound = new File("shoot.wav");
        hitSound = new File("hit.wav");
        gameoverSound = new File("gameover.wav");
        musicSound = new File("Platoon Theme.wav");
        appearSound = new File("appear.wav");
        
        // Create a new thread to run updates at regular intervals
        Thread updater = new Thread(new Updater(this));
        // Create a new thread to create new shapes at regular intervals
        Thread shapeCreator = new Thread(new ShapeCreator(this));
        
        // Part 2 - Actions
        playSound(musicSound, 6);
        updater.start(); // Call back run()
        shapeCreator.start();
    }
    
    public void moveCrosshair(MouseEvent evt){
        crosshair.moveTo(evt.getX(), evt.getY());
    }
    
    public void shootCrosshair(MouseEvent evt){
        playSound(shootSound, 6);
        // Iterate through all the shapes
        for (int i=0; i<shapes.size(); i++){
            shape = (Shape) shapes.elementAt(i);
            //Check if crosshair is within the range of a shape
            if ((crosshair.getX()>shape.getX() && crosshair.getX()<(shape.getX()+shape.getSize()))
                        && 
               (crosshair.getY()>shape.getY() && crosshair.getY()<(shape.getY()+shape.getSize()))){
                shapes.removeElementAt(i);
                score++;
                playSound(hitSound, 6);
            }    
        }
    }
    
    public void playSound(File sound, float volume){
        try{
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(sound));
            FloatControl gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); 
            soundClip.start();
        }catch(Exception e){
            System.out.println(e);
        }
    }
                 
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        
        // Draw shapes
        g.setColor(Color.GREEN);
        for (int i=0; i<shapes.size(); i++){
            shape = (Shape) shapes.elementAt(i);
            g.fillRect(shape.getX(), shape.getY(), shape.getSize(), shape.getSize());
        }
        
        // Draw crosshair
        g.setColor(Color.RED);
        g.drawOval(crosshair.getXLeft(), crosshair.getYTop(), crosshair.getSize(), crosshair.getSize());
        g.drawLine(crosshair.getX(), crosshair.getYTop()-crosshair.getSize()/2, crosshair.getX(), crosshair.getYBottom()+crosshair.getSize()/2);
        g.drawLine(crosshair.getXLeft()-crosshair.getSize()/2, crosshair.getY(), crosshair.getXRight()+crosshair.getSize()/2, crosshair.getY());
    
        // Draw counter
        g.setFont(new Font("Terminal", Font.PLAIN, 28)); 
        g.drawString(counter, 10, 29);
        g.drawString("Score: ", 90, 29);
        g.drawString(String.valueOf(score), 175, 29);
        
        // Draw Game Over
        if(shapes.size() == MAX_SHAPES){
            Font font = new Font("Terminal", Font.PLAIN, 128);
            String text = "GAME OVER";
            
            FontMetrics metrics = g.getFontMetrics(font);
            int x = (xSize - metrics.stringWidth(text)) / 2;
            int y = (ySize / 2);
            
            g.setFont(font); 
            g.drawString(text, x, y-30);
            
            font = new Font("Terminal", Font.PLAIN, 64);
            text = "Final Score: " + Integer.toString(score);
            
            metrics = g.getFontMetrics(font);
            x = (xSize - metrics.stringWidth(text)) / 2;
            y = (ySize / 2);
            
            g.setFont(font); 
            g.drawString(text, x, y+30);
        }
    }

}
