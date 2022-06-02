/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas.estaticas;

/**
 *
 * @author ulise
 */
public class ArbolHeapMax {

    private static final int TAMANIO = 20;
    Comparable[] heap;
    int ultimo = 0;

    public ArbolHeapMax() {
        heap = new Comparable[TAMANIO];
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;

        if (this.ultimo == 0) {
            this.heap[1] = elem;//Setear la raíz
            this.ultimo++;
            exito = true;
        } else {
            if (this.ultimo + 1 < TAMANIO) {//Si no sobrepasa el TAMANIO del arreglo
                exito = true;
                this.ultimo++;//
                int auxPadre = ultimo / 2;//Posición del padre del último elem
                int temp = ultimo;//Posición de elem
                this.heap[ultimo] = elem;
                if (this.heap[auxPadre].compareTo(elem) < 0) {//Si el primer padre es menor al insertado                    
                    while (auxPadre != 0 && this.heap[auxPadre].compareTo(elem) < 0) {//Mientras que el padre sea menor y no sea la posición 0                        
                        heap[temp] = heap[auxPadre];
                        heap[auxPadre] = elem;
                        auxPadre = auxPadre / 2;
                        temp = temp / 2;
                    }
                }
            }
        }
        return exito;
    }

    public boolean eliminarCima() {
        boolean exito = false;
        if (this.ultimo > 0) {
            this.heap[1] = this.heap[ultimo];
            this.ultimo--;
            hacerBajar(1);
            exito = true;
        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;

        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                if (posH < this.ultimo) {
                    if (this.heap[posH + 1].compareTo(this.heap[posH]) > 0) {
                        posH++;
                    }
                }
                if (this.heap[posH].compareTo(temp) > 0) {
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

    public boolean esVacio() {
        boolean vacio = false;
        if (ultimo == 0) {
            vacio = true;
        }
        return vacio;
    }

    public Object recuperarCima() {
        return this.heap[ultimo];
    }

    public String toString() {
        String cadena = "";
        int i = 1;
        for (i = 1; i <= ultimo; i++) {
            cadena = cadena + heap[i] + "-->";
            if (2 * i <= ultimo) {
                cadena = cadena + heap[2 * i] + " ";
                if (2 * i + 1 <= ultimo) {
                    cadena = cadena + heap[(2 * i) + 1] + "\n";
                } else {
                    cadena = cadena + "\n";
                }
            } else {
                cadena = cadena + "\n";
            }
        }
        return cadena;
    }

}
