package com.sistema.musicserver.instrucciones.declaracionAsignacion.funcionesArreglos;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class FuncionesArreglo extends Instruccions implements Serializable {

    private TablaSimbol tablaSimbol;
    private Token token;
    private int resultado = 0;
    private TipoOrdenamiento tipoOrden;
    private boolean isSumarizar = false;
    private String resultadoSumarizar = "";
    private boolean isLongitud = false;
    private String cadena = "";

    public FuncionesArreglo(Token token, TipoOrdenamiento tipoOrden) {
        this.token = token;
        this.tipoOrden = tipoOrden;
    }
    
    public FuncionesArreglo(Token token) {
        this.token = token;
        this.isSumarizar = true;
    }
    
    public FuncionesArreglo(Token token, boolean isLongitud) {
        this.token = token;
        this.isLongitud = isLongitud;
    }
    
    public FuncionesArreglo(String cadena, boolean isLongitud) {
        this.cadena= cadena;
        this.isLongitud = isLongitud;
    }
    
    
    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        //verificar que tipo de ordenamieto es
        //si es
        resultado = 1;
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol = tabla;
    }

    public TablaSimbol getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public TipoOrdenamiento getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(TipoOrdenamiento tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public boolean isIsSumarizar() {
        return isSumarizar;
    }

    public void setIsSumarizar(boolean isSumarizar) {
        this.isSumarizar = isSumarizar;
    }

    public String getResultadoSumarizar() {
        return resultadoSumarizar;
    }

    public void setResultadoSumarizar(String resultadoSumarizar) {
        this.resultadoSumarizar = resultadoSumarizar;
    }

    public boolean isIsLongitud() {
        return isLongitud;
    }

    public void setIsLongitud(boolean isLongitud) {
        this.isLongitud = isLongitud;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
    

}
