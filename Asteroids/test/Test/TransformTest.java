package Test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import GameComponents.Transform;
import Maths.Matrix3;
import Maths.Vector2;

public class TransformTest 
{
	/**
	 * Test transform attributes using random numbers.
	 * 
	 */
	
	@Test 
	public void transformTest()
	{
		Transform transform = new Transform();
		
		Random rand = new Random();
		
		float position = rand.nextFloat();
		
		transform.rotation = (-(float)Math.PI/2);
		
		transform.UpdateTransform();
		
		Vector2 testVector = new Vector2(1,0);
		
		assertEquals(0f,transform.LocalDirectionToWorld(testVector).x,0.0001f);
		assertEquals(1f,transform.LocalDirectionToWorld(testVector).y,0.0001f);
		
		transform = new Transform();
		transform.position = new Vector2(position);
		transform.UpdateTransform();
		
		assertEquals(position+1,transform.LocalPositionToWorld(testVector).x,0.0001f);
		assertEquals(position,transform.LocalPositionToWorld(testVector).y,0.0001f);
		
		float size = rand.nextFloat();
		
		transform = new Transform();
		transform.size = new Vector2(size);
		transform.UpdateTransform();
		
		
		assertEquals(1*size,transform.LocalDirectionToWorld(testVector).x,0.0001f);
		assertEquals(0*size,transform.LocalDirectionToWorld(testVector).y,0.0001f);
		
	}

}
