import java.io.StringReader;
import java.io.IOException;


public class YourScannerImpl implements Scanner {
	public static final char EOF = (char)0;
	public static final char EOL = '\n';
	
	private StringReader currentExpr;
	private char currentChar;
	

	public YourScannerImpl(StringReader expr) {
		currentExpr = expr;
		
		int c = 0;
		try {
			c = currentExpr.read();
			currentExpr.mark(1);
			currentChar = (char) c;
		} catch (IOException error){

		}
	
	}

	@Override
	public char current() {
		return currentChar;
	}

	@Override
	public char next() {
		int c = 0;
		try {
			//read the next char, save it to a local var, and move to the next position in the string
			c = currentExpr.read(); 
			
			//mark our position in the string so we can return to it if we peek ahead
			currentExpr.mark(1);
		} catch (IOException error){
			//return -1 if there's an exception 
			return (char) -1;
		}
		
		//check to see if the end of the string has been reached
		if (c == -1) {
			return EOF;
		}
		
		//return the next char in the string and save it to a class member variable
		currentChar = (char) c;
		return currentChar;
	}

	@Override
	public char peek() {
		int c = 0;
		try {
			//read the next char, save it to a local var,
			c = currentExpr.read();
			
			//reset our position in the string back to where we were before peek was called
			currentExpr.reset();
		} catch (IOException error){
			return (char) -1;
		}
		
		if (c == -1) {
			return EOF;
		}
		
		return (char) c;
	}

}
