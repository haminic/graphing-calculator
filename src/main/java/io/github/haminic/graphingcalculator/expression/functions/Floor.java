package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Floor extends UnaryExpression {

	public Floor(Expression arg) {
		super(arg, "floor");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.floor(a);
	}

}