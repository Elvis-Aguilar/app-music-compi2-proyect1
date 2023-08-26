package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.pista.Token;

/**
 * clase para representar un dato primitivo como: numero, cadena etc, asi mismo
 * representar la variable si fuese el caso, como funciones.
 */
public class Dato {

    private String cadena;
    private double decimal;
    private int numero = 0;
    private char caracter;
    private boolean booleano;
    private int inedexVariabla;
    private Token token;

    private boolean inicializado;

    private TipoDato tipoDato = TipoDato.ENTERO;

    public Dato(boolean inicializado, TipoDato tipoDato) {
        this.inicializado = inicializado;
        this.tipoDato = tipoDato;
    }

    public Dato(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public Dato(boolean inicializado, int entero, TipoDato tipo) {
        this.inicializado = inicializado;
        this.numero = entero;
        this.tipoDato = tipo;
    }

    public Dato(boolean inicializado, Token token, TipoDato tipo) {
        this.inicializado = inicializado;
        this.tipoDato = tipo;
        if (token != null) {
            this.convertirDato(token.getLexeme());
        }
    }

    private void convertirDato(String yytext) {
        switch (this.tipoDato) {
            case CADENA:
                this.cadena = yytext;
                break;
            case BOOLEAN:
                this.booleano = yytext.equalsIgnoreCase("verdadero");
                break;
            case CHAR:
                this.caracter = yytext.charAt(0);
                break;
            case DECIMAL:
                try {
                this.decimal = Double.parseDouble(yytext);
            } catch (NumberFormatException e) {
                this.decimal = 0;
            }
                break;
            default:
                try {
                    this.numero = Integer.parseInt(yytext);
                } catch (NumberFormatException e) {
                    this.decimal = 0;
                }
                break;
        }
    }

    /*espacio para getters y settesrs*/
    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public double getDecimal() {
        return decimal;
    }

    public void setDecimal(double decimal) {
        this.decimal = decimal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public boolean isBooleano() {
        return booleano;
    }

    public void setBooleano(boolean booleano) {
        this.booleano = booleano;
    }

    public int getInedexVariabla() {
        return inedexVariabla;
    }

    public void setInedexVariabla(int inedexVariabla) {
        this.inedexVariabla = inedexVariabla;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    

    @Override
    public String toString() {
        return "Dato{" + "cadena=" + cadena + ", decimal=" + decimal + ", numero=" + numero + ", caracter=" + caracter + ", booleano=" + booleano + ", inedexVariabla=" + inedexVariabla + ", inicializado=" + inicializado + ", tipoDato=" + tipoDato + '}';
    }
    
    

}
