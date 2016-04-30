package main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;

import infixVisitors.*;
import prefixVisitors.*;
import postfixVisitors.*;

public class ExpressionVisitor {
	public static List<ASTVisitor> getVisitors() {
		List<ASTVisitor> visitors = new ArrayList<ASTVisitor>();

		// collection of all types of visitors i.e. possible mutant operations
		visitors.addAll(InfixVisitor.getInfixVisitors());
		visitors.addAll(PostfixVisitor.getPostfixVisitors());
		visitors.addAll(PrefixVisitor.getPrefixVisitors());

		return visitors;
	}
}
