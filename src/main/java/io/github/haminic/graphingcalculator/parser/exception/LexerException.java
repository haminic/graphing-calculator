package io.github.haminic.graphingcalculator.parser.exception;

public class LexerException extends Exception {
	
    public LexerException() {
        super("Invalid character.");
    }

    public LexerException(String message) {
        super(message);
    }
    
}