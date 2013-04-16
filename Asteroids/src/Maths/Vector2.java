/**
 * @author Vincent Petrella
 * Vector2:
 * 		A Vector2 Class that implements Vector 2D stuff
 * 		Methods signatures stand also for Vector3
 *
 */

package Maths;

public class Vector2 
{
		public float x;
		public float y;

		
		public Vector2(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Vector2(float entries)
		{
			x=entries;
			y=entries;
		}
		
		public Vector2(Vector2 vector)
		{
			this.x = vector.x;
			this.y = vector.y;
		}
		
		public float GetLength()
		{
			return (float)Math.sqrt((this.x*this.x)+(this.y*this.y));
		}
		
		public String toString(){
			return "("+x+","+y+",length: "+this.GetLength()+")";
		}
		
		/**
		 * Normalizes the vector.
		 * 
		 * @return normalizedVector
		 */
		
		public Vector2 Normalized()
		{
			float length = (float) Math.sqrt(this.x * this.x + this.y * this.y);
			float newX =  x / length;
			float newY =  y / length;
			
			return new Vector2(newX,newY);
		}
		
		/**
		 * Scales a vector by a "scalar".
		 * Returns a different instance (i.e a new object).
		 * 
		 * @param scalar
		 * @returnS scaledVector
		 */
		
		public Vector2 Scaled(float scalar)
		{
			return new Vector2(scalar * x, scalar * y);
		}
	
		/**
		 * 
		 * Normalizes the vector.
		 * Destroys the current one !
		 * 
		 */
		
		public void Normalize()
		{
			float length = this.GetLength();
			this.x = this.x/length;
			this.y = this.y/length;
		}
		
		/**
		 * Negates a vector.
		 * Returns a different instance (i.e a new object).
		 * 
		 * @return negatedVector
		 */
		
		public Vector2 negate()
		{
			return new Vector2(-x,-y);
		}
		
		
		/**
		 * 
		 * YOU KNOW WHAT IT DOES COME ON !
		 * 
		 * @param vec1
		 * @param vec2
		 * @return vec1+vec2
		 */
		public static Vector2 Add(Vector2 vec1, Vector2 vec2)
		{
			return new Vector2(vec1.x+vec2.x,vec1.y+vec2.y);
		}
		
		/**
		 * 
		 * Returns the dot product of "vec1"."vec2"
		 * 
		 * @param vec1
		 * @param vec2
		 * @return vec1.vec2
		 */
		
		public static float Dot(Vector2 vec1, Vector2 vec2)
		{
			return (vec1.x * vec2.x + vec1.y*vec2.y);
		}
		
		public static Vector2 Scale(float scalar,Vector2 vec1)
		{
			return new Vector2(scalar * vec1.x, scalar * vec1.y);
		}
		
		public static Vector2 zero() 
		{
			return new Vector2(0);
		}

}

