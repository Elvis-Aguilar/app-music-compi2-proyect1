package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.instrucciones.funciones.FunMensaje;
import com.sistema.musicserver.instrucciones.funciones.Funcion;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.util.ArrayList;

public class Pista {

    private String nombre;
    private TablaSimbol tableSimbolGoblal;
    private ArrayList<Instruccions> instrucciones = new ArrayList<>();
    private ArrayList<ErrorSemantico> errorsSemanticos;
    private ArrayList<Funcion> funciones = new ArrayList<>();
    private Funcion funPrincipal;

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

    public Funcion getFuncionEspecifica(Token id, ArrayList<Dato> parametros, boolean conRetorno) {
        Funcion fun = null;
        for (Funcion funcion : funciones) {
            if (!id.getLexeme().equals(funcion.getNombre())) {
                continue;
            }
            if (conRetorno && null == funcion.getTipoRetono()) {
                continue;
            }
            if (funcion.getParametros().size() != parametros.size()) {
                continue;
            }
            if (comprobarTipos(parametros, funcion.getParametros(), id)) {
                fun = funcion;
                break;
            }

        }
        if (null == fun) {
            this.errorsSemanticos.add(new ErrorSemantico(id, "Funcion no encontrada, posibles errores, parametro incorrectos, nombre incorrecto, el tipo de retorno o no declarada"));
        }

        return fun;
    }

    public boolean comprobarTipos(ArrayList<Dato> parametros, ArrayList<Variable> varParametros, Token id) {
        boolean conciden = true;
        int index = 0;
        for (Variable varParametro : varParametros) {
            if (varParametro.getTipo() != parametros.get(index).getTipoDato()) {
                conciden = false;
                break;
            }
            varParametro.setDato(parametros.get(index), errorsSemanticos);
            index++;
        }
        return conciden;
    }

    public void referenciarTablasPadres() {
        this.funciones.forEach(fun -> {
            fun.actionReferenciarTabla(tableSimbolGoblal);
        });
        this.funPrincipal.actionReferenciarTabla(tableSimbolGoblal);

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

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }

    public Funcion getFunPrincipal() {
        return funPrincipal;
    }

    public void setFunPrincipal(Funcion funPrincipal) {
        this.funPrincipal = funPrincipal;
    }
    
    

    public void tostringDAts() {
        instrucciones.forEach(instruccione -> {
            instruccione.execute(errorsSemanticos);
        });
        //TODO: verificar si fun Principal existe
        this.funPrincipal.execute(errorsSemanticos);
        tableSimbolGoblal.getVariables().forEach(var -> {
            System.out.println(var.toString());
        });
        FunMensaje.getInstanceMensajes().getMensajes().forEach(ms ->{
            System.out.println(ms);
        });
        this.errorsSemanticos.forEach(err -> {
            System.out.println(err.getDescripcion() + err.getToken().getLexeme());
        });

    }

//    public void tostringstga(ArrayList<String> arry){
//        arry.forEach(ar -> {
//            System.out.println(ar);
//        });
//    }
}
