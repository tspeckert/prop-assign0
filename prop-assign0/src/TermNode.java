public class TermNode implements Node {
	public Node left;
	public String operator;
	public Node right;

	public Object visit(Visitor v) {
		return v.visitTerm(this);
	}
	
	@Override
	public String toTree() {
		return String.format("Term(left=%s, op=%s, right=%s)",
				left.toTree(), operator != null ? operator : "<NONE>",
				right != null ? right.toTree() : "<NONE>");
	}
}