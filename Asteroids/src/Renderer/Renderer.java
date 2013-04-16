/**
 * @author Vincent Petrella
 * 
 * Renderer Class Handles creating a Render object that's going to create an awt Frame window,
 * and assign it a new OpenGL context to draw stuff on.
 * THE GAME LOOP ACTUALLY HAPPENS HERE. 
 * The GLEeventListener is the game thread, and will call init at it's first frame,
 * then display every frame.
 * 
 */
package Renderer;

//Graphics specific imports
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLEventListener;

import Game.Controls;
import Game.MainGame;

import GameComponents.ObjectRenderer;
import Helpers.Color;
import Maths.*;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Dimension;
import java.awt.Font;
// Util imports
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
////

public class Renderer implements GLEventListener
{	
	public static final int FIXED_REFRESH_RATE = 60 ; // Refresh rate fixed at (1/60)hz, leading to 60frame/s
	
	public float deltaTime = 1.f/FIXED_REFRESH_RATE;
	
	public GLAutoDrawable externDrawable;
	public MainGame mainGame;
	public ArrayList<ObjectRenderer> renderVector;
	public Font font = new Font("Courier", Font.BOLD, 40);

	private long lastTime = System.currentTimeMillis();
	private String windowName = "";
	private Vector2 screenSize;
	private FPSAnimator animator;
    private TextRenderer textRenderer = new TextRenderer(font);

	public Renderer(String windowName)
	{
		renderVector = new ArrayList<ObjectRenderer>();
		this.windowName = windowName;
	}

	
	/**
	 * Creates a Window Frame and opens a GL Context
	 * 
	 * 
	 * @param size
	 * @param controls
	 * @return
	 */
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
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {

                System.exit(0);
            }
        } );
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - size.x) / 2);
	    int y = (int) ((dimension.getHeight() - size.y) / 2);
	    frame.setLocation(x, y);
	    frame.setResizable(false);
		return frame;
	}

	/**
	 * Executed every frame by the animator.
	 */
	
	@Override
	public void display(GLAutoDrawable drawable) 
	{
		externDrawable = drawable;
		render(drawable);
		Update(drawable);
	}

	/**
	 *  Has to be there  to Implement GLEventListner but not used.
	 */
	
	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
	}

	/**
	 * Initiate the GL context.
	 */
	
	@Override
	public void init(GLAutoDrawable drawable) 
	{
		externDrawable = drawable;
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

	/**
	 * Has to be there to implement GLEventListener, but we don't resize window anyway.
	 */
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) 
	{
	}

	public void Update(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		this.UpdateTime();
		mainGame.Update(gl);
	}
	
	/**
	 * Go through all objects in the render Vector and call their Draw() methods.
	 * 
	 * @param drawable
	 */

	private void render(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		for(int i =0; i<renderVector.size();i++)
		{
			renderVector.get(i).Draw(gl);
		}

	}
	
	/**
	 * Keeps track of the DeltaTime between to game frames 
	 * (unifies the physics if the OS override the 60 fps to a 120
	 *  Or if the computer lags under 60 FPS ).
	 */
	
	public void UpdateTime()
	{
		long time = System.currentTimeMillis();
		// DeltaTime is in second, so convert it back.
		deltaTime = (time - lastTime) / 1000.f;
		lastTime = time;
	}
	
	/**
	 * What's the framerate ? 1/DeltaTime of course !
	 * 
	 * @return FPS
	 */
	
	public int GetFPS()
	{
		return (int) (1.f/deltaTime);
	}
	
	/**
	 * Gently asks the graphic drivers to load some texture in memory,
	 * returns an integer textureID. This method should be called from SharedRessources.
	 * 
	 * @param filePath
	 * @return textureID
	 */
	
	public int CreateTexture(String filePath)
	{
		IntBuffer textureID = Buffers.newDirectIntBuffer(1);
		
		GL2 gl = externDrawable.getGL().getGL2();
		
		//first we create a BufferedImage object reference
		BufferedImage bufferedImage = null;
		//initalizing width and height of image
		int w = 0;
		int h = 0;
		try
		{
			//Creating BufferedImage by reading it from file
			bufferedImage = ImageIO.read(new File(filePath));
			//Setting width and height of image equal to BufferedImage width and height
			w = bufferedImage.getWidth();
			h = bufferedImage.getHeight();
		} 
		catch (IOException e) 
		{

			System.out.println("FUCK I CANT READ YOUR TEXTURE FILE: " + filePath);
			return 0;

		}
		//Creating a writable raster on which we write BufferedImage
		WritableRaster raster = 
				Raster.createInterleavedRaster (DataBuffer.TYPE_BYTE,
						w,
						h,
						4,
						null);
		//Making ComponenetColorModel for the BufferedImage
		ComponentColorModel colorModel=
				new ComponentColorModel (ColorSpace.getInstance(ColorSpace.CS_sRGB),
						new int[] {8,8,8,8},
						true,
						false,
						ComponentColorModel.TRANSLUCENT,
						DataBuffer.TYPE_BYTE);
		//Creating BufferedImage from the given WritableRaster and ComponentColorModel			
		BufferedImage dukeImg = 
				new BufferedImage (colorModel,
						raster,
						false,
						null);

		//Creating graphics object on which to draw image
		Graphics2D g = dukeImg.createGraphics();
		//Draw original image on new image graphics object 
		g.drawImage(bufferedImage, null, null);
		//Retriving DataBufferByte which is backing this WritableRaster Object
		DataBufferByte dukeBuf =
				(DataBufferByte)raster.getDataBuffer();
		//Storing data of DataBufferByte in byte array
		byte[] dukeRGBA = dukeBuf.getData();
		//Wrapping this byte array in ByteBuffer object
		ByteBuffer bb = ByteBuffer.wrap(dukeRGBA);
		//Marking position to start for reading this ByteBuffer
		bb.position(0);
		bb.mark();
		
		gl.glGenTextures(1, textureID);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textureID.get(0));
		gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexImage2D (GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, w, h, 0, GL2.GL_RGBA, 
				GL.GL_UNSIGNED_BYTE, bb);
		
		return textureID.get(0);
		
	}
	
	/**
	 * Draws some text on top of the frame buffer using the class TextRenderer and Font.
	 * Draws "text" at "Position" in the color "color" and opacity "opacity"
	 * 
	 * @param text
	 * @param position
	 * @param color
	 * @param opacity
	 */
	
	public void DrawText(String text,Vector2 position, Color color, float opacity)
	{
		Vector2 context = new Vector2(externDrawable.getWidth(),externDrawable.getHeight());
		textRenderer.beginRendering((int)context.x,(int)context.y);
	    // optionally set the color
		textRenderer.setColor(color.r, color.g, color.b, opacity);
		textRenderer.draw(text,(int)(position.x+context.x/2),(int)(position.y+context.y/2));
		textRenderer.endRendering();
	}
	
	/**
	 *  Call this whenever the game is suspected to lag, like after loading stuff
	 *  To avoid physics bumping all over the place
	 */
	
	public void CheatTime()
	{
		lastTime = System.currentTimeMillis();
	}

}