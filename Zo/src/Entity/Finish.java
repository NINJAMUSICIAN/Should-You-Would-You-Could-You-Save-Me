package Entity;

import GameState.GameStateManager;
import TileMap.TileMap;

public class Finish extends MapObject{

	private int goState;

	private GameStateManager gsm;
	
	
	public Finish(int x, int y, int newState, GameStateManager gsm, TileMap tm) {
		super(tm);
		
		goState = newState;
		this.x = x;
		this.y = y;
		this.gsm = gsm;
		
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
		
	}
	
	public void checkGrab(Rachel player){
		if(intersects(player)){
			grab(player);
			player.setDx(0);
			
			
		}
	}
	
	
	
	public void grab(Rachel player){	
		gsm.setState(goState);
	}
	
	public void update(){
		
	}
	
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
	}
}
