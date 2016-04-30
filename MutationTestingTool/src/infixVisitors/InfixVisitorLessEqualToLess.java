package infixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.InfixExpression;

class InfixVisitorLessEqualToLess extends InfixVisitor {
	void changeInfixNodeOperator(InfixExpression node) {
		if (node.getOperator().equals(InfixExpression.Operator.LESS_EQUALS)) {
			// convert <= to <
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Infix change: convert <= to <");
			node.setOperator(InfixExpression.Operator.LESS);
		}
	}
}