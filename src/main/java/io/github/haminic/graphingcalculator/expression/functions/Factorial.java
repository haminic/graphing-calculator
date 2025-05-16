package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Factorial extends UnaryExpression {

	public Factorial(Expression arg) {
		super(arg, "fact");
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
	    if (a < 0 || a != Math.floor(a)) return Double.NaN;
	    double result = 1;
	    for (int i = 2; i <= (int) a; i++) {
	        result *= i;
	        if (Double.isInfinite(result)) {
	            return Double.POSITIVE_INFINITY;
	        }
	    }
	    return result;
	}
	
}
