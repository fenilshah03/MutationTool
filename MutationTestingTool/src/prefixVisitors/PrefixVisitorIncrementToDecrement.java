package prefixVisitors;

import main.GeoProjectInformation;

import org.eclipse.jdt.core.dom.PrefixExpression;

class PrefixVisitorIncrementToDecrement extends PrefixVisitor {
	void changePrefixNodeOperator(PrefixExpression node) {
		if (node.getOperator().equals(PrefixExpression.Operator.INCREMENT)) {
			// convert ++ to --
			GeoProjectInformation.buildReport();
			GeoProjectInformation.buildReport("Prefix change: convert ++ to --");
			node.setOperator(PrefixExpression.Operator.DECREMENT);
		}
	}
}