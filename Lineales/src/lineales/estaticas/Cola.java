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
        this.arreglo = new Object[TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elElemento) {
        boolean exito = false;                
        
        if ((this.fin+1)%TAMANIO !=this.frente) {//Si no está lleno
            this.arreglo[this.fin] = elElemento;
            this.fin= (this.fin+1)%TAMANIO;
            exito = true;            
        }
        return exito;
    }

    public boolean sacar() {
        boolean exito = false;

        if (this.fin != this.frente) {//Si no está vacía
            this.arreglo[frente] = null;
            this.frente=(this.frente+1)%TAMANIO;
            exito = true;
        }

        return exito;
    }
    public Object obtenerFrente(){
        Object retornar=null;
        if(this.frente!=this.fin){//Si no está vacía
            retornar=this.arreglo[this.frente];
        }
            return retornar;
        }

    public void vaciar(){
        this.arreglo=new Object[TAMANIO];
        this.frente=0;
        this.fin=0;
    }

    public boolean esVacia() {
        boolean vacio = true;
        if (this.frente != this.fin) {
            vacio = false;
        }
        return vacio;
    }
    @Override
    public Cola clone(){
        Cola colaNueva= new Cola();
        colaNueva.arreglo=this.arreglo.clone();
        colaNueva.fin=this.fin;
        colaNueva.frente=this.frente;        
        
        return colaNueva;
    }
    
    @Override
    public String toString(){
        String cadena="[";
        int auxFrente=this.frente;        
        while(auxFrente!=this.fin){//Si no está vacía
            cadena= cadena+this.arreglo[auxFrente]+" ";
            auxFrente++;
            if(auxFrente==TAMANIO){
                auxFrente=0;
            }
        }
        cadena=cadena.trim()+"]";
        return cadena;
    }
}
