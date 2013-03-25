package Game;

import java.awt.event.KeyEvent;
import java.awt.image.renderable.RenderableImage;

import javax.media.opengl.GL2;


import Renderer.*;
import GameComponents.ObjectRenderer.Shape;
import GameObjects.GameChar;
import Game.Controls;


public class Menu {

	Renderer render; 
	GameChar playButton;
	GameChar startGameButton;
	
	public Menu(){ //constructor
		startGameButton = new GameChar();
		
		
		}
		
	public void Update(){ 
		System.out.println("prout");
			if (MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				System.out.println("prout2");
				//MainGame.inMenu = false;
		} else { 
			startGameButton.Update();
			System.out.println("prout3");
		}	
	}
		
	}


