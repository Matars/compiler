package ofppackage;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.elementOptions_return;
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
        currentScope = scopes.get(ctx);
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
        
        // why not use visit(ctx.getChild(0)) 
        OFPType LHS = visit(ctx.getChild(0));
        OFPType RHS = visit(ctx.getChild(2));

        if (LHS != RHS) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in assignment: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString());
            System.out.println("RHS: " + RHS.toString());
        }
        return visitChildren(ctx);
    }

    @Override
    public OFPType visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        return visitChildren(ctx);

    }

    @Override
    public OFPType visitDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {
        OFPType LHS = visit(ctx.getChild(1));
        OFPType RHS = visit(ctx.getChild(3));

        if (LHS != RHS) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Type mismatch in declaration: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString().strip());
            System.out.println("RHS: " + RHS.toString().strip());
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
        OFPType sliceType = visit(ctx.getChild(2));
        if (sliceType != OFPType.intType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Array index must be of type int: " + ctx.getText());
        }

        OFPType type = visit(ctx.getChild(0));

        if (type == OFPType.stringType || type == OFPType.charArrayType) {
            return OFPType.charType;
        } else if (type == OFPType.intArrayType) {
            return OFPType.intType;
        } else if (type == OFPType.floatArrayType) {
            return OFPType.floatType;
        } else {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant access array on non array objects: " + ctx.getText());
        }
        return visitChildren(ctx);
    }

    @Override
    public OFPType visitArrayAccess(OFPParser.ArrayAccessContext ctx) {
        OFPType sliceType = visit(ctx.getChild(2));
        if (sliceType != OFPType.intType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Array index must be of type int: " + ctx.getText());
        }

        OFPType type = visit(ctx.getChild(0));
        if (type == OFPType.stringType || type == OFPType.charArrayType) {
            return OFPType.charType;
        } else if (type == OFPType.intArrayType) {
            return OFPType.intType;
        } else if (type == OFPType.floatArrayType) {
            return OFPType.floatType;
        } else {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant access array on non array objects: " + ctx.getText());
        }
        return visitChildren(ctx);
    }

    @Override
    public OFPType visitArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {

        OFPType LHS = visit(ctx.getChild(0).getChild(0));

        if (LHS == OFPType.intArrayType) {
            LHS = OFPType.intType;
        } else if (LHS == OFPType.floatArrayType) {
            LHS = OFPType.floatType;
        } else if (LHS == OFPType.charArrayType) {
            LHS = OFPType.charType;
        } else {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant assign to non array objects: " + ctx.getText());
            System.out.println("LHS: " + LHS.toString().strip());
        }

        for (int i = 0; i < ctx.getChild(0).getChild(4).getChildCount(); i += 2) {
            // check if type of array is correct
            OFPType indexType = visit(ctx.getChild(0).getChild(4).getChild(i));
            if (LHS != indexType) {
                errorCount++;
                System.out.println(errorCount + "\t[TYPE] Type mismatch in array assignment: " + ctx.getText());
                System.out.println("LHS: " + LHS.toString().strip());
                System.out.println("indexType: " + indexType.toString().strip());

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
        OFPType boolType = OFPType.boolType;

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
        FunctionSymbol func = (FunctionSymbol) currentScope.resolve(ctx.getChild(0).getChild(0).getText());

        ArrayList<Symbol> parameters = func.getParameters();
        ArrayList<OFPType> arguments = new ArrayList<>();

        for (int i = 0; i < ctx.getChild(0).getChild(2).getChildCount(); i += 2) {
            arguments.add(visit(ctx.getChild(0).getChild(2).getChild(i)));
        }

        // check if number of parameters match
        if (parameters.size() != arguments.size()) {
            errorCount++;
            System.out.println(
                    errorCount + "\t[TYPE] Number of parameters in function call does not match: " + ctx.getText());
        }

        // check if types of parameters match

        for (int i = 0; i < parameters.size(); i++) {
            OFPType paramType = OFPType.getTypeFor(parameters.get(i).getType().toString());
            OFPType argType = arguments.get(i);

            if (paramType != argType) {
                errorCount++;
                System.out.println(errorCount + "\t[TYPE] Type mismatch in function call: " + ctx.getText());
                System.out.println("paramType: " + paramType.toString());
                System.out.println("argType: " + argType.toString());
            }
        }

        return visitChildren(ctx);
    }

    @Override
    public OFPType visitCallMethod(OFPParser.CallMethodContext ctx) {
        FunctionSymbol func = (FunctionSymbol) currentScope.resolve(ctx.getChild(0).getChild(0).getText());

        ArrayList<Symbol> parameters = func.getParameters();
        ArrayList<OFPType> arguments = new ArrayList<>();

        for (int i = 0; i < ctx.getChild(0).getChild(2).getChildCount(); i += 2) {
            arguments.add(visit(ctx.getChild(0).getChild(2).getChild(i)));
        }

        // check if number of parameters match
        if (parameters.size() != arguments.size()) {
            errorCount++;
            System.out.println(
                    errorCount + "\t[TYPE] Number of parameters in function call does not match: " + ctx.getText());
        }

        // check if types of parameters match

        for (int i = 0; i < parameters.size(); i++) {
            OFPType paramType = OFPType.getTypeFor(parameters.get(i).getType().toString());
            OFPType argType = arguments.get(i);

            if (paramType != argType) {
                errorCount++;
                System.out.println(errorCount + "\t[TYPE] Type mismatch in function call: " + ctx.getText());
                System.out.println("paramType: " + paramType.toString());
                System.out.println("argType: " + argType.toString());
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
        if (LHS == OFPType.stringType || RHS == OFPType.stringType) {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant compare strings: " + ctx.getText());
        }

        if (RHS == null || LHS == null) {
            System.out.println("Something is null, error at " + ctx.getText());
            System.exit(1);
        }

        if (LHS != RHS) {
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
        if (LHS == OFPType.stringType) {
            return OFPType.intType;
        } else if (LHS == OFPType.intArrayType || LHS == OFPType.floatArrayType || LHS == OFPType.charArrayType) {
            return OFPType.intType;
        } else {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant get length of non array objects: " + ctx.getText());
        }

        return visitChildren(ctx);
    }

    @Override
    public OFPType visitNewArray(OFPParser.NewArrayContext ctx) {

        OFPType RHS = null;
        String RHS_string = ctx.getChild(1).getText();

        if (RHS_string.equals("int")) {
            RHS = OFPType.intArrayType;
        } else if (RHS_string.equals("float")) {
            RHS = OFPType.floatArrayType;
        } else if (RHS_string.equals("char")) {
            RHS = OFPType.charArrayType;
        } else {
            errorCount++;
            System.out.println(errorCount + "\t[TYPE] Cant assign to non array objects: " + ctx.getText());
        }

        if (RHS == null) {
            System.out.println("RHS is null for " + ctx.getText());
            System.exit(1);
        }
        return RHS;
    }

    @Override
    public OFPType visitAddsub(OFPParser.AddsubContext ctx) {

        OFPType LHS = visit(ctx.getChild(0));
        OFPType RHS = visit(ctx.getChild(2));

        if (LHS != RHS) {

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
        return visit(ctx.getChild(1));
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

        if (LHS != RHS) {
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
        OFPType type = visit(ctx.getChild(0));
        return type;
    }

    // not sure why casting is needed
    // cant get it to work without casting
    @Override
    public OFPType visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() == OFPParser.ID) {
            // check if function
            if (currentScope.resolve(node.getText()) instanceof FunctionSymbol) {
                FunctionSymbol func = (FunctionSymbol) currentScope.resolve(node.getText());
                return OFPType.getTypeFor(func.getType().toString());

            } else {
                Symbol sym = currentScope.resolve(node.getText());
                return OFPType.getTypeFor(sym.getType().toString());

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
        } else if (node.getText().strip().equals("int[]")) {
            return OFPType.intArrayType;
        } else if (node.getText().strip().equals("float[]")) {
            return OFPType.floatArrayType;
        } else if (node.getText().strip().equals("char[]")) {
            return OFPType.charArrayType;
        } else if (node.getText().strip().equals("void")) {
            return OFPType.voidType;
        }

        return null;

    }

    public void setScopes(ParseTreeProperty scopes) {
        this.scopes = scopes;
    }

}