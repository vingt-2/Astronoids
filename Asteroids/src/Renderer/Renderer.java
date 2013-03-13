package Renderer;

//Graphis specific imports
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLEventListener;

// Util imports
import java.awt.Frame;
import java.util.ArrayList;
////

public class Renderer implements GLEventListener
{

	public Renderer(String windowName)
	{
		this.windowName = windowName;
	}
	
	ArrayList<ObjectRenderer> renderVector;
	
	
	static String windowName = "Asteroids";
	
	public void init(GLAutoDrawable drawable) 
	{	
		GL gl = drawable.getGL();
		
		//Neat grey background
        gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        // Enable z-buf test
        gl.glEnable(GL.GL_DEPTH_TEST);

        // Accept fragment if it closer to the camera than the former one
        gl.glDepthFunc(GL.GL_LESS);

        // Cull triangles which normal is not towards the camera
        gl.glEnable(GL.GL_CULL_FACE);
		
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) 
	{
		
	}

	public void display(GLAutoDrawable drawable) {
	    GL gl = drawable.getGL();
	    
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		//DRAW our shit

		gl.glFlush();
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) 
	{
	}
	
	public boolean CreateWindow()
	{
		Frame frame = new Frame(windowName);
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		frame.add(canvas);
		frame.setSize(300, 300);
		frame.setVisible(true);
		return true;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
		// TODO Auto-generated method stub
		
	}	
}