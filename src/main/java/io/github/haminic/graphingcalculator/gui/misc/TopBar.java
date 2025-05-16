package io.github.haminic.graphingcalculator.gui.misc;

import io.github.haminic.graphingcalculator.equation.utils.EquationController;
import io.github.haminic.graphingcalculator.graph.Point;
import io.github.haminic.graphingcalculator.gui.utils.FontUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class TopBar extends BorderPane {

    public TopBar(EquationController equationController) {
        Button helpButton = createTopBarButton("Help");
        helpButton.setOnAction(event -> {
        	HelpAlert helpAlert = new HelpAlert();
            helpAlert.showAndWait();
        });
        
        Button clearButton = createTopBarButton("Clear");
        clearButton.setOnAction(event -> {
        	equationController.removeAllEquation();
        });
        
        Button toOriginButton = createTopBarButton("To Origin");
        toOriginButton.setOnAction(event -> {
        	equationController.getEquationManager().getGraphManager().setCenter(new Point(0, 0));
        	equationController.getEquationManager().updateAllAndNotify();
        });
        
        Button resetZoomButton = createTopBarButton("Reset Zoom");
        resetZoomButton.setOnAction(event -> {
        	equationController.getEquationManager().getGraphManager().setScaleX(10);
        	equationController.getEquationManager().updateAllAndNotify();
        });

        HBox leftBox = new HBox(10, helpButton, clearButton);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        HBox rightBox = new HBox(10, toOriginButton, resetZoomButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        setPadding(new Insets(5));
        setBackground(new Background(new BackgroundFill(
				Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY
				)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null)));
        setLeft(leftBox);
        setRight(rightBox);
    }

    private Button createTopBarButton(String text) {
        Button button = new Button(text);

        button.setFont(FontUtils.reg(12));
        button.setPadding(new Insets(1, 8, 1, 8));
        button.setPrefHeight(24);

        button.setBackground(Background.EMPTY);
        button.setBorder(Border.EMPTY);

        Background normalBg = Background.EMPTY;
        Background hoverBg = new Background(new BackgroundFill(
            Color.web("#e0e0e0"), new CornerRadii(5), Insets.EMPTY
        ));

        button.setOnMouseEntered(event -> button.setBackground(hoverBg));
        button.setOnMouseExited(event -> button.setBackground(normalBg));
        button.setFocusTraversable(false);
        
        return button;
    }

}