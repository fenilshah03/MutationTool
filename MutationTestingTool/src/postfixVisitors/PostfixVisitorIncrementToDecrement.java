package postfixVisitors;

import org.eclipse.jdt.core.dom.PostfixExpression;

class PostfixVisitorIncrementToDecrement extends PostfixVisitor {
	void changePostfixNodeOperator(PostfixExpression node) {
		if (node.getOperator().equals(PostfixExpression.Operator.INCREMENT)) {
			// convert ++ to --
			System.out.println();
			System.out.println("Postfix chnage: convert ++ to --");
			node.setOperator(PostfixExpression.Operator.DECREMENT);
		}
	}
}