/**
 * Main.java
 * 10 sep. 2023
 * jlnmsi
 * 
 * Starting point for the ofp compiler
 */
package ofp;

import java.io.IOException;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.gui.Trees;

import generated.OFPLexer;
import generated.OFPParser;

public class Main {

    public static void main(String[] args) {

        // Select test program
        String testDir = "/Users/matar/School/compiler/Assignment2/ofp_examples_2024/";
        String testProgram = testDir + "tmp.ofp";

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
        PrintListener listener = new PrintListener();

        // walker.walk(listener, root);

        listener.print = true;
        walker.walk(listener, root);
    }

    public static void print(String msg) {
    }
}