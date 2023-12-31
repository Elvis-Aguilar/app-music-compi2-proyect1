package  com.example.reproductorapp.objects;

import java.io.Serializable;

public class Token implements Serializable {

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
}




