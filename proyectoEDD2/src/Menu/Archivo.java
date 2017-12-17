/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.util.ArrayList;

/**
 *
 * @author adgri_001
 */
public class Archivo {
    ArrayList <Registro> registros = new ArrayList();
    String nombre;
    
    public Archivo() {
    }

    public Archivo(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Registro getRegistro(int n) {
        return registros.get(n);
    }

    public String getNombre() {
        return nombre;
    }

    public void setRegistros(Registro r) {
        registros.add(r);
    }
}
