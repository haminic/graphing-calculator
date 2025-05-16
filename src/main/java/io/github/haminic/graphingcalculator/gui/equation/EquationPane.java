package io.github.haminic.graphingcalculator.gui.equation;

import java.util.function.Consumer;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Evaluable;
import io.github.haminic.graphingcalculator.equation.base.Graphable;
import io.github.haminic.graphingcalculator.gui.utils.FontUtils;
import io.github.haminic.graphingcalculator.gui.utils.IconUtils;
import io.github.haminic.graphingcalculator.gui.utils.NumberUtils;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EquationPane extends VBox {
	
	private static final double PANE_WIDTH = 300;
	
	private Equation equation;
	
	private final TextField equationField = new TextField();
	private final Text errorText = new Text();
	
	private final Text resultText = new Text();
	private final VisibilityButton visibilityButton = new VisibilityButton();
	private final ColorPickerButton colorPickerButton = new ColorPickerButton();
	
	private Consumer<String> onEquationFieldUpdate;
	private Runnable onGraphUpdate;
	private Runnable onDelete;
	
	public EquationPane(Equation equation) {
		setEquation(equation);
		
		equationField.setText(equation.getRawText());
		equationField.setOnAction(event -> {
			if (!equationField.getText().equals(getEquation().getRawText())) onEquationFieldUpdate.accept(equationField.getText());
		});
		equationField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				if (!equationField.getText().equals(getEquation().getRawText())) onEquationFieldUpdate.accept(equationField.getText());
			}
		});
		equationField.setFont(FontUtils.mono(12));
		
		Button deleteButton = new Button();
		deleteButton.setOnAction(event -> {
			onDelete.run();
		});
		ImageView deleteIcon = new ImageView(IconUtils.TRASH);
		deleteIcon.setFitWidth(15);
		deleteIcon.setFitHeight(15);
		deleteButton.setGraphic(deleteIcon);
		
		visibilityButton.setGraphVisible(!(getEquation() instanceof Graphable graphable) || graphable.getGraph().isVisible());
		visibilityButton.setOnVisibilityToggled(isGraphVisible -> {
			if (!(getEquation() instanceof Graphable graphable)) return;
			graphable.getGraph().setVisible(isGraphVisible);
			onGraphUpdate.run();
		});

		colorPickerButton.setOnColorSelected(color -> {
			if (!(getEquation() instanceof Graphable graphable)) return;
			graphable.getGraph().setColor(color);
			onGraphUpdate.run();
		});
		
		errorText.setFont(FontUtils.reg(10));
		errorText.setFill(Color.CRIMSON);
		
		resultText.setFont(FontUtils.reg(12));
		
		HBox buttonsPane = new HBox();
		buttonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		buttonsPane.getChildren().addAll(deleteButton, visibilityButton, colorPickerButton);
		buttonsPane.setSpacing(10);
		
		VBox topRow = new VBox();
		topRow.getChildren().addAll(equationField, errorText);
		topRow.setSpacing(2);
		
		BorderPane bottomRow = new BorderPane();
		bottomRow.setLeft(resultText);
		bottomRow.setRight(buttonsPane);
		
		setPadding(new Insets(10));
		setSpacing(5);
		setPrefWidth(PANE_WIDTH);
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		setBackground(new Background(new BackgroundFill(
				Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY
				)));
		getChildren().addAll(topRow, bottomRow);
		
		updatePane();
	}

	public void updatePane() {
		if (equation instanceof Evaluable) {
			resultText.setText("= " + NumberUtils.formatResult(((Evaluable) equation).getResult()));
			resultText.setVisible(true);
		} else {
			resultText.setVisible(false);
		}
		if (equation instanceof Graphable) {
			visibilityButton.setVisible(true);
			colorPickerButton.setVisible(true);
		} else {
			visibilityButton.setVisible(false);
			colorPickerButton.setVisible(false);
		}
	}

	public void setEquation(Equation equation) {
		if (equation instanceof Graphable graphable) {
			graphable.getGraph().setColor(colorPickerButton.getSelectedColor());
			graphable.getGraph().setVisible(visibilityButton.isGraphVisible());
		}
		this.equation = equation;
	}
	
	public void setError(String error) {
		errorText.setText(error);
	}
	
	public Equation getEquation() {
		return equation;
	}
	
	public void setOnEquationFieldUpdate(Consumer<String> onEquationFieldUpdate) {
		this.onEquationFieldUpdate = onEquationFieldUpdate;
	}
	
	public void setOnDelete(Runnable onDelete) {
		this.onDelete = onDelete;
	}
	
	public void setOnGraphUpdate(Runnable onGraphUpdate) {
		this.onGraphUpdate = onGraphUpdate;
	}

}
