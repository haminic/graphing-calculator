package io.github.haminic.graphingcalculator.gui.utils;

import javafx.scene.text.Font;

public class FontUtils {
	
    private static final String REGULAR_FONT_PATH = "/fonts/NotoSans-Regular.ttf";
    private static final String MONO_FONT_PATH = "/fonts/JetBrainsMono-Regular.ttf";
    
    public static Font reg(double size) {
        return Font.loadFont(FontUtils.class.getResource(REGULAR_FONT_PATH).toString(), size);
    }

    public static Font mono(double size) {
        return Font.loadFont(FontUtils.class.getResource(MONO_FONT_PATH).toString(), size);
    }

}