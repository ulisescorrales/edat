/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author Corrales Ulises FAI-3350
 */
public class Nodo {

    private Object elem;
    private Nodo enlace;

    //Constructor
    public Nodo(Object elElem, Nodo elEnlace) {
        this.elem = elElem;
        this.enlace = elEnlace;
    }

    //Modificadores
    public void setElem(Object elElemento) {

        this.elem = elElemento;
    }

    public void setEnlace(Nodo elEnlace) {
        this.enlace = elEnlace;
    }

    //Observadores
    public Object getElem() {
        return this.elem;
    }

    public Nodo getEnlace() {
        return this.enlace;
    }

}
