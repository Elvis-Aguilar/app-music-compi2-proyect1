/*primer seccion codigo de usuario*/
//package ;
package com.sistema.musicserver.analizadores.pista;
import java_cup.runtime.*;
import com.sistema.musicserver.errors.ErrorLexicos;
import com.sistema.musicserver.errors.ErroresSingleton;
import com.sistema.musicserver.analizadores.Token;

%%
/*segunda seccion configuracion*/
%class LexerPista
%public
%line
%column
%unicode
%cup
%state CADE

/*definicion de tokens*/
WhiteSpace = [\r|\n|\r\n|\s\t] | [\t\f]
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
LETRA = [a-zA-Z|ñ]
DECIMAL = ([0-9]+[.]([0-9]+))
NUM_ENTERO = [0-9]+
LLAVEA = "\{"
LLAVEC = "\}"
PARENTESISA ="\("
PARENTESISC ="\)"
COMA = ","
PUNTOCOMA = ";"
IGUAL="="
CORCHETA="\["
CORCHETC="\]"
COMILLAS= "\""
DOPUNTO = ":"
DIGONALB = "_"


/*operadores relacioneales*/
EQUALS=({IGUAL}{IGUAL})
NOTEQUALS="!="
MENORQ="<"
MAYORQ=">"
MAYOROI=">="
MENOROI="<="
ISNULO = "!!"

/*opreadores de incremento y decremento*/
SUMA_IMPLICITA="+="
INCREMENT="++"
DECREMENT="--"




/*opreadores logicos*/

OR="||"
AND="&&"
NAND="!&&"
NOR = "!||"
XOR = "&|"
NOT = "!"

/*operadores aritmeticos*/
POR = "*"
DIVISION = "/"
MENOS = "-"
MAS = "+"
MODULO = "%"
POTENCIA = "^"




/*palabras reservadas*/
PISTA = "pista"|"Pista"
KEEP = "keep"|"Keep"
EXTIENDE = "extiende" | "Extiende"
ENTERO = "entero" | "Entero"
DOBLE = "doble"| "Doble"
BOOLEAN = "boolean" | "Boolean"
CARACTER = "caracter" | "Caracter"
VERDADERO = "verdadero" | "Verdadero" | "true" | "True"
FALSO = "falso" | "Falso" | "false" | "False"
CONT_CARACTER = "'" "#"? [^] "'"
CADENA = "cadena"| "Cadena"
VAR = "var"| "Var"
ARREGLO = "arreglo"| "Arreglo"
SI = "si"| "Si"
SINO = "sino"| "Sino"
SWITCH = "switch"| "Switch"
CASO = "caso"| "Caso"
DEFAULT = "default" | "Default"
SALIR = "salir" | "Salir"
PARA = "para"|"Para"
MIENTRAS = "mientras"|"Mientras"
HACER = "Hacer"|"hacer"
CONTINUAR="continuar"|"Continuar"
RETORNA = "retorna"|"Retorna"
REPRODUCIR = "reproducir"|"Reproducir"
ESPERAR = "esperar"|"Esperar"
ORDENAR = "ordenar"|"Ordenar"
SUMARIZAR = "sumarizar"|"Sumarizar"
LONGITUD = "longitud"|"Longitud"
MENSAJE = "mensaje"|"Mensaje"
PRINCIPAL = "principal"|"Principal"  
ASCENDENTE = "ascendente"|"Ascendente"
DESCENDENTE = "descendente"|"Descendente"
PARES = "pares"|"Pares"
IMPARES = "impares"|"Impares"
PRIMOS = "primos"|"Primos"

NOTA_DO = "Do"                                    
NOTA_DO_S = "Do#"                                   
NOTA_RE = "Re"                                  
NOTA_RE_S = "Re#"                                   
NOTA_MI = "Mi"                                    
NOTA_FA = "Fa"                                    
NOTA_FA_S = "Fa#"                                   
NOTA_SOL = "Sol"                                  
NOTA_SOL_S = "Sol#"                                  
NOTA_LA = "La"                                    
NOTA_LA_S = "La#"                                  


ID = (({LETRA}|{DIGONALB})({LETRA}|{NUM_ENTERO}|{DIGONALB})*)


