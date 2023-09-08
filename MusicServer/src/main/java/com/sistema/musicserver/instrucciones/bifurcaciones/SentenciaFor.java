package com.sistema.musicserver.instrucciones.bifurcaciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaFor extends Instruccions implements Serializable{

    private Instruccions declaracion;
    private TablaSimbol tableSimbol;
    private ArrayList<Instruccions> instruccions;
    private Asignacion incremetDecremet;
    private Operation condicion;

    public SentenciaFor(Instruccions declaracion, TablaSimbol tableSimbol, Asignacion incremetDecremet, Operation condicion) {
        this.declaracion = declaracion;
        this.tableSimbol = tableSimbol;
        this.incremetDecremet = incremetDecremet;
        this.condicion = condicion;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.declaracion.execute(errorsSemanticos);
        while (comprobarCondicion(errorsSemanticos)) {
            this.instruccions.forEach(inst -> {
                inst.execute(errorsSemanticos);
            });
            incremetDecremet.execute(errorsSemanticos);
        }
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tableSimbol.setTablaSimbolPadre(tabla);
        this.declaracion.actionReferenciarTabla(tableSimbol);
        this.incremetDecremet.actionReferenciarTabla(tableSimbol);
        this.instruccions.forEach(inst -> {
            inst.actionReferenciarTabla(tableSimbol);
        });
    }

    private boolean comprobarCondicion(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = condicion.execute(errorsSemanticos, tableSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {
            //error la expresion no es valida, no se obtiene un valor booleano para la condicion Si(expresion boolean)
            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "El condicional de la sentancia PARA debe ser tipo booleana"));
            return false;
        }
        return dato.isBooleano();
    }

    public Operation getCondicion() {
        return condicion;
    }

    public void setCondicion(Operation condicion) {
        this.condicion = condicion;
    }

    public Instruccions getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(Instruccions declaracion) {
        this.declaracion = declaracion;
    }

    public TablaSimbol getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbol tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public ArrayList<Instruccions> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccions> instruccions) {
        this.instruccions = instruccions;
    }

    public Asignacion getIncremetDecremet() {
        return incremetDecremet;
    }

    public void setIncremetDecremet(Asignacion incremetDecremet) {
        this.incremetDecremet = incremetDecremet;
    }

}
