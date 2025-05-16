package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Arcsine extends UnaryExpression {

	public Arcsine(Expression arg) {
		super(arg, "arcsin");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.asin(a);
	}

}
