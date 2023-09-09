package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.funciones.Funcion;
import com.sistema.musicserver.pista.Pista;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

public class NodoOperation implements Serializable{

    private Dato dato;
    private TipoOperacion tipoOperacion;
    private NodoOperation opLeft;
    private NodoOperation opRight;
    private CasteoOperacion operation;
    private boolean funcion = false;
    private Token tokenFun;
    private ArrayList<Operation> parametros;
    private Pista pista;
    private boolean retorna = true;
    private boolean isOrdenar;

    public NodoOperation(Token tokenFun, ArrayList<Operation> parametros, Pista pista) {
        this.tokenFun = tokenFun;
        this.pista = pista;
        this.funcion = true;
        this.dato = new Dato(false, 0, TipoDato.ENTERO);
        this.capturarParametros(parametros);
    }

    public NodoOperation(Dato dato) {
        this.dato = dato;
        this.tipoOperacion = null;
        this.opLeft = null;
        this.opRight = null;
    }
    
    public NodoOperation() {
        
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
        Dato tmp = this.dato;
        if (funcion) {
            Funcion fun = pista.getFuncionEspecifica(tokenFun, parametros, retorna);
            if (fun != null) {
                fun.execute(errorsSemanticos);
                if (!fun.getTableSimbol().getVariables().isEmpty()) {
                    tmp.setInicializado(true);
                    tmp.setValorDato(fun.getTableSimbol().getVariables().get(0).getDato());
                    
                }
            }
            return tmp;
        }
        if (this.tipoOperacion == null) {
            if (this.dato.isIsVariable()) {
                tmp.setInicializado(true);
                tmp.setValorDato(tabla.getDato(dato.getToken(), dato.getNombreVar()));
            }
            if (this.dato.isIsVarArreglo()) {
                tmp.setInicializado(true);
                tmp.setValorDato(tabla.getDatoArreglo(dato));
            }
            return tmp;
        }
        Dato datoLeft = this.opLeft.executeOp(errorsSemanticos, tabla);
        Dato datoRight = this.opRight.executeOp(errorsSemanticos, tabla);
        //funcion que realize la operacion
        operation = new CasteoOperacion(errorsSemanticos);
        return this.operation.resultOp(datoLeft, datoRight, tipoOperacion);

    }

    private void capturarParametros(ArrayList<Operation> parameRecibidos) {
        this.parametros = new ArrayList<>();
        parameRecibidos.forEach(para -> {
            this.parametros.add(para);
        });
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
    public CasteoOperacion getOperation() {
        return operation;
    }

    public void setOperation(CasteoOperacion operation) {
        this.operation = operation;
    }

    public boolean isFuncion() {
        return funcion;
    }

    public void setFuncion(boolean funcion) {
        this.funcion = funcion;
    }

    public Token getTokenFun() {
        return tokenFun;
    }

    public void setTokenFun(Token tokenFun) {
        this.tokenFun = tokenFun;
    }

    public ArrayList<Operation> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Operation> parametros) {
        this.parametros = parametros;
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public boolean isRetorna() {
        return retorna;
    }

    public void setRetorna(boolean retorna) {
        this.retorna = retorna;
    }

}
