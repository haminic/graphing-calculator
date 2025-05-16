package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Tangent extends UnaryExpression {

	public Tangent(Expression arg) {
		super(arg, "tan");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.tan(a);
	}
	
}
