package io.github.haminic.graphingcalculator.equation.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Graphable;
import io.github.haminic.graphingcalculator.equation.base.Identifiable;
import io.github.haminic.graphingcalculator.equation.types.NullEquation;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.graph.GraphManager;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class EquationManager {
	
	private final List<Equation> equations = new ArrayList<Equation>();
	private final SymbolManager symbolManager;
	private final GraphManager graphManager;
	private final List<EquationUpdateListener> listeners = new ArrayList<>(); 
	
	public EquationManager(SymbolManager symbolManager, GraphManager graphManager) {
		this.symbolManager = symbolManager;
		this.graphManager = graphManager;
	}

	private void notifyListeners(EquationUpdateEvent event) {
		listeners.forEach(listener -> listener.onEquationUpdate(event));
	}
	
//	TODO: Add separate update equation for non-reparse and with reparse. Useful for cutting
//	down lines of other methods below.
	private EquationUpdateEvent updateEquation(Equation equation) {
		EquationUpdateEvent event = new EquationUpdateEvent();
		event.getAffected().add(equation);
		List<String> errors = validateEquation(equation);
		if (errors.size() > 0) {
			equation.setNone();
			event.getErrors().put(equation, errors);
		} else equation.update(this);
		return event;
	}
	
	public void updateEquationAndNotify(Equation equation) {
		EquationUpdateEvent event = updateEquation(equation);
		notifyListeners(event);
	}
	
//	TODO: Way too many tabs
	private void updateWithIdentifier(String identifier, EquationUpdateEvent event) {
		equations.forEach(equation -> {
			if (equation.hasIdentifier(identifier)) {
				if (event.getErrors().containsKey(equation)) return;
				event.getAffected().add(equation);
				try {
					equation.parse(symbolManager);
					List<String> errors = validateEquation(equation);
					if (errors.size() > 0) {
						equation.setNone();
						event.getErrors().put(equation, errors);
					} else equation.update(this);
				} catch (ParserException e) {
					equation.setNone();
					event.getErrors().put(equation, List.of(e.getMessage()));
				}
				if (equation instanceof Identifiable identifiable) updateWithIdentifier(identifiable.getIdentifier(), event);
			}
		});
	}
	
	private EquationUpdateEvent updateWithIdentifier(String identifier) {
		EquationUpdateEvent event = new EquationUpdateEvent();
		updateWithIdentifier(identifier, event);
		return event;
	}
	
	public void updateWithIdentifierAndNotify(String identifier) {
		EquationUpdateEvent event = updateWithIdentifier(identifier);
		notifyListeners(event);
	}
	
	private EquationUpdateEvent updateAll() {
		EquationUpdateEvent event = new EquationUpdateEvent();
		equations.forEach(equation -> {
			event.getAffected().add(equation);
			try {
				equation.parse(symbolManager);
				List<String> errors = validateEquation(equation);
				if (errors.size() > 0) {
					equation.setNone();
					event.getErrors().put(equation, errors);
				} else equation.update(this);
			} catch (ParserException e) {
				equation.setNone();
				event.getErrors().put(equation, List.of(e.getMessage()));
			}
		});
		return event;
	}
	
	public void updateAllAndNotify() {
		EquationUpdateEvent event = updateAll();
		notifyListeners(event);
	}
	
	private List<String> validateEquation(Equation equation) {
		ArrayList<String> errors = new ArrayList<String>();
		if (isRecursive(equation)) {
			errors.add("Recursion Error: This equation is self-referential.");
		}
//		TODO: Check for undefined symbol in tree. e.g. g(x) = f(x), f(x) = t with t undefined should show an error
//		t is undefined on both f and g.
		for (String identifier : equation.getIdentifiers()) {
			if (!symbolManager.hasIdentifier(identifier)) errors.add("Unknown Symbol: Symbol " + identifier + " is undefined.");
		}
		return errors;
	}
	
	public void addEquation(Equation equation) {
		if (equation instanceof Identifiable) ((Identifiable) equation).addIdentifierTo(symbolManager);
		if (equation instanceof Graphable) ((Graphable) equation).addGraphTo(graphManager);
		equations.add(equation); // TODO: handle can't add errors & repeated identifier definition
	}
	
	public void removeEquation(Equation equation) {
		equations.remove(equation);
		if (equation instanceof Identifiable) ((Identifiable) equation).removeIdentifierFrom(symbolManager); 
		if (equation instanceof Graphable) ((Graphable) equation).removeGraphFrom(graphManager);
		// TODO: handle can't remove errors & repeated identifier definition
	}
	
	public List<Equation> getEquations() {
		return equations;
	}

	public boolean isRecursive(Equation equation) {
		if (!(equation instanceof Identifiable)) return false;
		
		String identifier = ((Identifiable) equation).getIdentifier();
		Set<String> identifiers = equation.getIdentifiers();
		
		for (String i : identifiers) {
			if (i.equals(identifier)) return true;
			
			Equation childEquation = getSymbolManager().getEquation(i);
			Set<String> emptySet = new HashSet<>();
			if (isInTree(identifier, childEquation, emptySet)) return true;
			if (isRecursive(childEquation)) return true;
		}

		return false;
	}
	public boolean isInTree(String identifier, Equation equation, Set<String> visited) {
		if (equation == null || equation instanceof NullEquation) return false;
		String equationIdentifier = ((Identifiable) equation).getIdentifier();
		if (visited.contains(equationIdentifier)) return false;
		visited.add(equationIdentifier);
		
		Set<String> children = equation.getIdentifiers();
		for (String child : children) {
			if(getSymbolManager().getEquation(child) == null) continue;
			
			if (identifier.equals(child)) {
				return true;
			}
			if(isInTree(identifier, getSymbolManager().getEquation(child), visited)){
				return true;
			}
		}
		return false;
	}
	

	public SymbolManager getSymbolManager() {
		return symbolManager;
	}

	public GraphManager getGraphManager() {
		return graphManager;
	}
	
	public void addListener(EquationUpdateListener listner) {
		listeners.add(listner);
	}
	
	public void removeListener(EquationUpdateListener listener) {
		listeners.remove(listener);
	}

}
