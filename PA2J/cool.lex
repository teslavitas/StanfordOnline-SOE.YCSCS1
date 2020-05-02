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
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF);
%eofval}
%state CLASS_DEF
%state INHERITS
%state TYPESPEC
%state STRING
SPACE = [ \n\t]+
NAME = [a-zA-Z_][a-zA-Z_0-9]*

%class CoolLexer
%cup

%%

<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
class				{
					yybegin(CLASS_DEF);
					return new Symbol(TokenConstants.CLASS);
				}
<YYINITIAL>if			{
				    return new Symbol(TokenConstants.IF);
				}
<YYINITIAL,CLASS_DEF,INHERITS,TYPESPEC>[ \t]
                                {}
<YYINITIAL>[\n]                 {
                                        curr_lineno++;
                                }
<YYINITIAL>[\n]                 {
                                        curr_lineno++;
                                }
<CLASS_DEF>{NAME}               {
                                        yybegin(YYINITIAL);
                                        Symbol result = new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
                                        return result;
                                }
<YYINITIAL>inherits             {
                                    yybegin(INHERITS);
                                    return new Symbol(TokenConstants.INHERITS);
                                }
<INHERITS,TYPESPEC>{NAME}       {
                                    yybegin(YYINITIAL);
                                    return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));
                                }
<YYINITIAL>{NAME}               {
                                    return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
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
                                    yybegin(TYPESPEC);
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
<STRING>[^\"^\n^\n^\t^\f]       {//"
                                    currentString += yytext();
                                }
<STRING>\\n                     {
                                    currentString += "\n";
                                }
<STRING>\\b                     {
                                    currentString += "\b";
                                }
<STRING>\\t                     {
                                    currentString += "\t";
                                }
<STRING>\\f                     {
                                    currentString += "\f";
                                }
<STRING>\"                      {//"
                                    yybegin(YYINITIAL);
                                    return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(currentString));
                                }
.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
