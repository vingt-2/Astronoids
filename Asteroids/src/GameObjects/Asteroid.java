package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

public class Asteroid extends GameChar{

	public Asteroid(Vector2 pos){
		super(pos);
		objectRenderer.shape = Shape.Square;
		objectRenderer.SetTexture("asteroid");		
	}
	
	public void Update(){
		super.Update();
		Random rand = new Random();
		//rigidBody.SetPosition(new Vector2(rand.nextInt(3000), rand.nextInt(3000)));
		rigidBody.PushForce(Vector2.Scale(1000, new Vector2(1,1)),ForceMode.Impulse);
	}
	
	private Asteroid[] asteroidsArray;
	



}
