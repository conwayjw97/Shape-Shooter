package ShapeShooter;

/**
 *
 * @author Cigol
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameFrame {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int xSize = 1000, ySize = 800;
        
        GamePanel game = new GamePanel(xSize, ySize);
        
        // Action listeners are implemented in the frame declaration
        // These serve to detect the users mouse actions
        game.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent evt) {}
            
            @Override
            public void mouseMoved(MouseEvent evt) {
                game.moveCrosshair(evt);
            }
            
        });
        game.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent evt) {}
            
            @Override
            public void mousePressed(MouseEvent evt) {
                game.shootCrosshair(evt);
            }
            
            public void mouseReleased(MouseEvent evt) {}
            public void mouseEntered(MouseEvent evt) {}
            public void mouseExited(MouseEvent evt) {}
        });
        game.setFocusable(true);
        game.requestFocusInWindow();
        
        // Turn cursor invisible when inside the Frame
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");
        // Set the blank cursor to the JFrame.
        game.setCursor(blankCursor);
        
        f.add(game);
        f.setSize(xSize, ySize);
        f.setResizable(false);
        f.setVisible(true);
    }
}
