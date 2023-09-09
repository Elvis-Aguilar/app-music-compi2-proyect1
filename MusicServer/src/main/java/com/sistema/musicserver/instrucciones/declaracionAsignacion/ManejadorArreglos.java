package com.sistema.musicserver.instrucciones.declaracionAsignacion;

import com.sistema.musicserver.analizadores.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.Instruccions;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class ManejadorArreglos extends Instruccions implements Serializable {

    private TablaSimbol tablaSimbol;
    private ArrayList<Operation> operaciones;
    private boolean esDeclaracionGlobla = false;
    private ArrayList<Token> ids;
    private TipoDato tipoArreglo;
    private boolean inizializado;
    private int dimensionCreacion;
    private int dimensionAsignada;
    private ArrayList<ErrorSemantico> errorsSemanticos;
    private int indexI;
    private int indexF;
    private int indexMax;

    public ManejadorArreglos(int indexMax, ArrayList<Operation> operaciones, ArrayList<Token> ids, TipoDato tipoArreglo, int dimensionCreacion, int dimensionAsignada, ArrayList<ErrorSemantico> errorsSemanticos) {
        this.operaciones = operaciones;
        this.recolectarIDs(ids);
        this.tipoArreglo = tipoArreglo;
        this.dimensionCreacion = dimensionCreacion;
        this.dimensionAsignada = dimensionAsignada;
        this.errorsSemanticos = errorsSemanticos;
        this.indexMax = indexMax;
        this.inizializado = true;
    }

    public ManejadorArreglos(TipoDato tipoArreglo, TablaSimbol tablaSimbol, ArrayList<Operation> operaciones, ArrayList<Token> ids) {
        this.tablaSimbol = tablaSimbol;
        this.operaciones = operaciones;
        this.recolectarIDs(ids);
        this.esDeclaracionGlobla = true;
        this.tipoArreglo = tipoArreglo;
    }

    @Override
    public void execute(ArrayList<ErrorSemantico> errorsSemanticos) {
        if (esDeclaracionGlobla) {
            //realizar las operacions que definen el tamanio del arreglo [operacion1][operacion2]...
            this.ids.forEach(id -> {
                this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, operaciones.size(), this.indexMaximos(errorsSemanticos)));
            });
        } else {
            if (inizializado) {
                for (int i = indexI; i < indexF; i++) {
                    ArrayList<Dato> datos = this.datosAInsertar();
                    tablaSimbol.getArreglos().get(i).setDatos(datos);
                }
            } else {
                this.ids.forEach(id -> {
                    this.tablaSimbol.arregloYaDeclarado(id);
                    this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, operaciones.size(), this.indexMaximos(errorsSemanticos)));
                });
            }
        }

    }

    @Override
    public void actionReferenciarTabla(TablaSimbol tabla) {
        this.tablaSimbol = tabla;
        if (inizializado) {
            if (this.dimensionAsignada != this.dimensionCreacion) {
                this.errorsSemanticos.add(new ErrorSemantico(ids.get(0), "Erro en la Creacion del Arreglo, la inicializacion del arreglo no cumple con las dimensiones del mismo"));
            }
            this.indexI = this.tablaSimbol.getArreglos().size();
            this.indexF = this.indexI + this.ids.size();
            this.ids.forEach(id -> {
                this.tablaSimbol.arregloYaDeclarado(id);
                this.tablaSimbol.getArreglos().add(new Arreglo(id, tipoArreglo, this.dimensionCreacion, indexMax,this.dimensionAsignada));
            });
        }

    }

    private ArrayList<Dato> datosAInsertar() {
        ArrayList<Dato> datos = new ArrayList<>();
        this.operaciones.forEach(op -> {
            Dato tmp = op.execute(errorsSemanticos, tablaSimbol);
            if (tmp.getTipoDato() != this.tipoArreglo) {
                this.errorsSemanticos.add(new ErrorSemantico(ids.get(0), "El dato a insertar en la inicilizacion del arreglo no es compatible con el tipo"));
            }
            datos.add(tmp);
        });
        return datos;
    }

    private ArrayList<Integer> indexMaximos(ArrayList<ErrorSemantico> errorsSemanticos) {
        ArrayList<Integer> indexMaximos = new ArrayList<>();
        this.operaciones.forEach(oper -> {
            Dato dat = oper.execute(errorsSemanticos, tablaSimbol);
            if (TipoDato.ENTERO != dat.getTipoDato()) {
                errorsSemanticos.add(new ErrorSemantico(dat.getToken(), "Los valores que definen el tamaño del arreglo deben ser tipo Entero [entero]"));
                indexMaximos.add(1);
            } else {
                if (dat.getNumero() <= 0) {
                    errorsSemanticos.add(new ErrorSemantico(dat.getToken(), "Los valores que definen el tamaño del arreglo deben ser mayores a 0"));
                    indexMaximos.add(1);
                } else {
                    indexMaximos.add(dat.getNumero() - 1);
                }
            }

        });
        return indexMaximos;
    }

    private void recolectarIDs(ArrayList<Token> ids) {
        this.ids = new ArrayList<>();
        ids.forEach(id -> {
            this.ids.add(id);
        });
    }

    public TablaSimbol getTablaSimbol() {
        return tablaSimbol;
    }

    public void setTablaSimbol(TablaSimbol tablaSimbol) {
        this.tablaSimbol = tablaSimbol;
    }

    public ArrayList<Operation> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operation> operaciones) {
        this.operaciones = operaciones;
    }

    public boolean isEsDeclaracionGlobla() {
        return esDeclaracionGlobla;
    }

    public void setEsDeclaracionGlobla(boolean esDeclaracionGlobla) {
        this.esDeclaracionGlobla = esDeclaracionGlobla;
    }

    public ArrayList<Token> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Token> ids) {
        this.ids = ids;
    }

    public TipoDato getTipoArreglo() {
        return tipoArreglo;
    }

    public void setTipoArreglo(TipoDato tipoArreglo) {
        this.tipoArreglo = tipoArreglo;
    }

    public boolean isInizializado() {
        return inizializado;
    }

    public void setInizializado(boolean inizializado) {
        this.inizializado = inizializado;
    }

    public int getDimensionCreacion() {
        return dimensionCreacion;
    }

    public void setDimensionCreacion(int dimensionCreacion) {
        this.dimensionCreacion = dimensionCreacion;
    }

    public int getDimensionAsignada() {
        return dimensionAsignada;
    }

    public void setDimensionAsignada(int dimensionAsignada) {
        this.dimensionAsignada = dimensionAsignada;
    }

}
