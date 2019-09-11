package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.ListIterator;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Laser extends GameObject{
	private Rectangle2D rectangle;
	private BufferedImage image;
	private int ySpeed;

    public Laser(int x, int y, int width, int height, int ySpeed, BufferedImage image) {
		super(x, y, width, height, 0, ySpeed);
		rectangle = new Rectangle2D.Double(x, y, width, height);
		this.image = image;
		this.ySpeed = ySpeed;
    }
     public Rectangle2D getLaser(){
     	return rectangle;
     }
     
     public void update(ControlPanel panel){	
     	ListIterator<GameObject> list = panel.getObjects().listIterator();
     		while(list.hasNext()){
     			GameObject i = list.next();
     			if(i instanceof Blocks){
					if(rectangle.intersects(((Blocks)i).getBlock())){
						((Blocks)i).subtractLife(1);
						panel.increaseScore(50);
						if(((Blocks)i).returnLife() <= 0){
							list.remove();
							panel.addBlockCount(1); //H
							Random random = new Random();
							int spawnPercentage = random.nextInt(100) + 1;
							if(spawnPercentage >= 80)
								list.add(((Blocks)i).spawnPowerUp());
						}								
					}
     			}
     		}
     		y += ySpeed;
     }
     
     public void paintComponent(Graphics2D g2) {
		g2.drawImage(image, x, y, 5, 20,null);
		rectangle.setFrame(x, y, width, height);
	}
}