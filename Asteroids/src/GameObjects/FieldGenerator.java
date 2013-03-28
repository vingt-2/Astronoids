package GameObjects;

import java.util.Random;

import GameComponents.Transform;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

public class FieldGenerator extends GameObject {
	
	public Transform transform;
	
	public static Asteroid[] astrdField;
	
	public FieldGenerator(int nbAsteroids, int speed){
		this.transform = transform;
		astrdField = new Asteroid[nbAsteroids];
	}

	public static void Update(){
		Random rand = new Random();
		
		for(int i = 0; i < astrdField.length; i++){
			if(astrdField[i] == null){
				astrdField[i] = new Asteroid(new Vector2((float) rand.nextInt(1500)-750, (float) rand.nextInt(600)-300));
				astrdField[i].rigidBody.PushForce(new Vector2(rand.nextInt(60)-30,rand.nextInt(60)-30),ForceMode.Force);
				astrdField[i].rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
				int randSize =rand.nextInt(10)%2+4;
				astrdField[i].transform.size=(new Vector2( randSize, randSize));
			}
			else{
				astrdField[i].Update();
			}
		}
	}
}
