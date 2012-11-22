
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
		if (t.type() == Token.Type.IDENTIFIER && 
				n.type() == Token.Type.EQ) {
			node.left = new IdentifierNode();
			IdentifierNode.class.cast(node.left).value = t.text();
			
			to.next();
			node.right = this.parseExpression();
		} else {
			throw new RuntimeException("Invalid assignment syntax");
		}		
		return null;
	}
	
	
	private Node parseExpression () {
		return null;
	}
	
	private Node parseTerm  () {
		return null;
	}
	
	private Node parseFactor () {
		return null;
	}

}
