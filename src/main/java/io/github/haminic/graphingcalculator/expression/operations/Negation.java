package io.github.haminic.graphingcalculator.expression.operations;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Negation extends UnaryExpression {

	public Negation(Expression arg) {
		super(arg, "-");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return -a;
	}

	@Override
	public String toString() {
	    return String.format("(-%s)", arg);
	}
}
