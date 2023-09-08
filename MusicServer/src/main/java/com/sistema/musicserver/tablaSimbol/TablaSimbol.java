package com.sistema.musicserver.tablaSimbol;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Arreglo;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;
import java.io.Serializable;

import java.util.ArrayList;

public class TablaSimbol implements Serializable{

    private ArrayList<Token> ids = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<ErrorSemantico> erros;
    private TablaSimbol tablaSimbolPadre;
    private boolean isFuncion;
    private ArrayList<Arreglo> arreglos = new ArrayList<>();

    public TablaSimbol(ArrayList<ErrorSemantico> erros) {
        this.erros = erros;
        this.isFuncion = false;
    }

    public TablaSimbol(ArrayList<ErrorSemantico> erros, boolean isFuncion) {
        this.isFuncion = isFuncion;
        this.erros = erros;
    }

    public void capturarVariablesGloblase(TipoDato tipo, String funcionPadre, boolean inicializado) {
        ids.forEach(id -> {
            Variable tmp = new Variable(id, tipo, funcionPadre, inicializado);
            variables.add(tmp);
        });
    }

    public void capturarArreglos(TipoDato tipo, int dimensionesDecla, int maxIndice, int maxIndiceFila) {
        ids.forEach(id -> {
            Arreglo arreglo = new Arreglo(id, tipo, dimensionesDecla, maxIndice, maxIndiceFila);
            this.arreglos.add(arreglo);
        });
    }

