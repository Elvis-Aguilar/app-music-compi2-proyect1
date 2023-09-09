package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

public class Asignacion extends Instruccions implements Serializable{

    private int indiceInicial;
    private int indiceFinal;
    private Operation operation;
    private TablaSimbol tableSimbol;
    private Token token;
    private boolean anidado = false;    //cuando esta dentro de una funcion o sencia 
    private ArrayList<Operation> operaciones;
    private boolean isTabla = false;
    private boolean asignacionArrayIndividual = false;

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

    public Asignacion(int indiceInicial, int indiceFinal, TablaSimbol tableSimbol, ArrayList<Operation> operaciones) {
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        this.tableSimbol = tableSimbol;
        this.operaciones = operaciones;
        this.isTabla = true;
    }

    public Asignacion(Operation operation, Token token, ArrayList<Operation> operaciones) {
        this.operation = operation;
        this.token = token;
        this.operaciones = operaciones;
        this.asignacionArrayIndividual = true;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (asignacionArrayIndividual) {
            //al operar los indices verificar si son mayores a 0
            Dato dato = this.operation.execute(errorsSemanticos, this.tableSimbol);
            ArrayList<Integer> indices = indicesCalculados(errorsSemanticos);
            tableSimbol.asignacionValorArray(dato, token, operaciones.size(), indices);
        } else {
            if (isTabla) {
                this.inicializarArreglos(errorsSemanticos);
            } else {
                this.asignarVarDato(errorsSemanticos);
            }
        }
    }

    private void asignarVarDato(ArrayList<ErrorSemantico> errorsSemanticos) {
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

    private void inicializarArreglos(ArrayList<ErrorSemantico> errorsSemanticos) {
        boolean echoUnaves = false;
        for (int i = indiceInicial; i < indiceFinal; i++) {
            Arreglo arry = tableSimbol.getArreglos().get(i);
            if (!echoUnaves) {
                this.operaciones.forEach(op -> {
                    Dato dato = op.execute(errorsSemanticos, tableSimbol);
                    arry.recibirDato(dato, errorsSemanticos);
                });
            } else {
                arry.setDatos(tableSimbol.getArreglos().get(i - 1).getDatos());
            }
        }

    }

    private ArrayList<Integer> indicesCalculados(ArrayList<ErrorSemantico> errorsSemanticos) {
        ArrayList<Integer> indices = new ArrayList<>();
        operaciones.stream().map(operacione -> operacione.execute(errorsSemanticos, tableSimbol)).forEachOrdered(dato -> {
            if (dato.getTipoDato() == TipoDato.ENTERO) {
                if (dato.getNumero() < 0) {
                    errorsSemanticos.add(new ErrorSemantico(token, "Los indices deben No pueden ser menor a cero "));
                }else{
                    indices.add(dato.getNumero());
                }
            } else {
                errorsSemanticos.add(new ErrorSemantico(token, "Los indices deben ser del tipo entero, indice incorrecto al intentar acceder al arreglo"));
                indices.add(1);
            }
        });
        return indices;
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

    public ArrayList<Operation> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operation> operaciones) {
        this.operaciones = operaciones;
    }

    public boolean isIsTabla() {
        return isTabla;
    }

    public void setIsTabla(boolean isTabla) {
        this.isTabla = isTabla;
    }

    public boolean isAsignacionArrayIndividual() {
        return asignacionArrayIndividual;
    }

    public void setAsignacionArrayIndividual(boolean asignacionArrayIndividual) {
        this.asignacionArrayIndividual = asignacionArrayIndividual;
    }

}
