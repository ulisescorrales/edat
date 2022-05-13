/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jerarquicas;

/**
 *
 * @author ulise
 */
public class testJerarquica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolGen arbol=new ArbolGen();
        
        arbol.insertar('A', 0);
        arbol.insertar('B', 'A');
        arbol.insertar('C', 'A');
        arbol.insertar('D', 'A');
        arbol.insertar('E', 'B');        
        arbol.insertar('F', 'B');        
        arbol.insertar('G', 'B'); 
        arbol.insertar('H', 'D'); 
        arbol.insertar('I', 'D'); 
        arbol.insertar('S', 'G'); 
        
        
        System.out.println(arbol.porNivel().toString());
        
    }
    
}
