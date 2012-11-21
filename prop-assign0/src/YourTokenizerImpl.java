
public class YourTokenizerImpl implements Tokenizer {
	private Scanner sc;
	private Token currentToken;

	public YourTokenizerImpl(Scanner scanner) {
		sc = scanner;
		
		try {
			currentToken = extractToken();
			System.out.println("Tokenizer initialized. First token is: " + currentToken.text());
		} catch (Exception e){
			System.out.println("Error parsing token. Not a valid program. Quitting.");
			System.exit(1);
		}
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
		Token tempToken;
		
		//deal with whitespace
		if (Character.isWhitespace(ch)) {
			while (Character.isWhitespace(ch))  ch = sc.next();
		}
		
		//deal with numbers
		if (Character.isDigit(ch)) {
			StringBuilder numberBuilder = new StringBuilder();
			numberBuilder.append(ch);
			
			for (;;) {
				ch = sc.next();
				if (Character.isDigit(ch)) {
					numberBuilder.append(ch);
				} else if (Character.isWhitespace(ch)){
					return new Token (numberBuilder.toString(), new Integer (numberBuilder.toString()), Token.Type.NUMBER);
				} else {
					if (ch == '(' || ch == ')' || ch == '+' || ch == '-' || ch == '=' || ch == '*' || ch == '/' || ch == Scanner.EOF)
						return new Token (numberBuilder.toString(), new Integer (numberBuilder.toString()), Token.Type.NUMBER);
					else	
						throw new Exception ("Not a valid token.");
				}
			}	
		}
		
		//deal with ids
		if (Character.isLowerCase(ch)) {
			StringBuilder idBuilder = new StringBuilder();
			idBuilder.append(ch);
			
			for (;;) {
				ch = sc.next();
				if (Character.isLowerCase(ch)) {
					idBuilder.append(ch);
				} else if (Character.isWhitespace(ch)){
					return new Token (idBuilder.toString(), idBuilder.toString(), Token.Type.IDENTIFIER);
				} else {
					if (ch == '(' || ch == ')' || ch == '+' || ch == '-' || ch == '=' || ch == '*' || ch == '/' || ch == Scanner.EOF)
						return new Token (idBuilder.toString(), idBuilder.toString(), Token.Type.IDENTIFIER);
					else	
						throw new Exception ("Not a valid token.");
				}
			}
			
		}
		
		//deal with rest of the tokens
		switch (ch) {
		
			case '(':  
				tempToken = new Token(ch.toString(), ch, Token.Type.LEFT_PAREN);
				break;
			case ')':  
				tempToken = new Token(ch.toString(), ch, Token.Type.RIGHT_PAREN);
				break;
			case '+':  
				tempToken = new Token(ch.toString(), ch, Token.Type.PLUS);
				break;			
			case '-':  
				tempToken = new Token(ch.toString(), ch, Token.Type.MINUS);
				break;
			case '=':  
				tempToken = new Token(ch.toString(), ch, Token.Type.EQ);
				break;
			case '*':  
				tempToken = new Token(ch.toString(), ch, Token.Type.MULT);
				break;
			case '/':  
				tempToken = new Token(ch.toString(), ch, Token.Type.DIV);
				break;
			case Scanner.EOF:  
				tempToken = new Token(ch.toString(), Scanner.EOF, Token.Type.EOF);
				break;
			default: throw new Exception ("Not a valid token.");
		
		}
		sc.next();
		return tempToken;
	}
	
	
	@Override
	public Token current() {

		return currentToken;
	}

	@Override
	public Token next() {
		try {
			currentToken = extractToken();
		} catch (Exception e){
			System.out.println("Error parsing token. Not a valid program. Quitting.");
			System.exit(1);
		}
		return currentToken;
	}

	@Override
	public Token peek() {
		// TODO Auto-generated method stub
		return null;
	}

}
