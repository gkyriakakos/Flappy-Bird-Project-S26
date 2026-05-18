import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameS26 extends GameBase
{	
	Line L1 = new Line(200, 1000, -200, 1000);
	Line L2 = new Line(-200, 1000, -600, 1000);
	Line L3 = new Line(-600, 1000, -600, 600);
	
	public void initialize()
	{	
	}
	
	public void inGameLoop()
	{
		
		if(pressing[UP])  Camera.moveForward(5);
		if(pressing[DN])  Camera.moveBackward(5);
		if(pressing[LT])  Camera.moveLeft(5);
		if(pressing[RT])  Camera.moveRight(5);
		
		
		if(pressing[COMMA])  Camera.turnLT(3);
		if(pressing[PERIOD]) Camera.turnRT(3);
	}
	
		
	public void paint(Graphics g)
	{
		
		L1.draw(g);
		L2.draw(g);
		L3.draw(g);
	}
}
