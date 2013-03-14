package Renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import Maths.Matrix2;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;

import com.jogamp.common.nio.Buffers;

import Helpers.Files;

public class ObjectRenderer 
{
	float[] objectVertices;
	float[] objectUVs;

	int vertexArrayID;
	int vertexBufferID;
	int uvBufferID;
	int textureID;
	int shaderID;
	int matrixID;

	Matrix2 objectMatrix;

	public boolean LoadShaders(GL3 gl,String pathToVertexFile,String pathToFragmentFile)
	{

		int vertexShaderID = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		int fragmentShaderID = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);


		//Load VertexShader
		System.out.println("[Shader] Loading vertex Shader: \"" + pathToVertexFile + "\".");
		String vertexShader = Files.ReadFile(pathToVertexFile);
		if(vertexShader.equals(Helpers.Files.BROKEN_STRING))
		{
			System.out.println("Couldn't load vertex Shader: " + "\"" + pathToVertexFile + "\"." );
			return false;
		}

		System.out.print("[Shader] Compiling ...");
		gl.glShaderSource(vertexShaderID,1, new String[] { vertexShader }, (int[]) null, 0);
		gl.glCompileShader(vertexShaderID);		
		System.out.println(" Ok.");

		//Load FragmentShader

		System.out.println("[Shader] Loading vertex Shader: \"" + pathToFragmentFile + "\".");
		String fragmentShader = Files.ReadFile(pathToFragmentFile);
		if(vertexShader.equals(Helpers.Files.BROKEN_STRING))
		{
			System.out.println("Couldn't load vertex Shader: " + "\"" + pathToFragmentFile + "\"." );
			return false;
		}

		System.out.print("[Shader] Compiling ...");
		gl.glShaderSource(fragmentShaderID,1, new String[] { fragmentShader }, (int[]) null, 0);
		gl.glCompileShader(fragmentShaderID);
		System.out.println(" Ok.");

		// Link the program
		System.out.println("[Shader] Linking program");
		int programID = gl.glCreateProgram();
		gl.glAttachShader(programID, vertexShaderID);
		gl.glAttachShader(programID, fragmentShaderID);
		gl.glLinkProgram(programID);

		gl.glDeleteShader(vertexShaderID);
		gl.glDeleteShader(fragmentShaderID);

		this.shaderID = programID;
		this.matrixID = gl.glGetUniformLocation(programID, "MVP");

		return true;
	}

	public boolean GenerateArrays(GL3 gl)
	{

		IntBuffer vertexArrayID = Buffers.newDirectIntBuffer(1);

		gl.glGenVertexArrays(1, vertexArrayID);
		gl.glBindVertexArray(vertexArrayID.get());

		//TO BE RELOCATED WHEN WE SUPPORT TEXTURES

		// Load the texture
		//GLuint Texture = loadDDS("uvmap.DDS");
		int texture = 0;

		// Get a handle for our "myTextureSampler" uniform
		textureID  = gl.glGetUniformLocation(shaderID, "myTextureSampler");

		// Load it into a VBO

		IntBuffer vertexBufferID = Buffers.newDirectIntBuffer(1);

		FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(objectVertices.length);
		for (int i = 0; i < objectVertices.length; i++) 
		{
			vertexBuffer.put(objectVertices[i]);
		}
		vertexBuffer.rewind();

		gl.glGenBuffers(1, vertexBufferID);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferID.get());
		gl.glBufferData(GL.GL_ARRAY_BUFFER, (long)objectVertices.length, vertexBuffer, GL.GL_STATIC_DRAW);

		IntBuffer uvBufferID = Buffers.newDirectIntBuffer(1);

		FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(objectUVs.length);

		for (int i = 0; i < objectUVs.length; i++) 
		{
			uvBuffer.put(objectVertices[i]);
		}
		uvBuffer.rewind();

		if( objectUVs.length != 0 )
		{
			gl.glGenBuffers(1, uvBufferID);
			gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvBufferID.get());
			gl.glBufferData(GL.GL_ARRAY_BUFFER, (long)objectUVs.length, uvBuffer, GL.GL_STATIC_DRAW);
		}
		return true;
	}

	public boolean Draw(GL3 gl)
	{
		gl.glUseProgram(shaderID);

		FloatBuffer buff = Buffers.newDirectFloatBuffer(0);

		// Enable vertices
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferID);
		gl.glVertexAttribPointer
			(
				0,				// attribute
				3,				// size
				GL.GL_FLOAT,	// type
				false,			// normalized?
				0,				// stride
				buff			// array buffer offset
			);

		// Enable UVs
		if( objectUVs.length != 0 )
		{
			gl.glEnableVertexAttribArray(1);
			gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvBufferID);
			gl.glVertexAttribPointer
				(
					0,				// attribute
					3,				// size
					GL.GL_FLOAT,	// type
					false,			// normalized?
					0,				// stride
					buff			// array buffer offset
				);
		}
		// Draw
		//glDrawArrays(0x0003, 0, meshVertices.size() );
		gl.glDrawArrays(GL.GL_TRIANGLES,0, objectVertices.length );

		gl.glDisableVertexAttribArray(0);
		if( objectUVs.length != 0 )
		{
			gl.glDisableVertexAttribArray(1);
		}
		return true;
	}
}
