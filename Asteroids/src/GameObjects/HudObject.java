package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer.Shape;
import Maths.Vector2;

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
				
	
		HUD.changingStage = true;
		if(TimeToDie()){
			if(!permanent)
				Delete();
				HUD.changingStage = false;
						
		}
	
	}
	
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
