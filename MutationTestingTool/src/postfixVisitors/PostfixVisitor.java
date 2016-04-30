package postfixVisitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PostfixExpression;

public abstract class PostfixVisitor extends ASTVisitor {
	public boolean visit(PostfixExpression node) {
		changePostfixNodeOperator(node);
		return false;
	}

	abstract void changePostfixNodeOperator(PostfixExpression node);

	public static List<ASTVisitor> getPostfixVisitors() {
		List<ASTVisitor> postfixVisitors = new ArrayList<ASTVisitor>();

		postfixVisitors.add(new PostfixVisitorDecrementToIncrement());
		postfixVisitors.add(new PostfixVisitorIncrementToDecrement());

		return postfixVisitors;
	}
}