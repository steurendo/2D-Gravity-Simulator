public class Point
{
	public double x;
	public double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Point() { this(0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public static Point fromPoint(java.awt.Point point) { return new Point(point.getX(), point.getY()); }
}