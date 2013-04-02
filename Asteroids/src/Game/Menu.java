package Game;

import Helpers.Color;
import java.awt.event.KeyEvent;
import Maths.Vector2;
import Renderer.*;
import GameObjects.GameChar;
import Game.Controls;


public class Menu {
	
	Renderer render; 
	GameChar startGameButton;
	GameChar instruction;
	GameChar random;
	GameChar background;
	GameChar quitButton;
	GameChar instructionTitle;
	public int counter = 0;
	public int counter1 = 0, counter2 = 0;
	public boolean inInstructions = false;
	public boolean inGame = false;

	public Menu()
	{ 
		Controls.menuCounter = 0;
		background = new GameChar();
		startGameButton = new GameChar();
		instruction = new GameChar();
		quitButton = new GameChar();
		background.objectRenderer.SetTexture("background");
		background.transform.size = new Vector2(60,50);
		startGameButton.objectRenderer.SetTexture("logoOnHover");
		startGameButton.transform.size = new Vector2 (17,17);
		startGameButton.transform.position = new Vector2 (0,10);
		startGameButton.rigidBody.frictionCoefficient = 0.1f;
		instruction.objectRenderer.SetTexture("instruction");
		instruction.transform.size = new Vector2 (25,18);
		instruction.transform.position = new Vector2 (0,-9);
		instruction.rigidBody.frictionCoefficient = 0.1f;
		quitButton.objectRenderer.SetTexture("quit");
		quitButton.transform.position = new Vector2 (0,-8);
		quitButton.transform.size = new Vector2 (11,11);
		
		}
		
	public void Update(){ 
		if(!inInstructions){
		switch(Controls.menuCounter){
		case 0:
			startGameButton.objectRenderer.SetTexture("logoOnHover"); 
			instruction.objectRenderer.SetTexture("instruction");
			quitButton.objectRenderer.SetTexture("quit");
			
			MainGame.render.DrawText("drawing text exemple",Vector2.zero(),Color.Blue,1f);
			
			
			
			break;
		case 1:
			startGameButton.objectRenderer.SetTexture("logo"); 
			instruction.objectRenderer.SetTexture("instructionOnHover");
			quitButton.objectRenderer.SetTexture("quit");

			break;
		case 2:
			startGameButton.objectRenderer.SetTexture("logo"); 
			instruction.objectRenderer.SetTexture("instruction");
			quitButton.objectRenderer.SetTexture("quitOnHover");
			break;
		}
		}
			if (MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				switch(Controls.menuCounter){
				case 0:
					MainGame.inMenu = false;
					MainGame.enterKeyPressed = true;
					inGame = true;
					break;
				case 1:
					if (counter == 0){
						initInstructions();
						inInstructions = true;
						counter++;
					}
					random.Update();
					instructionTitle.Update();
					break;
				case 2:
					System.exit(0);
					break;
				}
			}
			if (MainGame.controls.isPressed(KeyEvent.VK_BACK_SPACE)){
				if(inInstructions){
					if (counter1 == 0){
						initMenu();
						Controls.menuCounter = 0;
						counter1++;
					}
					inInstructions = false;
					counter = 0;
					counter1 = 0;
					random.Delete();
					instructionTitle.Delete();
				} 
		} else {
			background.Update();
			startGameButton.Update();
			instruction.Update();
			quitButton.Update();
		}	
		
	}
	public void initInstructions(){
		startGameButton.Delete();
		instruction.Delete();
		quitButton.Delete();
		random = new GameChar(); 
		instructionTitle = new GameChar();
		instructionTitle.objectRenderer.SetTexture("instructionTitle");
		instructionTitle.transform.size = new Vector2 (20,5);
		instructionTitle.transform.position = new Vector2 (25,45);
		random.objectRenderer.SetTexture("random");
		random.transform.size = new Vector2 (35,25);
	}
	
	public void initMenu() { 
		background = new GameChar();
		startGameButton = new GameChar();
		instruction = new GameChar();
		quitButton = new GameChar();
		background.objectRenderer.SetTexture("background");
		background.transform.size = new Vector2(60,60);
		startGameButton.objectRenderer.SetTexture("logoOnHover");
		startGameButton.transform.size = new Vector2 (17,17);
		startGameButton.transform.position = new Vector2 (0,10);
		startGameButton.rigidBody.frictionCoefficient = 0.1f;
		instruction.objectRenderer.SetTexture("instruction");
		instruction.transform.size = new Vector2 (25,18);
		instruction.transform.position = new Vector2 (0,-9);
		instruction.rigidBody.frictionCoefficient = 0.1f;
		quitButton.objectRenderer.SetTexture("quit");
		quitButton.transform.position = new Vector2 (0,-8);
		quitButton.transform.size = new Vector2 (11,11);
	}
}


