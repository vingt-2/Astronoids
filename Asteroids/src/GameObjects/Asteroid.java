package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

/**
 * Asteroid specific GameChar, with isbroken boolean
 * to check if the Asteroid has been destroyed
 * as well as the Asteroid texture
 *  
 * @author Damien Doucet-Girard
 *
 */
public class Asteroid extends GameChar
{

	public boolean isBroken = false;
	public int lives = 1;
	
	
	
	public Asteroid(Vector2 pos){
		super(pos);
		objectRenderer.shape = Shape.Square;
		objectRenderer.SetTexture("asteroid");	
		
	}
	
	public void Update(){
		
		super.Update();
			
	}
	

	
	

}
