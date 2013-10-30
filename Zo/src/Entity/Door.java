package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemies.Enemy;
import TileMap.TileMap;

public class Door extends Enemy{
	
	private ArrayList<BufferedImage[]> sprites;
	
	int opener = 0;
	
	private static final int UP2 = 0;
	private static final int UP3 = 1;
	private static final int SIDE2 = 2;
	private static final int SIDE3 = 3;
	
	private static String dir;
	private static int wh;
	private static int rx;
	private static int ry;
	private static String cond;
	private static int cnum;
	
	public Door(int wh, int rx, int ry, String dir, String cond, int cnum, TileMap tm){
		super(tm);

		this.dir = dir;
		this.wh = wh;
		this.rx = rx;
		this.ry = ry;
		this.cond = cond;
		this.cnum = cnum;
		
		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		if(dir.equalsIgnoreCase("vert") || dir.equalsIgnoreCase("vertical")){
			if(wh == 3 || wh == 96){
				height = 96;
				cheight = 96;
				width = 32;
				cwidth = 32;
				currentAction = UP3;
			} else if(wh == 2 || wh == 64){
				height = 64;
				cheight = 64;
				width = 32;
				cwidth = 32;
				currentAction = UP2;
			}//wh == 
		}else if(dir.equalsIgnoreCase("hor") || dir.equalsIgnoreCase("horizontal")){
			
				if(wh == 3 || wh == 96){
				height = 32;
				cheight = 32;
				width = 96;
				cwidth = 96;
				currentAction = SIDE3;
				}else if(wh == 2 || wh == 64){
					height = 32;
					cheight = 32;
					width = 64;
					cwidth = 64;
					currentAction = SIDE2;
					
				}
			}
			
			health = maxHealth = -1;
			damage = 0;
			invincible = true;
			
			deathScore = 0;
			
			try{
				
				BufferedImage spritesheet = ImageIO.read(
						getClass().getResourceAsStream(
								"/Sprites/TempDoors.png"));
				
				sprites = new ArrayList<BufferedImage[]>();
				for(int i = 0; i < 4; i++){
					BufferedImage[] bi = new BufferedImage[1];
					for(int j = 0; j < 1; j++){
						bi[j] = spritesheet.getSubimage(0, downHowMuch(), width, height);
					}
					sprites.add(bi);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			facingRight = true;
			animation = new Animation();
			animation.setFrames(sprites.get(currentAction));
			animation.setDelay(500);
			
		}
	public int downHowMuch(){
		int down = 0;
		if(dir.equalsIgnoreCase("vert") || dir.equalsIgnoreCase("vertical")){
			if(wh == 3 || wh == 96){
				down = 64;
			}else if(wh == 2 || wh == 64){
				down = 160;
			}
		}else if(dir.equalsIgnoreCase("hor") || dir.equalsIgnoreCase("horizontal")){
			if(wh == 3 || wh == 96){
				down = 0;
			}else if(wh == 2 || wh == 64){
				down = 32;
			}
		}
		return down;
	}	

	public void block(Rachel r){
		if(intersects(r)){
			if(r.facingUp){
				r.setUp(false);
				r.setDy(1);
			}else if(r.facingLeft()){
				r.setLeft(false);
				r.setDx(1);
			}else if(r.facingRight){
				r.setRight(false);
				r.setDx(-1);
			}else{
				r.setDown(false);
				r.setDy(-1);
			}
		}
	}
	
	public void open(){
		if(opener > 0){kill();}
	}
	
	public void killOpen(ArrayList<Enemy> e, Rachel r){
			int killed = 0;
		if(getXScreen() > 0 && r.getXScreen() > 0){
			for(int i = 0; i < e.size(); i++){
				Enemy en = e.get(i);
				if(en.getXScreen() > 0 && en.getYScreen() > 0){
					if(en.isDead()){
						killed++;
					}//en.isdead
				}//en.getxscreen
			}//for loop
			if(killed >= cnum){
				opener++;
			}
		}
	}
	
	public void update(ArrayList<Enemy> e, Rachel r){
		open();
		block(r);
		if(cond.equalsIgnoreCase("kill")){
			killOpen(e, r);
		}	
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
}
