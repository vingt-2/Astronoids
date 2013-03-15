package Maths;

public class Vector3 
{
		public float x;
		public float y;
		public float z;

		
		public Vector3(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Vector3(Vector2 vector, float lastEntrie)
		{
			this.x = vector.x;
			this.y = vector.y;
			this.z = lastEntrie;
		}
		
		
		public float GetLength()
		{
			return (float)Math.sqrt((this.x*this.x)+(this.y*this.y) + (this.z*this.z));
		}

		public void Rotate(double argument)
		{
			float a=x;
			float b=y;
			this.x= (float) (a*Math.cos(argument) + b*Math.sin(argument));
			this.y= (float) ((-1)*a*Math.sin(argument) + b*Math.cos(argument));
		}
		
		public String toString(){
			return "("+x+","+y+","+z+", length: "+this.GetLength()+")";
		}
		
		public Vector3 Normalized()
		{
			float length = this.GetLength();
			float newX =  x / length;
			float newY =  y / length;
			float newZ =  z / length;
			
			return new Vector3(newX,newY,newZ);
		} 
	
		public boolean Normalize()
		{
			float length = this.GetLength();
			this.x = this.x/length;
			this.y = this.y/length;
			this.z = this.z/length;
			return true;
		}
		
		public static Vector3 Add(Vector3 vec1, Vector3 vec2)
		{
			return new Vector3 (vec1.x+vec2.x,vec1.y+vec2.y,vec1.z+vec2.z);
		}
		
		public static float Dot(Vector3 vec1, Vector3 vec2)
		{
			return vec1.x * vec2.x + vec1.y*vec2.y + vec1.z*vec2.z ;
		}
		
		public Vector2 GetVec2()
		{
			return new Vector2(this.x,this.y);
		}

}

