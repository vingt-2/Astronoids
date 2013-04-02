/**
 * Controls:
 * 		Key Listener.
 * 		
 * 		Summon isPressed(key) from everywhere in the program to know if one particular key is Pressed.
 * 
 */

package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controls implements KeyListener
{

	boolean[] keyPressed = new boolean[KeyEvent.KEY_LAST];
	static int menuCounter =0;
	
	
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		if(!keyPressed[arg0.getKeyCode()])
		{
			keyPressed[arg0.getKeyCode()] = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN && menuCounter < 3)
			menuCounter++;
		if(arg0.getKeyCode() == KeyEvent.VK_UP && menuCounter != 0)
			menuCounter--;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		keyPressed[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	
	public boolean isPressed(int keyCode)
	{	
		return keyPressed[keyCode];
	}

}
