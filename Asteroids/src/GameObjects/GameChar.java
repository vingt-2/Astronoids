/**
 * GameChar:
 * 		A special type of GameObject that holds:
 * 		 - a Transform: To keep track of space coordinates matters.
 * 		 - a RigidBody: To keep track of physic state matters.
 * 		 - an ObjectRenderer: To keep track of "Drawing to screen" matters
 * 
 */

package GameObjects;

import Game.MainGame;
import GameComponents.ObjectRenderer;
import GameComponents.RigidBody;
import GameComponents.Transform;
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
	
	public GameChar(Vector2 position)
	{
		transform 		= new Transform(position);
		objectRenderer 	= new ObjectRenderer(this);
		rigidBody 		= new RigidBody(this);
	}
	
	/**
	 * Update all the GameChar stuff related stuff.
	 * 
	 */
	public void Update()
	{
		// Makes sure we're not about trying to use smthign about to be garbage collected
		if(!isDeleted)
		{
			//Update Components
			transform.UpdateTransform();
			rigidBody.Update();
		
			// Do shit
			WrapPositionAroundScreen();
		}
	}
	
	/**
	 * 
	 * A special function to Wrap around the screen.
	 * 
	 */
	
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
	
	/**
	 * Assigns null pointer to all components to help Java's Garbage collector do it's stuff
	 */
	
	@Override
	public void Delete()
	{
		isDeleted		= true;
		
		MainGame.render.renderVector.remove(objectRenderer);
		transform 		= null;
		objectRenderer 	= null;
		rigidBody 		= null;
	}
	
}
