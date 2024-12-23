package ofppackage;

import java.util.List;
import java.util.function.Function;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.OFPBaseListener;
import generated.OFPParser;

public class BuildSymbolListener extends OFPBaseListener {

    private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>(); // Node-to-scope mapping
    private Scope currentScope;
    private Scope globalScope = null; // Used to resolve function symbols
    private String currentFunctionString; // For logging purposes
    private FunctionSymbol currentFunction;

    private int errorCount = 0; // Keep track of the total number of errors

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

    @Override
    public void exitStart(OFPParser.StartContext ctx) {
        // ToString();
        if (errorCount > 0) {
            System.out.println("Found " + errorCount + " errors in the program");
            System.exit(-1);
        }
        currentScope = globalScope;
    }

    @Override
    public void enterMainFunc(OFPParser.MainFuncContext ctx) {
        currentFunctionString = ctx.getChild(1).getText();
        OFPType currentFunctionType = new OFPType(ctx.getChild(0).getText());

        if (globalScope.resolve(currentFunctionString) != null) {
            errorCount++;
            System.out.println(errorCount + "\t[BUILD] Duplicate function declaration: " + currentFunctionString);
        } else {
            FunctionSymbol funcSymbol = new FunctionSymbol(currentFunctionType, currentFunctionString);
            currentFunction = funcSymbol;

            globalScope.addSymbol(funcSymbol);
            Scope mainScope = new Scope(globalScope);
            mainScope.setName("main");
            currentScope = mainScope;
            scopes.put(ctx, mainScope);
        }
    }

    @Override
    public void exitMainFunc(OFPParser.MainFuncContext ctx) {
        currentScope = globalScope;
    }

    @Override
    public void enterMethodFunc(OFPParser.MethodFuncContext ctx) {
        currentFunctionString = ctx.getChild(1).getText();
        OFPType currentFunctionType = new OFPType(ctx.getChild(0).getText());

        if (globalScope.resolve(currentFunctionString) != null) {
            errorCount++;
            System.out.println(errorCount + "\t[BUILD] Duplicate function declaration: " + currentFunctionString);
        } else {
            FunctionSymbol funcSymbol = new FunctionSymbol(currentFunctionType, currentFunctionString);
            globalScope.addSymbol(funcSymbol);
            Scope funcScope = new Scope(globalScope);

            currentFunction = funcSymbol;
            funcScope.setName("function" + ctx.start.getLine());
            currentScope = funcScope;
            scopes.put(ctx, funcScope);
        }
    }

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

    @Override
    public void exitStmtBlock(OFPParser.StmtBlockContext ctx) {
        // ToString();
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterArgList(OFPParser.ArgListContext ctx) {
        List<TerminalNode> names = ctx.ID();
        List<TerminalNode> types = ctx.TYPE();

        FunctionSymbol currentFuncSymbol = (FunctionSymbol) globalScope.resolve(currentFunctionString);

        for (int i = 0; i < names.size(); i++) {
            String varName = names.get(i).getText();
            OFPType varType = new OFPType(types.get(i).getText());

            if (currentScope.resolveLocally(varName) != null) {
                errorCount++;
                System.out.println(
                        errorCount + "\t[BUILD] Duplicate parameter in function " + currentFunctionString + ": "
                                + varName);
            } else {
                Symbol param = new Symbol(varType, varName);
                currentScope.addSymbol(param);
                currentFuncSymbol.addParameter(param);
                currentFuncSymbol.addVariable(param);

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
    public void enterDeclareStmt(OFPParser.DeclareStmtContext ctx) {

        String name = ctx.getChild(1).getText(); // Variable name
        OFPType type = new OFPType(ctx.getChild(0).getText());

        if (currentScope.resolveLocally(name) != null) {
            errorCount++;
            // this should be done in checkSymbolListener
            System.out.println(
                    errorCount + "\t[BUILD] Duplicate declaration in function " + currentFunctionString + ": "
                            + name);
        } else {
            Symbol sym = new Symbol(type, name);
            currentScope.addSymbol(sym);
            currentFunction.addVariable(sym);
        }
    }

    @Override
    public void enterDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {

        String name = ctx.getChild(1).getText();
        OFPType type = new OFPType(ctx.getChild(0).getText());

        if (currentScope.resolveLocally(name) != null) {
            errorCount++;
            System.out.println(
                    errorCount + "\t[BUILD] Duplicate declaration in function " + currentFunctionString + ": "
                            + name);
        } else {
            Symbol sym = new Symbol(type, name);
            currentScope.addSymbol(sym);
            currentFunction.addVariable(sym);
        }
    }

    @Override
    public void enterAssignStmt(OFPParser.AssignStmtContext ctx) {
        String name = ctx.getChild(0).getChild(0).getText(); // Variable name

        // if array access (includes [expr]) then remove the [expr] part
        name = name.split("\\[")[0];

        Symbol sym = currentScope.resolve(name);
        if (sym == null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[BUILD] Undeclared variable in function " + currentFunctionString
                            + ": " + name);
            System.out.println("Line: " + ctx.start.getLine());
        }
    }

    @Override
    public void enterArrayAssignStmt(OFPParser.ArrayAssignStmtContext ctx) {
        String name = ctx.getChild(0).getChild(1).getText(); // Variable name
        Symbol sym = currentScope.resolve(name);

        OFPType type = new OFPType(ctx.getChild(0).getChild(0).getText());
        if (sym != null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[BUILD] Duplicate variable in function " + currentFunctionString
                            + ": " + name);
        } else {
            currentScope.addSymbol(new Symbol(type, name));
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
    public void enterCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {

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
    }

    @Override
    public void enterIdExpr(OFPParser.IdExprContext ctx) {
        String name = ctx.getText();
        Symbol sym = currentScope.resolve(name);
        if (sym == null) {
            errorCount++;
            System.out
                    .println(errorCount + "\t[BUILD] Undeclared variable in function " + currentFunctionString
                            + ": " + name);
        }
    }

    public void ToString() {
        currentScope.ToString();
    }

    public ParseTreeProperty<Scope> getScopes() {
        return scopes;
    }

    public int getErrorCount() {
        return errorCount;
    }

}
