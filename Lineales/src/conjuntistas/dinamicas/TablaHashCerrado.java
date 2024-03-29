/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

import java.util.Arrays;
import lineales.dinamicas.Lista;

/**
 *
 * @author ulises.corrales
 */
public class TablaHashCerrado {

    private static final int TAMANIO = 10;
    private CeldaHash[] hash;   
    int cant;
    
    public TablaHashCerrado() {
        int i=0;
        cant=0;
        hash = new CeldaHash[TAMANIO];
        for(i=0;i<TAMANIO;i++){
            hash[i]=new CeldaHash();                    
        }
    }
    
    public boolean insertar(Object elemento) {
        boolean exito=true;
        int pos=elemento.hashCode()%TAMANIO;
        int intentos=0;
        
        while(hash[pos].getEstado()!=1 && intentos<TAMANIO){
            //recalcular
            pos=elemento.hashCode()%TAMANIO;
            intentos++;
        }
        
        return exito;
    }

    /*public boolean eliminar(Object buscado) {
        //calcula posicion inicial e incremento
        int pos=buscado.hashCode()%TAMANIO;
        int incremento=reHash(buscado)%TAMANIO;
        
        boolean encontrado=false;
        int intento=1;
        //busca el elemento hasta encontrarlo o encontrar una celda vacía
        //o para despues de TAM intentos
        
        while(!encontrado && intento<TAMANIO && this.hash[pos].getEstado()!=-1){
            if(this.hash[pos].getEstado()==1){
                encontrado=this.hash[pos].getElem()==buscado;
                if(encontrado){
                    //Si lo encuentra lo marca y para el ciclo
                    this.hash[pos].setEstado(0);
                    this.cant--;
                }
            }
            pos=(pos+intento*incremento)%TAMANIO;
            intento++;
        }
        return encontrado;
    }

    public boolean pertenece(Object elemento) {
        boolean pertenece=false;
        int intentos=0;
        int pos;
        
        do{
            pos= hashCode()+(intentos-1)*reHash(elemento);
            if(hash[pos].getElem()==elemento){
                pertenece=true;
            }     
            intentos++;
        }while(intentos<TAMANIO && !pertenece);
        
        return pertenece;
        
    }
    //Diccionario: relacion uno a uno y clave -dato- Se puede usar diccionario en AVL o hash
    //Mapeo: relacion uno a varios, un numero con el conjunto del rango. Ej alumno-materia-carrera- Hash map--Has set
    //No usar trim(solo AVL)
    
    public Lista listar() {
        Lista lis = new Lista();
        int i;
        for (i = TAMANIO - 1; i >= 0; i--) {
            lis.insertar(hash[i].getElem(), 1);
        }
        return lis;
    }
    
    public boolean esVacia(){
        boolean vacio=true;
        int i=0;
        
        while(!vacio && i<TAMANIO){
            if(hash[i].getEstado()==1){
                vacio=false;
            }
            i++;
        }
        return vacio;
    }
*/
}
