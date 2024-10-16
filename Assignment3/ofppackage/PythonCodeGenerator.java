package ofppackage;

import java.util.HashMap;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseVisitor;
import generated.OFPParser;

public class PythonCodeGenerator extends OFPBaseVisitor<String> {
    Scope currentScope;
    int depth;
    private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>(); // Node-to-scope mapping
    private HashMap<Integer, String> indentCache = new HashMap<Integer, String>();

    private String indent(int indentLevel) {
        String ind = indentCache.get(indentLevel);
        if (ind == null) {
            ind = "";
            for (int i = 0; i < indentLevel; i++)
                ind += "    ";
            indentCache.put(indentLevel, ind);
        }
        return ind;
    }

    public void setScopes(ParseTreeProperty<Scope> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String visitStart(OFPParser.StartContext ctx) {
        currentScope = scopes.get(ctx);
        StringBuilder buf = new StringBuilder(); // For generated Python code
        // main function must be generated last!
        ParserRuleContext main = null;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParserRuleContext p = (ParserRuleContext) ctx.getChild(i);
            String fName = p.getChild(1).getText();
            if (fName.equals("main")) {
                main = p; // defer visit until after the rest of the functions
                continue; // not sure if this is needed, fixed main running twice
            }
            buf.append(visit(p));
        }
        if (main != null) {
            buf.append(visit(main));
        }
        System.out.println(buf.toString());
        return buf.toString();
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
    public String visitMainFunc(OFPParser.MainFuncContext ctx) {
        currentScope = scopes.get(ctx);
        String start = "#\n# Program entry point - main\n#\n";
        depth = 0; // No indentation expected here ==> reset depth
        String body = visit(ctx.getChild(3)); // String for main body
        currentScope = currentScope.getEnclosingScope();
        return start + body;
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
    public String visitMethodFunc(OFPParser.MethodFuncContext ctx) {
        String funcstr = "def" + " " + ctx.getChild(1).getText();


        // get variable names in arglist with loop, index 2, 4, 6, ... with comma in between
        String argList = "";
        int childCount = ctx.getChild(2).getChildCount();

        for (int i = 2; i < childCount; i += 3) {
            argList += ctx.getChild(2).getChild(i).getText();
            if (i < childCount - 3) {
                argList += ", ";
            }
        }
        

        funcstr += "(" + argList + ")";


        funcstr += ":\n";
        funcstr += visit(ctx.getChild(3));

        return funcstr;
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
    public String visitStmtBlock(OFPParser.StmtBlockContext ctx) {
        currentScope = scopes.get(ctx);
        depth++;
        StringBuilder buf = new StringBuilder();
        for (int i = 1; i < ctx.getChildCount() - 1; i++) { // Visit statements
            String stmt = indent(depth) + visit(ctx.getChild(i));
            buf.append(stmt);
        }
        depth--;
        currentScope = currentScope.getEnclosingScope();
        return buf.toString();
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
    public String visitArgList(OFPParser.ArgListContext ctx) {
        return ctx.getText();
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
    public String visitExprList(OFPParser.ExprListContext ctx) {
        return ctx.getText();
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
    public String visitAssignStmt(OFPParser.AssignStmtContext ctx) {
        return visit(ctx.getChild(0).getChild(0)) + " = " + visit(ctx.getChild(0).getChild(2)) + "\n";

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
    public String visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        return "pass";
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
    public String visitDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {
        return visit(ctx.getChild(1)) + " = " + visit(ctx.getChild(3)) + "\n";
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
    public String visitArrayAccessStmt(OFPParser.ArrayAccessStmtContext ctx) {
        return ctx.getText();
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
    public String visitArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {
        return ctx.getText();
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
    public String visitIfStmt(OFPParser.IfStmtContext ctx) {
        System.out.println(depth);
        String ifString = visit(ctx.getChild(0))  + visit(ctx.getChild(1)) + ":\n";
        ifString += visit(ctx.getChild(2));

        return ifString;
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
    public String visitWhileStmt(OFPParser.WhileStmtContext ctx) {
        String whileString = visit(ctx.getChild(0)) + visit(ctx.getChild(1)) + ":\n";

        for (int i = 1; i < ctx.getChild(2).getChildCount() - 1; i++) {

            whileString += indent(depth) + visit(ctx.getChild(2).getChild(i));
        }

        return whileString;
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
    public String visitReturnStmt(OFPParser.ReturnStmtContext ctx) {
        return "return " + visit(ctx.getChild(1)) + "\n";
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
    public String visitCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        return ctx.getText();
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
    public String visitPrintStmt(OFPParser.PrintStmtContext ctx) {
        return "print(" + visit(ctx.getChild(0).getChild(2)) + ")" + "\n";
    }

    @Override
    public String visitPrintlnStmt(OFPParser.PrintlnStmtContext ctx) {
        return "print(" + visit(ctx.getChild(0).getChild(2)) + ", end='\\n')" + "\n";
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
    public String visitCallMethod(OFPParser.CallMethodContext ctx) {
        return ctx.getText();
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
    public String visitStrExpr(OFPParser.StrExprContext ctx) {
        return ctx.getText();
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
    public String visitCharExpr(OFPParser.CharExprContext ctx) {
        return ctx.getText();
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
    public String visitComp(OFPParser.CompContext ctx) {
        return ctx.getText();
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
    public String visitNegation(OFPParser.NegationContext ctx) {
        return ctx.getText();
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
    public String visitIntExpr(OFPParser.IntExprContext ctx) {
        return ctx.getText();
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
    public String visitLength(OFPParser.LengthContext ctx) {
        return ctx.getText();
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
    public String visitNewArray(OFPParser.NewArrayContext ctx) {
        return ctx.getText();
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
    public String visitAddsub(OFPParser.AddsubContext ctx) {
        String str = "";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            str += visit(ctx.getChild(i));
        }
        return str;
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
    public String visitParenthesis(OFPParser.ParenthesisContext ctx) {
        return ctx.getText();
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
    public String visitFloatExpr(OFPParser.FloatExprContext ctx) {
        return ctx.getText();
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
    public String visitMultdiv(OFPParser.MultdivContext ctx) {
        String str = "";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            str += visit(ctx.getChild(i));
        }
        return str;
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
    public String visitArrayAccess(OFPParser.ArrayAccessContext ctx) {
        return ctx.getText();
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
    public String visitBoolExpr(OFPParser.BoolExprContext ctx) {
        String b = ctx.getChild(0).getText();
        if (b.equals("true"))
            return "True";
        else
            return "False";
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
    public String visitIdExpr(OFPParser.IdExprContext ctx) {
        return ctx.getText();
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
    public String visitArrayAssign(OFPParser.ArrayAssignContext ctx) {
        return ctx.getText();
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
    public String visitArrayAcces(OFPParser.ArrayAccesContext ctx) {
        return ctx.getText();
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
    public String visitMethodCall(OFPParser.MethodCallContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        return node.getText();
    }
}