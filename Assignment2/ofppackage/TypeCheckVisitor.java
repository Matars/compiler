package ofppackage;

import java.util.List;

import org.antlr.v4.parse.ANTLRParser.id_return;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseVisitor;
import generated.OFPParser;

public class TypeCheckVisitor extends OFPBaseVisitor<OFPType> {

    private int errorCount = 0;
    private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>(); // Node-to-scope mapping
    private Scope currentScope;
    private FunctionSymbol currentFunction;

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitStart(OFPParser.StartContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitMainFunc(OFPParser.MainFuncContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitMethodFunc(OFPParser.MethodFuncContext ctx) {

        // get function block scope
        currentScope = scopes.get(ctx.getChild(3));

        // set current funciton
        currentFunction = (FunctionSymbol) currentScope.resolve(ctx.getChild(1).getText());

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitStmtBlock(OFPParser.StmtBlockContext ctx) {
        currentScope = scopes.get(ctx);
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArgList(OFPParser.ArgListContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitExprList(OFPParser.ExprListContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitAssignStmt(OFPParser.AssignStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public OFPType visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        // could be made better
        // still cant compare OPFType objects, defaultign to string comparison

        String LHS_string = ctx.getChild(0).getText().strip();
        if (LHS_string.contains("(")) {
            return visit(ctx.getChild(0));
        }
        String RHS_string = ctx.getChild(2).getText().strip();

        if (!RHS_string.equals(";")) {
            RHS_string = ctx.getChild(3).getText().strip();

            if (RHS_string.contains("new")) {
                RHS_string = ctx.getChild(3).getChild(1).getText().strip();
                LHS_string = LHS_string.replace("[]", "").strip();
            } else {
                if (ctx.getChild(3).getText().contains("[")) {

                    RHS_string = visit(ctx.getChild(3).getChild(0)).toString().strip();
                    // remove [] from RHS
                    RHS_string = RHS_string.replace("[]", "");

                    // pullling one char from string array
                    if (RHS_string.equals("string")) {
                        RHS_string = "char";
                    }

                } else if (ctx.getChild(3).getText().contains(".length")) {
                    RHS_string = "int";
                } else {
                    if (ctx.getChild(3).getText().contains("(")) {
                        return visit(ctx.getChild(3));
                    } else {
                        RHS_string = visit(ctx.getChild(3)).toString().strip();
                    }
                }

                // not sure if this is right, sometimes RHS_type is null (assuming on far right
                // of expression)

                if (!LHS_string.equals(RHS_string)) {
                    errorCount++;
                    System.out.println(errorCount + "\t[TYPE] Type mismatch in declaration: " +
                            ctx.getText());
                    System.out.println("LHS: " + LHS_string);
                    System.out.println("RHS: " + RHS_string);
                }

            }

        }

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArrayAccessStmt(OFPParser.ArrayAccessStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public OFPType visitArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {

        OFPType LHS = visit(ctx.getChild(0).getChild(0));
        String LHS_string = LHS.toString().strip();

        String currentType = LHS_string.replace("[]", "");

        for (int i = 0; i < ctx.getChild(0).getChild(4).getChildCount(); i += 2) {
            // check if type of array is correct
            String indexType = visit(ctx.getChild(0).getChild(4).getChild(i)).toString().strip();
            if (!indexType.equals(currentType)) {
                errorCount++;
                System.out.println(errorCount + "\t[TYPE] Type mismatch in array assignment: " + ctx.getText());
            }
        }
        // OFPType RHS = visit(ctx.getChild(0).getChild(2));
        // System.out.println("RHS: " + RHS.toString().strip());

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitIfStmt(OFPParser.IfStmtContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitWhileStmt(OFPParser.WhileStmtContext ctx) {

        OFPType condition = visit(ctx.getChild(1).getChild(1));
        OFPType boolType = ofppackage.OFPType.boolType;

        if (condition != boolType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in while condition: " + ctx.getText());
        }

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitReturnStmt(OFPParser.ReturnStmtContext ctx) {
        OFPType returnExpr = visit(ctx.getChild(1));

        String returnExprString = returnExpr.toString().strip();
        // get type of current func
        String funcTypeString = currentFunction.getType().toString().strip();

        if (!returnExprString.equals(funcTypeString)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in return statement: " + ctx.getText());
            System.out.println("funcType: " + funcTypeString);
            System.out.println("returnExpr: " + returnExprString);
        }

        return visitChildren(ctx);
    }

    @Override
    public OFPType visitCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        // get function symbol
        FunctionSymbol func = (FunctionSymbol) currentScope.resolve(ctx.getChild(0).getChild(0).getText());
        System.out.println("function: " + func.getName());

        List<OFPType> expectedParams = (List<OFPType>) func.getParameterTypes();

        if (expectedParams.size() == 0) {
            return visitChildren(ctx);
        }

        int j = 0;
        for (int i = 0; i <= expectedParams.size(); i += 2) {

            String actualParam = visit(ctx.getChild(0).getChild(2).getChild(i)).toString().strip();
            String expectedParam = expectedParams.get(j).toString().strip();
            j++;

            // debug

            if (!expectedParam.equals(actualParam)) {
                errorCount++;
                System.out.println(errorCount + "\t[TYPE] Type mismatch in function call: " + ctx.getText());
            }
        }

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitPrintStmt(OFPParser.PrintStmtContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitCallMethod(OFPParser.CallMethodContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitStrExpr(OFPParser.StrExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitCharExpr(OFPParser.CharExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitComp(OFPParser.CompContext ctx) {

        OFPType LHS = visit(ctx.getChild(0));
        OFPType RHS = visit(ctx.getChild(2));

        if (ctx.getChild(2).getChild(1) != null && ctx.getChild(2).getChild(1).getText().strip().equals(".length")) {
            RHS = OFPType.intType;
        }

        // if inlcude [ its index access
        if (ctx.getChild(0).getText().contains("[")) {
            LHS = visit(ctx.getChild(0).getChild(0));
        }
        if (ctx.getChild(2).getText().contains("[")) {
            RHS = visit(ctx.getChild(2).getChild(0));
        }

        String LHS_string = LHS.toString().strip();
        String RHS_string = RHS.toString().strip();

        // chech that LHS and RHS are not string
        if (LHS == ofppackage.OFPType.stringType || RHS == ofppackage.OFPType.stringType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant compare strings: " + ctx.getText());
        }

        if (!LHS_string.equals(RHS_string)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] here Type mismatch in expression: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString());
            System.out.println("RHS: " + RHS.toString());
        }

        return OFPType.boolType;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitNegation(OFPParser.NegationContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitIntExpr(OFPParser.IntExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitLength(OFPParser.LengthContext ctx) {
        OFPType LHS = visit(ctx.getChild(0));
        String LHS_string = LHS.toString().strip();

        if (!LHS_string.equals("int[]") && !LHS_string.equals("float[]") && !LHS_string.equals("char[]")
                && !LHS_string.equals("string")) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE]Cant .length on non array or string objects: " + ctx.getText());
        }
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitNewArray(OFPParser.NewArrayContext ctx) {
        OFPType expr = visit(ctx.getChild(3));
        String expr_string = expr.toString().strip();

        // should compare by OFPType, same reason as the others, pls fix asap
        if (!expr_string.equals("int")) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant create array with non int size: " + ctx.getText());
        }

        return visitChildren(ctx);
    }

    @Override
    public OFPType visitAddsub(OFPParser.AddsubContext ctx) {

        OFPType LHS;
        OFPType RHS;
        // check if includes .length
        if (ctx.getChild(0).getText().contains(".length")) {
            LHS = OFPType.intType;
        } else {
            if (ctx.getChild(0).getText().contains("(")) {
                return visit(ctx.getChild(0).getChild(0));
            } else {
                LHS = visit(ctx.getChild(0));
            }
        }

        if (ctx.getChild(2).getText().contains(".length")) {
            RHS = OFPType.intType;
        } else {
            if (ctx.getChild(2).getText().contains("(")) {
                return visit(ctx.getChild(2).getChild(0));
            } else {
                RHS = visit(ctx.getChild(2));
            }

        }

        String LHS_string = LHS.toString();
        String RHS_string = RHS.toString();

        if (!LHS_string.equals(RHS_string)) {

            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in expression: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString());
            System.out.println("RHS: " + RHS.toString());
        }

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitParenthesis(OFPParser.ParenthesisContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitFloatExpr(OFPParser.FloatExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitMultdiv(OFPParser.MultdivContext ctx) {

        OFPType LHS = visit(ctx.getChild(0));
        OFPType RHS = visit(ctx.getChild(2));

        String LHS_string = LHS.toString();
        String RHS_string = RHS.toString();

        if (!LHS_string.equals(RHS_string)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in expression: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString());
            System.out.println("RHS: " + RHS.toString());
        }

        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArrayAccess(OFPParser.ArrayAccessContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitBoolExpr(OFPParser.BoolExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitIdExpr(OFPParser.IdExprContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitPrint(OFPParser.PrintContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitAssign(OFPParser.AssignContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArrayAssign(OFPParser.ArrayAssignContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArrayAcces(OFPParser.ArrayAccesContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitMethodCall(OFPParser.MethodCallContext ctx) {
        return (OFPType) currentScope.resolve(ctx.getChild(0).getText()).getType();
    }

    // not sure why casting is needed
    // cant get it to work without casting
    @Override
    public OFPType visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() == OFPParser.ID) {
            // check if function
            if (currentScope.resolve(node.getText()) instanceof FunctionSymbol) {
                // this is wrong
                FunctionSymbol func = (FunctionSymbol) currentScope.resolve(node.getText());
                return OFPType.getTypeFor(func.getType().toString());

            } else {
                Symbol sym = currentScope.resolve(node.getText());
                if (sym == null) {
                    errorCount++;
                    System.out.println(errorCount + "\t[TYPE] Undeclared variable in argument: " + node.getText());
                } else {
                    // this is correct way of return the type of variable,
                    // should be the same done for above, but not sure why it is not working
                    // defaulting to resolving types by string comparison and not
                    // OFPType comparison, pls fix
                    return OFPType.getTypeFor(sym.getType().toString());
                }
                // exit program
                System.exit(1);

            }
        } else if (node.getSymbol().getType() == OFPParser.INT) {
            return OFPType.intType;
        } else if (node.getSymbol().getType() == OFPParser.FLOAT) {
            return OFPType.floatType;
        } else if (node.getSymbol().getType() == OFPParser.CHAR) {
            return OFPType.charType;
        } else if (node.getSymbol().getType() == OFPParser.BOOL) {
            return OFPType.boolType;
        } else if (node.getSymbol().getType() == OFPParser.STRING) {
            return (OFPType) ofppackage.OFPType.stringType;
        } else if (node.getText().strip().equals("int[]")) {
            return (OFPType) ofppackage.OFPType.intArrayType;
        } else if (node.getText().strip().equals("float[]")) {
            return (OFPType) ofppackage.OFPType.floatArrayType;
        } else if (node.getText().strip().equals("char[]")) {
            return (OFPType) ofppackage.OFPType.charArrayType;
        }

        return null;
    }

    public void setScopes(ParseTreeProperty scopes) {
        this.scopes = scopes;
    }

}