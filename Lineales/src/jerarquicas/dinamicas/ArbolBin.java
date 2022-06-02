/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas.dinamicas;

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

    public int altura() {
        int altura=-1;
        if(this.raiz!=null){
            altura=altura(this.raiz);
        }
        return altura;
    }

    private int altura(NodoArbol aux) {
        int alt, izquierda = -1, derecha = -1;
        if (aux.getDerecho() == null && aux.getIzquierdo() == null) {//Si es una hoja
            alt = 0;
        } else {
            if (aux.getIzquierdo() != null) {
                izquierda = altura(aux.getIzquierdo());
            }
            if (aux.getDerecho() != null) {
                derecha = altura(aux.getDerecho());
            }
            alt = Math.max(izquierda, derecha) + 1;//Incrementar altura
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
                i = nivel(elemento, aux.getIzquierdo());
            }
            if (i == -1) {
                if (aux.getDerecho() != null) {
                    i = nivel(elemento, aux.getDerecho());
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
        //aux.invertir();
        return aux;
    }

    private void listarPreOrden(NodoArbol auxNodo, Lista lista) {
        if (auxNodo != null) {
            lista.insertar(auxNodo.getElem(), lista.longitud() + 1);
            listarPreOrden(auxNodo.getIzquierdo(), lista);
            listarPreOrden(auxNodo.getDerecho(), lista);
        }
    }

    public Lista listarPosOrden() {
        Lista aux = new Lista();
        listarPosOrden(this.raiz, aux);
        //aux.invertir();
        return aux;
    }

    private void listarPosOrden(NodoArbol auxNodo, Lista lista) {
        if (auxNodo != null) {
            listarPreOrden(auxNodo.getIzquierdo(), lista);
            listarPreOrden(auxNodo.getDerecho(), lista);
            lista.insertar(auxNodo.getElem(), lista.longitud() + 1);
        }
    }

    public Lista listarInorden() {
        Lista aux = new Lista();
        listarInorden(this.raiz, aux);
        aux.invertir();
        return aux;
    }

    private void listarInorden(NodoArbol auxNodo, Lista lista) {
        if (auxNodo != null) {
            listarPreOrden(auxNodo.getIzquierdo(), lista);
            lista.insertar(auxNodo.getElem(), lista.longitud() + 1);
            listarPreOrden(auxNodo.getDerecho(), lista);

        }
    }

    public ArbolBin clone() {
        ArbolBin nuevoArbol = new ArbolBin();
        NodoArbol nuevoNodo = new NodoArbol(this.raiz.getElem(), null, null);
        nuevoArbol.raiz = nuevoNodo;

        clone(nuevoNodo, this.raiz);

        return nuevoArbol;
    }

    private void clone(NodoArbol aux, NodoArbol padre) {
        NodoArbol nuevoNodo;
        if (padre.getIzquierdo() != null) {
            nuevoNodo = new NodoArbol(padre.getIzquierdo().getElem(), null, null);
            aux.setIzquierdo(nuevoNodo);
            clone(aux.getIzquierdo(), padre.getIzquierdo());//
        }
        if (padre.getDerecho() != null) {
            nuevoNodo = new NodoArbol(padre.getDerecho().getElem(), null, null);
            aux.setDerecho(nuevoNodo);
            clone(aux.getDerecho(), padre.getDerecho());//
        }
    }

    public void vaciar() {
        this.raiz = null;
    }

    public boolean esVacio() {
        boolean vacio = false;
        if (this.raiz == null) {
            vacio = true;
        }
        return vacio;
    }
    //Agregar al árbol binario la operación frontera() que devuelve una lista con todos los elementos almacenados
    //en las hojas del árbol listadas de izquierda a derecha.

    public Lista frontera() {
        Lista lis = new Lista();
        frontera(this.raiz, lis);
        return lis;
    }

    private void frontera(NodoArbol auxNodo, Lista lista) {
        if (auxNodo.getDerecho() == null && auxNodo.getIzquierdo() == null) { //Si es una hoja, agregar a la lista
            lista.insertar(auxNodo.getElem(), lista.longitud() + 1);//Se lista de izquierda a derecha
        } else {
            if (auxNodo.getIzquierdo() != null) {
                frontera(auxNodo.getIzquierdo(), lista);
            }
            if (auxNodo.getDerecho() != null) {
                frontera(auxNodo.getDerecho(), lista);
            }
        }
    }

    public String toString() {
        String cadena = "";
        cadena = toString(this.raiz, cadena);
        return cadena;
    }

    private String toString(NodoArbol auxNodo, String cadena) {
        if (auxNodo != null) {

            if (auxNodo.getIzquierdo() == null && auxNodo.getDerecho() == null) {
                cadena = "Hoja: " + auxNodo.getElem().toString() + "\n";
            } else {
                cadena = "Padre: " + auxNodo.getElem().toString();

                if (auxNodo.getIzquierdo() != null) {
                    cadena = cadena + ", hijo Izquierdo: " + auxNodo.getIzquierdo().getElem();
                }
                if (auxNodo.getDerecho() != null) {
                    cadena = cadena + ", hijo derecho: " + auxNodo.getDerecho().getElem() + "\n";
                } else {
                    cadena = cadena + "\n";
                }
                if (auxNodo.getIzquierdo() != null) {
                    cadena = cadena + toString(auxNodo.getIzquierdo(), cadena);
                }
                if (auxNodo.getDerecho() != null) {
                    cadena = cadena + toString(auxNodo.getDerecho(), cadena);
                }
            }

        }
        return cadena;
    }
    /*private int cantidadNiveles(){
        int cant;
            
        return cant;
    }
     */
}
