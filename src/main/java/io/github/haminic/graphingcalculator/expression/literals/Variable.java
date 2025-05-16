package io.github.haminic.graphingcalculator.expression.literals;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Variable extends Expression {

	@Override
	public double eval(SymbolManager symbolManager) {
		return Double.NaN;
	}

	@Override
	public double eval(double x, SymbolManager symbolManager) {
		return x;
	}
	
	@Override
	public String toString() {
		return "x";
	}

}
