package com.sistema.musicserver.archivos;

import com.sistema.musicserver.pista.Lista;
import com.sistema.musicserver.pista.Pista;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author elvis_agui
 */
public class ManejadorArchivos {

    public void guardarPistasActivacion(String nombreArchivo, ArrayList<Pista> pistas) {
        this.crearArvhivosBin(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(pistas);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Guardar del archivo Binario, de las Pistas :(");
        }
    }

    public void guardarListasPistas(String nombreArchivo, ArrayList<Lista> listas) {
        this.crearArvhivosBin(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(listas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Guardar del archivo Binario, de las listas :(");
        }
    }

    public ArrayList<Pista> readPistasActivacion(String nombreArchivo) {
        ArrayList<Pista> pistas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return pistas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            pistas = (ArrayList<Pista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo Binario, de las Pistas :(");
        }
        return pistas;
    }

    public ArrayList<Lista> readListas(String nombreArchivo) {
        ArrayList<Lista> listas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            //System.out.println("El archivo no existe.");
            return listas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            listas = (ArrayList<Lista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error la lectura del archivo Binario, de las listas :(");
        }
        return listas;
    }

    /**
     * funcion que crea el archivo si no existe
     *
     * @param nombreArchivo
     */
    private void crearArvhivosBin(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error en la creacion del archivo Bin :(");
            }
        }
    }

}
