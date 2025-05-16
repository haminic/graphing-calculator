package io.github.haminic.graphingcalculator.expression.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public abstract class UnaryExpression extends Expression {
	
	protected final String symbol;
	protected final Expression arg;
	
	public UnaryExpression(Expression arg, String symbol) {
		this.arg = arg;
		this.symbol = symbol;
	}
	
	protected abstract double operate(double a, SymbolManager symbolManager);

	@Override
	public double eval(SymbolManager symbolManager) {
		double argValue = arg.eval(symbolManager);
		if (Double.isNaN(argValue)) return Double.NaN;
		return operate(argValue, symbolManager);
	}

	@Override
	public double eval(double x, SymbolManager symbolManager) {
		double argValue = arg.eval(x, symbolManager);
		if (Double.isNaN(argValue)) return Double.NaN;
		return operate(argValue, symbolManager);
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", symbol, arg);
	}

}
