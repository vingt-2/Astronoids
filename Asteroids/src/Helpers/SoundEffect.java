package Helpers;

import java.io.*;
import javax.sound.sampled.*;


public enum SoundEffect
{
	SHOOT("shoot.wav"),
	AFTERBURN("afterburn.wav"),
	CRASH("crash.wav"),
	ASTEROIDBREAK("asteroidbreak.wav"),
	EXPLOSION("explosion.wav");


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