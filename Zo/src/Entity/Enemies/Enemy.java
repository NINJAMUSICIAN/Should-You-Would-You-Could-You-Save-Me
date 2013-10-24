package Entity.Enemies;

import Entity.MapObject;
import Entity.Rachel;
import GameState.Levels.Level1State;
import TileMap.TileMap;


public class Enemy extends MapObject{

	protected int health;
	protected int maxHealth;
	protected int deathScore;
	protected boolean dead;
	protected int damage;
	
	protected boolean invincible;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}

	public boolean isDead(){return dead;}
	public int getDamage(){return damage;}
	public int getScore(){return deathScore;}
	
	public void addScore(int score){
		Level1State.score += deathScore;
	}
	
	public void kill(){dead = true;}
	
	public double distanceToPlayer(Rachel r){
		
		double exes = (getX() - r.getX());
		double whys = (getY() - r.getY());
		double inside = (exes * exes) + (whys * whys);
		
		double distance = Math.sqrt(inside);
		
		return distance;
	}
	
	public void hit(int damage){
		if(!invincible){
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
		}
	}
	
	public void update(){
		
	}
	
}
