package io.github.haminic.graphingcalculator.equation.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.expression.base.Expression;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.parser.Lexer;
import io.github.haminic.graphingcalculator.parser.Parser;
import io.github.haminic.graphingcalculator.parser.Token;
import io.github.haminic.graphingcalculator.parser.TokenType;
import io.github.haminic.graphingcalculator.parser.exception.ParserException;

public abstract class Equation {

	private final String rawText; // For saving equations (in the future)
	private final List<Token> tokens;
	protected Expression expression;
	private Set<String> identifiers;
	
	public Equation(String rawText, List<Token> tokens, SymbolManager symbolManager) throws ParserException {
		this.rawText = rawText;
		this.tokens = tokens;
		parse(symbolManager);
	}
	
//	For null object, but may be useful when testing.
	public Equation(String rawText, List<Token> tokens) {
		this.rawText = rawText;
		this.tokens = tokens;
	}
	
//	For testing only -- avoids having to tokenize from outside the class.
	public Equation(String rawText, String parsableText, SymbolManager symbolManager) {
		this.rawText = rawText;
		try {
			this.tokens = Lexer.tokenize(parsableText);
			parse(symbolManager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public abstract void update(EquationManager equationManager);
	
	public abstract void setNone();
	
	public void parse(SymbolManager symbolManager) throws ParserException {
		try {
			Parser parser = new Parser(tokens, symbolManager);
			parser.parse();
			this.expression = parser.getHeadExpression();
			Set<String> identifiers = new HashSet<String>();
			for (Token token : tokens) {
				if (token.type == TokenType.IDENTIFIER) identifiers.add(token.text);
			}
			this.identifiers = identifiers;
		} catch (ParserException e) {
			this.expression = Expression.NULL;
			throw e;
		}
	}
	
	@Override
	public String toString() {
		return rawText;
	}
	
	public String getRawText() {
		return rawText;
	}
	
	public Expression getExpression() {
		return this.expression;
	}
	
	public Set<String> getIdentifiers() {
		return this.identifiers;
	}
	
	public boolean hasIdentifier(String identifier) {
		return identifiers.contains(identifier);
	}
	
	protected void setIdentifiers(Set<String> identifiers) {
		this.identifiers = identifiers;
	}
	
}
