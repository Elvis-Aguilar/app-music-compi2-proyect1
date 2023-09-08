package com.sistema.musicserver.errors;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author elvis_agui
 */
public class ErroresSingleton {

    private ArrayList<ErrorSintactico> erroresSintacticos = new ArrayList<>();
    private ArrayList<ErrorSemantico> erroresSemanticos = new ArrayList<>();
    private ArrayList<ErrorLexicos> erroresLexicos = new ArrayList<>();

    private static ErroresSingleton instance;

    public static ErroresSingleton getInstance() {
        if (instance == null) {
            instance = new ErroresSingleton();
        }
        return instance;
    }

    public void clear() {
        this.erroresLexicos.clear();
        this.erroresSemanticos.clear();
        this.erroresSemanticos.clear();
    }

    public boolean existenErrores(JTextArea areaTexto) {
        if (!this.erroresLexicos.isEmpty() && !this.erroresSintacticos.isEmpty()) {
            areaTexto.setText("Revisar en el reporte lexicos como sintacticos, uno de los errores encontrados es (Lexicos): " + this.erroresLexicos.get(0).getDescripcion() + " Token: " + this.erroresLexicos.get(0).getToken());
            areaTexto.append("\n y (Sintacticos) " + this.erroresSintacticos.get(0).getDescripcion() + " en el Token: " + this.getErroresSemanticos().get(0).getToken());
            JOptionPane.showMessageDialog(null, "Existen errores Lexicos y sintacticos en el codigo fuente, Revisar Reporte de errores");
        } else if (!this.erroresLexicos.isEmpty()) {
            areaTexto.setText("Revisar en el reporte lexicos, uno de los errores encontrados es: " + this.erroresLexicos.get(0).getDescripcion() + " Token: " + this.erroresLexicos.get(0).getToken());
            JOptionPane.showMessageDialog(null, "Existen errores Lexicos en el codigo fuente, Revisar Reporte de errores");
        } else if (!this.erroresSintacticos.isEmpty()) {
            areaTexto.setText("Revisar en el reporte Sintacticos, uno de los errores encontrados es: " + this.erroresLexicos.get(0).getDescripcion() + " Token: " + this.erroresLexicos.get(0).getToken());
            JOptionPane.showMessageDialog(null, "Existen errores Sintacticos en el codigo fuente, Revisar Reporte de errores");
        } else if (!this.erroresSemanticos.isEmpty()) {
            areaTexto.setText("Revisar en el reporte Semanticos, uno de los errores encontrados es: " + this.erroresLexicos.get(0).getDescripcion() + " Token: " + this.erroresLexicos.get(0).getToken());
            JOptionPane.showMessageDialog(null, "Existen errores Semanticos en el codigo fuente, Revisar Reporte de errores");

        }
        return this.erroresLexicos.isEmpty() && this.erroresSintacticos.isEmpty() && this.erroresSemanticos.isEmpty();
    }
    
    public boolean noHayErrores(){
        return this.erroresLexicos.isEmpty() && this.erroresSintacticos.isEmpty();
    }

    public ArrayList<ErrorSintactico> getErroresSintacticos() {
        return erroresSintacticos;
    }

    public void setErroresSintacticos(ArrayList<ErrorSintactico> erroresSintacticos) {
        this.erroresSintacticos = erroresSintacticos;
    }

    public ArrayList<ErrorSemantico> getErroresSemanticos() {
        return erroresSemanticos;
    }

    public void setErroresSemanticos(ArrayList<ErrorSemantico> erroresSemanticos) {
        this.erroresSemanticos = erroresSemanticos;
    }

    public ArrayList<ErrorLexicos> getErroresLexicos() {
        return erroresLexicos;
    }

    public void setErroresLexicos(ArrayList<ErrorLexicos> erroresLexicos) {
        this.erroresLexicos = erroresLexicos;
    }

}
