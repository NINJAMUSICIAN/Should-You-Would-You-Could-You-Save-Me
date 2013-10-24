package GameState;

import java.util.ArrayList;

import GameState.Cutscenes.Cut1;
import GameState.Cutscenes.Cut2;
import GameState.Cutscenes.Cut3;
import GameState.Cutscenes.Cut4;
import GameState.Levels.Level1State;
import GameState.Levels.Level2State;
import GameState.Levels.Level3State;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int BLANKLEVEL = 0;
	public static final int FIRSTCUT = 1;
	public static final int LEVEL1STATE = 2;
	public static final int SECONDCUT = 3;
	public static final int LEVEL2STATE =4;
	public static final int THIRDCUT = 5;
	public static final int LEVEL3STATE = 6;
	//public static final int FINALCUT = 7;
	
	public GameStateManager(){
		gameStates = new ArrayList<GameState>();
		
		//ADD STATES HERE
		currentState = BLANKLEVEL;
	
		gameStates.add(new BlankLevel(this));
		gameStates.add(new Cut1(this));
		gameStates.add(new Level1State(this));
		gameStates.add(new Cut2(this));
		gameStates.add(new Level2State(this));
		gameStates.add(new Cut3(this));
		gameStates.add(new Level3State(this));
		gameStates.add(new Cut4(this));
		
	}
	
	public void setState(int state){
		currentState = state;
		gameStates.get(currentState).init();
	}
	public void update(){
		gameStates.get(currentState).update();
	}
	public void draw(java.awt.Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
	
	
}
