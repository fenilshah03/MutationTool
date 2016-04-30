package infixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorMinusToPlus extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.MINUS)) {
			// convert - to +
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Infix change: convert - to +");
			node.setOperator(InfixExpression.Operator.PLUS);
		}
	}
}