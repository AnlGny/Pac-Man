package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import tile.Tile;
import tile.TileManager;

public class Monster extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	int colorID;

	public Monster(GamePanel gp, KeyHandler keyH, int colorID) {

		this.gp = gp;
		this.keyH = keyH;
		this.colorID = colorID;

		solidArea = new Rectangle();
		solidArea.x = 8 * 2 / 3;
		solidArea.y = 8 * 2 / 3;
		solidArea.width = 32 * 2 / 3;
		solidArea.height = 32 * 2 / 3;

		getMonsterImage();
	}

	public void setDefaultValues() {

		x = gp.screenWidth / 2 - (gp.tileSize / 2);
		y = gp.screenHeight / 2 - (gp.tileSize / 2 - 32 * 9);
		speed = 1;
		direction = DirectionEnum.NONE;
	}

	public void getMonsterImage() {

		try {
			if (colorID == 1) {
				up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_green_left2.png"));

			} else if (colorID == 2) {
				up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/bot_red_left2.png"));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void update() {
		// CHECK TILE COLLISION
		collisionOn = gp.cChecker.checkTile(this, this.direction);

		if (collisionOn == true) {

			if (direction == DirectionEnum.UP) {
				direction = DirectionEnum.DOWN;
			} else if (direction == DirectionEnum.DOWN) {
				direction = DirectionEnum.UP;
			} else if (direction == DirectionEnum.RIGHT) {
				direction = DirectionEnum.LEFT;
			} else if (direction == DirectionEnum.LEFT) {
				direction = DirectionEnum.RIGHT;
			}
			collisionOn = gp.cChecker.checkTile(this, this.direction);
		}

		Boolean searchForTarget = true;
		List<DirectionEnum> playerDirections = getPlayerDirections();
		for (DirectionEnum playerDirection : playerDirections) {
			if (direction == playerDirection) {
				searchForTarget = false;
				break;
			}
		}

		if (searchForTarget) {
			List<DirectionEnum> freeDirections = getFreeDirections2();

			DirectionEnum targetDirection = DirectionEnum.NONE;
			Boolean exitFor = false;
			
			for (DirectionEnum freeDirection : freeDirections) {
				for (DirectionEnum playerDirection : playerDirections) {
					if (freeDirection == playerDirection) {

						if ((direction == DirectionEnum.DOWN && freeDirection != DirectionEnum.UP)
								|| (direction == DirectionEnum.UP && freeDirection != DirectionEnum.DOWN)
								|| (direction == DirectionEnum.LEFT && freeDirection != DirectionEnum.RIGHT)
								|| (direction == DirectionEnum.RIGHT && freeDirection != DirectionEnum.LEFT)) {

							targetDirection = freeDirection;
							exitFor = true;
							break;
						}
					}
				}
				if (exitFor)
					break;
			}

			if (targetDirection != DirectionEnum.NONE) {

				direction = targetDirection;
				collisionOn = gp.cChecker.checkTile(this, this.direction);
			}
		}

		if (direction == DirectionEnum.UP) {
			if (collisionOn == false)
				y -= speed;
		} else if (direction == DirectionEnum.DOWN) {
			if (collisionOn == false)
				y += speed;
		} else if (direction == DirectionEnum.LEFT) {
			if (collisionOn == false)
				x -= speed;
		} else if (direction == DirectionEnum.RIGHT) {
			if (collisionOn == false)
				x += speed;
		} else if (direction == DirectionEnum.NONE) {
			if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
				direction = DirectionEnum.UP;
			}
		}

		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public List<DirectionEnum> getPlayerDirections() {
		List<DirectionEnum> directionList = new ArrayList<DirectionEnum>();

		if (gp.player.x > this.x)
			directionList.add(DirectionEnum.RIGHT);
		else
			directionList.add(DirectionEnum.LEFT);

		if (gp.player.y > this.y)
			directionList.add(DirectionEnum.DOWN);
		else
			directionList.add(DirectionEnum.UP);

		return directionList;
	}

	public List<DirectionEnum> getFreeDirections2() {
		List<DirectionEnum> directionList = new ArrayList<DirectionEnum>();

		switch (direction) {
		case UP:
		case DOWN:
			if (gp.cChecker.checkTile(this, DirectionEnum.LEFT) == false)
				directionList.add(DirectionEnum.LEFT);
			if (gp.cChecker.checkTile(this, DirectionEnum.RIGHT) == false)
				directionList.add(DirectionEnum.RIGHT);
			break;
		case LEFT:
		case RIGHT:
			if (gp.cChecker.checkTile(this, DirectionEnum.UP) == false)
				directionList.add(DirectionEnum.UP);
			if (gp.cChecker.checkTile(this, DirectionEnum.DOWN) == false)
				directionList.add(DirectionEnum.DOWN);
			break;
		default:
			break;
		}

		return directionList;
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {

		case UP:
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;

		case DOWN:
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case LEFT:
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case RIGHT:
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		default:
			break;
		}
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

	}

}
