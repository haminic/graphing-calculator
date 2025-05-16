package io.github.haminic.graphingcalculator.expression.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public abstract class BinaryExpression extends Expression {
	
	protected final String symbol;
	protected final Expression left;
	protected final Expression right;
	
	public BinaryExpression(Expression left, Expression right, String symbol) {
		this.left = left;
		this.right = right;
		this.symbol = symbol;
	}
	
	protected abstract double operate(double a, double b);

	@Override
	public double eval(SymbolManager symbolManager) {
		double leftValue = left.eval(symbolManager);
		double rightValue = right.eval(symbolManager);
		if (Double.isNaN(leftValue) || Double.isNaN(rightValue)) return Double.NaN;
		return operate(leftValue, rightValue);
	}
	
	@Override
	public double eval(double x, SymbolManager symbolManager) {
		double leftValue = left.eval(x, symbolManager);
		double rightValue = right.eval(x, symbolManager);
		if (Double.isNaN(leftValue) || Double.isNaN(rightValue)) return Double.NaN;
		return operate(leftValue, rightValue);
	}
	
	@Override
	public String toString() {
		return String.format("(%s %s %s)", left, symbol, right);
	}

}
