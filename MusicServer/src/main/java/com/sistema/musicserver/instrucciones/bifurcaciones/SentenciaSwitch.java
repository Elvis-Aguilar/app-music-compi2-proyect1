package com.sistema.musicserver.instrucciones.bifurcaciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaSwitch extends Instruccions{

    private ArrayList<CasoSwitch> casos;
    private Variable varSwintch;
    private TablaSimbol tablaSimbol;

    public SentenciaSwitch(ArrayList<CasoSwitch> casos, Variable varSwintch, TablaSimbol tablaSimbol) {
        this.casos = casos;
        this.varSwintch = varSwintch;
        this.tablaSimbol = tablaSimbol;
    }
    
    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = this.tablaSimbol.getDato(varSwintch.getToken(), varSwintch.getNombre());
        for (CasoSwitch caso : casos) {
            caso.setDatoSwintch(dato);
            caso.execute(errorsSemanticos);
            if (caso.isSeRealizoAccion()) {
                break;
            }
        }
        
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        this.casos.forEach(caso ->{
            caso.actionReferenciarTabla(tablaSimbol);
        });
    }

    public ArrayList<CasoSwitch> getCasos() {
        return casos;
    }

    public void setCasos(ArrayList<CasoSwitch> casos) {
        this.casos = casos;
    }

    public Variable getVarSwintch() {
        return varSwintch;
    }

    public void setVarSwintch(Variable varSwintch) {
        this.varSwintch = varSwintch;
    }

    public TablaSimbol getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    
    
}
