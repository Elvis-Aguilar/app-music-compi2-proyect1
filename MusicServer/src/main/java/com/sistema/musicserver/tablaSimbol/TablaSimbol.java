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

    public TablaSimbol(ArrayList<ErrorSemantico> erros) {
        this.erros = erros;
    }

    public void capturarVariablesGloblase(TipoDato tipo, String funcionPadre, boolean inicializado) {
        ids.forEach(id -> {
            Variable tmp = new Variable(id, tipo, funcionPadre, inicializado);
            variables.add(tmp);
        });
    }

    public void capturarIds(Token id) {
        if (this.ids.isEmpty()) {
            ids.add(id);
        } else {
            ArrayList<Token> tmp = new ArrayList<>();
            ids.forEach(id1 -> {
                if (id1.getLexeme().equals(id.getLexeme())) {
                    //error semantico, variable ya declarada antes
                    this.erros.add(new ErrorSemantico(id, "variable ya declarada antes"));
                } else {
                    tmp.add(id);
                }
            });
            tmp.forEach(tok -> {
                ids.add(tok);
            });
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

    public Dato getDato(Token token) {
        Dato tmp = new Dato(true, 0, TipoDato.ENTERO);
        boolean encontredo = false;
        for (Variable variable : variables) {
            System.out.println(variable.toString());
            if (variable.getNombre().equals(token.getLexeme())) {
                tmp = variable.getDato();
                encontredo = true;
                break;
            }
        }
        if (!encontredo) {
            this.erros.add(new ErrorSemantico(token, "Variable no declarada"));
        }
        return tmp;
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
}
