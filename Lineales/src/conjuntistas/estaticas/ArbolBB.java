/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas.estaticas;

import jerarquicas.dinamicas.NodoArbol;
import lineales.dinamicas.Lista;

/**
 *
 * @author ulise
 */
public class ArbolBB {

    private NodoArbol raiz;

    public ArbolBB() {
        raiz = null;
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece;

        pertenece = pertenece(elem, this.raiz);

        return pertenece;
    }

    private boolean pertenece(Comparable elemento, NodoArbol n) {
        boolean pertenece = false;
        if (n != null) {
            if (n.getElem().equals(elemento)) {
                pertenece = true;
            } else {
                if (elemento.compareTo(n.getElem()) > 0) {
                    pertenece = pertenece(elemento, n.getDerecho());
                } else {
                    pertenece = pertenece(elemento, n.getIzquierdo());
                }
            }
        }
        return pertenece;
    }

    public boolean insertar(Comparable elemento) {
        //Método que inserta un elemento en el ABB, no acepta elementos repetidos
        boolean exito = true;
        if (this.raiz == null) {//Si el árbol es vacío
            this.raiz = new NodoArbol(elemento, null, null); //Setear la raíz
        } else {
            exito = insertarAux(this.raiz, elemento);//Recorrer recursivamente
        }
        return exito;
    }

    private boolean insertarAux(NodoArbol n, Comparable elemento) {
        boolean exito = true;

        if ((elemento.compareTo(n.getElem()) == 0)) {//Si ya existe el elemento entonces
            exito = false;
        } else if (elemento.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elemento);
            } else {
                n.setIzquierdo(new NodoArbol(elemento, null, null));
            }
        } else if (n.getDerecho() != null) {
            exito = insertarAux(n.getDerecho(), elemento);
        } else {
            n.setDerecho(new NodoArbol(elemento, null, null));
        }
        return exito;
    }

    public Lista listar() {
        Lista lis = new Lista();

        listar(lis, this.raiz);

        return lis;
    }

    private void listar(Lista lista, NodoArbol n) {
        //Recorrido Inorden
        if (n != null) {
            listar(lista, n.getIzquierdo());
            lista.insertar(n.getElem(), lista.longitud() + 1);
            listar(lista, n.getDerecho());
        }

    }

    public Comparable minimoElem() {
        Comparable elem;

        elem = minimoElem(this.raiz);

        return elem;
    }

    private Comparable minimoElem(NodoArbol n) {
        Comparable elem = null;
        if (n != null) {
            if (n.getIzquierdo() == null) {
                elem = (Comparable) n.getElem();
            } else {
                elem = minimoElem(n.getIzquierdo());
            }
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem;

        elem = maximoElem(this.raiz);

        return elem;
    }

    private Comparable maximoElem(NodoArbol n) {
        /*Método que busca el máximo elemento recorriendo por la derecha
        el árbol hasta antes de llegar a null*/
        Comparable elem = null;
        if (n != null) {
            if (n.getDerecho() == null) {
                elem = (Comparable) n.getElem();
            } else {
                elem = maximoElem(n.getDerecho());
            }
        }
        return elem;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        //Casos a tener en cuenta a partir de la raiz:
        //min y max pueden estar en el subarbol izquierdo y derecho respectivamente
        //min y max pueden estar en el subarbol izquierdo
        //min y max pueden estar en el subarbol derecho
        //min y max pueden no estar en el arbol (existen elementos mayores a min y menores a max)
        //min y max coinciden
        //

        Lista lis = new Lista();
        listarRango(lis, elemMinimo, elemMaximo, this.raiz);
        return lis;
    }

    private void listarRango(Lista lista, Comparable min, Comparable max, NodoArbol n) {
        if (n != null) {
            if (min.compareTo(n.getElem()) == 0) {//Recorrer hasta encontrar el min o encontrar un nodo nulo (min no se encuentra en el arbol
                lista.insertar(n.getElem(), lista.longitud() + 1);
                listarSubDerechoMin(lista, min, max, n.getDerecho());//Listar subarbolDerecho, puede contener el elemento máximo
            } else if (min.compareTo(n.getElem()) < 0) {//Si el nodo es mayor a min, ir por el izquierdo
                listarRango(lista, min, max, n.getIzquierdo());
                lista.insertar(n.getElem(), lista.longitud() + 1);//Listar los nodos que sean mayores a min

                listarSubDerecho(lista, max, n.getDerecho());/*Comenzar recorrido inorden 
                en el subarbol derecho hasta que n >= maximo (=inserta, > no inserta)*/

            } else if (min.compareTo(n.getElem()) > 0) {//Si el nodo es menor a min, ir por el derecho
                //Los nodos menores a min no se listan
                listarRango(lista, min, max, n.getDerecho());
            }
        }
    }

    private void listarRango2(Lista lista, Comparable min, Comparable max, NodoArbol n) {
        if (n != null) {
            if (min.compareTo(n.getElem()) <= 0 && max.compareTo(n.getElem()) >= 0) {//Si el elemento se encuentra en el rango(mayor o igual a minimo)
                lista.insertar(n.getElem(), 1);//Insertar desde el principio de la lista
                listarRango2(lista, min, max, n.getIzquierdo());
            } else if (min.compareTo(n.getElem()) > 0) {//Si el elemento es menor al mínimo, ir por la derecha
                listarRango2(lista, min, max, n.getDerecho());
            } else if (min.compareTo(n.getElem()) < 0) {//Si el elemento es mayor al mínimo, ir por la izquierda(no se lista ya que es mayor al máximo)
                listarRango2(lista, min, max, n.getIzquierdo());
            }
        }
    }

    private void listarSubDerecho(Lista lis, Comparable maximo, NodoArbol n) {
        if (n != null && maximo.compareTo(n.getElem()) >= 0) {
            listarSubDerecho(lis, maximo, n.getIzquierdo());
            lis.insertar(n.getElem(), lis.longitud() + 1);
            listarSubDerecho(lis, maximo, n.getDerecho());
        }
    }

    private void listarSubDerechoMin(Lista lis, Comparable minimo, Comparable maximo, NodoArbol n) {
        //Recorrer subarbol derecho, si en este subarbol se encuentra el maximo elemento, debe terminar el recorrido
        if (n != null) {

        }
    }

    @Override
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
                    cadena = cadena + "; HI: " + auxNodo.getIzquierdo().getElem();
                }
                if (auxNodo.getDerecho() != null) {
                    cadena = cadena + "; HD: " + auxNodo.getDerecho().getElem() + "\n";
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

    public boolean eliminar(Comparable elem) {
        //Enviar el padre como parámetro, no volver a buscarlo
        boolean exito = false;
        exito = eliminar(elem, this.raiz, null);
        return exito;
    }

    private boolean eliminar(Comparable elem, NodoArbol n, NodoArbol padre) {
        boolean exito = false;
        if (n != null) {
            if (n.getElem().equals(elem)) {
                exito = true;
                //se reemplaza por el candidato A
                
            } else if (elem.compareTo(n.getElem()) < 0) {//Si elem es mayor a elem
                exito = eliminar(elem, n.getIzquierdo(), n);                                                    
            } else {//Si elem es menor a elem
                exito = eliminar(elem, n.getDerecho(), n);
            }
        }
        return false;
    }
}
