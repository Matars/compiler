// Generated from /Users/matar/School/compiler/tutorial_island/Expressions.g4 by ANTLR 4.13.1
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
	 * Enter a parse tree produced by {@link ExpressionsParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(ExpressionsParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(ExpressionsParser.StmtContext ctx);
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
	 * Enter a parse tree produced by {@link ExpressionsParser#declare}.
	 * @param ctx the parse tree
	 */
	void enterDeclare(ExpressionsParser.DeclareContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#declare}.
	 * @param ctx the parse tree
	 */
	void exitDeclare(ExpressionsParser.DeclareContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#update}.
	 * @param ctx the parse tree
	 */
	void enterUpdate(ExpressionsParser.UpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#update}.
	 * @param ctx the parse tree
	 */
	void exitUpdate(ExpressionsParser.UpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#ifstmt}.
	 * @param ctx the parse tree
	 */
	void enterIfstmt(ExpressionsParser.IfstmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#ifstmt}.
	 * @param ctx the parse tree
	 */
	void exitIfstmt(ExpressionsParser.IfstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void enterElsePart(ExpressionsParser.ElsePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void exitElsePart(ExpressionsParser.ElsePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#whilestmt}.
	 * @param ctx the parse tree
	 */
	void enterWhilestmt(ExpressionsParser.WhilestmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#whilestmt}.
	 * @param ctx the parse tree
	 */
	void exitWhilestmt(ExpressionsParser.WhilestmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(ExpressionsParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(ExpressionsParser.PrintContext ctx);
}