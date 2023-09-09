package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import com.sistema.musicserver.tablaSimbol.Variable;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class Funcion extends Instruccions implements Serializable{

    private ArrayList<Variable> parametros;
    private TablaSimbol tableSimbol;
    private String nombre;
    private Token token;
    private ArrayList<Instruccions> instruccions;
    private TipoDato tipoRetono = null;

    public Funcion(ArrayList<Variable> parametros, TablaSimbol tableSimbol, String nombre, Token token, TipoDato tipoRetono) {
        this.parametros = new ArrayList<>(parametros);
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.tipoRetono = tipoRetono;
        this.agregarVarRetornoATableSimbol();
        this.agregarParametroATableSimbol();

    }

    public Funcion(ArrayList<Variable> parametros, TablaSimbol tableSimbol, String nombre, Token token) {
        this.parametros = parametros;
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.agregarParametroATableSimbol();
    }
    
    public Funcion(ArrayList<Variable> parametros, TablaSimbol tableSimbol, String nombre, Token token,ArrayList<Instruccions> instruccions) {
        this.parametros = parametros;
        this.tableSimbol = tableSimbol;
        this.nombre = nombre;
        this.token = token;
        this.instruccions = instruccions;
        this.agregarParametroATableSimbol();
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        for (Instruccions instruccion : instruccions) {
            instruccion.execute(errorsSemanticos);
            if (instruccion instanceof SentenciaRetorna) {
                if (this.tipoRetono == null) {
                    errorsSemanticos.add(new ErrorSemantico(token, "Funcion no tiene definido tipo de retono (void), sentencia retorno no pertenece al funcionamiento de la funcion"));
                }
                break;
            }
        }
    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tableSimbol.setTablaSimbolPadre(tabla);
        instruccions.forEach(inst -> {
            inst.actionReferenciarTabla(tableSimbol);
        });
    }

    private void agregarParametroATableSimbol() {
        this.parametros.forEach(para -> {
            this.tableSimbol.getVariables().add(para);
        });
    }

    private void agregarVarRetornoATableSimbol() {
        this.tableSimbol.getVariables().add(new Variable(token, tipoRetono, "varRetonoSimbolTable"));
    }
    
    
    
    /*Espacio para getter y setters*/
    public ArrayList<Instruccions> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(ArrayList<Instruccions> instruccions) {
        this.instruccions = instruccions;
    }

    public ArrayList<Variable> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Variable> parametros) {
        this.parametros = parametros;
    }

    public TablaSimbol getTableSimbol() {
        return tableSimbol;
    }

    public void setTableSimbol(TablaSimbol tableSimbol) {
        this.tableSimbol = tableSimbol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public TipoDato getTipoRetono() {
        return tipoRetono;
    }

    public void setTipoRetono(TipoDato tipoRetono) {
        this.tipoRetono = tipoRetono;
    }

    @Override
    public String toString() {
        return "Funcion{" + "parametros=" + parametros + ", tableSimbol=" + tableSimbol + ", nombre=" + nombre + ", token=" + token + ", instruccions=" + instruccions + ", tipoRetono=" + tipoRetono + '}';
    }
    
    

}
