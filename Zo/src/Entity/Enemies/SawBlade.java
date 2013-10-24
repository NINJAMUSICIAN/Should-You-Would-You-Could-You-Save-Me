package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import TileMap.TileMap;

public class SawBlade extends Enemy{

	private BufferedImage[] sprites;
	
	public SawBlade(TileMap tm) {
		super(tm);
	
		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		width = 32;
		height = 32;
		cwidth = 30;
		cheight = 30;
		
		health = maxHealth = -1;
		damage = 2;
		invincible = true;
		
		deathScore = 0;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/SawBlade.png"));
			sprites = new BufferedImage[2];
			
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		right = true;
		facingRight = true;
	}
	
	public void update(){
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		animation.update();
	}

	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
