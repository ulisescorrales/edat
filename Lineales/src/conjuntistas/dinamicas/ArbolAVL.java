/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

/**
 *
 * @author ulises.corrales
 */
public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece;

        pertenece = pertenece(elem, this.raiz);

        return pertenece;
    }

    private boolean pertenece(Comparable elemento, NodoAVL n) {
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
            this.raiz = new NodoAVL(elemento, null, null, 0); //Setear la raíz
        } else {
            exito = insertarAux(this.raiz, elemento);//Recorrer recursivamente
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL n, Comparable elemento) {
        //insertarAux(this.raiz,elem, this.raiz.altura(),0);
        boolean exito = true;

        if ((elemento.compareTo(n.getElem()) == 0)) {//Si ya existe el elemento entonces retorna false
            exito = false;
        } else if (elemento.compareTo(n.getElem()) < 0) {//Si elemento es menor a n.getElem()
            if (n.getIzquierdo() != null) {//Si no es nulo,  bajar por la izquierda
                exito = insertarAux(n.getIzquierdo(), elemento);
                if (exito) {
                    if (n.getDerecho() != null) {
                        n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                    } else {
                        n.setAltura(n.getIzquierdo().getAltura() + 1);//Evitar el getDerecho() de null
                    }
                }
            } else {//Si es nulo, crear el hijo izquierdo                
                n.setIzquierdo(new NodoAVL(elemento, null, null, 0));
                if (n.getDerecho() != null) {
                    n.setAltura(Math.max(0, n.getDerecho().getAltura()) + 1);
                } else {
                    n.setAltura(1);
                }
            }
        } else if (n.getDerecho() != null) {//Si elemento es mayor a n.getElem(), bajar por la derecha
            exito = insertarAux(n.getDerecho(), elemento);
            if (exito) {
                if (n.getIzquierdo() != null) {
                    n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                } else {
                    n.setAltura(n.getDerecho().getAltura() + 1);
                }

            }
        } else {//Si HD es nulo, crear el hijo derecho nuevo
            n.setDerecho(new NodoAVL(elemento, null, null, 0));
            if (n.getIzquierdo() != null) {
                n.setAltura(Math.max(0, n.getIzquierdo().getAltura()) + 1);
            } else {
                n.setAltura(1);
            }
        }
        return exito;
    }

    public Lista listar() {
        Lista lis = new Lista();

        listar(lis, this.raiz);

        return lis;
    }

    private void listar(Lista lista, NodoAVL n) {
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

    private Comparable minimoElem(NodoAVL n) {
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

    private Comparable maximoElem(NodoAVL n) {
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

    private void listarRango(Lista lista, Comparable min, Comparable max, NodoAVL n) {
        //Método que lista los elementos contenidos en el árbol entre los valores min y max de forma creciente
        //Se recorre el árbol en inorden inverso (Hijo Derecho - Padre - Hijo Izquerdo)
        if (n != null) {
            if (max.compareTo(n.getElem()) == 0) {//Se recorre hasta encontrar max o un elemento mayor a max
                //Si se encuentra, no seguir recorriendo
                lista.insertar(n.getElem(), 1);
                /*Con el preorden inverso se evita el orden O(n) de insertar de lista y 
                se usa el orden O(1)*/
            } else if (max.compareTo(n.getElem()) > 0) {//Sino ir por la derecha 
                listarRango(lista, min, max, n.getDerecho());
                if (min.compareTo(n.getElem()) <= 0) {
                    lista.insertar(n.getElem(), 1);
                }
            }
            if (min.compareTo(n.getElem()) < 0) {//Luego de buscar el máximo, buscar el mínimo
                listarRango(lista, min, max, n.getIzquierdo());
            }
        }
    }

    @Override
    public String toString() {
        //Método para testeo, retorna la cadena con los elementos en el árbol.
        String cadena = "";
        cadena = toString(this.raiz, cadena);
        return cadena;
    }

    private String toString(NodoAVL auxNodo, String cadena) {
        //Método auxiliar para la recursión de toString()
        if (auxNodo != null) {

            if (auxNodo.getIzquierdo() == null && auxNodo.getDerecho() == null) {
                cadena = "Hoja: " + auxNodo.getElem().toString() + "(" + auxNodo.getAltura() + ")" + "\n";
            } else {
                cadena = "Padre: " + auxNodo.getElem().toString() + "(" + auxNodo.getAltura() + ")";

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
        //Método que busca el elimento a eliminar y reacomoda los nodos según el orden, retorna éxito si el elemento existe        
        return eliminar(elem, this.raiz, null);
    }

    private boolean eliminar(Comparable elem, NodoAVL n, NodoAVL padre) {
        //Método auxiliar de eliminar que se invoca de forma recursiva
        boolean exito = false;
        if (n != null) {//Si el elemento no es nulo
            if (n.getElem().equals(elem)) {
                exito = true;//Si elemento existe, la operación será exitosa
                if (n.getIzquierdo() != null && n.getDerecho() != null) {//CASO: Si posee ambos hijos
                    //Usar el candidato A: el elemento mayor del subárbol izquierdo 

                    NodoAVL aux = buscarCandidatoA(n.getIzquierdo());

                    //Setear los hijos del nuevo padre
                    if (n.getIzquierdo() != aux) {//Si aux itera aunque sea una vez hacia la derecha, se setea el HI
                        aux.setIzquierdo(n.getIzquierdo());
                    }
                    aux.setDerecho(n.getDerecho());
                    //Setear la altura del candidato A en su posición nueva
                    aux.setAltura(Math.max(aux.getIzquierdo().getAltura(), aux.getDerecho().getAltura()) + 1);//Ya se sabe de antemano que tiene ambos hijos
                    //setear el padre del padre
                    setPadre(n, aux, padre);
                    //Setear la nueva altura del padre
                    padre.setAltura(Math.max(padre.getIzquierdo().getAltura(), padre.getDerecho().getAltura()) + 1);
                    padre.setAltura(Math.max(aux.getIzquierdo().getAltura(), aux.getDerecho().getAltura()) + 1);
                } else if (n.getIzquierdo() == null && n.getDerecho() != null) {//CASO: HD no es nulo, HI es nulo
                    setPadre(n, n.getDerecho(), padre);
                    
                } else if (n.getDerecho() == null && n.getIzquierdo() != null) {//CASO: HD es nulo, HI no es nulo
                    setPadre(n, n.getIzquierdo(), padre);
                } else {//CASO: Es hoja entonces se elimina directamente      
                    setPadre(n, null, padre);
                }
                //Si el elemento no se encontró entonces, buscar por la derecha o la izquierda según corresponda
            } else if (elem.compareTo(n.getElem()) < 0) {//Si elemento del nodo es mayor al elemento a eliminar
                exito = eliminar(elem, n.getIzquierdo(), n);//Ir por la izquierda
            } else {//Si elem es menor a elem
                exito = eliminar(elem, n.getDerecho(), n);//Ir por la derecha
            }
        }
        return exito;
    }

    private NodoAVL buscarCandidatoA(NodoAVL n) {
        //Método que busca al candidato A y que setea al padre del mismo en null
        //También va actualizando la altura de los nodos que recorre
        NodoAVL retornar;
        if (n.getDerecho() == null) {
            retornar = n;
        } else {
            retornar = buscarCandidatoA(n.getDerecho());
            if (retornar == n.getDerecho()) {//Setear en null al HD del padredel candidato A
                n.setDerecho(null);
                if (n.getIzquierdo() == null) {
                    n.setAltura(0);
                } else {
                    n.setAltura(n.getIzquierdo().getAltura() + 1);
                }
            } else if (n.getIzquierdo() != null) {
                n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
            } else {
                n.setAltura(n.getDerecho().getAltura() + 1);
            }
        }
        return retornar;
    }
    private void caso1(NodoAVL eliminar, NodoAVL nuevoHijo, NodoAVL padreSetear) {
        if (this.raiz != eliminar) {
            padreSetear.setDerecho(nuevoHijo);
            padreSetear.setAltura(nuevoHijo.getAltura()+1);
        }
    }
    private void caso2(NodoAVL eliminar, NodoAVL nuevoHijo, NodoAVL padreSetear) {
        if (this.raiz != eliminar) {
            padreSetear.setIzquierdo(nuevoHijo);
            padreSetear.setAltura(nuevoHijo.getAltura()+1);
        }
    }
    private void setPadre(NodoAVL eliminar, NodoAVL nuevoHijo, NodoAVL padreSetear) {
        //Método que vincula el padre del padre a eliminar con su nuevo hijo
        if (this.raiz != eliminar) {
            //Elemento a eliminar queda sin ser apuntado y se elimina
            if (padreSetear.getIzquierdo() == eliminar) {//Si el hijo es el izquierdo
                padreSetear.setIzquierdo(nuevoHijo);               
            } else {//sino es el derecho
                padreSetear.setDerecho(nuevoHijo);
            }
            padreSetear.setAltura(nuevoHijo.getAltura()+1);
        } else {//Si el eliminar a borrar es una raíz                       
            this.raiz = nuevoHijo;//Setear la nueva raíz
        }
    }
    private void setearAltura(NodoAVL n){
        if(n.getDerecho()!=null && n.getIzquierdo()!=null){
            n.setAltura(Math.max(n.getDerecho().getAltura(),n.getIzquierdo().getAltura())+1);
        }else if(n.getDerecho()!=null){
            n.setAltura(n.getDerecho().getAltura()+1);
        }else if(n.getIzquierdo()!=null){
            n.setAltura(n.getIzquierdo().getAltura()+1);
        }else{//Es hoja
            n.setAltura(0);
        }
    }
    private void rotarIzquierda(NodoAVL r) {

    }
}
