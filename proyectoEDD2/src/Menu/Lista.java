package Menu;

import java.util.*;

public class Lista{
    
    public static ArrayList<Integer> ingresados;


    public Lista()
    {
       ingresados = new ArrayList<Integer>();
    }
    public boolean buscar(int valor){
        boolean esta = false;
        for (int i = 0; i < ingresados.size() && !esta; i++){
            if (ingresados.get(i) == valor){
                 esta = true;
                
            }
        }
        return esta;
        
    }
}
