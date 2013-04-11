package GameObjects;

import GameComponents.ObjectRenderer.Shape;
import Maths.Vector2;

public class PowerUp extends GameChar 
{
	
	int counter = 0;
	public static boolean terminator = false;
	public String type;
	
	public PowerUp(Vector2 pos, String powerUpType){
		
		super(pos);
		transform.size = (new Vector2(3,3));
		objectRenderer.shape = Shape.Square;
		
		type = powerUpType;
		
		if(powerUpType.equalsIgnoreCase("life"))
		{
			objectRenderer.SetTexture("Life");		
		}
		else if(powerUpType.equalsIgnoreCase("shield"))
		{
			objectRenderer.SetTexture("shielded_ship");
		}
		
	}
	
	public void Update(){
		
		super.Update();
		
		
	}
	
	
}
