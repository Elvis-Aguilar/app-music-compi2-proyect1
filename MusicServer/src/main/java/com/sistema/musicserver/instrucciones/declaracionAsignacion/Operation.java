package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import java.util.ArrayList;

/**
 * clase para representar una operacion 5+2+2 o 5*5>55+2
 */
public class Operation {
    private NodoOperation rootOperation;

    public Operation(Dato dato, TipoOperacion tipoOp){
        this.rootOperation = new NodoOperation(dato, tipoOp);
    }

    public Operation(NodoOperation rootOperation) {
        this.rootOperation = rootOperation;
    }

    public NodoOperation getRootOperation() { 
        return rootOperation; 
    }


    public Dato execute(ArrayList<ErrorSemantico> errorsSemanticos){
        Dato temp =rootOperation.executeOp(errorsSemanticos); 
        return temp;
    }


}
