/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Lineales.Dinamicas;

import lineales.dinamicas.*;

/**
 * 
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elElemento) {
        //Método para poner en el fin de la cola el nuevoelemento
        Nodo nuevoNodo = new Nodo(elElemento, null);

        if (this.fin != null) {//Si no está vacío
            this.fin.setEnlace(nuevoNodo);//Se enlaza el nuevo nodo creado
            this.fin = nuevoNodo;//El nuevo nodo es el fin de la cola
        } else {//Si está vacío
            this.frente = nuevoNodo;//Frente y Cola tienen el mismo nodo
            this.fin = nuevoNodo;
        }        

        return true;//Siempre retornará true
    }
    public Object obtenerFrente(){
        //Método para obtener el frente de la cola
        Object retornar=null;
        if(this.frente!=null){
            retornar=this.frente.getElem();
        }
        return retornar;
    }

    public boolean sacar() {
        //Quitar el nodo que está en el frente de la cola, si no está vacía
        boolean exito = false;
        if (this.frente != null) {
            this.frente = this.frente.getEnlace();
            exito=true;
            if (this.frente == null)//Si la cola queda vacía, fin queda null
            {
                this.fin = null;
            }
        }
        return exito;
    }
    public boolean esVacia(){
        //Método que pregunta si la cola no contiene elementos
        boolean vacio=true;
        if(this.fin!=null){
            vacio=false;
        }
        return vacio;
    }
    public void vaciar(){
        //Método que quita todos los elementos de la cola
        this.frente=null;
        this.fin=null;
    }
    public Cola clone() {
        //Método que clona los elementos de la cola en un nuevo objeto Cola
        Cola colaNueva=new Cola();
        Nodo auxCola;
        Nodo auxCopia;

        if (!this.esVacia()) {//Si no está vacía
            //Copiar el primer nodo
            auxCola = this.frente;
            Nodo nuevoNodo = new Nodo(auxCola.getElem(), null);
            colaNueva.frente=nuevoNodo;
            auxCopia = nuevoNodo;
            while (auxCola.getEnlace() != null) {//Recorrer todos los demás nodos
                auxCola = auxCola.getEnlace();
                nuevoNodo = new Nodo(auxCola.getElem(), null);
                auxCopia.setEnlace(nuevoNodo);
                auxCopia = nuevoNodo;
            } 
            colaNueva.fin=nuevoNodo;//Setear el fin
        }

        return colaNueva;
    }

    @Override
    public String toString() {
        //Método para testear, que muestra los elementos de la cola desde el frente hasta el fin
        String cadena = "[";
        Nodo aux = this.frente;
        while (aux != null) {
            cadena = cadena + aux.getElem() + " ";
            aux = aux.getEnlace();
        }
        cadena=cadena.trim()+"]";
        return cadena;
    }
}
