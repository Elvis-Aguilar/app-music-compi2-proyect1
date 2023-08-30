
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
public class CasoSwitch extends Instruccions{
    
    private ArrayList<Instruccions> instruccions;
    private TablaSimbol tablaSimbol;
    private Dato dato;
    private Dato datoSwintch;
    private boolean seRealizoAccion;

    public CasoSwitch(ArrayList<Instruccions> instruccions, Dato dato) {
        this.instruccions = instruccions;
        this.dato = dato;
    }
    
    
    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (comprobarCaso(errorsSemanticos)) {
            this.instruccions.forEach(inst ->{
                inst.execute(errorsSemanticos);
            });
            this.seRealizoAccion = true;
        }        
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol = tabla;
        this.instruccions.forEach(inst ->{
            inst.actionReferenciarTabla(tablaSimbol);
        });
    }
    
    private boolean comprobarCaso(ArrayList<ErrorSemantico> errorsSemanticos){
        if (null == this.dato) {
            return true;
        }
        if (this.dato.isIsVariable()) {
            this.dato = this.tablaSimbol.getDato(dato.getToken(), dato.getNombreVar());
        }
        if (this.dato.getTipoDato() == this.datoSwintch.getTipoDato()) {
            return this.comprobarDato();
        }else{
            errorsSemanticos.add(new ErrorSemantico(this.dato.getToken(), "El Dato en la sentencia Switch no corresponde al esperado"));
            return true;
        }
    }
    
    private boolean comprobarDato(){
        switch(this.dato.getTipoDato()){
            case BOOLEAN:
                return this.dato.isBooleano() && this.datoSwintch.isBooleano();
            case CADENA:
                return this.dato.getCadena().equals(this.datoSwintch.getCadena());
            case CHAR:
                return this.dato.getCaracter() == this.datoSwintch.getCaracter();
            case DECIMAL:
                return this.dato.getDecimal() == this.datoSwintch.getDecimal();
            default:
                return this.dato.getNumero() == this.datoSwintch.getNumero();
        }
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

   
    public Dato getDato() {
        return dato;
    }

    public Dato getDatoSwintch() {
        return datoSwintch;
    }

    public void setDatoSwintch(Dato datoSwintch) {
        this.datoSwintch = datoSwintch;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }

    public boolean isSeRealizoAccion() {
        return seRealizoAccion;
    }

    public void setSeRealizoAccion(boolean seRealizoAccion) {
        this.seRealizoAccion = seRealizoAccion;
    }
    
    
    
    
    
    
  
}
