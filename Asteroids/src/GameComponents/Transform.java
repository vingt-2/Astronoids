package GameComponents;

import Maths.*;

public class Transform 
{
	public Vector2 size;
	public Vector2 position;
	public float rotation;
	
	public Matrix3 transformMatrix;
	
	public Transform()
	{
		size = new Vector2(1,1);
		position = new Vector2(0,0);
		rotation = 0;
		UpdateTransform();
	}
	
	public void UpdateTransform()
	{
		Matrix3 translationMatrix = new Matrix3(0);
		float[] thirdEntrie ={ position.x, position.y, 1};
		translationMatrix.array[2] = thirdEntrie;
		
		Matrix3 rotationMatrix = 
				new Matrix3(
						new float[][]{
						{(float)Math.cos(rotation), (float) (-1 * Math.sin(rotation)),0f},
						{(float)Math.sin(rotation), (float) Math.cos(rotation),0f},
						{0f,0f,1f}
						});
		
		Matrix3 sizeMatrix = new Matrix3(
				new Vector3(size.x,0,0),
				new Vector3(0,size.y,0),
				new Vector3(0,0,1)
				);
		Matrix3 rotationTsize = Matrix3.Multiply(rotationMatrix, sizeMatrix);
		transformMatrix = Matrix3.Add(translationMatrix,rotationTsize);
	}
	
}
