/**
 * Main.java
 * 10 sep. 2023
 * jlnmsi
 * 
 * Starting point for the ofp compiler
 */
package ofppackage;

import java.io.IOException;

import java.io.FileWriter;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import generated.OFPLexer;
import generated.OFPParser;

public class main {

    public static void main(String[] args) {

        // Select test program
        // get cwd
        String cwd = System.getProperty("user.dir");
        String testDir = cwd + "/ofp_examples_2024/";
        String fileName = "max.ofp";
        String testProgram = testDir + fileName;

        // Check if input ends with ".ofp"
        if (!testProgram.endsWith(".ofp")) {
            System.out.println("\nPrograms most end with suffix .ofp! Found " + testProgram);
            System.exit(-1);
        }
        System.out.println("Reading test program from: " + testProgram);

        // Parse input program
        System.out.println("\nParsing started");
        OFPParser parser = null;
        OFPParser.StartContext root = null;
        try {
            CharStream inputStream = CharStreams.fromFileName(testProgram);
            OFPLexer lexer = new OFPLexer(inputStream);
            parser = new OFPParser(new BufferedTokenStream(lexer));
            root = parser.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nParsing completed");

        // Display tree
        // Trees.inspect(root, parser);

        // Walk tree
        System.out.println("\nWalking tree started");
        ParseTreeWalker walker = new ParseTreeWalker();
        // PrintListener PrintListener = new PrintListener();
        System.out.println();
        System.out.println();

        BuildSymbolListener buildSymb = new BuildSymbolListener();

        checkSymbolListener checkSymb = new checkSymbolListener();

        System.out.println("Building symbol table");
        walker.walk(buildSymb, root);

        System.out.println();
        System.out.println();

        ParseTreeProperty<Scope> scopes = buildSymb.getScopes();
        checkSymb.setScopes(scopes);

        walker.walk(checkSymb, root);

        TypeCheckVisitor typeCheck = new TypeCheckVisitor();
        typeCheck.setScopes(scopes);
        typeCheck.visit(root);

        PythonCodeGenerator translation = new PythonCodeGenerator();
        translation.setScopes(scopes);
        String code = translation.visit(root);

        // Write the buffer content to a file

        String outputPath = "pyGenerated/" + fileName.replace(".ofp", ".py");
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}