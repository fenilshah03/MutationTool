package infixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorLessToGreaterEqual extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.LESS)) {
			// Convert < to >=
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Infix change: Convert < to >=");
			node.setOperator(InfixExpression.Operator.GREATER_EQUALS);
		}
	}
}