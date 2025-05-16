package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class SquareRoot extends UnaryExpression {

	public SquareRoot(Expression arg) {
		super(arg, "sqrt");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.sqrt(a);
	}

}
