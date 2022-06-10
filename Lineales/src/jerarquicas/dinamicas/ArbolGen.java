/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas.dinamicas;

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
            exito = true;
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

    public boolean pertenece(Object elemento) {
        //Método que se invoca de manera recursiva
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
            NodoGen aux = n.getHijoIzquierdo();//Preguntar a hermanos derechos si tiene HI
            listarInorden(lis, aux);//Visitar HI
            lis.insertar(n.getElem(), lis.longitud() + 1);//visitar el Padre
            
            if (aux != null) {
                aux=aux.getHermanoDerecho();
                while (aux!= null) {
                    listarInorden(lis, aux);
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

    public Lista listarPorNiveles() {
        //Método que solo se ejecuta de manera iterativa
        Cola q = new Cola();
        NodoGen n;
        Lista lis = new Lista();

        q.poner(this.raiz);
        while (!q.esVacia()) {
            n = (NodoGen) q.obtenerFrente();
            q.sacar();
            lis.insertar(n.getElem(), lis.longitud() + 1);

            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                q.poner(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return lis;
    }

    public Lista listarEntreNiveles(int niv1, int niv2) {
        Lista lista = new Lista();
        listarEntreNiveles(lista, niv1, niv2, this.raiz, 0);
        return lista;
    }

    private void listarEntreNiveles(Lista lis, int min, int max, NodoGen n, int nivel) {
        if (n != null) {
            NodoGen aux = n.getHijoIzquierdo();//Preguntar a hermanos derechos si tiene HI
            if (nivel == max) {
                lis.insertar(n.getElem(), lis.longitud() + 1);//Solo insertar
            } else if (nivel >= min && nivel < max) {//Si está en el rago
                listarEntreNiveles(lis, min, max, aux, nivel + 1);//Visitar HI                        
                lis.insertar(n.getElem(), lis.longitud() + 1);//visitar el Padre
            } else if (nivel < min) {//Solo visitar HI
                listarEntreNiveles(lis, min, max, aux, nivel + 1);
            }

            if (aux != null) {
                aux = aux.getHermanoDerecho();
                while (aux != null) {
                    listarEntreNiveles(lis, min, max, aux, nivel + 1);
                    aux = aux.getHermanoDerecho();
                }
            }
        }
    }

    public boolean eliminar2(Object elem) {
        boolean exito;
        exito = eliminar2(elem, this.raiz);
        return exito;
    }

    private boolean eliminar2(Object elem, NodoGen n) {
        boolean exito = false;
        NodoGen aux = n.getHijoIzquierdo();
        if (aux != null) {
            if (aux.getElem().equals(elem)) {
                n.setHijoIzquierdo(aux.getHermanoDerecho());
                exito = true;
            } else {
                while (aux != null && !exito) {
                    exito = eliminar2(elem, aux);
                    System.out.println(aux.getElem());                    
                    if (aux.getHermanoDerecho() != null) {
                        if (!exito && aux.getHermanoDerecho().getElem().equals(elem)) {
                            aux.setHermanoDerecho(aux.getHermanoDerecho().getHermanoDerecho());
                            exito = true;

                        }
                    }
                    aux = aux.getHermanoDerecho();
                }
            }
        }
        return exito;
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

    public Object padre(Object elem) {
        return padreAux(this.raiz, elem, null);
    }

    private Object padreAux(NodoGen n, Object hijo, Object padre) {
        Object ret = null;

        if (n != null) {
            if (n.getElem().equals(hijo)) {
                ret = padre;
            } else {
                NodoGen hijoAux = n.getHijoIzquierdo();

                while (ret == null && hijoAux != null) {
                    ret = padreAux(hijoAux, hijo, n.getElem());
                    if (ret == null) {
                        hijoAux = hijoAux.getHermanoDerecho();
                    }
                }
            }
        }
        return ret;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public boolean sonFrontera(Lista unaLista) {
        //Precondición: unaLista no puede contener elementos repetidos
        //ArbolBin puede tener elementos repetidos
        Lista lis = unaLista.clone();
        boolean frontera = false;
        if (!lis.esVacia() && this.raiz != null) {
            sonFrontera(unaLista, this.raiz);
            frontera = lis.esVacia();
        } else if (lis.esVacia() && this.raiz == null) {
            frontera = true;
        }
        return frontera;
    }

    private void sonFrontera(Lista lis, NodoGen n) {
        //Método que recorre cada hoja, lo busca en la lista y lo elimina en caso de que exista
        if (n != null) {
            NodoGen hijo = n.getHijoIzquierdo();
            if (hijo == null) {//Si n es hoja (no tiene HI)
                int pos = lis.localizar(n.getElem());//Buscar posición en la lista
                if (pos > 0) {
                    lis.eliminar(pos);//Eliminarlo
                }
            } else {
                while (hijo != null && !lis.esVacia()) {/*Preguntarle a sus hijos si es
                    hoja y si es hoja, verificar en la lista y eliminarlo*/
                    sonFrontera(lis, hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n) {
        String s = "";
        if (n != null) {
            //visita del nodo n
            s += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElem().toString() + " ";
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

    public boolean verificarPatron(Lista lis) {
        boolean patron;
        patron = verificarPatron(lis, this.raiz);
        return patron;
    }

    private boolean verificarPatron(Lista lista, NodoGen n) {
        boolean patron = false;
        if (n != null) {
            if (n.getElem().equals(lista.recuperar(1))) {
                lista.eliminar(1);

                NodoGen aux = n.getHijoIzquierdo();
                if (aux != null) {
                    while (aux != null && !patron) {
                        patron = verificarPatron(lista, aux);
                        aux = aux.getHermanoDerecho();
                    }
                } else if (lista.esVacia()) {
                    patron = true;
                }
            }
        }
        return patron;
    }

    private boolean verificarPatron2(Lista lista, NodoGen n) {
        boolean patron = false;
        if (lista.esVacia() && n == null) {
            patron = true;
        } else {
            if (n.getElem().equals(lista.recuperar(1))) {
                lista.eliminar(1);

                NodoGen aux = n.getHijoIzquierdo();
                while (aux != null && !patron) {
                    patron = verificarPatron(lista, aux);
                    aux = aux.getHermanoDerecho();
                }
            }
        }
        return patron;
    }

    public boolean insertarPorAncestros(Lista lis) {
        boolean exito;
        exito = insertarPorAncestros(lis, this.raiz);
        return exito;
    }

    private boolean insertarPorAncestros(Lista lista, NodoGen n) {
        boolean exito = false;
        if (lista.esVacia()) {

        } else {
            if (n.getElem().equals(lista.recuperar(1))) {
                lista.eliminar(1);

            }
        }
        return exito;
    }

    public boolean verificarCamino(Lista lis) {
        boolean verificado;
        verificado = verificarCamino(lis, this.raiz);
        return verificado;
    }

    private boolean verificarCamino(Lista lista, NodoGen n) {
        boolean exito = false;//Será true si la lista queda vacía y no existen más elementos a verificar(el último fue una hoja
        if (lista.esVacia()) {
            exito = true;
        } else {
            if (n != null) {
                if (n.getElem().equals(lista.recuperar(1))) {//Si el elemento coincide con el primero de la lista
                    Object eliminado = lista.recuperar(1);
                    lista.eliminar(1);

                    NodoGen aux = n.getHijoIzquierdo();
                    if (aux == null && lista.esVacia()) {//Caso especial
                        exito = true;
                    } else {
                        while (aux != null && !exito) {
                            exito = verificarCamino(lista, aux);
                            aux = aux.getHermanoDerecho();
                        }
                    }
                    if (!exito) {
                        lista.insertar(eliminado, 1);
                    }
                }
            }
        }
        return exito;
    }
}
