package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.Token;
import java.io.Serializable;

/**
 *
 * @author elvis_agui
 */
public class Errors implements Serializable{

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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    

}
