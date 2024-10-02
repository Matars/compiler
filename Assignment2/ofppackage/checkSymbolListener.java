package ofppackage;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseListener;
import generated.OFPParser;

public class checkSymbolListener extends OFPBaseListener {

    private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>(); // Node-to-scope mapping
    private Scope currentScope;
    private Scope globalScope; // Used to resolve function symbols
    private String currentFunction; // For logging purposes
    private boolean hasReturnStatement = false;

    int errorCount = 0; // Keep track of the total number of errors

    @Override
    public void enterStart(OFPParser.StartContext ctx) {
        this.globalScope = scopes.get(ctx);
        this.currentScope = globalScope;
    }

    @Override
    public void exitStart(OFPParser.StartContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterMainFunc(OFPParser.MainFuncContext ctx) {
        currentFunction = ctx.getChild(1).getText(); // Function name
        currentScope = scopes.get(ctx);
        hasReturnStatement = false;
    }

    @Override
    public void exitMainFunc(OFPParser.MainFuncContext ctx) {
        if (!hasReturnStatement && !currentFunction.equals("main")) {
            errorCount++;
            System.out.println(errorCount + "\t[CHECK] Missing return statement in function: " + currentFunction);
        }
        currentScope = globalScope;
    }

    @Override
    public void enterMethodFunc(OFPParser.MethodFuncContext ctx) {
        currentFunction = ctx.getChild(1).getText();
        currentScope = scopes.get(ctx);
        hasReturnStatement = false;
    }

    public void exitMethodFunc(OFPParser.MethodFuncContext ctx) {
        if (!hasReturnStatement) {
            errorCount++;
            System.out.println(errorCount + "\t[CHECK] Missing return statement in function: " + currentFunction);
        }
        currentScope = globalScope;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterStmtBlock(OFPParser.StmtBlockContext ctx) {
        currentScope = scopes.get(ctx);
    }

    @Override
    public void exitStmtBlock(OFPParser.StmtBlockContext ctx) {
    }

    @Override
    public void enterArgList(OFPParser.ArgListContext ctx) {
        List<TerminalNode> names = ctx.ID();

        for (TerminalNode node : names) {
            String varName = node.getText();
            Symbol sym = currentScope.resolve(varName);

        }
    }

    @Override
    public void enterDeclareStmt(OFPParser.DeclareStmtContext ctx) {

    }

    @Override
    public void enterCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        String name = ctx.getChild(0).getText().replaceAll("\\(.*\\);", "");
        Symbol sym = globalScope.resolve(name);
        if (sym == null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[CHECK] Undeclared function in function " + currentFunction + ": " + name);
        } else if (sym instanceof FunctionSymbol) {
            FunctionSymbol func = (FunctionSymbol) sym;

            int expectedArgs = func.getParameterCount();
            int actualArgs;

            String args = ctx.getChild(0).getChild(2).getText();

            // when 0 args child position seems to be ")". So we check for that
            if (args.equals(")")) {
                actualArgs = 0;
            } else {
                actualArgs = ctx.getChild(0).getChild(2).getText().split(",").length;
            }

            if (expectedArgs != actualArgs) {
                errorCount++;
                System.out.println(errorCount + "\t[CHECK] Incorrect number of arguments for function " + name
                        + ". Expected: " + expectedArgs + ", Got: " + actualArgs);
            }
        }
    }

    @Override
    public void enterCallMethod(OFPParser.CallMethodContext ctx) {
        String name = ctx.getChild(0).getText();
        name = name.replaceAll("\\(.*\\)", "");

        if (globalScope.resolve(name) == null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[CHECK] Undeclared function in function " + currentFunction + ": " + name);
        }

    }

    @Override
    public void enterIdExpr(OFPParser.IdExprContext ctx) {
        String name = ctx.getText();
        Symbol sym = currentScope.resolve(name);
        if (sym == null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[CHECK] Undeclared variable in function " + currentFunction + ": " + name);
        }
    }

    @Override
    public void enterReturnStmt(OFPParser.ReturnStmtContext ctx) {
        hasReturnStatement = true;
    }

    public void ToString() {
        currentScope.ToString();
    }

    public void setScopes(ParseTreeProperty<Scope> scopes) {
        this.scopes = scopes;
    }

}
