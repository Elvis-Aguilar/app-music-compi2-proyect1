
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.Token;

/**
 *
 * @author elvis_agui
 */
public class ErrorSemantico extends Errors {
    
    public ErrorSemantico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
    
    
}
