/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package conjuntistas.estaticas;

/**
 *
 * @author ulise
 */
public class TestAH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolHeapMax arbol= new ArbolHeapMax();
        
        arbol.insertar(10);
        arbol.insertar(5);
        arbol.insertar(15);
        arbol.insertar(20);
        arbol.insertar(1);
        arbol.insertar(30);
        arbol.insertar(40);
        
        arbol.eliminarCima();
        System.out.println(arbol.toString());
    }
    
}
