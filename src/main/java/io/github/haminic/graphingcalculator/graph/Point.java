package io.github.haminic.graphingcalculator.graph;

public class Point {
	
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void translate(Point translation) {
		this.x += translation.x;
		this.y += translation.y;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", x, y);
	}

}
