package Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;

import jogamp.graph.math.MathFloat;

import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import GameObjects.GameChar;
import Helpers.Color;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	Renderer render;
	Controls controls;
	Debug debug;

	ArrayList<GameChar> objectVector;
	GameChar object_1, object_2;

	float test = 0;

	public static void main(String[] args)
	{
		MainGame game = new MainGame();

		game.render = new Renderer("Asteroids");
		game.render.mainGame = game;

		game.controls = new Controls();

		game.debug = new Debug();

		game.render.CreateWindow(new Vector2(1024,780),game.controls);// Create a new Frame object and returns its reference
	}

	public void init(GL2 gl)
	{
		object_1 = new GameChar();
		object_1.objectRenderer.shape= Shape.Square;
		object_1.objectRenderer.SetTexture(gl, "./resources/textures/rocket_ship.png");
		render.renderVector.add(object_1.objectRenderer);

		object_2 = new GameChar();
		object_2.objectRenderer.shape = Shape.Square;
		render.renderVector.add(object_2.objectRenderer);

		object_1.transform.size = new Vector2(3,3);


	}

	public void Update(GL2 gl)
	{
		//Update every frame
		debug.gl = gl;

		// PUT GamePlay here !

		//ObjectDemo();

		if(controls.isPressed(KeyEvent.VK_RIGHT))
		{
			object_1.rigidBody.PushTorque(10,ForceMode.Impulse);
		}
		if(controls.isPressed(KeyEvent.VK_LEFT))
		{
			object_1.rigidBody.PushTorque(-10,ForceMode.Impulse);
		}
		if(controls.isPressed(KeyEvent.VK_UP))
		{
			Vector2 objectFrontInWorldCoordinates = object_1.transform.LocalDirectionToWorld(new Vector2(0,1));
			object_1.rigidBody.PushForce(Vector2.Scale(1000, objectFrontInWorldCoordinates),ForceMode.Impulse);
		}
		
		if(controls.isPressed(KeyEvent.VK_SPACE))
		{
			Random ran = new Random();
			object_1.rigidBody.PushForce(new Vector2((ran.nextInt(20)-10)*1000,(ran.nextInt(20)-10)*1000),ForceMode.Impulse);
		}
		
		if(controls.isPressed(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}

		Vector2 objectFrontInWorldCoordinates = object_1.transform.LocalDirectionToWorld(new Vector2(0,1));
		debug.DrawLine(object_1.transform.position,objectFrontInWorldCoordinates,100,Color.Blue);

		//Update object_1 transform, physics, rendering etc...
		object_1.Update();
		object_2.Update();

	}

	public void ObjectDemo()
	{
		test += 0.01f;

		if(test < 2){
			//Draw object 1 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

			object_1.transform.rotation   = (float) Math.cos(test);
			object_1.transform.position.x = 100f * (float) Math.cos(test);
			object_1.transform.position.y = 100f * (float) Math.sin(test);

			//Draw object 2 on a circular pattern (x,y) = (a*cost,a*sint)

			// the position depends on t test which varies in time, thus MOVEMENT !

			object_2.transform.rotation   = (float) Math.cos(test);
			object_2.transform.position.x = -140f * (float) Math.sin(test);
			object_2.transform.position.y = -140f * (float) Math.cos(test);
		}

		// debug Rays will be USEFUL(!) to show vectors to our puny human eyes.
		debug.DrawRay(Vector2.zero(), object_1.transform.position,Color.Green);
		debug.DrawRay(Vector2.zero(), object_2.transform.position,Color.Blue);

		// Draw the line between object1 and object2
		debug.DrawRay(object_1.transform.position, object_2.transform.position,Color.Red);
	}

}
