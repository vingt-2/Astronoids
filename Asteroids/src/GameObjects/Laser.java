package GameObjects;

import java.awt.event.KeyEvent;

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

	long lifeTime = 400;
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
			rigidBody.PushForce(new Vector2(-5*(float)Math.cos((shooterTrans.rotation+(direction))),
					5*(float)Math.sin((shooterTrans.rotation+(direction)))), ForceMode.Impulse);
			
		if (TimeToDie()) Delete();
		
		}
		
		watchmen =System.currentTimeMillis();
		// Laser Stuff
		
		
//		if(rigidBody.isColliding(MainGame.objectVector.get(0),MainGame.objectVector.get(1))){
//			
//			Delete();
//			
//		}
//		
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
