/**
 * @author Vincent Petrella
 * 
 * ObjectRenderer:
 * 		An Object component that holds all the infos related to Drawing the object.
 * 		It is linked to it's parent. so that It can access the transform information to draw at the right spot.
 * 		The renderer Display pipeline goes through all ObjectRenderer to be displays
 * 		and call their Draw() methods, according to: 
 * 		shape (of the primitive), the texture to be projected in this shape, and an opacity coefficient
 * 
 */

package GameComponents;

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
	private int textureID;
	
	public float opacity = 1;

	public ObjectRenderer(GameChar parent)
	{
		MainGame.render.renderVector.add(this);
		shape = Shape.Square;
		objectUVs = new Vector2[0];
		this.parent = parent;
	}

	/**
	 * Draws the object to the specified openGL context "gl".
	 * 
	 * @param gl
	 * @return
	 */
	
	public boolean Draw(GL2 gl)
	{
		if(textureSet)
		{
			gl.glEnable( GL.GL_TEXTURE_2D );
			gl.glBindTexture( GL.GL_TEXTURE_2D, textureID );
			gl.glEnable( GL2.GL_BLEND );
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			gl.glAlphaFunc(GL2.GL_GREATER,0.01f);
			gl.glTexEnvf(GL2.GL_TEXTURE_ENV,GL2.GL_TEXTURE_ENV_MODE,GL2.GL_MODULATE);
			gl.glColor4f(1,1,1,opacity);
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

	/**
	 * Get a texture name from the sharedRessource Table from MainGame,
	 * and assign it's corresponding textureID to the object.
	 * If the lookup fails, textureSet flag will not be set to True,
	 * the draw method will then display the primitive shape.
	 * 
	 * @param name
	 */
	public void SetTexture(String name)
	{
		int texID = MainGame.sharedRessources.GetTexture(name);
		textureSet = (texID != 0);
		textureID = texID;
	}

	/**
	 * Some Shapes that will be used as primitive to draw objects.
	 * The shapes are a couple of vertices, defined in local space.
	 *
	 */
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
