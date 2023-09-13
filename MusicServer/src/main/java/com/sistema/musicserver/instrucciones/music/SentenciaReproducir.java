package com.sistema.musicserver.instrucciones.music;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Operation;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class SentenciaReproducir extends Instruccions implements Serializable{

    private String Nota;
    private Operation octava;
    private Operation milisegundos;
    private Operation canal;
    private TablaSimbol TablaSimbol;
    private String notasCalculadas = "";
    private boolean isEsperar = false;
    private int milis = 0;

    public SentenciaReproducir(String Nota, Operation octava, Operation milisegundos, Operation canal) {
        this.Nota = Nota;
        this.octava = octava;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    public SentenciaReproducir(String Nota, Operation milisegundos, Operation canal) {
        this.Nota = Nota;
        this.isEsperar = true;
        this.milisegundos = milisegundos;
        this.canal = canal;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        int canalCalculado = ejecutarValorCanal(errorsSemanticos);
        milis = ejecutarValorMilis(errorsSemanticos);
        this.agregarNota(Nota);
        if (!isEsperar) {
            int octavaCalculada = ejecutarValorOctava(errorsSemanticos);
            this.agregarOtava(octavaCalculada);
        }
        this.agregarMilis(milis);
        ManejadorPistaMusical.getPistaMusical().pushCanal(notasCalculadas, canalCalculado, milis);
        this.notasCalculadas = "";
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
        if (tmpOctava.getNumero() < 0 && tmpOctava.getNumero() > 8) {
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
        if (tmpOctava.getNumero() < 0) {
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
        if (tmpOctava.getNumero() < 0) {
            errorsSemanticos.add(new ErrorSemantico(new Token("Milis", 0, 0), "La sentencia Reproducir en el parametro Milis no acepta valores menos a 0"));
            return resultado;
        }
        resultado = tmpOctava.getNumero();
        return resultado;
    }

    private void agregarNota(String nota) {
        switch (nota) {
            case "Do":
                this.notasCalculadas += "C";
                break;
            case "Do#":
                this.notasCalculadas += "C#";
                break;
            case "Re":
                this.notasCalculadas += "D";
                break;
            case "Re#":
                this.notasCalculadas += "D#";
                break;
            case "Mi":
                this.notasCalculadas += "E";
                break;
            case "Fa":
                this.notasCalculadas += "F";
                break;
            case "Fa#":
                this.notasCalculadas += "F#";
                break;
            case "Sol":
                this.notasCalculadas += "G";
                break;
            case "Sol#":
                this.notasCalculadas += "G#";
                break;
            case "La":
                this.notasCalculadas += "A";
                break;
            case "La#":
                this.notasCalculadas += "A#";
                break;
            case "Si":
                this.notasCalculadas += "B";
                break;
            case "Z":
                this.notasCalculadas += "R";
                break;
            default:
                this.notasCalculadas += "C";
                break;
        }
    }

    private void agregarOtava(int octava) {
        this.notasCalculadas += "" + octava;
    }

    private void agregarMilis(int milis) {
        if (milis <= 4000) {
            if (milis <= 500) {
                this.notasCalculadas += "q";
            } else if (milis <= 1000) {
                this.notasCalculadas += "h";
            } else if (milis <= 2000) {
                this.notasCalculadas += "h";
            } else if (milis <= 3000) {
                String tmp = this.notasCalculadas;
                this.notasCalculadas += "h ";
                this.notasCalculadas += tmp + "h";
            } else if (milis <= 4000) {
                this.notasCalculadas += "w";
            }
        } else {
            String tmp = this.notasCalculadas;
            milis = milis - 4000;
            this.notasCalculadas += "w ";
            while (milis > 0) {
                if (milis <= 500) {
                    this.notasCalculadas += tmp + "i ";
                } else if (milis <= 1000) {
                    this.notasCalculadas += tmp + "q ";
                } else if (milis <= 2000) {
                    this.notasCalculadas += tmp + "h ";
                } else if (milis <= 3000) {
                    this.notasCalculadas += tmp + "h ";
                } else if (milis >= 4000) {
                    this.notasCalculadas += tmp + "w ";
                }
                milis = milis - 4000;
            }

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

    public String getNotasCalculadas() {
        return notasCalculadas;
    }

    public void setNotasCalculadas(String notasCalculadas) {
        this.notasCalculadas = notasCalculadas;
    }

    public int getMilis() {
        return milis;
    }

    public void setMilis(int milis) {
        this.milis = milis;
    }
    
    

}
