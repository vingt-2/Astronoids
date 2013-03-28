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
	
	
	// Game singletons
	public static final SharedRessources sharedRessources	= new SharedRessources();
	public static final Renderer render 					= new Renderer(Window_Name);
	public static final Controls controls 					= new Controls();
	public static final Debug debug 						= new Debug();
	public static  Menu menu; 								//= new Menu();

	
	public static ArrayList<GameChar> objectVector;

	public static Player player;


	Alien alien;
	public static FieldGenerator fieldGenerator;
	Laser laser;

	
	public void init(GL2 gl)
	{
		if (inMenu) { 
			sharedRessources.LoadRessources
			(new Ressource [] {
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
		} else { 
			
		menu.startGameButton.Delete();
		menu.instruction.Delete();
		menu.quitButton.Delete();

		sharedRessources.LoadRessources
		(new Ressource[]
			{
			
				new Ressource("rocket_ship","./resources/textures/rocket_ship.png",RessourceType.Texture),
				new Ressource("smoke","./resources/textures/SmokeParticle.png",RessourceType.Texture),
				new Ressource("Alien","./resources/textures/Alien.png", RessourceType.Texture ),
				new Ressource("asteroid", "./resources/textures/asteroid.png", RessourceType.Texture),
				new Ressource("Laser","./resources/textures/Laser.png", RessourceType.Texture )
			}
		);
 		player = new Player();
		player.transform.size = new Vector2(3,3);
		player.rigidBody.frictionCoefficient = 0.1f;
		
		alien = new Alien();
		alien.transform.size=new Vector2(2,2);
		alien.rigidBody.frictionCoefficient= 0.1f;
		}
				
		fieldGenerator = new FieldGenerator(12, 5);
	
		
//		alien = new Alien();
//		alien.transform.size=new Vector2(2,2);
//		alien.rigidBody.frictionCoefficient= 0.1f;

	}
	
	public void Update(GL2 gl)
	{
		
		
		if(inMenu){ 
			menu.Update();
	} else {
		if (enterKeyPressed) { 
			init (gl);
			enterKeyPressed = false;
		}
		
		// Put Game Logic here
		
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}

		
		ArrayList<Asteroid> rocks = fieldGenerator.GetAsteroidArray();
		Laser[] lasers = player.secondEffect.GetLaserArray();
		//System.out.println(rocks[4].terminator);

		for (int i = 0; i<rocks.size(); i++)
		{
			//System.out.println(rocks[i].terminator);
			if (!(rocks.size()<= i)){
				if(player.rigidBody.isColliding(player, rocks.get(i) ))
				{
					//rocks[i].objectRenderer.opacity = 0;
					//rocks.get(i).terminator = true;
				//	rocks.get(i).Delete();
				//	rocks.remove(i);
				//	fieldGenerator.number--;
					
				}
				for(int j =0; j<lasers.length; j++){
					if( lasers[j] != null && i!=rocks.size()) {
						if(lasers[j].rigidBody.isColliding(lasers[j], rocks.get(i))){
							rocks.get(i).Delete();
							rocks.remove(i);
							fieldGenerator.number--;
							System.out.println("boom");
						}
					}
				}
			}
			
			if(!(fieldGenerator.GetAsteroidArray().size()<= i))
			rocks.get(i).terminator = false;
			
		}
		

		

		//Update object_1 transform, physics, rendering etc...
		
		player.Update();
		alien.Update();
		fieldGenerator.Update();

		

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
