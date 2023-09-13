/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.musicserver.conexion;

import com.sistema.musicserver.instrucciones.music.CanalMusical;
import com.sistema.musicserver.instrucciones.music.ListaMusical;
import com.sistema.musicserver.instrucciones.music.PistaMusical;
import com.sistema.musicserver.instrucciones.music.Solicitud;
import com.sistema.musicserver.pista.Lista;
import com.sistema.musicserver.pista.PistasCompiladas;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author elvis_agui
 */
public class SocketServidor extends Thread {

    private Socket actual;
    private ServerSocket servidor = null;
    private Socket oyente = null;
    private DataInputStream input;
    private DataOutputStream ouput;
    private JTextArea texArea;

    public void IniciarServer() {
        final int PUERTO = 7000;
        try {
            servidor = new ServerSocket(PUERTO);
            while (true) {
                oyente = servidor.accept();
                actual = oyente;
                // Para cada conexión entrante, crea un hilo o subproceso para manejarla
                Thread clientThread = new Thread(new ClientHandler(oyente));
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

// Clase para manejar las conexiones de clientes en hilos separados
    class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                input = new DataInputStream(oyente.getInputStream());
                OptionConexion aux = OptionConexion.valueOf(input.readUTF());
                //System.out.println(aux.toString());
                recibirPeticion(aux);
                //clientSocket.close(); // Cierra el socket del cliente después de manejar la conexión
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public void recibirPeticion(OptionConexion optionPeticion) throws IOException {
        switch (optionPeticion) {
            case LISTAS_COMPLETAS:
                enviarListasCompleatas();
                break;
            case LISTAS_NOMBRE:
                enviarListasConNombre();
                break;
            case PISTAS_COMPLETAS:
                enviarPistasCompletas();
                break;
            case PISTAS_NOMBRE:
                enviarPistasNombre();
                break;
            case ERROR:
                break;
            default:
                break;

        }
    }

    public void enviarListasCompleatas() {
        ObjectInputStream inputO;
        ObjectOutputStream ouputO;
        try {
            inputO = new ObjectInputStream(actual.getInputStream());
            ouputO = new ObjectOutputStream(actual.getOutputStream());
            Solicitud solicitud = (Solicitud) inputO.readObject();
            this.texArea.append("\n -----Solicitud: \n");
            this.texArea.append(solicitud.getTextSolicitud() + "\n");
            this.ouput = new DataOutputStream(oyente.getOutputStream());
            this.texArea.append("\n -----Respuesta: \n");
            Solicitud respusta = resolverSolicitudLista(solicitud);
            ouputO.writeObject(respusta);
            this.ouput.writeUTF(OptionConexion.LISTAS_COMPLETAS.toString());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private Solicitud resolverSolicitudLista(Solicitud solicitud) {
        ArrayList<ListaMusical> listasMusicales = recuperarLista();
        solicitud.setListasMusicales(listasMusicales);
        solicitud.setTextSolicitud(constructorResultadoListas(listasMusicales));
        return solicitud;

    }

    private ArrayList<ListaMusical> recuperarLista() {
        ArrayList<ListaMusical> listasMusicales = new ArrayList<>();
        ArrayList<Lista> listas = PistasCompiladas.getInstancePistasActivacion().getListas();
        listas.forEach(lista -> {
            listasMusicales.add(new ListaMusical(lista.getListasMusicales(), lista.getNombre()));
        });

        return listasMusicales;
    }

    private String constructorResultadoListas(ArrayList<ListaMusical> listasMusicales) {
        String inicio = "< listas >\n";
        inicio = listasMusicales.stream().map(listasMusicale -> "    < lista nombre = \"" + listasMusicale.getNombre() + "\" pistas = " + listasMusicale.getListasMusicales().size() + " > \n").reduce(inicio, String::concat);
        inicio += "</ listas >";
        this.texArea.append(inicio + "\n");
        return inicio;

    }

    /**
     * funcion encargada de responder la solicitud lista con nombre, listando
     * sus pistas
     */
    public void enviarListasConNombre() {
        ObjectInputStream inputO;
        ObjectOutputStream ouputO;
        try {
            inputO = new ObjectInputStream(actual.getInputStream());
            ouputO = new ObjectOutputStream(actual.getOutputStream());
            Solicitud solicitud = (Solicitud) inputO.readObject();
            this.texArea.append("\n -----Solicitud: \n");
            this.texArea.append(solicitud.getTextSolicitud() + "\n");
            this.ouput = new DataOutputStream(oyente.getOutputStream());
            this.texArea.append("\n -----Respuesta: \n");
            Solicitud respusta = resolverSolicitudListaNombre(solicitud);
            ouputO.writeObject(respusta);
            this.ouput.writeUTF(OptionConexion.LISTAS_NOMBRE.toString());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private Solicitud resolverSolicitudListaNombre(Solicitud solicitud) {
        int index = existeLista(solicitud.getNombre());
        if (index != -1) {
            solicitud.setLista(recuperarListaNombre(index, solicitud.getNombre()));
            solicitud.setTextSolicitud(contructorLista(solicitud.getLista(), true));
        } else {
            solicitud.setLista(new ListaMusical("no encontrada"));
            solicitud.setTextSolicitud(contructorLista(null, false));
        }
        return solicitud;

    }

    private int existeLista(String nombre) {
        int index = -1;
        ArrayList<Lista> listas = PistasCompiladas.getInstancePistasActivacion().getListas();
        for (int i = 0; i < listas.size(); i++) {
            if (listas.get(i).getNombre().equals(nombre)) {
                index = i;
                break;
            }
        }

        return index;

    }

    private ListaMusical recuperarListaNombre(int index, String nombre) {
        ListaMusical listasMusicales = new ListaMusical(PistasCompiladas.getInstancePistasActivacion().getListas().get(index).getListasMusicales(), nombre);
        return listasMusicales;
    }

    private String contructorLista(ListaMusical lista, boolean encontrada) {
        String inicio = "< lista ";
        if (encontrada) {
            inicio += "nombre = " + lista.getNombre() + " >\n";
            for (PistaMusical listasMusicale : lista.getListasMusicales()) {
                inicio += "    < pista nombre = \"" + listasMusicale.getNombre() + "\" duracion = " + listasMusicale.getMilisTotal() + " >\n";
            }
        } else {
            inicio += "nombre = := >\n";
            inicio += "    No Existente\n ";
        }
        inicio += "</ lista >";
        this.texArea.append(inicio + "\n");

        return inicio;
    }

    /**
     * funcion encargada de resolver la peticion de pistas sin nombre (todas las
     * pistas)
     */
    public void enviarPistasCompletas() {
        ObjectInputStream inputO;
        ObjectOutputStream ouputO;
        try {
            inputO = new ObjectInputStream(actual.getInputStream());
            ouputO = new ObjectOutputStream(actual.getOutputStream());
            Solicitud solicitud = (Solicitud) inputO.readObject();
            this.texArea.append("\n -----Solicitud: \n");
            this.texArea.append(solicitud.getTextSolicitud() + "\n");
            this.ouput = new DataOutputStream(oyente.getOutputStream());
            this.texArea.append("\n -----Respuesta: \n");
            Solicitud respusta = resolverPistasCompleatas(solicitud);
            ouputO.writeObject(respusta);
            this.ouput.writeUTF(OptionConexion.PISTAS_COMPLETAS.toString());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private Solicitud resolverPistasCompleatas(Solicitud solicitud) {
        if (!PistasCompiladas.getInstancePistasActivacion().getListas().isEmpty()) {
            ListaMusical listasMusicales = new ListaMusical(PistasCompiladas.getInstancePistasActivacion().getListas().get(0).getListasMusicales(), "");
            solicitud.setLista(listasMusicales);
            solicitud.setTextSolicitud(constructorPistasCompletas(true, listasMusicales));
        } else {
            solicitud.setLista(new ListaMusical("no encontrada"));
            solicitud.setTextSolicitud(constructorPistasCompletas(false, null));
        }

        return solicitud;
    }

    private String constructorPistasCompletas(boolean hayPistas, ListaMusical listasMusicales) {
        String inicio = "< pistas >\n";
        if (hayPistas) {
            for (PistaMusical pista : listasMusicales.getListasMusicales()) {
                inicio += "    < pista nombre = \"" + pista.getNombre() + "\" duracion = " + pista.getMilisTotal() + " >\n";
            }
        } else {
            inicio += "   Aun no hay pistas existentes\n";
        }
        inicio += "</ pistas >";
        this.texArea.append(inicio + "\n");

        return inicio;
    }

    public void enviarPistasNombre() {
        ObjectInputStream inputO;
        ObjectOutputStream ouputO;
        try {
            inputO = new ObjectInputStream(actual.getInputStream());
            ouputO = new ObjectOutputStream(actual.getOutputStream());
            Solicitud solicitud = (Solicitud) inputO.readObject();
            this.texArea.append("\n -----Solicitud: \n");
            this.texArea.append(solicitud.getTextSolicitud() + "\n");
            this.ouput = new DataOutputStream(oyente.getOutputStream());
            this.texArea.append("\n -----Respuesta: \n");
            Solicitud respusta = resolverPistaNombre(solicitud);
            ouputO.writeObject(respusta);
            this.ouput.writeUTF(OptionConexion.PISTAS_NOMBRE.toString());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private Solicitud resolverPistaNombre(Solicitud solicitud) {
        int index = indexPistaMusical(solicitud.getNombre());
        solicitud.setTextSolicitud(constructorPistaNombre(index));
        solicitud.setPista(pistaAenviar(index));
        return solicitud;
    }

    private int indexPistaMusical(String nombre) {
        int index = -1;
        Lista lis = PistasCompiladas.getInstancePistasActivacion().getListas().get(0);
        for (int i = 0; i < lis.getListasMusicales().size(); i++) {
            if (lis.getListasMusicales().get(i).getNombre().equals(nombre)) {
                index = i;
                break;
            }
        }

        return index;

    }

    private PistaMusical pistaAenviar(int index) {
        if (index != -1) {
            Lista lis = PistasCompiladas.getInstancePistasActivacion().getListas().get(0);
            return lis.getListasMusicales().get(index);
        }
        return null;
    }

    private String constructorPistaNombre(int index) {
        String inicio = "< pista nombre = ";
        if (index != -1) {
            Lista lis = PistasCompiladas.getInstancePistasActivacion().getListas().get(0);
            PistaMusical pista = lis.getListasMusicales().get(index);
            inicio += "\"" + pista.getNombre() + "\" >\n";
            for (CanalMusical canale : pista.getCanales()) {
                inicio += "    < canal numero = " + canale.getCanal() + " >\n";
                inicio += "        < duracion total = " + canale.getMilisTotal() + ">\n";
                inicio += "    </ canal >\n";
            }
        } else {
            inicio = "< pista nombre = :+= >\n    Pista no encontrada\n</ pista>";
        }
        this.texArea.append(inicio + "\n");

        return inicio;
    }

    public JTextArea getTexArea() {
        return texArea;
    }

    public void setTexArea(JTextArea texArea) {
        this.texArea = texArea;
    }

}
