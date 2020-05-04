/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
    private String currentString;
    private int nestedCommentLevel;
    private Boolean isTooLong(String s) {
	return (s.length() >= MAX_STR_CONST);
    }
    private Boolean hasNullChar(String s) {
	return (s.contains("\0"));
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
    case COMMENT:
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.ERROR,
		    AbstractTable.idtable.addString("EOF in comment"));
    case STRING:
	yybegin(YYINITIAL);
	return new Symbol(TokenConstants.ERROR,
		AbstractTable.idtable.addString("EOF in string constant"));
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF);
%eofval}
%state STRING
%state STRING_ERR
%state COMMENT

LOWERNAME = [a-z][a-zA-Z_0-9]*
UPPERNAME = [A-Z][a-zA-Z_0-9]*

%class CoolLexer
%cup
%%

<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>[cC][lL][aA][sS][sS]	{//class
				    return new Symbol(TokenConstants.CLASS);
				}
<YYINITIAL>[ \t\f\v\r]          {}
<YYINITIAL,COMMENT>[\n]         {
                                    curr_lineno++;
                                }
<YYINITIAL>[iI][nN][hH][eE][rR][iI][tT][sS]             
				{//inherits
                                    return new Symbol(TokenConstants.INHERITS);
                                }
<YYINITIAL>[iI][fF]	        {//if
                                    return new Symbol(TokenConstants.IF);
                                }
<YYINITIAL>[tT][hH][eE][nN]     {//then
                                    return new Symbol(TokenConstants.THEN);
                                }
<YYINITIAL>[eE][lL][sS][eE]     {//else
                                    return new Symbol(TokenConstants.ELSE);
                                }
<YYINITIAL>[fF][iI]	        {//fi
                                    return new Symbol(TokenConstants.FI);
                                }
<YYINITIAL>[iI][nN]	        {//in
                                    return new Symbol(TokenConstants.IN);
                                }
<YYINITIAL>[iI][sS][vV][oO][iI][dD]
			        {//isvoid
                                    return new Symbol(TokenConstants.ISVOID);
                                }
<YYINITIAL>[lL][eE][tT]	        {//let
                                    return new Symbol(TokenConstants.LET);
                                }
<YYINITIAL>[lL][oO][oO][pP]	{//loop
                                    return new Symbol(TokenConstants.LOOP);
                                }
<YYINITIAL>[pP][oO][oO][lL]     {//pool
                                    return new Symbol(TokenConstants.POOL);
                                }
<YYINITIAL>[wW][hH][iI][lL][eE] {//while
                                    return new Symbol(TokenConstants.WHILE);
                                }
<YYINITIAL>[cC][aA][sS][eE]     {//case
                                    return new Symbol(TokenConstants.CASE);
                                }
<YYINITIAL>[eE][sS][aA][cC]     {//esac
                                    return new Symbol(TokenConstants.ESAC);
                                }
<YYINITIAL>[nN][eE][wW]	        {//new
                                    return new Symbol(TokenConstants.NEW);
                                }
<YYINITIAL>[oO][fF]	        {//of
                                    return new Symbol(TokenConstants.OF);
                                }
<YYINITIAL>[nN][oO][tT]	        {//not
                                    return new Symbol(TokenConstants.NOT);
                                }
<YYINITIAL>{UPPERNAME} 		{
                                    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
                                }
<YYINITIAL>t[rR][uU][eE]        {//true
                                    return new Symbol(TokenConstants.BOOL_CONST, true);
                                }
<YYINITIAL>f[aA][lL][sS][eE]    {//false
                                    return new Symbol(TokenConstants.BOOL_CONST, false);
                                }
<YYINITIAL>{LOWERNAME}          {
                                    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
                                }
<YYINITIAL>[0-9]+               {
                                    return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
                                }
<YYINITIAL>"--"[^\n]*		{//single line comment
				}
<YYINITIAL>"(*"			{
				    this.nestedCommentLevel = 0;
				    yybegin(COMMENT);
				}
<COMMENT>[^\n]			{}
<COMMENT>"(*"			{

				    this.nestedCommentLevel++;
				}
<COMMENT>"*)"			{
				    if(this.nestedCommentLevel == 0)
				    {
					yybegin(YYINITIAL);
				    }
				    this.nestedCommentLevel--;
				}
