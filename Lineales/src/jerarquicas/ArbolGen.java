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
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object elem, Object padre) {
        boolean exito = false;
        NodoGen nodoPadre;
        NodoGen aux;

        if (this.raiz == null) {
            this.raiz = new NodoGen(elem, null, null);
        } else {
            nodoPadre = obtenerNodo(padre, this.raiz);
            if (nodoPadre != null) {
                exito = true;
                if (nodoPadre.getHijoIzquierdo() == null) {//Si no tiene hijo izquierdo, ponerlo
                    nodoPadre.setHijoIzquierdo(new NodoGen(elem, null, null));
                } else {
                    aux = nodoPadre.getHijoIzquierdo();
                    //Sino recorrer hermanos hasta que el último no tenga hermano derecho
                    while (aux.getHermanoDerecho() != null) {
                        aux = aux.getHermanoDerecho();
                    }
                    aux.setHermanoDerecho(new NodoGen(elem, null, null));
                }
            }
        }
        return exito;
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    public Lista listarPreorden() {
        Lista resultado = new Lista();
        listarPreorden(resultado, this.raiz);
        return resultado;
    }

    private void listarPreorden(Lista lis, NodoGen n) {
        if (n != null) {
            lis.insertar(n.getElem(), lis.longitud() + 1);//visitar el nodo
            NodoGen aux = n.getHijoIzquierdo();
            while (aux != null) {
                listarPreorden(lis, aux);
                aux = aux.getHermanoDerecho();
            }
        }
    }

    public Lista listarInorden() {
        Lista lista = new Lista();
        listarInorden(lista, this.raiz);
        return lista;
    }

    public void listarInorden(Lista lis, NodoGen n) {
        if (n != null) {
            listarInorden(lis, n.getHijoIzquierdo());//Visitar HI
            lis.insertar(n.getElem(), lis.longitud() + 1);//visitar el Padre
            NodoGen aux = n.getHijoIzquierdo();//Preguntar a hermanos derechos si tiene HI
            if (aux != null) {
                while (aux.getHermanoDerecho() != null) {
                    listarInorden(lis, aux.getHermanoDerecho());
                    aux = aux.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista lista = new Lista();
        listarPosorden(lista, this.raiz);
        return lista;
    }

    private void listarPosorden(Lista lis, NodoGen n) {
        if (n != null) {
            NodoGen aux = n.getHijoIzquierdo();
            while (aux != null) {
                listarPosorden(lis, aux);
                lis.insertar(aux.getElem(), lis.longitud() + 1);
                aux = aux.getHermanoDerecho();
            }
        }
    }

    /*public void listarInorden(Lista lis, NodoGen n) {
        if (n != null) {            
            NodoGen aux = n.getHijoIzquierdo();
            lis.insertar(n.getElem(), lis.longitud() + 1);//visitar el padre
            if (aux != null) {
                aux = aux.getHermanoDerecho();
            }
            while (aux != null) {
                lis.insertar(aux.getElem(), lis.longitud() + 1);//visitar el padre
                listarInorden(lis, aux);
                aux = aux.getHermanoDerecho();
            }
        }
    }*/

    private String toStringAux(NodoGen n) {
        String s = "";
        if (n != null) {
            //visita del nodo n
            s += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElem().toString();
                hijo = hijo.getHermanoDerecho();
            }
            //comienza recorrido de los hijos de n llamando recursivamente
            //para que cada hijo agregue su subcadena a la general
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }

        }
        return s;
    }

    private NodoGen obtenerNodo(Object buscado, NodoGen n) {
        NodoGen resultado = null;
        NodoGen hijo;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                resultado = n;
            } else {
                hijo = n.getHijoIzquierdo();

                while (hijo != null && resultado == null) { //A todos los hijos se los trata por igual
                    resultado = obtenerNodo(buscado, hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }
}
