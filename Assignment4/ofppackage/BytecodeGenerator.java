package ofppackage;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.Label;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import generated.OFPBaseVisitor;
import generated.OFPParser;

@SuppressWarnings("removal")
public class BytecodeGenerator extends OFPBaseVisitor<Type> implements Opcodes {
    private ClassWriter cw;
    private GeneratorAdapter mg;
    private String className;
    private ParseTreeProperty<Scope> scopes;
    private Scope currentscope;
    private FunctionSymbol currentFunction;

    public BytecodeGenerator(ParseTreeProperty<Scope> scopes, String className) {
        this.scopes = scopes;
        this.className = className;
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, className, null, "java/lang/Object", null);

        // Generate constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();
    }

    @Override
    public Type visitStart(OFPParser.StartContext ctx) {
        // Visit all children (including main)
        visitChildren(ctx);
        cw.visitEnd();
        return null;
    }

    @Override
    public Type visitMainFunc(OFPParser.MainFuncContext ctx) {
        currentscope = scopes.get(ctx);

        // Create main method
        Method m = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

        // Visit the function body
        currentFunction = (FunctionSymbol) currentscope.resolve("main");

        visit(ctx.stmtBlock());

        // Add return
        mg.returnValue();
        mg.endMethod();
        return null;
    }

    @Override
    public Type visitMethodFunc(OFPParser.MethodFuncContext ctx) {

        String type = ctx.getChild(0).getText();
        String name = ctx.getChild(1).getText();
        String args = ctx.getChild(2).getText();
        String body = ctx.getChild(3).getText();

        currentFunction = (FunctionSymbol) currentscope.resolve(name);

        Method m = Method.getMethod(type + " " + name + "(int,int)");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

        mg.loadArg(0);
        mg.loadArg(1);

        visit(ctx.stmtBlock());

        mg.returnValue();

        mg.endMethod();

        return null;
    }

    @Override
    public Type visitCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        String name = ctx.getChild(0).getChild(0).getText();
        String args = ctx.getChild(0).getChild(2).getText();

        mg.invokeStatic(Type.getType("L" + className + ";"), Method.getMethod("int" +
                " " + name + "(int,int)"));

        mg.storeLocal(3, Type.INT_TYPE);
        mg.loadLocal(3, Type.INT_TYPE);

        return null;
    }

    @Override
    public Type visitMethodCall(OFPParser.MethodCallContext ctx) {
        String name = ctx.getChild(0).getText();
        String args = ctx.getChild(2).getText();

        visitChildren(ctx);

        mg.invokeStatic(Type.getType("L" + className + ";"), Method.getMethod("int" +
                " " + name + "(int,int)"));

        return Type.INT_TYPE;
    }

    @Override
    public Type visitStmtBlock(OFPParser.StmtBlockContext ctx) {
        currentscope = scopes.get(ctx);
        return visitChildren(ctx);
    }

    @Override
    public Type visitAssignStmt(OFPParser.AssignStmtContext ctx) {
        Symbol var = currentscope.resolve(ctx.getChild(0).getText());
        Type eType = visit(ctx.getChild(2));

        int stackpointer = currentFunction.indexOf(var);

        mg.storeLocal(stackpointer, eType);

        return null;
    }

    @Override
    public Type visitDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {

        Symbol var = currentscope.resolve(ctx.getChild(1).getText());
        Type eType = visit(ctx.getChild(3));

        int stackpointer = currentFunction.indexOf(var);

        mg.storeLocal(stackpointer, eType);

        return null;
    }

    @Override
    public Type visitWhileStmt(OFPParser.WhileStmtContext ctx) {
        System.out.println(ctx.getChild(0));
        System.out.println(ctx.getChild(1).getText());
        System.out.println(ctx.getChild(2).getText());

        Label exitWhile = new Label();
        mg.goTo(exitWhile);
        Label enterWhile = mg.mark();

        // visit block
        visit(ctx.getChild(2));

        mg.mark(exitWhile);

        // load condition
        visit(ctx.getChild(1));

        // compare condition
        String comp = ctx.getChild(1).getChild(1).getChild(1).getText();

        if (comp.equals("<")) {
            mg.ifICmp(GeneratorAdapter.LT, enterWhile);
        } else if (comp.equals(">")) {
            mg.ifICmp(GeneratorAdapter.GT, enterWhile);
        } else if (comp.equals("==")) {
            mg.ifICmp(GeneratorAdapter.EQ, enterWhile);
        }

        return null;
    }

    @Override
    public Type visitParenthesis(OFPParser.ParenthesisContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Type visitAddsub(OFPParser.AddsubContext ctx) {
        visitChildren(ctx);

        if (ctx.getChild(1).getText().equals("+"))
            mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
        else
            mg.math(GeneratorAdapter.SUB, Type.INT_TYPE);
        return Type.INT_TYPE;
    }

    @Override
    public Type visitMultdiv(OFPParser.MultdivContext ctx) {
        visitChildren(ctx);

        if (ctx.getChild(1).getText().equals("*"))
            mg.math(GeneratorAdapter.MUL, Type.INT_TYPE);
        else
            mg.math(GeneratorAdapter.DIV, Type.INT_TYPE);
        return Type.INT_TYPE;
    }

    @Override
    public Type visitPrintStmt(OFPParser.PrintStmtContext ctx) {
        // Get System.out onto stack
        mg.getStatic(Type.getType(System.class), "out",
                Type.getType(java.io.PrintStream.class));

        Type eType = visit(ctx.getChild(0).getChild(2));

        String type = null; // Select print type

        if (eType == Type.INT_TYPE)
            type = "int";
        else if (eType == Type.DOUBLE_TYPE)
            type = "double";
        else if (eType == Type.CHAR_TYPE)
            type = "char";
        else if (eType == Type.BOOLEAN_TYPE)
            type = "boolean";
        else if (eType.toString().equals("Ljava/lang/String;"))
            type = "String";
        else
            throw new RuntimeException("Unkown print type " + eType);

        // Call print
        mg.invokeVirtual(Type.getType(java.io.PrintStream.class),
                Method.getMethod("void print (" + type + ") "));

        return null;
    }

    @Override
    public Type visitPrintlnStmt(OFPParser.PrintlnStmtContext ctx) {
        // Get System.out onto stack
        mg.getStatic(Type.getType(System.class), "out",
                Type.getType(java.io.PrintStream.class));

        Type eType = visit(ctx.getChild(0).getChild(2));

        String type = null; // Select print type

        if (eType == Type.INT_TYPE)
            type = "int";
        else if (eType == Type.DOUBLE_TYPE)
            type = "double";
        else if (eType == Type.CHAR_TYPE)
            type = "char";
        else if (eType == Type.BOOLEAN_TYPE)
            type = "boolean";
        else if (eType.toString().equals("Ljava/lang/String;"))
            type = "String";
        else
            throw new RuntimeException("Unkown print type " + eType);

        // Call print
        mg.invokeVirtual(Type.getType(java.io.PrintStream.class),
                Method.getMethod("void println (" + type + ") "));

        return null;
    }

    @Override
    public Type visitIdExpr(OFPParser.IdExprContext ctx) {

        Symbol var = currentscope.resolve(ctx.getText());

        int stackpointer = currentFunction.indexOf(var);

        if (var.getType().toString().equals("int")) {
            mg.loadLocal(stackpointer, Type.INT_TYPE);
            return Type.INT_TYPE;
        } else if (var.getType().toString().equals("float")) {
            // doulbe or float type?
            mg.loadLocal(stackpointer, Type.DOUBLE_TYPE);
            return Type.DOUBLE_TYPE;
        } else if (var.getType().toString().equals("char")) {
            mg.loadLocal(stackpointer, Type.CHAR_TYPE);
            return Type.CHAR_TYPE;
        } else if (var.getType().toString().equals("bool")) {
            mg.loadLocal(stackpointer, Type.BOOLEAN_TYPE);
            return Type.BOOLEAN_TYPE;
        } else if (var.getType().toString().equals("string")) {
            mg.loadLocal(stackpointer, Type.getType(String.class));
            return Type.getType(String.class);
        } else {
            throw new RuntimeException("Unknown type " + var.getType());
        }
    }

    @Override
    public Type visitStrExpr(OFPParser.StrExprContext ctx) {
        mg.push(ctx.getText());
        return Type.getType(String.class);
    }

    @Override
    public Type visitIntExpr(OFPParser.IntExprContext ctx) {
        mg.push(new Integer(ctx.getText()));
        return Type.INT_TYPE;
    }

    public byte[] getBytecode() {
        return cw.toByteArray();
    }

}