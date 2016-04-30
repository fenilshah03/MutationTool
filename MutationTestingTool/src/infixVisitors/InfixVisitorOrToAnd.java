package infixVisitors;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorOrToAnd extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.OR)) {
			// convert | to &
			System.out.println();
			System.out.println("Infix chnage: convert | to &");
			node.setOperator(InfixExpression.Operator.AND);
		}
	}
}