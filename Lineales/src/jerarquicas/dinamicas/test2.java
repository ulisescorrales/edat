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
        ArbolBin arbol = new ArbolBin();
        
        arbol.insertar(1, 0, true);
        arbol.insertar(2, 1, false);
        arbol.insertar(3, 1, true);
        arbol.insertar(4, 2, false);
        arbol.insertar(5, 2, true);
        arbol.insertar(5, 3, true);
        
        System.out.println(arbol.toString());

    }
}
