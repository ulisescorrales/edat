/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;
/**
 *
 * @author ulises.corrales
 */
public class NodoAVL{
    private Object elem;
    private NodoArbol izq;
    private NodoArbol der;
    private int altura;

    public NodoAVL(Object elem, NodoArbol izq, NodoArbol der) {
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
