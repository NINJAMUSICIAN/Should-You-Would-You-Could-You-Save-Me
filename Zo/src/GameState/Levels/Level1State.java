package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Finish;
import Entity.Rachel;
import Entity.Enemies.Enemy;
import Entity.Enemies.Sprinter;
import Entity.Enemies.Waiter;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.TileMap;

public class Level1State extends GameState {

	public static int score;
	private TileMap tileMap;
	
	private Rachel player;
	
	private Finish finish;
	
	private ArrayList<Waiter> waiters;
	private ArrayList<Sprinter> sprinters;
	private ArrayList<Enemy> enemies;
	
	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Tileset.png");
		tileMap.loadMap("/Maps/Level1.map");
		tileMap.setPosition(-32, -32);
		tileMap.setTween(1);
		
		player = new Rachel("/Sprites/TheGirl.png", tileMap);
		player.setPosition(810, 660); //840 660
		
		finish = new Finish(175, 111, GameStateManager.SECONDCUT, gsm, tileMap);//175
		
		populateEnemies();
	
		}

	public void populateEnemies(){
		sprinters = new ArrayList<Sprinter>();
		waiters = new ArrayList<Waiter>();
		enemies = new ArrayList<Enemy>();
		
		Sprinter s;
		Waiter w;
		
		Point waitPoints[] = new Point[]{
				new Point(510, 211),
				new Point(702, 211)
		};
		for(int i = 0; i < waitPoints.length; i++){
			w = new Waiter(tileMap, 0, "down");
			w.setPosition(waitPoints[i].x, waitPoints[i].y);
			waiters.add(w);
			enemies.add(w);
		}
		
		Point sprintPoints[] = new Point[]{
				new Point(607, 241),
				new Point(415, 241)
		};
		for(int i = 0; i < sprintPoints.length; i++){
			s = new Sprinter(tileMap, 2, "up");
			s.setPosition(sprintPoints[i].x, sprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
	}
	
	public void update() {
		player.update();
		player.checkAttack(enemies);
		
		finish.update();
		finish.checkGrab(player);
		
		for(int i = 0; i < waiters.size(); i++){
			Waiter w = waiters.get(i);
			w.followPlayer(player);
		}
		
		for(int i = 0; i < sprinters.size(); i++){
			Sprinter s = sprinters.get(i);
			s.Sprint(player);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				e.addScore(score);
				i--;
			}
			
		}
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getX() ,
				GamePanel.HEIGHT / 2 - player.getY());
		
	}

	@Override
	public void draw(Graphics2D g) {
	
		tileMap.draw(g);
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
	}

	@Override
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_Z) player.setPummeling();
		}
	


	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		
	}

}
