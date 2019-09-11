package src;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Random;
import javax.sound.sampled.*;

public class ControlPanel extends JPanel implements Runnable {
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private boolean startGame = true; //H
	private boolean running = true;
	private int blockDestroyed = 0; //H
	private JPanel scoreboard = new ScoreBoardPanel();// MAY 8th CHANGE MADE NEW CLASS, AT THE BOTTOM
	private JPanel bigPanel = new TotalPanel();// MAY 8th CHANGE MADE NEW CLASS, AT THE BOTTOM
	private int score = 0;
	private JLabel scoreLabel = new JLabel("Score" + score);
	Random random = new Random();
	private MouseListener laser = new FireLaser();
	private MouseMotionListener mouseMove = new mouseMove();
	private KeyListener bomb = new LaunchBomb();
	private BufferedImage image; //H
	private boolean WMDEnabled = false;
	private boolean WMDActive = false;
	private	BufferedImage [] imageList = new BufferedImage [18];{	
		for(int i = 0; i < 6; i++){
			try{ 
				imageList[i] = ImageIO.read(new File("Images/" + "block" + (i + 1) + ".png"));
				imageList[i + 8] = ImageIO.read(new File("Images/" +  "powerUp" + (i + 1) + ".png"));
				imageList[6] = ImageIO.read(new File("Images/" +  "ship.png"));
				imageList[7] = ImageIO.read(new File("Images/" +  "ball.png"));
				imageList[14] = ImageIO.read(new File("Images/" +  "powerUp7.png"));
				imageList[15] = ImageIO.read(new File("Images/" +  "backgroundFinal.jpg"));
				imageList[16] = ImageIO.read(new File("Images/" + "laserShip.png"));
				imageList[17] = ImageIO.read(new File("Images/" + "Head.png"));
			}// 6 is Player, 7 is ball
				catch(Exception e){
	                            System.out.println(e.getMessage());
					System.out.println("Image Missing!");
			}
		}
	}
	private int laserCount = 0;
	private JFrame frame;
	private EmptyBorder empty = new EmptyBorder(15,15,15,15);
	
