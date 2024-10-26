/**
 * Floats.java
 * 120 oct. 2019
 * jlnmsi
 * 
 public class Floats {

	public static void main(String[] args) {
		double f = 2.34;
		double ff = 2.0;
		double fff = mult(f,ff);
		System.out.println(fff);  // 4.68

	}
	
	private static double mult(double a, double b) {
		   return a * b;
	}

}
 * 
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
 * 
 */
public class Floats extends ClassLoader implements Opcodes {

    public static void main(final String args[]) throws Exception {
        // Class Floats
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Floats", null, "java/lang/Object", null);

        // creates a GeneratorAdapter for the (implicit) constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();

        /*
 public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=7, args_size=1
         0: ldc2_w        #16                 // double 2.34d
         3: dstore_1
         4: ldc2_w        #18                 // double 2.0d
         7: dstore_3
         8: dload_1
         9: dload_3
        10: invokestatic  #20                 // Method mult:(DD)D
        13: dstore        5
        15: getstatic     #24                 // Field java/lang/System.out:Ljava/io/PrintStream;
        18: dload         5
        20: invokevirtual #30                 // Method java/io/PrintStream.println:(D)V
        23: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      24     0  args   [Ljava/lang/String;
            4      20     1     f   D
            8      16     3    ff   D
           15       9     5   fff   D
 */
        m = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
        mg.push(new Double(2.34));
        mg.storeLocal(1,Type.DOUBLE_TYPE);
        mg.push(new Double(2.0));
        mg.storeLocal(3,Type.DOUBLE_TYPE);  // 3 since previous double requires two slots!
        
        mg.loadLocal(1,Type.DOUBLE_TYPE); 
        mg.loadLocal(3,Type.DOUBLE_TYPE);
        
        mg.invokeStatic(Type.getType("L"+"Floats"+";"), Method.getMethod("double mult (double,double)"));
        mg.storeLocal(5,Type.DOUBLE_TYPE);   // 3 since previous double requires two slots!
        
        
        mg.getStatic(Type.getType(System.class), "out",Type.getType(PrintStream.class));
        mg.loadLocal(5, Type.DOUBLE_TYPE);
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (double)"));
        mg.returnValue();
        mg.endMethod();
/*        
        private static double mult(double, double);
        descriptor: (DD)D
        flags: ACC_PRIVATE, ACC_STATIC
        Code:
          stack=4, locals=4, args_size=2
             0: dload_0
             1: dload_2
             2: dmul
             3: dreturn
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       4     0     a   D
                0       4     2     b   D
*/      
        m = Method.getMethod("double mult (double,double)");
        mg = new GeneratorAdapter(ACC_PRIVATE + ACC_STATIC, m, null, null, cw);
        mg.loadArg(0);
        mg.loadArg(1);
        mg.math(GeneratorAdapter.MUL, Type.DOUBLE_TYPE);
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
        Floats loader = new Floats();
        Class<?> exampleClass = loader.defineClass("Floats", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }
}
