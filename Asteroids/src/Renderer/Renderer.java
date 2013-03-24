package Renderer;

//Graphics specific imports
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLEventListener;

import Game.Controls;
import Game.MainGame;
import Game.Menu;
import GameComponents.ObjectRenderer;
import Maths.*;

import com.jogamp.opengl.util.FPSAnimator;

// Util imports
import java.awt.Frame;
import java.awt.event.KeyListener;
import java.util.ArrayList;
////

public class Renderer implements GLEventListener
{	
	public static final int RERFRESH_RATE = 60 ; // Refresh rate fixed at (1/60)hz, leading to 60frame/s
	String windowName = "";
	Vector2 screenSize;
	FPSAnimator animator;
	public GL2 openGLContext;

	public MainGame mainGame;
	public Menu menu;

	public ArrayList<ObjectRenderer> renderVector;


	public Renderer(String windowName)
	{
		renderVector = new ArrayList<ObjectRenderer>();
		this.windowName = windowName;
	}



	public Frame CreateWindow(Vector2 size, Controls controls)
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

		canvas.addKeyListener(controls);
		
		frame.add(canvas);
		frame.setSize((int)size.x, (int)size.y);
		frame.setVisible(true);
		return frame;
	}


	@Override
	public void display(GLAutoDrawable drawable) 
	{
		render(drawable);
		Update(drawable);
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
		gl.glScalef(2.f/screenSize.x,2.f/screenSize.y, 0);
		gl.glOrthof(0, screenSize.x, screenSize.y,0 , 1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		
		if (gl.isExtensionAvailable("GL_ARB_multisample"))
		{
		   gl.glEnable(GL2.GL_MULTISAMPLE);
		}
		
		gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

		mainGame.init(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) 
	{
	}

	public void Update(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		mainGame.Update(gl);
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