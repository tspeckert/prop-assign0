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
			c = currentExpr.read();
		} catch (IOException error){
			return (char) -1;
		}
		
		if (c == -1) {
			return EOF;
		}
		
		currentChar = (char) c;
		return currentChar;
	}

	@Override
	public char peek() {
		// TODO Auto-generated method stub
		return 0;
	}

}
