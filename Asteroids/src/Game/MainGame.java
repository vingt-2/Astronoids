package Game;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;

import CSV.User;
import Game.SharedRessources.RessourceType;
import Helpers.Debug;
import Helpers.SoundEffect;
import Maths.Vector2;
import Renderer.Renderer;

/**
 * Holds the architecture main loop.
 * @author Vincent Petrella
 *
 *  <p>
 *	Here we instantiate all of our game singletons, as well as the menu and game logics.
 *  The main method is also located here.
 *  </p>   
 *  <p> 
 *  The static Singletons that can be accessed everywhere in the program are:
 *  <ul>
 *  <li> sharedRessources -> The lookup table of ressources </li>
 *  <li>render  -> The One and only Renderer object of the program, holds info concerning the gl Context.</li>
 *  <li>controls -> A key listener thread. One can know if a key is pressed using isPressed(int key) </li>
 *  <li>debug  -> a debug object that can be summoned to do cool debug stuff for us. </li>
 *	</ul></p>
 *	<p>
 *	Careful! The Main Game Loop is not explicitly here (even though it LOGICALLY is here):
 *  JOGL GLEventListener thread calls display() in the render Singleton Object, which then call's this Update() function (where the game loop actually happens).
 *  </p>   
 *  @see Renderer
 */

public class MainGame 
{
	//Main Parameters
	public static String Window_Name		= "Asteroids";
	public static Vector2 Screen_Size 		= new Vector2(1024,780);
	public static boolean inMenu			= true;
	public static boolean inPauseGame		= false;
	public static boolean enterKeyPressed 	= false;
	public static boolean twoPlayerMode 	= false;
	public static boolean inStartGame 		= true;
	public static boolean inPauseGameMode 	= false;
	public static boolean inStartMenu		= false;

	public static boolean  update =true;
	public int pressCount = 0;

	public static boolean winChecker = false;
	public int counter=0;
	public static User currentUser;

	public static boolean playerOne = false;
	public static boolean playerTwo = false;
	public static int scoreOne = 0;
	public static int scoreTwo = 0;

	// Game singletons
	public static final SharedRessources sharedRessources	= new SharedRessources();
	public static final Renderer render 					= new Renderer(Window_Name);
	public static final Controls controls 					= new Controls();
	public static final Debug debug 						= new Debug();

	public static Menu menuLogic; 								
	public static GameLogic gameLogic;


	public void init(GL2 gl)
	{

		if (inMenu) { 
			sharedRessources.LoadRessources
			(new Ressource [] {
					new Ressource("resumeGameOnHover","./resources/textures/resumeGameOnHover.png",RessourceType.Texture),
					new Ressource("resumeGame","./resources/textures/resumeGame.png",RessourceType.Texture),
					new Ressource("mainMenuOnHover","./resources/textures/mainMenuOnHover.png",RessourceType.Texture),
					new Ressource("mainMenu","./resources/textures/mainMenu.png",RessourceType.Texture),
					new Ressource("hardOnHover","./resources/textures/hardOnHover.png",RessourceType.Texture),
					new Ressource("hard","./resources/textures/hard.png",RessourceType.Texture),
					new Ressource("mediumOnHover","./resources/textures/mediumOnHover.png",RessourceType.Texture),
					new Ressource("medium","./resources/textures/medium.png",RessourceType.Texture),
					new Ressource("easyOnHover","./resources/textures/easyOnHover.png",RessourceType.Texture),
					new Ressource("easy","./resources/textures/easy.png",RessourceType.Texture),
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
					new Ressource("quitOnHover","./resources/textures/quitOnhover.png", RessourceType.Texture),
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
			menuLogic = new Menu();
		} 

		else 
		{ 
			sharedRessources.LoadRessources
			(new Ressource[]
					{
					new Ressource("rocket_ship","./resources/textures/awesome_space_ship.png",RessourceType.Texture),
					new Ressource("smoke","./resources/textures/SmokeParticle.png",RessourceType.Texture),
					new Ressource("Alien","./resources/textures/Alien.png", RessourceType.Texture ),
					new Ressource("asteroid", "./resources/textures/Asteroid_2.png", RessourceType.Texture),
					new Ressource("redLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("greenLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("blueLaser","./resources/textures/Laser.png", RessourceType.Texture),
					new Ressource("game_over","./resources/textures/Game_over.png", RessourceType.Texture),
					new Ressource("Win","./resources/textures/anti_mind_virus.png", RessourceType.Texture),
					new Ressource("Empty","./resources/textures/Untitled-1.png", RessourceType.Texture),
					new Ressource("Life","./resources/textures/Life.png", RessourceType.Texture),
					new Ressource("Shield","./resources/textures/Shield.png", RessourceType.Texture),
					new Ressource("Life","./resources/textures/Life.png", RessourceType.Texture),
					new Ressource("Alien2", "./resources/textures/Alien2.png", RessourceType.Texture),
					new Ressource("RapidFire","./resources/textures/rapidFire.png", RessourceType.Texture),
					new Ressource("Stage2","./resources/textures/Stage2.png", RessourceType.Texture),
					new Ressource("Stage3","./resources/textures/Stage3.png", RessourceType.Texture),
					new Ressource("Stage4","./resources/textures/Stage4.png", RessourceType.Texture),
					new Ressource("Stage5","./resources/textures/Stage5.png", RessourceType.Texture),
					new Ressource("Shrapnel","./resources/textures/Shrapnel.png", RessourceType.Texture)
					}
					);

			SoundEffect.SHOOT.ordinal();
			SoundEffect.SHOOT.ordinal();
			SoundEffect.AFTERBURN.ordinal();
			SoundEffect.CRASH.ordinal();
			SoundEffect.ASTEROIDBREAK.ordinal();
			SoundEffect.EXPLOSION.ordinal();

			render.CheatTime();

			gameLogic = new GameLogic();
		}
	}


	public void Update(GL2 gl)
	{
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
		if(inMenu){ 
			menuLogic.Update();
		} else {
			if (enterKeyPressed) { 
				init (gl);
				enterKeyPressed = false;
			}
			// Two Player mode
			if(twoPlayerMode){
				init (gl);
				playerOne = true;
				playerTwo = true;
				twoPlayerMode = false;
			} 

			if(controls.isPressed(KeyEvent.VK_P) && pressCount==0){
				update =!(update);
				pressCount++;
			}
			if(!controls.isPressed(KeyEvent.VK_P))
				pressCount = 0;
			if(update){
				gameLogic.UpdateLogic();
				if(inPauseGameMode){
					menuLogic.resumeGame.Delete();
					menuLogic.backToMenu.Delete();
					menuLogic.quitPause.Delete();
					inPauseGameMode = false;
				}
			}

			else{ //Pause Game
				inMenu = true;
				inPauseGameMode = true;
				menuLogic.inLevelMenu = false;
			}
		}

	}

	public static void main(String[] args)
	{

		MainGame game = new MainGame();							// Initiating a new Game.

		render.mainGame = game;									// Associate this Game to the renderer.
		render.CreateWindow(new Vector2(1024,780),controls);	// Create a new Frame object and returns its reference.
		//SoundEffect.BACKGROUND.ordinal();
		//SoundEffect.BACKGROUND.play();
	}


}
