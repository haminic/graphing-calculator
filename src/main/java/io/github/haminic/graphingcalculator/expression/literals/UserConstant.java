package io.github.haminic.graphingcalculator.expression.literals;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class UserConstant extends Expression {
	
	private final String identifier;

	public UserConstant(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public double eval(SymbolManager symbolManager) {
		return symbolManager.getConstantExpression(identifier).eval(symbolManager);
	}

	@Override
	public double eval(double x, SymbolManager symbolManager) {
		return symbolManager.getConstantExpression(identifier).eval(symbolManager);
	}

}
