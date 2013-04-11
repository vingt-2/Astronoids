package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

public class Asteroid extends GameChar
{

	public boolean isBroken = false;
	
	public Asteroid(Vector2 pos){
		super(pos);
		objectRenderer.shape = Shape.Square;
		objectRenderer.SetTexture("asteroid");		
	}
	
	public void Update(){
		
		super.Update();
		Random rand = new Random();
				
	//	rigidBody.PushForce(new Vector2(RandomThrustX,RandomThrustY),ForceMode.Impulse);
		//rigidBody.PushTorque(RandomRotate, ForceMode.Impulse);
		
		//if(terminator) Delete();
	}
	

	
	

}
