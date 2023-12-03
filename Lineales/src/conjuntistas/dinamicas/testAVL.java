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
        ArbolAVL arbol = new ArbolAVL();

        //prueba1(arbol);
        //prueba2(arbol);
        prueba3(arbol);
        //prueba4(arbol);
    }

    public static void prueba1(ArbolAVL arbol) {
        arbol.insertar(12);
        arbol.insertar(5);
        arbol.insertar(23);
        arbol.insertar(3);
        arbol.insertar(8);
        arbol.insertar(10);

        imprimir(arbol);
    }

    public static void prueba2(ArbolAVL arbol) {
        arbol.insertar(100);
        arbol.insertar(110);
        arbol.insertar(120);

        imprimir(arbol);
    }

    public static void prueba3(ArbolAVL arbol) {
        arbol.insertar(50);
        arbol.insertar(60);
        arbol.insertar(40);
        arbol.insertar(65);
        arbol.insertar(30);
        arbol.insertar(45);
        arbol.insertar(42);
        
        arbol.eliminar(65);
        imprimir(arbol);
    }

    public static void prueba4(ArbolAVL arbol) {
        arbol.insertar(100);
        arbol.insertar(90);
        arbol.insertar(110);
        arbol.insertar(105);
        arbol.insertar(103);
        arbol.insertar(120);
        imprimir(arbol);

    }

    public static void imprimir(ArbolAVL arb) {
        System.out.println(arb.toString());
    }
}
