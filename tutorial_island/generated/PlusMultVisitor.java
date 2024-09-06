// Generated from /Users/matar/School/compiler/tutorial_island/PlusMult.g4 by ANTLR 4.13.1
    // Define name of package for generated Java files. 
    package generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PlusMultParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PlusMultVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PlusMultParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(PlusMultParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link PlusMultParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PlusMultParser.ExprContext ctx);
}