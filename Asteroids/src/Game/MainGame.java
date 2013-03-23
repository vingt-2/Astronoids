package Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import GameComponents.ObjectRenderer.Shape;
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
	public static final Renderer render 	= new Renderer(Window_Name);
	public static final Controls controls 	= new Controls();
	public static final Debug debug 		= new Debug();

	
	ArrayList<GameChar> objectVector;
	Player player;

	public void init(GL2 gl)
	{
		player = new Player();

		player.transform.size = new Vector2(3,3);		
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
	}

	
	// Main Method. DO NOT touch this. If you do. YOU WILL DIE.
	public static void main(String[] args)
	{
		MainGame game = new MainGame();							// Initiating a new Game.
		
		render.mainGame = game;									// Associate this Game to the renderer.
		render.CreateWindow(new Vector2(1024,780),controls);	// Create a new Frame object and returns its reference.
	}
	
}
