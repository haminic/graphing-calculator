package io.github.haminic.graphingcalculator.equation.base;

import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.graph.Graph;
import io.github.haminic.graphingcalculator.graph.GraphManager;

public interface Graphable {
	
	void graph(SymbolManager symbolManager, GraphManager graphManager);
	
	void addGraphTo(GraphManager graphManager);
	
	void removeGraphFrom(GraphManager graphManager);
	
	Graph getGraph();
	
}
