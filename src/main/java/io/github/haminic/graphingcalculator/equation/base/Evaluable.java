package io.github.haminic.graphingcalculator.equation.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public interface Evaluable {
	
	void evaluate(SymbolManager symbolManager);
	
	double getResult();

}
