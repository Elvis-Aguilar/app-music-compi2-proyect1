/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.funcionesArreglos.FuncionesArreglo;
import com.sistema.musicserver.instrucciones.music.SentenciaReproducir;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author elvis_agui
 */
public class NodoOpFuncionesNativas extends NodoOperation implements Serializable{
    
    private SentenciaReproducir reproducir;
    private FuncionesArreglo ordenar;
    
    public NodoOpFuncionesNativas(){
        
    }

    public void capturarReproducir(SentenciaReproducir reproducir){
        this.reproducir= reproducir;
    }
    
    public void capturarOrdenaar( FuncionesArreglo ordenar){
        this.ordenar = ordenar;
    }
    
    @Override
    public Dato executeOp(ArrayList<ErrorSemantico> errorsSemanticos, TablaSimbol tabla) {
        Dato tmp = new Dato(true, 0, TipoDato.ENTERO);
        if (reproducir != null) {
            this.reproducir.actionReferenciarTabla(tabla);
            this.reproducir.execute(errorsSemanticos);
            tmp.setNumero(this.reproducir.getMilis());
        }
        if (ordenar != null) {
           this.ordenar.setTablaSimbol(tabla);
           this.ordenar.execute(errorsSemanticos);
            if (this.ordenar.isIsSumarizar()) {
               tmp.setTipoDato(TipoDato.CADENA);
               tmp.setCadena(this.ordenar.getResultadoSumarizar());
            }else{
                tmp.setNumero(this.ordenar.getResultado());
            }
        }
        return tmp;
    }

    public SentenciaReproducir getReproducir() {
        return reproducir;
    }

    public void setReproducir(SentenciaReproducir reproducir) {
        this.reproducir = reproducir;
    }

    public FuncionesArreglo getOrdenar() {
        return ordenar;
    }

    public void setOrdenar(FuncionesArreglo ordenar) {
        this.ordenar = ordenar;
    }
    
    
    
    
    
}
