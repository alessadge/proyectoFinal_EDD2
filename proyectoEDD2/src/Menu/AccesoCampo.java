
package Menu;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class AccesoCampo {
    
    private static RandomAccessFile flujo;
    private static int numeroRegistros;
    private static int tamanoRegistro = 80;
    private static int tamCampo;
    private static int tamRegistro;
    private static ArrayList<String> nombresCampos = new ArrayList();
    
    public void crearFileCampo(File archivo) throws IOException {
        if (archivo.exists() && !archivo.isFile()) {
            throw new IOException(archivo.getName() + " no es un archivo");
        }
        flujo = new RandomAccessFile(archivo, "rw");
        numeroRegistros = (int) Math.ceil(
                (double) flujo.length() / (double) tamanoRegistro);
    }

    public static void cerrar() throws IOException {
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
    
    public  void escribirCampos(ArrayList<Campo> campos, String metadata) throws IOException{
        flujo.seek(0);
        flujo.writeUTF(metadata);
        numeroRegistros++;
        flujo.seek(numeroRegistros*tamanoRegistro);
        flujo.writeInt(campos.size());
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
            tamRegistro++;
            for (int j = 0; j < registro.get(i).getCampos().size(); j++) {
                if (setCampo(numeroRegistros, registro.get(i).getCampos().get(j))){
                    numeroRegistros++;
                }
            }
        }
    }
     
    public String leerMetadata() throws IOException{
        String metadata;
        flujo.seek(0);
        //numeroRegistros++;
        return flujo.readUTF();
    }
    
    public void leerNumCampos() throws IOException{
        int temp = 0;
        flujo.seek(1*tamanoRegistro);
        temp=flujo.readInt();
        //numeroRegistros++;
        tamCampo = temp;
    }
    
    public ArrayList<Campo> leerCampos() throws IOException{
        ArrayList<Campo> temporal = new ArrayList();
        
        for (int i = 2; i < tamCampo+2; i++) {
            Campo campo = new Campo();
            campo = getCampo(i);
            temporal.add(campo);
            //numeroRegistros++;
            nombresCampos.add(campo.getNombre());
        }
        return temporal;
    }
    
    
    
    
    public ArrayList<Registro> leerRegistros() throws IOException{
        ArrayList<Registro> temporal = new ArrayList();
        Registro registro = null;
        registro.setCampos(new ArrayList());
        int acum=0;
        for (int i = 0; i < tamRegistro; i++) {
            registro = new Registro();
            for (int j = 0; j < tamCampo; j++) {
                registro.getCampos().add(getCampo(acum+tamCampo+1+1));
                registro.getCampos().get(j).setNombre(nombresCampos.get(j));
                acum++;
                temporal.add(registro);
            }
        }
        return temporal;
    }
    public  Registro devolverRegistro(int x) throws IOException{
        Registro registro=null;
        registro.setCampos(new ArrayList());
        if (tamCampo == 0) {
            System.out.println("Tiene que cargar primero");
        }else{
            int posicion = tamCampo+2+x;
            flujo.seek(posicion);
            for (int i = 0; i < tamCampo; i++) {
                registro.getCampos().add(getCampo(posicion));
                registro.getCampos().get(i).setNombre(nombresCampos.get(i));
                posicion++;
            }
        }
        return registro;
    }
    
    public  void modificarRegistro(int x,Registro temp) throws IOException{
        if (tamCampo == 0) {
            System.out.println("Tiene que cargar primero");
        }else{
            int posicion = tamCampo+2+x;
            flujo.seek(posicion);
            for (int i = 0; i < tamCampo; i++) {
                for (int j = 0; j < tamanoRegistro; j++) {
                    flujo.seek(posicion*tamanoRegistro);
                    flujo.writeUTF("");
                }
                setCampo(posicion,temp.getCampos().get(i));
                posicion++;
            }
        }
    }
    
    public static ArrayList<Registro> devolverRegistros(int x, int y) throws IOException{
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
                registro.getCampos().add(getCampo(tamCampo +1+1+(acum*x)));
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
    
}
