package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Rachel;
import TileMap.TileMap;

public class Spitter extends Enemy {

	@SuppressWarnings("unused")
	private int spitDamage;
	private ArrayList<Spit> spits;
	private long spitTime;
	
	String s;
	
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1,1,1
	};
	
private int follow = 0;
	//IDLE CHARGING SPITTING
	private static final int IDLEDOWN = 0;
	private static final int CHARGINGDOWN = 3;
	private static final int SPITTINGDOWN = 6;
	private static final int IDLESIDE = 1;
	private static final int CHARGINGSIDE = 4;
	private static final int SPITTINGSIDE = 5;
	private static final int IDLEUP = 2;
	private static final int CHARGINGUP = 7;
	private static final int SPITTINGUP = 8;
	
	
	public Spitter(TileMap tm, int startingAction, String s) {
		super(tm);
		
		this.s = s;
		
		moveSpeed = 0; 
		maxSpeed = .3;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		width = 30;
		height = 26;
		cwidth = 30;
		cheight = 26;
		
		health = maxHealth = 1;
		damage = 1;
		invincible = false;
		
		spitDamage = 1;
		spits = new ArrayList<Spit>();
		
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
							"/Sprites/Enemies/Spitter.png"));
			
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

	public void charger(){
		if(currentAction == IDLEDOWN){
			spitTime = System.nanoTime();
			currentAction = CHARGINGDOWN;
		}
		if(currentAction == IDLESIDE){
			spitTime = System.nanoTime();
			currentAction = CHARGINGSIDE;
		}
		if(currentAction == IDLEUP){
			spitTime = System.nanoTime();
			currentAction = CHARGINGUP;
		}
	}
	
	public void checkSpitHit(Rachel r){
		for(int i = 0; i < spits.size(); i++){
			if(spits.get(i).intersects(r)){
			r.hit(1);
			spits.get(i).setHit();
			}
		}
	}
	
	public void update(){
		
		animation.update();
		
	if(currentAction == CHARGINGSIDE){
		long elapsed = (System.nanoTime() - spitTime) / 1000000;
		if(elapsed > 2500){
			currentAction = SPITTINGSIDE;
		}
	}
	if(currentAction == CHARGINGUP){
		long elapsed = (System.nanoTime() - spitTime) / 1000000;
		if(elapsed > 2500){
			currentAction = SPITTINGUP;
		}
	}
	if(currentAction == CHARGINGDOWN){
		long elapsed = (System.nanoTime() - spitTime) / 1000000;
		if(elapsed > 2500){
			currentAction = SPITTINGDOWN;
		}
	}
	
		if(currentAction == CHARGINGSIDE ||
				currentAction == IDLESIDE || 
					currentAction == SPITTINGSIDE){
			width = 20;
			cwidth = 26;
		}else if(currentAction == CHARGINGUP ||
				currentAction == IDLEUP || 
				currentAction == SPITTINGUP){
			width = 30;
			cwidth = 30;
		}else if(currentAction == CHARGINGDOWN ||
				currentAction == IDLEDOWN || 
					currentAction == SPITTINGDOWN){
			width = 30;
			cwidth = 30;
		}
		
		if(currentAction == SPITTINGUP){
			Spit sc = new Spit("up", tileMap);
			sc.setPosition(x, y);
			spits.add(sc);
			currentAction = IDLEUP;
		}
		if(currentAction == SPITTINGDOWN){
			Spit sc = new Spit("down", tileMap);
			sc.setPosition(x, y);
			spits.add(sc);
			currentAction = IDLEDOWN;
		}
		if(currentAction == SPITTINGSIDE){
			if(s.equalsIgnoreCase("right")){
			Spit sc = new Spit("right", tileMap);
			sc.setPosition(x, y);
			spits.add(sc);
			currentAction = IDLESIDE;
			} else {
				Spit sc = new Spit("left", tileMap);
				sc.setPosition(x, y);
				spits.add(sc);
				currentAction = IDLESIDE;
			}
		}
		
		for(int i = 0; i < spits.size(); i++){
			spits.get(i).update();
			if(spits.get(i).shouldRemove()){
				spits.remove(i);
				i--;
			}
		}
		
		
		charger();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
		
		for(int i = 0; i < spits.size(); i++){
			spits.get(i).draw(g);
		}
	}
}
