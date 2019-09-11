package src;
import java.awt.*;
import java.awt.geom.*;
import java.util.ListIterator;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ball extends GameObject {
	
	private int radius;
	private Ellipse2D circle;
	protected double orgXSpeed;
	protected double orgYSpeed;
	private boolean start = false;
	private boolean catchFlag = false;
	private boolean goFlag = true;
	private BufferedImage image;
	private int distance;
	private boolean lastCatch = false;
	private boolean slowDown = false;
	private boolean speedUp = false;
	
	public Ball(int x, int y, int radius, double xSpeed, double ySpeed, BufferedImage image) {
		super(x, y, radius, radius, xSpeed, ySpeed);
		this.radius = radius;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.circle = new Ellipse2D.Double(x, y, radius, radius);
		orgXSpeed = xSpeed;
		orgYSpeed = ySpeed;
		this.image = image;
	}

	public boolean checkCollision(Rectangle2D rectangle) {
		return circle.intersects(rectangle);
	}
	public Ellipse2D getBall(){
		return circle;
	}
	public boolean returnFlag() {
		return start;
	}
	public void setFlag(boolean bool) {
		start = bool;
	}
	public boolean returnCatch() {
		return catchFlag;
	}
	public void setCatch(boolean bool) {
		catchFlag = bool;
	}
	public void setGo(boolean bool) {
		goFlag = bool;
	}
	public boolean returnGo() {
		return goFlag;
	}
	public void setOrgX(double speed) {
		orgXSpeed = speed;
	}
	public void setOrgY(double speed) {
		orgYSpeed = speed;
	}
	
	public double getOriginalXSpeed(){
		return orgXSpeed;
	}
	
	public double getOriginalYSpeed(){
		return orgYSpeed;
	}
	public int getDistanceFromMidpoint(){
		return distance;
	}
    public void setLastCatch() {
    	lastCatch = lastCatch == true ? false : true;
    }
    public boolean getLastCatch() {
    	return lastCatch;
    }
    public boolean getSlowDown() {
    	return slowDown;
    }
    public void setSlowDown(boolean bool) {
    	slowDown = bool;
    }
    public boolean getSpeedUp() {
    	return speedUp;
    }
    public void setSpeedUp(boolean bool) {
    	speedUp = bool;
    }
    
	public void update(ControlPanel panel) {
		
			ListIterator<GameObject> secondList = panel.getObjects().listIterator();
			while(secondList.hasNext()){
				GameObject k = secondList.next();
				if(k instanceof Player){
					//distance = x + radius - (panel.getObjects().get(0).getX() + panel.getObjects().get(0).getWidth() / 2);
					ListIterator<GameObject> list = panel.getObjects().listIterator();
					while(list.hasNext()){
						GameObject i = list.next();
						if(i instanceof Ball){ //start boolean is for the launching part ONLY, catchflag is to see if hte player has the upgrade, 
						//goFlag is for seeing if there is a collision <- probably should rename but i'm too lazy.
							if (start == false || catchFlag == true && start == true && goFlag == true) {
								setYSpeed(0);
								//setX(distance - panel.getObjects().get(0).getX() - panel.getObjects().get(0).getWidth() / 2);
								setXSpeed((panel.getObjects().get(0).getXSpeed()));
								
								if(catchFlag != true) {
	                                                              setX(((Player)k).getX() + ((Player)k).getWidth() / 2 - radius / 2);
								}

								setY(k.getY() - radius - 1);
							}
						}
					}
				}
			}

		ListIterator<GameObject> list = panel.getObjects().listIterator();
		while(list.hasNext()){
			GameObject i = list.next();
			if(i instanceof Player){
				if(circle.intersects(((Player)i).getPlayer())){
						goFlag = catchFlag == true ? true : false;
							//System.out.println("X: " + getXSpeed() + " xPaddle: " + i.getXSpeed());
							//System.out.println("Y: " + getYSpeed() + " xPaddle: " + i.getYSpeed());
					/*
				//	if(i.getX() >= x -2  && getXSpeed() >= 0 || i.getX() + i.getWidth() <= x + 3 && getXSpeed() < 0 || i.getX() + i.getWidth() == x - 2 && getXSpeed() < 0 ||  i.getX() + i.getWidth() == x - 1 && getXSpeed() < 0) {
					if (x >= i.getX() + i.getWidth() - 10 && x <= i.getX() + i.getWidth() + 15 && getXSpeed() <= 0 || x >= i.getX() - 15 && x <= i.getX() + 10 && getXSpeed() >= 0 ) {
					y = i.getY() - i.getHeight() - 1;
						setYSpeed(getYSpeed() * -1);				
						setXSpeed(getXSpeed() * -1);  	
					} ///fix where if going from right or left, it will bounce back same way. 
					else {
						double angle = 0;
						angle = Math.atan( (getX()-i.getX()) > 0 ? Math.abs((getY()-i.getY())/(getX()-i.getX())) : 1);
					//	angle = 30*Math.abs((getY()-i.getY())/(getX()-i.getX() > 0 ? getX()-i.getX() : 1)) + 2/i.getWidth()*(Math.abs(i.getX() - getX())); 
						y = i.getY() - i.getHeight() - 1;
						setYSpeed(getYSpeed() * -1);	
					//	setXSpeed(getXSpeed()? 0.1 * Math.sqrt(Math.abs(i.getXSpeed()))* Math.cos(angle) + (i.getXSpeed() >=0  : -0.1* Math.cos(angle) + (i.getXSpeed() >=0  * Math.sqrt(Math.abs(i.getXSpeed()))));
						
						setXSpeed((getXSpeed()*Math.cos(angle) + (i.getXSpeed() >=0 ? 0.1 * Math.sqrt(Math.abs(i.getXSpeed())) : -0.1 * Math.sqrt(Math.abs(i.getXSpeed())))));
					}
					//	System.out.println(getXSpeed());
					*/
					y = i.getY() - i.getHeight() - 1;
					double coefficent = Math.atan2(panel.getObjects().get(0).getHeight(), Math.abs(panel.getObjects().get(0).getX() + panel.getObjects().get(0).getWidth() / 2 - getX() - radius / 2));
					//Checks whether there is speed up or slow down and set xSpeed and ySpeed accordingly
					if(Math.abs(ySpeed) == Math.abs(orgYSpeed) * 1.3)				
						xSpeed = panel.getObjects().get(0).getX() + panel.getObjects().get(0).getWidth() / 2 - getX() - radius > 0 ? - 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed * 1.3): 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed * 1.3);
					else if(Math.abs(ySpeed) == Math.abs(orgYSpeed) * 0.7)				
						xSpeed = panel.getObjects().get(0).getX() + panel.getObjects().get(0).getWidth() / 2 - getX() - radius > 0 ? - 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed * 0.7): 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed * 0.7);	
					else
						xSpeed = panel.getObjects().get(0).getX() + panel.getObjects().get(0).getWidth() / 2 - getX() - radius > 0 ? - 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed): 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * orgXSpeed);	
					ySpeed *= -1;
					//}
				}
			}
			else if(i instanceof Blocks){
				if(checkCollision(((Blocks)i).getBlock())){
					if(i.getY()  + 1 < y && y < i.getY() + i.getHeight() - 1)
						setYSpeed(getYSpeed() * -1);
					setXSpeed(getXSpeed() * -1);				
					((Blocks)i).subtractLife(1);
						panel.increaseScore(50);
					if(((Blocks)i).returnLife() == 0){
						list.remove();
						panel.addBlockCount(1); //H
						Random random = new Random();
						int spawnPercentage = random.nextInt(100) + 1;
						panel.playSound(0);
						if(spawnPercentage >= 80)
							list.add(((Blocks)i).spawnPowerUp());		
					}								
				}
			}
		}
		// Left wall impact
		if (x + getXSpeed() < panel.getBorderWidth()) {
			setXSpeed(getXSpeed() * -1);
			x = panel.getBorderWidth() + 1;
		}
		// Right wall impact
		else if (x +  getXSpeed() > panel.getWidth() - panel.getBorderWidth() - radius - 1) {
			x = panel.getWidth() - panel.getBorderWidth() - radius - 1;
			setXSpeed(getXSpeed() * -1);
		}
		// No horizontal impact
		else {
			x +=  getXSpeed();
		}
		// Ceiling impact
		if(y < panel.getBorderWidth()){
			y = panel.getBorderWidth() + 1;
			setYSpeed(getYSpeed() * -1);
		}
		// No vertical impact
		else {
			y += getYSpeed();
		}
	}
	
	public void paintComponent(Graphics2D g2) {
		g2.drawImage(image, x, y, null);
		circle.setFrame(x, y, radius, radius);
	}
}