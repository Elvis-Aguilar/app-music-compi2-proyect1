package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.errors.ErroresSingleton;
import com.sistema.musicserver.instrucciones.music.PistaMusical;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author elvis_agui
 */
public class Lista implements Serializable {

    private ArrayList<PistaMusical> listasMusicales = new ArrayList<>();
    private String nombre;
    private ArrayList<Token> ids = new ArrayList<>();
    private boolean random = false;
    private boolean circular = false;

    public Lista() {
    }

    public Lista(String nombre) {
        this.nombre = nombre;
    }

    public Lista(ArrayList<PistaMusical> listasMusicales, String nombre) {
        this.listasMusicales.addAll(listasMusicales);
        this.nombre = nombre;
    }

    public void capturarIds(ArrayList<Token> ids) {
        this.ids.addAll(ids);

    }

    public void capturarLista() {
        if (!this.ids.isEmpty() && !comprobarIds()) {
            this.obtnerListaMusical();
            if (!listasMusicales.isEmpty() && random) {
                Collections.shuffle(this.listasMusicales);
            }
        }
    }
    
    public void clear(){
        this.ids.clear();
        this.listasMusicales.clear();
        this.nombre = "";
    }
    
    private void obtnerListaMusical(){
        for (Token id : ids) {
          PistaMusical pist = PistasCompiladas.getInstancePistasActivacion().getpista(id);
            if (pist != null) {
                this.listasMusicales.add(pist);
            }
        }
    }

    private boolean comprobarIds() {
        boolean reptido = false;
        HashSet<String> valoresVistos = new HashSet<>();
        for (Token id : ids) {
            if (valoresVistos.contains(id.getLexeme())) {
                reptido = true;
                ErroresSingleton.getInstance().getErroresSemanticos().add(new ErrorSemantico(id, "El nombre de la pista se encuentra repetido en la declaracion"));
                break;
            }
            valoresVistos.add(id.getLexeme());
        }

        return reptido;
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

    public ArrayList<Token> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Token> ids) {
        this.ids = ids;
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
