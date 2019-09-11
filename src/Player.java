package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.ListIterator;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;

public class Player extends GameObject {

	private Rectangle2D rectangle;
	private int lives = -1;
	private String powerUp = "";
	double [] ballInfo = new double[4];  
	private int ballCount = 0;
	private BufferedImage image;
	private boolean laserEnabled = false;
	private int width;
	private int height;
	private boolean canMulti = false;
	
    public Player(int x, int y, int width, int height, int xSpeed, int ySpeed, BufferedImage image) {
		super(x, y, width, height, xSpeed, ySpeed);
		rectangle = new Rectangle2D.Double(x, y, width, height);
		this.image = image;
		this.height = height;
		this.width = width;
    }
    
    public Rectangle2D getPlayer() {
    	return rectangle;
    }
	
	public void resetSpeed(){
		xSpeed = 0;
	}
	
	public void addLife(int amount){
		lives += amount;
	}
	public int getLives(){
		return lives;
	}
	public boolean checkCollision(Rectangle2D rectangle) {
		return this.rectangle.intersects(rectangle);
	}
	public int getBallCount(){
		return ballCount;
	}
	public boolean getLaserEnabled(){
		return laserEnabled;
	}
	public void resetSpeed(Ball k){
		if (Math.abs(((Ball)k).getYSpeed()) == 1.3 * ((Ball)k).getOriginalYSpeed())
			((Ball)k).setXSpeed(k.getXSpeed() / 1.3);
		if (Math.abs(((Ball)k).getYSpeed()) == 0.7 * ((Ball)k).getOriginalYSpeed())
			((Ball)k).setXSpeed(k.getXSpeed() / 0.7);
		((Ball)k).setYSpeed((int)Math.abs((((Ball)k).getOriginalYSpeed())) * (((Ball)k).getYSpeed()/Math.abs(((Ball)k).getYSpeed())));
	}
    public void resetPlayer(){
		width = 80;
		laserEnabled = false;		
    }
    public void update(ControlPanel panel) {
    	if(laserEnabled == true)
    		image = panel.getImages()[16];
    	else
    		image = panel.getImages()[6];
    		    	
    	ListIterator<GameObject> list = panel.getObjects().listIterator();
    	
    	while(list.hasNext()){
    		
    		GameObject i = list.next();
    		if(i instanceof PowerUp && checkCollision(((PowerUp)i).getPowerUp())){
    			panel.increaseScore(100);
    			powerUp = ((PowerUp)i).returnType();
    			panel.playSound(1);
    			System.out.println(powerUp);
    							
    			if(((PowerUp)i).returnType().equals("Life")){
    				panel.playSound(9);
    				lives++; 
    			}
    			
    			if(i instanceof PowerUp && checkCollision(((PowerUp)i).getPowerUp()) && ((PowerUp)i).returnType().equals("Laser") && laserEnabled == false){
    				panel.addLaser();
    				laserEnabled = true;  	
    			}					
    					    				
  //ONE THING I NOTICED WAS THAT IF YOU GET SPEED UP/DOWN WHEN THE BALL IS CAUGHT, THE SPEED UP /DOWN DOESN'T AFFECT IT AT ALL. since we use angles to calculate based on original speed. SOOOOOO!!
				if(powerUp.equals("Slow") || powerUp.equals("Speed") || powerUp.equals("MultiBall") || powerUp.equals("Enlarge") || powerUp.equals("Catch") || ((PowerUp)i).returnType().equals("Laser")){					
					
					ListIterator<GameObject> secondList = panel.getObjects().listIterator();
					while(secondList.hasNext()){
						GameObject k = secondList.next();
						if(k instanceof Ball){
							if(powerUp.equals("Slow") && k.getYSpeed() != 0.7 * ((Ball)k).getOriginalYSpeed()){//checks if ball is currently slowed
								panel.playSound(5);
								resetSpeed((Ball)k);
								((Ball)k).setSlowDown(true);
								((Ball)k).setSpeedUp(false);
								((Ball)k).setXSpeed(((Ball)k).getXSpeed() * 0.7);
								((Ball)k).setYSpeed(((Ball)k).getOriginalYSpeed() * 0.7 * (((Ball)k).getYSpeed() / Math.abs(((Ball)k).getYSpeed())));
							}
							else if(powerUp.equals("Speed") && k.getYSpeed() != 1.3 * ((Ball)k).getOriginalYSpeed()){//checks if ball is currently sped uup
								panel.playSound(8);
								resetSpeed((Ball)k);
								((Ball)k).setSpeedUp(true);
								((Ball)k).setSlowDown(false);
								((Ball)k).setXSpeed(((Ball)k).getXSpeed() * 1.3);
								((Ball)k).setYSpeed(((Ball)k).getOriginalYSpeed() * 1.3 * (((Ball)k).getYSpeed() / Math.abs(((Ball)k).getYSpeed())));					
							}
							else if(laserEnabled == true && ((PowerUp)i).returnType().equals("Laser")){
								panel.playSound(7);
			    				width = 80;
			    				if(((Ball)k).getXSpeed() == 0)
			    					((Ball)k).setLastCatch(); //this boolean is to allow the user to launch the ball one last time when they a different paddle powerup and have catch before it
			    				else
			    					((Ball)k).setCatch(false);	
			    			}
							else if(powerUp.equals("Enlarge")) {
								panel.playSound(6);
								resetPlayer();
								panel.removeLaser();
								if(((Ball)k).getXSpeed() == 0)
									((Ball)k).setLastCatch();
								else
			    					((Ball)k).setCatch(false);
								width = 120;
							}
							else if(powerUp.equals("MultiBall") && ballCount < 2 && ((Ball)k).returnFlag() == true) {
								panel.playSound(10);
								//reset((Ball)k);
								ballInfo[0] = k.getX();
								ballInfo[1] = k.getY();
								ballInfo[2] = k.getXSpeed();
								ballInfo[3] =  k.getYSpeed();
								canMulti = true;
							}
							else if(powerUp.equals("Catch")) {
								panel.playSound(4);
								resetPlayer();
								panel.removeLaser();
								((Ball)k).setCatch(true);
								//System.out.println("GoFlag: " + ((Ball)k).returnGo());
							}
						}
					}				
					
    			}			
    			list.remove();
    			if(powerUp.equals("MultiBall") && ballCount < 2 && canMulti == true){
					Ball additionalBall = new Ball((int)ballInfo[0], (int)ballInfo[1], panel.getImages()[7].getWidth(),-1 * ballInfo[2], ballInfo[3], panel.getImages()[7]);
					Ball additionalBall2 = new Ball((int)ballInfo[0], (int)ballInfo[1], panel.getImages()[7].getWidth(), 0.5 * ballInfo[2], ballInfo[3], panel.getImages()[7]);
					additionalBall.setFlag(true);
					additionalBall2.setFlag(true);
					
					ListIterator<GameObject> secondList = panel.getObjects().listIterator();
					while(list.hasNext()){
					GameObject k = secondList.next();
						if(k instanceof Ball){
							additionalBall.setOrgY(((Ball)k).getOriginalYSpeed());
							additionalBall2.setOrgY(((Ball)k).getOriginalYSpeed());
							additionalBall.setOrgX(((Ball)k).getOriginalXSpeed());
							additionalBall2.setOrgX(((Ball)k).getOriginalXSpeed());
							//System.out.println("Ball 1 X: " + additionalBall.getOriginalXSpeed() + " Ball 1 Y: " + additionalBall.getOriginalYSpeed());
							if (((Ball)k).returnCatch() == true){
								additionalBall.setCatch(true);
								additionalBall2.setCatch(true);
								additionalBall.setGo(additionalBall.getYSpeed() == 0 ? true : false);
								additionalBall2.setGo(additionalBall2.getYSpeed() == 0 ? true : false);
							}
							break;					
						}
					}
					if(ballCount == 0) {
						list.add(additionalBall);
						list.add(additionalBall2);
						ballCount+=2;
					}
					else {
						list.add(additionalBall);
						ballCount+=1;						
					}
					canMulti = false;	
				}				
    		}
    		// Deals with ALL floor impacts
    		if(i.getY() > panel.getHeight() - 1 - i.getHeight()) {
    			list.remove();
    		
    			if(i instanceof Ball && ballCount > 0)
    				ballCount--;
    		}
    		if(i.getY() < 1 && i instanceof Laser)
    			list.remove();
    	}
		/*ListIterator<GameObject> secondList = panel.getObjects().listIterator();
		while(secondList.hasNext()){
			GameObject k = secondList.next();
			if(k instanceof Ball){
				if (((Ball)k).returnCatch() == true && ((Ball)k).getY() < getY() + width && ((Ball)k).getY() > getY() && ((Ball)k).getX() < getX() + width && ((Ball)k).getX() < getX()) {
					System.out.println("CATCHING");
					((Ball)k).setYSpeed(0);
					((Ball)k).setXSpeed(0);
					((Ball)k).setX(getX() + 40);
					((Ball)k).setY(getY() - 25);
				}
			}
		}*/
							
    	if(x + getXSpeed() > panel.getWidth() - panel.getBorderWidth() - width){ // Left Impact
    		x = panel.getWidth() - 1 - panel.getBorderWidth() - width;
    		setXSpeed(0);
    	}
    	else if(x + getXSpeed() < panel.getBorderWidth()){ // Left Impact
    		x = panel.getBorderWidth();
    		setXSpeed(0);
    	}
    	else { // No Impact
    		x += getXSpeed();
    	}
    }
    
    public void paintComponent(Graphics2D g2) {
    	//image = panel.getImages()[16];
		g2.drawImage(image, x, y, width, height, null);
		rectangle.setFrame(x, y, width, height);
	}

}