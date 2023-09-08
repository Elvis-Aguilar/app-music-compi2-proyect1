package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaRetorna extends Instruccions implements Serializable{

    private TablaSimbol tableSimbol;
    private Operation operation;
    private Asignacion asignacion;
    private Token token;
    private boolean realizarAccion;
    private ArrayList<ErrorSemantico> errorsSemanticos;

    public SentenciaRetorna(Operation operation, Token token, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.operation = operation;
        this.token = token;
        this.realizarAccion = false;
        this.asignacion = new Asignacion(operation, new Token("varRetonoSimbolTable", token.getLine(), token.getColumn()), true);
        this.errorsSemanticos = errorsSemanticos;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (realizarAccion) {
            this.asignacion.execute(errorsSemanticos);
        }
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tableSimbol = tabla;
        this.realizarAccion = tableSimbol.buscarRetorno("varRetonoSimbolTable");
        this.asignacion.actionReferenciarTabla(this.tableSimbol);
        if (!realizarAccion) {
            this.errorsSemanticos.add(new ErrorSemantico(token, "La funcion no tiene Tipo de Retrono Definido"));
        }
    }

    public TablaSimbol getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbol tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isRealizarAccion() {
        return realizarAccion;
    }

    public void setRealizarAccion(boolean realizarAccion) {
        this.realizarAccion = realizarAccion;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }
    
    

}
