package infixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

class InfixVisitorPlusToMinus extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.PLUS) && node.getLeftOperand() instanceof SimpleName && node.getRightOperand() instanceof SimpleName) {
			// convert + to -
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Infix change: convert + to -");
			node.setOperator(InfixExpression.Operator.MINUS);
		}
	}
}