package com.sistema.musicserver.pista;

import com.sistema.musicserver.instrucciones.music.PistaMusical;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class Lista implements Serializable {

    private ArrayList<PistaMusical> listasMusicales = new ArrayList<>();
    private String nombre;

    public Lista() {
    }

    public Lista(ArrayList<PistaMusical> listasMusicales, String nombre) {
        this.listasMusicales = listasMusicales;
        this.nombre = nombre;
    }

    public Lista(String nombre) {
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

}
