/**
 * HW.java
 * 22 apr. 2019
 * jlnmsi
 */
package asm;

/***
 * Generates bytecode corresponding to Java class
 * public class Hello {
 *     public static void main(String[] args) {
 *        System.out.println("hello World!");
 *     }
 * }    
 */
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

public class HW extends ClassLoader implements Opcodes {

    public static void main(String args[]) throws Exception {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Hello", null, "java/lang/Object", null);

        // Creates a GeneratorAdapter for the (implicit) constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();

        // Creates a GeneratorAdapter for the 'main' method
        Method main = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, main, null, null, cw);
        mg.getStatic(Type.getType(System.class), "out",Type.getType(PrintStream.class));
        mg.push("Hello world!");
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (String)"));
        mg.returnValue();
        mg.endMethod();

        cw.visitEnd();

        // Save
        byte[] code = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("Hello.class");
        fos.write(code);
        fos.close();
        
        // Diagnostics
        ClassReader cr = new ClassReader(code);
		ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
		ClassVisitor checker = new CheckClassAdapter(tracer, true);
		cr.accept(checker,0);
        
        // Execute
        HW loader = new HW();
        Class<?> exampleClass = loader.defineClass("Hello", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }
}
