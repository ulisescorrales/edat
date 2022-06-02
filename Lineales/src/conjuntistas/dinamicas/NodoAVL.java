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
public class NodoAVL {

    private Object elem;
    private NodoAVL izq;
    private NodoAVL der;
    private int altura;

    public NodoAVL(Object elem, NodoAVL izq, NodoAVL der,int alt) {
        this.elem = elem;
        this.izq = izq;
        this.der = der;
        this.altura=alt;
    }
    public NodoAVL(Object elem, NodoAVL izq, NodoAVL der) {
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

    public NodoAVL getIzquierdo() {
        return izq;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izq = izq;
    }

    public NodoAVL getDerecho() {
        return der;
    }

    public void setDerecho(NodoAVL der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

}
