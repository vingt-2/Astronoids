package Helpers;

import java.io.*;
import javax.sound.sampled.*;

/**
 * SoundEffect:
 * 		A sound effects enum which encapsulates all sound effects and music:
 * 			Action related sfx, background music, etc..
 * 			The sounds are then simply played where events occur by
 * 			using the command SoundEffect.EFFECTNAME.play()
 */

public enum SoundEffect
{
	//This is where all the different sounds are defined and the filenames specified
	SHOOT("shoot.wav"),
	AFTERBURN("afterburn.wav"),
	CRASH("crash.wav"),
	ASTEROIDBREAK("asteroidbreak.wav"),
	EXPLOSION("explosion.wav"),
	SHIELD("shield.wav"),
	RAPIDFIRE("rapidfire.wav"),
	NEWLIFE("newlife.wav"),
	GAMEOVER("gameover.wav"),
	BACKGROUND("Background_Music.wav");

	public static enum Volume
	{
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.MEDIUM;

	private Clip clip;

	SoundEffect(String soundFileName)
	{
		try
		{
			//loading sound files as clips into the game

			File soundclip = new File("./resources/soundfx/"+soundFileName);
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundclip);
			
			clip = AudioSystem.getClip();

			clip.open(audioInputStream);
		}
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}

	}
	
	/**
	 * Method used to play sounds, also rewinds clips by setting frame position to 0 before starting
	 */

	public void play()
	{
		if(volume != Volume.MUTE)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}
}