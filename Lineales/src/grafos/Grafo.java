/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

import jerarquicas.dinamicas.ArbolGen;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;

/**
 *
 * @author ulise
 */
public class Grafo {

    //Grafo que vincula los vértices en ambos sentidos
    private NodoVert inicio;

    public Grafo() {
        inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object elem) {
        boolean encontrado = false;
        if (inicio != null) {
            NodoVert aux = inicio;
            NodoVert auxAnt = null;
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(elem)) {
                    encontrado = true;
                    eliminarNodosAdyacentes(aux);
                    if (auxAnt != null) {
                        auxAnt.setSigVertice(aux.getSigVertice());
                    } else {
                        //Si el elemento a eliminar es el primer vértice de la lista
                        this.inicio = aux.getSigVertice();
                    }
                } else {
                    auxAnt = aux;
                    aux = aux.getSigVertice();
                }
            }
        }
        return encontrado;
    }

    private void eliminarNodosAdyacentes(NodoVert apuntado) {
        //Elimina los nodos adyacentes que apuntan al nodo vértice eliminado
        NodoAdy aux = apuntado.getPrimerAdy();
        NodoAdy aux2;//Será el nodo adyacente cuyo vértice tiene enlace con el nodo vertice a eliminar
        NodoAdy antAux2 = null;
        boolean encontrado = false;

        //Recorrer toda la lista de adyacentes del nodo vértice apuntado
        while (aux != null) {
            aux2 = aux.getVertice().getPrimerAdy();
            //Buscar el nodo adyacente que apunta al vértice a eliminar (apuntado)
            while (aux2 != null && !encontrado) {
                if (aux2.getVertice() == apuntado) {
                    encontrado = true;
                    //Borrar el nodo adyacente que apunta al vertice apuntado
                    if (antAux2 != null) {
                        antAux2.setSigAdyacente(aux2.getSigAdyacente());
                    } else {
                        //Si es el primer elemento
                        aux.getVertice().setPrimerAdy(aux2.getSigAdyacente());
                    }
                } else {
                    antAux2 = aux;
                    aux2 = aux2.getSigAdyacente();
                }
            }
            //Reiniciar valores
            encontrado = false;
            antAux2 = null;
            //
            aux = aux.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object elem) {
        boolean existe = false;
        NodoVert aux = inicio;
        while (aux != null && !existe) {
            if (aux.getElem().equals(elem)) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean insertarArco(Object unVertice, Object otroVertice, Object etiqueta) {
        boolean exito = false;
        NodoVert nodo1;
        NodoVert nodo2;
        NodoVert aux = this.inicio;

        //Obtener los nodos
        while (aux != null && !exito) {
            if (aux.getElem().equals(unVertice) || aux.getElem().equals(unVertice)) {
                if (aux.getElem().equals(unVertice)) {
                    nodo1 = aux;
                    while (aux != null && !exito) {
                        if (aux.getElem().equals(otroVertice)) {
                            nodo2 = aux;
                            vincularNodos(nodo1, nodo2, etiqueta);
                            exito = true;
                        } else {
                            aux = aux.getSigVertice();
                        }
                    }
                } else {
                    nodo2 = aux;
                    while (aux != null && !exito) {
                        if (aux.getElem().equals(unVertice)) {
                            nodo1 = aux;
                            vincularNodos(nodo1, nodo2, etiqueta);
                            exito = true;
                        } else {
                            aux = aux.getSigVertice();
                        }
                    }
                }
            }
            aux = aux.getSigVertice();
        }
        return exito;
    }

    private void vincularNodos(NodoVert unNodo, NodoVert otroNodo, Object etiq) {
        //Módulo que vincula los nodos en ambos sentidos
        NodoAdy aux = unNodo.getPrimerAdy();
        NodoAdy aux2 = otroNodo.getPrimerAdy();

        if (aux != null) {
            //Ir hasta el final en la lista de adyacentes
            while (aux.getSigAdyacente() != null) {
                aux = aux.getSigAdyacente();
            }
            aux.setSigAdyacente(new NodoAdy(otroNodo, null, etiq));
        } else {
            //Si no hay nodos adyacentes, setearlo como el primero
            unNodo.setPrimerAdy(new NodoAdy(otroNodo, null, etiq));
        }
        if (aux2 != null) {
            while (aux2.getSigAdyacente() != null) {
                aux2 = aux2.getSigAdyacente();
            }
            aux2.setSigAdyacente(new NodoAdy(unNodo, null, etiq));
        } else {
            otroNodo.setPrimerAdy(new NodoAdy(unNodo, null, etiq));
        }

    }

    public boolean eliminarArco(Object unVertice, Object otroVertice) {
        //Método que elimina un arco entre dos vértices eliminando los nodos adyacentes correspondientes 
        //en ambos sentidos
        boolean encontrado = false;
        NodoVert aux = this.inicio;
        NodoAdy nodoAdyAux;

        while (aux != null && !encontrado) {
            if (aux.getElem().equals(unVertice) || aux.getElem().equals(otroVertice)) {
                if (aux.getElem().equals(unVertice)) {
                    //Fue encontrado unvertice, elimina el nodo adyacente que apunta a otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(aux, otroVertice);
                    //Se elimina el nodo adyacente que apunta a unVertice
                    eliminarNodoAdyacente(nodoAdyAux.getVertice(), aux.getElem());
                } else {
                    //El encontrado fue el otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(aux, unVertice);
                    eliminarNodoAdyacente(nodoAdyAux.getVertice(), aux.getElem());
                }
            } else {
                aux = aux.getSigVertice();
            }
        }
        return encontrado;
    }

    private NodoAdy eliminarNodoAdyacente(NodoVert vertice, Object eliminar) {
        //Módulo que elimina un nodo adyacente en la lista del nodo vértice
        //Retorna el nodo adyacente eliminado para poder reutilizarlo en el método de origen
        NodoAdy aux = vertice.getPrimerAdy();
        NodoAdy antAux = null;
        boolean encontrado = false;

        while (aux != null && !encontrado) {
            if (aux.getVertice().getElem().equals(eliminar)) {
                encontrado = true;
                if (antAux != null) {
                    antAux.setSigAdyacente(aux.getSigAdyacente());
                }
            } else {
                antAux = aux;
                aux = aux.getSigAdyacente();
            }
        }
        return aux;
    }

    public boolean existeArco(Object unNodo, Object otroNodo) {
        NodoVert aux = this.inicio;
        boolean encontrado = false;
        NodoAdy auxAdy;

        while (aux != null && !encontrado) {
            if (aux.getElem().equals(unNodo) || aux.getElem().equals(otroNodo)) {
                auxAdy = aux.getPrimerAdy();
                if (aux.getElem().equals(unNodo)) {
                    while (auxAdy != null && !encontrado) {
                        if (auxAdy.getVertice().getElem().equals(otroNodo)) {
                            encontrado = true;
                        }
                    }
                } else {
                    while (auxAdy != null && !encontrado) {
                        if (auxAdy.getVertice().getElem().equals(unNodo)) {
                            encontrado = true;
                        }
                    }
                }
            } else {
                aux = aux.getSigVertice();
            }
        }
        return encontrado;
    }

    public boolean vacio() {
        return this.inicio == null;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }
        if (auxO != null && auxD != null) {
            //si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            //si verticen es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                //si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista lis=null;

        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }        
        if (auxO != null && auxD != null) {
            //si ambos vertices existen, se busca el camino más corto
            
            lis=caminoMasCorto(auxO, destino);
        }
        return lis;
    }

 

    

    private Lista caminoMasCorto(NodoVert or,Object dest) {
        Lista auxCola = new Lista();
        NodoVert auxV;
        NodoAdy auxA;
        auxCola.insertar(or,1);
        Lista saltos = new Lista();
        int posVisitados = 0;
        int contSalto = 0;
        int posSalto = 0;
        boolean encontrado=false;
        Lista recorrido=new Lista();
        Lista lisNivel=new Lista();

        while (!auxCola.esVacia() && !encontrado) {
            //
            posVisitados++;
            auxV = (NodoVert) auxCola.recuperar(1);
            lisNivel.insertar(auxV, posVisitados);
            auxCola.eliminar(1);
            //Colocar en la cola
            auxA = auxV.getPrimerAdy();
            while (auxA != null && !encontrado) {
                posSalto++;
                if (auxCola.localizar(auxA.getVertice()) < 0 && recorrido.localizar(auxA.getVertice()) < 0) {//Si ya fue visitado entonces no se pone en la cola
                    if (auxA.getVertice().getElem().equals(dest)) {//Si se encuentra el destino
                        recorrido.insertar(auxA.getVertice(), 1);
                        recorrido.insertar(auxV, 1);
                        encontrado = true;
                    } else {//Sino colocar en la cola
                        contSalto++;
                        auxCola.insertar(auxA.getVertice(),auxCola.longitud()+1);
                        saltos.insertar(contSalto, posSalto);
                    }
                }                
                auxA = auxA.getSigAdyacente();
            }
            contSalto--;
        }
        //Armar la Lista  
        while(posVisitados!=0){
            posVisitados=posVisitados-(int)saltos.recuperar(posVisitados-1);
            recorrido.insertar(lisNivel.recuperar(posVisitados-1),1);
        }
        
        recorrido.insertar(or, 1);
        
        return recorrido;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        //define un vértice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                //si el vértice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            //marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                //visita en profundidad los adyacentes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {//Si aun no fue visitado
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista lis = new Lista();
        Lista visitado = new Lista();
        listarEnAnchura(lis, this.inicio, visitado);
        return lis;
    }

    private void listarEnAnchura(Lista recorrido, NodoVert n, Lista visitados) {
        Cola auxCola = new Cola();
        NodoVert auxV;
        NodoAdy auxA;
        auxCola.poner(this.inicio);

        while (!auxCola.esVacia()) {
            //
            auxV = (NodoVert) auxCola.obtenerFrente();
            visitados.insertar(auxV, visitados.longitud() + 1);
            auxCola.sacar();
            //Colocar en la cola
            auxA = auxV.getPrimerAdy();
            while (auxA != null) {
                if (visitados.localizar(auxA.getVertice()) < 0) {//Si ya fue visitado entonces no se pone en la cola
                    auxCola.poner(auxA.getVertice());
                }
                auxA = auxA.getSigAdyacente();
            }
            //
        }
    }

    @Override
    public Grafo clone() {
        Grafo nuevoGrafo = new Grafo();
        NodoVert auxO = this.inicio;
        NodoVert auxN;
        NodoAdy auxAdyO;
        NodoAdy auxAdyN;
        int pos;

        //Clonar el primer vértice
        nuevoGrafo.inicio = new NodoVert(auxO.getElem(), null, null);
        auxN = nuevoGrafo.inicio;
        auxO = auxO.getSigVertice();
        //Clonar los siguientes vértices
        while (auxO != null) {
            auxN.setSigVertice(new NodoVert(auxO.getElem(), null, null));
            auxN = auxN.getSigVertice();
            auxO = auxO.getSigVertice();
        }
        //Clonar los nodos adyacentes, como no se puede clonar las direcciones a memoria, se hace referencia a su posicion en la lista
        auxO = this.inicio;
        auxN = nuevoGrafo.inicio;

        while (auxO != null) {
            //primer adyacente
            auxAdyO = auxO.getPrimerAdy();
            pos = posLista(auxAdyO.getVertice());
            auxN.setPrimerAdy(new NodoAdy(buscarPos(pos, nuevoGrafo), null, auxAdyO.getEtiqueta()));
            //punteros sobre el primer adyacente
            auxAdyN = auxN.getPrimerAdy();
            auxAdyO = auxAdyO.getSigAdyacente();
            //Siguientes adyacentes
            while (auxAdyO != null) {
                pos = posLista(auxAdyO.getVertice());
                auxAdyN.setSigAdyacente(new NodoAdy(buscarPos(pos, nuevoGrafo), null, auxAdyO.getEtiqueta()));
                auxAdyN = auxAdyN.getSigAdyacente();
                auxAdyO = auxAdyO.getSigAdyacente();
            }
        }
        return nuevoGrafo;
    }

    private int posLista(NodoVert buscado) {
        int pos = 0;
        NodoVert aux = this.inicio;
        boolean encontrado = false;

        while (aux != null && !encontrado) {
            pos++;
            if (buscado == aux) {
                encontrado = true;

            } else {
                aux = aux.getSigVertice();
            }
        }
        return pos;
    }

    private NodoVert buscarPos(int pos, Grafo g) {
        NodoVert aux = g.inicio;
        int i = 0;

        while (aux != null && i <= pos) {
            i++;
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public String toString() {

    }
}
