package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
	
	GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle();
        solidArea.x = 8*2/3;
        solidArea.y = 8*2/3;
        solidArea.width = 32*2/3;
        solidArea.height = 32*2/3;
        
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){

        x = gp.screenWidth/2 - (gp.tileSize/2);
        y = gp.screenHeight/2 - (gp.tileSize/2) - 32;
        speed = 4;
        direction = DirectionEnum.NONE;
    }

    public void getPlayerImage(){

        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left_2.png"));
            bait = ImageIO.read(getClass().getResourceAsStream("/player/bait.png"));

        }catch (IOException e){
            e.printStackTrace();
        }

    }

	public void update() {
		// CHECK TILE COLLISION
		collisionOn = gp.cChecker.checkTile(this, this.direction);

		if (keyH.upPressed) {
			direction = DirectionEnum.UP;
			if (collisionOn == false)
				y -= speed;
		} else if (keyH.downPressed) {
			direction = DirectionEnum.DOWN;
			if (collisionOn == false)
				y += speed;
		} else if (keyH.leftPressed) {
			direction = DirectionEnum.LEFT;
			if (collisionOn == false)
				x -= speed;
		} else if (keyH.rightPressed) {
			direction = DirectionEnum.RIGHT;
			if (collisionOn == false)
				x += speed;
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
