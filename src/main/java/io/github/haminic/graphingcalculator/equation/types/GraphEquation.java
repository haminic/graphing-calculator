package io.github.haminic.graphingcalculator.equation.types;

import java.util.ArrayList;
import java.util.List;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Graphable;
import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.graph.Graph;
import io.github.haminic.graphingcalculator.graph.GraphManager;
import io.github.haminic.graphingcalculator.graph.Point;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class GraphEquation extends Equation implements Graphable {

	private final Graph graph = new Graph();

	public GraphEquation(String rawText, List<Token> tokens, SymbolManager symbolManager) throws ParserException {
		super(rawText, tokens, symbolManager);
	}
	
//	For testing only -- avoids having to tokenize from outside the class.
	public GraphEquation(String text, String identifier, SymbolManager symbolManager) {
		super(text, text, symbolManager);
	}

	@Override
	public void update(EquationManager equationManager) {
		graph(equationManager.getSymbolManager(), equationManager.getGraphManager());
	}
	
	@Override
	public void setNone() {
		getGraph().setCurves(new ArrayList<List<Point>>());
	}

	@Override
	public void addGraphTo(GraphManager graphManager) {
		graphManager.addGraph(this.getGraph());
	}

	@Override
	public void removeGraphFrom(GraphManager graphManager) {
		graphManager.removeGraph(this.getGraph());
	}

	@Override
	public Graph getGraph() {
		return graph;
	}

//	Only add points that are in bounds and if it's finite and is only *just* out of bounds, still include it for continuity.
	@Override
	public void graph(SymbolManager symbolManager, GraphManager graphManager) {
		graph.clear();
		List<Point> currentCurve = new ArrayList<Point>();
		double minX = graphManager.getMinX();
		double maxX = graphManager.getMaxX();
		double step = graphManager.getRangeX() / graphManager.getResolution();
		Point lastPoint = null;
		boolean lastWasValid = false;
		boolean lastWasFinite = true;

		for (double x = minX; x <= maxX; x += step) {
			double y = expression.eval(x, symbolManager);
			Point currentPoint = new Point(x, y);

			if (!Double.isFinite(y)) {
				if (!currentCurve.isEmpty()) {
					graph.addCurve(currentCurve);
					currentCurve = new ArrayList<Point>();
				}
				lastWasFinite = false;
				lastWasValid = false;
				lastPoint = null;
				continue;
			}

			boolean inBounds = y >= graphManager.getMinY() && y <= graphManager.getMaxY();

			if (inBounds) {
				if (!lastWasValid && lastPoint != null && lastWasFinite) currentCurve.add(lastPoint);
				currentCurve.add(currentPoint);
			} else {
				if (lastWasValid) {
					currentCurve.add(currentPoint);
					graph.addCurve(currentCurve);
					currentCurve = new ArrayList<Point>();
				}
			}

			lastWasValid = inBounds;
			lastWasFinite = true;
			lastPoint = currentPoint;
		}

		if (!currentCurve.isEmpty()) graph.addCurve(currentCurve);
	}
	
}
