/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShapeShooter;

/**
 *
 * @author Cigol
 */
public class ShapeCreator implements Runnable{
    private GamePanel game;
    
    ShapeCreator(GamePanel game){
        this.game = game;
    }
    
    @Override
    public void run() {
        game.repaint();
        try {
            Thread.sleep(game.STARTUP_INTERVAL);
        } catch (InterruptedException ignore) {}
        while (true) {
            if(game.shapes.size() != game.MAX_SHAPES){ // If the player hasn't lost yet
                game.shape = new Shape(game.r.nextInt(game.xSize-100), game.r.nextInt(game.ySize-100), game.r.nextInt(75)+25);
                game.shapes.addElement(game.shape);
                game.playSound(game.appearSound, 1);
                game.repaint();  // Refresh the JFrame. Called back paintComponent()
                try {// Delay and give other thread a chance to run
                    Thread.sleep(game.SHAPE_INTERVAL);  // milliseconds
                } catch (InterruptedException ignore) {}
            }
            else{ // If the player has lost already
                break;
            }
        }
    }
}
