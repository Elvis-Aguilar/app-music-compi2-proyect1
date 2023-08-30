package com.sistema.musicserver.tablaSimbol;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.Dato;
import com.sistema.musicserver.instrucciones.declaracionAsignacion.TipoDato;

import java.util.ArrayList;

public class TablaSimbol {

    private ArrayList<Token> ids = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<ErrorSemantico> erros;
    private TablaSimbol tablaSimbolHijo;
    private TablaSimbol tablaSimbolPadre;
    private boolean isFuncion;

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

    public void capturarIds(Token id) {
        boolean capturar = true;
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            for (Token tok : ids) {
                if (tok.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "variable ya declarada antes"));
                    capturar = false;
                    break;
                }
            }
            if (capturar && !this.varYaDeclarada(id)) {
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

    public boolean buscarRetorno(String nomVar) {
        boolean repti = false;
        if (isFuncion) {
            for (Variable variable : variables) {
                if (variable.getNombre().equals(nomVar)) {
                    repti = true;
                    break;
                }
            }
        }else{
            repti =this.tablaSimbolPadre.buscarRetorno(nomVar);
        }
        return repti;
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

    public TablaSimbol getTablaSimbolHijo() {
        return tablaSimbolHijo;
    }

    public void setTablaSimbolHijo(TablaSimbol tablaSimbolHijo) {
        this.tablaSimbolHijo = tablaSimbolHijo;
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

}
