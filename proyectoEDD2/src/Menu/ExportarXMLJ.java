package Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class ExportarXMLJ {

    public ArrayList<String> key = new ArrayList();
    public ArrayList<String> value = new ArrayList();
    ArrayList<Registro> registrosAux1 = new ArrayList();

    public String nombre_archivo;

    Registro registro = new Registro();

    public ExportarXMLJ() {
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public ArrayList<String> getKey() {
        return key;
    }

    public void setKey(ArrayList<String> key) {
        this.key = key;
    }

    public void setKey(String k) {
        key.add(k);
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }

    public void setValue(String v) {
        value.add(v);
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public ArrayList<Registro> getRegistrosAux1() {
        return registrosAux1;
    }

    public void setRegistrosAux1(ArrayList<Registro> registrosAux1) {
        this.registrosAux1 = registrosAux1;
    }

    /*public void generate() throws Exception {

        if (key.isEmpty() || value.isEmpty() || key.size() != value.size()) {
            System.out.println("ERROR empty ArrayList");
            return;
        } else {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nombre_archivo, null);
            document.setXmlVersion("1.0");

            //Nodo main
            Element raiz = document.getDocumentElement();

            for (int i = 0; i < key.size(); i++) {
                
                Element itemNode = document.createElement("ITEM");
                //Nodo LLave
                Element keyNode = document.createElement("KEY");
                Text nodeKeyValue = document.createTextNode(key.get(i));
                keyNode.appendChild(nodeKeyValue);
                //Valor
                Element valueNode = document.createElement("VALUE");
                Text nodeValueValue = document.createTextNode(value.get(i));
                valueNode.appendChild(nodeValueValue);
                //QUE ES ESE SOLO?
                itemNode.appendChild(keyNode);
                itemNode.appendChild(valueNode);
                //append a la Raiz
                raiz.appendChild(itemNode); //pegar a la raiz "Documento"

            }
            System.out.println();
            //despeje y exportacion
            Source source = new DOMSource(document);
            //ES LA GUITARRA DE LOLO
            Result result = new StreamResult(new java.io.File(nombre_archivo + ".xml")); //
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        }

    }*/
    public void generate2() {

        Element raiz = new Element("Personas");
        Document documento = new Document(raiz);

        for (int i = 0; i < registrosAux1.size(); i++) {
            Element num = new Element("Registro");
            String aux = "" + i;
            num.setAttribute("No.", aux);
            for (int j = 0; j < registrosAux1.get(i).getCampos().size(); j++) {
                key.add(registrosAux1.get(i).getCampos().get(j).getNombre());
                value.add(registrosAux1.get(i).getCampos().get(j).getContenido());

            }
            for (int k = 0; k < key.size(); k++) {
                Element campo = new Element(key.get(k));
                campo.setText(value.get(k));
                
                num.addContent(campo);
                
            }
            raiz .addContent(num);
            key.clear();
            value.clear();
        }
        
        XMLOutputter xout = new XMLOutputter();

        try {

            //Criando o arquivo de saida
            FileWriter arquivo = new FileWriter(
                    new File("C:\\Users\\Jahaziel\\Desktop\\"+nombre_archivo+".xml"));

            //Imprimindo o XML no arquivo
            xout.output(documento, arquivo);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
