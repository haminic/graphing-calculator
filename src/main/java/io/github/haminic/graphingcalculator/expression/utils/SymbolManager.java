package io.github.haminic.graphingcalculator.expression.utils;

import java.util.HashMap;

import java.util.Map;
import java.util.function.Function;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.types.ConstantEquation;
import io.github.haminic.graphingcalculator.equation.types.FunctionEquation;
import io.github.haminic.graphingcalculator.equation.types.NullEquation;
import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.functions.Absolute;
import io.github.haminic.graphingcalculator.expression.functions.Ceiling;
import io.github.haminic.graphingcalculator.expression.functions.CubeRoot;
import io.github.haminic.graphingcalculator.expression.functions.Exponential;
import io.github.haminic.graphingcalculator.expression.functions.Factorial;
import io.github.haminic.graphingcalculator.expression.functions.Floor;
import io.github.haminic.graphingcalculator.expression.functions.Logarithm;
import io.github.haminic.graphingcalculator.expression.functions.SquareRoot;
import io.github.haminic.graphingcalculator.expression.functions.UserFunction;
import io.github.haminic.graphingcalculator.expression.functions.trig.Arccosine;
import io.github.haminic.graphingcalculator.expression.functions.trig.Arcsine;
import io.github.haminic.graphingcalculator.expression.functions.trig.Arctangent;
import io.github.haminic.graphingcalculator.expression.functions.trig.Cosecant;
import io.github.haminic.graphingcalculator.expression.functions.trig.Cosine;
import io.github.haminic.graphingcalculator.expression.functions.trig.Cotangent;
import io.github.haminic.graphingcalculator.expression.functions.trig.Secant;
import io.github.haminic.graphingcalculator.expression.functions.trig.Sine;
import io.github.haminic.graphingcalculator.expression.functions.trig.Tangent;
import io.github.haminic.graphingcalculator.expression.literals.Constant;
import io.github.haminic.graphingcalculator.expression.literals.UserConstant;
import io.github.haminic.graphingcalculator.expression.literals.Variable;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.TokenType;

public class SymbolManager {
	
	private enum SymbolType {
		VARIABLE,
		BUILT_IN_CONSTANT,
		BUILT_IN_FUNCTION,
		USER_CONSTANT,
		USER_FUNCTION,
	}
	
	private static final Map<String, Constant> builtInConstants = Map.of(
				"pi", new Constant(Math.PI, "pi"),
				"e", new Constant(Math.E, "e") 
			);
	
	private static final Map<String, Function<Expression, Expression>> builtInFunctions = Map.ofEntries(
			Map.entry("sin", Sine::new),
		    Map.entry("cos", Cosine::new),
		    Map.entry("tan", Tangent::new),
		    Map.entry("sec", Secant::new),
		    Map.entry("csc", Cosecant::new),
		    Map.entry("cot", Cotangent::new),
		    Map.entry("abs", Absolute::new),
		    Map.entry("exp", Exponential::new),
		    Map.entry("log", Logarithm::new),
		    Map.entry("ln", Logarithm::new),
		    Map.entry("sqrt", SquareRoot::new),
		    Map.entry("cbrt", CubeRoot::new),
		    Map.entry("arcsin", Arcsine::new),
		    Map.entry("arccos", Arccosine::new),
		    Map.entry("arctan", Arctangent::new),
		    Map.entry("floor", Floor::new),
		    Map.entry("ceil", Ceiling::new),
		    Map.entry("fact", Factorial::new)
		);
	
	private final Map<String, FunctionEquation> userFunctions = new HashMap<String, FunctionEquation>();
	private final Map<String, ConstantEquation> userConstants = new HashMap<String, ConstantEquation>();
	
	public boolean hasIdentifier(String identifier) {
		return userConstants.containsKey(identifier) || 
				userFunctions.containsKey(identifier) || 
				builtInConstants.containsKey(identifier) || 
				builtInFunctions.containsKey(identifier) || 
				identifier.equals("x");
	}
	
	public void addConstant(String identifier, ConstantEquation equation) {
		if (hasIdentifier(identifier)) return; //TODO: Throw error (? already handled from outside)
		userConstants.put(identifier, equation);
	}
	
	public void addFunction(String identifier, FunctionEquation equation) {
		if (hasIdentifier(identifier)) return; //TODO: Throw error (? already handled from outside)
		userFunctions.put(identifier, equation);
	}
	
	public void removeConstant(String identifier) {
		userConstants.remove(identifier);
	}
	
	public void removeFunction(String identifier) {
		userFunctions.remove(identifier);
	}
	
	private SymbolType getType(String identifier) {
	    if (builtInConstants.containsKey(identifier)) return SymbolType.BUILT_IN_CONSTANT;
	    if (builtInFunctions.containsKey(identifier)) return SymbolType.BUILT_IN_FUNCTION;
	    if (userConstants.containsKey(identifier)) return SymbolType.USER_CONSTANT;
	    if (userFunctions.containsKey(identifier)) return SymbolType.USER_FUNCTION;
	    if ("x".equals(identifier)) return SymbolType.VARIABLE;
	    return null;
	}
	
	private boolean isType(String identifier, SymbolType... types) {
	    SymbolType currentType = getType(identifier);
	    if (currentType == null) return false;
	    for (SymbolType type : types) {
	        if (currentType == type) return true;
	    }
	    return false;
	}
	
	public boolean isFunction(Token token) {
		if (token.type != TokenType.IDENTIFIER) return false;
		String identifier = token.text;
		return isType(identifier, SymbolType.USER_FUNCTION, SymbolType.BUILT_IN_FUNCTION);
	}
	
	public boolean isLiteral(Token token) {
		if (token.type != TokenType.IDENTIFIER) return false;
		String identifier = token.text;
		return isType(identifier, SymbolType.USER_CONSTANT, SymbolType.BUILT_IN_CONSTANT, SymbolType.VARIABLE);
	}
	
//	TODO: Cleanup code
	public Expression createLiteral(Token token) {
		if (token.type != TokenType.IDENTIFIER) return Expression.NULL;
		if (builtInConstants.containsKey(token.text))
			return builtInConstants.get(token.text);
		if (token.text.equals("x")) {
			return new Variable();
		}
		if (userFunctions.containsKey(token.text)) return Expression.NULL;
		return new UserConstant(token.text);
	}

//	TODO: Cleanup code
	public Expression createFunction(Token token, Expression arg) {
		if (token.type != TokenType.IDENTIFIER) return Expression.NULL;
		if (builtInFunctions.containsKey(token.text))
			return builtInFunctions.get(token.text).apply(arg);
		if (userConstants.containsKey(token.text)) return Expression.NULL;
		return new UserFunction(token.text, arg);
	}
	
	public Equation getEquation(String identifier) {
		if (userFunctions.containsKey(identifier)) return userFunctions.get(identifier);
		if (userConstants.containsKey(identifier)) return userConstants.get(identifier);
		return new NullEquation("", "Invalid identifier");
	} 
	
	public Expression getConstantExpression(String identifier) {
		if (!userConstants.containsKey(identifier)) return Expression.NULL;
		return userConstants.get(identifier).getExpression();
	}
	
	public Expression getFunctionExpression(String identifier) {
		if (!userFunctions.containsKey(identifier)) return Expression.NULL;
		return userFunctions.get(identifier).getExpression();
	}

	public Map<String, FunctionEquation> getUserFunctions() {
		return userFunctions;
	}

	public Map<String, ConstantEquation> getUserConstants() {
		return userConstants;
	}

}
