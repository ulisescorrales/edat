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

    
    public LinkedList caminoMasCortoPorCantNodos(Object origen, Object destino) {
        //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        LinkedList caminoMasCorto = new LinkedList();
        LinkedList<Object> retornar = new LinkedList();

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
            LinkedList visitados = new LinkedList();            
            //Empieza llamada recursiva, retorna lista de NodoVert
            getPosibleCaminoMasCortoPorCantNodos(auxO, auxD, visitados, caminoMasCorto, 0, -1);
            if(!caminoMasCorto.isEmpty()){
                caminoMasCorto.add(destino);
            }
        }

        return caminoMasCorto;
    }

    private int getPosibleCaminoMasCortoPorCantNodos(NodoVert n, NodoVert dest, LinkedList<Object> visitados, LinkedList caminoMasCortoTemp, int cantNodosAcumulados, int cantNodosCaminoMasCorto) {
        /*Método auxiliar para la recursión de getCaminoMasCortoPorCantNodos. Realiza un recorrido en profundidad hasta llegar a destn, con tope en 
        cantNodosAcumulados, retorna dicha variable que es la mínima cantidad de nodos encontrado para el camino mas corto
        durante la recursión*/
        if (n == dest) {
            //Aquí se clona solo cuando se llega a destino y termina la llamada recursiva                    
            caminoMasCortoTemp.clear();
            caminoMasCortoTemp.addAll((LinkedList) visitados);
            
            //Retorna la cantidad de nodos antes de llegar al destino            
            cantNodosCaminoMasCorto = cantNodosAcumulados - 1;      
            //System.out.println(visitados.toString()+dest.toString());
        } else if (cantNodosAcumulados < cantNodosCaminoMasCorto || cantNodosCaminoMasCorto == -1) {
            //System.out.println(visitados.toString());
            visitados.add(n.getElem());    
            //Recorrer los nodos adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                //Si el nodo vértice del adyacente no fue visitado
                if (visitados.indexOf(auxVert.getElem()) == -1) {
                    cantNodosCaminoMasCorto = getPosibleCaminoMasCortoPorCantNodos(auxVert, dest, visitados, caminoMasCortoTemp, cantNodosAcumulados + 1, cantNodosCaminoMasCorto);
                }
                ady = ady.getSigAdyacente();
            }
            visitados.removeLast();
        }
        return cantNodosCaminoMasCorto;
    }

    public LinkedList caminoMasCortoPorEtiqueta(Object origen, Object destino) {
         //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        LinkedList caminoMasCorto = new LinkedList();        

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
            LinkedList visitados = new LinkedList();            
            getCaminoMasCortoPorEtiqueta(auxO, auxD, visitados, caminoMasCorto, 0, -1);
            if(!caminoMasCorto.isEmpty()){
                caminoMasCorto.add(destino);
            }
        }

        return caminoMasCorto;
    }
    
    private int getCaminoMasCortoPorEtiqueta(NodoVert n, NodoVert dest, LinkedList visitados, LinkedList caminoMasCortoTemp, int sumatoriaAcumulada, int sumatoriaMenor) {
        /*Método auxiliar para la recursión de getCaminoMasCortoPorEtiqueta. Retorna la sumatoria de etiquetas del recorrido encontrado
        durante la llamada recursiva*/
        //Sumatoria menor actuaría como tope ante sumatoria acumulada                
        if (n == dest && ((sumatoriaAcumulada < sumatoriaMenor)|| sumatoriaMenor==-1)) {
            //Se clona manteniendo la referencia
            caminoMasCortoTemp.clear();
            caminoMasCortoTemp.addAll((LinkedList) visitados);
            //Retorna la cantidad de nodos antes de llegar al destino            
            sumatoriaMenor = sumatoriaAcumulada;                             
        } else if (sumatoriaAcumulada < sumatoriaMenor || sumatoriaMenor == -1) {
            //visitados.add(n);            
            visitados.add(n.getElem());           
            //Recorrer los nodos adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                //Si el nodo vértice del adyacente no fue visitado
                if (visitados.indexOf(auxVert.getElem()) == -1) {
                    sumatoriaMenor = getCaminoMasCortoPorEtiqueta(auxVert, dest, visitados, caminoMasCortoTemp, sumatoriaAcumulada +(int) ady.getEtiqueta(), sumatoriaMenor);
                }
                ady = ady.getSigAdyacente();
            }
            visitados.removeLast();
        }
        return sumatoriaMenor;
    }      

    public LinkedList getPosiblesCaminosSinPasarPor(Object origen, Object destino, Object claveAEvitar) {
        //Método que retorna los posibles caminos de un nodo a otro sin pasar por uno indicado        
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
            getPosiblesCaminoSinPasarPor(nodoOrigen, nodoDestino, nodoAEvitar, visitados, listaDeListas);
        }
        //Cargar las claves en la lista de listas a retornar
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
            retornar.add(listaInterna);
        }
        return retornar;
    }

    private void getPosiblesCaminoSinPasarPor(NodoVert n, NodoVert dest, NodoVert nodoEvitar, LinkedList visitados, LinkedList listaDeListas) {
        //Método auxiliar para getPosiblesCaminosSinPasarPor
        if (n == dest) {
            visitados.add(n);
            listaDeListas.addFirst(visitados.clone());
            visitados.removeLast();
        } else {
            NodoAdy ady = n.getPrimerAdy();
            visitados.add(n);
            //Recorrer los hijos
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                /*Aquí se agrega la verificación extra al detectarse el nodo a evitar, así no se realiza ninguna llamada 
                recursiva sobre el mismo*/
                if (visitados.indexOf(auxVert) == -1 && ady.getVertice() != nodoEvitar) {                    
                    getPosiblesCaminoSinPasarPor(auxVert, dest, nodoEvitar, visitados, listaDeListas);                    
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
    
    private String toString(NodoAdy a) {
        //Método auxiliar para toString() para la llamada recursiva
        String retornar = "";
        if (a != null) {
            retornar = "-" + a.getVertice().getElem() + " (" + a.getEtiqueta() + ")" + toString(a.getSigAdyacente());
        }
        return retornar;
    }
}
