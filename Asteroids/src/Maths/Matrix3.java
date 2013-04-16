package Maths;

/**
 * Matrix3:
 * 		A Matrix3 Class that implements Matrix3x3 stuff
 * 		Method names are pretty straightforward I think.
 *
 */

public class Matrix3 
{
	public float[][] array;
	
	public Matrix3(float diagonalEntries)
	{
		float[][] array = {{0,0,0},{0,0,0},{0,0,0}};
		array[0][0] = diagonalEntries;
		array[1][1] = diagonalEntries;
		array[2][2] = diagonalEntries;
		this.array = array;
	}
	
	public Matrix3(float[][] array)
	{
		this.array = array;
	}
	
	public Matrix3(Vector3 diagonalEntries)
	{
		float[][] array = {{0,0,0},{0,0,0},{0,0,0}};
		array[0][0] = diagonalEntries.x;
		array[1][1] = diagonalEntries.y;
		array[2][2] = diagonalEntries.z;
		this.array = array;
	}
	
	public Matrix3(Vector3 col1, Vector3 col2, Vector3 col3 )
	{
		float[][]array = {{col1.x,col1.y,col1.z},{col2.x,col2.y,col2.z},{col3.x,col3.y,col3.z}};
		this.array=array;
	}
	
	public Vector3[] ToVectors()
	{
		Vector3[] columns = { new Vector3(array[0][0],array[0][1],array[0][2]), 
								new Vector3(array[1][0],array[1][1],array[1][2]),
									new Vector3(array[2][0],array[2][1],array[2][2]) 
							};
		return columns;	
	}
	
	public static Matrix3 Add(Matrix3 mat1, Matrix3 mat2)
	{
		Vector3[] cols1 = mat1.ToVectors();
		Vector3[] cols2 = mat2.ToVectors();
		return new Matrix3(Vector3.Add(cols1[0], cols2[0]), Vector3.Add(cols1[1],cols2[1]), Vector3.Add(cols1[2],cols2[2])); 
	}
	
	public static Matrix3 Multiply(Matrix3 rotationMatrix, Matrix3 translationMatrix)
	{
		// Individual computations for performance;
		float a = rotationMatrix.array[0][0]*translationMatrix.array[0][0] + rotationMatrix.array[1][0] * translationMatrix.array[0][1] + rotationMatrix.array[0][2]*translationMatrix.array[0][2];
		float b = rotationMatrix.array[0][1]*translationMatrix.array[0][0] + rotationMatrix.array[1][1] * translationMatrix.array[0][1] + rotationMatrix.array[1][2]*translationMatrix.array[0][2];
		float c = rotationMatrix.array[0][2]*translationMatrix.array[0][0] + rotationMatrix.array[1][2] * translationMatrix.array[0][1] + rotationMatrix.array[2][2]*translationMatrix.array[0][2];
		float d = rotationMatrix.array[0][0]*translationMatrix.array[1][0] + rotationMatrix.array[1][0] * translationMatrix.array[1][1] + rotationMatrix.array[0][2]*translationMatrix.array[1][2];
		float e = rotationMatrix.array[0][1]*translationMatrix.array[1][0] + rotationMatrix.array[1][1] * translationMatrix.array[1][1] + rotationMatrix.array[1][2]*translationMatrix.array[1][2];
		float f = rotationMatrix.array[0][2]*translationMatrix.array[1][0] + rotationMatrix.array[1][2] * translationMatrix.array[1][1] + rotationMatrix.array[2][2]*translationMatrix.array[1][2];
		float g = rotationMatrix.array[0][0]*translationMatrix.array[2][0] + rotationMatrix.array[1][0] * translationMatrix.array[2][1] + rotationMatrix.array[0][2]*translationMatrix.array[2][2];
		float h = rotationMatrix.array[0][1]*translationMatrix.array[2][0] + rotationMatrix.array[1][1] * translationMatrix.array[2][1] + rotationMatrix.array[1][2]*translationMatrix.array[2][2];
		float i = rotationMatrix.array[0][2]*translationMatrix.array[2][0] + rotationMatrix.array[1][2] * translationMatrix.array[2][1] + rotationMatrix.array[2][2]*translationMatrix.array[2][2];
		
		return new Matrix3(new float[][]{ {a,b,c}, {d,e,f}, {g,h,i} });
	}
	
	public String toString()
	{
		String outputString = "{ ";
		for(int i = 0; i<3; i++)
		{
			outputString+="{ ";
			for(int j = 0; j<3 ; j++)
			{
				outputString+= array[i][j] + ",";
			}
			outputString+= "}, ";
		}
		outputString += "}";
		return outputString;
	}
	
	public static Vector3 Multiply(Matrix3 matrix, Vector3 vector)
	{
		float x = matrix.array[0][0] * vector.x + matrix.array[1][0] * vector.y + matrix.array[2][0] * vector.z; 
		float y = matrix.array[0][1] * vector.x + matrix.array[1][1] * vector.y + matrix.array[2][1] * vector.z;
		float z = matrix.array[0][2] * vector.x + matrix.array[1][2] * vector.y + matrix.array[2][2] * vector.z;
		
		return new Vector3(x,y,z);
	}
}
