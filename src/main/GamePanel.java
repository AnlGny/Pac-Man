package main;

import entity.Monster;
import entity.Player;
import entity.Entity.DirectionEnum;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
	
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	public final int scale = 2;

	public final int tileSize = originalTileSize * scale; // 32x32 tile
	public final int maxScreenCol = 19;
	public final int maxScreenRow = 23;
	public final int screenWidth = tileSize * maxScreenCol; // 608 pixels
	public final int screenHeight = tileSize * (maxScreenRow + 2); // 736 pixels

	public boolean gameOver = false;
	
	// FPS
	int FPS = 60;

	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public TextInformations textInfos = new TextInformations(this);
	public Points points = new Points(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this, keyH);
	public Monster monster = new Monster(this, keyH, 1);
	public Monster monster2 = new Monster(this, keyH, 2);

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		setMonsters();
	}

	
	public void setMonsters()
	{
		monster.setDefaultValues();
		monster.x = monster.x - 32*4;
		monster2.setDefaultValues();
		monster2.x = monster.x + 32*8;
	}
	
	public void restartGame() {
		
		gameOver = false;
		textInfos.lives = 3;
		Points.totalPoints = 0;
		tileM.loadMap();
		setMonsters();
	}
	
	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

    // Calculates how many times game will be displayed in a second = 60FPS
    @Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			if (gameOver && keyH.f2Pressed)
			{
				restartGame();
			}
			
			

		}
    }

	public void update() {

		player.update();
		monster.update();
		monster2.update();

		if (player != null) {
			textInfos.liveCounter();
			points.checkPoints(player);
			
			
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		
		
		tileM.draw(g2);
		player.draw(g2);
		monster.draw(g2);
		monster2.draw(g2);

		textInfos.drawText(g2);
		
	
		
		
		g2.dispose();

	}

}
