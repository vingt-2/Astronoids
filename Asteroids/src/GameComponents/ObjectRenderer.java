package GameComponents;

import GameObjects.GameChar;
import Maths.*;

import javax.media.opengl.GL2;

public class ObjectRenderer 
{
	public Vector2[] objectVertices;
	public Vector2[] objectUVs;

	private GameChar parent;
	
	public ObjectRenderer(GameChar parent)
	{
		objectVertices = new Vector2[0];
		objectUVs = new Vector2[0];
		this.parent = parent;
	}

	public boolean Draw(GL2 gl)
	{
		gl.glBegin(GL2.GL_QUADS);
		for(int i = 0; i<objectVertices.length; i++)
		{
			Vector2 vertexToDraw = Matrix3.Multiply(parent.transform.transformMatrix,new Vector3(objectVertices[i],1f)).GetVec2();
			gl.glColor3f(vertexToDraw.x,vertexToDraw.y,1);
			gl.glVertex2f(vertexToDraw.x,vertexToDraw.y);
		}
		gl.glEnd();
		return true;
	}
}
