package Game;

import java.util.ArrayList;
import javax.media.opengl.GL2;

import GameObjects.GameChar;
import Helpers.Color;
import Helpers.Debug;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	Renderer render;
	ArrayList<GameChar> objectVector;
	GameChar player;
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
		player = new GameChar();
		Vector2[] vert = { new Vector2(-0.5f,-0.5f), new Vector2(0.5f,-0.5f),
			new Vector2(0.5f,0.5f), new Vector2(-0.5f,0.5f) };
		player.objectRenderer.objectVertices = vert;
		render.renderVector.add(player.objectRenderer);
		player.transform.size= new Vector2(0.2f);
	}
	
	public void Update(GL2 gl)
	{
		//Update every frame
		debug.gl = gl;
		
		
		// PUT GamePlay here !
		// Exemple of basic control over objects TEST :)
		test += 0.01f;
		player.transform.rotation 	= (float) Math.cos(test);
		player.transform.position.x = 0.5f * (float) Math.cos(test);
		player.transform.position.y = 0.5f * (float) Math.sin(test);
		debug.DrawRay(Vector2.zero, player.transform.position,Color.Green);
		
		//Update player transform, physics, rendering etc...
		player.Update();
	}
}
