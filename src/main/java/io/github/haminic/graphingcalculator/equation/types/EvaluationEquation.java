package io.github.haminic.graphingcalculator.equation.types;

import java.util.List;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Evaluable;
import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class EvaluationEquation extends Equation implements Evaluable {
	
	private double result;

	public EvaluationEquation(String rawText, List<Token> tokens, SymbolManager symbolManager) throws ParserException {
		super(rawText, tokens, symbolManager);
	}
	
//	For testing only -- avoids having to tokenize from outside the class.
	public EvaluationEquation(String text, SymbolManager symbolManager) {
		super(text, text, symbolManager);
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
	
}
