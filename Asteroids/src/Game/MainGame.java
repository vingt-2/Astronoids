package Game;

import java.awt.Frame;
import java.util.ArrayList;

import GameObjects.GameChar;
import GameObjects.GameObject;
import Maths.*;
import Renderer.*;

public class MainGame 
{
	Renderer render;
	ArrayList<GameChar> objectVector;
	GameChar player;
	
	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		game.render = new Renderer("Asteroids");
		game.render.mainGame = game;
		Frame frame = game.render.CreateWindow(new Vector2(640,480));
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
	
	public void Update()
	{
		player.transform.rotation += 0.01f;
		player.transform.position.x -= 0.01f;
		player.Update();
	}
}
