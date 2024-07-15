/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.estaticas;

/**
 *
 * @author Corrales Ulises FAI-3350
 */
public class Pila {

    //Implementación estática de Pila
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 10;

    public Pila() {
        //Constructor
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElem) {
        //Ingresa un elemento sobre el tope
        boolean exito = false;

        if (this.tope + 1 >= this.TAMANIO) {
            //Error: pila llena
            exito = false;
        } else {
            //Pone el elemento en el tope de la pila e incrementa tope
            this.tope++;
            this.arreglo[this.tope] = nuevoElem;
            exito = true;
        }
        return exito;
    }

    public boolean desapilar() {
        //Elimina el último elemento ingresado a la pila
        boolean exito = false;

        if (this.tope > -1) {
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        }
        return exito;
    }

    public Object obtenerTope() {
        //Devuelve el último elemento ingresado a la pila
        Object devolver = null;
        if (this.tope > -1) {
            devolver = this.arreglo[tope];
        }
        return devolver;
    }

    public boolean esVacia() {
        return this.tope < 0;
    }

    public void vaciar() {
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }

    @Override
    public Pila clone() {
        //Clona el objeto
        Pila pilaNueva = new Pila();
        pilaNueva.arreglo = arreglo.clone();
        pilaNueva.tope = this.tope;

        return pilaNueva;
    }

    @Override
    public String toString() {
        /*Método para testeo. Genera una cadena de carácteres
        con los valores del arreglo*/
        String cadena = "";
        int i;
        
        if(this.esVacia()==false){
        for (i = 0; i <= this.tope; i++) {
            cadena = cadena + " " + this.arreglo[i];
        }
        }else{
            cadena="Pila Vacía";
        }        
        return cadena;
    }

}