	public ControlPanel() {
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS)); //CHANGE MAY 3
		frame = new JFrame("Brick Breaker");
		resetLevel();
		bigPanel.setSize(600,750);	
		frame.setVisible(true);
		frame.setSize(600, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addKeyListener(new ControlPlayer());
		frame.addMouseListener(new LaunchBall());
		frame.addMouseMotionListener(new mouseMove()); //H
		frame.addKeyListener(new Pause());
		frame.setIconImage(new ImageIcon(getClass().getResource("../Images/Icon.png")).getImage());
		scoreLabel.setForeground(Color.WHITE);//MAY 3
		scoreboard.add(scoreLabel);
		scoreboard.setBackground(Color.black);//CHANGE MAY 3
		//empty = (BorderFactory.createEmptyBorder(15,15,15,15)); //CHANGE MAY 3
		Border blackLine = BorderFactory.createLineBorder(Color.WHITE, 2); //CHANGE MAY 3
		scoreboard.setBorder(BorderFactory.createCompoundBorder(empty, blackLine));//CHANGE MAY 3
		setBorder(BorderFactory.createCompoundBorder(empty, blackLine)); //CHANGE MAY 3
		setPreferredSize(new Dimension(600, 750));
		scoreboard.setOpaque(false);
		setOpaque(false);
		bigPanel.add(this);
		bigPanel.add(scoreboard);			
		frame.add(bigPanel);
		frame.setVisible(true);
		setFocusable(true);
		frame.pack();
		playSound(3);
		run();
		
	}
	public void increaseScore(int amount) {
		score += amount;
	}
	public void addBlockCount(int blockCount) { //HH
		blockDestroyed += blockCount;
	}
	public int getBorderWidth(){
		return empty.getBorderInsets().top;
	}
	public void addLaser(){
		frame.addMouseListener(laser);
	}
	
	public void resetLevel(){
		ArrayList<GameObject> resetList = new ArrayList<GameObject>();
		resetList.add(new Player(250 - 20, 650, 80, 20, 0 , 0, imageList[6]));
		resetList.add(new Ball(resetList.get(0).getX() + resetList.get(0).getWidth() / 2 - 15, 625 - 15, 15, 5, 8, imageList[7]));	
		for(int y = 0; y < 11; y++){
			int blockLife = random.nextInt(6) + 1;
			blockLife = 1;
			for(int x = 0; x < 11; x++)							
				resetList.add(new Blocks(x * 50 + 32, y * 30 + 54, 40, 20, blockLife, imageList[blockLife - 1]));
		}
		objects = resetList;
		startGame = false; //H
		((Player)objects.get(0)).addLife(4);// lives start at -1, beacause of reset hapens when lives at -1
		score = 0;
		blockDestroyed = 0;
	}
	public BufferedImage [] getImages(){
		return imageList;
	}
	public ArrayList <GameObject> getObjects(){
		return objects;
	}
	public void removeLaser(){
		frame.removeMouseListener(laser);
	}
	
	public void SetWMDActive(boolean active) {
	  this.WMDActive = active;
	}
	
	public void playSound(int sound) {
		String file = "";
		
		switch (sound) {
			case 0:
				file = "Gun Blast";
				break;
			case 1:
				file = "PowerUp";
				break;
			case 2:
				file = "Laser";
				break;
			case 3:
				file = "BGM";
				break;
			case 4:
				file = "PowerUpCatch";
				break;
			case 5:
				file = "PowerUpSlow";
				break;
			case 6:
				file = "PowerUpEnlarge";
				break;
			case 7:
				file = "PowerUpLaser";
				break;
			case 8:
				file = "PowerUpSpeed";
				break;
			case 9:
				file = "PowerUpLife";
				break;
			case 10:
				file = "PowerUpMultiBall";
				break;
			case 11:
				file = "NukeExplosion";
				break;
		}
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Sounds/" + file + ".wav"));
         	Clip clip = AudioSystem.getClip();
         	clip.open(audioIn);
         	FloatControl gainControl = 
         	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         	gainControl.setValue(-20.0f); // Reduce volume by 30 decibels.
         	if(sound != 3)
         		clip.start();
         	else{
         		clip.start();
         		clip.loop(clip.LOOP_CONTINUOUSLY);  
         	}
		}
		catch (Exception e) {
		}
	}
	
    synchronized void resume() {
    	notify();
    }
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(16); //  60 ticks/second
				if(running == false){
					synchronized(this){
						while(running == false)
							wait();
					}
				}
			}	catch (Exception e) {
					System.out.println("SOmething went WRong");
			}
			int ballCount = 0;
			Object[] list = new Object[objects.size()];
			list = objects.toArray();
			for (Object i : list) {
				if(i instanceof Ball)
					ballCount++;
				((GameObject)i).update(this);
			}
			scoreLabel.setText(("Score: " + score));
			scoreboard.repaint();
			bigPanel.repaint();
			repaint();
			if(ballCount == 0){
				ListIterator<GameObject> list2 = objects.listIterator();
				while(list2.hasNext()){
					GameObject i = list2.next();
					if(i instanceof PowerUp){
						list2.remove();
					}
				}
				((Player)objects.get(0)).addLife(-1);		
				((Player)objects.get(0)).resetPlayer();
				removeLaser();
				if(((Player)objects.get(0)).getLives() != -1)
					objects.add(new Ball(objects.get(0).getX() + objects.get(0).getWidth() / 2 - 15, 625 - 15, 15, 5, 8, imageList[7]));	
			}		
			if (((Player)objects.get(0)).getLives() == -1 && startGame == false) { //H
				frame.removeMouseMotionListener(mouseMove); //ADDED REMOVING LISTENERS.. may 29th
				if (JOptionPane.showConfirmDialog(null, "You have lost, your score is: " + score + "\n Would you like to play again?", "Message", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					System.exit(0);
					resetLevel();
			}

			if (blockDestroyed >= 121 && WMDActive == false) {
				frame.removeMouseMotionListener(mouseMove);
				if (JOptionPane.showConfirmDialog(null, "Congratulations, you have won the game! Your score is: " + score + "\n Would you like to play again?", "Message", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					System.exit(0);
				resetLevel();
				blockDestroyed = 0;
				frame.addMouseMotionListener(mouseMove);					
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g; 
		Object[] list = new Object[objects.size()];
		list = objects.toArray();
		for (Object i : list) 
			((GameObject)i).paintComponent(g2);	
	}
	public static void main(String[] args) {	
			ControlPanel control = new ControlPanel();
	}
	
	private class ControlPlayer implements KeyListener {
		public void keyPressed(KeyEvent event) {
			
			switch(event.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					((Player)objects.get(0)).setXSpeed(-12);	//changed speed to 12 from 8		
					break;
				case KeyEvent.VK_RIGHT:
					((Player)objects.get(0)).setXSpeed(12);
					break;
				case KeyEvent.VK_SPACE: //keep updated/duplicated with MouseListener
					try{
						ListIterator<GameObject> list = getObjects().listIterator();
						while(list.hasNext()){
							GameObject i = list.next();
							if(i instanceof Ball){
								if (((Ball)i).returnFlag() == false || (((Ball)i).returnCatch() == true && (((Ball)i).returnGo() == true))) {
									((Ball)i).setYSpeed(-Math.abs(((Ball)i).getOriginalYSpeed()));
									double coefficent = Math.atan2(getObjects().get(0).getHeight(), Math.abs(getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getWidth()));
									((Ball)i).setXSpeed(-Math.abs(((Ball)i).getOriginalXSpeed()));
									double xSpeed = 0;
									if (((Ball)i).getSpeedUp() == true) {
										xSpeed = getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getHeight() > 0 ? -((Math.pow((Math.cos(coefficent)), 1)) * 1.3 * 1.5 *((Ball)i).getOriginalXSpeed()) : ((Math.pow((Math.cos(coefficent)), 1)) * 1.3 * 1.5 *((Ball)i).getOriginalXSpeed());
									}
									else if(((Ball)i).getSlowDown() == true) {
										xSpeed = getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getHeight() > 0 ? -((Math.pow((Math.cos(coefficent)), 1)) * 0.7 *((Ball)i).getOriginalXSpeed()) : ((Math.pow((Math.cos(coefficent)), 1)) * 0.7 *((Ball)i).getOriginalXSpeed());
									}
									else
										xSpeed = getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getHeight() > 0 ? -((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed()) : ((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed());
									//double xSpeed = getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getHeight() > 0 ? - 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed()): 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed());
									i.setXSpeed(xSpeed);
									System.out.println("Ball's speed when catching: " + ((Ball)i).getXSpeed());
									if (((Ball)i).getLastCatch() == true) {
										((Ball)i).setLastCatch();
										((Ball)i).setCatch(false);
									}
									((Ball)i).setGo(false);
									((Ball)i).setFlag(true);
								}				
							}
						}
					}
					catch(Exception e){
					}
				break;
				
				case KeyEvent.VK_F5: //H
				startGame = true;
				resetLevel();
				break;
			}
		}
	
		public void keyReleased(KeyEvent event) {
			((Player)objects.get(0)).resetSpeed();
		}		
		public void keyTyped(KeyEvent event) {
		}
	}
	private class LaunchBall implements MouseListener{
		public void mouseClicked(MouseEvent Event){
			try{
				ListIterator<GameObject> list = objects.listIterator();
				while(list.hasNext()){
					GameObject i = list.next();
					if(i instanceof Ball){
						if (((Ball)i).returnFlag() == false || (((Ball)i).returnCatch() == true && (((Ball)i).returnGo() == true))) {
							((Ball)i).setYSpeed(-Math.abs(((Ball)i).getOriginalYSpeed()));
							double coefficent = Math.atan2(getObjects().get(0).getHeight(), Math.abs(getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getWidth()));
							((Ball)i).setXSpeed(-Math.abs(((Ball)i).getOriginalXSpeed()));
							double xSpeed = getObjects().get(0).getX() + getObjects().get(0).getWidth() / 2 - i.getX() - i.getHeight() > 0 ? - 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed()): 1.5 *((Math.pow((Math.cos(coefficent)), 1)) * ((Ball)i).getOriginalXSpeed());
							i.setXSpeed(xSpeed);
							if (((Ball)i).getLastCatch() == true) {
								((Ball)i).setLastCatch();
								((Ball)i).setCatch(false);
							}
							((Ball)i).setGo(false);
							((Ball)i).setFlag(true);
						}				
					}
				}
			}
			catch(Exception e){
			}
				
		}
		public void mousePressed(MouseEvent Event){
		}
		public void mouseReleased(MouseEvent Event){
		}
		public void mouseEntered(MouseEvent Event){
		}
		public void mouseExited(MouseEvent Event){
		}
	}
	
	public class mouseMove implements MouseMotionListener { //HH

		public void mouseMoved(MouseEvent e) {
			//if(getWidth() - getBorderWidth() - objects.get(0).getWidth() > e.getX() && e.getX() > getBorderWidth()){
				int counter = 0;
				int[] distance = {0,0,0};
				
				ListIterator<GameObject> list = objects.listIterator();
				while(list.hasNext()){
					GameObject i = list.next();
					if(i instanceof Ball){
					//	if(((Ball)i).getY() == (objects.get(0).getY() - i.getWidth() - 1)){
						counter++;
						distance[counter - 1] = i.getX() - objects.get(0).getX();
					}
				}
				counter = 0;		
				ListIterator<GameObject> list2 = objects.listIterator();
				while(list2.hasNext()){
					GameObject i = list2.next();
					if(i instanceof Ball){
					//	if(((Ball)i).getY() == (objects.get(0).getY() - i.getWidth() - 1)){
						counter++;
						//	if(((Ball)i).getY() == (objects.get(0).getY() - i.getWidth() - 1)){
						//		i.setX(distance[counter - 1] + objects.get(0).getX());
								//System.out.println("Ball moving" + counter + " " + i.getX()); //multiball wontmove with the paddle. sklfejf 
						//	}
							if(e.getX() < getBorderWidth())
								((Player)objects.get(0)).setX(getBorderWidth());
							else if(getWidth() - getBorderWidth() - objects.get(0).getWidth() < e.getX())				
								((Player)objects.get(0)).setX(getWidth() - 1 - getBorderWidth() - objects.get(0).getWidth());
							else
								((Player)objects.get(0)).setX(e.getX());
							if(((Ball)i).getY() == (objects.get(0).getY() - i.getWidth() - 1)){
								i.setX(distance[counter - 1] + objects.get(0).getX());
								//System.out.println("alala");
							}
							//System.out.println("distance: " + counter + "  " + (objects.get(0).getX()));
					//	}
					}
				}
			}		   
	    public void mouseDragged(MouseEvent e) {
	    }
	}
	private class FireLaser implements MouseListener {
		public void mouseClicked(MouseEvent Event){
			playSound(2);
			GameObject [] list = objects.toArray(new GameObject [objects.size() + 2]);
			Laser laser1 = (new Laser(objects.get(0).getX(), objects.get(0).getY(), 5, 20, - 20, imageList[4]));
			Laser laser2 = (new Laser(objects.get(0).getX() + objects.get(0).getWidth(), objects.get(0).getY(), 5, 20, - 20, imageList[4]));		
			list[objects.size()] = laser1;
			list[objects.size() + 1] = laser2;
			objects = new ArrayList<GameObject>(Arrays.asList(list));
		}
		public void mousePressed(MouseEvent Event){
		}
		public void mouseReleased(MouseEvent Event){
		}
		public void mouseEntered(MouseEvent Event){
		}
		public void mouseExited(MouseEvent Event){
		}
	}

	private class Pause implements KeyListener{
		public void keyPressed(KeyEvent event){
			switch(event.getKeyCode()){
				case KeyEvent.VK_P:
				running = running ? false : true;
				if(running == true)
					resume();
				break;
				
				case KeyEvent.VK_Q:
				if(WMDEnabled == false)
					frame.addKeyListener(bomb);
				WMDEnabled = true;
				break;
			}
		}
		public void keyReleased(KeyEvent event){
		}
		public void keyTyped(KeyEvent event){
		}
	}
	private class LaunchBomb implements KeyListener{
		public void keyPressed(KeyEvent event){
			switch(event.getKeyCode()){
				case KeyEvent.VK_O:
				GameObject [] list = objects.toArray(new GameObject [objects.size() + 1]);
				Bomb nuke = (new Bomb(objects.get(0).getX(), objects.get(0).getY() + objects.get(0).getWidth() / 2, 40, 0, - 4, imageList[17]));
				list[objects.size()] = nuke;
				objects = new ArrayList<GameObject>(Arrays.asList(list));
				frame.removeKeyListener(bomb);
			}
		}
		public void keyReleased(KeyEvent event){
		}
		public void keyTyped(KeyEvent event){
		}
	}
	private class ScoreBoardPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g; 
			for(int i = 0; i < ((Player)objects.get(0)).getLives(); i++)
				g2.drawImage(imageList[6], (int)(i * 42) + 25, (int)(getHeight() * 0.40), 40, 12, null);
		}			
	}
	private class TotalPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g; 
			g2.drawImage(imageList[15], 0, 0, null);
		}			
	}
}	

