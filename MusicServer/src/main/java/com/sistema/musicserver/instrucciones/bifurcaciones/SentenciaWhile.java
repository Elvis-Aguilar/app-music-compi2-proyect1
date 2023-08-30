package com.sistema.musicserver.instrucciones.bifurcaciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaWhile extends Instruccions {

    private ArrayList<Instruccions> instruccions;
    private Operation condicion;
    private TablaSimbol tablaSimbol;

    public SentenciaWhile(ArrayList<Instruccions> instruccions, Operation condicion, TablaSimbol tablaSimbol) {
        this.instruccions = instruccions;
        this.condicion = condicion;
        this.tablaSimbol = tablaSimbol;
        
    }
    
    
    

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.actionReferenciarTabla(tablaSimbol);
        });
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        while (valorBoolean(errorsSemanticos)) {
            instruccions.forEach(inst -> {
                inst.execute(errorsSemanticos);
            });
        }
    }

    private boolean valorBoolean(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = this.condicion.execute(errorsSemanticos, tablaSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {
            //error la expresion no es valida, no se obtiene un valor booleano para la condicion Si(expresion boolean)
            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "expresion no es valida, no se obtiene un valor booleano para la condicion Mientras(expresion boolean)"));
            return false;
        }
        return dato.isBooleano();
    }

    public ArrayList<Instruccions> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccions> instruccions) {
        this.instruccions = instruccions;
    }

    public Operation getCondicion() {
        return condicion;
    }

    public void setCondicion(Operation condicion) {
        this.condicion = condicion;
    }

    public TablaSimbol getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }
    
    

}
