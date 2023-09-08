
package com.sistema.musicserver.instrucciones.music;

import java.io.Serializable;

/**
 *
 * @author elvis_agui
 */
public class CanalMusical implements Serializable{
    private int canal;
    private StringBuilder musicString = new StringBuilder();

    public CanalMusical(int canal, String musicString) {
        this.canal = canal;
        this.musicString.append(musicString).append(" ");
    }
    
    
    public void agregarNotas(String nota){
        this.musicString.append(nota).append(" ");
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
    
    
    
    
    
    
    
    
    
    
}
