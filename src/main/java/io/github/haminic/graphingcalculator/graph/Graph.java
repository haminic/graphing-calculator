package io.github.haminic.graphingcalculator.graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Graph {
	
	private List<List<Point>> curves;
	private Color color = Color.TEAL;
	private boolean isVisible = true;
	
	public Graph() {
		this.curves = new ArrayList<List<Point>>();
	}
	
	public void clear()	{
		curves = new ArrayList<List<Point>>();
	}
	
	public void addCurve(List<Point> curve) {
		curves.add(curve);
	}

	public List<List<Point>> getCurves() {
		return curves;
	}

	public void setCurves(List<List<Point>> curves) {
		this.curves = curves;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
