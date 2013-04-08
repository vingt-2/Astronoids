/**
 * MainGame:
 * 		Here we instantiate all of our game singletons, as well as the menu and game logics.
 * 		The main method is also located here.
 *		
 *		The static Singletons that can be accessed everywhere in the program are:
 *			sharedRessources -> The lookup table of ressources
 *			render	-> The One and only Renderer object of the program, holds info concerning the gl Context.
 *			controls -> A key listener thread. One can know if a key is pressed using isPressed(int key)
 *			debug	-> a debug object that can be summoned to do cool debug stuff for us. 
 * 
 */

package Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.media.opengl.GL2;

import GameObjects.Alien;
import Game.SharedRessources.RessourceType;

import GameObjects.Asteroid;
import GameObjects.FieldGenerator;
import GameObjects.GameChar;
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
	public static boolean enterKeyPressed 	= false;
	public static boolean inStartGame = true;

	
	
	

	public static boolean winChecker = false;
	public int counter=0;


	// Game singletons
	public static final SharedRessources sharedRessources	= new SharedRessources();
	public static final Renderer render 					= new Renderer(Window_Name);
	public static final Controls controls 					= new Controls();
	public static final Debug debug 						= new Debug();
	public static Menu menu; 								//= new Menu();
	public static GameChar GameOver;
	public static GameChar Win;
	public GameLogic logic;
	


	public static ArrayList<GameChar> objectVector;

	public static Player player;
	public static FieldGenerator fieldGenerator;

	static Alien alien;

	Laser laser;
	

	public void init(GL2 gl)
	{
		if (inMenu) 
		{ 
			sharedRessources.LoadRessources
			(new Ressource[] 
					{
					new Ressource("instructionTitle","./resources/textures/instructionFont.png", RessourceType.Texture),
					new Ressource("quitOnHover","./resources/textures/quitOnHover.png", RessourceType.Texture),
					new Ressource("quit","./resources/textures/quit.png", RessourceType.Texture),
					new Ressource("random","./resources/textures/instructions.png", RessourceType.Texture),
					new Ressource("instructionOnHover","./resources/textures/vertOnHover.png", RessourceType.Texture),
					new Ressource("instruction","./resources/textures/vert.png", RessourceType.Texture),
					new Ressource("logoOnHover","./resources/textures/startGameOnHover.png", RessourceType.Texture),
					new Ressource("logo","./resources/textures/startGame.png",RessourceType.Texture),
					new Ressource("background","./resources/textures/space.jpg", RessourceType.Texture)
					}
					);
			menu = new Menu();
		}
		else 
		{ 
			menu.startGameButton.Delete();
			menu.instruction.Delete();
			menu.quitButton.Delete();

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
					new Ressource("Win","./resources/textures/anti_mind_virus.png", RessourceType.Texture)
					}
					);
			player = new Player();
			player.transform.size = new Vector2(4,4);
			player.rigidBody.frictionCoefficient = 0.05f;

			alien = new Alien();
			alien.transform.size=new Vector2(2,2);
			alien.rigidBody.frictionCoefficient= 0.05f;
		}
		fieldGenerator = new FieldGenerator(12, 5);
		logic = new GameLogic();
	}

	public void Update(GL2 gl)
	{
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
		if(inMenu)
		{ 
			menu.Update();
		} 
		else 
		{
			if (enterKeyPressed) 
			{ 
				init (gl);
				enterKeyPressed = false;
			}

			// Put Game Logic here

			logic.UpdateLogic();

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
