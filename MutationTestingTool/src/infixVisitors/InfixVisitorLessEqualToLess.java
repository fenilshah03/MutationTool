package infixVisitors;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorLessEqualToLess extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.LESS_EQUALS)) {
			// convert <= to <
			System.out.println();
			System.out.println("Infix chnage: convert <= to <");
			node.setOperator(InfixExpression.Operator.LESS);
		}
	}
}