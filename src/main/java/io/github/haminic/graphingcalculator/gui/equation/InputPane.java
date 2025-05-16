package io.github.haminic.graphingcalculator.gui.equation;

import java.util.function.Consumer;

import io.github.haminic.graphingcalculator.gui.utils.FontUtils;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InputPane extends VBox {
	
	private static final double PANE_WIDTH = 300;
	
	private final TextField inputField = new TextField();
	private final Text errorText = new Text();
	
	private Consumer<String> onInputFieldUpdate;
	
	public InputPane() {
		inputField.setPromptText("Enter equation...");
		
		inputField.setOnAction(event -> {
			if (!inputField.getText().isBlank()) onInputFieldUpdate.accept(inputField.getText());
			else setError("");
		});
		inputField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused && !inputField.getText().isBlank()) onInputFieldUpdate.accept(inputField.getText());
			else if (inputField.getText().isBlank()) setError("");
		});
		inputField.setFont(FontUtils.mono(12));
		
		errorText.setFont(FontUtils.reg(10));
		errorText.setFill(Color.CRIMSON);
		
		setSpacing(2);
		setPadding(new Insets(10));
		setPrefWidth(PANE_WIDTH);
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		setBackground(new Background(new BackgroundFill(
				Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY
				)));
		getChildren().addAll(inputField, errorText);
	}
	
	public void setOnInputFieldUpdate(Consumer<String> onInputFieldUpdate) {
		this.onInputFieldUpdate = onInputFieldUpdate;
	}
	
	public void setError(String error) {
		errorText.setText(error);
	}
	
	public void clear() {
		errorText.setText("");
		inputField.setText("");
	}
	
}
