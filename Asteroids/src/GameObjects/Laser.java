package GameObjects;

import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import GameComponents.Transform;
import Helpers.Color;
import Maths.Vector2;

/** 
 * Main laser class, describes behavior and properties of an individual bullet 
 *  
 * @author Damien Doucet-Girard
 *
 */


public class Laser extends GameChar {
	
	/**
	 * Declaring and initializing booleans and other various class variables
	 * Notable properties are laser lifeTime, and hasCollided boolean to 
	 * describe behavior upon collision
	 */

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
			
			//sets original bullet position to current object the laser belongs to
			rigidBody.SetPosition(shooterTrans.LocalPositionToWorld(new Vector2(0,10)));
			counter ++;
			}
		
		// describes laser physical properties and applies a force 
		// in the direction in whichever the object is facong
			rigidBody.frictionCoefficient = 0.0f;
			rigidBody.mass = 0.001f;
			rigidBody.acceleration = new Vector2(0,0);
			rigidBody.PushForce(new Vector2(-10*(float)Math.cos((shooterTrans.rotation+(direction))),
					10*(float)Math.sin((shooterTrans.rotation+(direction)))), ForceMode.Impulse);
			
			
			if(hasCollided){
				Delete();
			}
		
		}
		
		watchmen =System.currentTimeMillis();
		// Laser Stuff
		
		
	}
	
	//returns true if laser has reached the end of its lifeTime
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
