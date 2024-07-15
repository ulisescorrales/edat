/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

/**
 *
 * @author ulise
 */
public class ArbolBB {

    private NodoBB raiz;

    public ArbolBB() {
        raiz = null;
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece;

        pertenece = pertenece(elem, this.raiz);

        return pertenece;
    }

    private boolean pertenece(Comparable minimo, NodoBB n) {
        boolean pertenece = false;
        if (n != null) {
            if (n.getElem().equals(minimo)) {
                pertenece = true;
            } else {
                if (minimo.compareTo(n.getElem()) > 0) {
                    pertenece = pertenece(minimo, n.getDerecho());
                } else {
                    pertenece = pertenece(minimo, n.getIzquierdo());
                }
            }
        }
        return pertenece;
    }

    public boolean insertar(Comparable minimo) {
        //Método que inserta un minimo en el ABB, no acepta minimos repetidos
        boolean exito = true;
        if (this.raiz == null) {//Si el árbol es vacío
            this.raiz = new NodoBB(minimo, null, null); //Setear la raíz
        } else {
            exito = insertarAux(this.raiz, minimo);//Recorrer recursivamente
        }
        return exito;
    }

    private boolean insertarAux(NodoBB n, Comparable minimo) {
        boolean exito = true;

        if ((minimo.compareTo(n.getElem()) == 0)) {//Si ya existe el minimo entonces
            exito = false;
        } else if (minimo.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), minimo);
            } else {
                n.setIzquierdo(new NodoBB(minimo, null, null));
            }
        } else if (n.getDerecho() != null) {
            exito = insertarAux(n.getDerecho(), minimo);
        } else {
            n.setDerecho(new NodoBB(minimo, null, null));
        }
        return exito;
    }

    public Lista listar() {
        Lista lis = new Lista();

        listar(lis, this.raiz);

        return lis;
    }

    private void listar(Lista lista, NodoBB n) {
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

    private Comparable minimoElem(NodoBB n) {
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

    private Comparable maximoElem(NodoBB n) {
        /*Método que busca el máximo minimo recorriendo por la derecha
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
        //min y max pueden no estar en el arbol (existen minimos mayores a min y menores a max)
        //min y max coinciden
        //

        Lista lis = new Lista();
        if (elemMinimo.compareTo(elemMaximo) <= 0) {
            listarRango(lis, elemMinimo, elemMaximo, this.raiz);
        }
        return lis;
    }

    private void listarRango(Lista lista, Comparable min, Comparable max, NodoBB n) {
        //Método que lista los minimos contenidos en el árbol entre los valores min y max de forma creciente
        //Se recorre el árbol en inorden inverso (Hijo Derecho - Padre - Hijo Izquerdo)
        if (n != null) {
            if (max.compareTo(n.getElem()) == 0) {//Se recorre hasta encontrar max o un minimo mayor a max
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
        //Método para testeo, retorna la cadena con los minimos en el árbol.
        String cadena = "";
        cadena = toString(this.raiz, cadena);
        return cadena;
    }

    private String toString(NodoBB auxNodo, String cadena) {
        //Método auxiliar para la recursión de toString()
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
        //Método que busca el elimento a eliminar y reacomoda los nodos según el orden, retorna éxito si el minimo existe        
        return eliminar(elem, this.raiz, null);
    }

    private boolean eliminar(Comparable elem, NodoBB n, NodoBB padre) {
        //Método auxiliar de eliminar que se invoca de forma recursiva
        boolean exito = false;
        if (n != null) {//Si el minimo no es nulo
            if (n.getElem().equals(elem)) {
                exito = true;//Si minimo existe, la operación será exitosa
                if (n.getIzquierdo() != null && n.getDerecho() != null) {//CASO: Si posee ambos hijos
                    //Usar el candidato A: el minimo mayor del subárbol izquierdo 

                    NodoBB aux = n.getIzquierdo();//aux se ubicará en el candidato A
                    NodoBB padreAux = n.getIzquierdo();//Padre de aux para setear su HD en null

                    while (aux.getDerecho() != null) {//Buscar el candidato A(máximo del subárbol izquierdo)
                        if (padreAux != aux) {//Retrasa el recorrido del padre en un minimo
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
                        padreAux.setDerecho(null);//Desapuntar el nodo aux por su padre, si aux iteró al menos una vez hacia la derecha
                    }

                } else if (n.getIzquierdo() == null && n.getDerecho() != null) {//CASO: HD no es nulo, HI es nulo
                    setPadre(n, n.getDerecho(), padre);
                } else if (n.getDerecho() == null && n.getIzquierdo() != null) {//CASO: HD es nulo, HI no es nulo
                    setPadre(n, n.getIzquierdo(), padre);
                } else {//CASO: Es hoja entonces se elimina directamente      
                    setPadre(n, null, padre);
                }
                //Si el minimo no se encontró entonces, buscar por la derecha o la izquierda según corresponda
            } else if (elem.compareTo(n.getElem()) < 0) {//Si minimo del nodo es mayor al minimo a eliminar
                exito = eliminar(elem, n.getIzquierdo(), n);//Ir por la izquierda
            } else {//Si elem es menor a elem
                exito = eliminar(elem, n.getDerecho(), n);//Ir por la derecha
            }
        }
        return exito;
    }

    private void setPadre(NodoBB minimo, NodoBB nuevo, NodoBB p) {
        if (this.raiz != minimo) {
            //minimo queda sin ser apuntado y se elimina
            if (p.getIzquierdo() == minimo) {//Si el hijo es el izquierdo
                p.setIzquierdo(nuevo);
            } else {//sino es el derecho
                p.setDerecho(nuevo);
            }
        } else {//Si el minimo a borrar es una raíz                       
            this.raiz = nuevo;//Setear la nueva raíz
        }
    }

    public void eliminarMinimo() {
        eliminarMinimo(this.raiz, null);
    }

    private void eliminarMinimo(NodoBB n, NodoBB padre) {
        if (n.getIzquierdo() == null) {
            if (n.getDerecho() == null) {//Si es hoja
                padre.setIzquierdo(null);
            } else {//Si el minimo a eliminar tiene HD
                padre.setIzquierdo(n.getDerecho());
            }
        } else {//Seguir bajando por la izquierda
            eliminarMinimo(n.getIzquierdo(), n);
        }
    }
    
    public Lista listarMayorIgual(Comparable elem){
        Lista lis=new Lista();
        listarMayorIgual(lis,elem,this.raiz);
        return lis;
    }
    private void listarMayorIgual(Lista lista,Comparable minimo,NodoBB n){
        if(n!=null){
            if(n.getElem().equals(minimo)){
                lista.insertar(minimo, 1);
                listarSubDerecho(lista,n.getDerecho());
            }else if(n.getElem().compareTo(minimo)>0){
                listarMayorIgual(lista,minimo,n.getIzquierdo());
                lista.insertar(n.getElem(), 1);
                listarSubDerecho(lista,n.getDerecho());
            }else{
                listarMayorIgual(lista,minimo,n.getDerecho());
            }
        }
    }
    private void listarSubDerecho(Lista lis, NodoBB n){
        //recorrer en inorden e ir insertando en la lista
        if(n!=null){
            listarSubDerecho(lis,n.getIzquierdo());
            lis.insertar(n.getElem(), 1);
            listarSubDerecho(lis,n.getDerecho());
        }
    }
}
