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
import Entity.Enemies.Spitter;
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
	private ArrayList<Spitter> spitters;
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
		tileMap.setPosition(-0, -760);// -0, -760
		
		player = new Rachel("/Sprites/TheGirl.png", tileMap);
		player.setPosition(48, 1072); //Room 4: 48, 1072 || Room 1 :48, 352 || Room 3 2096, 352
		
		populateEnemies();
		populateDoors();
		}

	public void populateDoors(){
		doors = new ArrayList<Door>();
		
		Door d = new Door(3, 1024, 0, "vertical", "kill", 2, tileMap);
		d.setPosition(1040, 528);
		doors.add(d);
	}
	
	public void populateEnemies(){
		sprinters = new ArrayList<Sprinter>();
		waiters = new ArrayList<Waiter>();
		pacers = new ArrayList<Pacer>();
		walkers = new ArrayList<Walker>();
		spitters = new ArrayList<Spitter>();
		enemies = new ArrayList<Enemy>();
		
		Sprinter s;
		Waiter w;
		Pacer p;
		Walker wa;
		Spitter sp;
		
		Point[] leftSpitPoints = new Point[]{
				new Point(1999, 239),
				new Point(1999, 271)
		};
		
		for(int i = 0; i < leftSpitPoints.length; i++){
			sp = new Spitter(tileMap, 1, "left");
			sp.setPosition(leftSpitPoints[i].x, leftSpitPoints[i].y);
			spitters.add(sp);
			enemies.add(sp);
		}
		
		Point[] rightSpitPoints = new Point[]{
				new Point(1264, 239),
				new Point(1264, 271)
		};
		
		for(int i = 0; i < rightSpitPoints.length; i++){
			sp = new Spitter(tileMap, 1, "right");
			sp.setPosition(rightSpitPoints[i].x, rightSpitPoints[i].y);
			spitters.add(sp);
			enemies.add(sp);
		}
		
		
		
		
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
		
		Point[] DownPacePoints = new Point[]{ 
				new Point(2192, 52),
				new Point(2512, 52),
				new Point(2800, 52),
				};
		
		for(int i = 0; i < DownPacePoints.length; i++){
			p = new Pacer(tileMap, 2, "down", 3.7);
			p.setPosition(DownPacePoints[i].x, DownPacePoints[i].y);
			p.init(DownPacePoints[i].x, DownPacePoints[i].y);
			pacers.add(p);
			enemies.add(p);
			
		}
		
		Point[] UpPacePoints = new Point[]{ 
				new Point(2352, 172),
				new Point(2672, 172),
				};
		
		for(int i = 0; i < UpPacePoints.length; i++){
			p = new Pacer(tileMap, 2, "up", 3.7);
			p.setPosition(UpPacePoints[i].x, UpPacePoints[i].y);
			p.init(UpPacePoints[i].x, UpPacePoints[i].y);
			pacers.add(p);
			enemies.add(p);
			
		}
		
		Point[] RightPacePoints = new Point[]{ 
				new Point(2896, 336),
				new Point(2896, 592),
				};
		
		for(int i = 0; i < RightPacePoints.length; i++){
			p = new Pacer(tileMap, 1, "right", 3.7);
			p.setPosition(RightPacePoints[i].x, RightPacePoints[i].y);
			p.init(RightPacePoints[i].x, RightPacePoints[i].y);
			pacers.add(p);
			enemies.add(p);	
		}
		
		Point[] LeftPacePoints = new Point[]{ 
				new Point(3024, 464),
				};
		
		for(int i = 0; i < LeftPacePoints.length; i++){
			p = new Pacer(tileMap, 1, "left", 3.7);
			p.setPosition(LeftPacePoints[i].x, LeftPacePoints[i].y);
			p.init(LeftPacePoints[i].x, LeftPacePoints[i].y);
			pacers.add(p);
			enemies.add(p);	
		}
		
		Point[] DownWaitPoints = new Point[]{ 
				new Point(1104, 368),
				new Point(1200, 368),
				};
		
		for(int i = 0; i < DownWaitPoints.length; i++){
			w = new Waiter(tileMap, 0, "down");
			w.setPosition(DownWaitPoints[i].x, DownWaitPoints[i].y);
			enemies.add(w);
			waiters.add(w);
		}
		
	}
	
	public void update() {
		player.update();
		player.checkAttack(enemies);
		
	
		for(int i = 0; i < spitters.size(); i++){
			spitters.get(i).checkSpitHit(player);
		}
		
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
		for(int i = 0; i < waiters.size(); i++){
			Waiter w = waiters.get(i);
			if(w.getXScreen() > 0 && w.getXScreen() < 1024){
				if( w.getYScreen() > 0 && w.getYScreen() < 1024){
			w.followPlayer(player);
				}
			}
		}
//		
		for(int i = 0; i < sprinters.size(); i++){
			Sprinter s = sprinters.get(i);
			s.Sprint(player);
		}
		
		for(int i = 0; i < pacers.size(); i++){
			Pacer p = pacers.get(i);
			if(p.getXScreen() > 0 && p.getXScreen() < 1024){
				if(p.getYScreen() > 0 && p.getYScreen() < 1024){
			p.whereToGo(player);
				}
			}
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
