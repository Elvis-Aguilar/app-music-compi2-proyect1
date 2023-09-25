package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.errors.ErroresSingleton;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Asignacion;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.ManejadorArreglos;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.instrucciones.funciones.FunMensaje;
import com.sistema.musicserver.instrucciones.funciones.Funcion;
import com.sistema.musicserver.instrucciones.music.HiloReproductorDePista;
import com.sistema.musicserver.instrucciones.music.ManejadorPistaMusical;
import com.sistema.musicserver.instrucciones.music.PistaMusical;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Pista implements Serializable {

    private String nombre;
    private TablaSimbol tableSimbolGoblal;
    private ArrayList<Instruccions> instrucciones = new ArrayList<>();
    private ArrayList<ErrorSemantico> errorsSemanticos;
    private ArrayList<Funcion> funciones = new ArrayList<>();
    private Funcion funPrincipal;
    private int sizeArray = 0;
    private ArrayList<Token> extendiende;
    private PistaMusical pistaMusical;
    private String codigoFuente;

    public Pista(String nombre, TablaSimbol tableSimbolGoblal, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.errorsSemanticos = errorsSemanticos;
    }

    public Pista(ArrayList<Token> extendiende, String nombre, TablaSimbol tableSimbolGoblal, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.errorsSemanticos = errorsSemanticos;
        this.extendiende = extendiende;
        this.accionExtender();
    }

    public Pista(String codigoFuente,PistaMusical pistaMusical, ArrayList<Funcion> funciones, String nombre, TablaSimbol tableSimbolGoblal) {
        this.nombre = nombre;
        this.tableSimbolGoblal = tableSimbolGoblal;
        this.funciones = funciones;
        this.pistaMusical = pistaMusical;
        this.codigoFuente = codigoFuente;
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

    public void capturarArregloGlobales(TipoDato tipo, int dimensionesDecla, int dimesionesAsign, ArrayList<Operation> operations) {
        if (dimensionesDecla > 2) {
            this.errorsSemanticos.add(new ErrorSemantico(this.tableSimbolGoblal.getIds().get(0), "Los arreglos inizializados solo perminte ser de dos dimensiones [][]"));
        }
        int indexI = this.tableSimbolGoblal.getArreglos().size();
        int indexF = indexI + tableSimbolGoblal.getIds().size();
        Asignacion asig = new Asignacion(indexI, indexF, tableSimbolGoblal, operations);
        this.instrucciones.add(asig);
        this.tableSimbolGoblal.capturarArreglos(tipo, dimensionesDecla, this.sizeArray, dimesionesAsign);
        tableSimbolGoblal.getIds().clear();
        this.sizeArray = 0;
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

    public Funcion getFuncionEspecifica(Token id, ArrayList<Operation> parametros, boolean conRetorno, TablaSimbol tablapadre) {
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
            if (comprobarTipos(parametros, funcion.getParametros(), id, tablapadre, funcion.getTableSimbol())) {
                fun = new Funcion(funcion);
                break;
            }

        }
        if (null == fun) {
            this.errorsSemanticos.add(new ErrorSemantico(id, "Funcion no encontrada, posibles errores, parametro incorrectos, nombre incorrecto, el tipo de retorno o no declarada"));
        }

        return fun;
    }

    public boolean comprobarTipos(ArrayList<Operation> parametros, ArrayList<Variable> varParametros, Token id, TablaSimbol tabla, TablaSimbol tablaFun) {
        boolean conciden = true;
        int index = 0;
        for (Variable varParametro : varParametros) {
            Dato dato = parametros.get(index).execute(errorsSemanticos, tabla);
            if (varParametro.getTipo() != dato.getTipoDato()) {
                conciden = false;
                break;
            }
            tablaFun.asignacionValorVariable(dato, varParametro.getToken());
            index++;
        }
        return conciden;
    }

    public void referenciarTablasPadres() {
        if (ErroresSingleton.getInstance().noHayErrores()) {
            this.funciones.forEach(fun -> {
                fun.actionReferenciarTabla(tableSimbolGoblal);
            });
            if (this.funPrincipal != null) {
                this.funPrincipal.actionReferenciarTabla(tableSimbolGoblal);
            }
        }

    }

    /**
     * funcion encargada de unir ambos array, comprobando si son del mismo
     * tamanio
     *
     * @param operations1
     * @param operations2
     * @param token
     * @return
     */
    public ArrayList<Operation> unirOperaciones(ArrayList<Operation> operations1, ArrayList<Operation> operations2, Token token) {
        if (this.sizeArray == 0) {
            this.sizeArray = operations2.size();
        }
        if (this.sizeArray != operations1.size()) {

            this.errorsSemanticos.add(new ErrorSemantico(token, "No cumple con los estandares de inicializacion de arreglos, los campos no son del mismo tamaño"));
        }
        operations1.addAll(operations2);
        return operations1;
    }

    /*funcion que captura un arreglo sin inicializar var entero arreglo algo[2][1+2];*/
    public void captruarDeclaracionArreglo(TipoDato tipoArreglo, ArrayList<Operation> operaciones) {
        ManejadorArreglos manejador = new ManejadorArreglos(tipoArreglo, this.tableSimbolGoblal, operaciones, this.tableSimbolGoblal.getIds());
        this.instrucciones.add(manejador);
        this.tableSimbolGoblal.getIds().clear();
    }

    public void autoguardar() {
        if (PistasCompiladas.getInstancePistasActivacion().sobreEscribir(nombre, errorsSemanticos)) {
            this.pistaMusical = ManejadorPistaMusical.getPistaMusical().pistaMusic(nombre, errorsSemanticos);
            PistasCompiladas.getInstancePistasActivacion().push(this, errorsSemanticos);
        }
    }

    public void accionExtender() {
        for (Token token : extendiende) {
            Pista tmpPista = PistasCompiladas.getInstancePistasActivacion().getPistaExtends(token);
            if (null == tmpPista) {
                this.errorsSemanticos.add(new ErrorSemantico(token, "La pista a extender no existe en el registro de pistas compiladas"));
                break;
            }
            this.tableSimbolGoblal.extenderDeOtraTabla(tmpPista.tableSimbolGoblal.getVariables(), tmpPista.tableSimbolGoblal.getArreglos());
            this.funciones.addAll(tmpPista.getFunciones());
        }
    }

    public void realizarAccionesSemanticas() {
        if (ErroresSingleton.getInstance().noHayErrores()) {
            instrucciones.forEach(instruccione -> {
                instruccione.execute(errorsSemanticos);
            });
            if (null != this.funPrincipal) {
                this.funPrincipal.execute(errorsSemanticos);
            }
        }
    }
    
    public void pushFuncion(Funcion fun){
        boolean capturar = true;
        for (Funcion funcione : funciones) {
            if (funcione.getNombre().equals(fun.getNombre())) {
                capturar = false;
                this.errorsSemanticos.add(new ErrorSemantico(fun.getToken(), "Funcion ya existente, ya fue declarado anterioremte"));
                break;
            }
        }
        if (capturar) {
            this.funciones.add(fun);
        }

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

    public int getSizeArray() {
        return sizeArray;
    }

    public void setSizeArray(int sizeArray) {
        this.sizeArray = sizeArray;
    }

    public PistaMusical getPistaMusical() {
        return pistaMusical;
    }

    public void setPistaMusical(PistaMusical pistaMusical) {
        this.pistaMusical = pistaMusical;
    }

    public String getCodigoFuente() {
        return codigoFuente;
    }

    public void setCodigoFuente(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }
    
    

    public void tostringDAts() {
        instrucciones.forEach(instruccione -> {
            instruccione.execute(errorsSemanticos);
        });
        //TODO: verificar si fun Principal existe
        if (null != this.funPrincipal) {
            this.funPrincipal.execute(errorsSemanticos);
        }
//        tableSimbolGoblal.getVariables().forEach(var -> {
//            System.out.println(var.toString());
//        });
        this.tableSimbolGoblal.getArreglos().forEach(arr -> {
            System.out.println(arr.toString());
        });
        FunMensaje.getInstanceMensajes().getMensajes().forEach(ms -> {
            System.out.println(ms);
        });
        this.errorsSemanticos.forEach(err -> {
            System.out.println(err.getDescripcion() + err.getToken().getLexeme());
        });

    }

}
