package io.github.haminic.graphingcalculator.equation.types;

import java.util.List;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Evaluable;
import io.github.haminic.graphingcalculator.equation.base.Identifiable;
import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class ConstantEquation extends Equation implements Identifiable, Evaluable {

	private final String identifier;
	private double result;
	
	public ConstantEquation(String rawText, List<Token> tokens, String identifier, SymbolManager symbolManager) throws ParserException {
		super(rawText, tokens, symbolManager);
		this.identifier = identifier;
	}
	
//	For testing only -- avoids having to tokenize from outside the class.
	public ConstantEquation(String text, String identifier, SymbolManager symbolManager) {
		super(identifier + " = " + text, text, symbolManager);
		this.identifier = identifier;
	}
	
	@Override
	public void evaluate(SymbolManager symbolManager) {
		result = expression.eval(symbolManager);
	}

	@Override
	public void update(EquationManager equationManager) {
		evaluate(equationManager.getSymbolManager());
	}
	
	@Override
	public void setNone() {
		this.result = Double.NaN;
	}

	@Override
	public double getResult() {
		return result;
	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void addIdentifierTo(SymbolManager symbolManager) {
		symbolManager.addConstant(identifier, this);
	}

	@Override
	public void removeIdentifierFrom(SymbolManager symbolManager) {
		symbolManager.removeConstant(identifier);
	}

}
