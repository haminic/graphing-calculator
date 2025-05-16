package io.github.haminic.graphingcalculator.equation.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;

public interface Identifiable {
	
	void addIdentifierTo(SymbolManager symbolManager);
	
	void removeIdentifierFrom(SymbolManager symbolManager);
	
	String getIdentifier();

}
