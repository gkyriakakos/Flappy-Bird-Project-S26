import java.awt.*;

public class Line
{
	int Ax;
	int Az;
	
	int Bx;
	int Bz;
				
	double Nx;
	double Nz;
	
	double c;
	
	
	int d = 512;
	
	
	public Line(int Ax, int Az, int Bx, int Bz)
	{
		this.Ax = Ax;
		this.Az = Az;
		
		this.Bx = Bx;
		this.Bz = Bz;
		
		int Vx = Ax - Bx;
		int Vz = Az - Bz;
		
		double mag = Math.sqrt(Vx*Vx + Vz*Vz);
		
		Nx = -Vz / mag;
		Nz =  Vx / mag;
		
		c = - Nx * Ax - Nz * Az;
	}
	
	
	public double distanceTo(double Px, double Pz)
	{
		return Nx * Px + Nz * Pz + c;
	}
	
	
	public void draw(Graphics g)
	{
		int yT = 100;
		int yB = 0;
		
		double cosA = Camera.cosA;
		double sinA = Camera.sinA;
		
		// Adjust for Camera position
		double Axc = Ax - Camera.x;
		double ATc = yT - Camera.y;
		double ABc = yB - Camera.y;
		double Azc = Az - Camera.z;

		double Bxc = Bx - Camera.x;
		double BTc = yT - Camera.y;
		double BBc = yB - Camera.y;
		double Bzc = Bz - Camera.z;
		
		// Rotate by Camera -angle
		double Axr = Axc * cosA - Azc * sinA;
		double ATr = ATc;
		double ABr = ABc;
		double Azr = Axc * sinA + Azc * cosA;
		
		double Bxr = Bxc * cosA - Bzc * sinA;
		double BTr = BTc;
		double BBr = BBc;
		double Bzr = Bxc * sinA + Bzc * cosA;
		
		// 3D perspective Transformation
		double Ax3D = d * Axr / Azr;
		double AT3D = d * ATr / Azr;
		double AB3D = d * ABr / Azr;
		
		double Bx3D = d * Bxr / Bzr;
		double BT3D = d * BTr / Bzr;
		double BB3D = d * BBr / Bzr;
		
		// Center Vanishing Point
		int Axs = (int) Ax3D + Origin.x; 
		int ATs = (int) AT3D + Origin.y; 
		int ABs = (int) AB3D + Origin.y; 
		
		int Bxs = (int) Bx3D + Origin.x; 
		int BTs = (int) BT3D + Origin.y; 
		int BBs = (int) BB3D + Origin.y; 
		
		// Draw Polygon
		
		int[] x_points = {Axs, Bxs, Bxs, Axs};
		int[] y_points = {ATs, BTs, BBs, ABs};
		
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillPolygon(x_points, y_points, 4);
		
		g.setColor(Color.BLACK);
		g.drawPolygon(x_points, y_points, 4);
		
	}

}