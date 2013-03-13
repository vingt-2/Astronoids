package Game;

import Renderer.*;

public class MainGame 
{
	public static void main(String[] args)
	{
		Renderer render = new Renderer("Asteroids");
		render.CreateWindow();
		while(true);
	}
}
