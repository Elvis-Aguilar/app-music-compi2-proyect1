package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class DeclaracionVar extends Instruccions implements Serializable{

    private ArrayList<Variable> variables;
    private TablaSimbol tableSimbol;
    private boolean asignacion; // var entero algo = 0;
    private Asignacion asig;
    private Operation op;

    public DeclaracionVar(ArrayList<Variable> variables, boolean asignacion, Operation op) {
        this.variables = variables;
        this.asignacion = asignacion;
        this.op = op;
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tableSimbol = tabla;
        if (asignacion) {
            int indexI = this.tableSimbol.getVariables().size();
            int indexF = indexI + this.variables.size();
            this.asig = new Asignacion(indexI, indexF, op, tableSimbol);
        }
        variables.forEach(var -> {
            this.tableSimbol.varYaDeclarada(var.getToken());
            this.tableSimbol.getVariables().add(var);
        });
        //this.variables.clear();
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (asignacion) {
            this.asig.execute(errorsSemanticos);
        }
    }

    /*getter and setters*/
    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public TablaSimbol getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbol tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public boolean isAsignacion() {
        return asignacion;
    }

    public void setAsignacion(boolean asignacion) {
        this.asignacion = asignacion;
    }

    public Asignacion getAsig() {
        return asig;
    }

    public void setAsig(Asignacion asig) {
        this.asig = asig;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

}
