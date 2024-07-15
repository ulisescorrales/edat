/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.estaticas;

/**
 *
 * @author Corrales Ulises FAI-3350
 */
public class Cola {

    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    public Cola() {
        //Constructor
        this.arreglo = new Object[TAMANIO];//El tamaño real del objeto es TAMANIO-1
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elElemento) {
        //Método que coloca un elemento en la última posición, de forma circular en el arreglo       
        boolean exito = false;

        if ((this.fin + 1) % TAMANIO != this.frente) {//Si no está lleno (se reserva un espacio vacío si está lleno)
            this.arreglo[this.fin] = elElemento;
            this.fin = (this.fin + 1) % TAMANIO;//Si se acaba el espacio, comienza al principio del arreglo
            exito = true;
        }
        return exito;
    }

    public boolean sacar() {
        //Método que quita el frente de la Cola
        boolean exito = false;

        if (this.fin != this.frente) {//Si no está vacía
            this.arreglo[frente] = null;
            this.frente = (this.frente + 1) % TAMANIO;
            exito = true;
        }

        return exito;
    }

    public Object obtenerFrente() {
        //Método que obtiene el frente de la Cola
        Object retornar = null;
        if (this.frente != this.fin) {//Si no está vacía
            retornar = this.arreglo[this.frente];
        }
        return retornar;
    }

    public void vaciar() {
        //Método que vacia la Cola
        this.arreglo = new Object[TAMANIO];
        /*El objeto que ya no se apunta
        lo elimina el recolector de basura de la memoria*/
        this.frente = 0;
        this.fin = 0;
    }

    public boolean esVacia() {
        //Método que comprueba si la Cola es vacía
        boolean vacio = true;
        if (this.frente != this.fin) {//Si fin y frente coinciden
            vacio = false;
        }
        return vacio;
    }

    @Override
    public Cola clone() {
        //Método que clona y crea un objeto nuevo con el contenido del arreglo
        Cola colaNueva = new Cola();
        colaNueva.arreglo = this.arreglo.clone();
        colaNueva.fin = this.fin;
        colaNueva.frente = this.frente;

        return colaNueva;
    }

    @Override
    public String toString() {
        //Método para testear, devuelve un String con el contenido desde el frente hasta el final
        String cadena = "[";
        int auxFrente = this.frente;
        while (auxFrente % TAMANIO != this.fin) {//Si no está vacía            
            cadena = cadena + this.arreglo[auxFrente] + " ";
            auxFrente = (auxFrente + 1) % TAMANIO;
        }
        cadena = cadena.trim() + "]";
        return cadena;
    }
}
