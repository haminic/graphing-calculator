package io.github.haminic.graphingcalculator.gui.misc;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import io.github.haminic.graphingcalculator.gui.utils.FontUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public class HelpAlert extends Alert {
	
	private static final String guideURL = "/guide/guide.txt";
	
	public HelpAlert() {
		super(AlertType.INFORMATION);
		setTitle("Help");
		setHeaderText("Graphing Calculator Guide");
		
		TextArea guideText = new TextArea();
		guideText.setFont(FontUtils.mono(12));
		guideText.setEditable(false);
		guideText.setWrapText(true);
		guideText.setPrefWidth(550);
		guideText.setPrefHeight(400);
		
		try {
			URL url = getClass().getResource(guideURL);
			if (url != null) {
				try (var inputStream = getClass().getResourceAsStream(guideURL)) {
					if (inputStream != null) {
						String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
						guideText.setText(content);
					} else {
						guideText.setText("Error: Unable to load guide from stream.");
					}
				}
			} else {
				guideText.setText("Error: guide.txt not found.");
			}
		} catch (Exception e) {
			guideText.setText("Error loading guide: " + e.getMessage());
		}
		
		getDialogPane().setContent(guideText);
		
		Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
		okButton.setFont(FontUtils.reg(12));
		okButton.setDefaultButton(false);
	}

}
