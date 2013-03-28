package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

public class Asteroid extends GameChar{

	int counter = 0;
	int RandomThrustX ;
	int RandomThrustY ;
	int RandomRotate  ;
	
	public Asteroid(Vector2 pos){
		super(pos);
		objectRenderer.shape = Shape.Square;
		objectRenderer.SetTexture("asteroid");		
	}
	
	public void Update(){
		
		super.Update();
		Random rand = new Random();
		
		RandomThrustX = rand.nextInt(30);
		RandomThrustY = rand.nextInt(30);
		RandomRotate = rand.nextInt(5);
		
	//	rigidBody.PushForce(new Vector2(RandomThrustX,RandomThrustY),ForceMode.Impulse);
		//rigidBody.PushTorque(RandomRotate, ForceMode.Impulse);
	}
	
	private Asteroid[] asteroidsArray;
	



}
