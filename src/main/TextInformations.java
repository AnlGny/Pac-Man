package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


import entity.Entity.DirectionEnum;
import tile.TileManager;

public class TextInformations {

	BufferedImage hearth;

	GamePanel gp;
	TileManager tileM;
	int lives = 3;
	int point = 0;
	int maxPoints = 1280;

	public TextInformations(GamePanel gp) {

		this.gp = gp;

		this.getHearthImage();

	}

	public void drawText(Graphics2D g2) {

		if (gp.monster.direction == DirectionEnum.NONE) {

			displayGameStart(g2);

		} else {

			if (lives >= 0) {

				if (Points.totalPoints == maxPoints) {
					displayYouWon(g2);
				}

				else {

					displayPoints(g2);

					if (lives >= 1)
						g2.drawImage(hearth, 32 * 5 + 8, 735, gp.tileSize, gp.tileSize, null);
					if (lives >= 2)
						g2.drawImage(hearth, 32 * 6 + 8, 735, gp.tileSize, gp.tileSize, null);
					if (lives >= 3)
						g2.drawImage(hearth, 32 * 7 + 8, 735, gp.tileSize, gp.tileSize, null);
				}
			}

			else if (lives < 0) {

				gp.gameOver = true;

				displayGameOver(g2);

			}

		}
	}

	public void getHearthImage() {

		try {

			hearth = ImageIO.read(getClass().getResourceAsStream("/tiles/hearth.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void liveCounter() {

		int entityCenterX = gp.player.x + (gp.tileSize / 2);
		int entityCenterY = gp.player.y + (gp.tileSize / 2);

		int entityCol = entityCenterX / gp.tileSize;
		int entityRow = entityCenterY / gp.tileSize;

		int monster1CenterX = gp.monster.x + (gp.tileSize / 2);
		int monster1CenterY = gp.monster.y + (gp.tileSize / 2);

		int monster1Col = monster1CenterX / gp.tileSize;
		int monster1Row = monster1CenterY / gp.tileSize;

		int monster2CenterX = gp.monster2.x + (gp.tileSize / 2);
		int monster2CenterY = gp.monster2.y + (gp.tileSize / 2);

		int monster2Col = monster2CenterX / gp.tileSize;
		int monster2Row = monster2CenterY / gp.tileSize;

		if ((entityCol == monster1Col && entityRow == monster1Row)
				|| (entityCol == monster2Col && entityRow == monster2Row)) {
			lives--;

			gp.player.x = gp.screenWidth / 2 - (gp.tileSize / 2);
			gp.player.y = gp.screenHeight / 2 - (gp.tileSize / 2) - 32;

			gp.monster.x = 5 * gp.tileSize;
			gp.monster.y = 21 * gp.tileSize;

			gp.monster2.x = 13 * gp.tileSize;
			gp.monster2.y = 21 * gp.tileSize;
		}

	}

	public void displayPoints(Graphics2D g2) {

		g2.setColor(Color.WHITE);
		Font font = new Font("Monospaced", Font.PLAIN, 32);
		g2.setFont(font);
		g2.drawString("Lives", 32 * 2, 760);
		g2.drawString("Point: ", 32 * 12, 760);

		String pointCounter = Integer.toString(Points.totalPoints);

		g2.drawString(pointCounter, 32 * 16, 760);

	}

	public void displayGameOver(Graphics2D g2) {

		g2.setColor(Color.BLACK);
		g2.fillRoundRect(4 * gp.tileSize, 5 * gp.tileSize, gp.tileSize * 11, gp.tileSize * 1, 50, 50);

		g2.setColor(Color.YELLOW);
		Font font1 = new Font("Monospaced", Font.PLAIN, 28);
		g2.setFont(font1);
		g2.drawString("PRESS F2 TO RESTART", 32 * 5 - 16, 185);

		g2.setColor(Color.WHITE);
		Font font2 = new Font("Monospaced", Font.PLAIN, 25);
		g2.setFont(font2);
		g2.drawString("Best Score: ", 32 * 7 - 24, 780);
		String pointCounter = Integer.toString(Points.totalPoints);

		g2.drawString(pointCounter, 32 * 12 - 16, 782);

		g2.setColor(Color.RED);
		Font font3 = new Font("Monospaced", Font.PLAIN, 45);
		g2.setFont(font3);
		g2.drawString("GAME IS OVER!", 32 * 5 - 16, 750);

		gp.player.x = 9 * gp.tileSize;
		gp.player.y = 11 * gp.tileSize;

		gp.monster.x = 12 * gp.tileSize;
		gp.monster.y = 11 * gp.tileSize;

		gp.monster2.x = 6 * gp.tileSize;
		gp.monster2.y = 11 * gp.tileSize;

	}

	public void displayGameStart(Graphics2D g2) {

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);
		Font font = new Font("Monospaced", Font.PLAIN, 32);
		g2.setFont(font);

		g2.drawString("Please press any key to start", 32, 764);

	}

	public void displayYouWon(Graphics2D g2) {

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.YELLOW);
		Font font = new Font("Monospaced", Font.PLAIN, 32);
		g2.setFont(font);
		g2.drawString("You Win!", 32 * 8 - 20, 750);

		g2.setColor(Color.BLACK);
		g2.fillRoundRect(4 * gp.tileSize, 5 * gp.tileSize, gp.tileSize * 11, gp.tileSize * 1, 50, 50);
		g2.setColor(Color.YELLOW);
		g2.drawRoundRect(gp.tileSize * 5, gp.tileSize * 22 + 16, gp.tileSize * 9, gp.tileSize * 3 - 25, 50, 50);

		g2.setColor(Color.YELLOW);
		Font font1 = new Font("Monospaced", Font.PLAIN, 28);
		g2.setFont(font1);
		g2.drawString("PRESS F2 TO RESTART", 32 * 5 - 16, 185);

		g2.setColor(Color.WHITE);
		Font font2 = new Font("Monospaced", Font.PLAIN, 25);
		g2.setFont(font2);
		g2.drawString("Best Score: ", 32 * 7 - 24, 780);
		String pointCounter = Integer.toString(Points.totalPoints);

		g2.drawString(pointCounter, 32 * 12 - 16, 782);

		gp.player.x = 9 * gp.tileSize;
		gp.player.y = 11 * gp.tileSize;

		gp.monster.x = 12 * gp.tileSize;
		gp.monster.y = 11 * gp.tileSize;

		gp.monster2.x = 6 * gp.tileSize;
		gp.monster2.y = 11 * gp.tileSize;

	}

}
