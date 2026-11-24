import java.awt.*;

public class Pipe
{
	int x;
	int gapY;
	boolean scored = false;
	//the pipe object, containing a location and a gap, which is the location of the gap
	public Pipe(int x, int gapY)
	{
		this.x = x;
		this.gapY = gapY;
	}
	//moves pipes from right to left
	public void update()
	{
		x -= Const.PIPE_SPEED;
	}
	//checks for when the pipe has gone offscreen to the left
	public boolean isOffScreen()
	{
		return x + Const.PIPE_WIDTH < 0;
	}
	//gets the top rectangel of the pipe
	public Rectangle getTopBounds()
	{
		return new Rectangle(x, 0, Const.PIPE_WIDTH, gapY);
	}
	//gets the bottom rectangle of the pipe
	public Rectangle getBottomBounds()
	{
		int top = gapY + Const.PIPE_GAP;
		return new Rectangle(x, top, Const.PIPE_WIDTH, Const.HEIGHT - Const.GROUND_HEIGHT - top);
	}
	//checks if another rectangle(the bird) is overlapping with a pipe
	public boolean collidesWith(Rectangle r)
	{
		return r.intersects(getTopBounds()) || r.intersects(getBottomBounds());
	}
	//draws the pipes
	public void draw(Graphics2D g)
	{
		Rectangle top = getTopBounds();
		Rectangle bot = getBottomBounds();
		//the main part of the pipe
		g.setColor(new Color(76, 187, 76));
		g.fillRect(top.x, top.y, top.width, top.height);
		g.fillRect(bot.x, bot.y, bot.width, bot.height);
		//the pipehead
		g.setColor(new Color(56, 152, 56));
		g.fillRect(x - 4, gapY - 24, Const.PIPE_WIDTH + 8, 24);
		g.fillRect(x - 4, gapY + Const.PIPE_GAP, Const.PIPE_WIDTH + 8, 24);
	}
}
