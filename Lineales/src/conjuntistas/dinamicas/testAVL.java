  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

/**
 *
 * @author ulises.corrales
 */
public class testAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolAVL arbol=new ArbolAVL();
        
        arbol.insertar(12);
        arbol.insertar(5);
        arbol.insertar(23);
        arbol.insertar(3);
        arbol.insertar(8);
        arbol.insertar(10);
        
        System.out.println(arbol.toString());
        
        arbol.reacomodar(arbol.raiz);
        
        System.out.println(arbol.toString());
    }
    
}
