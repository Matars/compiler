package ofppackage;

import java.util.List;

import org.abego.treelayout.internal.util.java.lang.string.StringUtil;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseListener;
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

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {

        String LHS = ctx.getChild(0).toString().strip();
        String RHS = ctx.getChild(2).toString().strip();

        if (!RHS.equals(";")) {
            OFPType RHS_type = visit(ctx.getChild(3));
            // not sure if this is right, sometimes RHS_type is null (assuming on far right
            // of expression)
            if (!(RHS_type == null)) {

                String RHS_type_string = RHS_type.toString();

                if (!LHS.equals(RHS_type_string)) {
                    errorCount++;
                    System.out.println(errorCount + "\t[TYPE] Type mismatch in declaration: " +
                            ctx.getText());
                    System.out.println("LHS: " + LHS);
                    System.out.println("RHS: " + RHS_type);
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

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public OFPType visitArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {
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
        String conditionString = condition.toString().strip();

        if (!conditionString.equals(ofppackage.OFPType.boolType.toString().strip())) {
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

        List<OFPType> expectedParams = (List<OFPType>) func.getParameterTypes();

        int j = 0;
        for (int i = 0; i <= expectedParams.size(); i += 2) {

            String actualParam = visit(ctx.getChild(0).getChild(2).getChild(i)).toString().strip();
            String expectedParam = expectedParams.get(j).toString().strip();
            j++;

            // debug
            System.out.println("expected: " + expectedParam);
            System.out.println("actual: " + actualParam);

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

        // chech that LHS and RHS are not string
        if (LHS == ofppackage.OFPType.stringType || RHS == ofppackage.OFPType.stringType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant compare strings: " + ctx.getText());
        }

        String LHS_string = LHS.toString().strip();
        String RHS_string = RHS.toString().strip();

        if (!LHS_string.equals(RHS_string)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in expression: " + ctx.getText());
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

        if (LHS != ofppackage.OFPType.stringType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant .length on non array or string objects: " + ctx.getText());
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
    public OFPType visitAddsub(OFPParser.AddsubContext ctx) {

        OFPType LHS = visit(ctx.getChild(0));
        OFPType RHS = visit(ctx.getChild(2));

        String LHS_string = LHS.toString();
        String RHS_string = RHS.toString();

        if (!LHS_string.equals(RHS_string)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in expression: " + ctx.getText());
            System.out.println("LHS: " + LHS_string);
            System.out.println("RHS: " + RHS_string);
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
        System.out.println("parenthesis: " + ctx.getText());
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

        String LHS_string = LHS.toString().strip();
        String RHS_string = RHS.toString().strip();

        if (!LHS_string.equals(RHS_string)) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in expression: " + ctx.getText());
            System.out.println("LHS: " + LHS_string);
            System.out.println("RHS: " + RHS_string);
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
                return (OFPType) ((FunctionSymbol) currentScope.resolve(node.getText())).getType();
            } else {
                Symbol sym = currentScope.resolve(node.getText());
                return (OFPType) sym.getType();
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
            return OFPType.stringType;
        }

        return null;
    }

    public void setScopes(ParseTreeProperty scopes) {
        this.scopes = scopes;
    }

}