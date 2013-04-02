package Maths;

/**
 * Matrix2:
 * 		A Matrix2 Class that implements Matrix2x2 stuff
 *
 */


public class Matrix2 
{
	public float[][] array;
	
	public Matrix2(float diagonalEntries)
	{
		float[][] array = {{0,0},{0,0}};
		array[0][0] = diagonalEntries;
		array[1][1] = diagonalEntries;
		this.array = array;
	}
	
	public Matrix2(float[][] array)
	{
		this.array = array;
	}
	
	public Matrix2(Vector2 diagonalEntries)
	{
		float[][] array = {{0,0},{0,0}};
		array[0][0] = diagonalEntries.x;
		array[1][1] = diagonalEntries.y;
		this.array = array;
	}
	
	public Matrix2(Vector2 col1, Vector2 col2 )
	{
		float[][]array = {{col1.x,col1.y},{col2.x,col2.y}};
		this.array=array;
	}
	
	public Vector2[] ToVectors()
	{
		Vector2[] columns = { new Vector2(array[0][0],array[0][1]), new Vector2(array[1][0], array[1][1]) };
		return columns;
	}
	
	public static Matrix2 Add(Matrix2 mat1, Matrix2 mat2)
	{
		Vector2[] cols1 = mat1.ToVectors();
		Vector2[] cols2 = mat2.ToVectors();
		return new Matrix2(Vector2.Add(cols1[0],cols2[0]), Vector2.Add(cols1[1], cols2[1]));
	}
	
	public static Matrix2 Multiply(Matrix2 mat1, Matrix2 mat2)
	{
		float x = mat1.array[0][0]*mat2.array[1][0] + mat1.array[0][0] * mat2.array[0][1];
		float y = mat1.array[0][1]*mat2.array[1][1] + mat1.array[0][0] * mat2.array[0][1];
		float z = mat1.array[0][0]*mat2.array[1][0] + mat1.array[1][0] * mat2.array[1][1];
		float w = mat1.array[0][1]*mat2.array[1][1] + mat1.array[1][0] * mat2.array[1][1];
		
		return new Matrix2(new float[][]{ {x,y}, {z,w} });
	}
	
	public static Vector2 Multiply(Matrix2 matrix, Vector2 vector)
	{
		float x = matrix.array[0][0] * vector.x + matrix.array[1][0] * vector.y; 
		float y = matrix.array[0][1] * vector.x + matrix.array[1][1] * vector.y;
		
		return new Vector2(x,y);
	}
	

	
}
