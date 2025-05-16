package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Cosine extends UnaryExpression {

	public Cosine(Expression arg) {
		super(arg, "cos");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.cos(a);
	}

}
