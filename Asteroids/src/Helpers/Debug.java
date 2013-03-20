package Helpers;

import javax.media.opengl.GL2;
import Maths.Vector2;

public class Debug 
{
	public GL2 gl;
	
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


}
