package Game;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;

import jogamp.graph.math.MathFloat;

import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody;
import GameComponents.RigidBody.ForceMode;
import GameObjects.GameChar;
import GameObjects.Player;
import Helpers.Color;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	Renderer render;
	public static final Controls controls = new Controls();
	
	// Game singletons
	public static final Debug debug = new Debug();
	public static Vector2 Screen_Size = new Vector2(1024,780);
	
	ArrayList<GameChar> objectVector;
	Player player;
	GameChar object_2;
	GameChar object_3;
	GameChar object_4;

	float test = 0;

	public static void main(String[] args)
	{
		MainGame game = new MainGame();

		game.render = new Renderer("Asteroids");
		game.render.mainGame = game;

		game.render.CreateWindow(new Vector2(1024,780),controls);// Create a new Frame object and returns its reference
	}

	public void init(GL2 gl)
	{
		player = new Player();
		player.objectRenderer.shape= Shape.Square;
		player.objectRenderer.SetTexture(gl, "./resources/textures/KFC.png");
		render.renderVector.add(player.objectRenderer);
	
		object_2 = new GameChar();
		object_2.objectRenderer.shape = Shape.Square;
		render.renderVector.add(object_2.objectRenderer);

		player.transform.size = new Vector2(6,6);
		object_2.transform.size = new Vector2(6,6);
		player.rigidBody.frictionCoefficient = 0.05f;
		

		object_3 = new GameChar();
		object_3.objectRenderer.shape = Shape.Square;
		render.renderVector.add(object_3.objectRenderer);
		object_3.objectRenderer.SetTexture(gl, "./resources/textures/asteroid.png");
		
		object_3.transform.position = new Vector2(30,10);
		object_3.transform.size = new Vector2(10,10);
		object_3.rigidBody.PushTorque(0.5f, RigidBody.ForceMode.Force );
		object_3.rigidBody.PushForce(new Vector2(15, 100), ForceMode.Force);
		
		
	}

	public void Update(GL2 gl)
	{
		//Update every frame
		debug.gl = gl;

		// Put Game Logic here
		
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}

		//Update object_1 transform, physics, rendering etc...
		
		player.Update();
		object_2.Update();
		object_3.Update();
		
		if(player.rigidBody.isColliding(player, object_2)){
			  
		}
		player.rigidBody.isColliding(player, object_3);
	}

	public void ObjectDemo()
	{
		test += 0.01f;

		if(test < 2){
			//Draw object 1 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

			player.transform.rotation   = (float) Math.cos(test);
			player.transform.position.x = 100f * (float) Math.cos(test);
			player.transform.position.y = 100f * (float) Math.sin(test);

			//Draw object 2 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

			object_2.transform.rotation   = (float) Math.cos(test);
			object_2.transform.position.x = -140f * (float) Math.sin(test);
			object_2.transform.position.y = -140f * (float) Math.cos(test);
		}

		// debug Rays will be USEFUL(!) to show vectors to our puny human eyes.
		debug.DrawRay(Vector2.zero(), player.transform.position,Color.Green);
		debug.DrawRay(Vector2.zero(), object_2.transform.position,Color.Blue);

		// Draw the line between object1 and object2
		debug.DrawRay(player.transform.position, object_2.transform.position,Color.Red);
	}

}
