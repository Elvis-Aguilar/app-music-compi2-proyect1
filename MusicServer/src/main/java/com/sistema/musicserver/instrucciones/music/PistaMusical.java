package com.sistema.musicserver.instrucciones.music;

import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;

/**
 *
 * @author elvis_agui
 */
public class PistaMusical {

    private Player player = new Player();
    private Pattern combinedPattern;
    private String nombre;

    public PistaMusical(Pattern combinedPattern, String nombre) {
        this.combinedPattern = combinedPattern;
        this.nombre = nombre;
    }
    
    public void ejecutarMusica(){
        if (combinedPattern != null) {
            player.play(combinedPattern);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
