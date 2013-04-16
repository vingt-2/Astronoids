package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 		Control Listener
 * 	<p>	Summon isPressed(key) from everywhere in the program to know if one particular key is Pressed.
 *  </p>
 * @author Vincent Petrella
 * 
 */

public class Controls implements KeyListener
{
	// that's an array of Bools, the index in the array is the keyCode. Funny Hmm ?
	boolean[] keyPressed = new boolean[KeyEvent.KEY_LAST];
	
	// To record a string from the keyboard, pass the flag recordKey to True,
	// It's latch all valid keyboard inputs (A-Z, 0-9) in recordString.
	// Backspace input removes last Char. Cool huh ?
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
	
	/**
	 * Is the key "keyCode" being pressed on the keyboard ?
	 * 
	 * @param keyCode
	 * @return true if key pressed, false otherwise.
	 */
	
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
