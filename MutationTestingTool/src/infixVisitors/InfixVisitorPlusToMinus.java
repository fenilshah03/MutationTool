package infixVisitors;

import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

class InfixVisitorPlusToMinus extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.PLUS) && node.getLeftOperand() instanceof SimpleName && node.getRightOperand() instanceof SimpleName) {
			// convert + to -
			System.out.println();
			System.out.println("Infix chnage: convert + to -");
			node.setOperator(InfixExpression.Operator.MINUS);
		}
	}
}