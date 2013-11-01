package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import TileMap.TileMap;

public class Spit extends MapObject{
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	
	public Spit(String dir, TileMap tm){
		super(tm);
		
		if(dir.equals("right")){
			facingRight = true;
			facingUp = false;
			facingDown = false;
		} else if(dir.equals("up")){
			facingRight = false;
			facingUp = true;
			facingDown = false;
		} else if(dir.equals("down")){
			facingRight = false;
			facingUp = false;
			facingDown = true;
		}else{
			facingRight = false;
			facingUp = false;
			facingDown = false;
		}
		
		moveSpeed = 3.8;
		if(dir.equals("right")){
			dx = moveSpeed;
			dy = 0;
		}else if(dir.equals("left")){
			dx = -moveSpeed;
			dy = 0;
		}else if(dir.equals("up")){
			dy = -moveSpeed;
			dx = 0;
		}else{
			dy = moveSpeed;
			dx = 0;
		}
		
		width = 28;
		height = 30;
		cwidth = 28;
		cheight = 30;
		
try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/SpitterBall.png"));
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(width * i, 0, width, height);
			}
		
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setHit(){
		if(hit)return;
		hit = true;
		animation.setDelay(70);
		if(dx > 0 || dx < 0){
			dx = 0;
		}
		if(dy > 0 || dy < 0){
			dy = 0;
		}
	}
	
	public boolean shouldRemove(){return remove;}
	
	public void update(){
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if((dx == 0 && dy == 0) && !hit){
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()){
			remove = true;
		}
		animation.update();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
