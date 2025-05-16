package io.github.haminic.graphingcalculator.expression.functions;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.base.UnaryExpression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class UserFunction extends UnaryExpression {
	
	private final String identifier;
	
	public UserFunction(String identifier, Expression arg) {
		super(arg, identifier);
		this.identifier = identifier;
	}

	@Override
	protected double operate(double a, SymbolManager symbolManager) {
		return symbolManager.getFunctionExpression(identifier).eval(a, symbolManager);
	}
	
}
