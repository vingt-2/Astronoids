package Game;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.media.opengl.GL2;

import GameComponents.ObjectRenderer.Shape;

import GameObjects.Alien;
import Game.SharedRessources.RessourceType;

import GameObjects.Asteroid;
import GameObjects.FieldGenerator;
import GameObjects.GameChar;
import GameObjects.HUD;
import GameObjects.Laser;
import GameObjects.Player;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	//Main Parameters
	public static String Window_Name		= "Asteroids";
	public static Vector2 Screen_Size 		= new Vector2(1024,780);
	public static boolean inMenu			= true;
	public static boolean inPauseGame		= false;
	public static boolean enterKeyPressed 	= false;
	public static boolean inStartGame = true;

	public boolean update=true;
	public int pressCount = 0;
	
	public static boolean winChecker = false;
	public int counter=0;


	// Game singletons
	public static final SharedRessources sharedRessources	= new SharedRessources();
	public static final Renderer render 					= new Renderer(Window_Name);
	public static final Controls controls 					= new Controls();
	public static final Debug debug 						= new Debug();
	public static  Menu menu; 								//= new Menu();
	public static GameChar GameOver;
	public static GameChar Win;
	public static GameChar pauseImage;
	
	public GameLogic logic;
	

	public static ArrayList<GameChar> objectVector;

	public static Player player;
	public static FieldGenerator fieldGenerator;
	Laser laser;
	static Alien alien;
	public static HUD hud;

	

	public void init(GL2 gl)
	{
		if (inMenu) { 
			sharedRessources.LoadRessources
			(new Ressource [] {
						new Ressource("instructionTable","./resources/textures/instructionTable.png",RessourceType.Texture),
						new Ressource("instructionFont","./resources/textures/instructionFont.png",RessourceType.Texture),
						new Ressource("startGame","./resources/textures/startGame.png",RessourceType.Texture),
						new Ressource("instructions","./resources/textures/instructions.png",RessourceType.Texture),
						new Ressource("statistics","./resources/textures/statistics.png",RessourceType.Texture),
						new Ressource("highScores","./resources/textures/highScores.png",RessourceType.Texture),
						new Ressource("startGameOnHover","./resources/textures/startGameOnHover.png",RessourceType.Texture),
						new Ressource("instructionsOnHover","./resources/textures/instructionsOnHover.png",RessourceType.Texture),
						new Ressource("statisticsOnHover","./resources/textures/statisticsOnHover.png",RessourceType.Texture),
						new Ressource("highScoresOnHover","./resources/textures/highScoresOnHover.png",RessourceType.Texture),
						new Ressource("enterUsername","./resources/textures/enterUsername.png", RessourceType.Texture),
						new Ressource("quitOnHover","./resources/textures/Quit_hover.png", RessourceType.Texture),
						new Ressource("quit","./resources/textures/quit.png", RessourceType.Texture),
						new Ressource("twoPlayerOnHover","./resources/textures/two_player_hover.png",RessourceType.Texture),
						new Ressource("twoPlayer","./resources/textures/two_player.png", RessourceType.Texture),
						new Ressource("loadUserOnHover","./resources/textures/load_user_hover.png", RessourceType.Texture),
						new Ressource("loadUser","./resources/textures/load_user.png", RessourceType.Texture),
						new Ressource("createUserOnHover","./resources/textures/create_user_hover.png", RessourceType.Texture),
						new Ressource("createUser","./resources/textures/create_user.png",RessourceType.Texture),
						new Ressource("background","./resources/textures/space.jpg", RessourceType.Texture)
					}
			);
			menu = new Menu();
		} else { 
			
		menu.startGame.Delete();
		menu.instructions.Delete();
		menu.statistics.Delete();
		menu.highScores.Delete();
		
			sharedRessources.LoadRessources
			(new Ressource[]
					{
					new Ressource("rocket_ship","./resources/textures/awesome_space_ship.png",RessourceType.Texture),
					new Ressource("shielded_ship","./resources/textures/awesome_space_ship_shielded.png",RessourceType.Texture),
					new Ressource("smoke","./resources/textures/SmokeParticle.png",RessourceType.Texture),
					new Ressource("Alien","./resources/textures/Alien.png", RessourceType.Texture ),
					new Ressource("asteroid", "./resources/textures/Asteroid_2.png", RessourceType.Texture),
					new Ressource("redLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("greenLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("blueLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("game_over","./resources/textures/Game_over.png", RessourceType.Texture),
					new Ressource("Win","./resources/textures/anti_mind_virus.png", RessourceType.Texture),
					new Ressource("Empty","./resources/textures/Untitled-1.png", RessourceType.Texture),
					new Ressource("Life","./resources/textures/Life.png", RessourceType.Texture)
					}
					);
			player = new Player();
			player.transform.size = new Vector2(4,4);
			player.rigidBody.frictionCoefficient = 0.05f;

			alien = new Alien();
			alien.transform.size=new Vector2(2,2);
			alien.rigidBody.frictionCoefficient= 0.05f;

			
			hud = new HUD();

		}
		
		
		fieldGenerator = new FieldGenerator(12, 5);

		
		
//		alien = new Alien();
//		alien.transform.size=new Vector2(2,2);
//		alien.rigidBody.frictionCoefficient= 0.1f;

		logic = new GameLogic();
		

	}


	
	
	public void Update(GL2 gl)
	{
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
		if(inMenu){ 
			menu.Update();
	} else {
		if (enterKeyPressed) { 
			init (gl);
			enterKeyPressed = false;
		}
		
			if (enterKeyPressed) 
			{ 
				init (gl);
				enterKeyPressed = false;
			}

			// Put Game Logic here
			if(controls.isPressed(KeyEvent.VK_P) && pressCount==0){
				update = !(update);
				pressCount++;
			}
			if(!controls.isPressed(KeyEvent.VK_P))
				pressCount = 0;
			if(update)
				logic.UpdateLogic();

			else{ //Pause Game
				
			}


		}

		//alien.Update();
		
		//player.rigidBody.isColliding(player,alien);
		
	}

	public static void main(String[] args)
	{

		MainGame game = new MainGame();							// Initiating a new Game.
	
		render.mainGame = game;									// Associate this Game to the renderer.
		render.CreateWindow(new Vector2(1024,780),controls);	// Create a new Frame object and returns its reference.
		
	}
	

}
