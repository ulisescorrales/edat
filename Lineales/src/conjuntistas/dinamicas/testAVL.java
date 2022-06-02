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
        
        arbol.insertar(100);
        arbol.insertar(105);
        arbol.insertar(110);
        arbol.insertar(120);
        arbol.insertar(90);
        arbol.insertar(95);
        arbol.insertar(80);
        System.out.println(arbol.toString());
    }
    
}
