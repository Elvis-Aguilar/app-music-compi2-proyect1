
package com.sistema.musicserver.errors;

import com.sistema.musicserver.analizadores.Token;
import java.io.Serializable;

/**
 *
 * @author elvis_agui
 */
public class ErrorSemantico extends Errors implements Serializable{
    
    public ErrorSemantico(Token token, String descripcion) {
        super(token, descripcion);
    }
    
    
    
}
