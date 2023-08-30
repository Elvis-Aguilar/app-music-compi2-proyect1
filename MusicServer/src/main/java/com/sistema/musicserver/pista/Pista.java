package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.instrucciones.funciones.Funcion;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;

public class Pista {

    private String nombre;
    private TablaSimbol tableSimbolGoblal;
    private ArrayList<Instruccions> instrucciones = new ArrayList<>();
    private ArrayList<ErrorSemantico> errorsSemanticos;
    
    
    public Pista(String nombre, TablaSimbol tableSimbolGoblal, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.errorsSemanticos = errorsSemanticos;
    }
    

    public void carpturarVariablesGlobales(TipoDato tipo, boolean inicializado, Operation op) {
        if (inicializado) {
            int indexI = tableSimbolGoblal.getVariables().size();
            int indexF = indexI + tableSimbolGoblal.getIds().size();
            Asignacion asig = new Asignacion(indexI, indexF, op, tableSimbolGoblal);

            this.instrucciones.add(asig);
        }
        tableSimbolGoblal.capturarVariablesGloblase(tipo, this.nombre, inicializado);

        tableSimbolGoblal.getIds().clear();
    }

    //TODO: incorporar la logica de la busquda de esta variable en el resto de tablas que vaya a extender
    public void capturarAsignacion(Token id, Operation op) {
        /*buscar variable en tabla de datos*/
        int index = this.tableSimbolGoblal.varExiste(id);
        if (index != -1) {
            Asignacion asig = new Asignacion(index, index + 1, op, tableSimbolGoblal);
            this.instrucciones.add(asig);

        }
    }

    public void referenciarTablasPadres() {
        this.instrucciones.forEach(inst -> {
            if (inst instanceof Funcion) {
                inst.actionReferenciarTabla(tableSimbolGoblal);
            }
        });
    }

    public void addInstruccion(Instruccions instruccion) {
        this.instrucciones.add(instruccion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TablaSimbol getTableSimbolGoblal() {
        return tableSimbolGoblal;
    }

    public void setTableSimbolGoblal(TablaSimbol tableSimbolGoblal) {
        this.tableSimbolGoblal = tableSimbolGoblal;
    }

    public ArrayList<Instruccions> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<Instruccions> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public ArrayList<ErrorSemantico> getErrorsSemanticos() {
        return errorsSemanticos;
    }

    public void setErrorsSemanticos(ArrayList<ErrorSemantico> errorsSemanticos) {
        this.errorsSemanticos = errorsSemanticos;
    }

    public void tostringDAts() {
        instrucciones.forEach(instruccione -> {
            instruccione.execute(errorsSemanticos);
        });
        tableSimbolGoblal.getVariables().forEach(var -> {
            System.out.println(var.toString());
        });
        this.errorsSemanticos.forEach(err->{
            System.out.println(err.getDescripcion()+ err.getToken().getLexeme());
        });

    }

//    public void tostringstga(ArrayList<String> arry){
//        arry.forEach(ar -> {
//            System.out.println(ar);
//        });
//    }
}
