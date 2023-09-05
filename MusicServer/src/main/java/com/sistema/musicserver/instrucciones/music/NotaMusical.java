package com.sistema.musicserver.instrucciones.music;

/**
 *
 * @author elvis_agui
 */
public class NotaMusical {

    private int nota;
    private int ocatava;
    private int milisegundos;
    private int canal;

    public NotaMusical(int nota, int ocatava, int milisegundos, int canal) {
        this.nota = nota;
        this.ocatava = ocatava;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getOcatava() {
        return ocatava;
    }

    public void setOcatava(int ocatava) {
        this.ocatava = ocatava;
    }

    public int getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(int milisegundos) {
        this.milisegundos = milisegundos;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }
    
    
    
    
}
