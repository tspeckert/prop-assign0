/*
 * @author Thomas Speckert, Shah Faisal Darwaish
 * 
 * PROP Winter 2012 Assignment 0
 */

public class YourParserImpl implements Parser {
	private Tokenizer to;	

	public YourParserImpl(Tokenizer to) {
		this.to = to;
	}

	@Override
	public Node parse() throws RuntimeException {
		AssignNode node = new AssignNode();
		Token t = to.current();
		Token n = to.next();
		
		//check to see if assign statement is valid
		if (t.type() == Token.Type.IDENTIFIER && 
				n.type() == Token.Type.EQ) {
			node.left = new IdentifierNode();
			IdentifierNode.class.cast(node.left).value = t.text();
			
			node.right = this.parseExpression();
		} else {
			throw new RuntimeException("Invalid assignment syntax");
		}		
		return node;
	}
	
	
	private Node parseExpression () {
		ExpressionNode node = new ExpressionNode();
		
		Token t = to.next();
		
		//left node is a term, so lets parse it
		node.left = this.parseTerm();
		
		//reset t's position to the current position in the tokenizer
		t = to.current();
		
		//check to see if we are at the end of the stream
		if (t.type() == Token.Type.EOF){
			node.operator = null;
			node.right = null;
		//check to see if this expression contains multiple terms added/subtracted
		} else if (t.type() == Token.Type.PLUS || t.type() == Token.Type.MINUS ) {
			node.operator = t.text();

			//treat the possibly repeating expression by calling parse expression again
			node.right = this.parseExpression();
			
		//we can encounter a right parenthesis here if we are parsing an expr surrounded by brackets
		//if we encounter anything else, its an error
		} else if (t.type()!=Token.Type.RIGHT_PAREN) { 
			throw new RuntimeException("Not a valid Expression");
		}
		
		return node;
	}
	
	private Node parseTerm  () {
		TermNode node = new TermNode();
				
		node.left = this.parseFactor();
		
		Token op = to.next();
		
		if (op.type() == Token.Type.EOF){
			node.operator = null;
			node.right = null;
		} else if (op.type() == Token.Type.MULT || op.type() == Token.Type.DIV ) {
			node.operator = op.text();
			to.next(); //need to move to the beginning of the factor 
			node.right = this.parseTerm(); //deal with the possibility of repeating factors in the term
			
		//We can encounter a +, -, or ) here. If not, then its an error
		} else if (!(op.type() == Token.Type.PLUS ||  
				op.type() == Token.Type.MINUS ||
				op.type() == Token.Type.RIGHT_PAREN)) {
			throw new RuntimeException("Not a valid Term");
		}
		
		return node;
	}
	
	private Node parseFactor () {
		FactorNode node = new FactorNode();
		
		Token t = to.current();
		
		//Here we either expect an int, or an expression surrounded by brackets 
		if (t.type() == Token.Type.NUMBER) {
			NumberNode n = new NumberNode ();
			n.value = (Integer) t.value();
			node.node = n;
		} else if (t.type() == Token.Type.LEFT_PAREN) {
			//if we have a bracket we should then have an expression
			node.node = this.parseExpression();
			
			t = to.current(); //update t to the tokenizer's position
			
			//after the expression we need a closing bracket
			if (t.type() != Token.Type.RIGHT_PAREN) 
				throw new RuntimeException("Not a valid Factor, ')' expected.");
			
		} else {
			throw new RuntimeException("Not a valid Factor, '(' expected.");
		}
		
		return node;
	}

}
