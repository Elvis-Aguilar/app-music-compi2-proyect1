package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.pista.Token;

/**
 *
 * @author elvis_agui
 */
public class Errors {

    protected Token token;
    protected String descripcion;
    
    protected Errors(Token token, String descripcion) {
        this.token = token;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
