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
	long lifeTime = 800;
	Transform shooterTrans;
	
	public Laser(Vector2 position, Transform shooterTrans){
		
		super(position);
		creationTime = System.currentTimeMillis();
		this.shooterTrans = shooterTrans;
	}
	
	public void Update()
	{
		super.Update();
		if (counter == 0)
			{	
			
			rigidBody.SetPosition(shooterTrans.LocalPositionToWorld(new Vector2(0,10)));
			counter ++;
			}
		
			rigidBody.frictionCoefficient = 0.0f;
			rigidBody.mass = 0.001f;
			rigidBody.acceleration = new Vector2(0,0);
			rigidBody.PushForce(new Vector2(-(float)Math.cos((shooterTrans.rotation+(Math.PI/2))),
					(float)Math.sin((shooterTrans.rotation+(Math.PI/2)))), ForceMode.Impulse);
			
		if (TimeToDie()) Delete();
		
		// Laser Stuff
		
		
//		if(rigidBody.isColliding(MainGame.objectVector.get(0),MainGame.objectVector.get(1))){
//			
//			Delete();
//			
//		}
		
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
