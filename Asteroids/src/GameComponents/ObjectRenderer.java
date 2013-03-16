package GameComponents;

import GameObjects.GameChar;
import Maths.*;

import javax.media.opengl.GL2;

public class ObjectRenderer 
{
	public Vector2[] objectUVs;
	public Shape shape;

	private GameChar parent;
	
	public ObjectRenderer(GameChar parent)
	{
		objectUVs = new Vector2[0];
		this.parent = parent;
	}

	public boolean Draw(GL2 gl)
	{
		gl.glBegin(GL2.GL_QUADS);
		for(int i = 0; i<shape.vertices.length; i++)
		{
			Vector2 vertexToDraw = Matrix3.Multiply(parent.transform.transformMatrix,new Vector3(shape.vertices[i],1f)).GetVec2();
			Vector2 color = vertexToDraw.Normalized();
			gl.glColor3f(color.x,color.y,1);
			gl.glVertex2f(vertexToDraw.x,vertexToDraw.y);
		}
		gl.glEnd();
		return true;
	}
	
	public enum Shape
	{
		Square(new Vector2[]{ new Vector2(-10f,-10f), new Vector2(10f,-10f),
			new Vector2(10f,10f), new Vector2(-10f,10f) });
		
		Vector2[] vertices;
		
		Shape(Vector2[] vertices)
		{
			this.vertices = vertices;
		}
		
	}
	
}
