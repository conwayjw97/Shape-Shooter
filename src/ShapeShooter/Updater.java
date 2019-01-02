package ShapeShooter;

/**
 *
 * @author Cigol
 */
public class Updater implements Runnable{
    private GamePanel game;
    
    public Updater(GamePanel game) {
        this.game = game;
    }
    
    @Override
    public void run(){ 
        game.repaint();
        try {
            Thread.sleep(game.STARTUP_INTERVAL);
        } catch (InterruptedException ignore) {}
        while (true) {
            game.counter = Integer.toString(game.shapes.size()) + "/" + GamePanel.MAX_SHAPES;
            if(game.shapes.size() != game.MAX_SHAPES){ // If the player hasn't lost yet
                game.repaint(); // Refresh the JFrame. Called back paintComponent()
                try { // Delay and give other thread a chance to run
                    Thread.sleep(game.UPDATE_INTERVAL);  // milliseconds
                } catch (InterruptedException ignore) {}
            }
            else{ // If the player has lost already
                game.repaint();
                game.playSound(game.gameoverSound, 6);
                break;
            }
        }
    }
}
