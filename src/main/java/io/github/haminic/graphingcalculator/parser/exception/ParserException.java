package io.github.haminic.graphingcalculator.parser.exception;

public class ParserException extends Exception {
	
    public ParserException() {
        super("Invalid syntax.");
    }

    public ParserException(String message) {
        super(message);
    }
    
}

