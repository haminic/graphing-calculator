package io.github.haminic.graphingcalculator.expression.literals;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public class Constant extends Expression {
	
	private final double value;
	private final String name;
	
	public Constant(double value) {
		this.value = value;
		this.name = null;
	}
	
	public Constant(double value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public double eval(SymbolManager symbolManager) {
		return value;
	}

	@Override
	public double eval(double x, SymbolManager symbolManager) {
		return value;
	}
	
	@Override
	public String toString() {
		return (name == null)? Double.toString(value) : name;
	}
	
}
