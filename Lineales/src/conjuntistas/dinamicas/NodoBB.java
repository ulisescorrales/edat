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
public class NodoBB {
            
    private Comparable elem;
    private NodoBB izq;
    private NodoBB der;    

    public NodoBB(Comparable elem, NodoBB izq, NodoBB der,int alt){
        this.elem = elem;
        this.izq = izq;
        this.der = der;        
    }
    public NodoBB(Comparable elem, NodoBB izq, NodoBB der){
        this.elem = elem;
        this.izq = izq;
        this.der = der;        
    }

    public Comparable getElem() {
        return elem;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public NodoBB getIzquierdo() {
        return izq;
    }

    public void setIzquierdo(NodoBB izq) {
        this.izq = izq;
    }

    public NodoBB getDerecho() {
        return der;
    }

    public void setDerecho(NodoBB der) {
        this.der = der;
    }   
}
