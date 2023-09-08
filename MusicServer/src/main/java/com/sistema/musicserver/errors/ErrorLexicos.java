
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.pista.Token;

/**
 *
 * @author elvis_agui
 */
public class ErrorLexicos extends Errors{
    
    public ErrorLexicos(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
