package src;
import java.awt.*;
import java.awt.geom.*;

public abstract class GameObject {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected double xSpeed;
	protected double ySpeed;
	protected double orgXSpeed;
	protected double orgYSpeed;
		
	public GameObject(int x, int y, int width, int height, double xSpeed, double ySpeed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setXSpeed(double newSpeed){
		xSpeed = newSpeed;
	}
	
	public void setYSpeed(double newSpeed){
		ySpeed = newSpeed;
	}
	
	public double getXSpeed(){
		return xSpeed;
	}
	
	public double getYSpeed(){
		return ySpeed;
	}
		
	public abstract void update(ControlPanel panel);
		
	public abstract void paintComponent(Graphics2D g2);	
		
}