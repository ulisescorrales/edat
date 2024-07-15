/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author ulise
 */
public class Lista {

    private Nodo cabecera;

    public Lista() {
        cabecera = null;
    }

    public boolean insertar(Object elemento, int posicion) {
        boolean exito = false;
        int pos = posicion - 1;
        int longitud = this.longitud(), i = 1;
        Nodo aux = this.cabecera;
        Nodo nodoNuevo;

        if (posicion <= longitud + 1 && longitud >= 0 && posicion > 0) {//Si existe la posición            
            if (posicion > 1 && posicion <= longitud + 1) {//Si hay que insertar en la mitad de la lista
                while (i < pos) {//Llega hasta la posicion destino - 1
                    aux = aux.getEnlace();
                    i++;
                }
                //Enlazar nuevo nodo al nodo que ocupa la posicion indicada
                nodoNuevo = new Nodo(elemento, aux.getEnlace());
                //enlazar el nodo en posicion-1 al nodo nuevo
                aux.setEnlace(nodoNuevo);
            }
            if (posicion == 1) {//Si hay que insertar en la primera posicion                
                this.cabecera = new Nodo(elemento, this.cabecera);
            }
            //Si la posición a ocupar no supera la longitud de la lista o es un número negativo entonces nunca hay error de lista llena
            exito = true;
        }
        return exito;
    }

    public int longitud() {
        int longitud = 0;
        Nodo aux = this.cabecera;
        while (aux != null) {
            longitud++;
            aux = aux.getEnlace();
        }
        return longitud;
    }

    public Object recuperar(int posicion) {
        int i, pos = posicion - 1;
        Nodo aux;
        Object retornar=null;

        if (posicion > 0 && posicion<=this.longitud()) {
            aux = this.cabecera;
            for(i=1;i<=pos;i++){
                aux=aux.getEnlace();
            }
            retornar=aux.getElem();
        }
        return retornar;
    }

    public int localizar(Object elObjeto) {
        Nodo aux = this.cabecera;
        boolean encontrado = false;
        int contador = 0;
        while (aux != null && !encontrado) {
            if (aux.getElem() == elObjeto) {
                encontrado = true;
            }
            aux = aux.getEnlace();
            contador++;
        }
        if (!encontrado) {
            contador = -1;
        }

        return contador;
    }

    public boolean eliminar(int posicion) {
        int i = 1, pos = posicion - 1, longitud = this.longitud();
        Nodo aux = this.cabecera;
        Nodo aux2;
        boolean exito = false;

        if (posicion >= 1 && posicion <= longitud) {//Comprobar posicion
            if (posicion == 1) {//Si es en la primera posición
                this.cabecera = this.cabecera.getEnlace();
            } else {//Si es una posición mayor a 1 y hasta longitud
                while (i < pos) {//Ubicar aux en posicion-1 de la lista
                    aux = aux.getEnlace();
                    i++;
                }
                aux2 = aux.getEnlace().getEnlace();// nodo en posición-1 se enlaza con nodo en dos posiciones adelante                    
                aux.setEnlace(aux2);
            }
            exito = true;
        }
        return exito;
    }

    public boolean esVacia() {
        boolean vacia = false;
        if (this.cabecera == null) {
            vacia = true;
        }
        return vacia;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    @Override
    public Lista clone() {
        Lista listaNueva = new Lista();
        Nodo aux = this.cabecera;
        Nodo auxCopia;
        Nodo nodoNuevo;

        if (this.cabecera != null) {
            nodoNuevo = new Nodo(aux.getElem(), null);
            listaNueva.cabecera = nodoNuevo;
            auxCopia = nodoNuevo;
            aux = aux.getEnlace();
            while (aux != null) {
                nodoNuevo = new Nodo(aux.getElem(), null);
                auxCopia.setEnlace(nodoNuevo);
                auxCopia = nodoNuevo;
                aux = aux.getEnlace();
            }
        }

        return listaNueva;
    }
    public void invertir(){
        invertir(this.cabecera);
    }
    private Nodo invertir(Nodo nodoAnt){
        
        if(nodoAnt.getEnlace().getEnlace()==null){
            this.cabecera.setEnlace(null);
            this.cabecera=nodoAnt.getEnlace();
            nodoAnt.getEnlace().setEnlace(nodoAnt);            
        }else{
            invertir(nodoAnt.getEnlace()).setEnlace(nodoAnt);            
        }
        return nodoAnt;
        
    }

    @Override
    public String toString() {
        String cadena = "[";
        Nodo aux = this.cabecera;
        while (aux != null) {
            cadena = cadena + aux.getElem() + " ";
            aux = aux.getEnlace();
        }
        cadena = cadena.trim() + "]";
        return cadena;
    }
}
