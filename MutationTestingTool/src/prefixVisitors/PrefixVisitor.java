package prefixVisitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PrefixExpression;

public abstract class PrefixVisitor extends ASTVisitor {
	public boolean visit(PrefixExpression node) {
		changePrefixNodeOperator(node);
		return false;
	}

	abstract void changePrefixNodeOperator(PrefixExpression node);

	public static List<ASTVisitor> getPrefixVisitors() {
		List<ASTVisitor> prefixVisitors = new ArrayList<ASTVisitor>();

		prefixVisitors.add(new PrefixVisitorDecrementToIncrement());
		prefixVisitors.add(new PrefixVisitorIncrementToDecrement());

		return prefixVisitors;
	}
}