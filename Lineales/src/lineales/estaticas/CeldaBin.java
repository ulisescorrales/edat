/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.estaticas;

/**
 *
 * @author ulises
 */
public class CeldaBin {

    private Object elem;
    private int izquierdo;
    private int derecho;
    private boolean enUso;

    public CeldaBin(Object elemento, int izq, int der) {
        this.elem = elemento;
        this.izquierdo = izq;
        this.derecho = der;
    }

    public Object getElem() {
        return elem;
    }

    public int getIzquierdo() {
        return izquierdo;
    }

    public int getDerecho() {
        return derecho;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setIzquierdo(int izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(int derecho) {
        this.derecho = derecho;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    public boolean enUso() {
        return enUso;
    }

}
