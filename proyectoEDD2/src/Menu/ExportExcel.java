package Menu;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExportExcel {
    
    ArrayList <Campo> Campos = new ArrayList();
    ArrayList <Registro> Registros = new ArrayList();
    String Nombre;
    
    public ExportExcel() {
        
    }

    public ArrayList<Campo> getCampos() {
        return Campos;
    }

    public void setCampos(ArrayList<Campo> Campos) {
        this.Campos = Campos;
    }

    public ArrayList<Registro> getRegistros() {
        return Registros;
    }

    public void setRegistros(ArrayList<Registro> Registros) {
        this.Registros = Registros;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
   
    
    public void ExportXMLJazz() throws IOException{
        
        /*La ruta donde se creará el archivo*/
        String rutaArchivo = System.getProperty("user.home")+"/"+Nombre+".xls";//sustituir ruta por user.home
        /*Se crea el objeto de tipo File con la ruta del archivo*/
        File archivoXLS = new File(rutaArchivo);
        /*Si el archivo existe se elimina*/
        if(archivoXLS.exists()) archivoXLS.delete();
        /*Se crea el archivo*/
        archivoXLS.createNewFile();
        
        /*Se crea el libro de excel usando el objeto de tipo Workbook*/
        Workbook libro = new HSSFWorkbook();
        /*Se inicializa el flujo de datos con el archivo xls*/
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        
        /*Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del libro que creamos anteriormente*/
        Sheet hoja = libro.createSheet(Nombre);
         
        Row fila2 = hoja.createRow(0);            
        for (int j = 0; j < Campos.size(); j++) {
                    Cell celda = fila2.createCell(j);        
                    celda.setCellValue(Campos.get(j).getNombre());
        }
        
        
        
        for (int i = 0; i < Registros.size(); i++) {
            Row fila = hoja.createRow(i+1);            
                for (int j = 0; j < Campos.size(); j++) {
                    Cell celda = fila.createCell(j);        
                    
                     //Si no es la primera fila establecemos un valor
                     celda.setCellValue(Registros.get(i).getCampos().get(j).getContenido());
                        
            
                }
        }
        
        /*Escribimos en el libro*/
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
            
    }
   
}
