package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Rachel;
import TileMap.TileMap;

public class Walker extends Enemy{

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1,1,1
	};
	
	//player finding stuff
	private int follow = 0;
	private int pauser = 0;
	private int decide = 0;
	
	private long randMove;
	private long randPause;
	
	private boolean walk, pause;
	private long walkTime, pauseTime;
	
	private static final int DOWN = 0;
	private static final int SIDE = 1;
	private static final int UP = 2;
	
	public Walker(TileMap tm, int startingAction, String s){
		super(tm);
		
		moveSpeed = .3;
		maxSpeed = 1;
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

	public void init(){
		Random r = new Random();
		int choice = r.nextInt(2);
		if(choice == 0){
			walk = false;
			pause = true;
			
		}else{
			walk = true;
			pause = false;
			
		}
		
		walkTime = System.nanoTime();
		pauseTime = System.nanoTime();
		
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
	
	public long waiter(int maxSeconds){
		Random r = new Random();
		int seconds = r.nextInt(maxSeconds) + 1;
		
		long milliseconds = (long)(seconds * 1000);
		
		return milliseconds;
		
	}
	
	public void mover(){
		
		if(pauser == 1){
			switchDirection();
			walkTime = System.nanoTime();
			randMove = waiter(1);
			pauser--;
		}
		
		long elapsed2 = (System.nanoTime() - walkTime) / 1000000;
		if(elapsed2 < randMove){
			
			move();
			} else {
				decide++;
				
			}
		System.out.println("go " + randMove);
		}
	public void move(){
		
		if(facingRight){
			setLeft(false);
			setRight(true);
			setUp(false);
			setDown(false);
		}else if(facingLeft()){
			setLeft(true);
			setRight(false);
			setUp(false);
			setDown(false);
		}else if(facingUp){
			setLeft(false);
			setRight(false);
			setUp(true);
			setDown(false);
		}else if(facingDown){
			setLeft(false);
			setRight(false);
			setUp(false);
			setDown(true);
		}
		
	}	
	public void switchDirection(){
		Random r = new Random();
		
		int nextAction = r.nextInt(4);
		if(nextAction == 0){
			facingUp = true;
			facingRight = false;
			facingDown = false;
			setLeft(false);
			setRight(false);
			setUp(true);
			setDown(false);
			
			

		}else if(nextAction == 1){
			facingDown = true;
			facingRight = false;
			facingUp = false;
			setLeft(false);
			setRight(false);
			setUp(false);
			setDown(true);
			//facingDown = false;

		}else if(nextAction == 2){
			facingRight = true;
			facingUp = false;
			facingDown = false;
			setLeft(false);
			setRight(true);
			setUp(false);
			setDown(false);
			//facingRight = false;
		}else{
			setLeft(true);
			setRight(false);
			setUp(false);
			setDown(false);
		}
	}	
	
	public void stop(){
		
		long pressIt = 0;
		
		if(pauser == 0){
			pauseTime = System.nanoTime();
			randPause = waiter(2);
			pauser++;
			
		}
		
		long elapsed = (System.nanoTime() - pauseTime) / 1000000;
		if(elapsed <= randPause){
			setLeft(false);
			setRight(false);
			setUp(false);
			setDown(false);
			dx = 0;
			dy = 0;
			
		} else {
			
			decide--;
			
		}
		System.out.println("pause " + elapsed + "  " + randPause);
		
	}

	public void wander(){
		if(decide == 0){
			mover();
		}else if(decide == 1){
			stop();
		}
	}
	public void whereToGo(Rachel r){
		followPlayer(r);
		if(follow == 0){
			wander();
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
		
		if((follow == 0 && dy == 0) && (facingRight || facingLeft()) || (follow > 0 && (dx > dy || dx < dy))){
			if(currentAction != SIDE){
				currentAction = SIDE;
				animation.setFrames(sprites.get(SIDE));
				animation.setDelay(100);
				if(facingRight){
				width = 20;
				}	
			}
		} else if(facingUp){
			if(currentAction != UP){
				currentAction = UP;
				animation.setFrames(sprites.get(UP));
				animation.setDelay(100);
				width = 30;
			}
		} else if(facingDown){
			if(currentAction != DOWN){

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
