package com.sistema.musicserver.instrucciones.music;

import com.sistema.musicserver.errors.ErrorSemantico;
import java.io.Serializable;
import java.util.ArrayList;
import org.jfugue.pattern.Pattern;

/**
 *
 * @author elvis_agui
 */
public class ManejadorPistaMusical implements Serializable{

    private ArrayList<CanalMusical> canales = new ArrayList<>();
    private static ManejadorPistaMusical instance;

    public static ManejadorPistaMusical getPistaMusical() {
        if (instance == null) {
            instance = new ManejadorPistaMusical();
        }
        return instance;
    }

    public void pushCanal(String notaCalculad, int canal) {
        if (this.canales.isEmpty()) {
            CanalMusical nuevoCanal = new CanalMusical(canal, notaCalculad);
            this.canales.add(nuevoCanal);
        } else {
            boolean agregar = true;
            for (CanalMusical canale : canales) {
                if (canale.getCanal() == canal) {
                    canale.agregarNotas(notaCalculad);
                    agregar = false;
                    break;
                }
            }
            if (agregar) {
                CanalMusical nuevoCanal = new CanalMusical(canal, notaCalculad);
                this.canales.add(nuevoCanal);
            }
        }
    }

    public PistaMusical pistaMusic(String nombre, ArrayList<ErrorSemantico> errorsSemanticos) {
        PistaMusical pista = null;
        if (!errorsSemanticos.isEmpty() || this.canales.isEmpty()) {
            return pista;
        }
       
        
        pista = new PistaMusical(this.canales, nombre);
        this.canales.clear();
        return pista;
    }

    public ArrayList<CanalMusical> getCanales() {
        return canales;
    }

    public void setCanales(ArrayList<CanalMusical> canales) {
        this.canales = canales;
    }

    public ManejadorPistaMusical getInstance() {
        return instance;
    }

    public void setInstance(ManejadorPistaMusical instance) {
        ManejadorPistaMusical.instance = instance;
    }

}
