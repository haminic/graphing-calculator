package io.github.haminic.graphingcalculator.expression.operations;

import io.github.haminic.graphingcalculator.expression.base.BinaryExpression;
import io.github.haminic.graphingcalculator.expression.base.Expression;

public class Addition extends BinaryExpression {

	public Addition(Expression left, Expression right) {
		super(left, right, "+");
	}

	@Override
	protected double operate(double a, double b) {
		return a + b;
	}
	
}
