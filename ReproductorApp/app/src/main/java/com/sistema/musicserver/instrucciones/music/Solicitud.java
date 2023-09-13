package com.sistema.musicserver.instrucciones.music;


import java.io.Serializable;
import java.util.ArrayList;

public class Solicitud implements Serializable {

    private String textSolicitud;
    private String nombre = " ";
    private ArrayList<ListaMusical> listasMusicales;
    private ListaMusical lista;
    private PistaMusical pista;
    private static final long serialVersionUID = 6529685098267757690L;

    public Solicitud(String textSolicitud , String nombre) {
        this.textSolicitud = textSolicitud;
        this.nombre = nombre;
    }

    public Solicitud(String textSolicitud ) {
        this.textSolicitud = textSolicitud;
    }



    public String getTextSolicitud() {
        return textSolicitud;
    }

    public void setTextSolicitud(String textSolicitud) {
        this.textSolicitud = textSolicitud;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<ListaMusical> getListasMusicales() {
        return listasMusicales;
    }

    public void setListasMusicales(ArrayList<ListaMusical> listasMusicales) {
        this.listasMusicales = listasMusicales;
    }

    public ListaMusical getLista() {
        return lista;
    }

    public void setLista(ListaMusical lista) {
        this.lista = lista;
    }

    public PistaMusical getPista() {
        return pista;
    }

    public void setPista(PistaMusical pista) {
        this.pista = pista;
    }
}
