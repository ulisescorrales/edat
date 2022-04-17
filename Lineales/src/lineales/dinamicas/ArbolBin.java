/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.dinamicas;

/**
 *
 * @author ulise
 */
public class ArbolBin {
    private NodoArbol raiz;
    
    public ArbolBin(){
        this.raiz=null;
    }
    public boolean insertar(Object elemNuevo, Object elemPadre, boolean lugar){
        boolean exito=true;
            if(this.raiz==null){
                this.raiz= new NodoArbol(elemNuevo,null,null);
            }else{
                NodoArbol aux=obtenerNodo(this.raiz,elemPadre);//Localizar nodo padre
                if(lugar==false && aux.getIzquierdo()==null){
                    aux.setIzquierdo(new NodoArbol(elemNuevo,null,null));
                }else if (lugar==true && aux.getDerecho()==null){
                    aux.setDerecho(new NodoArbol(elemNuevo,null,null));
                }else{
                    exito=false;
                }
            }
        return exito;
    }
    public boolean esVacio(){
        boolean vacio=false;
            if(this.raiz==null){
                vacio=true;
            }
        return vacio;
    }
    public Object padre(Object elemento){
        Object retornar=null;
            
        return retornar;
    }
    private NodoArbol obtenerNodo(NodoArbol n, Object buscado){
        NodoArbol encontrado=null;
        if(n!=null){
            if(n.getElem().equals(buscado)){
                encontrado=n;                                
            }else{
                encontrado=obtenerNodo(n.getIzquierdo(),buscado);
                if(encontrado==null){
                    encontrado=obtenerNodo(n.getDerecho(),buscado);
                }
            }
        }
        return encontrado;
    }
}
