/**
 * Define a grammar called Expressions
 */
grammar Expressions;


@header {    // Define name of package for generated Java files. 
    package generated;
}

// Syntax Specification ==> Context-free Grammar
start : stmt+ ;

stmt : expr ';'
     | assign
     ;

expr :  '(' expr ')'
        | expr ('*' | '/') expr
        | expr ('+' | '-') expr
        | (INT | ID)
     ;

assign : ID '=' expr ';' ;

// Lexer Specification ==> Regular Expressions 
// Only non-trivial expressions. Trivial token definitions are hard coded in grammar.
INT : ('0'..'9')+ ;
ID  : ('a'..'z') ;
WS : [ \t\r\n]+ -> skip ;