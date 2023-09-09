package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class Arreglo implements Serializable{

    private Token Token;
    private TipoDato tipoArreglo;
    private int dimension;
    private ArrayList<Integer> indexMaximos = new ArrayList<>();
    private ArrayList<Dato> datos = new ArrayList<>();
    private int size;

    public Arreglo(Token Token, TipoDato tipoArreglo, int dimension, ArrayList<Integer> indexMaximos) {
        this.Token = Token;
        this.tipoArreglo = tipoArreglo;
        this.dimension = dimension;
        this.indexMaximos = indexMaximos;
    }

    public Arreglo(Token Token, TipoDato tipoArreglo, int dimension, int indexMax, int maxIndiceFila) {
        this.Token = Token;
        this.tipoArreglo = tipoArreglo;
        this.dimension = dimension;
        this.inicializarIndicesMax(indexMax, maxIndiceFila);
    }

    public void recibirDato(Dato dato, ArrayList<ErrorSemantico> erros) {
        if (tipoArreglo != dato.getTipoDato()) {
            erros.add(new ErrorSemantico(Token, "El el tipo de dato no conside con el tipo del arreglo"));
        } else {
            this.datos.add(dato);
        }
    }

    public Dato getDatoEspecifico(ArrayList<ErrorSemantico> erros, ArrayList<Integer> indices) {
        Dato tmp = new Dato(false, 0, tipoArreglo);
        if (this.datos.isEmpty()) {
            this.inicializarArreglo();
            erros.add(new ErrorSemantico(Token, "El arreglo esta vacio, null pointerExpetion xd"));
        } else {
            if (indices.size() == 1) {
                tmp = this.datos.get(indices.get(0));
            } else {
                int indece = this.calculoIndice(indices);
                tmp = this.datos.get(indece);
                if (!tmp.isInicializado()) {
                    erros.add(new ErrorSemantico(Token, "El arreglo en el indice a acceder esta vacio o null, null pointerExpetion xd"));
                }
            }
        }
        return tmp;
    }

    /**
     * funcion encargada de inicializar los indices de este arreglo
     * [indexMax][indexMax]... solo cuando el array ya inicializado por lo tanto
     * todos los indices son iguales
     *
     * @param indexMax
     */
    private void inicializarIndicesMax(int indexMax, int maxIndiceFila) {
        this.indexMaximos.add(maxIndiceFila - 1);
        this.indexMaximos.add(indexMax - 1);

    }

    public boolean indicesValido(ArrayList<Integer> indices, ArrayList<ErrorSemantico> erros, Token id) {
        boolean valid = true;
        if (indices.size() == this.indexMaximos.size()) {
            for (int i = 0; i < indices.size(); i++) {
                if (indices.get(i) > this.indexMaximos.get(i)) {
                    erros.add(new ErrorSemantico(id, "Indice excede el tamaño del arreglo"));
                    valid = false;
                    break;
                }
            }
        } else {
            erros.add(new ErrorSemantico(id, "Excede la dimension del arreglo que intenta acceder"));
            valid = false;
        }
        return valid;
    }

    public void capturarValor(Dato dato, Token id, ArrayList<Integer> indices, ArrayList<ErrorSemantico> erros) {
        if (this.datos.isEmpty()) {
            this.inicializarArreglo();
        }
        if (dato.getTipoDato() == this.tipoArreglo) {
            if (indices.size() == 1) {
                this.datos.set(indices.get(0), dato);
            } else {
                int ind = calculoIndice(indices);
                this.datos.set(ind, dato);
            }
        } else {
            erros.add(new ErrorSemantico(id, "El tipo de dato que intentas asignar no conside con el tipo del Arreglo"));
        }

    }

    private int calculoIndice(ArrayList<Integer> indices) {
        int res = 0;
        for (int i = 1; i < this.indexMaximos.size(); i++) {
            res = indices.get(i - 1) * (this.indexMaximos.get(i) + 1) + indices.get(i);
            indices.set(i, res);
        }
        return res;
    }

    private void inicializarArreglo() {
        size = 1;
        for (int i = 0; i < this.indexMaximos.size(); i++) {
            size = size * (this.indexMaximos.get(i) + 1);
        }
        for (int i = 0; i < size; i++) {
            Dato tmp = new Dato(false, tipoArreglo);
            this.datos.add(tmp);
        }
    }

    public Token getToken() {
        return Token;
    }

    public void setToken(Token Token) {
        this.Token = Token;
    }

    public TipoDato getTipoArreglo() {
        return tipoArreglo;
    }

    public void setTipoArreglo(TipoDato tipoArreglo) {
        this.tipoArreglo = tipoArreglo;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public ArrayList<Integer> getIndexMaximos() {
        return indexMaximos;
    }

    public void setIndexMaximos(ArrayList<Integer> indexMaximos) {
        this.indexMaximos = indexMaximos;
    }

    public ArrayList<Dato> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Dato> datos) {
        this.datos = datos;
        this.size = this.datos.size();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Arreglo{" + "Token=" + Token + ", tipoArreglo=" + tipoArreglo + ", dimension=" + dimension + ", indexMaximos=" + indexMaximos + ", datos=" + datos + '}';
    }

}
