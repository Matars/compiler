// Generated from /Users/jlnmsi/Software/java/4DV507-2024/PlusMult/PlusMult.g4 by ANTLR 4.13.1
    // Define name of package for generated Java files. 
    package generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PlusMultParser}.
 */
public interface PlusMultListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PlusMultParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(PlusMultParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link PlusMultParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(PlusMultParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link PlusMultParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PlusMultParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PlusMultParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PlusMultParser.ExprContext ctx);
}