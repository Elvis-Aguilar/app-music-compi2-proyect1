package com.sistema.musicserver.instrucciones.music;

import java.io.Serializable;
import java.util.ArrayList;
import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;

/**
 *
 * @author elvis_agui
 */
public class HiloReproductorDePista extends Thread implements Serializable {

    private Player player = new Player();
    private Pattern combinedPattern;

    @Override
    public void run() {
        this.ejecutarMusica();
    }

    public void ejecutarMusica() {
        if (combinedPattern != null) {
            player.play(combinedPattern);            
        }
    }

    public void prepararReproduccion(ArrayList<CanalMusical> canales) {
        this.combinedPattern = new Pattern();
        int index = 0;
        for (CanalMusical canale : canales) {
            Pattern patten = new Pattern("V" + index + " " + canale.getMusicString().toString());
            this.combinedPattern.add(patten);
            index++;
        }
    }

    public void detenerReproduccion() {
        player.getManagedPlayer().finish();
    }

    

    public Pattern getCombinedPattern() {
        return combinedPattern;
    }

    public void setCombinedPattern(Pattern combinedPattern) {
        this.combinedPattern = combinedPattern;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
}
