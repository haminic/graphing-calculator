package io.github.haminic.graphingcalculator.gui.equation;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class EquationContainer extends ScrollPane {
	
	private final InputPane inputPane = new InputPane();
	private final VBox equations = new VBox();
	
	public EquationContainer() {
		equations.setPadding(new Insets(10));
		equations.setSpacing(10);
		equations.getChildren().add(inputPane);
		
		setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		setFitToWidth(true);
		setContent(equations);
	}

	public InputPane getInputPane() {
		return inputPane;
	}
	
	public VBox getEquations() {
		return equations;
	}

}
