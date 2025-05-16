package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Ceiling extends UnaryExpression {

	public Ceiling(Expression arg) {
		super(arg, "ceil");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.ceil(a);
	}

}