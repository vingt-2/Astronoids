package GameObjects;

import Maths.Vector2;

public class LifeDisplay extends GameChar {
	
	

	public LifeDisplay(Vector2 pos){
		super(pos);
		
		objectRenderer.SetTexture("rocket_ship");		
	}
	
	public void Update(){
		
		super.Update();
		
	}

}
