/**
 * Define a grammar called Expressions
 */
grammar Expressions;

@header {    // Define name of package for generated Java files. 
    package generated;
}

// Syntax Specification ==> Context-free Grammar
start: methodFunc* mainFunc methodFunc*;

mainFunc: 'void' 'main' paramList '{' stmt* '}';

methodFunc: TYPE ID paramList stmtBlock;

// common blocks
stmtBlock: stmt | '{' stmt* '}';
paramList: '(' (TYPE ID (',' TYPE ID)*)? ')';
exprList: expr (',' expr)*; // could add ( '(' | '}' )

stmt:
	assign
	| declare
	| arrayAcces
	| ifstmt
	| whilestmt
	| print
	| methodCall
	| returnstmt;

expr:
	'(' expr ')'
	| '-' expr
	| expr ('*' | '/') expr
	| expr ('+' | '-') expr
	| expr ('>' | '<' | '==') expr
	| (INT | FLOAT | BOOL | ID | STRING | CHAR)
	| expr '[' expr ']'
	| expr '.length'
	| 'new' TYPE '[' expr ']'
	| methodCall;

// stmt
ifstmt: 'if' expr stmtBlock elsePart?;
elsePart: 'else' (ifstmt | stmtBlock);
whilestmt: 'while' expr stmtBlock;
returnstmt: 'return' expr ';';
print: ('print' | 'println') '(' '"'? expr* '"'? ')' ';';

declare: TYPE ID ';';
// ---------- UlGLY ------------
arrayAcces: ID '[' expr ']' '=' expr ';'; // finish
assign: TYPE? ID '=' (expr | '{' exprList? '}') ';';

methodCall:
	ID '(' (expr+ (',' expr+)*)? ')' ';'? // Method call without assignment
	| TYPE ID '=' ID '(' exprList? ')' ';'?; // Method call with assignment
// ---------- UGLY END --------

// Lexer Specification ==> Regular Expressions Only non-trivial expressions. Trivial token
// definitions are hard coded in grammar.
INT: '0' | [1-9] [0-9]*;
FLOAT: '0' '.' [0-9]+ | [1-9] [0-9]* '.' [0-9]+;
BOOL: 'true' | 'false';
ID: [a-zA-Z] [a-zA-Z0-9_]*; // cant be _hello
// stirng and char by AI
STRING: '"' [a-zA-Z!.,?=:() ]* '"';
CHAR: '\'' [a-zA-Z!.,?=:() ] '\'';
WS: [ \t\r\n]+ -> skip;

// is there a way to esacpe the empty space trailing the type? yes, add WS
TYPE:
	' '? (
		'int'
		| 'float'
		| 'bool'
		| 'char'
		| 'string'
		| 'int[]'
		| 'float[]'
		| 'bool[]'
		| 'char[]'
		| 'string[]'
	) ' '?;

// line below generated by AI
COMMENT: '#' (~[\r\n])* -> skip;