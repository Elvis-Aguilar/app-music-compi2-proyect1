package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.NodoOperation;

/**
 *
 * @author elvis_agui
 */
public class LlamadaFuncion extends Instruccions {

    private NodoOperation rootOperation;

    public LlamadaFuncion(NodoOperation rootOperation) {
        this.rootOperation = rootOperation;
    }
    
    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.rootOperation.setRetorna(false);
        this.rootOperation.executeOp(errorsSemanticos, null);
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {

    }

    public NodoOperation getRootOperation() {
        return rootOperation;
    }

    public void setRootOperation(NodoOperation rootOperation) {
        this.rootOperation = rootOperation;
    }
    
    

}
