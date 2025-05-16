package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Logarithm extends UnaryExpression {

	public Logarithm(Expression arg) {
		super(arg, "log");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.log(a);
	}

}
