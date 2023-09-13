
package com.sistema.musicserver.instrucciones.music;

import java.io.Serializable;

/**
 *
 * @author elvis_agui
 */
public class CanalMusical implements Serializable{
    private int canal;
    private StringBuilder musicString = new StringBuilder();
    private int milisTotal = 0;
    private static final long serialVersionUID = 6529685098267757690L;

    public CanalMusical(int canal, String musicString, int milisTotal) {
        this.canal = canal;
        this.musicString.append(musicString).append(" ");
        this.milisTotal = milisTotal;
    }
    
    
    public void agregarNotas(String nota, int milis){
        this.musicString.append(nota).append(" ");
        this.milisTotal+=milis;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public StringBuilder getMusicString() {
        return musicString;
    }

    public void setMusicString(StringBuilder musicString) {
        this.musicString = musicString;
    }

    public int getMilisTotal() {
        return milisTotal;
    }

    public void setMilisTotal(int milisTotal) {
        this.milisTotal = milisTotal;
    }
    
    
    
    
    
    
    
    
    
    
}
