package com.sistema.musicserver.UI;

import com.sistema.musicserver.instrucciones.music.PistaMusical;
import com.sistema.musicserver.pista.Lista;
import com.sistema.musicserver.pista.PistasCompiladas;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author elvis_agui
 */
public class ManejadorListas {

    public void mostrarListasJlist(JList<String> jlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        ArrayList<Lista> lista = PistasCompiladas.getInstancePistasActivacion().getListas();
        lista.forEach(lsi -> {
            listModel.addElement(lsi.getNombre());
        });
    }

    public void mostrarPistasExistentes(JList<String> jlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        if (!PistasCompiladas.getInstancePistasActivacion().getListas().isEmpty()) {
            Lista lista = PistasCompiladas.getInstancePistasActivacion().getListas().get(0);
            lista.getListasMusicales().forEach(lsi -> {
                listModel.addElement(lsi.getNombre());
            });
        }
    }

    public Lista mostrarPistasDeLaLista(JList<String> jlist, int index) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        Lista lista = PistasCompiladas.getInstancePistasActivacion().getListas().get(index);
        lista.getListasMusicales().forEach(lsi -> {
            listModel.addElement(String.format("%-15s %-15s %15s",
                    lsi.getNombre(),
                    "seg: " + convertirSegundos(lsi.getMilisTotal()),
                    "milis: " + lsi.getMilisTotal()));
        });
        return lista;
    }

    public Lista mostrarPistasDeLaLista(JList<String> jlist, Lista lista) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
        lista.getListasMusicales().forEach(lsi -> {
            listModel.addElement(lsi.getNombre());
        });
        return lista;
    }

    private double convertirSegundos(int milis) {
        double val = milis / 1000;
        return (double) (Math.round(val * 100.00) / 100.00);
    }

    public void limpiarJlist(JList<String> jlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jlist.setModel(listModel);
    }

}
