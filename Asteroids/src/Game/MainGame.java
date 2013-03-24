package Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.media.opengl.GL2;

import GameComponents.ObjectRenderer.Shape;
import GameObjects.Alien;
import Game.SharedRessources.RessourceType;
import GameObjects.GameChar;
import GameObjects.Player;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	//Main Parameters
	public static String Window_Name		= "Asteroids";
	public static Vector2 Screen_Size 		= new Vector2(1024,780);
	
	
	// Game singletons
	public static final SharedRessources sharedRessources	= new SharedRessources();
	public static final Renderer render 					= new Renderer(Window_Name);
	public static final Controls controls 					= new Controls();
	public static final Debug debug 						= new Debug();

	
	ArrayList<GameChar> objectVector;
	public static Player player;
	 Alien alien;


	public void init(GL2 gl)
	{
		sharedRessources.LoadRessources
		(new Ressource[]
			{
			
				new Ressource("rocket_ship","./resources/textures/rocket_ship.png",RessourceType.Texture),
				new Ressource("smoke","./resources/textures/SmokeParticle.png",RessourceType.Texture),
				new Ressource("Alien","./resources/textures/Alien.png", RessourceType.Texture )
			}
		);

		player = new Player();
		player.transform.size = new Vector2(3,3);
		player.rigidBody.frictionCoefficient = 0.1f;
		
		alien = new Alien();
		alien.transform.size=new Vector2(2,2);
		alien.rigidBody.frictionCoefficient= 0.1f;
		//alien.objectRenderer.shape=Shape.Square;
		//render.renderVector.add(alien.objectRenderer);

	}

	public void Update(GL2 gl)
	{
		// Put Game Logic here
		
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}

		//Update object_1 transform, physics, rendering etc...
		player.Update();
		alien.Update();
	}

	public static void main(String[] args)
	{

		MainGame game = new MainGame();							// Initiating a new Game.
	
		render.mainGame = game;									// Associate this Game to the renderer.
		render.CreateWindow(new Vector2(1024,780),controls); // Create a new Frame object and returns its reference.
		
	}
	
//	// Main Method. DO NOT touch this. If you do. YOU WILL DIE.
//	public static void main(String[] args)
//	{
//
//		test += 0.01f;
//
//		if(test < 2){
//			//Draw object 1 on a circular pattern (x,y) = (a*cost,a*sint)
//
//			// the position depends on t test which varies in time, thus MOVEMENT !
//
//			player.transform.rotation   = (float) Math.cos(test);
//			player.transform.position.x = 100f * (float) Math.cos(test);
//			player.transform.position.y = 100f * (float) Math.sin(test);
//
//			//Draw object 2 on a circular pattern (x,y) = (a*cost,a*sint)
//
//			// the position depends on t test which varies in time, thus MOVEMENT !
//
//			object_2.transform.rotation   = (float) Math.cos(test);
//			object_2.transform.position.x = -140f * (float) Math.sin(test);
//			object_2.transform.position.y = -140f * (float) Math.cos(test);
//			
//			
//			
//		}
//
//		// debug Rays will be USEFUL(!) to show vectors to our puny human eyes.
//		debug.DrawRay(Vector2.zero(), player.transform.position,Color.Green);
//		debug.DrawRay(Vector2.zero(), object_2.transform.position,Color.Blue);
//		debug.DrawRay(Vector2.zero(), alien.transform.position, Color.Red);
//		
//
//		// Draw the line between object1 and object2
//		debug.DrawRay(player.transform.position, object_2.transform.position,Color.Red);
//	}
//	
//    
//   
//    
//
//
//		MainGame game = new MainGame();							// Initiating a new Game.
//		
//		render.mainGame = game;									// Associate this Game to the renderer.
//		render.CreateWindow(new Vector2(1024,780),controls);	// Create a new Frame object and returns its reference.
//	}
//	

}
