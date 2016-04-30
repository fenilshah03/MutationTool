package infixVisitors;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorLessToGreaterEqual extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.LESS)) {
			// Convert < to >=
			System.out.println();
			System.out.println("Infix chnage: Convert < to >=");
			node.setOperator(InfixExpression.Operator.GREATER_EQUALS);
		}
	}
}