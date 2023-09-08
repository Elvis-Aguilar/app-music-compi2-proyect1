/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.musicserver.instrucciones;

import com.sistema.musicserver.errors.ErrorSemantico;
import com.sistema.musicserver.tablaSimbol.TablaSimbol;
import java.io.Serializable;
import java.util.ArrayList;



/**
 *
 * @author elvis_agui
 */
public abstract class Instruccions implements Serializable{
    
    
    public abstract void execute(ArrayList<ErrorSemantico> errorsSemanticos);
    
    public abstract void actionReferenciarTabla(TablaSimbol tabla);
    
}
