/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Grafo;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Corrales Ulises
 */
public class Grafo {

    //Grafo que vincula los vértices en ambos sentidos
    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        //Crea un nuevo objeto NodoVert y lo añade a la lista de vértices
        boolean exito = false;
        NodoVert consultaVert = ubicarVertice(nuevoVertice);
        //Si no existe el vértice, se lo inserta en la primera posición en la lista de vértices
        if (consultaVert == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        //Iterar sobre la lista de vértices hasta encontrar el objeto
        NodoVert vertice = this.inicio;
        while (vertice != null && !vertice.getElem().equals(buscado)) {
            vertice = vertice.getSigVertice();
        }
        return vertice;
    }

    public boolean eliminarVertice(Object elem) {
        //Busca el vértice en la lista de vértices y elimina los arcos unidos al mismo
        boolean encontrado = false;
        if (this.inicio != null) {
            NodoVert vertice = this.inicio;
            NodoVert verticeAnterior = null;
            //Buscar el vértice en la lista de adyacentes
            while (vertice != null && !encontrado) {
                if (vertice.getElem().equals(elem)) {
                    encontrado = true;
                    eliminarNodosAdyacentes(vertice);
                    if (verticeAnterior != null) {
                        //Si no es el primer vértice, enlazar el anterior con el siguiente
                        verticeAnterior.setSigVertice(vertice.getSigVertice());
                    } else {
                        //Sino setear al siguiente como el inicio de la lista
                        this.inicio = vertice.getSigVertice();
                    }
                } else {
                    verticeAnterior = vertice;
                    vertice = vertice.getSigVertice();
                }
            }
        }
        return encontrado;
    }

