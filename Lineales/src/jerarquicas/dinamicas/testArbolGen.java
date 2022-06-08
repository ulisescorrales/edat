/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas.dinamicas;
import lineales.dinamicas.Lista;
/**
 *
 * @author uliseslinux
 */
public class testArbolGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolGen arbol=new ArbolGen();
        Lista lis=new Lista();
        
        lis.insertar('A',lis.longitud()+1);
        lis.insertar('B',lis.longitud()+1);
        lis.insertar('G',lis.longitud()+1); 
       lis.insertar('H',lis.longitud()+1); 
        
        llenarArbol(arbol);
        
        System.out.println(arbol.verificarCamino(lis));
        
    }
    public static void llenarArbol(ArbolGen arb){
        //Método para llenar el arbol con elementos de prueba
        arb.insertar('A', 0);
        arb.insertar('B', 'A');
        arb.insertar('C', 'A');//Hoja que posee hermanos con hijos
        arb.insertar('D', 'A');
        arb.insertar('E', 'B');//Hijo Izquierdo sin hijos
        arb.insertar('F', 'B');        
        arb.insertar('G', 'B'); 
        arb.insertar('H', 'D'); 
        arb.insertar('I', 'D'); 
        arb.insertar('S', 'G'); 
        arb.insertar('S', 'G'); //Elemento repetido
        arb.insertar('J', 'H'); //Hijo único
        arb.insertar('K', 'D'); //Hermano final sin hijo
        
    }
}
