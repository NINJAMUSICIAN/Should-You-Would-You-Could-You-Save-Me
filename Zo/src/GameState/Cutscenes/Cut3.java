package GameState.Cutscenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import GameState.GameState;
import GameState.GameStateManager;

public class Cut3 extends GameState{
	
	private Animation animation;
	private BufferedImage[] scenet;
	private int scene;
	
	public Cut3(GameStateManager gsm){
		this.gsm = gsm;
		scene = 0;
		
		try{
			BufferedImage scenes = ImageIO.read(
					getClass().getResourceAsStream(
							"/Cutscene_Things/Cut3.png"
							)
						);
					
			scenet = new BufferedImage[3];
			for(int i = 0; i < scenet.length; i++){
				scenet[i] = scenes.getSubimage(0, 0, 1024, 720);

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(scenet);
		animation.setFrame(0);
		
	}
	
	public void init(){
		
	}
	
	public void update(){
		
		if(scene == 0){
			animation.setFrame(0);
		}else if(scene == 1){
			animation.setFrame(1);
		} else {
			animation.setFrame(2);
		}
	}
		
	public void switcher(){
		
		switch(scene){
		case 0:
			scene++;
			break;
		case 1:
			gsm.setState(GameStateManager.LEVEL3STATE);
		}
	}

	
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), 0, 0, null);		
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) switcher();
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	}
	

