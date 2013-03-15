package Renderer;

//Graphics specific imports
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLEventListener;

import Game.MainGame;
import GameComponents.ObjectRenderer;
import Maths.*;

import com.jogamp.opengl.util.FPSAnimator;

// Util imports
import java.awt.Frame;
import java.util.ArrayList;
////

public class Renderer implements GLEventListener
{	
	public static final int RERFRESH_RATE = 60 ; // Refresh rate fixed at (1/60)hz, leading to 60frame/s
	String windowName = "";
	Vector2 screenSize;
	FPSAnimator animator;
	GL2 openGLContext;

	public MainGame mainGame;


	public ArrayList<ObjectRenderer> renderVector;


	public Renderer(String windowName)
	{
		renderVector = new ArrayList<ObjectRenderer>();
		this.windowName = windowName;
	}



	public Frame CreateWindow(Vector2 size)
	{
		Frame frame = new Frame(windowName);
		GLProfile glp = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(glp);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);

		animator = new FPSAnimator(canvas,60);
		animator.add(canvas);
		animator.start();

		screenSize = size;

		frame.add(canvas);
		frame.setSize((int)size.x, (int)size.y);
		frame.setVisible(true);
		return frame;
	}


	@Override
	public void display(GLAutoDrawable drawable) 
	{
		render(drawable);
		Update();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-screenSize.x/2, screenSize.x/2, screenSize.y/2, - screenSize.y/2, 1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		
		gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

		mainGame.init();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) 
	{
	}

	public void Update()
	{
		mainGame.Update();
	}

	private void render(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		for(int i =0; i<renderVector.size();i++)
		{
			renderVector.get(i).Draw(gl);
		}

	}

}