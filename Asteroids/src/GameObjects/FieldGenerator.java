package GameObjects;

import java.util.Random;

import GameComponents.Transform;
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
				astrdField[i] = new Asteroid(new Vector2((float) rand.nextInt(1000)-500, (float) rand.nextInt(600)-300));
			}
			else{
				astrdField[i].Update();
			}
		}
	}
}
