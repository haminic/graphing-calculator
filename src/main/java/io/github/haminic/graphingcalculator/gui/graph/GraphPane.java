package io.github.haminic.graphingcalculator.gui.graph;

import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.graph.GraphManager;
import io.github.haminic.graphingcalculator.graph.Point;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GraphPane extends StackPane {
	
	private static final double ZOOM_IN_RATIO = 0.75;
	private static final double PAN_RATIO = 0.25;
	
	public GraphPane(GraphCanvas graphCanvas, EquationManager equationManager) {
		
		GraphPanPane graphPanPane = new GraphPanPane();
		
		graphPanPane.setOnPanLeft(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.translate(new Point(-graphManager.getScaleX()*PAN_RATIO, 0));
			equationManager.updateAllAndNotify();
		});

		graphPanPane.setOnPanRight(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.translate(new Point(graphManager.getScaleX()*PAN_RATIO, 0));
			equationManager.updateAllAndNotify();
		});

		graphPanPane.setOnPanUp(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.translate(new Point(0, graphManager.getScaleX()*PAN_RATIO));
			equationManager.updateAllAndNotify();
		});

		graphPanPane.setOnPanDown(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.translate(new Point(0, -graphManager.getScaleX()*PAN_RATIO));
			equationManager.updateAllAndNotify();
		});

		GraphZoomPane graphZoomPane = new GraphZoomPane();
		
		graphZoomPane.setOnZoomIn(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.setScaleX(graphManager.getScaleX()*ZOOM_IN_RATIO);
			equationManager.updateAllAndNotify();
		});
		
		graphZoomPane.setOnZoomOut(() -> {
			GraphManager graphManager = equationManager.getGraphManager();
			graphManager.setScaleX(graphManager.getScaleX()/ZOOM_IN_RATIO);
			equationManager.updateAllAndNotify();
		});
		
		BorderPane graphControlPane = new BorderPane();
		graphControlPane.setBottom(graphZoomPane);
		graphControlPane.setPadding(new Insets(20));
		BorderPane.setAlignment(graphZoomPane, Pos.BOTTOM_RIGHT);
		graphControlPane.setPickOnBounds(false);
		
		getChildren().addAll(graphCanvas, graphPanPane, graphControlPane);
	}

}
