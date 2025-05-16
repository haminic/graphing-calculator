package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Exponential extends UnaryExpression {

	public Exponential(Expression arg) {
		super(arg, "exp");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.exp(a);
	}
	
}
