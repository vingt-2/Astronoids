package GameObjects;

import GameComponents.ObjectRenderer;
import GameComponents.RigidBody;
import GameComponents.Transform;

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
		transform.UpdateTransform();
		rigidBody.UpdateState();
	}
	
}
