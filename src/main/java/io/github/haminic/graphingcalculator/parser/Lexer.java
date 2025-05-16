package io.github.haminic.graphingcalculator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.haminic.graphingcalculator.parser.exception.LexerException;

public class Lexer {
	
	private static final String identifiable = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String numerable = "0123456789.";
	private static final Map<Character, TokenType> tokenMap = Map.of(
				'+', TokenType.PLUS,
				'-', TokenType.MINUS,
				'*', TokenType.MULTIPLY,
				'/', TokenType.DIVIDE,
				'%', TokenType.MODULO,
				'^', TokenType.POWER,
				'(', TokenType.LPAREN,
				')', TokenType.RPAREN,
				'=', TokenType.EQUAL
			);
	
	private static boolean isIdentifiable(char character) {
		return (identifiable.contains(String.valueOf(character)));
	}
	
	private static boolean isNumerable(char character) {
		return (numerable.contains(String.valueOf(character)));
	}
	
	public static List<Token> tokenize(String input) throws LexerException {
		ArrayList<Token> tokens = new ArrayList<Token>();
		Token lastToken = null;
		
		for (char character : input.toCharArray()) {
			
			if (Character.isWhitespace(character)) {
				lastToken = null;
				continue;
			}
			
			if (tokenMap.containsKey(character)) {
				lastToken = new Token(tokenMap.get(character), String.valueOf(character));
				tokens.add(lastToken);
				continue;
			}
			
			if (isIdentifiable(character)) {
				if (lastToken != null && lastToken.type == TokenType.IDENTIFIER) {
					lastToken.text = lastToken.text + character;
				} else {
					lastToken = new Token(TokenType.IDENTIFIER, String.valueOf(character));
					tokens.add(lastToken);
				}
				continue;
			}
			
			if (isNumerable(character)) {
				if (lastToken != null && lastToken.type == TokenType.NUMBER) {
					if (character == '.' && lastToken.text.contains(".")) throw new LexerException("Invalid number.");
					lastToken.text = lastToken.text + character;
				} else {
					lastToken = new Token(TokenType.NUMBER, String.valueOf(character));
					tokens.add(lastToken);
				}
				continue;
			}
			
			throw new LexerException("Unknown character: '" + character + "'");
		}
		
		if (tokens != null) tokens.add(new Token(TokenType.END, "END"));
		return tokens;
	}

}
