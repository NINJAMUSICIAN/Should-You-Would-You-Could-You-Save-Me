package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Rachel;
import TileMap.TileMap;

public class Pacer extends Enemy{

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1,1,1
	};
	
	//player finding stuff
	private int follow = 0;
	private int walkLength;
	
	private TileMap tm;
	
	private double startingX;
	private double startingY;
	private double endingX;
	private double endingY;
	
	private static final int DOWN = 0;
	private static final int SIDE = 1;
	private static final int UP = 2;
	
	public Pacer(TileMap tm, int startingAction, String s, int walkLength){
		super(tm);
		this.tm = tm;
		this.walkLength = walkLength;
		
		moveSpeed = .1;
		maxSpeed = .6;
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
		}else if(s.equals("left")){
			facingRight = false;
			facingUp = false;
			facingDown = false;
			
		}
		
		deathScore = 100;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/Pacer.png"));
			
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
		
	//Set initial positions
	public void setStartingPosition(double x, double y){
		startingX = x;
		startingY = y;
	}
	public void setEndingPosition(){
		if(facingRight){
			endingX = startingX + (walkLength * tm.getTileSize());
		}else if(facingDown){
			endingY = startingY + (walkLength * tm.getTileSize());
		}else if(facingUp){
			endingY = startingY - (walkLength * tm.getTileSize());
		} else {
			endingX = startingX - (walkLength * tm.getTileSize());
		}
	}
	public void init(double x, double y){
		setStartingPosition(x, y);
		setEndingPosition();
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

	//know when and how far to walk and when to turn around
	public void walkSideways(){
		if(facingRight){
			if(x < endingX){
				setRight(true);
			}else{
				turnAround();
				}
			}
		
		if(facingLeft()){
				if(x > endingX){
					setLeft(true);
				}else{
					turnAround();
			}
		}
	}
	public void walkVertical(){
		if(facingUp){
			if(y > endingY){
				setUp(true);
			}else{
				turnAround();
			}
		}
		if(facingDown){
			if(y < endingY){
				setDown(true);
			}else{
				turnAround();
			}
		}
	}	
	public void turnAround(){
		if(facingRight){
			setRight(false);
			double temp = startingX;
			startingX = endingX;
			endingX = temp;
			setLeft(true);
		
		} else if(facingLeft()){
			setLeft(false);
			double temp = startingX;
			startingX = endingX;
			endingX = temp;
			setRight(true);
		} 
		if(facingUp){
			setUp(false);
			double temp = startingY;
			startingY = endingY;
			endingY = temp;
			setDown(true);
		}else if(facingDown){
			setDown(false);
			double temp2 = startingY;
			startingY = endingY;
			endingY = temp2;
			setUp(true);
		}
	}	
	public void pace(){
		if(follow == 0){
			if(facingRight || facingLeft()){
				walkSideways();
			}else if(facingUp || facingDown){
				walkVertical();
			}//if up or down
		}//if follow = 0
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
	public void whereToGo(Rachel r){
		followPlayer(r);
		if(follow == 0){
			pace();
		}
	}
	public void update(){
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		System.out.println("x " + y + " end x " + endingY);
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed > 4000){
					flinching = false;
			}//if elapsed
		}//if flinching
		
		if((follow == 0 && dy == 0) || (follow > 0 && (dx > dy || dx < dy))){
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


