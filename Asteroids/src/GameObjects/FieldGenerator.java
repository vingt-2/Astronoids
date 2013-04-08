package GameObjects;

import java.util.ArrayList;
import java.util.Random;

import Game.GameLogic;
import GameComponents.Transform;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;
import GameObjects.Asteroid;

public class FieldGenerator extends GameObject {
	
	public Transform transform;
	public static int number;
	public ArrayList<Asteroid> astrdField;
	int randPosX;
	int randPosY;
	int randSign;
	
	
	public FieldGenerator(int nbAsteroids, int speed){
		this.transform = transform;
		astrdField = new ArrayList<Asteroid>() ;
		number = nbAsteroids;
	}

	public void Update(){
		Random rand = new Random();

		
		
		for(int i = 0; i < number; i++){
			
			if(astrdField.size() <= i){
				if(GameLogic.breakChecker){					
				
					astrdField.add(new Asteroid(GameLogic.brokenAsteroid));
					astrdField.get(i).rigidBody.PushForce(new Vector2(rand.nextInt(400)-200,rand.nextInt(400)-200),ForceMode.Force);
					astrdField.get(i).rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
					astrdField.get(i).transform.size=(new Vector2( GameLogic.brokenSize, GameLogic.brokenSize ));
					
				}
				else{
				randPosX = rand.nextInt(1500)-750;
				randPosY = rand.nextInt(1000)-500;	
				if(rand.nextBoolean()) {
					randSign=1;
				}else{ 
					randSign=-1;
				}
				if (randPosX<150 && randPosX>-190) randPosX = 200+rand.nextInt(100)*randSign;
				
				if (randPosY<150 && randPosY>-190) randPosX = 200+rand.nextInt(100)*randSign;
			
				astrdField.add(new Asteroid(new Vector2((float) randPosX, (float) randPosY)));
				astrdField.get(i).rigidBody.PushForce(new Vector2(rand.nextInt(60)-30,rand.nextInt(60)-30),ForceMode.Force);
				astrdField.get(i).rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
				int randSize =rand.nextInt(12)%4+3;
				astrdField.get(i).transform.size=(new Vector2( randSize, randSize));
				}
				
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

	public void addToField(Asteroid asteroid){
		
		astrdField.add(asteroid);
		
	}
	
}
