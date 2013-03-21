package GameObjects;

import Game.MainGame;
import GameComponents.ObjectRenderer;
import GameComponents.RigidBody;
import GameComponents.Transform;
import Helpers.Color;
import Maths.Vector2;

public class GameChar extends GameObject 
{
	public Transform transform;
	public RigidBody rigidBody;
	public ObjectRenderer objectRenderer;
	
	public GameChar()
	{
		transform 		= new Transform();
		objectRenderer 	= new ObjectRenderer(this);
		rigidBody 		= new RigidBody(this);
	}
	
	public void Update()
	{
		//Update Components
		transform.UpdateTransform();
		rigidBody.Update();
		
		// Do shit
		WrapPositionAroundScreen();
		
		Vector2 charFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
		MainGame.debug.DrawLine(transform.position,charFrontInWorldCoordinates,100,Color.Blue);
	}
	
	private void WrapPositionAroundScreen()
	{
		
		if(transform.position.x > MainGame.Screen_Size.x/2)
		{
			rigidBody.SetPosition(new Vector2(-MainGame.Screen_Size.x/2,transform.position.y));
		}
		if(transform.position.x < -MainGame.Screen_Size.x/2)
		{
			rigidBody.SetPosition(new Vector2(MainGame.Screen_Size.x/2,transform.position.y));
		}
		if(transform.position.y > MainGame.Screen_Size.y/2)
		{
			rigidBody.SetPosition(new Vector2(transform.position.x,-MainGame.Screen_Size.y/2));
		}		
		if(transform.position.y < -MainGame.Screen_Size.y/2)
		{
			rigidBody.SetPosition(new Vector2(transform.position.x,MainGame.Screen_Size.y/2));
		}			
	}
	
}
