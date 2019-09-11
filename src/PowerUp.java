package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.ListIterator;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PowerUp extends GameObject{
	private Rectangle2D rectangle;
	private String type;;
	private BufferedImage image; 

    public PowerUp(int x, int y, double ySpeed, int width, int height, String type, BufferedImage image) {
    	super(x, y, width, height, 0, ySpeed);
    	this.rectangle = new Rectangle2D.Double(x, y, width, height);
    	this.type = type;
    	this.image = image;	
    }
    public String returnType(){
    	return type;
    }
    public void update(ControlPanel panel){
		if(type.equals("Catch"))
			image = panel.getImages()[8];
		else if(type.equals("Slow"))
			image = panel.getImages()[9];
		else if(type.equals("Enlarge"))
			image = panel.getImages()[10];
		else if(type.equals("Laser"))
			image = panel.getImages()[11];
		else if(type.equals("Speed"))
			image = panel.getImages()[12];
		else if(type.equals("Life"))
			image = panel.getImages()[13]; 
		else if (type.equals("MultiBall"))
			image = panel.getImages()[14];   	
    	y += ySpeed;
    }   
    public Rectangle2D getPowerUp() {
    	return rectangle;
    }
    public void paintComponent(Graphics2D g2){
    	g2.drawImage(image, x, y, null);
    	rectangle.setFrame(x, y, width, height);
    }
}