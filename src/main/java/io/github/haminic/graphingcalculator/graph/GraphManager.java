package io.github.haminic.graphingcalculator.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphManager {
	
	private Point center;
	private double scaleX;
	private double scaleY;
	private final double aspectRatio; // x/y
	private int resolution; // Quality of graph -- used differently based on the Graphable.
	
	private final List<Graph> graphs = new ArrayList<Graph>();
	
	public GraphManager(Point center, double scaleX, double aspectRatio, int resolution) {
		this.aspectRatio = aspectRatio;
		this.resolution = resolution;
		this.setCenter(center);
		this.setScaleX(scaleX);
	}
	
//	For testing only -- initializes some values.
	public GraphManager() {
		this.aspectRatio = 5/4;
		this.resolution = 100;
		this.setCenter(new Point(0, 0));
		this.setScaleX(5);
	}
	
	public void translate(Point translation) {
		getCenter().translate(translation);
	}
	
	public void addGraph(Graph graph) {
		if (graphs.contains(graph)) return; //TODO: Throw error
		this.graphs.add(graph);
	}
	
	public void removeGraph(Graph graph) {
		this.graphs.remove(graph);
	}

	public List<Graph> getGraphs() {
		return this.graphs;
	}

	public double getMinX() {
		return getCenter().x - getScaleX();
	}
	
	public double getMaxX() {
		return getCenter().x + getScaleX();
	}
	
	public double getRangeX() {
		return getMaxX() - getMinX();
	}
	
	public double getMinY() {
		return getCenter().y - getScaleY();
	}
	
	public double getMaxY() {
		return getCenter().y + getScaleY();
	}
	
	public double getRangeY() {
		return getMaxY() - getMinY();
	}
	
	public int getResolution() {
		return resolution;
	}
	
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		if (scaleX <= 0) this.scaleX = 1;
		this.scaleX = scaleX;
		this.scaleY = scaleX/aspectRatio;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		if (scaleY <= 0) this.scaleY = 1;
		this.scaleY = scaleY;
		this.scaleX = scaleY*aspectRatio;
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	
	public double getAspectRatio() {
		return aspectRatio;
	}

}
