/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jerarquicas.dinamicas;
import jerarquicas.dinamicas.ArbolGen;
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
        Lista lis=new Lista();
        
        llenarArbol(arbol); 
        System.out.println("Árbol a testear: ");
        System.out.println(arbol.toString());
        
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
        
        //Arbol con un solo elemento igual a lista
        System.out.println("Arbol con un solo elemento:");
        arbol.vaciar();
        arbol.insertar('A', 0);
        lis.vaciar();
        lis.insertar('A', 1);
        System.out.println(lis.toString());
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));        
        
        arbol.vaciar();
        llenarArbol(arbol);                
        
        
        //Una lista con todos las hojas del arbol
        System.out.println("Una lista con todos las hojas del arbol:");
        lis.vaciar();
        lis.insertar('E', 1);
        lis.insertar('F', 1);
        lis.insertar('S', 1);        
        lis.insertar('J', 1);
        lis.insertar('C', 1);
        lis.insertar('I', 1);
        lis.insertar('K', 1);
        System.out.println(lis.toString());        
        System.out.println("Se espera true: "+arbol.sonFrontera(lis));
        System.out.println("--------");
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
