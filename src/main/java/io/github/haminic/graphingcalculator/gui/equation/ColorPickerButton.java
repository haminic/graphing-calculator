package io.github.haminic.graphingcalculator.gui.equation;

import java.util.List;
import java.util.function.Consumer;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorPickerButton extends MenuButton {

	private static final List<Color> COLOR_OPTIONS = List.of(
				Color.BLUEVIOLET, Color.ROYALBLUE, Color.DODGERBLUE, Color.TEAL, Color.GREEN, Color.GOLDENROD, Color.DARKORANGE, Color.CRIMSON, Color.DIMGRAY, Color.BLACK
			);	
	private static final Color INITIAL_COLOR = Color.TEAL;
	
	private final Rectangle colorRect;
	private Color selectedColor;
	private Consumer<Color> onColorSelected;

	public ColorPickerButton() {
		this.colorRect = new Rectangle(15, 15);
		setGraphic(colorRect);

		for (Color color : COLOR_OPTIONS) {
			Rectangle rect = new Rectangle(15, 15, color);
			MenuItem item = new MenuItem();
			item.setGraphic(rect);
			item.setOnAction(event -> {
				setSelectedColor(color);
				onColorSelected.accept(color);
			});
			getItems().add(item);
		}
		
		setSelectedColor(INITIAL_COLOR);
	}

	public void setSelectedColor(Color color) {
		this.selectedColor = color;
		colorRect.setFill(color);
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}
	
	public void setOnColorSelected(Consumer<Color> onColorSelected) {
		this.onColorSelected = onColorSelected;
	}


	
}