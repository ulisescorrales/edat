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
        if (elemMinimo.compareTo(elemMaximo) <= 0) {
            listarRango(lis, elemMinimo, elemMaximo, this.raiz);
        }
        return lis;
    }   
    private void listarRango(Lista lista, Comparable min, Comparable max, NodoArbol n) {
        //Preorden inverso, se evitar el orden n de insertar en lista
        if (n != null) {
            if (max.compareTo(n.getElem()) == 0) {
                //No seguir a la derecha
                lista.insertar(n.getElem(), 1);
            } else if (max.compareTo(n.getElem()) > 0) {
                listarRango(lista, min, max, n.getDerecho());
                if (min.compareTo(n.getElem()) <= 0) {
                    lista.insertar(n.getElem(), 1);
                }
            }
            if (min.compareTo(n.getElem()) < 0) {
                listarRango(lista, min, max, n.getIzquierdo());
            }
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
        boolean exito;
        exito = eliminar(elem, this.raiz, null);
        return exito;
    }

    private boolean eliminar(Comparable elem, NodoArbol n, NodoArbol padre) {
        boolean exito = false;
        if (n != null) {
            if (n.getElem().equals(elem)) {
                exito = true;//El elemento existe

                if (n.getIzquierdo() != null && n.getDerecho() != null) {//Si tiene ambos hijos
                    //Usar el candidato A: el mayor del subárbol izquierdo
                    NodoArbol aux = n.getIzquierdo();//Aux se va a ubicar en el candidato A
                    NodoArbol padreAux = n.getIzquierdo();//Padre de aux para setear su HD en null

                    while (aux.getDerecho() != null) {//Buscar el candidato A(máximo del subárbol izquierdo)
                        if (padreAux != aux) {
                            padreAux = padreAux.getDerecho();
                        }
                        aux = aux.getDerecho();
                    }
                    //Setear los hijos del nuevo padre
                    if (n.getIzquierdo() != aux) {//Si aux itera aunque sea una vez hacia la derecha se setea el HD                
                        aux.setIzquierdo(n.getIzquierdo());
                    }
                    aux.setDerecho(n.getDerecho());
                    //setear el padre del padre
                    setPadre(n, aux, padre);
                    if (padreAux != aux) {
                        padreAux.setDerecho(null);//Desapuntar el nodo aux por su padre                    
                    }

                } else if (n.getIzquierdo() == null && n.getDerecho() != null) {//HD no es nulo, HI es nulo
                    setPadre(n, n.getDerecho(), padre);
                } else if (n.getDerecho() == null && n.getIzquierdo() != null) {//HD es nulo, HI no es nulo
                    setPadre(n, n.getIzquierdo(), padre);
                } else {//Es hoja entonces se elimina directamente      
                    setPadre(n, null, padre);
                }
            } else if (elem.compareTo(n.getElem()) < 0) {//Si elemento del nodo es mayor al elemento a eliminar
                exito = eliminar(elem, n.getIzquierdo(), n);
            } else {//Si elem es menor a elem
                exito = eliminar(elem, n.getDerecho(), n);
            }
        }
        return exito;
    }

    private void setPadre(NodoArbol elemento, NodoArbol nuevo, NodoArbol p) {
        if (this.raiz != elemento) {
            //elemento queda sin ser apuntado y se elimina
            if (p.getIzquierdo() == elemento) {//Si el hijo es el izquierdo
                p.setIzquierdo(nuevo);
            } else {//sino es el derecho
                p.setDerecho(nuevo);
            }
        } else {//Si el elemento a borrar es una raíz                       
            this.raiz = nuevo;//Setear la nueva raíz
        }
    }
}
