package io.github.haminic.graphingcalculator.equation.utils;

import java.util.ArrayList;
import java.util.List;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.base.Identifiable;
import io.github.haminic.graphingcalculator.equation.types.NullEquation;
import io.github.haminic.graphingcalculator.gui.equation.EquationContainer;
import io.github.haminic.graphingcalculator.gui.equation.EquationPane;
import io.github.haminic.graphingcalculator.gui.equation.InputPane;

public class EquationController implements EquationUpdateListener {
	
	private final EquationManager equationManager;
	private final EquationContainer equationContainer;
	private final List<EquationPane> equationPanes = new ArrayList<EquationPane>();
	
	public EquationController(EquationManager equationManager, EquationContainer equationContainer) {
		this.equationManager = equationManager;
		this.equationContainer = equationContainer;
		equationManager.addListener(this);
		initializeContainer();
	}
	
	private void initializeContainer() {
		equationContainer.getInputPane().setOnInputFieldUpdate(input -> {
			addEquation(input);
		});
	}
	
	public void addEquation(String input) {
		InputPane inputPane = equationContainer.getInputPane();
		Equation equation = EquationFactory.createEquation(input, equationManager.getSymbolManager());
		if (equation instanceof NullEquation nullEquation) {
			inputPane.setError(nullEquation.getErrorMessage());
			return;
		}
		equationManager.addEquation(equation);
		EquationPane equationPane = createEquationPane(equation);
		equationPanes.add(equationPane);
		if (equation instanceof Identifiable identifiable)
			equationManager.updateWithIdentifierAndNotify(identifiable.getIdentifier());
		equationManager.updateEquationAndNotify(equation); // TODO: Redundant graph update twice?
		equationContainer.getEquations().getChildren().add(equationContainer.getEquations().getChildren().size() - 1, equationPane);
		inputPane.clear();
	}
	
	public void deleteEquation(EquationPane equationPane) {
		equationContainer.getEquations().getChildren().remove(equationPane);
		equationPanes.remove(equationPane);
		equationManager.removeEquation(equationPane.getEquation());
		if (equationPane.getEquation() instanceof Identifiable identifiable)
			equationManager.updateWithIdentifierAndNotify(identifiable.getIdentifier());
		else equationManager.updateWithIdentifierAndNotify(null); // TODO: Find a better way to do this. This is for updating graph.
	}
	
	public void modifyEquation(String input, EquationPane equationPane) {
		equationManager.removeEquation(equationPane.getEquation());
		Equation newEquation = EquationFactory.createEquation(input, equationManager.getSymbolManager());
		equationManager.addEquation(newEquation);
		equationPane.setEquation(newEquation);
		if (newEquation instanceof Identifiable identifiable) {
			equationManager.updateWithIdentifierAndNotify(identifiable.getIdentifier());
		}
		equationManager.updateEquationAndNotify(newEquation);
	}
	
	public void updateGraph() {
		equationManager.updateWithIdentifierAndNotify(null); // TODO: Find a better way to do this. This is for updating graph.
	}
	
	public void removeAllEquation() {
		equationPanes.forEach(equationPane -> {
			equationContainer.getEquations().getChildren().remove(equationPane);
			equationManager.removeEquation(equationPane.getEquation());
		});
		equationPanes.clear();
		equationManager.updateAllAndNotify();
	}
	
	private EquationPane createEquationPane(Equation equation) {
		EquationPane retEquationPane = new EquationPane(equation);
		retEquationPane.setOnEquationFieldUpdate(input -> modifyEquation(input, retEquationPane));
		retEquationPane.setOnDelete(() -> deleteEquation(retEquationPane));
		retEquationPane.setOnGraphUpdate(() -> updateGraph());
		return retEquationPane;
	}
	
	public EquationManager getEquationManager() {
		return equationManager;
	}
	
	public EquationContainer getEquationContainer()	{
		return equationContainer;
	}

	@Override
	public void onEquationUpdate(EquationUpdateEvent event) {
		equationPanes.forEach(equationPane -> {
			Equation equation = equationPane.getEquation();
			if (!event.getAffected().contains(equation)) return;
			if (event.getErrors().containsKey(equation)) {
				equationPane.setError(event.getErrors().get(equation).get(0));
			} else if (equation instanceof NullEquation nullEquation) {
				equationPane.setError(nullEquation.getErrorMessage()); //TODO: Find better way to do this. Perhaps make NullEquation errors be included in EqUpdateEvent.
			} else {
				equationPane.setError("");
			}
			equationPane.updatePane();
		});
	}

}
