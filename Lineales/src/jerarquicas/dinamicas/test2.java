/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jerarquicas.dinamicas;

/**
 *
 * @author ulise
 */
public class test2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolGen arbol=new ArbolGen();
        llenarArbol(arbol);
        
        Object padre=arbol.padre('C');
        System.out.println(padre.toString());
                       
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
