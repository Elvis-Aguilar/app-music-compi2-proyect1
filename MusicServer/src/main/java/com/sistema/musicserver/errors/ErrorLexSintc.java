
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.pista.Token;

/**
 *
 * @author elvis_agui
 */
public class ErrorLexSintc extends Errors{
    
    public ErrorLexSintc(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
