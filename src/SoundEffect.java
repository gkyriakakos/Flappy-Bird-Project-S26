import javax.sound.sampled.*;
import java.io.File;

//loads a sound effect to play it on command
public class SoundEffect
{
	private Clip clip;

	public SoundEffect(String path)
	{
		try
		{
			AudioInputStream in = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(in);
		}
		catch (Exception e)
		{
			System.out.println("Could not load sound '" + path + "': " + e);
			clip = null;
		}
	}

	public void play()
	{
		if (clip == null) return;

		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
}
