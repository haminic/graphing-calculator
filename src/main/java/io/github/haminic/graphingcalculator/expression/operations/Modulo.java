package io.github.haminic.graphingcalculator.expression.operations;

import io.github.haminic.graphingcalculator.expression.base.BinaryExpression;
import io.github.haminic.graphingcalculator.expression.base.Expression;

public class Modulo extends BinaryExpression {

	public Modulo(Expression left, Expression right) {
		super(left, right, "%");
	}

	@Override
	protected double operate(double a, double b) {
		return a % b;
	}
	
}
