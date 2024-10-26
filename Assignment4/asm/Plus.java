/**
 * Plus.java
 * 12 oct. 2019
 * jlnmsi
 */
package asm;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * Bytecode for
public class Plus {
	public static void main(String[] args) {
		int a = 25;
		int b = 25 + 3*a;  
		int p = plus(a,b);
		System.out.println(p);  // 100
	}
	
	private static int plus(int a, int b) {
		return a+b;
	}	
}
 */
public class Plus extends ClassLoader implements Opcodes {

    public static void main(final String args[]) throws Exception {
        // Class Plus
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Plus", null, "java/lang/Object", null);

        // Code for the (implicit) constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();


/* 
 public static void main(java.lang.String[]);
    0 = args, 1 = a, 2 = b, 3 = p
         0: bipush        25
         2: istore_1
         3: bipush        25
         5: iconst_3
         6: iload_1
         7: imul
         8: iadd
         9: istore_2
        10: iload_1
        11: iload_2
        12: invokestatic  #16                 // Method plus:(II)I
        15: istore_3
        16: getstatic     #20                 // Field java/lang/System.out:Ljava/io/PrintStream;
        19: iload_3
        20: invokevirtual #26                 // Method java/io/PrintStream.println:(I)V
        23: return
*/
        
        Method main = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, main, null, null, cw);
        mg.push(new Integer(25));
        mg.storeLocal(1,Type.INT_TYPE);   // a = 25
        
        mg.push(new Integer(25));
        mg.push(new Integer(3));
        mg.loadLocal(1,Type.INT_TYPE);
        mg.math(GeneratorAdapter.MUL, Type.INT_TYPE);
        mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
        mg.storeLocal(2,Type.INT_TYPE);  // b = 25 + 3*a
        
        mg.loadLocal(1,Type.INT_TYPE);
        mg.loadLocal(2,Type.INT_TYPE);
        mg.invokeStatic(Type.getType("L"+"Plus"+";"), Method.getMethod("int plus(int,int)"));
        mg.storeLocal(3,Type.INT_TYPE);    // Call plus(a,b)
        
        mg.getStatic(Type.getType(System.class), "out",Type.getType(PrintStream.class));
        mg.loadLocal(2,Type.INT_TYPE);            // print(p)
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (int)"));
        
        mg.returnValue();
        mg.endMethod();
        
        /*
private static int plus(int, int);
   0 = a, 1 = b
         0: iload_0
         1: iload_1
         2: iadd
         3: ireturn
*/
        Method plus = Method.getMethod("int plus (int, int)");
        mg = new GeneratorAdapter(ACC_PRIVATE + ACC_STATIC, plus, null, null, cw);
        mg.loadArg(0);
        mg.loadArg(1);
        mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
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
        Plus loader = new Plus();
        Class<?> exampleClass = loader.defineClass("Plus", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }
}
