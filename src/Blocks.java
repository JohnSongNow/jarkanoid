package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.ListIterator;

public class Blocks extends GameObject {
	private Rectangle2D rectangle;
	private int life;
	private BufferedImage image;

    public Blocks(int x, int y, int width, int height, int life, BufferedImage image) {
    	super(x, y, width, height,0, 0);
    	this.rectangle = new Rectangle2D.Double(x, y, width, height);
    	this.life = life;
    	this.image = image;
    }
    public Rectangle2D getBlock(){
    	return rectangle;
    }
    public int returnLife(){
    	return life;
    }
    public void subtractLife(int newLife){
    	life -= newLife;
    }
    public void update(ControlPanel panel){
    	ListIterator<GameObject> list = panel.getObjects().listIterator();
    	while(list.hasNext()){
    		GameObject i = list.next();
    		if(i instanceof Laser){
    			if(rectangle.intersects(((Laser)i).getLaser()))
    				list.remove();
    		}
	   		if(i instanceof Bomb){
	   			//if((((Bomb)i).getBomb()).intersects(rectangle))
	    			//list.remove();
	    	}
    	}
   	// The life of the block is dealt with in ball
		if(life == 1)
			image = panel.getImages()[0];
		else if(life == 2)
			image = panel.getImages()[1];
		else if(life == 3)
			image = panel.getImages()[2];
		else if(life == 4)
			image = panel.getImages()[3];
		else if(life == 5)
			image = panel.getImages()[4];
		else if(life == 6)
			image = panel.getImages()[5];
		  	
    }
    public PowerUp spawnPowerUp(){ //Spawning Random Power-Ups
    	Random random = new Random();
    	String powerUp = ("");
    	int type = random.nextInt(7) + 1;
    	//type = 1;
    	if(type == 1)
    		powerUp = "Catch";
    	else if(type == 2)
    		powerUp = "Slow";
    	else if(type == 3)
    		powerUp = "Enlarge";
    	else if(type == 4)
    		powerUp = "Laser";
    	else if(type == 5)
    		powerUp = "Speed";
    	else if(type == 6)
    		powerUp = "Life";
    	else if(type == 7)
    		powerUp = "MultiBall";
    	else if(type == 8) // can't happen.. yet
      		powerUp = "Qayum Ball???";
     	return new PowerUp(x + width / 2, y + 1, 4.0, 30, 10, powerUp, image);
    }
    public void paintComponent(Graphics2D g2) {
    	g2.drawImage(image, x, y, 40, 20, null);
    	rectangle.setFrame(x, y, width, height);
	}   
}