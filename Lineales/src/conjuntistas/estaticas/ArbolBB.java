/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas.estaticas;

import jerarquicas.dinamicas.NodoArbol;
import lineales.dinamicas.Lista;

/**
 *
 * @author ulise
 */
public class ArbolBB {

    private NodoArbol raiz;

    public ArbolBB() {
        raiz = null;
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece;

        pertenece = pertenece(elem, this.raiz);

        return pertenece;
    }

    private boolean pertenece(Comparable elemento, NodoArbol n) {
        boolean pertenece = false;
        if (n != null) {
            if (n.getElem().equals(elemento)) {
                pertenece = true;
            } else {
                if (elemento.compareTo(n.getElem()) > 0) {
                    pertenece = pertenece(elemento, n.getDerecho());
                } else {
                    pertenece = pertenece(elemento, n.getIzquierdo());
                }
            }
        }
        return pertenece;
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoArbol(elemento, null, null);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoArbol n, Comparable elemento) {
        boolean exito = true;

        if ((elemento.compareTo(n.getElem()) == 0)) {
            exito = false;
        } else if (elemento.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elemento);
            } else {
                n.setIzquierdo(new NodoArbol(elemento, null, null));
            }
        } else if (n.getDerecho() != null) {
            exito = insertarAux(n.getDerecho(), elemento);
        } else {
            n.setDerecho(new NodoArbol(elemento, null, null));
        }
        return exito;
    }

    public Lista listar() {
        Lista lis = new Lista();

        listar(lis, this.raiz);

        return lis;
    }

    private void listar(Lista lista, NodoArbol n) {
        //Recorrido Inorden
        if (n != null) {
            listar(lista, n.getIzquierdo());
            lista.insertar(n.getElem(), lista.longitud() + 1);
            listar(lista, n.getDerecho());
        }

    }

    public Comparable minimoElem() {
        Comparable elem;

        elem = minimoElem(this.raiz);

        return elem;
    }

    private Comparable minimoElem(NodoArbol n) {
        Comparable elem = null;
        if (n != null) {
            if (n.getIzquierdo() == null) {
                elem = (Comparable) n.getElem();
            } else {
                elem = minimoElem(n.getIzquierdo());
            }
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem;

        elem = maximoElem(this.raiz);

        return elem;
    }

    private Comparable maximoElem(NodoArbol n) {
        Comparable elem = null;
        if (n != null) {
            if (n.getDerecho() == null) {
                elem = (Comparable) n.getElem();
            } else {
                elem = minimoElem(n.getDerecho());
            }
        }
        return elem;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        Lista lis = new Lista();
        listarRango(lis, elemMinimo, elemMaximo, this.raiz);
        return lis;
    }

    private void listarRango(Lista lista, Comparable min, Comparable max, NodoArbol n) {
        if (n != null) {
            if (min.compareTo(n.getElem()) == 0) {//Comenzar recorrido inorden
                lista.insertar(n.getElem(), lista.longitud() + 1);
            } else if (min.compareTo(n) < 0) {
                listarRango(lista, min, max, n.getIzquierdo());
                lista.insertar(n.getElem(), lista.longitud() + 1);
                //Listar todo el recorrido inorden del subArbol derecho
                listarSubDerecho(lista,n.getDerecho(),max);

            } else if (min.compareTo(n) > 0) {
                if (n.getDerecho() != null) {
                    listarRango(lista, min, max, n.getDerecho());
                }
            }
        }
    }
    
    private void listarSubDerecho(Lista lis, NodoArbol n,Comparable maximo){
        if(n!=null && maximo.compareTo(n.getElem())<0){
            listarSubDerecho(lis,n.getIzquierdo(),maximo);
            lis.insertar(n.getElem(), lis.longitud() + 1);
            listarSubDerecho(lis,n.getDerecho(),maximo);
        }
    }
}
