// Grammar for plus/mult expressions 
grammar PlusMult; 

@header {    // Define name of package for generated Java files. 
    package generated;
}

start	:	expr ';';

expr : expr '*' expr
	 | expr '+' expr
	 | INT
	 ;
	 
INT	:	('0'..'9')+ ;
WS	:	[ \t\r\n]+ -> skip ;
