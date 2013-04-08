import java.io.*
import java.net.URL;
import javax.sound.sampled.*;


public enum SoundEffect
{
	EXPLODE("explode.wav"),
	SHOOT("shoot.wav");


	public static enum Volume
	{
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;

	private Clip clip;

	SoundEffect(String soundFileName)
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource(soundFileName);
	
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			
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