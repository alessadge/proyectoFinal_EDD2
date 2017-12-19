/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author adgri_001
 */
public class Campo implements Serializable {
    String Nombre;
    String Contenido;
    ArrayList <String> contenidos = new ArrayList();
    
    public Campo() {
  
    }
    
    public Campo(String Nombre, String Contenido) {
        this.Nombre = Nombre;
        this.Contenido = Contenido;
    }

    public ArrayList<String> getContenidos() {
        return contenidos;
    }

    public void setContenidos(ArrayList<String> contenidos) {
        this.contenidos = contenidos;
    }
    
    public String getContenidoA(int n) {
        return contenidos.get(n);
    }

    public void setContenidoA(String a) {
        contenidos.add(a);
    }
    
    
    public Campo(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }
    
    @Override
    public String toString() {
        return "\nNombre: " + Nombre;
    }   
    
     public int getTamano() {
        return getNombre().length()*2 + 1;
    }
}
