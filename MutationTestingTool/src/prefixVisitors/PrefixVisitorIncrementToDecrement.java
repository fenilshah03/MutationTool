package prefixVisitors;

import org.eclipse.jdt.core.dom.PrefixExpression;

class PrefixVisitorIncrementToDecrement extends PrefixVisitor {
	void changePrefixNodeOperator(PrefixExpression node) {
		if (node.getOperator().equals(PrefixExpression.Operator.INCREMENT)) {
			// convert ++ to --
			System.out.println();
			System.out.println("Prefix chnage: convert ++ to --");
			node.setOperator(PrefixExpression.Operator.DECREMENT);
		}
	}
}