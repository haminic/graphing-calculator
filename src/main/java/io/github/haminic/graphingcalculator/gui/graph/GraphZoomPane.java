package io.github.haminic.graphingcalculator.gui.graph;

import io.github.haminic.graphingcalculator.gui.utils.IconUtils;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GraphZoomPane extends HBox {
	
	private Runnable onZoomIn;
	private Runnable onZoomOut;
	
	public GraphZoomPane() {
		Button zoomInButton = new Button();
		zoomInButton.setOnAction(event -> {
			if (onZoomIn != null) onZoomIn.run();
		});
		ImageView zoomInIcon = new ImageView(IconUtils.PLUS);
		zoomInIcon.setFitWidth(15);
		zoomInIcon.setFitHeight(15);
		zoomInButton.setGraphic(zoomInIcon);
		
		Button zoomOutButton = new Button();
		zoomOutButton.setOnAction(event -> {
			if (onZoomOut != null) onZoomOut.run();
		});
		ImageView zoomOutIcon = new ImageView(IconUtils.MINUS);
		zoomOutIcon.setFitWidth(15);
		zoomOutIcon.setFitHeight(15);
		zoomOutButton.setGraphic(zoomOutIcon);
		
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		setSpacing(10);
		setPadding(new Insets(10));
		setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		setBackground(new Background(new BackgroundFill(
				Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY
				)));
		setMaxWidth(Region.USE_PREF_SIZE);
	    setMinWidth(Region.USE_PREF_SIZE); 
		getChildren().addAll(zoomInButton, zoomOutButton);
	}
	
	public void setOnZoomIn(Runnable onZoomIn) {
		this.onZoomIn = onZoomIn;
	}
	
	public void setOnZoomOut(Runnable onZoomOut) {
		this.onZoomOut = onZoomOut;
	}
	
}
