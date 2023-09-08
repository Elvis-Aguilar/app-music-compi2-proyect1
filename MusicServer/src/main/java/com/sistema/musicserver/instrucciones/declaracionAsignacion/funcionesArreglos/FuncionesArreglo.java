
package com.sistema.musicserver.instrucciones.declaracionAsignacion.funcionesArreglos;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class FuncionesArreglo extends Instruccions implements Serializable{
    
    
    private TablaSimbol tablaSimbol;
    private Token token;
    private Dato resultado;
    private TipoOrdenamiento tipoOrden;

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

    public Dato getResultado() {
        return resultado;
    }

    public void setResultado(Dato resultado) {
        this.resultado = resultado;
    }
    
    

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        //verificar que tipo de ordenamieto es
        //si es 
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol = tabla;
    }
    
    
    
    
    
    
}
