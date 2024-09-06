// Generated from /Users/jlnmsi/Software/java/4DV507-2024/Expressions/Expressions.g4 by ANTLR 4.13.1
    // Define name of package for generated Java files. 
    package generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionsParser}.
 */
public interface ExpressionsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ExpressionsParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ExpressionsParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(ExpressionsParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(ExpressionsParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(ExpressionsParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(ExpressionsParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(ExpressionsParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(ExpressionsParser.AssignContext ctx);
}