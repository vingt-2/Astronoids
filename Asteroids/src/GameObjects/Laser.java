package GameObjects;

import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import GameComponents.Transform;
import Helpers.Color;
import Maths.Vector2;



public class Laser extends GameChar {

	int counter = 0;
	long creationTime;

	public float deltaTime = MainGame.render.deltaTime;
	public long watchmen;
	public boolean hasCollided = false;
	public long lifeTime = 400;
	Transform shooterTrans;
	float direction;

	
	public Laser(Vector2 position, Transform shooterTrans, float direction){
		
		super(position);
		creationTime = System.currentTimeMillis();
		this.shooterTrans = shooterTrans;
		this.direction = direction;
	}
	
	public void Update()
	{
		if(System.currentTimeMillis()-watchmen>deltaTime){
		super.Update();
		if (counter == 0)
			{	
			
			rigidBody.SetPosition(shooterTrans.LocalPositionToWorld(new Vector2(0,10)));
			counter ++;
			}
		
			rigidBody.frictionCoefficient = 0.0f;
			rigidBody.mass = 0.001f;
			rigidBody.acceleration = new Vector2(0,0);
			rigidBody.PushForce(new Vector2(-10*(float)Math.cos((shooterTrans.rotation+(direction))),
					10*(float)Math.sin((shooterTrans.rotation+(direction)))), ForceMode.Impulse);
			
			//if (TimeToDie()) Delete();
			if(hasCollided){
				Delete();
			}
		
		}
		
		watchmen =System.currentTimeMillis();
		// Laser Stuff
		
		
	}
	
	public boolean TimeToDie()
	{
		long currentTime = System.currentTimeMillis();

		if(currentTime - creationTime > lifeTime)
		{
			return true;
		}

		return false;
	}
	
	
	
}
