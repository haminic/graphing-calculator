package io.github.haminic.graphingcalculator.parser;

import java.util.List;

import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.literals.Constant;
import io.github.haminic.graphingcalculator.expression.operations.Addition;
import io.github.haminic.graphingcalculator.expression.operations.Division;
import io.github.haminic.graphingcalculator.expression.operations.Exponentiation;
import io.github.haminic.graphingcalculator.expression.operations.Modulo;
import io.github.haminic.graphingcalculator.expression.operations.Multiplication;
import io.github.haminic.graphingcalculator.expression.operations.Negation;
import io.github.haminic.graphingcalculator.expression.operations.Subtraction;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public class Parser {
		
	private final SymbolManager symbolManager;
	
	private static final List<TokenType> implicitMultipliable = List.of(
				TokenType.IDENTIFIER, TokenType.LPAREN, TokenType.NUMBER
			);
	
	private final List<Token> tokens;
	private Expression headExpression;
	private int current = 0;
	
	public Parser(List<Token> tokens, SymbolManager symbolManager) {
		this.tokens = tokens;
		this.symbolManager = symbolManager;
	}
	
	public void parse() throws ParserException {
		Expression expression = parseAddition();
		if (currentToken().type != TokenType.END) throw new ParserException("Expected 'END' but found: " + currentToken().text);
		headExpression = expression;
	}
	
	private Expression parseAddition() throws ParserException {
		Expression retExpression = parseMultiplication();
		
		while (true) {
			switch (currentToken().type) {
			case PLUS:
				advance();
				retExpression = new Addition(retExpression, parseMultiplication());
				continue;
			case MINUS:
				advance();
				retExpression = new Subtraction(retExpression, parseMultiplication());
				continue;
			default:
				break;
			}
			break;
		}
		
		return retExpression;
	}
	
	private Expression parseMultiplication() throws ParserException {
		Expression retExpression = parsePower();
		
		while (true) {
			switch (currentToken().type) {
			case MULTIPLY:
				advance();
				retExpression = new Multiplication(retExpression, parsePower());
				continue;
			case DIVIDE:
				advance();
				retExpression = new Division(retExpression, parsePower());
				continue;
			case MODULO:
				advance();
				retExpression = new Modulo(retExpression, parsePower());
				continue;
			default:
				if (canImplicitMultiply(currentToken())) {
					retExpression = new Multiplication(retExpression, parsePower());
					continue;
				}
				break;
			}
			break;
		}
		
		return retExpression;
	}
	
	private Expression parsePower() throws ParserException {
		Expression retExpression = parseBase();
		if (currentToken().type == TokenType.POWER) {
			advance();
			retExpression = new Exponentiation(retExpression, parsePower());
		}
		return retExpression;
	}
	
	private Expression parseBase() throws ParserException {
		Expression retExpression;
		
		switch (currentToken().type) {
		case LPAREN:
			advance();
			retExpression = parseAddition();
			if (currentToken().type != TokenType.RPAREN) throw new ParserException("Expected ')' but found: " + currentToken().text);
			advance();
			break;
		case NUMBER:
			retExpression = new Constant(Double.parseDouble(currentToken().text));
			advance();
			break;
		case IDENTIFIER:
			if (symbolManager.isFunction(currentToken())) {
				retExpression = parseFunction();
				break;
			} 
			retExpression = symbolManager.createLiteral(currentToken());
			advance();
			break;
		case MINUS:
			advance();
			retExpression = new Negation(parsePower());
			break;
		default:
			throw new ParserException(); //TODO: Exception message.
		}
		
		return retExpression;
	}
	
//	TODO: Clean code 
	private Expression parseFunction() throws ParserException {
		Expression retExpression;
		if (!symbolManager.isFunction(currentToken())) throw new ParserException(currentToken().text + " is not a function."); // TODO: Exception (impossible to get here?)
		Token function = currentToken();
		advance();
		
		if (currentToken().type == TokenType.POWER) {
			advance();
			Expression power = parsePower();
			if (currentToken().type != TokenType.LPAREN) {
				retExpression = new Exponentiation(symbolManager.createFunction(function, parsePower()), power);
			} else {
				advance();
				Expression argument = parseAddition();
				if (currentToken().type != TokenType.RPAREN) throw new ParserException("Expected ')' but found: " + currentToken().text);
				retExpression = new Exponentiation(symbolManager.createFunction(function, argument), power);
				advance();
			}
		} else {
			if (currentToken().type != TokenType.LPAREN) {
				retExpression = symbolManager.createFunction(function, parsePower());
			} else {
				advance();
				Expression argument = parseAddition();
				if (currentToken().type != TokenType.RPAREN) throw new ParserException("Expected ')' but found: " + currentToken().text);
				retExpression = symbolManager.createFunction(function, argument);
				advance();
			}
		}
		
		return retExpression;
	}
	
	private void advance() {
		if (current < tokens.size() - 1) current++;
	}
	
	private Token currentToken() {
		return tokens.get(current);
	}

	public Expression getHeadExpression() {
		return headExpression;
	}
	
	private static boolean canImplicitMultiply(Token token) {
		return implicitMultipliable.contains(token.type);
	}

}
