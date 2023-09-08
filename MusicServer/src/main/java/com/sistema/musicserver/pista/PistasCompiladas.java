package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.archivos.ManejadorArchivos;
import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.instrucciones.music.PistaMusical;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * clase que represnta la lista de pistas compiladas, extraendo su arbol de
 * activacion, tabla de simbolos asi mismo servira para extender de ellas, esta
 * clase es la que hay que serializar y obtener cuando el programa sea
 * inicializado
 *
 * @author elvis_agui
 */
public class PistasCompiladas implements Serializable {

    private static PistasCompiladas instance;
    private ArrayList<Pista> pistas = new ArrayList<>();
    private ArrayList<Lista> listas = new ArrayList<>();

    public static PistasCompiladas getInstancePistasActivacion() {
        if (instance == null) {
            instance = new PistasCompiladas();
        }
        return instance;
    }

    /**
     * agrega la pista compilada a array de pista si y solo si no hay errores
     *
     * @param pista
     * @param errorsSemanticos
     */
    public void push(Pista pista, ArrayList<ErrorSemantico> errorsSemanticos) {
        if (errorsSemanticos.isEmpty()) {
            pushPista(pista);
            if (pista.getPistaMusical() != null) {
                if (listas.isEmpty()) {
                    Lista listGeneral = new Lista("----Pistas existentes----");
                    listGeneral.getListasMusicales().add(pista.getPistaMusical());
                    listas.add(listGeneral);
                } else {
                    listas.forEach(lista -> {
                        this.pushPistaEnLista(pista.getPistaMusical(), lista);
                    });
                }
            }
        }
    }

    private void pushPistaEnLista(PistaMusical pistaMus, Lista lista) {
        int index = -1;
        for (int i = 0; i < lista.getListasMusicales().size(); i++) {
            if (lista.getListasMusicales().get(0).getNombre().equals(pistaMus.getNombre())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            lista.getListasMusicales().set(index, pistaMus);
        } else if (lista.getNombre().equals("----Pistas existentes----")) {
            lista.getListasMusicales().add(pistaMus);
        }
    }

    private void pushPista(Pista pistaInser) {
        int index = -1;
        Pista pst = new Pista(pistaInser.getCodigoFuente(), pistaInser.getPistaMusical(), pistaInser.getFunciones(), pistaInser.getNombre(), pistaInser.getTableSimbolGoblal());
        for (int i = 0; i < this.pistas.size(); i++) {
            if (this.pistas.get(i).getNombre().equals(pistaInser.getNombre())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.pistas.set(index, pst);
        } else {
            this.pistas.add(pst);
        }

    }

    public boolean sobreEscribir(String nombre, ArrayList<ErrorSemantico> errorsSemanticos) {
        boolean sobreEscribir = true;
        if (errorsSemanticos.isEmpty()) {
            if (this.pistas.isEmpty()) {
                return sobreEscribir;
            }
            for (Pista pista : pistas) {
                if (pista.getNombre().equals(nombre)) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Pista existente, deseas sobrescribirla?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (respuesta) {
                        case JOptionPane.YES_OPTION:
                            sobreEscribir = true;
                            break;
                        case JOptionPane.NO_OPTION:
                            sobreEscribir = false;
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            sobreEscribir = false;
                            break;
                        default:
                            sobreEscribir = true;
                            break;
                    }
                    break;
                }
            }

        } else {
            sobreEscribir = false;
        }

        return sobreEscribir;
    }

    /**
     * funcion para buscar en base al nombre de la pista, la pista que extendera
     *
     * @param id
     * @return
     */
    public Pista getPistaExtends(Token id) {
        Pista pist = null;
        for (Pista pista : pistas) {
            if (pista.getNombre().equals(id.getLexeme())) {
                pist = pista;
                break;
            }
        }

        return pist;
    }

    public void guardarPistaALista(PistaMusical pista, String nameLista) {
        boolean guardado = false;
        for (Lista lista : listas) {
            if (lista.getNombre().equals(nameLista)) {
                guardado = true;
                for (PistaMusical listasMusicale : lista.getListasMusicales()) {
                    if (pista.getNombre().equals(listasMusicale.getNombre())) {
                        guardado = false;
                        break;
                    }
                }
                if (guardado) {
                    lista.getListasMusicales().add(pista);
                }
                break;
            }
        }
        if (guardado) {
            this.guardarEnBinario();
            JOptionPane.showMessageDialog(null, " Pista agregada con exito");
        } else {
            JOptionPane.showMessageDialog(null, " Pista ya existente en la Lista");

        }
    }

    public void guardarNuevaLista(String name) {
        boolean guardar = true;
        for (Lista lista : listas) {
            if (lista.getNombre().equals(name)) {
                guardar = false;
                JOptionPane.showMessageDialog(null, " Lista ya existente");
                break;
            }
        }
        if (guardar) {
            Lista lis = new Lista(name);
            this.listas.add(lis);
            JOptionPane.showMessageDialog(null, " Lista Creada con exito!!");

            this.guardarEnBinario();
        }
    }

    public void guardarEnBinario() {
        ManejadorArchivos archivos = new ManejadorArchivos();
        archivos.guardarListasPistas("Listas.bin", listas);
        archivos.guardarPistasActivacion("PistasActivacion.bin", pistas);
    }
    
    public void eliminarPistaLista(String nombre, int indexLista){
        for (Lista lista : listas) {
            if (lista.getNombre().equals(nombre)) {
                lista.getListasMusicales().remove(indexLista);
                break;
            }
        }
    }

    public ArrayList<Lista> getListas() {
        return listas;
    }

    public void setListas(ArrayList<Lista> listas) {
        this.listas = listas;
    }

    public ArrayList<Pista> getPistas() {
        return pistas;
    }

    public void setPistas(ArrayList<Pista> pistas) {
        this.pistas = pistas;
    }

}
