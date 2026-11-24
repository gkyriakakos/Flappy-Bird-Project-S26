import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bird
{
	double y;
	double velocity;
	double angle;

	public Bird()
	{
		reset();
	}
	//places the bird when the game is reset; reset also is used on initial game start
	public void reset()
	{
		y = Const.HEIGHT / 2.0;
		velocity = 0;
		angle = 0;
	}
	//flaps the birds wings
	public void flap()
	{
		velocity = Const.FLAP_STRENGTH;
	}
	//handles bird physics, applying constant gravity to it
	public void update()
	{
		velocity += Const.GRAVITY;
		if (velocity > Const.MAX_FALL_SPEED) velocity = Const.MAX_FALL_SPEED;
		y += velocity;

		angle = Math.max(-25, Math.min(90, velocity * 4));
	}

	//Keeps the bird from ascending too high
	public void clampToCeiling()
	{
		if (y - Const.BIRD_SIZE / 2.0 <= 0)
		{
			y = Const.BIRD_SIZE / 2.0;
			velocity = 0;
		}
	}
	//checks if the bird has hit the ground, so as to fire a gameover
	public boolean hitsGround()
	{
		return y + Const.BIRD_SIZE / 2.0 >= Const.HEIGHT - Const.GROUND_HEIGHT;
	}
	//locks the bird to the ground so it stops falling
	public void landOnGround()
	{
		y = Const.HEIGHT - Const.GROUND_HEIGHT - Const.BIRD_SIZE / 2.0;
	}
	//calculates the bird's rectangular hitbox
	public Rectangle getBounds()
	{
		return new Rectangle(
				Const.BIRD_X - Const.BIRD_SIZE / 2,
				(int) (y - Const.BIRD_SIZE / 2),
				Const.BIRD_SIZE,
				Const.BIRD_SIZE);
	}
	//draws the bird
	public void draw(Graphics2D g)
	{
		final int S = Const.BIRD_SIZE;
		AffineTransform old = g.getTransform();

		g.translate(Const.BIRD_X, (int) y);
		g.rotate(Math.toRadians(angle));

		g.setColor(new Color(255, 213, 61));
		g.fillOval(-S / 2, -S / 2, S, S);

		g.setColor(new Color(240, 170, 30));
		g.fillOval(-S / 2, -2, S / 2, S / 2);

		g.setColor(Color.WHITE);
		g.fillOval(S / 6, -S / 3, 10, 10);
		g.setColor(Color.BLACK);
		g.fillOval(S / 6 + 3, -S / 3 + 2, 5, 5);

		g.setColor(new Color(255, 140, 30));
		g.fillPolygon(
				new int[]{S / 2 - 4, S / 2 + 10, S / 2 - 4},
				new int[]{-4, 2, 8},
				3);

		g.setTransform(old);
	}
}
