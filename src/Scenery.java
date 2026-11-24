import java.awt.*;


public class Scenery
{
	private int groundOffset = 0;
	//updates the ground so it runs at the same speed as the pipes
	public void update()
	{
		groundOffset = (groundOffset - Const.PIPE_SPEED) % 40;
	}
	//draws the sky on a gradient so it's not monocolor,
	public void drawSky(Graphics2D g)
	{
		GradientPaint sky = new GradientPaint(0, 0, new Color(78, 192, 202),
				0, Const.HEIGHT, new Color(180, 235, 235));
		g.setPaint(sky);
		g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
		//clouds so the sky is not featureless
		g.setColor(new Color(255, 255, 255, 180));
		g.fillOval(50, 80, 70, 30);
		g.fillOval(90, 65, 60, 30);
		g.fillOval(300, 150, 80, 28);
		g.fillOval(340, 135, 55, 28);
	}
	//draws the ground, with a some filler so it's movement can be visible
	public void drawGround(Graphics2D g)
	{
		int groundY = Const.HEIGHT - Const.GROUND_HEIGHT;
		g.setColor(new Color(222, 216, 149));
		g.fillRect(0, groundY, Const.WIDTH, Const.GROUND_HEIGHT);

		g.setColor(new Color(99, 170, 66));
		g.fillRect(0, groundY, Const.WIDTH, 12);

		g.setColor(new Color(210, 200, 130));
		for (int x = groundOffset - 40; x < Const.WIDTH; x += 40)
		{
			g.fillRect(x, groundY + 20, 20, 8);
		}
	}
}
