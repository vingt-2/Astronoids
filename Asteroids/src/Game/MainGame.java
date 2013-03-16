package Game;

import java.util.ArrayList;
import javax.media.opengl.GL2;

import GameComponents.RigidBody.ForceMode;
import GameObjects.GameChar;
import Helpers.Color;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	Renderer render;
	ArrayList<GameChar> objectVector;
	GameChar object_1, object_2;
	Debug debug;
	
	float test = 0;
	
	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		
		game.render = new Renderer("Asteroids");
		game.render.mainGame = game;
		
		game.debug = new Debug();
		
		game.render.CreateWindow(new Vector2(640,480));// Create a new Frame object and returns its reference
	}
	
	public void init()
	{
		Vector2[] vert = { new Vector2(-0.5f,-0.5f), new Vector2(0.5f,-0.5f),
				new Vector2(0.5f,0.5f), new Vector2(-0.5f,0.5f) };
		
		object_1 = new GameChar();
		object_1.objectRenderer.objectVertices = vert;
		render.renderVector.add(object_1.objectRenderer);
		object_1.transform.size= new Vector2(0.2f);
		
		object_2 = new GameChar();
		object_2.objectRenderer.objectVertices = vert;
		render.renderVector.add(object_2.objectRenderer);
		object_2.transform.size= new Vector2(0.2f);
	}
	
	public void Update(GL2 gl)
	{
		//Update every frame
		debug.gl = gl;
		
		object_1.rigidBody.DrawAcceleration(debug,Color.White);
		
		// PUT GamePlay here !

		ObjectDemo();
		
		//Update object_1 transform, physics, rendering etc...
		object_1.Update();
		object_2.Update();
		
		System.out.println("OBJECT_1 velocity: " + object_1.rigidBody.velocity);
		
	}
	
	public void ObjectDemo()
	{
		test += 0.01f;

		//Draw object 1 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

		object_1.transform.rotation   = (float) Math.cos(test);
		object_1.transform.position.x = 0.5f * (float) Math.cos(test);
		object_1.transform.position.y = 0.5f * (float) Math.sin(test);
		debug.DrawRay(Vector2.zero(), object_1.transform.position,Color.Green);

		//Draw object 2 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

		object_2.transform.rotation   = (float) Math.cos(test);
		object_2.transform.position.x = -0.7f * (float) Math.sin(test);
		object_2.transform.position.y = -0.7f * (float) Math.cos(test);

		// debug Rays will be USEFUL(!) to show vectors to our puny human eyes.
		debug.DrawRay(Vector2.zero(), object_2.transform.position,Color.Blue);

		// Draw the line between object1 and object2
		debug.DrawRay(object_1.transform.position, object_2.transform.position,Color.Red);
	}
	
}
