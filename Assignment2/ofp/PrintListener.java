package ofp;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseListener;
import generated.OFPParser;

public class PrintListener extends OFPBaseListener {

    private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>(); // Node-to-scope mapping
    private Scope currentScope;
    private Scope globalScope = null; // Used to resolve function symbols
    private String currentFunction; // For logging purposes
    public boolean print = false;
    int errorCount = 0; // Keep track of the total number of errors

    public int getErrorCount() {
        return errorCount;
    }

    public void printIndented(ParserRuleContext ctx) {
        String tab = "    ";
        String indent = tab.repeat(ctx.depth());

        if (currentScope == null) {
            System.out.println(indent + ctx.getClass().getSimpleName() + "\t\t\t\t\t Scope: null");
            return;
        } else {
            System.out.println(indent + ctx.getClass().getSimpleName() + "\t\t\t\t\t Scope: " + currentScope.name);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterStart(OFPParser.StartContext ctx) {
        globalScope = new Scope(null); // Create a global scope with no parent
        currentScope = globalScope;
        scopes.put(ctx, currentScope); // Assign the global scope to the StartContext
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
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

        // Check for duplicate function names
        if (globalScope.resolve(currentFunction) != null) {
            errorCount++;
            System.out.println(errorCount + "\tDuplicate function declaration: " + currentFunction);
        } else {
            globalScope.addSymbol(new FunctionSymbol(currentFunction));
            Scope mainScope = new Scope(globalScope);
            mainScope.setName("main");
            currentScope = mainScope;
            scopes.put(ctx, mainScope);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitMainFunc(OFPParser.MainFuncContext ctx) {
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
    public void enterMethodFunc(OFPParser.MethodFuncContext ctx) {
        currentFunction = ctx.getChild(1).getText(); // Function name

        // Check for duplicate function names
        if (globalScope.resolve(currentFunction) != null) {
            errorCount++;
            System.out.println(errorCount + "\tDuplicate function declaration: " + currentFunction);
        } else {
            globalScope.addSymbol(new FunctionSymbol(currentFunction));
            Scope funcScope = new Scope(globalScope);
            funcScope.setName("funciton" + ctx.start.getLine());
            currentScope = funcScope;
            scopes.put(ctx, funcScope);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitMethodFunc(OFPParser.MethodFuncContext ctx) {
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
        Scope blockScope = new Scope(currentScope);
        currentScope = blockScope;
        currentScope.setName("block" + ctx.start.getLine());
        scopes.put(ctx, blockScope);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitStmtBlock(OFPParser.StmtBlockContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArgList(OFPParser.ArgListContext ctx) {
        // get variable names
        List<TerminalNode> name = ctx.ID();
        for (TerminalNode n : name) {
            String varName = n.getText();
            if (currentScope.resolveLocally(varName) != null) {
                errorCount++;
                System.out.println(
                        errorCount + "\tDuplicate declaration in function " + currentFunction + ": " + varName);
            } else {
                currentScope.addSymbol(new Symbol(varName));
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArgList(OFPParser.ArgListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterExprList(OFPParser.ExprListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitExprList(OFPParser.ExprListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAssignStmt(OFPParser.AssignStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAssignStmt(OFPParser.AssignStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        String name = ctx.getChild(1).getText(); // Variable name

        if (currentScope.resolveLocally(name) != null) {
            errorCount++;
            System.out.println(errorCount + "\tDuplicate declaration in function " + currentFunction + ": " + name);
        } else {
            currentScope.addSymbol(new Symbol(name));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArrayAccessStmt(OFPParser.ArrayAccessStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArrayAccessStmt(OFPParser.ArrayAccessStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterIfStmt(OFPParser.IfStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitIfStmt(OFPParser.IfStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterWhileStmt(OFPParser.WhileStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitWhileStmt(OFPParser.WhileStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterReturnStmt(OFPParser.ReturnStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitReturnStmt(OFPParser.ReturnStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        // get ctx func name
        String name = ctx.getChild(0).getText();
        // the reges is used to strip foo(); to foo
        // there should be a better way to do this
        name = name.replaceAll("\\(.*\\);", "");

        Symbol sym = globalScope.resolve(name);

        if (sym == null || !(sym instanceof FunctionSymbol)) {
            errorCount++;
            System.out.println(errorCount + "\t (call methodstmt) Undeclared function call in function " +
                    currentFunction + ": " + name);

        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterPrintStmt(OFPParser.PrintStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitPrintStmt(OFPParser.PrintStmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCallMethod(OFPParser.CallMethodContext ctx) {
        String name = ctx.getChild(0).getText(); // Function name
        Symbol sym = globalScope.resolve(name);

        if (sym == null || !(sym instanceof FunctionSymbol)) {
            errorCount++;
            System.out.println(errorCount + "\t (call method) Undeclared function call in function " +
                    currentFunction + ": " + name);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCallMethod(OFPParser.CallMethodContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterStrExpr(OFPParser.StrExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitStrExpr(OFPParser.StrExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCharExpr(OFPParser.CharExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCharExpr(OFPParser.CharExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterComp(OFPParser.CompContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitComp(OFPParser.CompContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNegation(OFPParser.NegationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNegation(OFPParser.NegationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterIntExpr(OFPParser.IntExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitIntExpr(OFPParser.IntExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterLength(OFPParser.LengthContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitLength(OFPParser.LengthContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNewArray(OFPParser.NewArrayContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNewArray(OFPParser.NewArrayContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAddsub(OFPParser.AddsubContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAddsub(OFPParser.AddsubContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterParenthesis(OFPParser.ParenthesisContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitParenthesis(OFPParser.ParenthesisContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterFloatExpr(OFPParser.FloatExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitFloatExpr(OFPParser.FloatExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterMultdiv(OFPParser.MultdivContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitMultdiv(OFPParser.MultdivContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArrayAccess(OFPParser.ArrayAccessContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArrayAccess(OFPParser.ArrayAccessContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterBoolExpr(OFPParser.BoolExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitBoolExpr(OFPParser.BoolExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterIdExpr(OFPParser.IdExprContext ctx) {
        String name = ctx.getText();
        Symbol sym = currentScope.resolve(name);

        if (sym == null) {
            errorCount++;
            System.out.println(errorCount + "\tUndeclared variable in function " + currentFunction + ": " + name);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitIdExpr(OFPParser.IdExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterPrint(OFPParser.PrintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitPrint(OFPParser.PrintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAssign(OFPParser.AssignContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAssign(OFPParser.AssignContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArrayAssign(OFPParser.ArrayAssignContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArrayAssign(OFPParser.ArrayAssignContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterArrayAcces(OFPParser.ArrayAccesContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitArrayAcces(OFPParser.ArrayAccesContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterMethodCall(OFPParser.MethodCallContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitMethodCall(OFPParser.MethodCallContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if (print) {
            printIndented(ctx);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitTerminal(TerminalNode node) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
    }
}
