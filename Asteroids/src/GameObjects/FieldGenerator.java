package GameObjects;

import java.util.ArrayList;
import java.util.Random;

import GameComponents.Transform;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;
import GameObjects.Asteroid;

public class FieldGenerator extends GameObject {
	
	public Transform transform;
	public static int number;
	public ArrayList<Asteroid> astrdField;
	
	public FieldGenerator(int nbAsteroids, int speed){
		this.transform = transform;
		astrdField = new ArrayList<Asteroid>() ;
		number = nbAsteroids;
	}

	public void Update(){
		Random rand = new Random();
		
		for(int i = 0; i < number; i++){
			
			if(astrdField.size() <= i){
				astrdField.add(new Asteroid(new Vector2((float) rand.nextInt(1500)-750, (float) rand.nextInt(600)-300)));
				astrdField.get(i).rigidBody.PushForce(new Vector2(rand.nextInt(60)-30,rand.nextInt(60)-30),ForceMode.Force);
				astrdField.get(i).rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
				int randSize =rand.nextInt(10)%2+4;
				astrdField.get(i).transform.size=(new Vector2( randSize, randSize));
				
			}
			else{
				if(!(astrdField.size()<= i)){
				if (astrdField.get(i).terminator) {
					
					astrdField.get(i).Delete();
					//astrdField[i] = astrdField[i+1];
				}
				else{
					astrdField.get(i).Update();
				}
			}
			}
		}
	}
	
	public ArrayList<Asteroid> GetAsteroidArray(){
		
		return astrdField;
	}
	
	public static void Die( Asteroid[] field, int i){
		
		field[i].Delete();
	}

	
}
