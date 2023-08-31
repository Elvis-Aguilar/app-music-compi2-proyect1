package com.sistema.musicserver.instrucciones.funciones;

import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class FunMensaje{

    private static FunMensaje instance;
    private ArrayList<String> mensajes = new ArrayList<>();

    public static FunMensaje getInstanceMensajes() {
        if (instance == null) {
            instance = new FunMensaje();
        }
        return instance;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

    public void push(Dato dato) {
        switch (dato.getTipoDato()) {
            case ENTERO:
                this.mensajes.add("" + dato.getNumero());
                break;
            case DECIMAL:
                this.mensajes.add("" + dato.getDecimal());
                break;
            case CADENA:
                this.mensajes.add("" + dato.getCadena());
                break;
            case CHAR:
                this.mensajes.add("" + dato.getCaracter());
                break;
            case BOOLEAN:
                this.mensajes.add("" + dato.isBooleano());
                break;

        }

    }

}
