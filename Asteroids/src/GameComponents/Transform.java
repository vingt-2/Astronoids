package GameComponents;

import Maths.*;
/**
 * 
 *	Holds the world representation of an object.
 * 		
 * 	<p>	An object is then placed in the world according to it's Position, Size, and Rotation,
 * 		which are the main attributes of a Transform.
 * 		These 3 attribute are concatenated into 1 matrix, the transformMatrix
 *  </p>
 * @author Vincent Petrella	
 */
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
	
	public Transform(Vector2 position)
	{
		size = new Vector2(1,1);
		this.position = new Vector2(position.x,position.y);
		rotation = 0;
		UpdateTransform();
	}
	
	/**
	 * Updates the Transform matrix according to current Position/Scale/Rotation
	 * 
	 */
	public void UpdateTransform()
	{
		Matrix3 translationMatrix = new Matrix3(0);
		float[] thirdEntrie = { position.x, position.y, 1};
		translationMatrix.array[2] = thirdEntrie;
		
		Matrix3 rotationMatrix = 
				new Matrix3(
						new float[][]
						{
							{(float)Math.cos(rotation)	, (float) (-1 * Math.sin(rotation)),0f	},
							{(float)Math.sin(rotation)	, (float) Math.cos(rotation),		0f	},
							{	0f						, 0f,								1f	}
						});
		
		Matrix3 sizeMatrix =
				new Matrix3
					(
						new Vector3(size.x,	0,		0),
						new Vector3(0,		size.y,	0),
						new Vector3(0,		0,		1)
					);
		
		Matrix3 rotationTsize = Matrix3.Multiply(rotationMatrix, sizeMatrix);
		transformMatrix = Matrix3.Add(translationMatrix,rotationTsize);
	}
	
	/**
	 * Transform the direction vector "vector" from Local space to World Space
	 * (Discard Translations)
	 * 
	 * @param vector
	 * @return
	 */
	
	public Vector2 LocalDirectionToWorld(Vector2 vector)
	{
		// Homogeneous coordinate to 0 so that it's left untouched by the translation (Direction)
		Vector3 homogeneous = new Vector3(vector,0f);
		return Matrix3.Multiply(transformMatrix,homogeneous).GetVec2();
	}
	
	/**
	 * Transform the Position vector "vector" from Local space to World Space
	 * (Keeps track of translations)
	 * 
	 * @param vector
	 * @return
	 */
	
	public Vector2 LocalPositionToWorld(Vector2 vector)
	{
		// Homogeneous coordinate to 1 so that it's affected by the translation (Position)
		Vector3 homogeneous = new Vector3(vector,1f);
		return Matrix3.Multiply(transformMatrix,homogeneous).GetVec2();
	}
	
}
