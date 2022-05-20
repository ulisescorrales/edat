/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jerarquicas;
import lineales.dinamicas.Lista;
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
        
        llenarArbol(arbol);
        
        
        Lista lis=new Lista();
        
        //Dos elementos hermanos
        System.out.println("Dos elementos hermanos:");
        
        lis.insertar('E',1);
        lis.insertar('F',1);                    
        
        System.out.println(lis.toString());
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Dos elementos hermanos invertidos
        System.out.println("Dos elementos hermanos invertidos:");
        lis.vaciar();
        lis.insertar('F',1);
        lis.insertar('E',1);                            
        System.out.println(lis.toString());
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Lista vacia
        System.out.println("Lista vacia:");
        lis.vaciar();
        System.out.println("ListaVacia:");
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Un solo elemento que pertenece
        System.out.println("Un solo elemento que pertenece:");
        lis.insertar('S', 1);
        System.out.println(lis.toString());
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Un solo elemento que no pertenece
        System.out.println("Un solo elemento que no pertenece");
        lis.vaciar();
        lis.insertar('Z', 1);
        System.out.println(lis.toString());
        System.out.println("Se espera false: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Un elemento que no es hoja, es interno
        System.out.println("Un elemento que no es hoja, es interno:");
        lis.vaciar();
        lis.insertar('G', 1);
        System.out.println(lis.toString());
        System.out.println("Se espera falso:"+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Arbol vacio, lista con elementos
        System.out.println("Arbol vacio, lista con elementos:");
        arbol.vaciar();
        lis.vaciar();
        lis.insertar('S', 1);
        System.out.println("Se espera false: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Elemento que es raíz del árbol
        System.out.println("Elemento que es raíz del árbol:");
        lis.vaciar();
        lis.insertar('A', 1);
        System.out.println(lis.toString());        
        System.out.println("Se espera false: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        llenarArbol(arbol);
        
        //Elemento que que está en medio de hermanos con hijos
        System.out.println("Elemento que que está en medio de hermanos con hijos:");
        lis.vaciar();
        lis.insertar('C', 1);
        System.out.println(lis.toString());        
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //Una lista con todos las hojas del arbol
        System.out.println("Una lista con todos las hojas del arbol:");
        lis.vaciar();
        lis.insertar('E', 1);
        lis.insertar('F', 1);
        lis.insertar('S', 1);
        lis.insertar('H', 1);
        lis.insertar('I', 1);
        lis.insertar('C', 1);        
        System.out.println(lis.toString());        
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
        
        //La lista anterior pero con un elemento añadido que no pertenece a la lista
    }
    public static void llenarArbol(ArbolGen arb){
        arb.insertar('A', 0);
        arb.insertar('B', 'A');
        arb.insertar('C', 'A');
        arb.insertar('D', 'A');
        arb.insertar('E', 'B');        
        arb.insertar('F', 'B');        
        arb.insertar('G', 'B'); 
        arb.insertar('H', 'D'); 
        arb.insertar('I', 'D'); 
        arb.insertar('S', 'G'); 
    }
    
}
