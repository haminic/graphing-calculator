package io.github.haminic.graphingcalculator.expression.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public abstract class Expression {
	
	public static Expression NULL = new Expression() {
		@Override
		public double eval(double x, SymbolManager symbolManager) { return Double.NaN; }
		@Override
		public double eval(SymbolManager symbolManager) { return Double.NaN; }
	};
	
	public abstract double eval(SymbolManager symbolManager);
	
	public abstract double eval(double x, SymbolManager symbolManager);

}
