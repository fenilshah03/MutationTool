package postfixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.PostfixExpression;

class PostfixVisitorIncrementToDecrement extends PostfixVisitor {
	void changePostfixNodeOperator(PostfixExpression node) {
		if (node.getOperator().equals(PostfixExpression.Operator.INCREMENT)) {
			// convert ++ to --
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Postfix change: convert ++ to --");
			node.setOperator(PostfixExpression.Operator.DECREMENT);
		}
	}
}