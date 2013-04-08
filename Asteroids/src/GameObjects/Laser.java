package GameObjects;

import java.awt.event.KeyEvent;

import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;



public class Laser extends GameChar {

	int counter = 0;
	long creationTime;
	long lifeTime = 700;
	public float deltaTime = MainGame.render.deltaTime;
	public long watchmen;
	
	public Laser(Vector2 position){
		
		super(position);
		creationTime = System.currentTimeMillis();
		
	}
	
	public void Update()
	{
		if(System.currentTimeMillis()-watchmen>deltaTime){
		super.Update();
		if (counter == 0)
			{	
			
			rigidBody.SetPosition(MainGame.player.transform.LocalPositionToWorld(new Vector2(0,0)));
			counter ++;
			}
		
			rigidBody.frictionCoefficient = 0.0f;
			rigidBody.mass = 0.001f;
			rigidBody.acceleration = new Vector2(0,0);
			rigidBody.PushForce(new Vector2(-(float)Math.cos((MainGame.player.transform.rotation+(Math.PI/2))),
					(float)Math.sin((MainGame.player.transform.rotation+(Math.PI/2)))), ForceMode.Impulse);
			
		if (TimeToDie()) Delete();
		
		}
		
		watchmen =System.currentTimeMillis();
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
