package Menu;

public class Nodo{
    
    public int []valores;
    public Nodo []nodo;
    //public Registro [] registros;
    public static int numValores;
    public boolean tengoHijos = false;
    public int ocupados = 0;
    public Nodo padre;
    public Nodo(){
       nodo = new Nodo [Raiz.grado * 2 + 3];
       valores = new int [Raiz.grado * 2 + 1];
    }
    
}