    public void capturarIds(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "Se esta declarando dos veces la misma variabla"));
                    capturar = false;
                    break;
                }
            }
            if (capturar && !this.varYaDeclarada(id)) {
                this.ids.add(id);
            }

        }

    }

    public void capturarIdsFun(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "Se esta declarando dos veces la misma variabla"));
                    capturar = false;
                    break;
                }
            }
            if (capturar) {
                this.ids.add(id);
            }

        }

    }

    /*funcion para corroborar los arrglos*/
    public void capturarIdsArr(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "Se esta declarando dos veces el mismo arreglo"));
                    capturar = false;
                    break;
                }
            }
            if (capturar && !this.arregloYaDeclarado(id)) {
                this.ids.add(id);
            }

        }

    }

    public int varExiste(Token token) {
        int index = -1;
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).getNombre().equals(token.getLexeme())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            this.erros.add(new ErrorSemantico(token, "Variable no declarada"));
        }
        return index;
    }

    /**
     * funcion para obtener el dato en la tabla de simbolos, esto para cuando se
     * resquiera el dato de cierta variable en la tabla de simbolos
     *
     * @param token
     * @param nomVar
     * @return
     */
    public Dato getDato(Token token, String nomVar) {
        Dato tmp = new Dato(true, 0, TipoDato.ENTERO);
        boolean encontredo = false;
        if (!nomVar.equals("")) {
            for (Variable variable : variables) {
                if (variable.getNombre().equals(nomVar)) {
                    tmp = variable.getDato();
                    encontredo = true;
                    break;
                }
            }
        }
        if (!encontredo) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(token, "Variable no declarada"));
            } else {
                tmp = this.tablaSimbolPadre.getDato(token, nomVar);
            }
        }
        return tmp;
    }

    /**
     * esta funcion insertara el dato a la variable anidada en sentencias,
     * buscar la variable desde su taba actual hata la tabla padre de la misma y
     * reportara el error si fuese necesario
     *
     * @param dato
     * @param id
     */
    public void asignacionValorVariable(Dato dato, Token id) {
        boolean encontrado = false;
        for (Variable variable : variables) {
            if (variable.getNombre().equals(id.getLexeme())) {
                variable.setDato(dato, erros);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(id, "Variable no declarada"));
            } else {
                this.tablaSimbolPadre.asignacionValorVariable(dato, id);
            }
        }
    }

    public void asignacionValorArray(Dato dato, Token id, int dimension, ArrayList<Integer> indices) {
        boolean encontrado = false;
        for (Arreglo arreglo : arreglos) {
            if (arreglo.getToken().getLexeme().equals(id.getLexeme())) {
                if (dimension != arreglo.getDimension()) {
                    this.erros.add(new ErrorSemantico(id, "La Dimension del arreglo no conside, puede que tenga mas [] o menos[]"));
                } else {
                    if (arreglo.indicesValido(indices, erros, id)) {
                        arreglo.capturarValor(dato, id, indices, this.erros);
                    }
                }
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(id, "Variable no declarada"));
            } else {
                this.tablaSimbolPadre.asignacionValorArray(dato, id, dimension, indices);
            }
        }
    }

    /**
     * funcion que verifica si esta variable ya existe en la tabla de simbolos
     *
     * @param id
     * @return
     */
    public boolean varYaDeclarada(Token id) {
        boolean repti = false;
        for (Variable variable : variables) {
            if (variable.getNombre().equals(id.getLexeme())) {
                repti = true;
                this.erros.add(new ErrorSemantico(id, " variable ya declarada antes"));
                break;
            }
        }
        if (!repti && null != this.tablaSimbolPadre && !this.isFuncion) {
            repti = this.tablaSimbolPadre.varYaDeclarada(id);
        }
        return repti;
    }

    public boolean arregloYaDeclarado(Token id) {
        boolean repti = false;
        for (Arreglo arreglo : arreglos) {
            if (arreglo.getToken().getLexeme().equals(id.getLexeme())) {
                repti = true;
                this.erros.add(new ErrorSemantico(id, " Arreglo ya declarada antes"));
                break;
            }
        }
        if (!repti && null != this.tablaSimbolPadre && !this.isFuncion) {
            repti = this.tablaSimbolPadre.arregloYaDeclarado(id);
        }
        return repti;
    }

    public boolean buscarRetorno(String nomVar) {
        boolean repti = false;
        if (isFuncion) {
            for (Variable variable : variables) {
                if (variable.getNombre().equals(nomVar)) {
                    repti = true;
                    break;
                }
            }
        } else {
            repti = this.tablaSimbolPadre.buscarRetorno(nomVar);
        }
        return repti;
    }

    public Dato getDatoArreglo(Dato dato) {
        Dato tmp = new Dato(true, 0, TipoDato.ENTERO);
        boolean encontredo = false;
        if (null != dato.getToken()) {
            for (Arreglo arreglo : arreglos) {
                if (arreglo.getToken().getLexeme().equals(dato.getToken().getLexeme())) {
                    ArrayList<Integer> indeces = dato.getIndices(erros, this);
                    if (arreglo.indicesValido(indeces, erros, dato.getToken())) {
                        tmp = arreglo.getDatoEspecifico(erros, indeces);
                    }
                    encontredo = true;
                    break;
                }
            }
        }
        if (!encontredo) {
            if (null == this.tablaSimbolPadre) {
                //reportar error
                this.erros.add(new ErrorSemantico(dato.getToken(), "Variable no declarada"));
            } else {
                tmp = this.tablaSimbolPadre.getDatoArreglo(dato);
            }
        }
        return tmp;
    }

    public void extenderDeOtraTabla(ArrayList<Variable> variables, ArrayList<Arreglo> arreglos) {
        this.variables.addAll(variables);
        this.arreglos.addAll(arreglos);

    }

    /*espacio para getters y setters*/
    public ArrayList<Token> getIds() {
        return ids;
    }

    public ArrayList<ErrorSemantico> getErros() {
        return erros;
    }

    public void setErros(ArrayList<ErrorSemantico> erros) {
        this.erros = erros;
    }

    public void setIds(ArrayList<Token> ids) {
        this.ids = ids;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public TablaSimbol getTablaSimbolPadre() {
        return tablaSimbolPadre;
    }

    public void setTablaSimbolPadre(TablaSimbol tablaSimbolPadre) {
        this.tablaSimbolPadre = tablaSimbolPadre;
    }

    public boolean isIsFuncion() {
        return isFuncion;
    }

    public void setIsFuncion(boolean isFuncion) {
        this.isFuncion = isFuncion;
    }

    public ArrayList<Arreglo> getArreglos() {
        return arreglos;
    }

    public void setArreglos(ArrayList<Arreglo> arreglos) {
        this.arreglos = arreglos;
    }

}
