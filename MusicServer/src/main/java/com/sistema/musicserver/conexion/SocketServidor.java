/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.musicserver.conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author elvis_agui
 */
public class SocketServidor {

    private Socket actual;
    private ServerSocket servidor = null;
    private Socket oyente = null;
    private DataInputStream input;
    private DataOutputStream ouput;

    public void IniciarServer() {
        final int PUERTO = 7000;
        try {
            servidor = new ServerSocket(PUERTO);
            while (true) {
                oyente = servidor.accept();
                // Para cada conexión entrante, crea un hilo o subproceso para manejarla
                Thread clientThread = new Thread(new ClientHandler(oyente));
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                actual = clientSocket;
                input = new DataInputStream(clientSocket.getInputStream());
                OptionConexion aux = OptionConexion.valueOf(input.readUTF());
                recibirPeticion(aux);
                clientSocket.close(); // Cierra el socket del cliente después de manejar la conexión
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void recibirPeticion(OptionConexion optionPeticion) throws IOException {
        switch (optionPeticion) {
            case LISTAS:
                recibirProyectos();
                break;
            case PETICION:
                break;
            default:
                break;

        }
    }

    public void recibirProyectos() {
        ObjectInputStream inputO;
        try {
            inputO = new ObjectInputStream(actual.getInputStream());
            String archivos = (String) inputO.readObject();
            System.out.println(archivos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
