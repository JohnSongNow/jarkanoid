package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.ListIterator;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.File;
import javax.imageio.ImageIO;

public class Bomb extends GameObject{
	private int radius;
	private Ellipse2D circle;
	private BufferedImage image;
	private boolean destroyBlocks = false;
	private int frame = 1;
	private boolean explode = false;

 	public Bomb(int x, int y, int radius, double xSpeed, double ySpeed, BufferedImage image) {
		super(x, y, radius, radius, xSpeed, ySpeed);
		this.radius = radius;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.circle = new Ellipse2D.Double(x, y, radius, radius);
		this.image = image;
	}
	
	public boolean checkCollision(Rectangle2D rectangle) {
		return circle.intersects(rectangle);
	}
	public Ellipse2D getBomb(){
		return circle;
	}
	
	public void update(ControlPanel panel){
		ListIterator<GameObject> secondList = panel.getObjects().listIterator();
		while(secondList.hasNext()){
			GameObject k = secondList.next();
			if(k instanceof Blocks){
				if(checkCollision(((Blocks)k).getBlock())){
					panel.playSound(11);
					destroyBlocks = true;
					explode = true;
				}
			}
		}
		if(destroyBlocks == true){
                  panel.SetWMDActive(true);
			ListIterator<GameObject> list = panel.getObjects().listIterator();
			while(list.hasNext()){
				GameObject k = list.next();
				if(k instanceof Blocks){
				  panel.addBlockCount(1);
				  panel.increaseScore(50);
					list.remove();
					}
				}
		}

		y += ySpeed;
		
                if(frame == 16) {
                  panel.SetWMDActive(false);
               }     
	}
	public void paintComponent(Graphics2D g2) {
		g2.drawImage(image, x, y, radius, radius,null);
		circle.setFrame(x, y, radius, radius);
		if(explode){
	        try {
	            BufferedImage image = ImageIO.read(new File("Images/e" + frame + ".png"));
	            g2.drawImage(image, 0, 0, 600, 770, null);
	            Thread.sleep(225);
	            frame++;
          
	        } catch (Exception e) {
	        }
		}
	}
}