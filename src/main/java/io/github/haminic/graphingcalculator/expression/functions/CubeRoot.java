package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class CubeRoot extends UnaryExpression {

	public CubeRoot(Expression arg) {
		super(arg, "cbrt");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return Math.cbrt(a);
	}

}
