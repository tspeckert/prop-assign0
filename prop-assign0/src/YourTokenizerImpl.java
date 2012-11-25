
public class YourTokenizerImpl implements Tokenizer {
	private Scanner sc;
	private Token currentToken;

	public YourTokenizerImpl(Scanner scanner) {
		sc = scanner;
		
		try {
			currentToken = extractToken(false);
			//System.out.println("Tokenizer initialized. First token is: " + currentToken.text());
		} catch (Exception e){
			System.out.println("Error parsing token. Not a valid program. Quitting.");
			System.exit(1);
		}
	}


	//method extracts a single token
	//parameter peeking is used to determine if we are peeking or not
	private Token extractToken(boolean peeking) throws Exception {
		Character ch = sc.current();
		Token tempToken;
		
		//deal with whitespace
		if (Character.isWhitespace(ch)) {
			while (Character.isWhitespace(ch))  {
				
				//either Scanner.peek() if we are peeking, otherwise use Scanner.next()
				if (peeking) ch = sc.peek();
				else ch = sc.next();
			}
		}
		
		//deal with numbers
		if (Character.isDigit(ch)) {
			StringBuilder numberBuilder = new StringBuilder(); //used to build a string of numbers 1 char at a time
			numberBuilder.append(ch); //add the character to the string
			
			for (;;) {
				if (peeking) ch = sc.peek();
				else ch = sc.next();
				
				if (Character.isDigit(ch)) {
					numberBuilder.append(ch);
				} else if (Character.isWhitespace(ch)){
					//we now have a token
					return new Token (numberBuilder.toString(), new Integer (numberBuilder.toString()), Token.Type.NUMBER); 
				} else {
					if (ch == '(' || ch == ')' || ch == '+' || ch == '-' || 
							ch == '=' || ch == '*' || ch == '/' || ch == Scanner.EOF)
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
				if (peeking) ch = sc.peek();
				else ch = sc.next();
				
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
		
		//deal with rest of the tokens (the operators and the end of the sentence
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
		if (peeking) ch = sc.peek();
		else ch = sc.next();
		
		return tempToken;
	}
	
	@Override
	public Token current() {

		return currentToken;
	}

	@Override
	public Token next() {
		try {
			//call extractToken with the peeking parameter set to false (since this is next)
			//and update the current token
			currentToken = extractToken(false);
		} catch (Exception e){
			System.out.println("Error extracting token. Not a valid program. Quitting.");
			System.exit(1);
		}
		return currentToken;
	}

	@Override
	//allows us to peek 1 token ahead
	public Token peek() {
		Token peekedToken;
		try {
			//return the peeked token
			return extractToken(true);
			
		} catch (Exception e){
			System.out.println("Error extracting token. Not a valid program. Quitting.");
			System.exit(1);
		}
		//will never get here
		//our error handling should probably be improved on 
		peekedToken = new Token(null,null,null);
		return peekedToken;
	}

}
