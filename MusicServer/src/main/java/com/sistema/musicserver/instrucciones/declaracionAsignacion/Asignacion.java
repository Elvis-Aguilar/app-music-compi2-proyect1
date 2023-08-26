package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.util.ArrayList;

public class Asignacion extends Instruccions {

    private int indiceInicial;
    private int indiceFinal;

    private Operation operation;
    private ArrayList<Variable> variables;

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public Asignacion(int indiceInicial, int indiceFinal, Operation operation, ArrayList<Variable> variables) {
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        this.operation = operation;
        this.variables = variables;
    }

    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        Dato dato = this.operation.execute(errorsSemanticos);
        for (int i = indiceInicial; i < indiceFinal; i++) {
            variables.get(i).setDato(dato);
        }
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

   
   

}
