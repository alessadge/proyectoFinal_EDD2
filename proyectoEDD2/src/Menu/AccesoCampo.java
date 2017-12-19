
package Menu;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class AccesoCampo {
    
    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    static int tamanoRegistro = 80;
    int tamCampo;
    int tamRegistro;
    private static ArrayList<String> nombresCampos = new ArrayList();
    
    public void crearFileCampo(File archivo) throws IOException {
        if (archivo.exists() && !archivo.isFile()) {
            throw new IOException(archivo.getName() + " no es un archivo");
        }
        flujo = new RandomAccessFile(archivo, "rw");
        numeroRegistros = (int) Math.ceil(
                (double) flujo.length() / (double) tamanoRegistro);
    }

    public  void cerrar() throws IOException {
        flujo.close();
    }
    
    public static boolean setCampo(int i, Campo campo) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(campo.getTamano() > tamanoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamanoRegistro);
                flujo.writeUTF(campo.getNombre());
                //tamCampo++;
                //flujo.writeBoolean(persona.isActivo());
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }
  
    public static int getNumeroRegistros() {
        return numeroRegistros;
    }
    public void escribirMetadata(String metadata) throws IOException{
        flujo.seek(0);
        flujo.writeUTF(metadata);
        numeroRegistros++;
    }
    public  void escribirCampos(ArrayList<Campo> campos, String metadata) throws IOException{
        flujo.seek(numeroRegistros*tamanoRegistro);
        int numerodeCampos = campos.size();
        String temp = ""+numerodeCampos;
        flujo.writeUTF(temp);
        numeroRegistros++;
        for (int i = 0; i < campos.size(); i++) {
            anadirCampo(campos.get(i));
        }
        
    }
   
    public static void anadirCampo(Campo campo) throws IOException{
        if (setCampo(numeroRegistros, campo)){
            numeroRegistros++;
        } 
    }
    
     public void escribirRegistro(ArrayList<Registro> registro) throws IOException{        
        for (int i = 0; i < registro.size(); i++) {
            for (int j = 0; j < registro.get(i).getCampos().size(); j++) {
                if (setCampoRegistro(numeroRegistros, registro.get(i).getCampos().get(j))){
                    numeroRegistros++;
                }
            }
        }
    }
     
    public String leerMetadata() throws IOException{
        flujo.seek(0);
        numeroRegistros++;
        return flujo.readUTF();
    }
    public int leerNumRegistros() throws IOException{
        int retur;
        flujo.seek(1*tamanoRegistro);
        String temp=flujo.readUTF();
        retur = Integer.parseInt(temp);
        numeroRegistros++;
        tamRegistro = retur;
        return retur;
    }
    public void escribirNumRegistros(ArrayList<Registro>registros) throws IOException{
        flujo.seek(numeroRegistros*tamanoRegistro);
        int numerodeRegistros = registros.size();
        String temp = "" +numerodeRegistros;
        flujo.writeUTF(temp);
        numeroRegistros++;
    }
    
    public int leerNumCampos() throws IOException{
        String temp;
        flujo.seek(2*tamanoRegistro);
        temp=flujo.readUTF();
        int retur = Integer.parseInt(temp);
        numeroRegistros++;
        tamCampo = retur;
        return retur;
    }
    
    public ArrayList<Campo> leerCampos() throws IOException{
        ArrayList<Campo> temporal = new ArrayList();
        
        for (int i = 3; i < tamCampo+3; i++) {
            Campo campo = new Campo();
            campo = getCampo(i);
            temporal.add(campo);
            numeroRegistros++;
            nombresCampos.add(campo.getNombre());
        }
        return temporal;
    }

    public ArrayList<Registro> leerRegistros() throws IOException{
        ArrayList<Registro> temporal = new ArrayList();
        ArrayList<Campo> nada = new ArrayList();
        Registro registro = new Registro();
        registro.setCampos(nada);
        int acum=0;
        for (int i = 0; i < tamRegistro; i++) {
            registro = new Registro();
            nada = new ArrayList();
            registro.setCampos(nada);
            for (int j = 0; j < tamCampo; j++) {
                registro.getCampos().add(getCampoReg(acum+tamCampo+3));
                registro.getCampos().get(j).setNombre(nombresCampos.get(j));
                acum++;

            }
            temporal.add(registro);
        }
        return temporal;
    }
    
    public Registro devolverRegistro(int x) throws IOException{
        Registro registro=null;
        ArrayList<Campo> nada = new ArrayList();
        //registro.setCampos(nada);
        if (tamCampo == 0) {
            System.out.println("Tiene que cargar primero");
        }else{
            int posicion = tamCampo+3+x;

            for (int i = 0; i < tamCampo; i++) {
                flujo.seek(posicion);
                Campo campo = new Campo();
                campo.setContenido(flujo.readUTF());
                campo.setNombre(nombresCampos.get(i));
                registro.getCampos().add(campo);
                posicion++;
            }
        }
        return registro;
    }
    
   
    public  void modificarRegistro(int x,Registro temp) throws IOException{
        if (tamCampo == 0) {
            System.out.println("Tiene que cargar primero");
        }else{
            int posicion = (x)*tamanoRegistro;
            flujo.seek(posicion);
            for (int i = 0; i < tamCampo; i++) {
                for (int j = 0; j < tamanoRegistro; j++) {
                    flujo.seek(posicion+j);
                    flujo.writeUTF("");
                }
                setCampoRegistro(posicion,temp.getCampos().get(i));
                posicion++;
            }
        }
    }
    public void modificarCampo(int x,String temp) throws IOException{
        if (tamCampo == 0) {
            System.out.println("Tiene que cargar primero");
        }else{
            int posicion = (x)*tamanoRegistro;
                for (int j = 0; j < tamanoRegistro; j++) {
                    flujo.seek(posicion+j);
                    flujo.writeUTF("");
                }          
            flujo.seek(posicion);
            flujo.writeUTF(temp);
        }
    }
    
    public ArrayList<Registro> devolverRegistros(int x, int y) throws IOException{
        ArrayList<Registro> temporal = new ArrayList();
        Registro registro = null;
        registro.setCampos(new ArrayList());
        int numeroR = y-x;
        x = x * tamCampo;
        y = y * tamCampo;
        
        int acum = 0;
        for (int i = 0; i < numeroR; i++) {
            registro = new Registro();
            for (int j = 0; j < tamCampo; j++) {
                registro.getCampos().get(j).setNombre(nombresCampos.get(j));
                registro.getCampos().add(getCampo(tamCampo +3+(acum*x)));
                acum++;
                temporal.add(registro);
            }
        }
        return temporal;
    }
   
    public static Campo getCampo(int i) throws IOException {
        if (i >= 0 && i <= getNumeroRegistros()){
            flujo.seek(i * tamanoRegistro);
            return new Campo(flujo.readUTF(), null);
        }else{
            System.out.println("\nNumero de registro fuera de limites");
            return null;
        }
    }
    public static Campo getCampoReg(int i) throws IOException {
        if (i >= 0 && i <= getNumeroRegistros()){
            flujo.seek(i * tamanoRegistro);
            return new Campo(null, flujo.readUTF());
        }else{
            System.out.println("\nNumero de registro fuera de limites");
            return null;
        }
    }
    
    public boolean setCampoRegistro(int i, Campo campo) throws IOException {
        if(i >= 0 && i <= getNumeroRegistros()) {
            if(campo.getTamano() > tamanoRegistro) {
                System.out.println("\nTamaño de registro excedido.");
            } else {
                flujo.seek(i*tamanoRegistro);
                flujo.writeUTF(campo.getContenido());
                //tamCampo++;
                //flujo.writeBoolean(persona.isActivo());
                return true;
            }
        } else {
            System.out.println("\nNúmero de registro fuera de límites.");
        }
        return false;
    }
    
}
