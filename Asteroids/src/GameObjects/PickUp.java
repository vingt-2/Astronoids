package GameObjects;

import java.util.Random;


import GameComponents.ObjectRenderer.Shape;
import Maths.Vector2;

public abstract class PickUp extends GameChar {
	
	
	public PickUp(Vector2 pos){
		
		super(pos);
		
		objectRenderer.shape = Shape.Square;
				
	}
	
	public void Update(){
		
		super.Update();
		transform.size = (new Vector2 (2.5f,2.5f));
		objectRenderer.opacity= (float)(Math.cos(System.currentTimeMillis()/100)+3.5)/4;
	}
	
	public abstract void OnPickUp();
	
}
