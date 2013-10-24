package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemies.Enemy;
import TileMap.TileMap;

public class Rachel extends MapObject{

	private int health;
	private int maxHealth;
	private int ammunition;
	private int maxAmmunition;
	private TileMap tiley;
	
	private boolean dead;
	
	private boolean flinching;
	private long flinchTimer;
	
	private boolean shooting;
	private int ammo;
	private int gunDamage;
	//private ArrayList<Bullet> bullets;
	
	private boolean pummeling;
	private int pummelDamage;
	private int pummelRange;
	
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1, 1, 1, 1, 1, 1, 1, 1, 1
	};

	//animation Actions
	private static final int DOWN = 0;
	private static final int SIDE = 1;
	private static final int UP = 2;
	private static final int BATUP= 3;
	private static final int BATSIDE = 4;
	private static final int  BATDOWN = 5;
	private static final int GUNDOWN = 6;
	private static final int GUNSIDE = 7;
	private static final int GUNUP= 8;
	
	public Rachel(String s, TileMap tm){
		
		super(tm);
		this.tiley = tm;
		
		width = 44;
		height = 60;
		
		cwidth = 30;
		cheight = 44;
		
		moveSpeed = 0.6;
		maxSpeed = 2.0;
		stopSpeed = 0.4;
		
		facingRight = false;
		facingUp = true;
		facingDown = false;
		
		health = maxHealth = 2;
		
		ammunition = maxAmmunition = 30;
		ammo = 1;
		//bullets = new ArrayList<Bullet>();
		
		pummelDamage = 1;
		if(facingRight || facingLeft()){
		pummelRange = 44;
		} else if(facingUp || facingDown){
			pummelRange = 57;
		}
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(s));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 9; i++){
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
		currentAction = UP;
		animation.setFrames(sprites.get(UP));
		animation.setDelay(400);
		
	}
	
	public int getHealth() {return health;}
	public int getMaxHealth() {return maxHealth;}
	public int getFire() {return ammunition;}
	public int getMaxFire() {return maxAmmunition;}
	
	public void setFiring(){
		shooting = true;
	}
	
	public void setAmmo(int amount){
		maxAmmunition = amount;
	}
	
	public void setPummeling(){
		pummeling = true;
	}
	
	public boolean isDead() {return dead;}
	public void kill() {dead = true;}
	public void revive() {dead = false;}
	
	public void setDx(double x){
		dx = x;
	}
	
	public void setCurrentAction(int action){
		currentAction = action;
	}
	
	public void checkAttack(ArrayList<Enemy> enemies){
		for(int i = 0; i < enemies.size();i++){
			Enemy e = enemies.get(i);
			if(pummeling){
				if(facingRight){
					if(
						e.getX() > x &&
						e.getX() < x + pummelRange &&
						e.getY() > y - height / 2 &&
						e.getY() < y + height / 2
						){
						e.hit(pummelDamage);
					}
				}else if(facingUp){
					if(
					e.getX() < x + width / 2 &&
					e.getX() > x - width / 2 &&
					e.getY() < y &&
					e.getY() > y - pummelRange
					){
						e.hit(pummelDamage);
					}
				}else if(facingDown){
					if(
					e.getX() < x + width / 2 &&
					e.getX() > x - width / 2 &&
					e.getY() > y &&
					e.getY() < y + pummelRange
					){
						e.hit(pummelDamage);
					}
				}else{
					if(
							
						e.getX() < x &&
						e.getX() > x - pummelRange &&
						e.getY() > y - height / 2 &&
						e.getY() < y + height / 2
						){
						e.hit(pummelDamage);
					}
				}
			}
			
			if(intersects(e)){
				hit(e.getDamage());
			}
		}
	}
	
	public boolean checkFirst(){
		if(!left && !right){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void getNextPosition(){
		if(up){
			dy -= moveSpeed;
			if(dy < -maxSpeed){
				dy = -maxSpeed;
			}
		}else if(down){
			dy += moveSpeed + 8;
			if(dy > maxSpeed + 8){
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
			dx += moveSpeed;
			if(dx > -maxSpeed){
				dx = maxSpeed;
			}
		}else if(left){
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
	
	public void setSprites(){
		if(facingDown){
			
		if(currentAction != DOWN){
		currentAction = DOWN;
		animation.setFrames(sprites.get(DOWN));
		animation.setDelay(100);
		}
	}else if(facingUp){
		if(currentAction != UP){
			currentAction = UP;
			animation.setFrames(sprites.get(UP));
			animation.setDelay(100);
			}
	}if(facingRight || (!facingRight && !facingUp && !facingDown)){
		if(currentAction != SIDE){
			currentAction = SIDE;
			animation.setFrames(sprites.get(SIDE));
			animation.setDelay(100);
		}
	}
	}
	
	public void update(){
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(currentAction == BATUP ||currentAction == BATSIDE || currentAction == BATDOWN){
			if(animation.hasPlayedOnce()){ pummeling = false; 
				setSprites();
				
			}	
		}
		if(currentAction == GUNUP || currentAction == GUNSIDE || currentAction == GUNDOWN){
			if(animation.hasPlayedOnce()) shooting = false;
		}
		
		if(ammunition > maxAmmunition){ammunition = maxAmmunition;}
		
		if(shooting){
			if(currentAction != GUNUP || currentAction != GUNSIDE || currentAction != GUNDOWN){
				if(ammunition > ammo){
					ammunition -= ammo;
					//Bullet b = new Bullet(tileMap, facingRight, facingUp);
//					b.setPosition(x, y);
//					bullets.add(b);			
				}
			}
		}
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).update();
//			if(bullets.get(i).shouldRemove()){
//				bullets.remove(i);
//				i--;
//			}
//		}
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000){
				flinching = false;
			}
		}
		
		if(pummeling){
			if(facingUp && currentAction != BATUP){
				currentAction = BATUP;
				animation.setFrames(sprites.get(BATUP));
				animation.setDelay(50);
//				width = 30;
//				height = 57;
			}else if(!facingUp && !facingDown && currentAction != BATSIDE){
				currentAction = BATSIDE;
				animation.setFrames(sprites.get(BATSIDE));
				animation.setDelay(50);
//				width = 44;
//				height = 47;
			}else if(facingDown && currentAction != BATDOWN){
				currentAction = BATDOWN;
				animation.setFrames(sprites.get(BATDOWN));
				animation.setDelay(50);
//				width = 30;
//				height = 60;
			}
		}
		
		
			if(down){
				if(checkFirst()){
			if(currentAction != DOWN){
			currentAction = DOWN;
			animation.setFrames(sprites.get(DOWN));
			animation.setDelay(100);
			cwidth = 15;
			cheight = 22;
			height = 60;
			width = 44;
					}
				}
			
		}else if(up){
			if(checkFirst()){
			if(currentAction != UP){
				currentAction = UP;
				animation.setFrames(sprites.get(UP));
				animation.setDelay(100);
				cheight = 44;
				cwidth = 30;
				height = 60;
				width = 42;
				
				}
			}
		}if(right || left){
			if(currentAction != SIDE){
				currentAction = SIDE;
				animation.setFrames(sprites.get(SIDE));
				animation.setDelay(100);
				if(facingRight){
				cheight = 44;
				cwidth = 23;
				width  = 23;
				height = 44;
			}else {
				cheight = 44;
				cwidth = 23;
				width = 46;
				height = 60;
				}
			}
		}
		
		
		
		
			animation.update();
			
			if(currentAction != BATUP && currentAction != BATDOWN && currentAction != BATSIDE &&
					currentAction != GUNDOWN && currentAction != GUNSIDE && currentAction != GUNUP){
				if(right){ facingRight = true; facingUp = false; facingDown = false; pummeling = false;}
				if(left){facingRight = false; facingUp = false; facingDown = false; pummeling = false;}
				if(up){ facingUp = true; facingDown = false; facingRight = false; pummeling = false;}
				if(down){ facingUp = false; facingDown = true; facingRight = false; pummeling = false;}	
				
				if(right && down){facingRight = true; facingDown = true;}
				if(right && up){facingRight = true; facingUp = true;}
			}
				
}
	
	public void reset(){
		health = maxHealth = 2;
	}
	
	public void draw(Graphics2D g){
	
		setMapPosition();
		
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).draw(g);
//		}

		if(flinching){
			long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){
				return;
			}
		}
	super.draw(g);	
	}
}