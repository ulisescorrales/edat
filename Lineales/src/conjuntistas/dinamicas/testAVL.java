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
        
        arbol.insertar(80);
        arbol.insertar(90);
        arbol.insertar(70);
        arbol.insertar(60);
        arbol.insertar(75);
        arbol.insertar(76);
        
        System.out.println(arbol.toString());
        
        arbol.reacomodar(arbol.raiz);
        
        System.out.println(arbol.toString());
    }
    
}
