package GameComponents;

import java.nio.IntBuffer;

import Game.MainGame;
import GameObjects.GameChar;
import Maths.*;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class ObjectRenderer 
{
	public Vector2[] objectUVs;
	public Shape shape;

	private GameChar parent;
	private boolean textureSet = false;
	private IntBuffer textureID;

	public ObjectRenderer(GameChar parent)
	{
		MainGame.render.renderVector.add(this);
		objectUVs = new Vector2[0];
		this.parent = parent;
	}

	public boolean Draw(GL2 gl)
	{
		if(textureSet)
		{
			gl.glEnable( GL.GL_TEXTURE_2D );
			gl.glBindTexture( GL.GL_TEXTURE_2D, textureID.get(0) );
			gl.glEnable( GL2.GL_BLEND );
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		}
		gl.glBegin(GL2.GL_QUADS);
		for(int i = 0; i<shape.vertices.length; i++)
		{
			Vector2 vertexToDraw = Matrix3.Multiply(parent.transform.transformMatrix,new Vector3(shape.vertices[i],1f)).GetVec2();
			Vector2 color = vertexToDraw.Normalized();
			Vector2 UVs = shape.UVs[i];
			if(textureSet)
			{
				gl.glTexCoord2f(UVs.x, UVs.y);
			}
			else
			{
				gl.glColor3f(color.x,color.y,1);
			}
			gl.glVertex2f(vertexToDraw.x,vertexToDraw.y);
		}
		gl.glEnd();
		if(textureSet)
		{
			gl.glDisable(GL.GL_TEXTURE_2D);
		}
		return true;
	}

	public void SetTexture(String filePath)
	{
		textureID = MainGame.render.CreateTexture(filePath);
		textureSet = true;
	}

	public enum Shape
	{
		Square
		( 
				new Vector2[]{ new Vector2(-10f,-10f), new Vector2(10f,-10f),
				new Vector2(10f,10f), new Vector2(-10f,10f) },
				
				new Vector2[]{ new Vector2(0,1), new Vector2(1,1),
				new Vector2(1,0), new Vector2(0,0) } 
		);

		Vector2[] vertices;
		Vector2[] UVs;

		Shape(Vector2[] vertices, Vector2[] UVs)
		{
			this.vertices = vertices;
			this.UVs = UVs;
		}

	}

}
