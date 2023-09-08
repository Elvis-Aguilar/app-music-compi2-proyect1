
package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class Mensaje extends Instruccions implements Serializable{
    
    private Operation operation;
    private TablaSimbol  TablaSimbol ;

    public Mensaje(Operation operation) {
        this.operation = operation;
    }

    
    
    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = operation.execute(errorsSemanticos, TablaSimbol);
        FunMensaje.getInstanceMensajes().push(dato);
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.TablaSimbol = tabla;
        
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public TablaSimbol getTablaSimbol() {
        return TablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol TablaSimbol) {
        this.TablaSimbol = TablaSimbol;
    }
    
    
    
    

    
    
    
    
}
