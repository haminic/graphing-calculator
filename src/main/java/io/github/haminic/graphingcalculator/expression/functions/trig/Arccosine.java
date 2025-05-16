package io.github.haminic.graphingcalculator.expression.functions.trig;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Arccosine extends UnaryExpression {

	public Arccosine(Expression arg) {
		super(arg, "arccos");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.acos(a);
	}

}