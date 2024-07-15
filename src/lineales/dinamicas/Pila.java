/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author Corrales Ulises
 */
public class Pila {
//Implementación dinámica de Pila

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object elElemento) {
        //Crea un nodo nuevo delante de la nueva cabecera
        Nodo nuevoNodo = new Nodo(elElemento, this.tope);
        //Actualiza el tope para que apunte al nodo nuevo
        this.tope = nuevoNodo;
        //Nunca hay error de pila llena, entonces devuelve true
        return true;
    }

    public boolean desapilar() {
        //Tope deja de apuntar al último nodo para apuntar a su antecesor        
        boolean exito = true;

        if (this.tope != null) {
            this.tope = this.tope.getEnlace();
        } else {
            exito = false;
        }
        return exito;
    }

    public Object obtenerTope() {
        //Retornar el elemento del nodo que apunta this.tope
        Object retornar = null;
        if (this.tope != null) {
            retornar = this.tope.getElem();
        }
        return retornar;
    }

    public boolean esVacia() {
        boolean vacio = false;
        if (this.tope == null) {
            vacio = true;
        }
        return vacio;
    }

    public void vaciar() {
        //Apuntar a ningún nodo, el recolector de basura elimina automáticamente los objetos creados      
        this.tope = null;
    }

    @Override
    public Pila clone() {
        //Método que clona la pila en un objeto nuevo
        Pila pilaNueva = new Pila();
        Nodo topeAux;//Puntero que apuntará hacia cada nodo de la pila origen
        Nodo auxCopia;//Puntero que apuntará a los nodos recien creados de la pila nueva
        Nodo nodoNuevo;//Nodo copiado

        if (this.esVacia() == false) {
            /*Cuando se clona this.tope, se apunta topeAux al tope del arreglo
            y se apunta el tope de pilaNueva al nodo nuevo como particularidad*/
            topeAux = this.tope;
            nodoNuevo = new Nodo(topeAux.getElem(), null);
            auxCopia = nodoNuevo;
            pilaNueva.tope = nodoNuevo;
            topeAux = topeAux.getEnlace();

            while (topeAux != null) {
                //En los restantes ciclos se sigue la rutina:
                nodoNuevo = new Nodo(topeAux.getElem(), null);//Crear un nodo nuevo
                auxCopia.setEnlace(nodoNuevo);//Con auxCopia se enlaza el nodo anteriormente creado con el que se acabó de crear
                auxCopia = nodoNuevo;//auxCopia es el nodo recién creado
                topeAux = topeAux.getEnlace();//topeAux recorre el arreglo original
            }
        }
        return pilaNueva;
    }

    @Override
    public String toString() {
        //Método para testear el funcionamiento del arreglo
        String cadena = "";        
        
        if (this.tope==null){
            cadena="Pila Vacía";
        }else{
            //Se ubica para recorrer la pila
            Nodo aux=this.tope;
            cadena="[";
            while(aux!=null){
                //Agrega el texto de elem y avanza
                cadena+=aux.getElem().toString();
                aux=aux.getEnlace();
                if(aux!=null){
                    cadena+=",";
                }
            }
            cadena+="]";            
        }        

        return cadena;
    }

}
