package io.github.haminic.graphingcalculator.equation.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.haminic.graphingcalculator.equation.base.Equation;

public class EquationUpdateEvent {
	
	private final Set<Equation> affected;
	private final Map<Equation, List<String>> errors;
	
	public EquationUpdateEvent(Set<Equation> affected, Map<Equation, List<String>> errors) {
		this.affected = affected;
		this.errors = errors;
	}
	
	public EquationUpdateEvent() {
		this.affected = new HashSet<Equation>();
		this.errors = new HashMap<Equation, List<String>>();
	}
	
	public void combine(EquationUpdateEvent event) {
		this.getAffected().addAll(event.getAffected());
		this.getErrors().putAll(event.getErrors()); // NOTE: Doesn't combine fully -- might have to redo (for future me).
	}

	public Set<Equation> getAffected() {
		return affected;
	}
	
	public Map<Equation, List<String>> getErrors() {
		return errors;
	}
	
}
