package io.github.haminic.graphingcalculator.gui.graph;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

import io.github.haminic.graphingcalculator.equation.utils.EquationUpdateEvent;
import io.github.haminic.graphingcalculator.equation.utils.EquationUpdateListener;
import io.github.haminic.graphingcalculator.graph.Graph;
import io.github.haminic.graphingcalculator.graph.GraphManager;
import io.github.haminic.graphingcalculator.graph.Point;
import io.github.haminic.graphingcalculator.gui.utils.FontUtils;
import io.github.haminic.graphingcalculator.gui.utils.NumberUtils;

public class GraphCanvas extends Canvas implements EquationUpdateListener {
	
	private final int GRID_WIDTH_SUBDIVISION = 15;
	private final int LABEL_FONT_SIZE = 12;
	private final int LABEL_OFFSET = 5;
	private final GraphManager graphManager;
	
	public GraphCanvas(double width, GraphManager graphManager) {
		super(width, width*graphManager.getRangeY()/graphManager.getRangeX());
		this.graphManager = graphManager;
	    initEmptyGrid();
	}
	
	private Point getPointOnCanvas(Point graphPoint) {
		return new Point((graphPoint.x - graphManager.getMinX())*this.getWidth()/graphManager.getRangeX(), 
				this.getHeight() - (graphPoint.y - graphManager.getMinY())*this.getHeight()/graphManager.getRangeY());
	}
	
	private Point getPointOnCanvas(double x, double y) {
		return new Point((x - graphManager.getMinX())*this.getWidth()/graphManager.getRangeX(), 
				this.getHeight() - (y - graphManager.getMinY())*this.getHeight()/graphManager.getRangeY());
	}
	
//	a and b are points on the graph, not the canvas.
	private void drawLine(Point a, Point b, Color color) {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(color);
	    gc.setLineWidth(2.5);
	    Point canvasA = getPointOnCanvas(a);
	    Point canvasB = getPointOnCanvas(b);
	    gc.strokeLine(canvasA.x, canvasA.y, canvasB.x, canvasB.y);
	}
	
	private void drawGraph(Graph graph) {
		if (!graph.isVisible()) return;
		List<List<Point>> curves = graph.getCurves();
	   
		for (List<Point> curve : curves) {
			if (curve.size() <= 1) continue;
			for (int i = 0; i < curve.size()-1; i++) {
				drawLine(curve.get(i), curve.get(i+1), graph.getColor());
			}
		}
	   
	}
    
    private void drawAllGraphs() {
    	graphManager.getGraphs().forEach(graph -> {
			drawGraph(graph);
		});
    }

	@Override
	public void onEquationUpdate(EquationUpdateEvent event) {
		initEmptyGrid();
		drawAllGraphs();
	}

	private void initEmptyGrid() {
	    GraphicsContext gc = getGraphicsContext2D();
	    gc.clearRect(0, 0, getWidth(), getHeight());

	    double minX = graphManager.getMinX();
	    double maxX = graphManager.getMaxX();
	    double minY = graphManager.getMinY();
	    double maxY = graphManager.getMaxY();
	    double rangeX = graphManager.getRangeX();
	    double step = NumberUtils.getNiceStep(rangeX/GRID_WIDTH_SUBDIVISION);
	    List<Double> latticeX = new ArrayList<Double>();
	    List<Double> latticeY = new ArrayList<Double>();
	    gc.setStroke(Color.LIGHTGRAY);
	    gc.setLineWidth(1);

	    for (double x = Math.ceil(minX/step)*step; x <= maxX; x += step) {
	        latticeX.add(x);
	        Point start = getPointOnCanvas(x, minY);
	        Point end = getPointOnCanvas(x, maxY);
	        gc.strokeLine(start.x, start.y, end.x, end.y);
	    }
	    for (double y = Math.ceil(minY/step)*step; y <= maxY; y += step) {
	        latticeY.add(y);
	        Point start = getPointOnCanvas(minX, y);
	        Point end = getPointOnCanvas(maxX, y);
	        gc.strokeLine(start.x, start.y, end.x, end.y);
	    }

	    gc.setStroke(Color.BLACK);
	    gc.setLineWidth(2);

	    double axisX = (0 >= minX && 0 <= maxX) ? 0 : (Math.abs(minX) < Math.abs(maxX) ? minX : maxX);
	    Point xAxisStart = getPointOnCanvas(axisX, minY);
	    Point xAxisEnd = getPointOnCanvas(axisX, maxY);
	    gc.strokeLine(xAxisStart.x, xAxisStart.y, xAxisEnd.x, xAxisEnd.y);
	    double axisY = (0 >= minY && 0 <= maxY) ? 0 : (Math.abs(minY) < Math.abs(maxY) ? minY : maxY);
	    Point yAxisStart = getPointOnCanvas(minX, axisY);
	    Point yAxisEnd = getPointOnCanvas(maxX, axisY);
	    gc.strokeLine(yAxisStart.x, yAxisStart.y, yAxisEnd.x, yAxisEnd.y);
	    
	    gc.setFill(Color.BLACK);
	    gc.setFont(FontUtils.reg(LABEL_FONT_SIZE));
	    boolean axisXTooRight = getPointOnCanvas(axisX, 0).x > getWidth() - 4*LABEL_FONT_SIZE;
	    boolean axisYTooTop = getPointOnCanvas(0, axisY).y < 2*LABEL_FONT_SIZE;
	    gc.setTextAlign(axisXTooRight ? TextAlignment.RIGHT : TextAlignment.LEFT);
	    gc.setTextBaseline(axisYTooTop ? VPos.TOP : VPos.BASELINE);
	    int signX = axisXTooRight ? -1 : 1;
	    int signY = axisYTooTop ? 1 : -1;
	    
	    latticeX.forEach(x -> {
	        Point labelPos = getPointOnCanvas(x, axisY);
	        gc.fillText(NumberUtils.formatGraphLabel(x, step), labelPos.x + signX*LABEL_OFFSET, labelPos.y + signY*LABEL_OFFSET);
	    });
	    latticeY.forEach(y -> {
	        Point labelPos = getPointOnCanvas(axisX, y);
	        gc.fillText(NumberUtils.formatGraphLabel(y, step), labelPos.x + signX*LABEL_OFFSET, labelPos.y + signY*LABEL_OFFSET);
	    });
	}

}