import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class FlappyBird extends GameBase
{
	GameState   state = GameState.READY;
	Bird        bird  = new Bird();
	PipeManager pipes = new PipeManager();
	Scenery     scenery = new Scenery();
	
	//
	SoundEffect pointSound = new SoundEffect("../SFX/point.wav");
	
	
	//scoreboard variables
	int score     = 0;
	int highScore = 0;

	//to prevent keys from simply being held down
	boolean flapWasDown = false;
	boolean rWasDown    = false;

	//added due to mouse listener running on a different thread than the gameloop
	volatile boolean mouseFlap = false;
	
	//resets the game without clearing the highscore
	private void resetGame()
	{
		bird.reset();
		pipes.reset();
		score = 0;
		state = GameState.READY;
	}
	
	//the very first flap that occurs on game start
	private void flap()
	{
		if (state == GameState.READY) state = GameState.PLAYING;
		if (state == GameState.PLAYING) bird.flap();
	}

	//Core run function
	public void run()
	{
		while (true)
		{
			inGameLoop();

			repaint();

			try
			{
				Thread.sleep(16);
			}
			catch (Exception x) {}
		}
	}
	//the frame by frame loop, increases the score as pipes are passed
	public void inGameLoop()
	{
		handleInput();

		scenery.update();

		if (state != GameState.PLAYING) return;

		bird.update();
		score += pipes.update();
		int passed = pipes.update();
		if (passed > 0) {
			pointSound.play();
		}
		if (score > highScore) {
			highScore = score;
		}

		checkCollisions();
	}
	
	//handles the inputs, both from spacebar, any letter key, or mouse click.  Since r is both reset from gameover and a flap, it can be played solely through r or left click
	private void handleInput()
	{
		boolean flapDown = pressing[SPACE] || anyLetterDown();
		boolean rDown = pressing[_R];

		if (flapDown && !flapWasDown) flap();
		if (rDown && !rWasDown && state == GameState.GAME_OVER) resetGame();
		if (pressing[KeyEvent.VK_ESCAPE]) System.exit(0);

		if (mouseFlap)
		{
			mouseFlap = false;
			if (state == GameState.GAME_OVER) resetGame();
			else flap();
		}

		flapWasDown = flapDown;
		rWasDown = rDown;
	}
	//checks for keys being held down, so each press is only a 1 flap
	private boolean anyLetterDown()
	{
	    for (int key = _A; key <= _Z; key++)
	    {
	        if (pressing[key]) return true;
	    }
	    return false;
	}
	//checks for collisions with ground and pipes, which result in a gameover, and with the ceiling which simply blocks further ascension
	private void checkCollisions()
	{
		if (bird.hitsGround())
		{
			bird.landOnGround();
			gameOver();
			return;
		}

		bird.clampToCeiling();

		if (pipes.collidesWith(bird.getBounds()))
		{
			gameOver();
		}
	}
	//ends the game when lost, and updates the highscore if necessary
	private void gameOver()
	{
		if (state != GameState.GAME_OVER)
		{
			state = GameState.GAME_OVER;
			if (score > highScore) highScore = score;
		}
	}

	
	//paints the world procedurally/randomly, starting offscreen, as a tilemap would not work
	public void paint(Graphics g0)
	{
		Graphics2D g = (Graphics2D) g0;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		scenery.drawSky(g);
		pipes.draw(g);
		scenery.drawGround(g);
		bird.draw(g);
		drawUI(g);
	}
	//handles the UI
	private void drawUI(Graphics2D g)
	{
		//playing state
		if (state == GameState.PLAYING || state == GameState.GAME_OVER)
		{
			g.setFont(new Font("Arial", Font.BOLD, 36));
			FontMetrics fm = g.getFontMetrics();
			String scoreText = String.valueOf(score);
			int tx = (Const.WIDTH - fm.stringWidth(scoreText)) / 2;
			g.setColor(Color.BLACK);
			g.drawString(scoreText, tx + 2, 62);
			g.setColor(Color.WHITE);
			g.drawString(scoreText, tx, 60);
		}
		//ready state
		if (state == GameState.READY)
		{
			drawCenteredMessage(g, "FLAPPY BIRD", -40, 32);
			drawCenteredMessage(g, "Press SPACE / Click to Start", 10, 18);
		}
		//gameover state
		else if (state == GameState.GAME_OVER)
		{
			g.setColor(new Color(0, 0, 0, 120));
			g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);

			drawCenteredMessage(g, "GAME OVER", -60, 34);
			drawCenteredMessage(g, "Score: " + score, -15, 22);
			drawCenteredMessage(g, "Best:  " + highScore, 15, 22);
			drawCenteredMessage(g, "Press R to Restart", 55, 18);
		}
	}
	//helper function for text, controls font size and centers the text
	private void drawCenteredMessage(Graphics2D g, String text, int yOffset, int size)
	{
		g.setFont(new Font("Arial", Font.BOLD, size));
		FontMetrics fm = g.getFontMetrics();
		int tx = (Const.WIDTH - fm.stringWidth(text)) / 2;
		int ty = Const.HEIGHT / 2 + yOffset;
		g.setColor(Color.BLACK);
		g.drawString(text, tx + 2, ty + 2);
		g.setColor(Color.WHITE);
		g.drawString(text, tx, ty);
	}

//handles mouse input
	public void mousePressed(MouseEvent e)
	{
		mouseFlap = true;
	}

	// GameBase's mouseClicked prints coordinates; silence it for the game.
	public void mouseClicked(MouseEvent e) {}
}