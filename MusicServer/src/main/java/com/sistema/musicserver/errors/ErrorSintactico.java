
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.Token;
import java.io.Serializable;

/**
 *
 * @author elvis_agui
 */
public class ErrorSintactico extends Errors implements Serializable {
    
    public ErrorSintactico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
}
