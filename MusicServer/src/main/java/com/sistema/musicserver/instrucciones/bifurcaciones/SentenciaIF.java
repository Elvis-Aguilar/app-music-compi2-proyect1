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
public class SentenciaIF extends Instruccions {

    private ArrayList<Instruccions> instruccions;
    private Operation condicion;
    private TablaSimbol tablaSimbol;
    private SentenciaElse sentenciaElse;
    private SentenciaIF sentenciaElseIf;

    public SentenciaIF(ArrayList<Instruccions> instruccions, Operation condicion, TablaSimbol tablaSimbol) {
        this.instruccions = instruccions;
        this.condicion = condicion;
        this.tablaSimbol = tablaSimbol;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (valorBoolean(errorsSemanticos)) {
            this.instruccions.forEach(inst -> {
                inst.execute(errorsSemanticos);
            });
        } else {
            if (null == sentenciaElseIf && null != this.sentenciaElse) {
                this.sentenciaElse.execute(errorsSemanticos);
            } else {
                if (null != sentenciaElseIf) {
                    this.sentenciaElseIf.execute(errorsSemanticos);
                }
            }
        }
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol.setTablaSimbolPadre(tabla);
        if (null != this.sentenciaElseIf) {
            this.sentenciaElseIf.actionReferenciarTabla(tabla);
        }
        if (null != this.sentenciaElse) {
            this.sentenciaElse.actionReferenciarTabla(tabla);
        }
        instruccions.forEach(inst -> {
            inst.actionReferenciarTabla(tablaSimbol);
        });
    }

    private boolean valorBoolean(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = this.condicion.execute(errorsSemanticos, tablaSimbol);
        if (dato.getTipoDato() != TipoDato.BOOLEAN) {
            //error la expresion no es valida, no se obtiene un valor booleano para la condicion Si(expresion boolean)
            errorsSemanticos.add(new ErrorSemantico(dato.getToken(), "expresion no es valida, no se obtiene un valor booleano para la condicion Si(expresion boolean)"));
            return true;
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

    public SentenciaElse getSentenciaElse() {
        return sentenciaElse;
    }

    public void setSentenciaElse(SentenciaElse sentenciaElse) {
        this.sentenciaElse = sentenciaElse;
    }

    public SentenciaIF getSentenciaElseIf() {
        return sentenciaElseIf;
    }

    public void setSentenciaElseIf(SentenciaIF sentenciaElseIf) {
        this.sentenciaElseIf = sentenciaElseIf;
    }

}
