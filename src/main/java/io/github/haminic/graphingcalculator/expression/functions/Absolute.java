package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Absolute extends UnaryExpression {

	public Absolute(Expression arg) {
		super(arg, "abs");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.abs(a);
	}

}
