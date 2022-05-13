/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

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

        if (this.raiz != null) {
            listarPosorden(lista, this.raiz);
            lista.insertar(this.raiz.getElem(), lista.longitud() + 1);
        }
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

    //------------------------------------------------------
    /*public Lista listarPosorrden() {
        Lista lista = new Lista();
        listarPosorden(lista, this.raiz);
        return lista;
    }
     */
 /*private void listarPosorden(Lista lis, NodoGen n) {
        if (n != null) {
            NodoGen aux = this.getHijoIzquierdo();
            while (aux != null) {
                listarPosorden(aux = aux.getHermanoDerecho();
            }
            lis.listar(n.getElem(), lis.longitud() + 1);
        }
    }
     */
    public Lista porNivel() {
        Lista lista = new Lista();
        Cola cola = new Cola();
        cola.poner(this.raiz);
        porNivel(lista, cola, this.raiz);
        return lista;
    }

    private void porNivel(Lista lis, Cola q, NodoGen n) {
        if (n != null) {
            NodoGen aux2;
            NodoGen aux;
            while (q.esVacia() == false) {
                aux2 = (NodoGen) q.obtenerFrente();
                q.sacar();
                lis.insertar(q.obtenerFrente(), lis.longitud() + 1);
                aux = n.getHijoIzquierdo();
                while (aux != null) {
                    q.poner(aux.getElem());
                    aux = aux.getHermanoDerecho();
                }
            }
        }
    }

    public boolean pertenece(Object elemento) {
        boolean devolver;
        devolver = pertenece(elemento, this.raiz);
        return devolver;
    }

    private boolean pertenece(Object elemento, NodoGen n) {
        boolean encontrado = false;
        if (n != null) {
            encontrado = n.getElem().equals(elemento);
            NodoGen aux = n.getHijoIzquierdo();
            while (aux != null && !encontrado) {
                encontrado = pertenece(elemento, aux);
                aux = aux.getHermanoDerecho();
            }
        }
        return encontrado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    @Override
    public ArbolGen clone() {
        ArbolGen nuevo = new ArbolGen();
        nuevo.raiz = new NodoGen(this.raiz.getElem(), null, null);

        clone(nuevo.raiz, this.raiz);
        return nuevo;
    }

    private void clone(NodoGen aux, NodoGen n) {
        if (n != null) {
            NodoGen hijo = n.getHijoIzquierdo();
            if (hijo != null) {
                aux.setHijoIzquierdo(new NodoGen(hijo.getElem(), null, null));
                aux = aux.getHijoIzquierdo();
            }
            while (hijo != null) {
                clone(aux, hijo);
                hijo = hijo.getHermanoDerecho();
                if (hijo != null) {// HD
                    aux.setHermanoDerecho(new NodoGen(hijo.getElem(), null, null));
                    aux = aux.getHermanoDerecho();
                }
            }
        }
    }

    public int altura() {
        int altura = -1;
        altura = altura + altura(this.raiz);
        return altura;
    }

    private int altura(NodoGen n) {
        int retornar = 0;//
        int maximo;
        if (n != null) {
            retornar = 1;
            maximo = 1;
            NodoGen aux = n.getHijoIzquierdo();
            while (aux != null) {
                retornar = 1 + altura(aux);
                maximo = Math.max(retornar, maximo);
                aux = aux.getHermanoDerecho();
            }
            retornar = maximo;
        }
        return retornar;
    }

    public int nivel(Object elemento) {
        int retornar = -1;
        retornar = retornar + nivel(elemento, this.raiz);
        return retornar;
    }

    private int nivel(Object elemento, NodoGen n) {
        int retornar = 0;
        if (n != null) {
            if (n.getElem().equals(elemento)) {
                retornar = 1;
            } else {
                NodoGen aux = n.getHijoIzquierdo();
                while (aux != null && retornar == 0) {
                    retornar = retornar + nivel(elemento, aux);
                    if (retornar != 0) {
                        retornar++;
                    }
                    aux = aux.getHermanoDerecho();
                }
            }
        }
        return retornar;
    }

    public Lista ancestros(Object elemento) {
        Lista lista = new Lista();
        if (elemento != null) {
            ancestros(lista, elemento, this.raiz);
        }
        return lista;

    }

    private boolean ancestros(Lista lis, Object elemento, NodoGen n) {
        boolean encontrado = false;
        if (n != null) {
            if (n.getElem().equals(elemento)) {
                encontrado = true;
            } else {
                NodoGen aux = n.getHijoIzquierdo();
                while (aux != null && !encontrado) {
                    encontrado = ancestros(lis, elemento, aux);
                    if (encontrado) {
                        lis.insertar(n.getElem(), 1);
                    }
                    aux = aux.getHermanoDerecho();
                }
            }
        }
        return encontrado;
    }

    public NodoGen padre(Object elem) {
        NodoGen padre = null;
        if (!this.raiz.getElem().equals(elem)) {//Si la raíz no es el elemento
            padre = padre(elem, this.raiz);
        }
        return padre;
    }

    private NodoGen padre(Object elem, NodoGen n) {
        NodoGen padre = null;
        if (n.getHijoIzquierdo() != null) {
            NodoGen hijo = n.getHijoIzquierdo();
            if (hijo.equals(elem)) {
                padre = n;
            } else {
                while (hijo != null && padre == null) {
                    padre = padre(elem, hijo);
                    if (hijo.getElem().equals(elem)) {
                        padre = n;
                    }
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return padre;
    }

    public boolean sonFrontera(Lista unaLista) {
        //Precondición: unaLista no puede contener elementos repetidos
        //ArbolBin puede tener elementos repetidos

        if (!unaLista.esVacia()) {
            sonFrontera(unaLista, this.raiz);
        }
        return unaLista.esVacia();
    }

    private void sonFrontera(Lista lis, NodoGen n) {
        if (n != null) {
            NodoGen hijo = n.getHijoIzquierdo();
            if (hijo == null) {//Si n es hoja (no tiene HI)
                int pos = lis.localizar(n.getElem());//Buscar posición en la lista
                if (pos > 0) {
                    lis.eliminar(pos);
                }
            } else {
                while (hijo != null && !lis.esVacia()) {
                    sonFrontera(lis, hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }
}
