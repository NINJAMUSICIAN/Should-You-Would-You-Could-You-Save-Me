package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Door;
import Entity.Finish;
import Entity.Rachel;
import Entity.Enemies.Enemy;
import Entity.Enemies.Pacer;
import Entity.Enemies.Sprinter;
import Entity.Enemies.Waiter;
import Entity.Enemies.Walker;
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
	private ArrayList<Door> doors;
	
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
		populateDoors();
		}

	public void populateDoors(){
		doors = new ArrayList<Door>();
		
		Door d = new Door(3, 1024, 720, "hor", "kill", 1, tileMap);
		d.setPosition(1904, 784);
		doors.add(d);
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
		
		
		
		Point[] downSprintPoints = new Point[]{ 
				new Point(784, 432),
				new Point(656, 432),
				new Point(528, 432),
				new Point(400, 432),
				new Point(208, 560),
				new Point(176, 48),
				new Point(304, 48),
				new Point(432, 48),
				new Point(560, 48),
				new Point(688, 48),
				new Point(816, 48),
				};
		
		for(int i = 0; i < downSprintPoints.length; i++){
			s = new Sprinter(tileMap, 0, "down");
			s.setPosition(downSprintPoints[i].x, downSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
		Point[] UpSprintPoints = new Point[]{ 
				new Point(848, 688),
				new Point(720, 688),
				new Point(592, 688),
				new Point(464, 688),
				new Point(368, 272),
				new Point(496, 272),
				new Point(624, 272),
				new Point(752, 272),
				};
		

		for(int i = 0; i < UpSprintPoints.length; i++){
			s = new Sprinter(tileMap, 2, "up");
			s.setPosition(UpSprintPoints[i].x, UpSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
		Point[] pacePoints = new Point[]{
				new Point(1500, 960)
		};
		
		for(int i = 0; i < pacePoints.length; i++){
			p = new Pacer(tileMap, 2, "down", 5);
			p.setPosition(pacePoints[i].x, pacePoints[i].y);
			p.init(pacePoints[i].x, pacePoints[i].y);
			pacers.add(p);
			enemies.add(p);
			
		}
		
	}
	
	public void update() {
		player.update();
		player.checkAttack(enemies);
		
	
		
		for(int i = 0; i < walkers.size(); i++){
			Walker wa = walkers.get(i);
			wa.whereToGo(player);
		}
		
		for(int i = 0; i < doors.size(); i++){
			Door d = doors.get(i);
			d.update(enemies, player);
			if(d.isDead()){
				doors.remove(i);
				i--;
			}
		}
//		for(int i = 0; i < waiters.size(); i++){
//			Waiter w = waiters.get(i);
//			w.followPlayer(player);
//		}
//		
		for(int i = 0; i < sprinters.size(); i++){
			Sprinter s = sprinters.get(i);
			s.Sprint(player);
		}
		
		for(int i = 0; i < pacers.size(); i++){
			Pacer p = pacers.get(i);
			p.whereToGo(player);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			if(e.getXScreen() > 0 && e.getXScreen() < 1024){
				if( e.getYScreen() > 0 && e.getYScreen() < 1024){
			e.update();
				}
			}
			if(e.isDead()){
				enemies.remove(i);
				e.addScore(score);
				i--;
			}
			
		}
		
		tileMap.autoScroll(player);
		
	}


	public void draw(Graphics2D g) {
	
		tileMap.draw(g);
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		for(int i = 0; i < doors.size(); i++){
			doors.get(i).draw(g);
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
