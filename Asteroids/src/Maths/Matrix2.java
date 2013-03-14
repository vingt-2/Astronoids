package Maths;

public class Matrix2 
{
	Vector2[] columns;
	
	public Matrix2(int diagonalEntries)
	{
		Vector2[] columns = {new Vector2(0), new Vector2(0)};
		columns[0].x = diagonalEntries;
		columns[1].y = diagonalEntries;
		this.columns = columns;
	}
	
	public Matrix2(Vector2 col1, Vector2 col2 )
	{
		Vector2[]columns = {col1,col2};
		this.columns=columns;
	}
	
	public static Matrix2 Multiply(Matrix2 mat1, Matrix2 mat2)
	{
		float x = mat1.columns[0].x*mat2.columns[1].x + mat1.columns[0].x * mat2.columns[0].y;
		float y = mat1.columns[0].y*mat2.columns[1].y + mat1.columns[0].x * mat2.columns[0].y;
		float z = mat1.columns[0].x*mat2.columns[1].x + mat1.columns[1].x * mat2.columns[1].y;
		float w = mat1.columns[0].y*mat2.columns[1].y + mat1.columns[1].x * mat2.columns[1].y;
		
		return new Matrix2(new Vector2(x,y),new Vector2(z,w));
	}
	
	public static Vector2 Multiply(Matrix2 matrix, Vector2 vector)
	{
		float x = matrix.columns[0].x * vector.x + matrix.columns[1].x * vector.y; 
		float y = matrix.columns[0].y * vector.x + matrix.columns[1].y * vector.y;
		
		return new Vector2(x,y);
	}
	
}
