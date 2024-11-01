package ofppackage;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.Label;

import java.util.ArrayList;

import org.antlr.v4.parse.ANTLRParser.id_return;
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

        // Build method signature
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

        // Register parameters in current function scope
        for (Symbol param : params) {
            currentFunction.addVariable(param);
        }

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

        // Load the array from local variable before method call
        if (ctx.getChild(0).getChildCount() != 0) {
            Symbol arrayVar = currentscope.resolve(ctx.getChild(0).getChild(2).getText());
            int stackpointer = currentFunction.indexOf(arrayVar);
            mg.loadLocal(stackpointer, Type.getType(arrayVar.getType().toString()));
        }

        mg.invokeStatic(Type.getType("L" + className + ";"),
                Method.getMethod(methodSignature.toString()));

        return null;
    }

    @Override
    public Type visitMethodCall(OFPParser.MethodCallContext ctx) {
        String name = ctx.getChild(0).getText();
        FunctionSymbol func = (FunctionSymbol) currentscope.resolve(name);

        // Handle arguments first
        if (ctx.getChildCount() > 2) { // Has arguments
            ParseTree argList = ctx.getChild(2);
            for (int i = 0; i < argList.getChildCount(); i++) {
                ParseTree arg = argList.getChild(i);
                if (!arg.getText().equals(",")) { // Skip commas
                    if (arg.getText().startsWith("'") && arg.getText().endsWith("'")) {
                        // Handle character literal
                        mg.push((int) arg.getText().charAt(1));
                    } else {
                        // Handle other types of arguments
                        visit(arg);
                    }
                }
            }
        }

        // Build method signature
        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(convertType(func.getType().toString())).append(" ")
                .append(name).append("(");

        ArrayList<Symbol> params = func.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0)
                methodSignature.append(",");
            methodSignature.append(convertType(params.get(i).getType().toString()));
        }
        methodSignature.append(")");

        // Make the method call
        mg.invokeStatic(Type.getType("L" + className + ";"),
                Method.getMethod(methodSignature.toString()));

        return getAsmType(func.getType().toString());
    }

    @Override
    public Type visitArgList(OFPParser.ArgListContext ctx) {
        // Visit each argument in order
        for (ParseTree child : ctx.children) {
            if (!child.getText().equals(",")) {
                visit(child);
            }
        }
        return null;
    }

    @Override
    public Type visitIfStmt(OFPParser.IfStmtContext ctx) {
        Label elseLabel = new Label();
        Label endLabel = new Label();

        // Visit condition - this will put boolean result on stack
        visit(ctx.getChild(1)); // Visit the condition expression

        // If condition is false, jump to else block
        mg.ifZCmp(GeneratorAdapter.EQ, elseLabel);

        // Visit 'then' block
        visit(ctx.getChild(2));

        // Jump over else block
        mg.goTo(endLabel);

        // Else block
        mg.mark(elseLabel);
        if (ctx.getChild(4) != null) {
            visit(ctx.getChild(4));
        }

        // End of if statement
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
        if (ctx.getChild(0).getText().contains("[")) {
            // Get array variable and resolve it
            String arrayName = ctx.getChild(0).getChild(0).getText();
            Symbol arrayVar = currentscope.resolve(arrayName);
            String arrayType = arrayVar.getType().toString();

            // Handle array loading differently for parameters vs local variables
            if (currentFunction.getParameters().contains(arrayVar)) {
                // For parameters, use loadArg
                int paramIndex = currentFunction.getParameters().indexOf(arrayVar);
                mg.loadArg(paramIndex);
            } else {
                // For local variables, use loadLocal
                int stackpointer = currentFunction.indexOf(arrayVar);
                mg.loadLocal(stackpointer, Type.getType(arrayType));
            }

            // Load index onto stack
            visit(ctx.getChild(0).getChild(2));

            // Load value to store onto stack
            visit(ctx.getChild(2));

            // Store into array
            switch (arrayType) {
                case "int[]":
                    mg.arrayStore(Type.INT_TYPE);
                    break;
                case "float[]":
                    mg.arrayStore(Type.DOUBLE_TYPE);
                    break;
                case "char[]":
                    mg.arrayStore(Type.CHAR_TYPE);
                    break;
                case "boolean[]":
                    mg.arrayStore(Type.BOOLEAN_TYPE);
                    break;
                default:
                    throw new RuntimeException("Unsupported array type for assignment: " + arrayType);
            }
        } else {
            // Original assignment handling for non-array cases
            Symbol var = currentscope.resolve(ctx.getChild(0).getText());
            Type eType = getAsmType(var.getType().toString());
            visit(ctx.getChild(2));
            int stackpointer = currentFunction.indexOf(var);
            mg.storeLocal(stackpointer, eType);
        }
        return null;
    }

    @Override
    public Type visitDeclareAssignStmt(OFPParser.DeclareAssignStmtContext ctx) {
        Symbol var = currentscope.resolve(ctx.getChild(1).getText());
        String type = ctx.getChild(0).getText();

        // Register with current function
        currentFunction.addVariable(var);

        Type eType = getAsmType(type);
        visit(ctx.getChild(3));

        int stackpointer = currentFunction.indexOf(var);
        mg.storeLocal(stackpointer, eType);

        return null;
    }

    @Override
    public Type visitDeclareStmt(OFPParser.DeclareStmtContext ctx) {
        Symbol var = currentscope.resolve(ctx.getChild(1).getText());
        currentFunction.addVariable(var);
        return null;
    }

    @Override
    public Type visitWhileStmt(OFPParser.WhileStmtContext ctx) {
        Label conditionLabel = new Label();
        Label endWhile = new Label();

        // Mark the start of the condition
        mg.mark(conditionLabel);

        visit(ctx.getChild(1)); // Visit the comparison expression

        // Branch to end if condition is false
        mg.ifZCmp(GeneratorAdapter.EQ, endWhile);

        // loop body
        visit(ctx.getChild(2));

        // Jump back to condition
        mg.goTo(conditionLabel);

        // Mark end of loop
        mg.mark(endWhile);

        return null;
    }

    @Override
    public Type visitArrayAssign(OFPParser.ArrayAssignContext ctx) {
        // Get the array identifier and type
        String arrayName = ctx.getChild(1).getText();
        String arrayType = ctx.getChild(0).getText();
        Symbol arrayVar = currentscope.resolve(arrayName);

        // Register the array variable with the current function
        currentFunction.addVariable(arrayVar);

        // Get the elements string and split by comma
        String elementsStr = ctx.getChild(4).getText();
        String[] elements = elementsStr.split(",");

        // Push array size
        mg.push(elements.length);

        // Create array of appropriate type
        Type elementType;
        switch (arrayType) {
            case "int[]":
                elementType = Type.INT_TYPE;
                mg.newArray(Type.INT_TYPE);
                break;
            case "float[]":
                elementType = Type.DOUBLE_TYPE;
                mg.newArray(Type.DOUBLE_TYPE);
                break;
            case "boolean[]":
                elementType = Type.BOOLEAN_TYPE;
                mg.newArray(Type.BOOLEAN_TYPE);
                break;
            case "char[]":
                elementType = Type.CHAR_TYPE;
                mg.newArray(Type.CHAR_TYPE);
                break;
            default:
                throw new RuntimeException("Unsupported array type: " + arrayType);
        }

        // For each element in initialization list
        for (int i = 0; i < elements.length; i++) {
            mg.dup();
            mg.push(i);
            String value = elements[i].trim();

            switch (arrayType) {
                case "int[]":
                    mg.push(Integer.parseInt(value));
                    break;
                case "float[]":
                    mg.push(Double.parseDouble(value));
                    break;
                case "boolean[]":
                    mg.push(Boolean.parseBoolean(value));
                    break;
                case "char[]":
                    if (value.length() >= 3) {
                        mg.push(value.charAt(1));
                    } else {
                        throw new RuntimeException("Invalid char literal: " + value);
                    }
                    break;
            }
            mg.arrayStore(elementType);
        }

        int stackpointer = currentFunction.indexOf(arrayVar);
        if (stackpointer == -1) {
            throw new RuntimeException("Variable " + arrayName + " not found in function scope");
        }

        Type arrayObjType = Type.getType("[" + elementType.getDescriptor());
        mg.storeLocal(stackpointer, arrayObjType);

        return arrayObjType;
    }

    @Override
    public Type visitNewArray(OFPParser.NewArrayContext ctx) {

        // Visit the size expression first - this puts the size on the stack
        visit(ctx.getChild(3)); // This visits the size expression (10 in your case)

        // Get the array type
        String arrayType = ctx.getChild(1).getText();

        // Generate the newarray instruction with the appropriate type
        switch (arrayType) {
            case "int":
                mg.newArray(Type.INT_TYPE);
                break;
            case "float":
                mg.newArray(Type.DOUBLE_TYPE);
                break;
            case "bool":
                mg.newArray(Type.BOOLEAN_TYPE);
                break;
            case "char":
                mg.newArray(Type.CHAR_TYPE);
                break;
            default:
                throw new RuntimeException("Unsupported array type: " + arrayType);
        }

        // Return the array type
        return Type.getType("[" + getAsmType(arrayType).getDescriptor());
    }

    // we have some really bad naming for array access
    // the below is responsible for a[0]
    @Override
    public Type visitArrayAccess(OFPParser.ArrayAccessContext ctx) {
        String arrayName = ctx.getChild(0).getText();
        Symbol arrayVar = currentscope.resolve(arrayName);
        String arrayType = arrayVar.getType().toString();

        // Special handling for strings
        if (arrayType.equals("string")) {
            // Load the string reference
            if (currentFunction.getParameters().contains(arrayVar)) {
                int paramIndex = currentFunction.getParameters().indexOf(arrayVar);
                mg.loadArg(paramIndex);
            } else {
                int stackpointer = currentFunction.indexOf(arrayVar);
                mg.loadLocal(stackpointer, Type.getType(String.class));
            }

            // Load the index
            visit(ctx.getChild(2));

            // Call charAt method
            mg.invokeVirtual(Type.getType(String.class),
                    Method.getMethod("char charAt (int)"));

            return Type.CHAR_TYPE;
        }

        // Regular array handling (existing code)
        if (currentFunction.getParameters().contains(arrayVar)) {
            int paramIndex = currentFunction.getParameters().indexOf(arrayVar);
            mg.loadArg(paramIndex);
        } else {
            int stackpointer = currentFunction.indexOf(arrayVar);
            mg.loadLocal(stackpointer, Type.getType(arrayType));
        }

        visit(ctx.getChild(2));

        Type elementType;
        if (arrayType.equals("int[]")) {
            elementType = Type.INT_TYPE;
            mg.arrayLoad(Type.INT_TYPE);
        } else if (arrayType.equals("float[]")) {
            elementType = Type.DOUBLE_TYPE;
            mg.arrayLoad(Type.DOUBLE_TYPE);
        } else if (arrayType.equals("char[]")) {
            elementType = Type.CHAR_TYPE;
            mg.arrayLoad(Type.CHAR_TYPE);
        } else if (arrayType.equals("boolean[]")) {
            elementType = Type.BOOLEAN_TYPE;
            mg.arrayLoad(Type.BOOLEAN_TYPE);
        } else {
            throw new RuntimeException("Unsupported array type for access: " + arrayType);
        }

        return elementType;
    }

    // the below is responsible for a[0] = 1
    @Override
    public Type visitArrayAcces(OFPParser.ArrayAccesContext ctx) {
        String arrayName = ctx.getChild(0).getText();
        Symbol arrayVar = currentscope.resolve(arrayName);
        String arrayType = arrayVar.getType().toString();

        // Add array reference
        if (currentFunction.indexOf(arrayVar) == -1) {
            currentFunction.addVariable(arrayVar);
        }

        int stackpointer = currentFunction.indexOf(arrayVar);
        Type type = Type.getType(arrayType);
        mg.loadLocal(stackpointer, type);

        // Load index
        visit(ctx.getChild(2));

        return type;
    }

    @Override
    public Type visitComp(OFPParser.CompContext ctx) {
        // First evaluate LHS - puts value on stack
        Type LHS = visit(ctx.getChild(0));
        String comp = ctx.getChild(1).getText();

        // Create labels for comparison result
        Label trueLabel = new Label();
        Label endLabel = new Label();

        // For character comparisons
        if (LHS == Type.CHAR_TYPE) {
            // Push the comparison value ('A') onto stack
            String rhs = ctx.getChild(2).getText();
            if (rhs.startsWith("'") && rhs.endsWith("'")) {
                // It's a character literal
                mg.push((int) rhs.charAt(1));
            } else {
                // It's a variable or other expression
                visit(ctx.getChild(2));
            }

            // Do comparison (both values are now on stack)
            mg.ifICmp(GeneratorAdapter.EQ, trueLabel);
        } else {
            // Original numeric comparison code
            Type RHS = visit(ctx.getChild(2));
            switch (comp) {
                case "<":
                    mg.ifCmp(LHS, GeneratorAdapter.LT, trueLabel);
                    break;
                case ">":
                    mg.ifCmp(LHS, GeneratorAdapter.GT, trueLabel);
                    break;
                case "==":
                    mg.ifCmp(LHS, GeneratorAdapter.EQ, trueLabel);
                    break;
                default:
                    throw new RuntimeException("Unknown comparison operator: " + comp);
            }
        }

        // Push false and jump to end
        mg.push(false);
        mg.goTo(endLabel);

        // Push true for true case
        mg.mark(trueLabel);
        mg.push(true);

        // Mark end of comparison
        mg.mark(endLabel);

        return Type.BOOLEAN_TYPE;
    }

    @Override
    public Type visitLength(OFPParser.LengthContext ctx) {
        Symbol var = currentscope.resolve(ctx.getChild(0).getText());
        int stackpointer = currentFunction.indexOf(var);
        String varType = var.getType().toString();

        // Load variable reference
        if (currentFunction.getParameters().contains(var)) {
            mg.loadArg(stackpointer);
        } else {
            mg.loadLocal(stackpointer, Type.getType(varType));
        }

        // Handle arrays vs strings
        if (varType.contains("[")) {
            mg.arrayLength();
        } else if (varType.equals("string")) {
            mg.invokeVirtual(Type.getType(String.class),
                    Method.getMethod("int length ()"));
        } else {
            throw new RuntimeException("Length operation not supported for type: " + varType);
        }

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
        String eTypeString = eType.toString();

        String type = null; // Select print type

        // Check if we're dealing with an array type
        if (eType.toString().contains("[")) {
            String baseType = eTypeString.substring(0, eTypeString.length() - 2);

            switch (baseType) {
                case "int":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(int[])"));
                    break;
                case "float":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(double[])"));
                    break;
                case "bool":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(boolean[])"));
                    break;
                case "char":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(char[])"));
                    break;
                default:
                    throw new RuntimeException("Unsupported array type for printing: " + baseType);
            }

            // Now call print with the string
            mg.invokeVirtual(
                    Type.getType(java.io.PrintStream.class),
                    Method.getMethod("void print(String)"));
        } else {

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
        }

        return null;
    }

    @Override
    public Type visitPrintlnStmt(OFPParser.PrintlnStmtContext ctx) {
        // Get System.out onto stack
        mg.getStatic(Type.getType(System.class), "out",
                Type.getType(java.io.PrintStream.class));

        Type eType = visit(ctx.getChild(0).getChild(2));
        String eTypeString = eType.toString();

        // Check if we're dealing with an array type
        if (eTypeString.endsWith("[]")) {
            // Get the base type by removing the []
            String baseType = eTypeString.replace("[]", "");

            switch (baseType) {
                case "int":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(int[])"));
                    break;
                case "float":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(double[])"));
                    break;
                case "bool":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(boolean[])"));
                    break;
                case "char":
                    mg.invokeStatic(
                            Type.getType(java.util.Arrays.class),
                            Method.getMethod("String toString(char[])"));
                    break;
                default:
                    throw new RuntimeException("Unsupported array type for printing: " + baseType);
            }

            mg.invokeVirtual(
                    Type.getType(java.io.PrintStream.class),
                    Method.getMethod("void println(String)"));
        } else {
            // Original handling for non-array types
            String type = null;
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
                throw new RuntimeException("Unknown print type " + eType);

            mg.invokeVirtual(Type.getType(java.io.PrintStream.class),
                    Method.getMethod("void println (" + type + ")"));
        }

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
        } else if (var.getType().toString().contains("[")) {
            if (isParameter) {
                mg.loadArg(stackpointer);
            } else {
                mg.loadLocal(stackpointer, Type.getType(var.getType().toString()));
            }
            return Type.getType(var.getType().toString());
        } else {
            throw new RuntimeException("Unknown type " + var.getType());
        }
    }

    @Override
    public Type visitStrExpr(OFPParser.StrExprContext ctx) {
        // Remove the quotes from the string literal
        String text = ctx.getText();
        String unquoted = text.substring(1, text.length() - 1);
        mg.push(unquoted);
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

    @Override
    public Type visitBoolExpr(OFPParser.BoolExprContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.getText());
        mg.push(value);
        return Type.BOOLEAN_TYPE;
    }

    @Override
    public Type visitCharExpr(OFPParser.CharExprContext ctx) {
        String text = ctx.getText();
        if (text.startsWith("'") && text.endsWith("'") && text.length() == 3) {
            mg.push((int) text.charAt(1));
            return Type.CHAR_TYPE;
        }
        throw new RuntimeException("Invalid character literal: " + text);
    }

    @Override
    public Type visitReturnStmt(OFPParser.ReturnStmtContext ctx) {
        visit(ctx.getChild(1));
        mg.returnValue();
        return null;
    }

    // Helper method to convert OFP types to Java types
    private String convertType(String ofpType) {
        // check if array type
        if (ofpType.contains("[")) {
            String baseType = ofpType.replace("[]", "");
            return convertType(baseType) + "[]";
        }

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

    private Type getAsmType(String ofpType) {
        if (ofpType.contains("[")) {
            return Type.getType(ofpType);
        }

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