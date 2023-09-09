package com.sistema.musicserver.tablaSimbol;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import java.io.Serializable;
import java.util.ArrayList;

public class Variable implements Serializable {

    private String nombre;
    private TipoDato tipo;
    private String funcionPadre = "";
    private boolean inicializado = false;
    private Token token;
    private Dato dato;

    public Variable(Token token, TipoDato tipo, String funcionPadre, boolean inicializado) {
        this.token = token;
        this.nombre = token.getLexeme();
        this.tipo = tipo;
        this.funcionPadre = funcionPadre;
        this.inicializado = inicializado;
        this.dato = new Dato(inicializado, token, tipo);
    }

    public Variable(Token token, TipoDato tipo, String nombre) {
        this.token = token;
        this.nombre = nombre;
        this.tipo = tipo;
        this.inicializado = false;
        this.dato = new Dato(inicializado, token, tipo);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public String getFuncionPadre() {
        return funcionPadre;
    }

    public void setFuncionPadre(String funcionPadre) {
        this.funcionPadre = funcionPadre;
    }

    public Dato getDato() {
        return dato;
    }

    public void setDato(Dato dato, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.dato.setInicializado(true);
        this.casteoAsignacion(dato, errorsSemanticos);
    }

    /**
     * funcion que representa la asignacion y conversion de el dato de variable
     * ejemplo String algo = 1; este se 1 se castea a un string "1" o puede
     * marcarse error
     *
     * @param dato
     * @param errorsSemanticos
     * @return
     */
    private void casteoAsignacion(Dato datoNuevo, ArrayList<ErrorSemantico> errorsSemanticos) {
        switch (this.dato.getTipoDato()) {
            case CADENA:
                this.cadenaAsigOtroDato(datoNuevo);
                break;
            case BOOLEAN:
                this.booleanAsigOtroDato(datoNuevo, errorsSemanticos);
                break;
            case CHAR:
                this.caracterAsigOtroDato(datoNuevo, errorsSemanticos);
                break;
            case DECIMAL:
                this.doubleAsigOtroDato(datoNuevo, errorsSemanticos);
                break;
            default:
                this.enteroAsigOtroDato(datoNuevo, errorsSemanticos);
                break;
        }

    }

    private void cadenaAsigOtroDato(Dato datoNuevo) {
        String cade = "";
        switch (datoNuevo.getTipoDato()) {
            case CADENA:
                this.dato.setCadena(datoNuevo.getCadena());
                break;
            case BOOLEAN:
                cade = (datoNuevo.isBooleano()) ? "true" : "false";
                this.dato.setCadena(cade);
                break;
            case CHAR:
                cade = datoNuevo.getCaracter() + "";
                this.dato.setCadena(cade);
                break;
            case DECIMAL:
                cade = datoNuevo.getDecimal() + "";
                this.dato.setCadena(cade);
                break;
            default:
                cade = datoNuevo.getNombreVar() + "";
                this.dato.setCadena(cade);
                break;
        }

    }

    private void enteroAsigOtroDato(Dato datoNuevo, ArrayList<ErrorSemantico> errorsSemanticos) {
        int num = 0;
        switch (datoNuevo.getTipoDato()) {
            case CADENA:
                errorsSemanticos.add(new ErrorSemantico(datoNuevo.getToken(), "Las variables Entero no acepta datos del tipo Cadena"));
                break;
            case BOOLEAN:
                num = (datoNuevo.isBooleano()) ? 1 : 2;
                this.dato.setNumero(num);
                break;
            case CHAR:
                num = (int) datoNuevo.getCaracter();
                this.dato.setNumero(num);
                break;
            case DECIMAL:
                num = (int) datoNuevo.getDecimal();
                this.dato.setNumero(num);
                break;
            default:
                this.dato.setNumero(datoNuevo.getNumero());
                break;
        }

    }

    private void caracterAsigOtroDato(Dato datoNuevo, ArrayList<ErrorSemantico> errorsSemanticos) {
        char carcter = 'a';
        switch (datoNuevo.getTipoDato()) {
            case CHAR:
                this.dato.setCaracter(datoNuevo.getCaracter());
                break;
            case ENTERO:
                carcter = (char) datoNuevo.getNumero();
                this.dato.setCaracter(carcter);
                break;
            default:
                errorsSemanticos.add(new ErrorSemantico(datoNuevo.getToken(), "Las variables tipo Char no aceptan otro tipo de dato que no sea char o entero"));
                break;
        }

    }

    private void doubleAsigOtroDato(Dato datoNuevo, ArrayList<ErrorSemantico> errorsSemanticos) {
        switch (datoNuevo.getTipoDato()) {
            case DECIMAL:
                this.dato.setDecimal(datoNuevo.getDecimal());
                break;
            case ENTERO:
                this.dato.setDecimal(datoNuevo.getNumero());
                break;
            default:
                errorsSemanticos.add(new ErrorSemantico(datoNuevo.getToken(), "Las variables tipo Decimal no aceptan otro tipo de dato que no sea decimal o entero"));
                break;
        }

    }

    private void booleanAsigOtroDato(Dato datoNuevo, ArrayList<ErrorSemantico> errorsSemanticos) {
        if (datoNuevo.getTipoDato() == TipoDato.BOOLEAN) {
            this.dato.setBooleano(datoNuevo.isBooleano());
        } else {
            if (datoNuevo.getTipoDato() == TipoDato.ENTERO && (datoNuevo.getNumero() == 1 || datoNuevo.getNumero() == 0)) {
                this.dato.setBooleano(datoNuevo.getNumero() == 1 );
            } else {
                errorsSemanticos.add(new ErrorSemantico(datoNuevo.getToken(), "Las variables booleanas no aceptan otro tipo de dato que no sea booleano"));

            }
        }

    }

    @Override
    public String toString() {
        return "Variable{" + "nombre=" + nombre + ", tipo=" + tipo + ", funcionPadre=" + funcionPadre + dato.toString() + ", inicializado=" + inicializado + '}';
    }

}
