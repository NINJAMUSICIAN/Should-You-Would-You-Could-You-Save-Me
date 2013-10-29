package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Finish;
import Entity.Rachel;
import Entity.Enemies.Enemy;
import Entity.Enemies.Pacer;
import Entity.Enemies.Sprinter;
import Entity.Enemies.Waiter;
import Entity.Enemies.Walker;
import Main.GamePanel;
import TileMap.TileMap;

public class BlankLevel extends GameState {

	public static int score;
	private TileMap tileMap;
	
	private Rachel player;
	
	private Finish finish;
	
	private ArrayList<Waiter> waiters;
	private ArrayList<Sprinter> sprinters;
	private ArrayList<Pacer> pacers;
	private ArrayList<Walker> walkers;
	private ArrayList<Enemy> enemies;
	
	public BlankLevel(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Tileset.png");
		tileMap.loadMap("/Maps/Test.map");
		tileMap.setTween(1);
		tileMap.setPosition(-0, -760);
		
		player = new Rachel("/Sprites/TheGirl.png", tileMap);
		player.setPosition(48, 1072); //Room 4: 48, 1072 || Room 1 :48, 352 || Room 3 2096, 352
		
		populateEnemies();
		
		}

	public void populateEnemies(){
		sprinters = new ArrayList<Sprinter>();
		waiters = new ArrayList<Waiter>();
		pacers = new ArrayList<Pacer>();
		walkers = new ArrayList<Walker>();
		enemies = new ArrayList<Enemy>();
		
		Sprinter s;
		Waiter w;
		Pacer p;
		Walker wa;
		
		Point pacePoints[] = new Point[]{
				new Point(120, 1072),
				new Point(600, 1072)
		};
		
		for(int i = 0; i < pacePoints.length; i++){
			s = new Sprinter(tileMap, 1, "right");
			s.setPosition(pacePoints[i].x, pacePoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
//		for(int i = 0; i < pacePoints.length; i++){
//			p = new Pacer(tileMap, 2, "up", 5);
//			p.setPosition(pacePoints[i].x, pacePoints[i].y);
//			p.init(pacePoints[i].x, pacePoints[i].y);
//			pacers.add(p);
//			enemies.add(p);
//			
//		}
		
	}
	
	public void update() {
		player.update();
		player.checkAttack(enemies);
		
		for(int i = 0; i < walkers.size(); i++){
			Walker wa = walkers.get(i);
			wa.whereToGo(player);
		}
		
//		for(int i = 0; i < waiters.size(); i++){
//			Waiter w = waiters.get(i);
//			w.followPlayer(player);
//		}
//		
		for(int i = 0; i < sprinters.size(); i++){
			Sprinter s = sprinters.get(i);
			s.Sprint(player, sprinters);
		}
		
//		for(int i = 0; i < pacers.size(); i++){
//			Pacer p = pacers.get(i);
//			p.whereToGo(player);
//		}
		
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				e.addScore(score);
				i--;
			}
			
		}
		
		tileMap.autoScroll(player);
		
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
