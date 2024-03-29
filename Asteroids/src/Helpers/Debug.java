package Helpers;

/**
 * 		A debug Class to access some useful infos:
 * 
 * 		<p>
 * 		Drawing rays, vectors, shape, that kind of stuff.
 * 		</p>
 * @author Vincent Petrella
 */
import javax.media.opengl.GL2;

import Game.MainGame;
import GameComponents.RigidBody.CollisionShape;
import GameComponents.Transform;
import Maths.Matrix3;
import Maths.Vector2;
import Maths.Vector3;

public class Debug 
{
	public static GL2 gl;

	/**
	 * Draw Ray between to 2D vertices "origin","end", in Red
	 * 
	 * @param origin
	 * @param end
	 */
	public void DrawRay(Vector2 origin,Vector2 end)
	{
		GL2 gl = MainGame.render.externDrawable.getGL().getGL2();
		
		gl.glColor3f(1f,0,0);
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(end.x,end.y);
		gl.glEnd();
	}
	
	/**
	 * Draw Ray between to 2D vertices "origin","end", in "color"
	 * 
	 * @param origin
	 * @param end
	 * @param color
	 */
	
	public void DrawRay(Vector2 origin,Vector2 end,Color color)
	{
		GL2 gl = MainGame.render.externDrawable.getGL().getGL2();
		gl.glColor3f(color.r,color.g,color.b);
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(end.x,end.y);
		gl.glEnd();
	}
	
	/**
	 * Draw a Line from "origin" in the direction "direction" with length "length". In Red
	 * 
	 * @param origin
	 * @param direction
	 * @param length
	 */

	public void DrawLine(Vector2 origin,Vector2 direction,float length)
	{
		GL2 gl = MainGame.render.externDrawable.getGL().getGL2();
		
		gl.glColor3f(1f,0,0);;
		
		Vector2 normalDir = direction.Normalized();
		
		float a = origin.x + length*normalDir.x;
		float b = origin.y + length*normalDir.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}
	
	/**
	 * Draw a Line from "origin" in the direction "direction" with length "length". In "color"
	 * 
	 * @param origin
	 * @param direction
	 * @param length
	 * @param color
	 */
	
	public void DrawLine(Vector2 origin,Vector2 direction,float length,Color color)
	{
		GL2 gl = MainGame.render.externDrawable.getGL().getGL2();
		
		gl.glColor3f(color.r,color.g,color.b);
		
		
		Vector2 normalDir = direction.Normalized();
		
		float a = origin.x + length*normalDir.x;
		float b = origin.y + length*normalDir.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}
	
	/**
	 * Draw "shape" transformed by "transform" in "color"
	 * 
	 * @param shape
	 * @param transform
	 * @param color
	 */
	
	public void DrawShape(CollisionShape shape,Transform transform,Color color)
	{
		GL2 gl = MainGame.render.externDrawable.getGL().getGL2();
		
		gl.glBegin(GL2.GL_LINES);
		for(int i = 0; i<shape.vertices.length; i++)
		{
			Vector2 vertexToDraw = Matrix3.Multiply(transform.transformMatrix,new Vector3(shape.vertices[i],1f)).GetVec2();
			gl.glColor3f(color.r,color.g,color.b);
			gl.glVertex2f(vertexToDraw.x,vertexToDraw.y);
		}
		gl.glEnd();
	}


}
