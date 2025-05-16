package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Cosecant extends UnaryExpression {

	public Cosecant(Expression arg) {
		super(arg, "csc");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return 1/Math.sin(a);
	}

}
