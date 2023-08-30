package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;

public class Asignacion extends Instruccions {

    private int indiceInicial;
    private int indiceFinal;
    private Operation operation;
    private TablaSimbol tableSimbol;
    private Token token;
    private boolean anidado = false;

    public Asignacion(int indiceInicial, int indiceFinal, Operation operation, TablaSimbol table) {
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        this.operation = operation;
        this.tableSimbol = table;
    }

    public Asignacion(Operation operation, Token token, boolean anidado) {
        this.operation = operation;
        this.token = token;
        this.anidado = anidado;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = this.operation.execute(errorsSemanticos, this.tableSimbol);
        if (anidado) {
            this.tableSimbol.asignacionValorVariable(dato, token);
        } else {
            for (int i = indiceInicial; i < indiceFinal; i++) {
                tableSimbol.getVariables().get(i).setDato(dato, errorsSemanticos);
            }
        }
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tableSimbol = tabla;

    }

    public int getIndiceInicial() {
        return indiceInicial;
    }

    public void setIndiceInicial(int indiceInicial) {
        this.indiceInicial = indiceInicial;
    }

    public int getIndiceFinal() {
        return indiceFinal;
    }

    public void setIndiceFinal(int indiceFinal) {
        this.indiceFinal = indiceFinal;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public TablaSimbol getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbol tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isAnidado() {
        return anidado;
    }

    public void setAnidado(boolean anidado) {
        this.anidado = anidado;
    }

}
