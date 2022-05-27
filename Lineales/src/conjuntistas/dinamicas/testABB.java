/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

import conjuntistas.dinamicas.ArbolBB;

/**
 *
 * @author ulises.corrales
 */
public class testABB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolBB arbol=new ArbolBB();
        
        arbol.insertar(5);
        arbol.insertar(2);
        arbol.insertar(3);
        arbol.insertar(1);
        arbol.insertar(10);
        arbol.insertar(7);
        //Insertar elemento repetido
        arbol.insertar(5);
        arbol.insertar(9);
        
        
        //Mostrar el arbol
        System.out.println(arbol.toString());
        
        //Pertenece
        System.out.println(arbol.pertenece(3));
        System.out.println(arbol.pertenece(9));
        System.out.println(arbol.pertenece(20));
        
        //Listar
        System.out.println(arbol.listar().toString());
        
        //Minimo elemento
        System.out.println(arbol.minimoElem());
        //Maximo elemento
        System.out.println(arbol.maximoElem());
        
        //Arbol vacio        
        System.out.println(arbol.esVacio());
        arbol= new ArbolBB();
        System.out.println(arbol.esVacio());
        llenarArbol(arbol);
        System.out.println(arbol.listar().toString());
        
        
        
        arbol.eliminar(5);
        System.out.println(arbol.listar().toString());
        
    }
    
    public static void llenarArbol(ArbolBB arb){
        arb.insertar(5);
        arb.insertar(2);
        arb.insertar(3);
        arb.insertar(1);
        arb.insertar(10);
        arb.insertar(7);
        //Insertar elemento repetido
        arb.insertar(5);
        arb.insertar(9);
        arb.insertar(11);
    }
    
}
