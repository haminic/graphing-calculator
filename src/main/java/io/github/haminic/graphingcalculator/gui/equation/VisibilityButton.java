package io.github.haminic.graphingcalculator.gui.equation;

import java.util.function.Consumer;

import io.github.haminic.graphingcalculator.gui.utils.IconUtils;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class VisibilityButton extends Button {

	private boolean isGraphVisible = true;
	private final ImageView eyeIcon = new ImageView();
	
	private Consumer<Boolean> onVisibilityToggled;
	
	public VisibilityButton() {
		setGraphic(eyeIcon);
		eyeIcon.setFitWidth(15);
		eyeIcon.setFitHeight(15);
		setGraphVisible(isGraphVisible);
		setOnAction(event -> {
			setGraphVisible(!isGraphVisible);
			onVisibilityToggled.accept(isGraphVisible);
		});
	}
	
	public boolean isGraphVisible() {
		return isGraphVisible;
	}

	public void setGraphVisible(boolean isGraphVisible) {
		this.isGraphVisible = isGraphVisible;
		eyeIcon.setImage(isGraphVisible ? IconUtils.EYE : IconUtils.EYE_OFF);
	}

	public void setOnVisibilityToggled(Consumer<Boolean> onVisibilityToggled) {
		this.onVisibilityToggled = onVisibilityToggled;
	}

}
