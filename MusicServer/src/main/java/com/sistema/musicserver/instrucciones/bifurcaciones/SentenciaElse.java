package com.sistema.musicserver.instrucciones.bifurcaciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaElse extends Instruccions implements Serializable{

    private ArrayList<Instruccions> instruccions;
    private TablaSimbol tablaSimbol;

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.instruccions.forEach(inst -> {
            inst.execute(errorsSemanticos);
        });

    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.actionReferenciarTabla(tablaSimbol);
        });
    }

    public SentenciaElse(ArrayList<Instruccions> instruccions, TablaSimbol tablaSimbol) {
        this.instruccions = instruccions;
        this.tablaSimbol = tablaSimbol;
    }

    public ArrayList<Instruccions> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccions> instruccions) {
        this.instruccions = instruccions;
    }

    public TablaSimbol getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

}
