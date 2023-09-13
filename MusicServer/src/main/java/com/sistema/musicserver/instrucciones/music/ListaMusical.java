package com.sistema.musicserver.instrucciones.music;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class ListaMusical implements Serializable {

  
    private ArrayList<PistaMusical> listasMusicales = new ArrayList<>();
    private String nombre;
    private boolean random = false;
    private boolean circular = false;
    private static final long serialVersionUID = 6529685098267757690L;


    public ListaMusical(ArrayList<PistaMusical> listasMusicales, String nombre) {
        this.listasMusicales.addAll(listasMusicales);
        this.nombre = nombre;
    }

    public ListaMusical(String nombre) {
        this.nombre = nombre;
    }

    

    public ArrayList<PistaMusical> getListasMusicales() {
        return listasMusicales;
    }

    public void setListasMusicales(ArrayList<PistaMusical> listasMusicales) {
        this.listasMusicales = listasMusicales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

}
