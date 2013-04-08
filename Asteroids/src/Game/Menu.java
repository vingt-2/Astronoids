package Game;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.jogamp.graph.curve.opengl.Renderer;

import GameObjects.GameChar;
import Helpers.Color;
import Maths.Vector2;



public class Menu {
	Renderer render;
	GameChar createUser;
	GameChar loadUser;
	GameChar twoPlayer;
	GameChar quit;
	GameChar background;
	GameChar enterUsername;
	GameChar startGame;
	GameChar instructions;
	GameChar statistics;
	GameChar highScores;
	GameChar instructionTable;
	GameChar instructionFont;
	
	String inputUsername = "";
	public static int counter = 0;
	public static int counter1 = 0;
	public static int counter2 = 0;
	public static int counter3 = 0;
	public boolean inEnterUsernameNew = false;
	public boolean inEnterUsername = false;
	public boolean inGameMenu = false;
	public boolean inInstructions = false;
	public boolean stopShowing = true;
	public boolean inHighScores = false;
	public boolean inStatistics = false;
	
	public Menu(){ 
		Controls.menuCounter = 0;
		background = new GameChar();
		createUser = new GameChar();
		loadUser = new GameChar();
		twoPlayer = new GameChar();
		quit = new GameChar();
		background.objectRenderer.SetTexture("background");
		background.transform.size = new Vector2(60,50);
		createUser.objectRenderer.SetTexture("createUser");
		createUser.transform.size = new Vector2 (25,10);
		createUser.transform.position = new Vector2 (0,20);
		createUser.rigidBody.frictionCoefficient = 0.1f;
		loadUser.objectRenderer.SetTexture("loadUser");
		loadUser.transform.size = new Vector2 (25,10);
		loadUser.transform.position = new Vector2 (0,10);
		loadUser.rigidBody.frictionCoefficient = 0.1f;
		twoPlayer.objectRenderer.SetTexture("twoPlayer");
		twoPlayer.transform.size = new Vector2 (25,10);
		twoPlayer.transform.position = new Vector2 (0,-5);
		twoPlayer.rigidBody.frictionCoefficient = 0.1f;
		quit.objectRenderer.SetTexture("quit");
		quit.transform.position = new Vector2 (0,-10);
		quit.transform.size = new Vector2 (11,11);
		
		MainGame.controls.recordKey = true;
		
	}
	public void Update() { 
		if (!inEnterUsernameNew && !inGameMenu && !inEnterUsername){
		switch(Controls.menuCounter){
		case 0:
			createUser.objectRenderer.SetTexture("createUserOnHover");
			loadUser.objectRenderer.SetTexture("loadUser");
			twoPlayer.objectRenderer.SetTexture("twoPlayer");
			quit.objectRenderer.SetTexture("quit");
			
			break;
		case 1: 
			createUser.objectRenderer.SetTexture("createUser");
			loadUser.objectRenderer.SetTexture("loadUserOnHover");
			twoPlayer.objectRenderer.SetTexture("twoPlayer");
			quit.objectRenderer.SetTexture("quit");
			break;
		case 2:
			createUser.objectRenderer.SetTexture("createUser");
			loadUser.objectRenderer.SetTexture("loadUser");
			twoPlayer.objectRenderer.SetTexture("twoPlayerOnHover");
			quit.objectRenderer.SetTexture("quit");
			break;
		case 3:
			createUser.objectRenderer.SetTexture("createUser");
			loadUser.objectRenderer.SetTexture("loadUser");
			twoPlayer.objectRenderer.SetTexture("twoPlayer");
			quit.objectRenderer.SetTexture("quitOnHover");
			break;
		}
		}
		if (MainGame.controls.isPressed(KeyEvent.VK_ENTER) && !inGameMenu ){
			switch(Controls.menuCounter){
			case 0: 
				if (counter == 0)
				{
					initEnterUsername();
					inEnterUsernameNew = true;
					counter++;
				}
				break;
			case 1: 
				if (counter1 == 0 ){
					initEnterUsername();
					inEnterUsername = true;
					counter1 ++;
				}
				break;
			case 3:
				System.exit(0);
			}
		}
		if (inEnterUsernameNew){
			inEnterUsername();
		} 
		if (inEnterUsername){
			if(stopShowing){
			inputUsername = MainGame.controls.recordString;
			MainGame.render.DrawText(inputUsername,Vector2.zero(),Color.Blue,1f);
			}
			enterUsername.Update();
			if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				try {
					if(CSV.LoginMenu.login(inputUsername)){
						if (counter2 == 0){
							initGameMenu();
							inGameMenu = true;
							stopShowing = false;
							counter2++;
						}
						
					} else {
						System.out.println("Not possible");
					}
				} catch (IOException e) {
				}
			}
		} 
		if (inGameMenu){
			if (MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
					switch(Controls.menuCounter){
					case 0:
						MainGame.inMenu = false;
						MainGame.enterKeyPressed = true;
						break;
					case 1:
						if(counter3 == 0){
						inInstructions = true;
						initInstructions();
						counter3++;
						}
						instructionTable.Update();
						instructionFont.Update();
						break;
					case 2: 
						inStatistics = true;
						initHighScoresAndStatistics();
						break;
					case 3: 
						inHighScores = true;
						initHighScoresAndStatistics();
						break;
					}
				} 
			if (inHighScores){
				try {
					CSV.Highscore.getHighscores();
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
					}
					for(int i =0; i < 10; i++){
					MainGame.render.DrawText(CSV.Highscore.highscores.get(i)[0],
					new Vector2(-100, 80 - i*30),Color.Blue,1f);
					MainGame.render.DrawText(CSV.Highscore.highscores.get(i)[1],
					new Vector2(40, 80 - i*30),Color.Blue,1f);
					}
			}
			if (inStatistics){
				MainGame.render.DrawText("Username: " + CSV.LoginMenu.player.getUsername(),
						new Vector2(-100, 80),Color.Blue,1f);
						MainGame.render.DrawText("Average Score: "+CSV.LoginMenu.player.getAvgScore(),
						new Vector2(-100, 50),Color.Blue,1f);
						MainGame.render.DrawText("Games Played: "+CSV.LoginMenu.player.getNbGamesPlayed(),
						new Vector2(-100, 20),Color.Blue,1f);
						MainGame.render.DrawText("Play Time: "+CSV.LoginMenu.player.getPlayTime(),
						new Vector2(-100, -10),Color.Blue,1f);
						MainGame.render.DrawText("Best Score: "+CSV.LoginMenu.player.getBestScore(),
						new Vector2(-100, -40),Color.Blue,1f);
			}
			startGame.Update();
			instructions.Update();
			statistics.Update();
			highScores.Update();
			
		}
		if(inGameMenu && !inInstructions && !inHighScores && !inStatistics){
			switch(Controls.menuCounter){
			case 0:
				startGame.objectRenderer.SetTexture("startGameOnHover");
				instructions.objectRenderer.SetTexture("instructions");
				statistics.objectRenderer.SetTexture("statistics");
				highScores.objectRenderer.SetTexture("highScores");
				break;
			case 1: 
				startGame.objectRenderer.SetTexture("startGame");
				instructions.objectRenderer.SetTexture("instructionsOnHover");
				statistics.objectRenderer.SetTexture("statistics");
				highScores.objectRenderer.SetTexture("highScores");
				break;
			case 2:
				startGame.objectRenderer.SetTexture("startGame");
				instructions.objectRenderer.SetTexture("instructions");
				statistics.objectRenderer.SetTexture("statisticsOnHover");
				highScores.objectRenderer.SetTexture("highScores");
				break;
			case 3:
				startGame.objectRenderer.SetTexture("startGame");
				instructions.objectRenderer.SetTexture("instructions");
				statistics.objectRenderer.SetTexture("statistics");
				highScores.objectRenderer.SetTexture("highScoresOnHover");
				break;
			}
		}
		background.Update();
		createUser.Update();
		loadUser.Update();
		twoPlayer.Update();
		quit.Update();
		
	}
	private void inEnterUsername() {
		if(stopShowing){
		inputUsername = MainGame.controls.recordString;
		MainGame.render.DrawText(inputUsername,Vector2.zero(),Color.Blue,1f);
		}
		if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
			try {
				CSV.LoginMenu.addUser(inputUsername);
				CSV.LoginMenu.login(inputUsername);
			} catch (IOException e) {
			}
		}
		enterUsername.Update();
		
			if(CSV.LoginMenu.available){
				if (counter2 == 0){
					initGameMenu();
					inGameMenu = true;
					stopShowing = false;
					counter2++;
				}
			} else {
				System.out.println("Not possible");
			}
	}
	
	public void initEnterUsername() { 
		createUser.Delete();
		loadUser.Delete();
		twoPlayer.Delete();
		quit.Delete();
		enterUsername = new GameChar();
		enterUsername.objectRenderer.SetTexture("enterUsername");
		enterUsername.transform.size = new Vector2(25,10);
		enterUsername.transform.position = new Vector2(0,5);
		MainGame.controls.keyPressed[KeyEvent.VK_ENTER] = false;
	}
	
	public void initGameMenu(){
		Controls.menuCounter = 0;
		enterUsername.Delete();
		startGame = new GameChar();
		instructions = new GameChar();
		statistics = new GameChar();
		highScores = new GameChar();
		startGame.objectRenderer.SetTexture("startGame");
		startGame.transform.size = new Vector2 (25,10);
		startGame.transform.position = new Vector2 (0,20);
		startGame.rigidBody.frictionCoefficient = 0.1f;
		instructions.objectRenderer.SetTexture("instructions");
		instructions.transform.size = new Vector2 (25,10);
		instructions.transform.position = new Vector2 (0,10);
		instructions.rigidBody.frictionCoefficient = 0.1f;
		statistics.objectRenderer.SetTexture("statistics");
		statistics.transform.size = new Vector2 (25,10);
		statistics.transform.position = new Vector2 (0,-5);
		statistics.rigidBody.frictionCoefficient = 0.1f;
		highScores.objectRenderer.SetTexture("highScores");
		highScores.transform.position = new Vector2 (0,-10);
		highScores.transform.size = new Vector2 (25,10);
		MainGame.controls.keyPressed[KeyEvent.VK_ENTER] = false;
	}
	
	public void initInstructions(){ 
		startGame.Delete();
		instructions.Delete();
		statistics.Delete();
		highScores.Delete();
		instructionTable = new GameChar(); 
		instructionFont = new GameChar();
		instructionFont.objectRenderer.SetTexture("instructionFont");
		instructionFont.transform.size = new Vector2 (20,5);
		instructionFont.transform.position = new Vector2 (25,45);
		instructionTable.objectRenderer.SetTexture("instructionTable");
		instructionTable.transform.size = new Vector2 (35,25);
	}
	
	public void initHighScoresAndStatistics() {
		startGame.Delete();
		instructions.Delete();
		statistics.Delete();
		highScores.Delete();
	}
	
}
