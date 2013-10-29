package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Rachel;
import TileMap.TileMap;

public class Sprinter extends Enemy{

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1,1,1
	};
		
		//player finding stuff
		private int follow = 0;
		private int started = 0;
		
		private static final int DOWN = 0;
		private static final int SIDE = 1;
		private static final int UP = 2;
		
		public Sprinter(TileMap tm, int startingAction, String s){
			super(tm);
			
			moveSpeed = .3;
			maxSpeed = 10;
			fallSpeed = 0;
			maxFallSpeed = 0;
			
			width = 30;
			height = 34;
			cwidth = 10;
			cheight = 10;
			
			health = maxHealth = 2;
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
								"/Sprites/Enemies/Sprinter.png"));
				
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
				dy -= moveSpeed;
				if(dy < -maxSpeed){
					dy = -maxSpeed;
				}
			}else if(down){
				dy += moveSpeed;
				if(dy > maxSpeed){
					dy = maxSpeed;
				}
			} else {
				if(dy > 0){
					dy -= stopSpeed;
					if(dy < 0){
						dy = 0;
					}
				}else if(dy < 0){
					dy += stopSpeed;
					if(dy > 0){
						dy = 0;
					}
				}	
			}
			if(right){
				facingRight = true;
				dx += moveSpeed;
				if(dx > maxSpeed){
					dx = maxSpeed;
				}
			}else if(left){
				facingRight = false;
				dx -= moveSpeed;
				if(dx < -maxSpeed){
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
			if(distanceToPlayer(r) < 256){
			if(facingRight){
				if(
					r.getX() > x &&
					r.getY() > y - height / 2 &&
					r.getY() < y + height / 2
						){
					return true;
				}
				//left
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
			}
			return false;
		}

		public boolean hitByOther(ArrayList<Sprinter> s){
			boolean runNow = false;
			for(int i = 0; i < s.size(); i++){
				Sprinter sp = s.get(i);
				if(intersects(s.get(i))){
				runNow = true;
				}else{
					runNow = false;
				}
			}
			return runNow;
			
		}
		
		public boolean beganSprinting(){
			if(dx > 0 || dx < 0 || dy > 0 || dy < 0){
				return true;
			}else{
				return false;
			}
		}
		
		public void Sprint(Rachel r, ArrayList<Sprinter> s){
			if(seePlayer(r)){
				follow++;
			}
			
			if(beganSprinting()){
				started++;
			}
			
			if(follow > 0){
				if(facingUp){
					setUp(true);
				}else if(facingRight){
					setRight(true);
				}else if(facingDown){
					setDown(true);
				}else {
					setLeft(true);
				}
				if(started > 0){
				if(dx == 0 && dy == 0){
					kill();
					}
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
					
					if(facingRight || (!facingRight && !facingDown && !facingUp)){
						
						if(currentAction != SIDE){
							currentAction = SIDE;
							animation.setFrames(sprites.get(SIDE));
							animation.setDelay(100);
							if(facingRight){
							width = 20;
							}	
						}
					} else if(up){
						if(currentAction != UP){
							currentAction = UP;
							animation.setFrames(sprites.get(UP));
							animation.setDelay(100);
							width = 30;
						}
					} else if(down){
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
