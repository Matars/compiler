/**
 * Sum.java
 * 22 apr. 2019
 * jlnmsi
 */
package asm;

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
public class Sum extends ClassLoader implements Opcodes {

    public static void main(final String args[]) throws Exception {
        // Class Sum
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Sum", null, "java/lang/Object", null);

        // creates a GeneratorAdapter for the (implicit) constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();

        /* main method
public static void main(java.lang.String[]);
   args = 0, n = 1, r = 2
       0: bipush        10
       2: istore_1
       3: iload_1
       4: invokestatic  #16                 // Method sumUpTo:(I)I
       7: istore_2
       8: getstatic     #20                 // Field java/lang/System.out:Ljava/io/PrintStream;
      11: iload_2
      12: invokevirtual #26                 // Method java/io/PrintStream.println:(I)V
      15: return
*/
        
        m = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
        mg.push(new Integer(10));
        mg.storeLocal(1,Type.INT_TYPE);
        
        mg.loadLocal(1,Type.INT_TYPE);        
        mg.invokeStatic(Type.getType("L"+"Sum"+";"), Method.getMethod("int sumUpTo(int)"));
        mg.storeLocal(2,Type.INT_TYPE);
        

        mg.getStatic(Type.getType(System.class), "out",Type.getType(PrintStream.class));
        mg.loadLocal(2,Type.INT_TYPE);
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (int)"));
        mg.returnValue();
        mg.endMethod();
        
        /*
/*  private static int sumUpTo(int);
      n = 0, sum = 1, i = 2
         0: iconst_0
         1: istore_1
         2: iconst_1
         3: istore_2
         4: goto          14
         7: iload_1
         8: iload_2
         9: iadd
        10: istore_1
        11: iinc          2, 1
        14: iload_2
        15: iload_0
        16: if_icmple     7
        19: iload_1
        20: ireturn
*/
        m = Method.getMethod("int sumUpTo (int)");
        mg = new GeneratorAdapter(ACC_PRIVATE + ACC_STATIC, m, null, null, cw);
        mg.push(new Integer(0));
        mg.storeLocal(1,Type.INT_TYPE);
        mg.push(new Integer(1));
        mg.storeLocal(2,Type.INT_TYPE);
        
        Label exitWhile = new Label();
        mg.goTo(exitWhile);
        Label enterWhile = mg.mark();
        mg.loadLocal(1,Type.INT_TYPE);
        mg.loadLocal(2,Type.INT_TYPE);
        mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
        mg.storeLocal(1,Type.INT_TYPE);
        mg.loadLocal(2,Type.INT_TYPE);   // start of i = i + 1
        mg.push(new Integer(1));
        mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
        mg.storeLocal(2,Type.INT_TYPE);
        mg.mark(exitWhile);
        
        mg.loadLocal(2,Type.INT_TYPE);  // i<n
        mg.loadArg(0);               // Read n
        mg.ifICmp(GeneratorAdapter.LE, enterWhile);
        mg.loadLocal(1,Type.INT_TYPE);
        mg.returnValue();
        mg.endMethod();

        cw.visitEnd();
        // Save
        byte[] code = cw.toByteArray();
        
        ClassReader cr = new ClassReader(code);
		ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
		ClassVisitor checker = new CheckClassAdapter(tracer, true);
		cr.accept(checker,0);
        //System.exit(-1);
        
        
//        FileOutputStream fos = new FileOutputStream("Sum.class");
//        fos.write(code);
//        fos.close();
        
        // Execute
        Sum loader = new Sum();
//        ClassLoader loader = RunSum.class.getClassLoader();
        Class<?> exampleClass = loader.defineClass("Sum", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }
}
