package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Sine extends UnaryExpression {

	public Sine(Expression arg) {
		super(arg, "sin");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.sin(a);
	}

}
