package Helpers;

import javax.media.opengl.GL2;
import Maths.Vector2;

public class Debug 
{
	public GL2 gl;
	
	public void DrawRay(Vector2 origin,Vector2 direction)
	{
		gl.glColor3f(1f,0,0);
		float a = direction.x - origin.x;
		float b = direction.y - origin.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(direction.x,direction.y);
		gl.glEnd();
	}
	
	public void DrawRay(Vector2 origin,Vector2 direction,Color color)
	{
		gl.glColor3f(color.r,color.g,color.b);
		float a = direction.x;
		float b = direction.y;;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(direction.x,direction.y);
		gl.glEnd();
	}

	public void DrawRay(Vector2 origin,Vector2 direction,float length)
	{
		gl.glColor3f(1f,0,0);
		float a = origin.x + length*direction.x;
		float b = origin.y + length*direction.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}
	
	public void DrawRay(Vector2 origin,Vector2 direction,float length,Color color)
	{
		gl.glColor3f(color.r,color.g,color.b);
		float a = origin.x + length*direction.x;
		float b = origin.y + length*direction.y;
		
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(origin.x, origin.y);
			gl.glVertex2f(a,b);
		gl.glEnd();
	}


}
