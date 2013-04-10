package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer.Shape;
import Maths.Vector2;

public class PowerUp extends GameChar {
	
	int counter = 0;
	public static boolean terminator = false;
	public String type;
	
	public PowerUp(Vector2 pos, String powerUpType){
		
		super(pos);
		transform.size = (new Vector2(3,3));
		objectRenderer.shape = Shape.Square;
		
		type = powerUpType;
		
		switch(powerUpType){
		
		case("Life"):
			objectRenderer.SetTexture("Life");		
			break;
		case("Shield"):
			objectRenderer.SetTexture("shielded_ship");
			break;
//		case("spread"):
//			objectRenderer.SetTexture("shotgun");		
//				
		
		}
		
	}
	
	public void Update(){
		
		super.Update();
		
		
	}
	
	
}
