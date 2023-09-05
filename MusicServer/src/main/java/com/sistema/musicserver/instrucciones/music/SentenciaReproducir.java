package com.sistema.musicserver.instrucciones.music;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaReproducir extends Instruccions {

    private String Nota;
    private Operation octava;
    private Operation milisegundos;
    private Operation canal;
    private TablaSimbol TablaSimbol;

    public SentenciaReproducir(String Nota, Operation octava, Operation milisegundos, Operation canal) {
        this.Nota = Nota;
        this.octava = octava;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        int octavaCalculada = ejecutarValorOctava(errorsSemanticos);
        int canalCalculado = ejecutarValorCanal(errorsSemanticos);
        int milis = ejecutarValorMilis(errorsSemanticos);
        int nota = convertidorNOTA(Nota, octavaCalculada);
        NotaMusical notaMusical = new NotaMusical(nota, octavaCalculada, milis, canalCalculado);

    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.TablaSimbol = tabla;
    }

    private int ejecutarValorOctava(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Dato tmpOctava = this.octava.execute(errorsSemanticos, TablaSimbol);
        if (tmpOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Octava", 0, 0), "La sentencia Reproducir en el parametro octava no acepta valors no numericos enteros"));
            return resultado;
        }
        if (tmpOctava.getNumero()<0 && tmpOctava.getNumero()>8) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Octava", 0, 0), "La sentencia Reproducir en el parametro octava no acepta valores menos a 0 o mayores a 8"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }
    
    private int ejecutarValorCanal(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Dato tmpOctava = this.canal.execute(errorsSemanticos, TablaSimbol);
        if (tmpOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Canal", 0, 0), "La sentencia Reproducir en el parametro Canal no acepta valors no numericos enteros"));
            return resultado;
        }
        if (tmpOctava.getNumero()<0) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Canal", 0, 0), "La sentencia Reproducir en el parametro Canal no acepta valores menos a 0"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }
    
    private int ejecutarValorMilis(ArrayList<ErrorSemantico> errorsSemanticos) {
        int resultado = 1;

        Dato tmpOctava = this.milisegundos.execute(errorsSemanticos, TablaSimbol);
        if (tmpOctava.getTipoDato() != TipoDato.ENTERO) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Milis", 0, 0), "La sentencia Reproducir en el parametro Milis no acepta valors no numericos enteros"));
            return resultado;
        }
        if (tmpOctava.getNumero()<0) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Milis", 0, 0), "La sentencia Reproducir en el parametro Milis no acepta valores menos a 0"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }
    
     private int convertidorNOTA(String nota,int ocatava){
          // Mapeo de nombres de notas a valores MIDI
        String[] noteNames = {"Do", "Do#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si"};
        
        int baseMIDIValue = 60; // Valor MIDI para la nota C4 (do central)
        
        // Encuentra la posición de la nota en el arreglo
        int notePosition = -1;
        for (int i = 0; i < noteNames.length; i++) {
            if (noteNames[i].equalsIgnoreCase(nota)) {
                notePosition = i;
                break;
            }
        }
        
        if (notePosition != -1) {
            // Calcula el valor MIDI sumando la posición de la nota al valor base y ajusta por la octava
            int midiValue = baseMIDIValue + notePosition + (ocatava - 4) * 12;
            return midiValue;
        } else {
            // Si el nombre de la nota no se encuentra, retorna -1 para indicar un error
            return -1;
        }
        
        
    }
    

    /*getters and Setters*/
    public String getNota() {
        return Nota;
    }

    public void setNota(String Nota) {
        this.Nota = Nota;
    }

    public Operation getOctava() {
        return octava;
    }

    public void setOctava(Operation octava) {
        this.octava = octava;
    }

    public Operation getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(Operation milisegundos) {
        this.milisegundos = milisegundos;
    }

    public Operation getCanal() {
        return canal;
    }

    public void setCanal(Operation canal) {
        this.canal = canal;
    }

    public TablaSimbol getTablaSimbol() {
        return TablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol TablaSimbol) {
        this.TablaSimbol = TablaSimbol;
    }

}
