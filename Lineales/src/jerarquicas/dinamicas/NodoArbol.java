/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas.dinamicas;

/**
 *
 * @author ulise
 */
public class NodoArbol {
    private Object elem;
    private NodoArbol izq;
    private NodoArbol der;

    public NodoArbol(Object elem, NodoArbol izq, NodoArbol der) {
        this.elem = elem;
        this.izq = izq;
        this.der = der;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public NodoArbol getIzquierdo() {
        return izq;
    }

    public void setIzquierdo(NodoArbol izq) {
        this.izq = izq;
    }

    public NodoArbol getDerecho() {
        return der;
    }

    public void setDerecho(NodoArbol der) {
        this.der = der;
    }
    
    
}
