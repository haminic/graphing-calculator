package io.github.haminic.graphingcalculator.equation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.haminic.graphingcalculator.equation.base.Equation;
import io.github.haminic.graphingcalculator.equation.types.ConstantEquation;
import io.github.haminic.graphingcalculator.equation.types.EvaluationEquation;
import io.github.haminic.graphingcalculator.equation.types.FunctionEquation;
import io.github.haminic.graphingcalculator.equation.types.GraphEquation;
import io.github.haminic.graphingcalculator.equation.types.NullEquation;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.parser.Lexer;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.TokenType;
import io.github.haminic.graphingcalculator.parser.exception.LexerException;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class EquationFactory {
	
	private enum EquationType {
		EVALUATION, CONSTANT, GRAPH, FUNCTION, INVALID
	}
	
	private static final List<TokenType> functionPattern = List.of(
				TokenType.IDENTIFIER, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.RPAREN, TokenType.EQUAL
			);
	private static final List<TokenType> constantPattern = List.of(
				TokenType.IDENTIFIER, TokenType.EQUAL
			);
	
	public static Equation createEquation(String rawText, SymbolManager symbolManager) {
		ArrayList<Token> tokens;
		try {
			tokens = (ArrayList<Token>) Lexer.tokenize(rawText);
		} catch (LexerException e) {
			return new NullEquation(rawText, e.getMessage());
		}
		boolean hasVariable = hasVariable(tokens);
		
		try {
			switch (getEquationType(tokens)) {
			case FUNCTION:
				if (!tokens.get(2).text.equals("x")) return new NullEquation(rawText, "Invalid Equation: Functions must be in terms of x.");
				if (symbolManager.hasIdentifier(tokens.get(0).text)) return new NullEquation(rawText, "Invalid Equation: The identifier '" + tokens.get(0).text + "' already exists.");
				return new FunctionEquation(rawText, tokens.subList(5, tokens.size()), tokens.get(0).text, symbolManager);
			case CONSTANT:
				if (hasVariable) return new NullEquation(rawText, "Invalid Equation: Constants must not contain variables");
				if (symbolManager.hasIdentifier(tokens.get(0).text)) return new NullEquation(rawText, "Invalid Equation: The identifier '" + tokens.get(0).text + "' already exists.");
				return new ConstantEquation(rawText, tokens.subList(2, tokens.size()), tokens.get(0).text, symbolManager);
			case EVALUATION:
				if (hasVariable) return new NullEquation(rawText, "Invalid Equation: Evaluations must not contain variables");
				return new EvaluationEquation(rawText, tokens, symbolManager);
			case GRAPH:
				return new GraphEquation(rawText, tokens, symbolManager);
			default:
				return new NullEquation(rawText, "Invalid Equation: Unknown equation syntax.");
			}
		} catch (ParserException e) {
			return new NullEquation(rawText, e.getMessage());
		}
	
	}
	
	private static EquationType getEquationType(List<Token> tokens) {
		ArrayList<TokenType> tokenTypes = tokens.stream()
				.map(token -> token.type)
				.collect(Collectors.toCollection(ArrayList::new));
		long equalCount = tokenTypes.stream()
				.filter(tokenType -> tokenType == TokenType.EQUAL)
				.count();
		if (equalCount > 1) return EquationType.INVALID;
//		2 equal signs
		if (startsWith(tokenTypes, functionPattern)) {
			return EquationType.FUNCTION;
		}
		if (startsWith(tokenTypes, constantPattern)) {
			return EquationType.CONSTANT;
		}
		if (equalCount > 0) return EquationType.INVALID;
//		1 equal sign
		if (hasVariable(tokens)) {
			return EquationType.GRAPH;
		}
		return EquationType.EVALUATION;
	}
	
	private static boolean startsWith(List<TokenType> tokenTypes, List<TokenType> toCompare) {
		if (tokenTypes.size() < toCompare.size()) return false;
		for (int i = 0; i < toCompare.size(); i++)
			if (tokenTypes.get(i) != toCompare.get(i)) return false;
		return true;
	}
	
	private static boolean hasVariable(List<Token> tokens) {
		for (Token token : tokens)
			if (token.type == TokenType.IDENTIFIER && token.text.equals("x")) return true;
		return false;
	}

}
