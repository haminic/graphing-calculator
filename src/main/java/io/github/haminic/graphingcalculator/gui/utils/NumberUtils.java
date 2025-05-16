package io.github.haminic.graphingcalculator.gui.utils;

public class NumberUtils {
	
	public static double getNiceStep(double x) {
        double exp = Math.floor(Math.log10(x));
        double frac = x / Math.pow(10, exp);

        double niceFrac;
        if (frac <= 1) niceFrac = 1;
        else if (frac <= 2) niceFrac = 2;
        else if (frac <= 5) niceFrac = 5;
        else niceFrac = 10;

        return niceFrac * Math.pow(10, exp);
    }
	
	public static int getDecimalPlaces(double value) {
		int places = (int) Math.floor(Math.log10(value));
		if (places >= 0) return 0;
		return -places;
	}
	
	public static String formatGraphLabel(double value, double step) {
		if (Math.abs(value) < step/100) value = 0;
		int places = getDecimalPlaces(step);
		if (places > 5) {
			if (value == 0) return "0";
			return String.format("%.0e", value);
		}
	    return String.format("%." + places + "f", value);
	}
	
	public static String formatResult(double value) {
        if (!Double.isFinite(value)) return String.valueOf(value);
        double abs = Math.abs(value);
        if (abs < 2e-16) return "0";
        if (abs >= 1e6 || abs < 1e-4) {
        	String sci = String.format("%.6e", value);
        	sci = sci.replaceAll("\\.0*e", "e");
            sci = sci.replaceAll("(\\.\\d*?[1-9])0+e", "$1e");
            sci = sci.replaceAll("e\\+?", "e");
            return sci;
        }
        String result = String.format("%.12f", value);
        result = result.replaceAll("0+$", "");
        result = result.replaceAll("\\.$", "");
        return result;
    }

}
