package io.github.haminic.graphingcalculator.parser;

public class Token {
	
	public TokenType type;
	public String text;
	
	public Token(TokenType type, String text) {
		this.type = type;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
