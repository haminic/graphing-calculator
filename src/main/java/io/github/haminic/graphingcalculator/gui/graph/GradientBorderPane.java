package io.github.haminic.graphingcalculator.gui.graph;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class GradientBorderPane extends StackPane {
	
	private static final double GRADIENT_LENGTH = 100;
	private static final Color GRADIENT_COLOR = Color.BLACK.deriveColor(0, 1, 1, 0.1);
	
	private final Region leftGradient = new Region();
	private final Region rightGradient = new Region();
	private final Region topGradient = new Region();
	private final Region bottomGradient = new Region();
	
	public GradientBorderPane() {
		
        leftGradient.setPrefWidth(GRADIENT_LENGTH);
        leftGradient.setVisible(false);
        leftGradient.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, GRADIENT_COLOR), new Stop(1, Color.TRANSPARENT)),
            CornerRadii.EMPTY, Insets.EMPTY
        )));
        BorderPane leftBorder = new BorderPane();
        leftBorder.setLeft(leftGradient);
        
        rightGradient.setPrefWidth(GRADIENT_LENGTH);
        rightGradient.setVisible(false);
        rightGradient.setBackground(new Background(new BackgroundFill(
            new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, GRADIENT_COLOR), new Stop(1, Color.TRANSPARENT)),
            CornerRadii.EMPTY, Insets.EMPTY
        )));
        BorderPane rightBorder = new BorderPane();
        rightBorder.setRight(rightGradient);

        topGradient.setPrefHeight(GRADIENT_LENGTH);
        topGradient.setVisible(false);
        topGradient.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, GRADIENT_COLOR), new Stop(1, Color.TRANSPARENT)),
            CornerRadii.EMPTY, Insets.EMPTY
        )));
        BorderPane topBorder = new BorderPane();
        topBorder.setTop(topGradient);

        bottomGradient.setPrefHeight(GRADIENT_LENGTH);
        bottomGradient.setVisible(false);
        bottomGradient.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, GRADIENT_COLOR), new Stop(1, Color.TRANSPARENT)),
            CornerRadii.EMPTY, Insets.EMPTY
        )));
        BorderPane bottomBorder = new BorderPane();
        bottomBorder.setBottom(bottomGradient);
        
        getChildren().addAll(leftBorder, rightBorder, topBorder, bottomBorder);
		
	}
	
	public void setLeftVisible(boolean leftVisible) {
		leftGradient.setVisible(leftVisible);
	}
	
	public void setRightVisible(boolean rightVisible) {
	    rightGradient.setVisible(rightVisible);
	}

	public void setTopVisible(boolean topVisible) {
	    topGradient.setVisible(topVisible);
	}

	public void setBottomVisible(boolean bottomVisible) {
	    bottomGradient.setVisible(bottomVisible);
	}

}
