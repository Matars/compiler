// Generated from /Users/matar/School/compiler/Assignment1/Expressions.g4 by ANTLR 4.13.1
    // Define name of package for generated Java files. 
    package generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ExpressionsParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#maindef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaindef(ExpressionsParser.MaindefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(ExpressionsParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(ExpressionsParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#stmtBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtBlock(ExpressionsParser.StmtBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#exprBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprBlock(ExpressionsParser.ExprBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#argBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgBlock(ExpressionsParser.ArgBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(ExpressionsParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#newArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArray(ExpressionsParser.NewArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#declare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclare(ExpressionsParser.DeclareContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(ExpressionsParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#ifstmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstmt(ExpressionsParser.IfstmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#elsePart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsePart(ExpressionsParser.ElsePartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#whilestmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhilestmt(ExpressionsParser.WhilestmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#returnstmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnstmt(ExpressionsParser.ReturnstmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(ExpressionsParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#methodDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDef(ExpressionsParser.MethodDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionsParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(ExpressionsParser.PrintContext ctx);
}