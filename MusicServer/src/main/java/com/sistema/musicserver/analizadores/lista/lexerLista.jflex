package com.sistema.musicserver.analizadores.lista;
import java_cup.runtime.*;
import com.sistema.musicserver.errors.ErrorLexicos;
import com.sistema.musicserver.errors.ErroresSingleton;
import com.sistema.musicserver.analizadores.Token;

%%
/*segunda seccion configuracion*/
%class LexerLista
%public
%line
%column
%unicode
%cup
%state CADE

/*definicion de tokens*/
WhiteSpace = [\r|\n|\r\n|\s\t] | [\t\f]

LETRA = [a-zA-Z|ñ]
NUM_ENTERO = [0-9]+
LLAVEA = "\{"
LLAVEC = "\}"
CORCHETA="\["
CORCHETC="\]"
DOPUNTO = ":"
DIGONALB = "_"
COMA = ","
COMILLAS= "\""
FALSO = "falso" | "Falso" | "false" | "False"
VERDADERO = "verdadero" | "Verdadero" | "true" | "True"

/*palabras reservadas*/
NOMBRE = "Nombre"|"nombre"
LISTA = "Lista"|"lista"
RANDOM = "random"| "Random"
CIRCULAR = "Circular"|"circular"
PISTAS = "Pistas"|"pistas"



ID = (({LETRA}|{DIGONALB})({LETRA}|{NUM_ENTERO}|{DIGONALB})*)

/*comodin % para agregar codigo java*/
%{
  
    private Symbol symbol(int type, String lexema) {
        return new Symbol(type, new Token(lexema, yyline + 1, yycolumn + 1));
    }
    private String cadena ="";

    
    
%}

/*accion al finlizar el texto*/
%eof{
   System.out.println("LLegue al final desde flex");
%eof}

%%
/* reglas lexicas */
<YYINITIAL> {
{WhiteSpace} 	        {/* ignoramos */}
{LLAVEA}                { return symbol(sym.LLAVEA,yytext());}
{LLAVEC}                { return symbol(sym.LLAVEC,yytext());}
{CORCHETA}              { return symbol(sym.CORCHETA,yytext());}
{CORCHETC}              { return symbol(sym.CORCHETC,yytext());}
{COMA}                  { return symbol(sym.COMA,yytext());}
{DOPUNTO}               { return symbol(sym.DOPUNTO,yytext());}
{VERDADERO}             { return symbol(sym.VERDADERO,yytext());}
{FALSO}                 { return symbol(sym.FALSO,yytext());}
{NOMBRE}               { return symbol(sym.NOMBRE,yytext());}
{LISTA}               {return symbol(sym.LISTA,yytext());}
{CIRCULAR}               { return symbol(sym.CIRCULAR,yytext());}
{RANDOM}               { return symbol(sym.RANDOM,yytext());}
{PISTAS}               { return symbol(sym.PISTAS,yytext());}
{ID}                    { return symbol(sym.ID,yytext());}
{COMILLAS}              {cadena = ""; yybegin(CADE);}
}
<CADE>{
{COMILLAS}                  { yybegin(YYINITIAL); return symbol(sym.CONT_CADENA, cadena);}
[^]                         {cadena+=yytext();}

}


[^] {ErroresSingleton.getInstance().getErroresLexicos().add(new ErrorLexicos(new Token(yytext(), yyline+1, yycolumn+1), "El Token no es reconocido por el lenguaje"));}
