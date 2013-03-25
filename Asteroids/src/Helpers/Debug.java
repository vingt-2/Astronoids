package Helpers;

import javax.media.opengl.GL2;

import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.CollisionShape;
import GameComponents.Transform;
import Maths.Matrix3;
import Maths.Vector2;
import Maths.Vector3;

public class Debug 
{
	public static GL2 gl;
	
	public void DrawRay(Vector2 origin,Vector2 end)
	{
		gl.glColor3f(1f,0,0);
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(end.x,end.y);
		gl.glEnd();
	}
	
	public void DrawRay(Vector2 origin,Vector2 end,Color color)
	{
		gl.glColor3f(color.r,color.g,color.b);
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(end.x,end.y);
		gl.glEnd();
	}

	public void DrawLine(Vector2 origin,Vector2 direction,float length)
	{
		gl.glColor3f(1f,0,0);
		float a = origin.x + length*direction.x;
		float b = origin.y + length*direction.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}
	
	public void DrawLine(Vector2 origin,Vector2 direction,float length,Color color)
	{
		gl.glColor3f(color.r,color.g,color.b);
		float a = origin.x + length*direction.x;
		float b = origin.y + length*direction.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}
	
	public void DrawShape(CollisionShape shape,Transform transform,Color color)
	{
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
