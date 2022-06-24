/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

/**
 *
 * @author ulises.corrales
 */
public class TablaHashCerrado {

    private static final int TAMANIO = 10;
    private Object[] arreglo;

    public TablaHashCerrado() {
        arreglo = new Object[TAMANIO];
    }

    public boolean insertar(Object elemento) {

    }

    public boolean eliminar(Object elemento) {

    }

    public boolean pertenece(Object elemento) {

    }

    public Lista listar() {
        Lista lis = new Lista();
        int i;
        for (i = TAMANIO - 1; i >= 0; i--) {
            lis.insertar(arreglo[TAMANIO], 1);
        }
        return lis;
    }
    
    public boolean esVacia(){
        boolean vacio=false;
        int i=0;
        
        while(!vacio && i<TAMANIO){
            if(arreglo[i]!=null && arreglo[i]!=)
            i++;
        }
    }
}
