
package com.sistema.musicserver;

import com.sistema.musicserver.UI.VentanaPrin;
import com.sistema.musicserver.UI.VentanaPrincipal;
import com.sistema.musicserver.archivos.ManejadorArchivos;
import com.sistema.musicserver.conexion.SocketServidor;
import com.sistema.musicserver.pista.Lista;
import com.sistema.musicserver.pista.Pista;
import com.sistema.musicserver.pista.PistasCompiladas;
import java.util.ArrayList;

/**
 *
 * @author elvis_agui
 */
public class Main {
    public static void main(String[] args) {
        ManejadorArchivos archivos = new ManejadorArchivos();
        ArrayList<Pista> pistas = archivos.readPistasActivacion("PistasActivacion.bin");
        ArrayList<Lista> listas = archivos.readListas("Listas.bin");
        PistasCompiladas.getInstancePistasActivacion().setPistas(pistas);
        PistasCompiladas.getInstancePistasActivacion().setListas(listas);
        VentanaPrincipal vtn = new VentanaPrincipal();
        vtn.setVisible(true);
        SocketServidor conexionSoc = new SocketServidor();
        conexionSoc.setTexArea(vtn.getTex());
        conexionSoc.IniciarServer();
//        VentanaPrin vtn = new VentanaPrin();
//        vtn.setVisible(true);
    }
}