<YYINITIAL>"*)"			{
				    return new Symbol(TokenConstants.ERROR,
					    AbstractTable.idtable.addString("Unmatched *)"));
				}
<YYINITIAL>"<-"                 {
                                    return new Symbol(TokenConstants.ASSIGN);
                                }
<YYINITIAL>"{"                  {
                                    return new Symbol(TokenConstants.LBRACE);
                                }
<YYINITIAL>"}"                  {
                                    return new Symbol(TokenConstants.RBRACE);
                                }
<YYINITIAL>"("                  {
                                    return new Symbol(TokenConstants.LPAREN);
                                }
<YYINITIAL>")"                  {
                                    return new Symbol(TokenConstants.RPAREN);
                                }
<YYINITIAL>";"                  {
                                    return new Symbol(TokenConstants.SEMI);
                                }
<YYINITIAL>":"                  {
                                    return new Symbol(TokenConstants.COLON);
                                }
<YYINITIAL>"+"                  {
                                    return new Symbol(TokenConstants.PLUS);
                                }
<YYINITIAL>"/"                  {
                                    return new Symbol(TokenConstants.DIV);
                                }
<YYINITIAL>"-"                  {
                                    return new Symbol(TokenConstants.MINUS);
                                }
<YYINITIAL>"*"                  {
                                    return new Symbol(TokenConstants.MULT);
                                }
<YYINITIAL>"="                  {
                                    return new Symbol(TokenConstants.EQ);
                                }
<YYINITIAL>"<"                  {
                                    return new Symbol(TokenConstants.LT);
                                }
<YYINITIAL>"<="                 {
                                    return new Symbol(TokenConstants.LE);
                                }
<YYINITIAL>"."                  {
                                    return new Symbol(TokenConstants.DOT);
                                }
<YYINITIAL>"~"                  {
                                    return new Symbol(TokenConstants.NEG);
                                }
<YYINITIAL>","                  {
                                    return new Symbol(TokenConstants.COMMA);
                                }
<YYINITIAL>"@"                  {
                                    return new Symbol(TokenConstants.AT);
                                }
<YYINITIAL>\"                   {//"
                                    currentString = "";
                                    yybegin(STRING);
                                }
<STRING>\\?[^\"^\n]|\\n|\\b|\\t|\\f|\\\\|\\\"|\\\n|"^"
				{//"
				    String toAdd = yytext();
				    if(toAdd.equals("\\n")){
					toAdd = "\n";
				    }
				    if(toAdd.equals("\\b"))
					toAdd = "\b";
				    if(toAdd.equals("\\t"))
					toAdd = "\t";
				    if(toAdd.equals("\\f"))
					toAdd = "\f";
				    if(toAdd.equals("\\\\"))
					toAdd = "\\";
				    if(toAdd.equals("\\\""))
					toAdd = "\"";
				    if(toAdd.equals("\\\n"))
				    {
					curr_lineno++;
					toAdd = "\n";
				    }
				    if(toAdd.length() == 2 && toAdd.charAt(0)=='\\')
					toAdd = toAdd.substring(1);
                                    currentString += toAdd;
				    if(isTooLong(currentString)){
					yybegin(STRING_ERR);
					return new Symbol(TokenConstants.ERROR,
					    AbstractTable.idtable.addString("String constant too long"));
				    }
				    if(hasNullChar(currentString)){
					yybegin(STRING_ERR);
					return new Symbol(TokenConstants.ERROR,
					    AbstractTable.idtable.addString("String contains null character"));
				    }
                                }
<STRING_ERR>[^\"^\n]*[\"\n]	{//"
				    int index = yytext().length() - 1;
				    if(yytext().charAt(index) == '\n'){
					curr_lineno++;
				    }
				    yybegin(YYINITIAL);
				}
<STRING>\n			{
				    yybegin(YYINITIAL);
				    curr_lineno++;
				    
	    			    return  new Symbol(TokenConstants.ERROR,
					    AbstractTable.idtable.addString("Unterminated string constant"));
				}
<STRING>\"                      {//"
				    yybegin(YYINITIAL);                        
                                    return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(currentString));
                                }
.				{
				    //for some reason carriage return (\r) is not detected by a whitespace regexp
				    if(!yytext().equals("\013")){
					return new Symbol(TokenConstants.ERROR,AbstractTable.idtable.addString(yytext()));
				    }
				}
.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
