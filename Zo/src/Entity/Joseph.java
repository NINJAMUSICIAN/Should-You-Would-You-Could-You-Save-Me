package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemies.Enemy;
import TileMap.TileMap;

public class Joseph extends Enemy{

	private int health;
	private int maxHealth;
	
	private BufferedImage[] sprites;

	public Joseph(TileMap tm){
		
		super(tm);

		
		width = 42;
		height = 38;
		
		cwidth = 0;
		cheight = 0;
		
		facingRight = true;
		
		
		invincible = true;
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/deadJoey.png"));
			
			sprites = new BufferedImage[1];
				for(int i = 0; i < sprites.length; i++){
				
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(400);
		
	}


	public void update(){
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
	}
	

	
	public void draw(Graphics2D g){
	setMapPosition();
	super.draw(g);	
	}
}