/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas;

import lineales.dinamicas.Lista;

/**
 *
 * @author ulise
 */
public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, boolean lugar) {
        //true: derecho, false:izquierdo
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            NodoArbol aux = obtenerNodo(this.raiz, elemPadre);//Localizar nodo padre
            if (lugar == false && aux.getIzquierdo() == null) {
                aux.setIzquierdo(new NodoArbol(elemNuevo, null, null));
            } else if (lugar == true && aux.getDerecho() == null) {
                aux.setDerecho(new NodoArbol(elemNuevo, null, null));
            } else {
                exito = false;
            }
        }
        return exito;
    }
    public void vaciar(){
        this.raiz=null;
    }
    public boolean esVacio() {
        boolean vacio = false;
        if (this.raiz == null) {
            vacio = true;
        }
        return vacio;
    }
    public int altura(){
        return altura(this.raiz);
    }
    private int altura(NodoArbol aux){
        int alt=1, izquierda=0,derecha=0;
        if(aux.getDerecho()==null && aux.getIzquierdo()==null){//Si es una hoja
            alt=1;
        }else{
            if(aux.getIzquierdo()!=null){                
                izquierda=altura(aux.getIzquierdo());
            }
            if(aux.getDerecho()!=null){
                derecha=altura(aux.getDerecho());
            }
            alt=Math.max(izquierda, derecha)+alt;
        }
        return alt;
    }

    public Object padre(Object elemento) {
        Object retornar;
        retornar = padre(this.raiz, elemento).getElem();
        return retornar;
    }

    public NodoArbol padre(NodoArbol auxPadre, Object elem) {
        NodoArbol padre = null;
        if (auxPadre.getDerecho().getElem() != null || auxPadre.getIzquierdo().getElem() != null) {
            if (auxPadre.getDerecho().getElem() == elem || auxPadre.getIzquierdo().getElem() == elem) {
                padre = auxPadre;
            } else {
                padre = padre(auxPadre.getIzquierdo(), elem);
                if (padre == null) {
                    padre = padre(auxPadre.getDerecho(), elem);
                }
            }
        }
        return padre;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        NodoArbol encontrado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                encontrado = n;
            } else {
                encontrado = obtenerNodo(n.getIzquierdo(), buscado);
                if (encontrado == null) {
                    encontrado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return encontrado;
    }

    public int nivel(Object elem) {        
        int i;

        i = nivel(elem, this.raiz);

        return i;
    }

    private int nivel(Object elemento, NodoArbol aux) {
        int i = -1;
        if (elemento == aux.getElem()) {
            i = 0;
        } else {
            if (aux.getIzquierdo() != null) {
                i = nivel(elemento,aux.getIzquierdo());
            }
            if (i == -1) {
                if (aux.getDerecho() != null) {
                    i = nivel(elemento,aux.getDerecho());
                }
                if (i != -1) {
                    i++;
                }
            } else {
                i++;
            }
        }
        return i;
    }

    public Lista listarPreOrden() {
        Lista aux = new Lista();
        listarPreOrden(this.raiz, aux);
        aux.invertir();
        return aux;
    }

    private void listarPreOrden(NodoArbol padre, Lista lista) {
        if (padre.getDerecho() == null && padre.getIzquierdo() == null) {//si es una hoja
            lista.insertar(padre.getElem(), 1);
        } else {
            lista.insertar(padre.getElem(), 1);//Inserta al padre
            if (padre.getIzquierdo() != null) {
                listarPreOrden(padre.getIzquierdo(), lista);//Listar hi izquierdo
            }            
            if (padre.getDerecho() != null) {
                listarPreOrden(padre.getDerecho(), lista);//Listar al hijo derecho
            }            
        }
    }

    public Lista listarPosOrden() {
        Lista aux = new Lista();
        listarPosOrden(this.raiz, aux);
        aux.invertir();
        return aux;
    }

    private void listarPosOrden(NodoArbol padre, Lista lista) {
        if (padre.getDerecho() == null && padre.getIzquierdo() == null) {//si es una hoja
            lista.insertar(padre.getElem(), 1);
        } else {
            if (padre.getIzquierdo() != null) {
                listarPosOrden(padre.getIzquierdo(), lista);//Listar hi izquierdo
            }
            if (padre.getDerecho() != null) {
                listarPosOrden(padre.getDerecho(), lista);//Listar al hijo derecho
            }
            lista.insertar(padre.getElem(), 1);//Inserta al padre
        }
    }


    public Lista listarInorden() {
        Lista aux = new Lista();
        listarInorden(this.raiz, aux);
        aux.invertir();
        return aux;
    }

    private void listarInorden(NodoArbol padre, Lista lista) {
        if (padre.getDerecho() == null && padre.getIzquierdo() == null) {//si es una hoja
            lista.insertar(padre.getElem(), 1);
        } else {
            if (padre.getIzquierdo() != null) {
                listarInorden(padre.getIzquierdo(), lista);//Listar primero al hijo izquierdo
            }
            lista.insertar(padre.getElem(), 1);//Luego listar al padre
            if (padre.getDerecho() != null) {
                listarInorden(padre.getDerecho(), lista);//Listar al hijo derecho
            }
        }
    }
    
    public ArbolBin clone(){
        ArbolBin nuevoArbol=new ArbolBin();
        NodoArbol nuevoNodo=new NodoArbol(this.raiz.getElem(),null,null);
        nuevoArbol.raiz=nuevoNodo;
        
        clone(nuevoNodo, this.raiz);
        
        return nuevoArbol;
    }
    private void clone(NodoArbol aux,NodoArbol padre){                        
        NodoArbol nuevoNodo;        
            if(padre.getIzquierdo()!=null){
                nuevoNodo=new NodoArbol(padre.getIzquierdo().getElem(),null,null);
                aux.setIzquierdo(nuevoNodo);
                clone(aux.getIzquierdo(),padre.getIzquierdo());
            }
            if(padre.getDerecho()!=null){
                nuevoNodo=new NodoArbol(padre.getDerecho().getElem(),null,null);
                aux.setDerecho(nuevoNodo);
                clone(aux.getDerecho(),padre.getDerecho());
            }                          
    }
    

    public String toString() {
        Lista aux = this.listarPosOrden();
        return aux.toString();
    }
    /*private int cantidadNiveles(){
        int cant;
            
        return cant;
    }
     */
}
