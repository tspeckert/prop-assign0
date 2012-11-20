
public class YourTokenizerImpl implements Tokenizer {
	private YourScannerImpl sc;
	private Token currentToken;

	public YourTokenizerImpl(YourScannerImpl scanner) {
		sc = scanner;		
	}

	/*
	 * string of chars == id
	 * integer == factor
	 * (
	 * )
	 * =
	 * + 
	 * -
	 * *
	 * /
	 * EOF
	 */
	
	private Token extractToken() throws Exception {
		Character ch = sc.current();
		
		if (Character.isDigit(ch)) {
			//deal with int
		}
		
		if (Character.isLowerCase(ch)) {
			//deal with id
		}
		
		if (Character.isWhitespace(ch)) {
			//deal with whitespace 
		}
		
		//rest of the tokens
		switch (ch) {
		
			case '(':  return new Token(ch.toString(), ch, Token.Type.LEFT_PAREN);
			case ')':  return new Token(ch.toString(), ch, Token.Type.RIGHT_PAREN);
			case '+':  return new Token(ch.toString(), ch, Token.Type.PLUS);
			case '-':  return new Token(ch.toString(), ch, Token.Type.MINUS);
			case '=':  return new Token(ch.toString(), ch, Token.Type.EQ);
			case '*':  return new Token(ch.toString(), ch, Token.Type.MULT);
			case '/':  return new Token(ch.toString(), ch, Token.Type.DIV);
			case Scanner.EOF:  return new Token(ch.toString(), Scanner.EOF, Token.Type.EOF);
						
			default: throw new Exception ("Not a valid token.");
		
		}
	}
	
	
	@Override
	public Token current() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token peek() {
		// TODO Auto-generated method stub
		return null;
	}

}
