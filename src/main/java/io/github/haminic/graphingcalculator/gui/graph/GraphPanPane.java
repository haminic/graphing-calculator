package io.github.haminic.graphingcalculator.gui.graph;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GraphPanPane extends StackPane {
	
	private static final double BUTTON_SIZE = 150;

	private Runnable onPanLeft;
	private Runnable onPanRight;
	private Runnable onPanUp;
	private Runnable onPanDown;

    public GraphPanPane() {
        GradientBorderPane gradientBorderPane = new GradientBorderPane();
        BorderPane buttonsPane = new BorderPane();

        Button leftButton = makeEdgeButton(() -> {
            if (onPanLeft != null) onPanLeft.run();
        }, () -> gradientBorderPane.setLeftVisible(true), () -> gradientBorderPane.setLeftVisible(false));
        leftButton.setPrefWidth(BUTTON_SIZE);
        leftButton.setMaxHeight(Double.MAX_VALUE);
        leftButton.setFocusTraversable(false);
        buttonsPane.setLeft(leftButton);

        Button rightButton = makeEdgeButton(() -> {
            if (onPanRight != null) onPanRight.run();
        }, () -> gradientBorderPane.setRightVisible(true), () -> gradientBorderPane.setRightVisible(false));
        rightButton.setPrefWidth(BUTTON_SIZE);
        rightButton.setMaxHeight(Double.MAX_VALUE);
        rightButton.setFocusTraversable(false);
        buttonsPane.setRight(rightButton);

        Button topButton = makeEdgeButton(() -> {
            if (onPanUp != null) onPanUp.run();
        }, () -> gradientBorderPane.setTopVisible(true), () -> gradientBorderPane.setTopVisible(false));
        topButton.setPrefHeight(BUTTON_SIZE);
        topButton.setMaxWidth(Double.MAX_VALUE);
        topButton.setFocusTraversable(false);
        buttonsPane.setTop(topButton);
        
        Button bottomButton = makeEdgeButton(() -> {
            if (onPanDown != null) onPanDown.run();
        }, () -> gradientBorderPane.setBottomVisible(true), () -> gradientBorderPane.setBottomVisible(false));
        bottomButton.setPrefHeight(BUTTON_SIZE);
        bottomButton.setMaxWidth(Double.MAX_VALUE);
        bottomButton.setFocusTraversable(false);
        buttonsPane.setBottom(bottomButton);

        getChildren().addAll(gradientBorderPane, buttonsPane);
    }

    private Button makeEdgeButton(Runnable onClick, Runnable onEnter, Runnable onExit) {
        Button button = new Button();
        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnMouseClicked(event -> onClick.run());
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> onEnter.run());
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> onExit.run());
        button.setOpacity(0);
        return button;
    }

	public void setOnPanLeft(Runnable onPanLeft) {
		this.onPanLeft = onPanLeft;
	}

	public void setOnPanRight(Runnable onPanRight) {
		this.onPanRight = onPanRight;
	}

	public void setOnPanUp(Runnable onPanUp) {
		this.onPanUp = onPanUp;
	}

	public void setOnPanDown(Runnable onPanDown) {
		this.onPanDown = onPanDown;
	}
}