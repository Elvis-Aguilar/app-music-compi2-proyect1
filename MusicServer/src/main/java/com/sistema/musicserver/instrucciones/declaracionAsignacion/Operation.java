package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * clase para representar una operacion 5+2+2 o 5*5>55+2
 */
public class Operation implements Serializable{
    private NodoOperation rootOperation;

    public Operation(Dato dato){
        this.rootOperation = new NodoOperation(dato);
    }

    public Operation(NodoOperation rootOperation) {
        this.rootOperation = rootOperation;
    }

    public NodoOperation getRootOperation() { 
        return rootOperation; 
    }


    public Dato execute(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbol tabla){
        Dato temp =rootOperation.executeOp(errorsSemanticos, tabla); 
        return temp;
    }


}
