package GameComponents;

import java.util.ArrayList;

import GameObjects.GameChar;
import Maths.*;

import Renderer.Renderer;

public class RigidBody 
{
	public static float deltaTime = 1/Renderer.RERFRESH_RATE;
	
	GameChar parent;
	
	protected ArrayList<Vector2> forces;

	
	public RigidBody(GameChar parent)
	{
		this.parent = parent;
	}
	
}
