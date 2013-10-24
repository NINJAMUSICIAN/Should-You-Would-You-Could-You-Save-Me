package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Rachel;
import TileMap.TileMap;

public class Waiter extends Enemy{

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1,1,1
	};
	
	
	//player finding stuff
	private int follow = 0;
	
	private static final int DOWN = 0;
	private static final int SIDE = 1;
	private static final int UP = 2;
	
	public Waiter(TileMap tm, int startingAction, String s){
		super(tm);
		
		moveSpeed = .1;
		maxSpeed = .3;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		width = 30;
		height = 34;
		cwidth = 30;
		cheight = 34;
		
		health = maxHealth = 1;
		damage = 1;
		invincible = false;
		
		if(s.equals("right")){
			facingRight = true;
			facingUp = false;
			facingDown = false;
		} else if(s.equals("up")){
			facingRight = false;
			facingUp = true;
			facingDown = false;
		} else if(s.equals("down")){
			facingRight = false;
			facingUp = false;
			facingDown = true;
		}else{
			facingRight = false;
			facingUp = false;
			facingDown = false;
		}
		
		deathScore = 100;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/Waiters.png"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 3; i++){
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++){
				bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(bi);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = startingAction;
		animation.setFrames(sprites.get(startingAction));
		animation.setDelay(100);
		
	}
	
	public void getNextPosition(){
		if(up){
			facingUp = true;
			facingDown = false;
			dy -= moveSpeed;
			if(dy < -maxSpeed){
				dy = -maxSpeed;
			}
		}else if(down){
			facingUp = false;
			facingDown = true;
			dy += moveSpeed;
			if(dy > maxSpeed){
				dy = maxSpeed;
			}
		} else {
			if(dy > 0){
				dy = 0;
				
			}else if(dy < 0){
				dy = 0;
			}	
		}
		if(right){
			facingRight = true;
			dx += moveSpeed;
			if(dx > -maxSpeed){
				dx = maxSpeed;
			}
		}else if(left){
			facingRight = false;
			dx -= moveSpeed;
			if(dx < maxSpeed){
				dx = -maxSpeed;
			}
		}else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0){
					dx = 0;
				}
			}else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0){
					dx = 0;
				}
			}	
		}
	}
	
	public boolean seePlayer(Rachel r){
		
		if(facingRight){
			if(
				r.getX() > x &&
				r.getY() > y - height / 2 &&
				r.getY() < y + height / 2
					){
				return true;
			}
		}else if(!facingRight && !facingDown && !facingUp){
			if(
				r.getX() < x &&
				r.getY() > y - height / 2 &&
				r.getY() < y + height / 2
				){
				return true;
			}
		}else if(facingUp){
			if(r.getY() < y &&
				r.getX() > x - width / 2 &&
				r.getX() < x + height / 2
				){
				return true;
			}
		} else {
			if(
				r.getY() > y &&
				r.getX() > x - width / 2 &&
				r.getX() < x + height / 2
				){
				return true;
			}
		}
		return false;
	}

	public void followPlayer(Rachel r){
		
		if(seePlayer(r)){
			follow++;
		}
		
		if(follow > 0){
			if(r.getY() > getY()){
				setDown(true);
			} else {
				setDown(false);
			}
			if(r.getY() < getY()){
				setUp(true);
			} else {
				setUp(false);
			}
			if(r.getX() < getX()){
				setLeft(true);
			} else {
				setLeft(false);
			}
			if(r.getX() > getX()){
				setRight(true);
			}else {
				setRight(false);
			}
		}
	}
	
	public void update(){
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		if(dx > dy || dx < dy){
			if(currentAction != SIDE){
				currentAction = SIDE;
				animation.setFrames(sprites.get(SIDE));
				animation.setDelay(100);
				if(facingRight){
				width = 20;
				}	
			}
		} else if(dy < 0){
			if(currentAction != UP){
				currentAction = UP;
				animation.setFrames(sprites.get(UP));
				animation.setDelay(100);
				width = 30;
			}
		} else if(dy > 0){
			if(currentAction != DOWN){
				facingDown = true;
				currentAction = DOWN;
				animation.setFrames(sprites.get(DOWN));
				animation.setDelay(100);
				width = 30;
				
			}
		}
		animation.update();
		
	}

	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
