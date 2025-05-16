package io.github.haminic.graphingcalculator.equation.types;

import java.util.HashSet;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.utils.EquationManager;

public class NullEquation extends Equation {
	
	private final String errorMessage;

	public NullEquation(String rawText, String errorMessage) {
		super(rawText, null);
		setIdentifiers(new HashSet<String>());
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void update(EquationManager equationManager) {
    }

	@Override
	public void setNone() {
    }

}
