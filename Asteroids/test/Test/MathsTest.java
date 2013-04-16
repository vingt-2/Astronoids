package Test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import Game.MainGame;
import Maths.*;

public class MathsTest 
{
	
	@Test
	public void TestMatrix2()
	{
		Matrix2 identityMat = new Matrix2(new Vector2(1f,0f),new Vector2(0,1));
		
		Random rand = new Random();
		
		float entrie = rand.nextFloat();
		
		Vector2 vec1 = new Vector2(entrie,entrie);
		
		assertEquals(entrie,Matrix2.Multiply(identityMat, vec1).x,0.0001f);
		assertEquals(entrie,Matrix2.Multiply(identityMat, vec1).y,0.0001f);
	
	}
	
	@Test
	public void TestMatrix3()
	{
		
		Random rand = new Random();
		
		float entrie = rand.nextFloat();
		
		float randX = rand.nextFloat();
		float randY = rand.nextFloat();
		float randZ = rand.nextFloat();
		
		Vector3 vec1 = new Vector3(entrie,entrie,entrie);
		
		Matrix3 identityMat = new Matrix3(new Vector3(randX,0f,0f),new Vector3(0,randY,0),new Vector3(0,0,randZ));
		
		assertEquals(randX*entrie,Matrix3.Multiply(identityMat, vec1).x,0.0001f);
		assertEquals(randY*entrie,Matrix3.Multiply(identityMat, vec1).y,0.0001f);
		assertEquals(randZ*entrie,Matrix3.Multiply(identityMat, vec1).z,0.0001f);
	
	}
	

}
