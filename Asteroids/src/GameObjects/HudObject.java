package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer.Shape;
import Maths.Vector2;

/**
 * Objects to be passed to the HUD and displayed.
 * Used specifically for displaying Stage numbers.
 *  
 * @author Damien Doucet-Girard
 *
 */
public class HudObject  extends GameChar{

	long creationTime;
	public boolean permanent = false;
	
	public HudObject(Vector2 pos){
		super(pos);
		objectRenderer.shape = Shape.Square;
		this.creationTime = System.currentTimeMillis();	
	}
	
	public void Update(){
		
		super.Update();
				
		//Checking if stage is over
		HUD.changingStage = true;
		
		//Deleting stage number display after a pre-determined time
		if(TimeToDie()){
			if(!permanent)
				Delete();
				HUD.changingStage = false;
						
		}
	
	}
	
	//Time to die set to 1000ms in this case. Same function used in Particles.java
	public boolean TimeToDie()
	{
		long currentTime = System.currentTimeMillis();

		if(currentTime - creationTime > 1000)
		{
			return true;
		}

		return false;
	}

}
