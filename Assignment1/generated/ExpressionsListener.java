// Generated from /Users/matar/School/compiler/Assignment1/Expressions.g4 by ANTLR 4.13.1
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
	 * Enter a parse tree produced by {@link ExpressionsParser#maindef}.
	 * @param ctx the parse tree
	 */
	void enterMaindef(ExpressionsParser.MaindefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#maindef}.
	 * @param ctx the parse tree
	 */
	void exitMaindef(ExpressionsParser.MaindefContext ctx);
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
	 * Enter a parse tree produced by {@link ExpressionsParser#returnstmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnstmt(ExpressionsParser.ReturnstmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#returnstmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnstmt(ExpressionsParser.ReturnstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(ExpressionsParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(ExpressionsParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#methodDef}.
	 * @param ctx the parse tree
	 */
	void enterMethodDef(ExpressionsParser.MethodDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#methodDef}.
	 * @param ctx the parse tree
	 */
	void exitMethodDef(ExpressionsParser.MethodDefContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link ExpressionsParser#println}.
	 * @param ctx the parse tree
	 */
	void enterPrintln(ExpressionsParser.PrintlnContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionsParser#println}.
	 * @param ctx the parse tree
	 */
	void exitPrintln(ExpressionsParser.PrintlnContext ctx);
}