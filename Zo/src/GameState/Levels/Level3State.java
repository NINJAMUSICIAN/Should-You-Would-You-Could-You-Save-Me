package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Finish;
import Entity.Joseph;
import Entity.Rachel;
import Entity.Enemies.Enemy;
import Entity.Enemies.Sprinter;
import Entity.Enemies.Waiter;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.TileMap;

public class Level3State extends GameState{

private TileMap tileMap;
	
	private Rachel player;
	private Joseph joey;
	
	private Finish finish;
	
	private ArrayList<Waiter> waiters;
	private ArrayList<Sprinter> sprinters;
	private ArrayList<Enemy> enemies;
	
	public Level3State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Tileset.png");
		tileMap.loadMap("/Maps/Level3.map");
		tileMap.setPosition(-32, -32);
		tileMap.setTween(1);
		
		player = new Rachel("/Sprites/TheGirl.png", tileMap);
		player.setPosition(943, 1480); //840 660
		
		joey = new Joseph(tileMap);
		joey.setPosition(464, 392);
		
		finish = new Finish(463, 427, GameStateManager.LEVEL1STATE, gsm, tileMap);//175
		
		populateEnemies();
	
	}

	public void populateEnemies(){
		sprinters = new ArrayList<Sprinter>();
		waiters = new ArrayList<Waiter>();
		enemies = new ArrayList<Enemy>();
		
		Waiter w;
		Sprinter s;

		Point[] downWaitPoints = new Point[]{ 
		new Point(319, 1039),
		new Point(575, 1039),
		};
		
		for(int i = 0; i < downWaitPoints.length; i++){
			w = new Waiter(tileMap, 0, "down");
			w.setPosition(downWaitPoints[i].x, downWaitPoints[i].y);
			enemies.add(w);
			waiters.add(w);
		}
		
		Point[] leftWaitPoints = new Point[]{ 
				new Point(656, 1216),
				};
		
		for(int i = 0; i < leftWaitPoints.length; i++){
			w = new Waiter(tileMap, 1, "left");
			w.setPosition(leftWaitPoints[i].x, leftWaitPoints[i].y);
			enemies.add(w);
			waiters.add(w);
		}
		
		Point[] rightWaitPoints = new Point[]{ 
				new Point(240, 1216),
				};
		
		for(int i = 0; i < rightWaitPoints.length; i++){
			w = new Waiter(tileMap, 1, "right");
			w.setPosition(rightWaitPoints[i].x, rightWaitPoints[i].y);
			enemies.add(w);
			waiters.add(w);
		}
		
		Point[] upWaitPoints = new Point[]{ 
				new Point(320, 1424),
				new Point(576, 1424),
				};
		
		for(int i = 0; i < upWaitPoints.length; i++){
			w = new Waiter(tileMap, 2, "up");
			w.setPosition(upWaitPoints[i].x, upWaitPoints[i].y);
			enemies.add(w);
			waiters.add(w);
		}
		
	}
	
	public void update() {

		joey.update();
		
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
				e.addScore(Level1State.score);
				i--;
			}
		}
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getX() ,
				GamePanel.HEIGHT / 2 - player.getY());
		
	}

	public void draw(Graphics2D g) {

		tileMap.draw(g);
		player.draw(g);
		joey.draw(g);
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
	}
	
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_Z) player.setPummeling();
		}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		
	}

}
