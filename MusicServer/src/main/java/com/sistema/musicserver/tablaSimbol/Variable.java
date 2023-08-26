package com.sistema.musicserver.tablaSimbol;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;

public class Variable {

    private String nombre;
    private TipoDato tipo;
    private String funcionPadre = "";
    private boolean inicializado = false;
    private Token token;
    private Dato dato;

    public Variable(Token token, TipoDato tipo, String funcionPadre, boolean inicializado) {
        this.token = token;
        this.nombre = token.getLexeme();
        this.tipo = tipo;
        this.funcionPadre = funcionPadre;
        this.inicializado = inicializado;
        this.dato = new Dato(inicializado, token, tipo);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public String getFuncionPadre() {
        return funcionPadre;
    }

    public void setFuncionPadre(String funcionPadre) {
        this.funcionPadre = funcionPadre;
    }

    public Dato getDato() {
        return dato;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return "Variable{" + "nombre=" + nombre + ", tipo=" + tipo + ", funcionPadre=" + funcionPadre + dato.toString() + ", inicializado=" + inicializado + '}';
    }

}
