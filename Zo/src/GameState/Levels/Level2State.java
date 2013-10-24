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

public class Level2State extends GameState{

private TileMap tileMap;
	
	private Rachel player;
	
	private Finish finish;
	
	private ArrayList<Waiter> waiters;
	private ArrayList<Sprinter> sprinters;
	private ArrayList<Enemy> enemies;
	
	public Level2State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Tileset.png");
		tileMap.loadMap("/Maps/Level2.map");
		tileMap.setPosition(-32, -32);
		tileMap.setTween(1);
		
		player = new Rachel("/Sprites/TheGirl.png", tileMap);
		player.setPosition(840, 690); //840 660
		
		finish = new Finish(463, 367, GameStateManager.THIRDCUT, gsm, tileMap);//175
		
		populateEnemies();
	
	}

	public void populateEnemies(){
		sprinters = new ArrayList<Sprinter>();
		enemies = new ArrayList<Enemy>();
		
		Sprinter s;
		
		Point[] leftSprintPoints = new Point[]{ 
	
				new Point(975, 543),
				new Point(975, 415),
				new Point(975, 287),
				new Point(975, 159),
				new Point(335, 383),
				new Point(335, 511),
				new Point(655, 623),
				new Point(655, 495)
				};
		
		for(int i = 0; i < leftSprintPoints.length; i++){
			s = new Sprinter(tileMap, 1, "left");
			s.setPosition(leftSprintPoints[i].x, leftSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
		Point[] rightSprintPoints = new Point[]{ 
				new Point(719, 607),
				new Point(719, 479),
				new Point(719, 351),
				new Point(719, 223),
				new Point(79, 319),
				new Point(79, 447),
				new Point(79, 575),
				new Point(399, 559),
				new Point(399, 431),
				};
		
		for(int i = 0; i < rightSprintPoints.length; i++){
			s = new Sprinter(tileMap, 1, "right");
			s.setPosition(rightSprintPoints[i].x, rightSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
		Point[] downSprintPoints = new Point[]{ 
				new Point(287, 47),
				new Point(415, 47),
				new Point(543, 47),
				new Point(671, 47),

				};
		
		for(int i = 0; i < downSprintPoints.length; i++){
			s = new Sprinter(tileMap, 0, "down");
			s.setPosition(downSprintPoints[i].x, downSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
		Point[] upSprintPoints = new Point[]{ 
				new Point(351, 271),
				new Point(479, 271),
				new Point(607, 271),
				};
		
		for(int i = 0; i < upSprintPoints.length; i++){
			s = new Sprinter(tileMap, 2, "up");
			s.setPosition(upSprintPoints[i].x, upSprintPoints[i].y);
			enemies.add(s);
			sprinters.add(s);
		}
		
	}
	
	public void update() {

		player.update();
		player.checkAttack(enemies);
		
		System.out.println(player.getHealth());
		
		finish.update();
		finish.checkGrab(player);
		
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
