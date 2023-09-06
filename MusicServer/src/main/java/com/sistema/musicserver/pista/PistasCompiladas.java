package com.sistema.musicserver.pista;

import com.sistema.musicserver.analizadores.pista.Token;
import com.sistema.musicserver.errors.ErrorSemantico;
import java.util.ArrayList;

/**
*clase que represnta la lista de pistas compiladas, extraendo su arbol de activacion, tabla de simbolos
*asi mismo servira para extender de ellas, esta clase es la que hay que serializar y obtener cuando el programa 
*sea inicializado
* @author elvis_agui
*/
public class PistasCompiladas {

    
    private static PistasCompiladas instance;
    private ArrayList<Pista> pistas = new ArrayList<>();
    
    public static PistasCompiladas getInstancePistasActivacion() {
        if (instance == null) {
            instance = new PistasCompiladas();
        }
        return instance;
    }
    
    /**
     * agrega la pista compilada a array de pista si y solo si no hay errores
     * @param pista 
     * @param errorsSemanticos 
     */
    public void push(Pista pista, ArrayList<ErrorSemantico> errorsSemanticos){
        if (errorsSemanticos.isEmpty()) {
            this.pistas.add(new Pista(pista.getPistaMusical(), pista.getFunciones(), pista.getNombre(), pista.getTableSimbolGoblal()));
        }else{
            System.out.println("errores en la pista");
        }
    }
    
    /**
     * funcion para buscar en base al nombre de la pista, la pista que extendera
     * @param id
     * @return 
     */
    public Pista getPistaExtends(Token id){
        Pista pist = null;
        for (Pista pista : pistas) {
            if (pista.getNombre().equals(id.getLexeme())) {
                pist = pista;
                break;
            }
        }
        
        return pist;
    }
    
    
    
    

   
    public ArrayList<Pista> getPistas() {
        return pistas;
    }

    public void setPistas(ArrayList<Pista> pistas) {
        this.pistas = pistas;
    }
    
    
    
    
    
    
    
    

}
