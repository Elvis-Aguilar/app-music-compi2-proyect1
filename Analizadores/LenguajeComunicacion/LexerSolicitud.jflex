package com.example.reproductorapp.analizadores;

import com.example.reproductorapp.objects.*;
import java_cup.runtime.*;


%%
/*segunda seccion configuracion*/
%class LexerSolicitud
%public
%line
%column
%unicode
%cup
%state CADE

/*definicion de tokens*/
WhiteSpace = [\r|\n|\r\n|\s\t] | [\t\f]
BRACKETA = "<"
BRACKETC = ">"
BRACKET_TERMINAL = "</"
COMILLAS= "\""

/*palabras reservadas*/
NOMBRE = "Nombre"|"nombre"
SOLICITUD = "Solicitud"|"solicitud"
TIPO = "Tipo"|"tipo"
LISTA = "Lista"|"lista"
PISTA = "Pista"|"pista"


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
{BRACKETA}                { return symbol(sym.BRACKETA,yytext());}
{BRACKETC}                { return symbol(sym.BRACKETC,yytext());}
{BRACKET_TERMINAL}              { return symbol(sym.BRACKET_TERMINAL,yytext());}
{NOMBRE}              { return symbol(sym.NOMBRE,yytext());}
{SOLICITUD}                  { return symbol(sym.SOLICITUD,yytext());}
{TIPO}               { return symbol(sym.TIPO,yytext());}
{LISTA}             { return symbol(sym.LISTA,yytext());}
{PISTA}                 { return symbol(sym.PISTA,yytext());}
{COMILLAS}              {cadena = ""; yybegin(CADE);}
}
<CADE>{
{COMILLAS}                  { yybegin(YYINITIAL); return symbol(sym.CONT_CADENA, cadena);}
[^]                         {cadena+=yytext();}

}


[^] {ErrorSingleton.getInstance().getErrores().add(new Errores("El Token no es reconocido por el lenguaje ", TipoError.LEXICO, new Token(yytext(), yyline+1, yycolumn+1)));}
