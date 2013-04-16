/**
 * @author Vincent Petrella
 * 
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
	boolean recordKey = false;
	String recordString = "";
	
	static int menuCounter = 0;
	
	
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		int keyCode = arg0.getKeyCode();
		if(!keyPressed[keyCode])
		{
			keyPressed[keyCode] = true;
		}
		
		if(recordKey)
		{
			if(keyCode == KeyEvent.VK_BACK_SPACE && recordString.length() > 0)
			{
				recordString = recordString.substring(0, recordString.length()-1);
			}
			else if ((keyCode >= '0' && keyCode <= '9') || (keyCode >= 'A' && keyCode <= 'Z'))
			{
				recordString += "" + (char) arg0.getKeyCode();
			}
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
	
	public String currentString()
	{
		String output = "";
		for(int i=0; i < keyPressed.length; i++)
		{
			if(keyPressed[i])
			{
				output += (char) i;
			}
		}
		return output;
	}

}