    public Object getEtiqueta(Object nodo1, Object nodo2) {
        //Busca el arco entre ambos nodos y retorna su etiqueta        
        Object etiqueta = null;
        NodoVert vertice = this.inicio;
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        //Variable para iterar sobre la lista de nodos adyacentes
        NodoAdy auxAdy;
        //Encontrar nodo1 o estación 2 y luego buscar en la lista de adyacentes la otra estación
        while (!encontrado1 && !encontrado2 && vertice != null) {
            if (vertice.getElem().equals(nodo1)) {
                encontrado1 = true;
                auxAdy = vertice.getPrimerAdy();
                while (!encontrado2 && auxAdy != null) {
                    if (!auxAdy.getVertice().getElem().equals(nodo2)) {
                        encontrado2 = true;
                        etiqueta = auxAdy.getEtiqueta();
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            } else if (vertice.getElem().equals(nodo2)) {
                encontrado2 = true;
                auxAdy = vertice.getPrimerAdy();
                while (!encontrado1 && auxAdy != null) {
                    if (!auxAdy.getVertice().getElem().equals(nodo2)) {
                        encontrado1 = true;
                        etiqueta = auxAdy.getEtiqueta();
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
            vertice = vertice.getSigVertice();
        }
        return etiqueta;
    }

    public boolean cambiarEtiqueta(Object nodo1, Object nodo2, Object nuevaEtiqueta) {
        //Método que busca los arcos entre ambos nodos y modifica la etiqueta
        NodoVert vertice = this.inicio;
        boolean exito = false;
        NodoAdy auxAdy;
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        //Encontrar ambos nodos y luego unir
        while (!encontrado1 && vertice != null) {
            //Si se encuentra primero el nodo1
            if (vertice.getElem().equals(nodo1)) {
                encontrado1 = true;
                auxAdy = vertice.getPrimerAdy();
                while (!encontrado2 && auxAdy != null) {
                    if (auxAdy.getVertice().getElem().equals(nodo2)) {
                        encontrado2 = true;
                        auxAdy.setEtiqueta(nuevaEtiqueta);
                        exito = true;
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
                //Si se encuentra primero el nodo2
            } else if (vertice.getElem().equals(nodo2)) {
                encontrado1 = true;
                auxAdy = vertice.getPrimerAdy();
                while (!encontrado2 && auxAdy != null) {
                    if (auxAdy.getVertice().getElem().equals(nodo1)) {
                        encontrado2 = true;
                        auxAdy.setEtiqueta(nuevaEtiqueta);
                        exito = true;
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            } else {
                vertice = vertice.getSigVertice();
            }
        }

        return exito;
    }

    private void eliminarNodosAdyacentes(NodoVert apuntado) {
        //Método auxiliar para eliminar un nodo vértice que elimina los nodos adyacentes que apuntan al nodo vértice eliminado       
        NodoAdy adyDelApuntado = apuntado.getPrimerAdy();//Apunta a cada adyacente del vértice a eliminar
        NodoAdy adyApuntador;//Será el nodo adyacente cuyo vértice tiene enlace con el nodo vertice a eliminar
        NodoAdy adyApuntadorAnterior = null;//Es para saber si adyApuntador tiene un nodo anterior en la lista de adyacentes cuando encuentra a apuntado
        boolean encontrado = false;

        //Por cada adyacente en la lista de adyacentes
        while (adyDelApuntado != null) {
            //Buscar el nodo adyacente que apunta al vértice a eliminar (apuntado)
            adyApuntador = adyDelApuntado.getVertice().getPrimerAdy();
            while (adyApuntador != null && !encontrado) {
                if (adyApuntador.getVertice() == apuntado) {
                    encontrado = true;
                    //Enlazar el nodo adyacente anterior a adyApuntador con el siguiente del mismo
                    if (adyApuntadorAnterior != null) {
                        adyApuntadorAnterior.setSigAdyacente(adyApuntador.getSigAdyacente());
                    } else {
                        //Si es el primer elemento, setear el siguiente como primer adyacente
                        adyDelApuntado.getVertice().setPrimerAdy(adyApuntador.getSigAdyacente());
                    }
                } else {
                    adyApuntadorAnterior = adyApuntador;
                    adyApuntador = adyApuntador.getSigAdyacente();
                }
            }
            //Reiniciar valores
            encontrado = false;
            adyApuntadorAnterior = null;
            adyDelApuntado = adyDelApuntado.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object elem) {
        //Método para saber si existe un vértice en la lista de NodoVert
        boolean existe = false;
        NodoVert vertice = this.inicio;
        while (vertice != null && !existe) {
            if (vertice.getElem().equals(elem)) {
                existe = true;
            }
            vertice = vertice.getSigVertice();
        }
        return existe;
    }

    public boolean insertarArco(Object unVertice, Object otroVertice, Object etiqueta) {
        //Método para insertar un arco entre dos nodos
        boolean exito = false;
        NodoVert nodo1;
        NodoVert nodo2;
        NodoVert vertice = this.inicio;

        //Obtener los nodos
        while (!exito && vertice != null) {//Recorrer la lista de vértices            
            if (vertice.getElem().equals(unVertice)) {//Si es uno, sigue buscando el otro                    
                nodo1 = vertice;
                vertice = vertice.getSigVertice();
                while (vertice != null && !exito) {
                    if (vertice.getElem().equals(otroVertice)) {
                        nodo2 = vertice;
                        vincularNodos(nodo1, nodo2, etiqueta);
                        exito = true;
                    } else {
                        vertice = vertice.getSigVertice();
                    }
                }
            } else if (vertice.getElem().equals(otroVertice)) {
                nodo2 = vertice;
                vertice = vertice.getSigVertice();
                while (vertice != null && !exito) {
                    if (vertice.getElem().equals(unVertice)) {
                        nodo1 = vertice;
                        vincularNodos(nodo1, nodo2, etiqueta);
                        exito = true;
                    } else {
                        vertice = vertice.getSigVertice();
                    }
                }
            } else {
                vertice = vertice.getSigVertice();
            }
        }
        return exito;
    }

    private void vincularNodos(NodoVert unNodo, NodoVert otroNodo, Object etiq) {
        //Módulo que vincula los nodos en ambos sentidos
        NodoAdy adyUnNodo = unNodo.getPrimerAdy();
        NodoAdy adyOtroNodo = otroNodo.getPrimerAdy();

        if (adyUnNodo != null) {
            //Ir hasta el final en la lista de adyacentes
            while (adyUnNodo.getSigAdyacente() != null) {
                adyUnNodo = adyUnNodo.getSigAdyacente();
            }
            adyUnNodo.setSigAdyacente(new NodoAdy(otroNodo, null, etiq));
        } else {
            //Si no hay nodos adyacentes, setearlo como el primero
            unNodo.setPrimerAdy(new NodoAdy(otroNodo, null, etiq));
        }
        if (adyOtroNodo != null) {
            while (adyOtroNodo.getSigAdyacente() != null) {
                adyOtroNodo = adyOtroNodo.getSigAdyacente();
            }
            adyOtroNodo.setSigAdyacente(new NodoAdy(unNodo, null, etiq));
        } else {
            otroNodo.setPrimerAdy(new NodoAdy(unNodo, null, etiq));
        }

    }

    public boolean eliminarArco(Object unVertice, Object otroVertice) {
        //Método que elimina un arco entre dos vértices eliminando los nodos adyacentes correspondientes 
        //en ambos sentidos
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        NodoVert vertice = this.inicio;
        NodoAdy nodoAdyAux;

        while (vertice != null && !encontrado1) {
            if (vertice.getElem().equals(unVertice) || vertice.getElem().equals(otroVertice)) { //Si es algún vértice buscado
                encontrado1 = true;
                if (vertice.getElem().equals(unVertice)) {
                    //Fue encontrado unvertice, elimina el nodo adyacente que apunta a otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(vertice, otroVertice);
                    //Se elimina el nodo adyacente que apunta a unVertice
                    if (nodoAdyAux != null) {
                        encontrado2 = true;
                        eliminarNodoAdyacente(nodoAdyAux.getVertice(), vertice.getElem());
                    }
                } else {
                    //El encontrado fue el otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(vertice, unVertice);
                    if (nodoAdyAux != null) {
                        encontrado2 = true;
                        eliminarNodoAdyacente(nodoAdyAux.getVertice(), vertice.getElem());
                    }
                }
            } else {
                vertice = vertice.getSigVertice();
            }
        }
        return encontrado2;
    }

    private NodoAdy eliminarNodoAdyacente(NodoVert vertice, Object eliminar) {
        /*Módulo que elimina un nodo adyacente en la lista del nodo vértice
        Retorna el nodo adyacente eliminado para poder reutilizarlo en el método de origen*/
        NodoAdy ady = vertice.getPrimerAdy();
        NodoAdy adyAnterior = null;
        boolean encontrado = false;

        while (ady != null && !encontrado) {//Busca el nodo adyacente a eliminar
            if (ady.getVertice().getElem().equals(eliminar)) {
                encontrado = true;
                if (adyAnterior != null) {//Si no es el primer adyacente, une adyAnterior con el siguiente de ady
                    adyAnterior.setSigAdyacente(ady.getSigAdyacente());
                } else {//Sino se setea el primer adyacente del vértice con el siguiente de ady
                    vertice.setPrimerAdy(ady.getSigAdyacente());
                }
            } else {
                adyAnterior = ady;
                ady = ady.getSigAdyacente();
            }
        }
        return ady;
    }

    public boolean existeArco(Object unNodo, Object otroNodo) {
        //Método para saber si dos nodos están conectados
        NodoVert vertice = this.inicio;
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        NodoAdy auxAdy;

        while (vertice != null && !encontrado1) {
            if (vertice.getElem().equals(unNodo) || vertice.getElem().equals(otroNodo)) {//Si encuentra alguno de los dos nodos
                //sigue buscando el otro nodo en su lista de adyacentes
                encontrado1 = true;
                auxAdy = vertice.getPrimerAdy();
                if (vertice.getElem().equals(unNodo)) {
                    while (auxAdy != null && !encontrado2) {
                        if (auxAdy.getVertice().getElem().equals(otroNodo)) {
                            encontrado2 = true;
                        }
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                } else {
                    while (auxAdy != null && !encontrado2) {
                        if (auxAdy.getVertice().getElem().equals(unNodo)) {
                            encontrado2 = true;
                        }
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
            } else {
                vertice = vertice.getSigVertice();
            }
        }
        return encontrado2;
    }

    public boolean vacio() {
        //Método para saber si el grafo no contiene nodos
        return this.inicio == null;
    }

    public boolean existeCamino(Object origen, Object destino) {
        //Método para verificar si existe un camino entre dos nodos
        boolean exito = false;
        //verifica si ambos vértices existen
        NodoVert nodoO = null;
        NodoVert nodoD = null;
        NodoVert aux = this.inicio;

        //Buscar los NodoVert de ambos objetos
        while ((nodoO == null || nodoD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                nodoO = aux;
            }
            if (aux.getElem().equals(destino)) {
                nodoD = aux;
            }
            aux = aux.getSigVertice();
        }
        if (nodoO != null && nodoD != null) {
            //si ambos vertices existen busca si existe camino entre ambos
            LinkedList visitados = new LinkedList();
            exito = existeCaminoAux(nodoO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, LinkedList vis) {
        //Método auxiliar para existeCamino
        boolean exito = false;
        if (n != null) {
            //si verticen es el destino: HAY CAMINO
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                //si no es el destino verifica si hay camino entre n y destino
                vis.add(n.getElem());
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.indexOf(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public LinkedList caminoMasCortoPorCantNodos(Object origen, Object destino) {
       /* //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        LinkedList lis = new LinkedList();

        //Comprobar que ambos nodos existan
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
            //si no existe camino retorna null
            lis = caminoMasCortoPorCantNodos2(auxO, auxD);
        }*/
        return new LinkedList();
    }

    /*private LinkedList caminoMasCortoPorCantNodos2(NodoVert origen, NodoVert destino) {
        //Los recorridos se van almacenando en el árbol, como no se vuelven a visitar nodos ya visitados y el algoritmo para cuando se encuentra
        //destino entonces los elementos sólo existen una sola vez en el árbol genérico
        ArbolGen recorridos = new ArbolGen();
        LinkedList caminoMasCorto = new LinkedList();
        LinkedList visitados = new LinkedList();
        boolean exito;

        //La raíz del ábol es el origen
        recorridos.insertar(origen.getElem(), null);
        //Si desde origen no se llega a destino es porque no están unidos por ningún arco, no es necesario seguir recorriendo nodos inalcanzables        
        exito = caminoMasCortoPorCantNodos3(origen, visitados, recorridos, destino);

        if (exito) {
            //Recuperar el recorrido a partir del árbol
            caminoMasCorto = recorridos.ancestros(destino.getElem());
            caminoMasCorto.add(destino.getElem());
        }

        return caminoMasCorto;
    }

    private boolean caminoMasCortoPorCantNodos3(NodoVert vertice, LinkedList vis, ArbolGen arbol, NodoVert dest) {
        //Método auxiliar para caminoMasCortoPorCantNodos2
        //Inicia el recorrido en anchura hasta llegar al destino
        Queue cola = new LinkedList();
        vis.addFirst(vertice);
        cola.add(vertice);
        NodoVert u;
        NodoAdy auxAdy;
        NodoVert ultimoPadre;
        boolean exito = false;
        while (!cola.isEmpty() && !exito) {
            u = (NodoVert) cola.poll();
            auxAdy = u.getPrimerAdy();
            ultimoPadre = u;
            while (auxAdy != null && !exito) {
                //Si el adyacente no está en visitados
                if (vis.indexOf(auxAdy.getVertice()) == -1) {
                    vis.add(auxAdy.getVertice());
                    cola.add(auxAdy.getVertice());

                    arbol.insertar(auxAdy.getVertice().getElem(), ultimoPadre.getElem());                    
                    if (auxAdy.getVertice().getElem().equals(dest.getElem())) {
                        //Encuentra el destino y frena el bucle                        
                        exito = true;
                    }
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return exito;
    }*/

    /*public LinkedList caminoMasCortoPorCantNodos(Object origen, Object destino) {
        //Método que encuentra el camino entre dos nodos con la menor cantidad de nodos posibles        
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        LinkedList lis = new LinkedList();

        //Comprobar que ambos nodos existan
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
            //
            lis = caminoMasCorto(auxO, destino, lis);
            lis.addFirst(origen);
        }
        return lis;
    }

    private LinkedList caminoMasCorto(NodoVert or, Object dest, LinkedList recorrido) {
        //Método auxiliar para caminoMasCortoPorCantNodos
        //Usa el recorrido en anchura hasta encontrar el destino para luego construir la lista
        LinkedList auxCola = new LinkedList();
        LinkedList saltos = new LinkedList();
        int posCola = 0;
        int contSalto = 0;
        int puntero;

        int posSalto = 1;//Para recuperar el recorrido anterior
        saltos.addFirst(1);

        boolean encontrado = false;
        //
        auxCola.addFirst(or);
        int longCola = 1;
        //
        NodoVert auxV;
        NodoAdy auxA;

        while (posCola <= longCola && !encontrado) {
            auxV = (NodoVert) auxCola.get(posCola);
            //Colocar en la cola
            auxA = auxV.getPrimerAdy();
            while (auxA != null && !encontrado) {
                posSalto++;
                if (auxCola.indexOf(auxA.getVertice()) < 0) {//Si el nodo no fue visitado anteriormente
                    if (auxA.getVertice().getElem().equals(dest)) {//Si se encuentra el destino
                        //insertar el destino en la lista a retornar (no se inserta en auxCola
                        recorrido.addFirst(auxA.getVertice().getElem());
                        encontrado = true;
                    } else {
                        contSalto++;
                        //
                        longCola++;
                        auxCola.add(auxA.getVertice());
                        //
                        saltos.add(contSalto);
                    }
                }
                auxA = auxA.getSigAdyacente();
            }
            posCola++;
            contSalto--;
        }
        //Crear la lista de recorrido más corto según la lista de saltos
        puntero = posCola - 1;
        NodoVert auxRecuperar;
        while (puntero != 0) {
            auxRecuperar = (NodoVert) auxCola.get(puntero);
            recorrido.addFirst(auxRecuperar.getElem());
            puntero = puntero - (int) saltos.get(puntero);
        }
        return recorrido;
    }*/
    public LinkedList listarEnProfundidad() {
        //Método para listar en profundidad con el algoritmo definido para grafo
        LinkedList visitados = new LinkedList();
        //define un vértice donde comenzar a recorrer
        NodoVert vertice = this.inicio;
        while (vertice != null) {
            if (visitados.indexOf(vertice.getElem()) < 0) {
                //si el vértice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(vertice, visitados);
            }
            vertice = vertice.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, LinkedList vis) {
        //Método auxiliar para listar en profundidad
        if (n != null) {
            //marca al vertice n como visitado
            vis.add(n.getElem());
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                //visita en profundidad los adyacentes de n aun no visitados
                if (vis.indexOf(ady.getVertice().getElem()) < 0) {//Si aun no fue visitado
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public LinkedList reAnchura() {
        //Método para listar en anchura
        LinkedList visitados = new LinkedList();
        NodoVert vertice = this.inicio;
        while (vertice != null) {
            if (visitados.indexOf(vertice) == -1) {
                anchuraDesde(vertice, visitados);
            }
            vertice = vertice.getSigVertice();
        }
        return visitados;
    }

    private void anchuraDesde(NodoVert aux, LinkedList visitados) {
        //Método auxiliar para reAnchura()
        Queue cola = new LinkedList();
        visitados.add(aux);
        cola.add(aux);

        NodoVert frente;
        NodoAdy auxAdy;
        while (!cola.isEmpty()) {
            frente = (NodoVert) cola.poll();
            auxAdy = frente.getPrimerAdy();
            while (auxAdy != null) {
                if (visitados.indexOf(auxAdy.getVertice()) == -1) {
                    visitados.add(auxAdy.getVertice());
                    cola.add(auxAdy.getVertice());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }

    }

    @Override
    public Grafo clone() {
        //Método para clonar el grafo retornando un nuevo objeto
        Grafo nuevoGrafo = new Grafo();
        NodoVert vertOrigen = this.inicio;
        NodoVert vertNuevo;
        NodoAdy adyOrigen;
        NodoAdy adyNuevo;
        int pos;

        //Clonar el primer vértice
        nuevoGrafo.inicio = new NodoVert(vertOrigen.getElem(), null, null);
        vertNuevo = nuevoGrafo.inicio;
        vertOrigen = vertOrigen.getSigVertice();
        //Clonar los siguientes vértices
        while (vertOrigen != null) {
            vertNuevo.setSigVertice(new NodoVert(vertOrigen.getElem(), null, null));
            vertNuevo = vertNuevo.getSigVertice();
            vertOrigen = vertOrigen.getSigVertice();
        }
        //Clonar los nodos adyacentes, como no se puede clonar las direcciones a memoria, se hace referencia a su posicion en la lista
        vertOrigen = this.inicio;
        vertNuevo = nuevoGrafo.inicio;

        while (vertOrigen != null) {
            //primer adyacente
            adyOrigen = vertOrigen.getPrimerAdy();
            pos = posLista(adyOrigen.getVertice());
            vertNuevo.setPrimerAdy(new NodoAdy(buscarPos(pos, nuevoGrafo), null, adyOrigen.getEtiqueta()));
            //punteros sobre el primer adyacente
            adyNuevo = vertNuevo.getPrimerAdy();
            adyOrigen = adyOrigen.getSigAdyacente();
            //Siguientes adyacentes
            while (adyOrigen != null) {
                pos = posLista(adyOrigen.getVertice());
                adyNuevo.setSigAdyacente(new NodoAdy(buscarPos(pos, nuevoGrafo), null, adyOrigen.getEtiqueta()));
                adyNuevo = adyNuevo.getSigAdyacente();
                adyOrigen = adyOrigen.getSigAdyacente();
            }
        }
        return nuevoGrafo;
    }

    private int posLista(NodoVert buscado) {
        //Método auxiliar para clone() ,retorna el índice del nodo buscado en la lista de adyacentes
        int pos = 0;
        NodoVert vertice = this.inicio;
        boolean encontrado = false;

        while (vertice != null && !encontrado) {
            pos++;
            if (buscado == vertice) {
                encontrado = true;

            } else {
                vertice = vertice.getSigVertice();
            }
        }
        return pos;
    }

    private NodoVert buscarPos(int pos, Grafo g) {
        //Método auxiliar para clone() donde retorna el vértice de la lista de vértices que se encuentra en la posición indicada
        NodoVert aux = g.inicio;
        int i = 0;

        while (aux != null && i <= pos) {
            i++;
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public LinkedList<NodoVert> caminoMasCortoPorEtiqueta(Object origen, Object destino) {
        //Método que retorna el camino más largo por la distancia indicada en cada etiqueta entre cada nodo
        LinkedList<LinkedList> listaDeListas = getPosiblesCaminos(origen, destino);
        LinkedList<NodoAdy> caminoMasCorto = new LinkedList();
        int longitud1 = listaDeListas.size();
        int aux;
        int menor = 0;
        LinkedList<NodoAdy> listaAux;
        int longitud2;

        //Conseguir los km acumulados del primer recorrido
        if (longitud1 != 0) {
            listaAux = listaDeListas.get(0);
            longitud2 = listaAux.size();
            aux = 0;
            //Acumula la sumatoria del comparable        
            for (int j = 0; j < longitud2; j++) {
                aux += (int) listaAux.get(j).getEtiqueta();
            }
            menor = aux;
            caminoMasCorto = listaAux;
        }
        //comparar con el resto de los recorridos
        for (int i = 1; i < longitud1; i++) {
            listaAux = listaDeListas.get(i);
            longitud2 = listaAux.size();
            //Acumula la sumatoria del comparable
            aux = 0;
            for (int j = 0; j < longitud2; j++) {
                aux += (int) listaAux.get(j).getEtiqueta();
            }
            if (aux < menor) {
                menor = aux;
                caminoMasCorto = listaAux;
            }
        }
        //Construir la lista a retornar con el valor de las claves        
        int longitud3 = caminoMasCorto.size();
        LinkedList retornar = new LinkedList();
        retornar.add(origen);
        for (int i = 0; i < longitud3; i++) {
            retornar.add(caminoMasCorto.get(i).getVertice().getElem());
        }

        return retornar;
    }

    public String toString() {
        //Método para debugging
        //Formato:
        //Vertice1---> Adyacente11 (etiqueta11) - Adyacente12 (etiqueta12)...
        //Vertice2---> Adyacente21 (etiqueta21) - Adyacente22 (etiqueta22)...
        //...
        String cadena = "";
        NodoVert auxV = this.inicio;
        NodoAdy auxA;

        while (auxV != null) {
            cadena = cadena + auxV.getElem() + "--->";

            auxA = auxV.getPrimerAdy();
            if (auxA != null) {
                cadena += auxA.getVertice().getElem() + " (" + auxA.getEtiqueta() + ")" + toString(auxA.getSigAdyacente());
            }
            cadena = cadena + "\n";
            auxV = auxV.getSigVertice();
        }
        return cadena;
    }

    private String toString(NodoAdy a) {
        //Método auxiliar para toString() para la llamada recursiva
        String retornar = "";
        if (a != null) {
            retornar = "-" + a.getVertice().getElem() + " (" + a.getEtiqueta() + ")" + toString(a.getSigAdyacente());
        }
        return retornar;
    }

    public LinkedList<Object> caminoMasLargoPorCantNodos(Object origen, Object destino) {
        //Método que retorna una lista con el camino más largo por cantidad de nodos en el recorrido
        LinkedList<LinkedList> listaDeListas = getPosiblesCaminos(origen, destino);
        LinkedList<NodoAdy> caminoMasLargo = new LinkedList();
        int longitud = listaDeListas.size();
        int aux;
        int mayor = 0;
        LinkedList listaAux;

        //Conseguir la lista con mayor cantidad de nodos
        for (int i = 0; i < longitud; i++) {
            listaAux = listaDeListas.get(i);
            aux = listaAux.size();
            if (aux > mayor) {
                mayor = aux;
                caminoMasLargo = listaAux;
            }
        }
        LinkedList retornar = new LinkedList();
        retornar.add(origen);
        //Colocar los valores del vértice en la lista a retornar
        for (int i = 0; i < mayor; i++) {
            retornar.add(caminoMasLargo.get(i).getVertice().getElem());
        }

        return retornar;
    }

    public LinkedList<Object> caminoMasLargoPorEtiqueta(Object origen, Object destino) {
        //Método que retorna el camino más largo por la distancia indicada en cada etiqueta entre cada nodo
        LinkedList<LinkedList> listaDeListas = getPosiblesCaminos(origen, destino);
        LinkedList<NodoAdy> caminoMasLargo = new LinkedList();
        int longitud1 = listaDeListas.size();
        int aux;
        int mayor = 0;
        LinkedList<NodoAdy> listaAux;
        int longitud2;
        for (int i = 0; i < longitud1; i++) {
            listaAux = listaDeListas.get(i);
            longitud2 = listaAux.size();
            //Acumula la sumatoria del comparable
            aux = 0;
            for (int j = 0; j < longitud2; j++) {
                aux += (int) listaAux.get(j).getEtiqueta();
            }
            if (aux > mayor) {
                mayor = aux;
                caminoMasLargo = listaAux;
            }
        }
        int longitud3 = caminoMasLargo.size();
        LinkedList retornar = new LinkedList();
        retornar.add(origen);
        for (int i = 0; i < longitud3; i++) {
            retornar.add(caminoMasLargo.get(i).getVertice().getElem());
        }

        return retornar;
    }

    private LinkedList<LinkedList> getPosiblesCaminos(Object origen, Object destino) {
        //Método auxiliar para caminoMasLargoPorCantNodos, caminoMasLargoPorEtiqueta y caminoMasCortoPorEtiqueta que retorna los posibles caminos de un nodo a otro
        LinkedList<NodoAdy> listaActual = new LinkedList();//Va acumulando los arcos cuando avanza y se borran los nodos cuando se retrocede, si llega a destino se clona y se suma a listaDeListas
        //Se almacenan los nodos adyacentes para tener luego acceso a su etiqueta
        LinkedList<NodoVert> visitados = new LinkedList();//Misma función que en listarEnProfundidad
        LinkedList<LinkedList> listaDeListas = new LinkedList();//Contiene todos los posibles caminos        

        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        NodoVert vertice = this.inicio;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        //Buscar los nodos origen y destino
        while ((!encontradoOrigen || !encontradoDestino) && vertice != null) {
            if (vertice.getElem().equals(origen)) {
                nodoOrigen = vertice;
                encontradoOrigen = true;
            } else if (vertice.getElem().equals(destino)) {
                nodoDestino = vertice;
                encontradoDestino = true;
            }
            vertice = vertice.getSigVertice();
        }
        //Si fueron encontrados, empieza la búsqueda de caminos
        if (encontradoOrigen && encontradoDestino) {
            getPosiblesCaminos(nodoOrigen, nodoDestino, listaActual, visitados, listaDeListas);
        }
        return listaDeListas;
    }

    private void getPosiblesCaminos(NodoVert n, NodoVert dest, LinkedList<NodoAdy> listaActual, LinkedList visitados, LinkedList listaDeListas) {
        /*Método auxiliar para la recursión de getPosiblesCaminos. En el camino no se pueden repetir nodos
        y si el camino no conduce al nodo buscado, en ningún momento se incorpora a listaDeListas*/
        if (n == dest) {
            //Aquí se clona solo cuando se llega a destino y termina la llamada recursiva
            listaDeListas.addFirst(listaActual.clone());
        } else {
            NodoAdy ady = n.getPrimerAdy();
            visitados.add(n);
            //Si no hubieran más nodos adyacentes, termina la llamada recursiva
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                if (visitados.indexOf(auxVert) == -1) {
                    listaActual.add(ady);
                    getPosiblesCaminos(auxVert, dest, listaActual, visitados, listaDeListas);
                    listaActual.removeLast();
                }
                ady = ady.getSigAdyacente();
            }
            visitados.removeLast();
        }
    }

    public LinkedList getPosiblesCaminosSinPasarPor(Object origen, Object destino, Object claveAEvitar) {
        //Método que retorna los posibles caminos de un nodo a otro sin pasar por uno indicado
        LinkedList<NodoVert> listaActual = new LinkedList();//Va acumulando los arcos cuando avanza y se borran los nodos cuando se retrocede, si llega a destino se clona y se suma a listaDeListas
        //Se almacenan los nodos adyacentes para tener luego acceso a su etiqueta
        LinkedList<NodoVert> visitados = new LinkedList();//Misma función que en listarEnProfundidad
        LinkedList<LinkedList> listaDeListas = new LinkedList();//Contiene todos los posibles caminos        

        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        boolean encontradoEvitar = false;
        NodoVert aux = this.inicio;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        NodoVert nodoAEvitar = null;
        //Buscar los nodos origen y destino
        while ((!encontradoOrigen || !encontradoDestino || !encontradoEvitar) && aux != null) {
            if (aux.getElem().equals(origen)) {
                nodoOrigen = aux;
                encontradoOrigen = true;
            } else if (aux.getElem().equals(destino)) {
                nodoDestino = aux;
                encontradoDestino = true;
            } else if (aux.getElem().equals(claveAEvitar)) {
                encontradoEvitar = true;
                nodoAEvitar = aux;
            }
            aux = aux.getSigVertice();
        }
        //Si fueron encontrados, empieza la búsqueda de caminos
        if (encontradoOrigen && encontradoDestino && encontradoEvitar) {
            getPosiblesCaminoSinPasarPor(nodoOrigen, nodoDestino, nodoAEvitar, listaActual, visitados, listaDeListas);
        }
        //Cargar las claves en la lista de listas
        int long2 = listaDeListas.size();
        int long3;
        LinkedList retornar = new LinkedList();
        LinkedList<Object> listaInterna;
        LinkedList<NodoVert> listaAux;
        for (int i = 0; i < long2; i++) {
            listaInterna = new LinkedList();
            listaAux = listaDeListas.get(i);
            long3 = listaAux.size();
            for (int j = 0; j < long3; j++) {
                listaInterna.add(listaAux.get(j).getElem());
            }
            listaInterna.addFirst(origen);
            retornar.add(listaInterna);
        }
        return retornar;
    }

    private void getPosiblesCaminoSinPasarPor(NodoVert n, NodoVert dest, NodoVert nodoEvitar, LinkedList<NodoVert> listaActual, LinkedList visitados, LinkedList listaDeListas) {
        //Es el mismo método que getPosiblesCaminos pero con una verificación extra para nodoEvitar
        if (n == dest) {
            listaDeListas.addFirst(listaActual.clone());
        } else {
            NodoAdy ady = n.getPrimerAdy();
            visitados.add(n);
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                //Aquí se agrega la verificación extra al detectarse el nodo a evitar, no se realiza ninguna llamada
                //recursiva sobre el mismo

                if (visitados.indexOf(auxVert) == -1 && ady.getVertice() != nodoEvitar) {
                    listaActual.add(ady.getVertice());
                    getPosiblesCaminoSinPasarPor(auxVert, dest, nodoEvitar, listaActual, visitados, listaDeListas);
                    listaActual.removeLast();
                }
                ady = ady.getSigAdyacente();
            }
            visitados.removeLast();
        }
    }

    public boolean verificarCaminoConKmMax(Object origen, Object destino, int kmMax) {
        //Se almacenan los nodos adyacentes para tener luego acceso a su etiqueta
        LinkedList<NodoVert> visitados = new LinkedList();//Misma función que en listarEnProfundidad        
        boolean existeCamino = false;

        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        NodoVert vertice = this.inicio;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        //Buscar los nodos
        while ((!encontradoOrigen || !encontradoDestino) && vertice != null) {
            if (vertice.getElem().equals(origen)) {
                nodoOrigen = vertice;
                encontradoOrigen = true;
            } else if (vertice.getElem().equals(destino)) {
                nodoDestino = vertice;
                encontradoDestino = true;
            }
            vertice = vertice.getSigVertice();
        }
        //Si fueron encontrados, empieza la búsqueda de caminos
        if (encontradoOrigen && encontradoDestino) {
            existeCamino = getPosiblesCaminosConMaxKm(nodoOrigen, nodoDestino, visitados, kmMax, 0);
        }
        return existeCamino;
    }

    private boolean getPosiblesCaminosConMaxKm(NodoVert n, NodoVert dest, LinkedList visitados, int cantKmMax, int sumatoria) {
        /*Método auxiliar para la recursión de verificarCaminoConKmMax. En el camino no se pueden repetir nodos
        y si el camino no conduce al nodo buscado, en ningún momento se incorpora a listaDeListas*/
        boolean existeCaminoConMaxKm = false;
        if (sumatoria <= cantKmMax) {
            if (n == dest) {
                existeCaminoConMaxKm = true;
            } else {
                NodoAdy ady = n.getPrimerAdy();
                visitados.add(n);
                //Si no hubieran más nodos adyacentes, termina la llamada recursiva
                while (ady != null && !existeCaminoConMaxKm) {
                    NodoVert auxVert = ady.getVertice();
                    if (visitados.indexOf(auxVert) == -1) {
                        sumatoria += (int) ady.getEtiqueta();
                        existeCaminoConMaxKm = getPosiblesCaminosConMaxKm(auxVert, dest, visitados, cantKmMax, sumatoria);
                        sumatoria -= (int) ady.getEtiqueta();
                    }
                    ady = ady.getSigAdyacente();
                }
                visitados.removeLast();
            }
        } else {
            existeCaminoConMaxKm = false;
        }
        return existeCaminoConMaxKm;
    }

}
