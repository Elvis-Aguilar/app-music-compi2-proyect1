
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.Token;

/**
 *
 * @author elvis_agui
 */
public class ErrorSintactico extends Errors {
    
    public ErrorSintactico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
