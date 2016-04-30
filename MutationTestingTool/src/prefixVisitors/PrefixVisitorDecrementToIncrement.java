package prefixVisitors;

import org.eclipse.jdt.core.dom.PrefixExpression;

class PrefixVisitorDecrementToIncrement extends PrefixVisitor {
	void changePrefixNodeOperator(PrefixExpression node) {
		if (node.getOperator().equals(PrefixExpression.Operator.DECREMENT)) {
			// convert -- to ++
			System.out.println();
			System.out.println("Prefix change: convert -- to ++");
			node.setOperator(PrefixExpression.Operator.INCREMENT);
		}
	}
}