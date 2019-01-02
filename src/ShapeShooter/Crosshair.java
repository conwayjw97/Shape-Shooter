package ShapeShooter;

/**
 *
 * @author Cigol
 */

public class Crosshair {
    
    private int crosshairX, crosshairY, crosshairSize;
    
    Crosshair(int xSize, int ySize){
        crosshairX = xSize / 2;
        crosshairY = ySize / 2;
        crosshairSize = 40;
    }
    
    public int getX(){
        return crosshairX;
    }
    
    public int getY(){
        return crosshairY;
    }
    
    public int getXLeft(){
        return crosshairX - (crosshairSize / 2);
    }
    
    public int getXRight(){
        return crosshairX + (crosshairSize / 2);
    }
    
    public int getYTop(){
        return crosshairY - (crosshairSize / 2);
    }
    
    public int getYBottom(){
        return crosshairY + (crosshairSize / 2);
    }
    
    public int getSize(){
        return crosshairSize;
    }
    
    public void moveTo(int x, int y){
        crosshairX = x;
        crosshairY = y;
    }
    
    public void moveLeft(){
        crosshairX -= 10;
    }
    
    public void moveRight(){
        crosshairX += 10;
    }
}
