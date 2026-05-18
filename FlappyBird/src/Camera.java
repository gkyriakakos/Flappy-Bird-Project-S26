
public class Camera
{
	static int x = 0;
	static int y = 0;
	static int z = 0;
	
	static int A = 0;
	
	static double cosA = 1;
	static double sinA = 0;
	
	
	public static void setAngle(int A)
	{
		Camera.A = A;
		
		cosA = Math.cos(Math.PI / 180 * A);
		sinA = Math.sin(Math.PI / 180 * A);
	}
	
	public static void SetLocation(int x, int y, int z)
	{
		Camera.x = x;
		Camera.y = y;
		Camera.z = z;
	}
	
	
	public static void turnLT(int dA)
	{
		A -= dA;

		cosA = Math.cos(Math.PI / 180 * A);
		sinA = Math.sin(Math.PI / 180 * A);
	}
	
	
	public static void turnRT(int dA)
	{
		A += dA;

		cosA = Math.cos(Math.PI / 180 * A);
		sinA = Math.sin(Math.PI / 180 * A);
	}
	
	
	public static void moveRight(int d)
	{
		x += d * cosA;
		z += d * sinA;
	}
	
	
	public static void moveLeft(int d)
	{
		x -= d * cosA;
		z -= d * sinA;
	}
	
	public static void moveBackward(int d)
	{
		x += d * sinA;
		z -= d * cosA;		
	}
	
	public static void moveForward(int d)
	{
		x -= d * sinA;
		z += d * cosA;		
	}
	
	public static void moveUp(int d)
	{
		y -= d;
	}
	
	public static void moveDown(int d)
	{
		y += d;
	}
	
	
	
	

}