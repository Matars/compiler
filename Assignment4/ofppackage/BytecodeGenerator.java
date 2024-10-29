package ofppackage;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.Label;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
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

        currentscope = scopes.get(ctx);
        currentFunction = (FunctionSymbol) currentscope.resolve(name);

        // Build the method signature based on parameter count and types
        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(convertType(type)).append(" ").append(name).append("(");

        ArrayList<Symbol> params = currentFunction.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0)
                methodSignature.append(",");
            methodSignature.append(convertType(params.get(i).getType().toString()));
        }
        methodSignature.append(")");

        Method m = Method.getMethod(methodSignature.toString());
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

        visit(ctx.stmtBlock());
        mg.returnValue();
        mg.endMethod();

        return null;
    }

    @Override
    public Type visitCallMethodStmt(OFPParser.CallMethodStmtContext ctx) {
        String name = ctx.getChild(0).getChild(0).getText();

        // Build method signature for call
        FunctionSymbol func = (FunctionSymbol) currentscope.resolve(name);
        String funcType = func.getType().toString();

        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(convertType(funcType)).append(" ").append(name).append("(");

        ArrayList<Symbol> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0)
                methodSignature.append(",");
            methodSignature.append(convertType(params.get(i).getType().toString()));
        }
        methodSignature.append(")");

        mg.invokeStatic(Type.getType("L" + className + ";"),
                Method.getMethod(methodSignature.toString()));

        return null;
    }

    @Override
    public Type visitMethodCall(OFPParser.MethodCallContext ctx) {
        String name = ctx.getChild(0).getText();
        visitChildren(ctx);

        // Build method signature for call
        FunctionSymbol func = (FunctionSymbol) currentscope.resolve(name);
        String funcType = func.getType().toString();

        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(convertType(funcType)).append(" ").append(name).append("(");

        ArrayList<Symbol> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0)
                methodSignature.append(",");
            methodSignature.append(convertType(params.get(i).getType().toString()));
        }
        methodSignature.append(")");

        mg.invokeStatic(Type.getType("L" + className + ";"),
                Method.getMethod(methodSignature.toString()));

        return getAsmType(funcType);
    }

    @Override
    public Type visitIfStmt(OFPParser.IfStmtContext ctx) {
        Label elseLabel = new Label();
        Label endLabel = new Label();

        // Generate condition code
        Type condType = visit(ctx.getChild(1)); // Visit the condition expression

        // Compare and branch to else block if condition is false
        ParseTree comp = ctx.getChild(1).getChild(1).getChild(1);

        if (condType == Type.BOOLEAN_TYPE) {
            mg.ifZCmp(GeneratorAdapter.EQ, elseLabel);
        } else {
            String compString = comp.getText();

            switch (compString) {
                case "<":
                    mg.ifCmp(condType, GeneratorAdapter.GE, elseLabel);
                    break;
                case ">":
                    mg.ifCmp(condType, GeneratorAdapter.LE, elseLabel);
                    break;
                case "==":
                    mg.ifCmp(condType, GeneratorAdapter.NE, elseLabel);
                    break;
            }
        }

        visit(ctx.getChild(2)); // Visit the 'then' block

        mg.goTo(endLabel);

        mg.mark(elseLabel);

        if (ctx.getChild(4) != null) { // If there's an else block
            visit(ctx.getChild(4)); // Visit the 'else' block
        }

        mg.mark(endLabel);
        return null;
    }

    @Override
    public Type visitStmtBlock(OFPParser.StmtBlockContext ctx) {
        currentscope = scopes.get(ctx);
        return visitChildren(ctx);
    }

    @Override
    public Type visitAssignStmt(OFPParser.AssignStmtContext ctx) {
        Symbol var = currentscope.resolve(ctx.getChild(0).getText());
        Type eType = getAsmType(var.getType().toString());
        visit(ctx.getChild(2));

        int stackpointer = currentFunction.indexOf(var);
        mg.storeLocal(stackpointer, eType);

        return null;
    }

    @Override
    public Type visitDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {

        Symbol var = currentscope.resolve(ctx.getChild(1).getText());
        Type eType = getAsmType(ctx.getChild(0).getText());
        visit(ctx.getChild(3));

        int stackpointer = currentFunction.indexOf(var);

        mg.storeLocal(stackpointer, eType);

        return null;
    }

    @Override
    public Type visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        return null;
    }

    @Override
    public Type visitWhileStmt(OFPParser.WhileStmtContext ctx) {
        Label conditionLabel = new Label();
        Label loopBody = new Label();
        Label endWhile = new Label(); // Add end label

        // Jump to condition check first
        mg.goTo(conditionLabel);

        // Loop body
        mg.mark(loopBody);
        Type condType = visit(ctx.getChild(2));

        // Condition check
        mg.mark(conditionLabel);
        visit(ctx.getChild(1)); // This loads both comparison values onto stack

        // Compare and branch based on condition
        String comp = ctx.getChild(1).getChild(1).getChild(1).getText();
        switch (comp) {
            case "<":
                mg.ifCmp(condType, GeneratorAdapter.LT, loopBody);
                break;
            case ">":
                mg.ifCmp(condType, GeneratorAdapter.GT, loopBody);
                break;
            case "==":
                mg.ifCmp(condType, GeneratorAdapter.EQ, loopBody);
                break;
        }

        mg.mark(endWhile); // Mark end of while loop
        return null;
    }

    @Override
    public Type visitComp(OFPParser.CompContext ctx) {
        Type LHS = visit(ctx.getChild(0));
        String comp = ctx.getChild(1).getText();
        Type RHS = visit(ctx.getChild(2));

        return LHS;
    }

    @Override
    public Type visitLength(OFPParser.LengthContext ctx) {
        Type type = visit(ctx.getChild(0));

        // Now that the string is on the stack, we can call length()
        mg.invokeVirtual(Type.getType(String.class),
                Method.getMethod("int length ()"));

        return Type.INT_TYPE;
    }

    @Override
    public Type visitParenthesis(OFPParser.ParenthesisContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Type visitAddsub(OFPParser.AddsubContext ctx) {
        // Visit left and right operand
        Type leftType = visit(ctx.getChild(0));
        Type rightType = visit(ctx.getChild(2));

        Type eType = leftType;

        if (ctx.getChild(1).getText().equals("+"))
            mg.math(GeneratorAdapter.ADD, eType);
        else
            mg.math(GeneratorAdapter.SUB, eType);

        return eType;
    }

    @Override
    public Type visitMultdiv(OFPParser.MultdivContext ctx) {

        Type leftType = visit(ctx.getChild(0));
        Type rightType = visit(ctx.getChild(2));

        Type eType = leftType;

        if (ctx.getChild(1).getText().equals("*"))
            mg.math(GeneratorAdapter.MUL, eType);
        else
            mg.math(GeneratorAdapter.DIV, eType);
        return eType;
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

        // Check if this is a parameter
        // should be done in argList instead for cleaner code
        boolean isParameter = currentFunction.getParameters().contains(var);

        if (var.getType().toString().equals("int")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.INT_TYPE);
            }
            return Type.INT_TYPE;
        } else if (var.getType().toString().equals("float")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.DOUBLE_TYPE);
            }
            return Type.DOUBLE_TYPE;
        } else if (var.getType().toString().equals("char")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.CHAR_TYPE);
            }
            return Type.CHAR_TYPE;
        } else if (var.getType().toString().equals("bool")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.BOOLEAN_TYPE);
            }
            return Type.BOOLEAN_TYPE;
        } else if (var.getType().toString().equals("string")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.getType(String.class));
            }
            return Type.getType(String.class);
        } else {
            throw new RuntimeException("Unknown type " + var.getType());
        }
    }

    @Override
    public Type visitStrExpr(OFPParser.StrExprContext ctx) {
        System.out.println("Found String: " + ctx.getText());
        mg.push(ctx.getText());
        return Type.getType(String.class);
    }

    @Override
    public Type visitIntExpr(OFPParser.IntExprContext ctx) {
        mg.push(new Integer(ctx.getText()));
        return Type.INT_TYPE;
    }

    @Override
    public Type visitFloatExpr(OFPParser.FloatExprContext ctx) {
        mg.push(new Double(ctx.getText()));
        return Type.DOUBLE_TYPE;
    }

    public Type visitBoolExpr(OFPParser.BoolExprContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.getText());
        mg.push(value);
        return Type.BOOLEAN_TYPE;
    }

    @Override
    public Type visitReturnStmt(OFPParser.ReturnStmtContext ctx) {
        visit(ctx.getChild(1));
        mg.returnValue();
        return null;
    }

    // Helper method to convert OFP types to Java types
    private String convertType(String ofpType) {
        switch (ofpType.toLowerCase()) {
            case "int":
                return "int";
            case "float":
                return "double";
            case "bool":
                return "boolean";
            case "char":
                return "char";
            case "string":
                return "String";
            case "void":
                return "void";
            default:
                throw new RuntimeException("Unknown type: " + ofpType);
        }
    }

    // Helper method to get ASM Type
    private Type getAsmType(String ofpType) {
        switch (ofpType.toLowerCase()) {
            case "int":
                return Type.INT_TYPE;
            case "float":
                return Type.DOUBLE_TYPE;
            case "bool":
                return Type.BOOLEAN_TYPE;
            case "char":
                return Type.CHAR_TYPE;
            case "string":
                return Type.getType(String.class);
            case "void":
                return Type.VOID_TYPE;
            default:
                throw new RuntimeException("Unknown type: " + ofpType);
        }
    }

    public byte[] getBytecode() {
        return cw.toByteArray();
    }

}