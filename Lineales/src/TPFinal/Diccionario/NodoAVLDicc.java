/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Diccionario;


/**
 *
 * @author Corrales Ulises
 */
public class NodoAVLDicc {
    private Comparable clave;    
    private int altura;
    private NodoAVLDicc hijoIzquierdo;
    private NodoAVLDicc hijoDerecho;
    
    private Object dato;

    //Constructor con altura
    public NodoAVLDicc(Comparable clave, int altura, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho, Object dato) {
        this.clave = clave;
        this.altura = altura;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.dato = dato;
    }
    //Constructor sin altura
    public NodoAVLDicc(Comparable clave, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho, Object dato) {
        this.clave = clave;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.dato = dato;        
    }
    
    public Comparable getClave() {
        return this.clave;
    }   
    public Object getDato(){
        return this.dato;
    }
    public boolean setDato(Object unDato){
        this.dato=unDato;
        return true;
    }

    public NodoAVLDicc getIzquierdo() {
        return this.hijoIzquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izq) {
        this.hijoIzquierdo = izq;
    }

    public NodoAVLDicc getDerecho() {
        return this.hijoDerecho;
    }

    public void setDerecho(NodoAVLDicc der) {
        this.hijoDerecho = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
