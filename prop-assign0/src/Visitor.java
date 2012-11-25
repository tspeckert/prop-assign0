import java.util.*;

/*
 * @author Thomas Speckert, Shah Faisal Darwaish
 * 
 * PROP Winter 2012 Assignment 0
 */

public class Visitor {

	public Object visit(Node node) {
		if(node != null) {
			return node.visit(this);
		}
		return null;
	}

	public Object visitAssign(AssignNode n) {
		Map<String, Number> assignments = new HashMap<String, Number>();
		String left = (String) visit(n.left);
		Number right = (Number) visit(n.right);
		assignments.put(left, right);
		return assignments;
	}

	public Object visitExpression(ExpressionNode n) {
		//check to see if the expression is just a single term
		if (n.operator == null)
			return visitTerm(TermNode.class.cast(n.left));
		
		//perform the add/sub
		Integer lhs = (Integer) visitTerm(TermNode.class.cast(n.left));
		
		//deal repeating terms in the expression
		//...not sure if best way to have done this
		ExpressionNode rightBranch = ExpressionNode.class.cast(n.right);
		
		Integer rhs = (Integer) visitExpression(rightBranch); //recursively call visitExpression to deal with repeating expressions 
		
		Integer result;
		
		if (n.operator.equals("+")) 
			result = lhs + rhs;
		else 
			result = lhs - rhs;
		
		return result;
		
	}

	public Object visitTerm(TermNode n) {
		//check to see if the term is just a single factor
		if (n.operator == null)
			return visitFactor(FactorNode.class.cast(n.left));
		
		//since it isn't, do a multiplication or division!
		Integer lhs = (Integer) visitFactor(FactorNode.class.cast(n.left));
		
		TermNode rightBranch = TermNode.class.cast(n.right); //deal with repeating factors in the term
		
		Integer rhs = (Integer) visitTerm(rightBranch); //recursively call visitTerm to deal with the repeating factors
		
		Integer result;
		if (n.operator.equals("*")) 
			result = (lhs * rhs);
		else 
			result = (lhs / rhs);
		return result;
	}
	
	public Object visitFactor(FactorNode n) {
		
		//return the number value if this factor is an int 
		if (n.node instanceof NumberNode) 
			return NumberNode.class.cast(n.node).value;
		
		//otherwise we need to visit the expression
		return visitExpression(ExpressionNode.class.cast(n.node));
	}

	public Object visitIdentifier(IdentifierNode n) {
		return n.value;
	}

	public Object visitNumber(NumberNode n) {
		return n.value;
	}
}