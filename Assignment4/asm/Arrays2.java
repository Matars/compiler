/**
 * Arrays2.java
 * 20 oct. 2022
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
public class Arrays2 {
	public static void main(String[] args) {
		int[] arra = {6,7};
		int a = arra[0];
		System.out.println(a);
	}
}
 */
public class Arrays2 extends ClassLoader implements Opcodes {

    public static void main(final String args[]) throws Exception {
        // Class Plus
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Arrays2", null, "java/lang/Object", null);

        // Code for the (implicit) constructor
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,cw);
        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();


/*  Our main method target code
 public static void main(java.lang.String[]);
    args = 0, arr = 1, a = 2
        0: iconst_2
         1: newarray       int
         3: dup
         4: iconst_0
         5: bipush        6
         7: iastore
         8: dup
         9: iconst_1
        10: bipush        7
        12: iastore
        13: astore_1
        14: aload_1
        15: iconst_0
        16: iaload
        17: istore_2
        18: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        21: iload_2
        22: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
        25: return
*/
        
        Method main = Method.getMethod("void main (String[])");
        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, main, null, null, cw);

        mg.push(Integer.valueOf(2));
        mg.newArray(Type.INT_TYPE);   // pop size and push array ref

        mg.dup();   // push array ref again
		mg.push(0); // push index
		mg.push(Integer.valueOf(6));  // Push element value 6
		mg.arrayStore(Type.INT_TYPE);  // pop, pop, pop and store in array

        mg.dup();   // push array ref again
		mg.push(1); // push index
		mg.push(Integer.valueOf(7));  // Push element value 7
		mg.arrayStore(Type.INT_TYPE);  // store in array

        Type intArray = Type.getType(int[].class);
        mg.storeLocal(1, intArray);  // assign array to variable arr

        mg.loadLocal(1, intArray);  // push array ref
        mg.push(0);                 // push index 0
        mg.arrayLoad(Type.INT_TYPE);   // push value for arr[0]

        mg.storeLocal(2, Type.INT_TYPE);	// a = ...

        mg.getStatic(Type.getType(System.class), "out",Type.getType(PrintStream.class));
        mg.loadLocal(2,Type.INT_TYPE);            // print(a)
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (int)"));
        
        mg.returnValue();
        mg.endMethod();
 
        cw.visitEnd();

        // Save bytecode
        byte[] code = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("arrays2.class");
        fos.write(code);
        fos.close();
        
        // Bytecode diagnostics
        ClassReader cr = new ClassReader(code);
		ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
		ClassVisitor checker = new CheckClassAdapter(tracer, true);
		cr.accept(checker,0);
		
        // Execute
        System.out.println("Execution");
        Arrays2 loader = new Arrays2();
        Class<?> exampleClass = loader.defineClass("Arrays2", code, 0, code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }
}

