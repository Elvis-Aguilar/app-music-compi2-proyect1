
package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaContinuar extends Instruccions implements Serializable{

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        //logica para salir de los bucles XD
    }
    

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        //referenciar la tabla del padre 
    }
    
}
