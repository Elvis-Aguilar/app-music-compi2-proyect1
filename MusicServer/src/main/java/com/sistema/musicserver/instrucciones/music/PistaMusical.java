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
    private int milisTotal = 0;

    public PistaMusical(ArrayList<CanalMusical> canales , String nombre) {
        this.nombre = nombre;
        this.canales.addAll(canales);
        this.calcularMilis();
    }
    
    
    private void calcularMilis(){
        canales.stream().filter(canale -> (this.milisTotal < canale.getMilisTotal())).forEachOrdered(canale -> {
            this.milisTotal = canale.getMilisTotal();
        });
    }

    /*getters and setters*/
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

    public int getMilisTotal() {
        return milisTotal;
    }

    public void setMilisTotal(int milisTotal) {
        this.milisTotal = milisTotal;
    }
    
    

}
