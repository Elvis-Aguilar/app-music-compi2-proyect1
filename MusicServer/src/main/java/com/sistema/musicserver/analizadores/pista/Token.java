package com.sistema.musicserver.analizadores.pista;



public class Token {

    private final String lexeme;
    private final int line;
    private final int column;



    public Token(String lexema, int linea, int columna) {
        this.lexeme = lexema;
        this.line = linea;
        this.column = columna;

    }

    public Token(int fila, int columna) {
        this(null, fila, columna);
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Token{" + "lexeme=" + lexeme + ", line=" + line + ", column=" + column + '}';
    }
    
    


}