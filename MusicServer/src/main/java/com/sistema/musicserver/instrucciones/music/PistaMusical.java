package com.sistema.musicserver.instrucciones.music;


import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author elvis_agui
 */
public class PistaMusical implements Serializable{


    private String nombre;
    private ArrayList<CanalMusical> canales = new ArrayList<>();

    public PistaMusical(ArrayList<CanalMusical> canales , String nombre) {
        this.nombre = nombre;
        this.canales.addAll(canales);
    }
    


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<CanalMusical> getCanales() {
        return canales;
    }

    public void setCanales(ArrayList<CanalMusical> canales) {
        this.canales = canales;
    }
    
    

}
