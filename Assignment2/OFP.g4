/**
 * Define a grammar called Expressions
 */
grammar OFP;

@header {    // Define name of package for generated Java files. 
    package generated;
}

// Syntax Specification ==> Context-free Grammar
start: methodFunc* mainFunc methodFunc*;

mainFunc: 'void' 'main' argList stmtBlock;

methodFunc: TYPE ID argList stmtBlock;

// common blocks
stmtBlock: stmt | '{' stmt* '}';
argList: '(' (TYPE ID (',' TYPE ID)*)? ')';
exprList: expr (',' expr)*; // could add ( '(' | '}' )

stmt:
	assign												# assignStmt
	| TYPE ID ';'										# declareStmt
	| arrayAcces										# arrayAccessStmt
	| arrayAssign										# arrayAssignStmt
	| 'if' expr stmtBlock ('else' (stmt | stmtBlock))?	# ifStmt
	| 'while' expr stmtBlock							# whileStmt
	| 'return' expr ';'									# returnStmt
	| methodCall										# callMethodStmt
	| print												# printStmt;

expr:
	'(' expr ')'					# parenthesis
	| '-' expr						# negation
	| expr ('*' | '/') expr			# multdiv
	| expr ('+' | '-') expr			# addsub
	| expr ('>' | '<' | '==') expr	# comp
	| expr '[' expr ']'				# arrayAccess
	| expr '.length'				# length
	| 'new' TYPE '[' expr ']'		# newArray
	| methodCall					# callMethod
	| INT							# intExpr
	| FLOAT							# floatExpr
	| BOOL							# boolExpr
	| ID							# idExpr
	| STRING						# strExpr
	| CHAR							# charExpr;

// stmt
print: ('print' | 'println') '(' expr ')' ';';

// 'TYPE?' should not be here
assign: TYPE? ID '=' expr ';';
arrayAssign: TYPE? ID '=' '{' exprList? '}' ';';
arrayAcces: ID '[' expr ']' '=' expr ';';
methodCall: ID '(' (expr (',' expr)*)? ')' ';'?;

// Lexer Specification ==> Regular Expressions Only non-trivial expressions. Trivial token
// definitions are hard coded in grammar.
INT: '0' | [1-9][0-9]*;
// below is probably wrong
FLOAT: '0' '.' [0-9]+ | [1-9] [0-9]* '.' [0-9]+;
BOOL: 'true' | 'false';
ID: [a-zA-Z] [a-zA-Z0-9_]*; // cant be _hello
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