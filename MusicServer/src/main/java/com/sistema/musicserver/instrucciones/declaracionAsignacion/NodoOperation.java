package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;

public class NodoOperation {

    private Dato dato;
    private TipoOperacion tipoOperacion;
    private NodoOperation opLeft;
    private NodoOperation opRight;
    private CasteoOperacion operation;

    

    public NodoOperation(Dato dato) {
        this.dato = dato;
        this.tipoOperacion = null;
        this.opLeft = null;
        this.opRight = null;
    }

    public NodoOperation(TipoOperacion tipoOperacion, NodoOperation opLeft, NodoOperation opRight) {
        this.tipoOperacion = tipoOperacion;
        this.opLeft = opLeft;
        this.opRight = opRight;
    }

    /**
     * funcion para ejecutar la operacion post orden
     *
     * @param errorsSemanticos
     * @param tabla
     * @return
     */
    public Dato executeOp(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbol tabla) {
        if (this.tipoOperacion == null) {
            if (this.dato.isIsVariable()) {
                this.dato = tabla.getDato(dato.getToken(), dato.getNombreVar());
            }
            return this.dato;
        }
        Dato datoLeft = this.opLeft.executeOp(errorsSemanticos, tabla);
        Dato datoRight = this.opRight.executeOp(errorsSemanticos, tabla);
        //funcion que realize la operacion
        operation = new CasteoOperacion(errorsSemanticos);
        return this.operation.resultOp(datoLeft, datoRight, tipoOperacion);

    }

    /*apartado para getter y setters*/
    public NodoOperation getOpLeft() {
        return opLeft;
    }

    public void setOpLeft(NodoOperation opLeft) {
        this.opLeft = opLeft;
    }

    public NodoOperation getOpRight() {
        return opRight;
    }

    public void setOpRight(NodoOperation opRight) {
        this.opRight = opRight;
    }

    public Dato getDato() {
        return dato;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    

//    public void insertSiguineteOp(Dato dato, TipoOperacion tipoOP) {
//        if (this.siguienteOP == null) {
//            this.siguienteOP = new NodoOperation(dato, tipoOP);
//        } else {
//            this.siguienteOP.insertSiguineteOp(dato, tipoOP);
//        }
//
//    }
}
