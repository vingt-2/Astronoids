package GameObjects;

import Maths.Vector2;

/** 
 * This class is a simply a gameChar with a the rocket ship texture pre-applied.
 * Used in HUD to display lives  
 * @author Damien Doucet-Girard
 *
 */

public class LifeDisplay extends GameChar {
	
	

	public LifeDisplay(Vector2 pos){
		super(pos);
		
		objectRenderer.SetTexture("rocket_ship");		
	}
	
	public void Update(){
		
		super.Update();
		
	}

}
