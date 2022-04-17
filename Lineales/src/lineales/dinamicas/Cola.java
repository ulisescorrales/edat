/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author Corrales Ulises FAI-3350
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elElemento) {
        Nodo nuevoNodo = new Nodo(elElemento, null);

        if (this.fin != null) {//Si no está vacío
            this.fin.setEnlace(nuevoNodo);
            this.fin = nuevoNodo;
        } else {//Si está vacío
            this.frente = nuevoNodo;
            this.fin = nuevoNodo;
        }
        this.fin = nuevoNodo;

        return true;
    }
    public Object obtenerFrente(){
        Object retornar=null;
        if(this.frente!=null){
            retornar=this.frente.getElem();
        }
        return retornar;
    }

    public boolean sacar() {
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
        boolean vacio=true;
        if(this.fin!=null){
            vacio=false;
        }
        return vacio;
    }
    public void vaciar(){
        this.frente=null;
        this.fin=null;
    }
    public Cola clone() {
        Cola colaNueva=new Cola();
        Nodo auxCola;
        Nodo auxCopia;

        if (this.esVacia() != true) {
            auxCola = this.frente;
            Nodo nuevoNodo = new Nodo(auxCola.getElem(), null);
            colaNueva.frente=nuevoNodo;
            auxCopia = nuevoNodo;
            while (auxCola.getEnlace() != null) {
                auxCola = auxCola.getEnlace();
                nuevoNodo = new Nodo(auxCola.getElem(), null);
                auxCopia.setEnlace(nuevoNodo);
                auxCopia = nuevoNodo;
            } 
            colaNueva.fin=nuevoNodo;
        }

        return colaNueva;
    }

    @Override
    public String toString() {
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
