import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PipeManager
{
	//array to hold the pipes
	private final ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	private final Random random = new Random();
	
	//grabs the sound effect for when pipes are passed
	SoundEffect pointSound = new SoundEffect("../SFX/point.wav");
	
	public PipeManager()
	{
		reset();
	}
	//resets the pipe manager, for startup so they are not carried over from one game to another
	public void reset()
	{
		pipes.clear();
		int startX = Const.WIDTH + 100;
		for (int i = 0; i < 4; i++)
		{
			pipes.add(makePipe(startX + i * Const.PIPE_SPACING));
		}
	}
	//creates a new pipe, with a random location on the screen
	private Pipe makePipe(int x)
	{
		int minGapY = 80;
		int maxGapY = Const.HEIGHT - Const.GROUND_HEIGHT - Const.PIPE_GAP - 80;
		int gapY = minGapY + random.nextInt(Math.max(1, maxGapY - minGapY));
		return new Pipe(x, gapY);
	}
	//handles the pipe's updates each frame; moving from right to left, removing any pipes fully offscreen on the left and creates new pipes as pipes are removed
	public int update()
	{
		for (Pipe p : pipes) p.update();

		if (pipes.get(0).isOffScreen())
		{
			pipes.remove(0);
			Pipe last = pipes.get(pipes.size() - 1);
			pipes.add(makePipe(last.x + Const.PIPE_SPACING));
		}

		int points = 0;
		for (Pipe p : pipes)
		{
			if (!p.scored && p.x + Const.PIPE_WIDTH < Const.BIRD_X)
			{
				p.scored = true;
				points++;
				pointSound.play();
			}
		}
		return points;
	}
	//checks if a pipe has collided with a pipe
	public boolean collidesWith(Rectangle r)
	{
		for (Pipe p : pipes)
		{
			if (p.collidesWith(r)) return true;
		}
		return false;
	}
	//draws the pipes
	public void draw(Graphics2D g)
	{
		for (Pipe p : pipes) p.draw(g);
	}
}