/*apartado para comentarios*/
EndOfLineComment     = ">>" {InputCharacter}* {LineTerminator}?
TraditionalComment   = "<-" [^*] ~"->" | "<-" "-"+ ">"
CommentContent       = ( [^*] | <-+ [^<-] )*
DocumentationComment = "<--" {CommentContent} "-"+ ">"
COMMET = ({TraditionalComment} | {EndOfLineComment} | {DocumentationComment})

/*comodin % para agregar codigo java*/
%{
  
    private Symbol symbol(int type, String lexema) {
        return new Symbol(type, new Token(lexema, yyline + 1, yycolumn + 1));
    }
    private String cadena ="";

    public String limpiarCaracter(String lexema){
        if (lexema.length()>1) {
            String cara = ""+lexema.charAt(1);
            switch(cara){
                case "n":
                    lexema = "\n";
                    break;
                case "t":
                    lexema = "\t";
                    break;
                case "r":
                    lexema = "\r";
                    break;
                case "f":
                    lexema = "\f";
                    break;
            }
            lexema = cara;
        }
        return lexema;
    }
    
%}

/*accion al finlizar el texto*/
%eof{
   System.out.println("LLegue al final desde flex");
%eof}


%%
/* reglas lexicas */
<YYINITIAL> {
{WhiteSpace} 	        {/* ignoramos */}
{DECIMAL}               { return symbol(sym.DECIMAL,yytext());}
{NUM_ENTERO}            { return symbol(sym.NUM_ENTERO,yytext());}
{LLAVEA}                { return symbol(sym.LLAVEA,yytext());}
{LLAVEC}                { return symbol(sym.LLAVEC,yytext());}
{PARENTESISA}           { return symbol(sym.PARENTESISA,yytext());}
{PARENTESISC}           { return symbol(sym.PARENTESISC,yytext());}
{COMA}                  { return symbol(sym.COMA,yytext());}
{PUNTOCOMA}             { return symbol(sym.PUNTOCOMA,yytext());}
{IGUAL}                 { return symbol(sym.IGUAL,yytext());}
{CORCHETA}              { return symbol(sym.CORCHETA,yytext());}
{CORCHETC}              { return symbol(sym.CORCHETC,yytext());}
{EQUALS}                { return symbol(sym.EQUALS,yytext());}
{NOTEQUALS}             { return symbol(sym.NOTEQUALS,yytext());}
{MENORQ}                { return symbol(sym.MENORQ,yytext());}
{MAYORQ}                { return symbol(sym.MAYORQ,yytext());}
{MAYOROI}               { return symbol(sym.MAYOROI,yytext());}
{MENOROI}               { return symbol(sym.MENOROI,yytext());}
{ISNULO}                { return symbol(sym.ISNULO,yytext());}
{SUMA_IMPLICITA}        { return symbol(sym.SUMA_IMPLICITA,yytext());}
{INCREMENT}             { return symbol(sym.INCREMENT,yytext());}
{DECREMENT}             { return symbol(sym.DECREMENT,yytext());}
{NOT}                   { return symbol(sym.NOT,yytext());}
{OR}                    { return symbol(sym.OR,yytext());}
{AND}                   { return symbol(sym.AND,yytext());}
{NAND}                  { return symbol(sym.NAND,yytext());}
{NOR}                   { return symbol(sym.NOR,yytext());}
{XOR}                   { return symbol(sym.XOR,yytext());}
{POR}                   { return symbol(sym.POR,yytext());}
{DIVISION}              { return symbol(sym.DIVISION,yytext());}
{MENOS}                 { return symbol(sym.MENOS,yytext());}
{MAS}                   { return symbol(sym.MAS,yytext());}
{MODULO}                { return symbol(sym.MODULO,yytext());}
{POTENCIA}              { return symbol(sym.POTENCIA,yytext());}
{PISTA}                 { return symbol(sym.PISTA,yytext());}
{KEEP}                  { return symbol(sym.KEEP,yytext());}
{EXTIENDE}              { return symbol(sym.EXTIENDE,yytext());}
{ENTERO}                { return symbol(sym.ENTERO,yytext());}
{DOBLE}                 { return symbol(sym.DOBLE,yytext());}
{BOOLEAN}               { return symbol(sym.BOOLEAN,yytext());}
{CARACTER}              { return symbol(sym.CARACTER,yytext());}
{VERDADERO}             { return symbol(sym.VERDADERO,yytext());}
{FALSO}                 { return symbol(sym.FALSO,yytext());}
{CONT_CARACTER}         { return symbol(sym.CONT_CARACTER,limpiarCaracter(yytext()));}
{CADENA}                { return symbol(sym.CADENA,yytext());}
{VAR}                   { return symbol(sym.VAR,yytext());}
{SINO}                  { return symbol(sym.SINO,yytext());}
{SI}                    { return symbol(sym.SI,yytext());}
{SWITCH}                { return symbol(sym.SWITCH,yytext());}
{CASO}                  { return symbol(sym.CASO,yytext());}
{DEFAULT}               { return symbol(sym.DEFAULT,yytext());}
{SALIR}                 { return symbol(sym.SALIR,yytext());}
{PARA}                  { return symbol(sym.PARA,yytext());}
{MIENTRAS}              { return symbol(sym.MIENTRAS,yytext());}
{HACER}                 { return symbol(sym.HACER,yytext());}
{CONTINUAR}             { return symbol(sym.CONTINUAR,yytext());}
{RETORNA}               { return symbol(sym.RETORNA,yytext());}
{REPRODUCIR}            { return symbol(sym.REPRODUCIR,yytext());}
{ESPERAR}               { return symbol(sym.ESPERAR,yytext());}
{ORDENAR}               { return symbol(sym.ORDENAR,yytext());}
{SUMARIZAR}             { return symbol(sym.SUMARIZAR,yytext());}
{LONGITUD}              { return symbol(sym.LONGITUD,yytext());}
{MENSAJE}               { return symbol(sym.MENSAJE,yytext());}
{PRINCIPAL}             { return symbol(sym.PRINCIPAL,yytext());}
{DOPUNTO}               { return symbol(sym.DOPUNTO,yytext());}
{ARREGLO}               { return symbol(sym.ARREGLO,yytext());}
{NOTA_DO}               {return symbol(sym.NOTA_DO,yytext());}                                  
{NOTA_DO_S}             {return symbol(sym.NOTA_DO_S,yytext());}                                  
{NOTA_RE}               {return symbol(sym.NOTA_RE,yytext());}                                
{NOTA_RE_S}             {return symbol(sym.NOTA_RE_S,yytext());}                                  
{NOTA_MI}               {return symbol(sym.NOTA_MI,yytext());}                                  
{NOTA_FA}               {return symbol(sym.NOTA_FA,yytext());}                                  
{NOTA_FA_S}             {return symbol(sym.NOTA_FA_S,yytext());}                                  
{NOTA_SOL}              {return symbol(sym.NOTA_SOL,yytext());}                                 
{NOTA_SOL_S}            {return symbol(sym.NOTA_SOL_S,yytext());}                                  
{NOTA_LA}               {return symbol(sym.NOTA_LA,yytext());}                                  
{NOTA_LA_S}             {return symbol(sym.NOTA_LA_S,yytext());}  
{ASCENDENTE}            {return symbol(sym.ASCENDENTE,yytext());}      
{DESCENDENTE}           {return symbol(sym.DESCENDENTE,yytext());}      
{PARES}                 {return symbol(sym.PARES,yytext());} 
{IMPARES}               {return symbol(sym.IMPARES,yytext());}  
{PRIMOS}                {return symbol(sym.PRIMOS,yytext());}                                 
{COMMET}                { /*return symbol(sym.COMMET,yytext());*/}
{ID}                    { return symbol(sym.ID,yytext());}
{COMILLAS}              {cadena = ""; yybegin(CADE);}

}

<CADE>{
{COMILLAS}                  { yybegin(YYINITIAL); return symbol(sym.CONT_CADENA, cadena);}
[^]                         {cadena+=yytext();}

}

[^] {ErroresSingleton.getInstance().getErroresLexicos().add(new ErrorLexicos(new Token(yytext(), yyline+1, yycolumn+1), "El Token no es reconocido por el lenguaje"));}
