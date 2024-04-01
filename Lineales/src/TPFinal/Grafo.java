/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ulise
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
        NodoVert aux = ubicarVertice(nuevoVertice);
        //Si no existe el vértice, se lo inserta en la primera posición en la lista de vértices
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        //Iterar sobre la lista de vértices hasta encontrar el objeto
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object elem) {
        //Busca el vértice en la lista de vértices y elimina los arcos unidos al mismo
        boolean encontrado = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            NodoVert auxAnt = null;
            //Buscar el vértice en la lista de adyacentes
            while (aux != null && !encontrado) {
                if (aux.getElem().equals(elem)) {
                    encontrado = true;
                    eliminarNodosAdyacentes(aux);
                    if (auxAnt != null) {
                        //Si no es el primer vértice, enlazar el anterior con el siguiente
                        auxAnt.setSigVertice(aux.getSigVertice());
                    } else {
                        //Sino setear al siguiente como el inicio de la lista
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

    public Object getEtiqueta(Object estacion1, Object estacion2) {
        //Busca el arco entre ambos nodos y retorna su etiqueta
        //Retorna -1 en caso de que no exista un arco entre un objeto y otro
        int distancia = -1;
        NodoVert aux = this.inicio;
        while (aux.getElem().equals(estacion1) && aux != null) {
            aux = aux.getSigVertice();
        }

        if (aux != null) {
            NodoAdy auxAdy = aux.getPrimerAdy();
            while (!auxAdy.getVertice().getElem().equals(estacion2) && auxAdy != null) {
                auxAdy = auxAdy.getSigAdyacente();
            }
            if (auxAdy != null) {
                distancia = (int) auxAdy.getEtiqueta();
            }
        }
        return distancia;
    }

    public boolean cambiarEtiqueta(Object estacion1, Object estacion2, int nuevaEtiqueta) {
        //Método que busca los arcos entre ambos nodos y modifica la etiqueta
        NodoVert aux = this.inicio;
        boolean exito = true;
        while (aux.getElem().equals(estacion1) && aux != null) {
            aux = aux.getSigVertice();
        }

        if (aux != null) {
            NodoAdy auxAdy = aux.getPrimerAdy();
            while (!auxAdy.getVertice().getElem().equals(estacion2) && auxAdy != null) {
                auxAdy = auxAdy.getSigAdyacente();
            }
            if (auxAdy != null) {
                auxAdy.setEtiqueta(nuevaEtiqueta);
                NodoVert aux2 = auxAdy.getVertice();
                NodoAdy auxAdy2 = aux2.getPrimerAdy();
                while (auxAdy2 != null && !auxAdy.getVertice().getElem().equals(estacion2)) {
                    auxAdy2 = auxAdy2.getSigAdyacente();
                }
                if (auxAdy2 != null) {
                    auxAdy2.setEtiqueta(nuevaEtiqueta);
                } else {
                    exito = false;
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private void eliminarNodosAdyacentes(NodoVert apuntado) {
        //Módulo que elimina los nodos adyacentes que apuntan al nodo vértice eliminado
        NodoAdy aux = apuntado.getPrimerAdy();//Apunta a cada adyacente del vértice a eliminar
        NodoAdy aux2;//Será el nodo adyacente cuyo vértice tiene enlace con el nodo vertice a eliminar
        NodoAdy antAux2 = null;
        boolean encontrado = false;

        //Por cada adyacente en la lista de adyacentes
        while (aux != null) {
            //Buscar el nodo adyacente que apunta al vértice a eliminar (apuntado)
            aux2 = aux.getVertice().getPrimerAdy();
            while (aux2 != null && !encontrado) {
                if (aux2.getVertice() == apuntado) {
                    encontrado = true;
                    //Enlazar el nodo adyacente anterior a aux2 con el siguiente del mismo
                    if (antAux2 != null) {
                        antAux2.setSigAdyacente(aux2.getSigAdyacente());
                    } else {
                        //Si es el primer elemento, setear el siguiente como primer adyacente
                        aux.getVertice().setPrimerAdy(aux2.getSigAdyacente());
                    }
                } else {
                    antAux2 = aux2;
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
        //Método para saber si existe un vértice en la lista de NodoVert
        boolean existe = false;
        NodoVert aux = inicio;
        while (aux != null && !existe) {
            if (aux.getElem().equals(elem)) {
                existe = true;
            }
            aux = aux.getSigVertice();
        }
        return existe;
    }

    public boolean insertarArco(Object unVertice, Object otroVertice, int etiqueta) {
        boolean exito = false;
        NodoVert nodo1;
        NodoVert nodo2;
        NodoVert aux = this.inicio;

        //Obtener los nodos
        while (!exito && aux != null) {//Recorrer la lista de vértices            
            if (aux.getElem().equals(unVertice)) {//Si es uno, sigue buscando el otro                    
                nodo1 = aux;
                aux = aux.getSigVertice();
                while (aux != null && !exito) {
                    if (aux.getElem().equals(otroVertice)) {
                        nodo2 = aux;
                        vincularNodos(nodo1, nodo2, etiqueta);
                        exito = true;
                    } else {
                        aux = aux.getSigVertice();
                    }
                }
            } else if (aux.getElem().equals(otroVertice)) {
                nodo2 = aux;
                aux = aux.getSigVertice();
                while (aux != null && !exito) {
                    if (aux.getElem().equals(unVertice)) {
                        nodo1 = aux;
                        vincularNodos(nodo1, nodo2, etiqueta);
                        exito = true;
                    } else {
                        aux = aux.getSigVertice();
                    }
                }
            } else {
                aux = aux.getSigVertice();
            }
        }
        return exito;
    }

    private void vincularNodos(NodoVert unNodo, NodoVert otroNodo, int etiq) {
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

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        grafo.insertarVertice("A");
        grafo.insertarVertice("B");
        grafo.insertarVertice("C");
        grafo.insertarVertice("D");
        grafo.insertarVertice("E");
        grafo.insertarVertice("F");
        grafo.insertarVertice("G");
        System.out.println(grafo.insertarArco("A", "B", 23));
        System.out.println(grafo.insertarArco("A", "C", 23));
        System.out.println(grafo.insertarArco("B", "F", 23));
        System.out.println(grafo.insertarArco("F", "G", 23));
        System.out.println(grafo.insertarArco("G", "D", 23));
        System.out.println(grafo.insertarArco("C", "D", 23));
        System.out.println(grafo.insertarArco("D", "G", 23));
        System.out.println(grafo.insertarArco("C", "E", 23));
        grafo.reAnchura();
        
    }

    public boolean eliminarArco(Object unVertice, Object otroVertice) {
        //Método que elimina un arco entre dos vértices eliminando los nodos adyacentes correspondientes 
        //en ambos sentidos
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        NodoVert aux = this.inicio;
        NodoAdy nodoAdyAux;

        while (aux != null && !encontrado1) {
            if (aux.getElem().equals(unVertice) || aux.getElem().equals(otroVertice)) { //Si es algún vértice buscado
                encontrado1 = true;
                if (aux.getElem().equals(unVertice)) {
                    //Fue encontrado unvertice, elimina el nodo adyacente que apunta a otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(aux, otroVertice);
                    //Se elimina el nodo adyacente que apunta a unVertice
                    if (nodoAdyAux != null) {
                        encontrado2 = true;
                        eliminarNodoAdyacente(nodoAdyAux.getVertice(), aux.getElem());
                    }
                } else {
                    //El encontrado fue el otroVertice
                    nodoAdyAux = eliminarNodoAdyacente(aux, unVertice);
                    if (nodoAdyAux != null) {
                        encontrado2 = true;
                        eliminarNodoAdyacente(nodoAdyAux.getVertice(), aux.getElem());
                    }
                }
            } else {
                aux = aux.getSigVertice();
            }
        }
        return encontrado2;
    }

    private NodoAdy eliminarNodoAdyacente(NodoVert vertice, Object eliminar) {
        /*Módulo que elimina un nodo adyacente en la lista del nodo vértice
        Retorna el nodo adyacente eliminado para poder reutilizarlo en el método de origen*/
        NodoAdy aux = vertice.getPrimerAdy();
        NodoAdy antAux = null;
        boolean encontrado = false;

        while (aux != null && !encontrado) {//Busca el nodo adyacente a eliminar
            if (aux.getVertice().getElem().equals(eliminar)) {
                encontrado = true;
                if (antAux != null) {//Si no es el primer adyacente, une antAux con el siguiente de aux
                    antAux.setSigAdyacente(aux.getSigAdyacente());
                } else {//Sino se setea el primer adyacente del vértice con el siguiente de aux
                    vertice.setPrimerAdy(aux.getSigAdyacente());
                }
            } else {
                antAux = aux;
                aux = aux.getSigAdyacente();
            }
        }
        return aux;
    }

    public boolean existeArco(Object unNodo, Object otroNodo) {
        //Método para saber si dos nodos están conectados
        NodoVert aux = this.inicio;
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        NodoAdy auxAdy;

        while (aux != null && !encontrado1) {
            if (aux.getElem().equals(unNodo) || aux.getElem().equals(otroNodo)) {//Si encuentra alguno de los dos nodos
                //sigue buscando el otro nodo en su lista de adyacentes
                encontrado1 = true;
                auxAdy = aux.getPrimerAdy();
                if (aux.getElem().equals(unNodo)) {
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
                aux = aux.getSigVertice();
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
            LinkedList visitados = new LinkedList();
            exito = existeCaminoAux(auxO, destino, visitados);
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

    public LinkedList caminoMasCorto(Object origen, Object destino) {
        //verifica si ambos vértices existen
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
        }
        return lis;
    }

    private LinkedList caminoMasCorto(NodoVert or, Object dest, LinkedList recorrido) {
        //Método que busca el camino más corto entre el nodo origen y el nodo destino
        //Usa el recorrido en anchura hasta encontrar el destino para luego construir la lista
        LinkedList auxCola = new LinkedList();
        LinkedList saltos = new LinkedList();
        int posCola = 1;
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
                        auxCola.add(longCola, auxA.getVertice());
                        //
                        saltos.add(longCola, contSalto);
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
    }

    public String toStringLista(LinkedList lis) {
        String cadena = "";
        int longitud = lis.size();
        int i = 1;
        NodoVert aux;
        while (i <= longitud) {
            aux = (NodoVert) lis.get(i);
            cadena = cadena + aux.getElem() + " - ";
            i++;
        }
        return cadena;
    }

    public LinkedList caminoMasLargo(Object origen, Object destino) {
        //verifica si ambos vértices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        LinkedList lis = null;

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

            lis = caminoMasLargo(auxO, destino);
        }
        return lis;
    }

    private LinkedList caminoMasLargo(NodoVert or, Object dest) {
        /*Método que busca el camino más corto entre el nodo origen y el nodo destino
        Usa el recorrido en anchura hasta encontrar el destino para luego construir la lista
        Misma metodología que caminoMasCorto pero con la diferencia de que:
        No se inserta en la cola el nodo del que derivó el mismo ni el nodo origen (puede repetirse)
        Se insertan elementos en la cola hasta que no existan más posibilidades de insertar, luego
        la lista de recorrido mas largo se construye a partir del último nodo destino registrado en la cola*/
        LinkedList auxCola = new LinkedList();
        LinkedList saltos = new LinkedList();
        LinkedList recorrido = new LinkedList();
        int posCola = 1;
        int contSalto = 0;
        int puntero;

        NodoVert anterior;

        int posSalto = 1;//Para recuperar el recorrido anterior
        saltos.addFirst(1);

        //
        auxCola.addFirst(or);
        int longCola = 1;
        //
        NodoVert auxV;
        NodoAdy auxA;

        while (posCola <= longCola) {
            auxV = (NodoVert) auxCola.get(posCola);
            //Colocar en la cola
            if (!auxV.getElem().equals(dest)) {//Con el nodo destino no se agregan sus adyacentes
                auxA = auxV.getPrimerAdy();
                while (auxA != null) {
                    posSalto++;
                    anterior = (NodoVert) auxCola.get(posCola - (int) saltos.get(posCola));
                    if (auxA.getVertice() != or && auxA.getVertice() != anterior) {//Si no es el anterior o el origen

                        contSalto++;
                        //
                        longCola++;
                        auxCola.add(longCola, auxA.getVertice());
                        //
                        saltos.add(longCola, contSalto);

                    }
                    auxA = auxA.getSigAdyacente();
                }
            }
            posCola++;
            contSalto--;
        }
        System.out.println(toStringLista(auxCola));
        System.out.println(saltos.toString());
        //Ubicar el puntero en el último nodo destino encontrado
        puntero = longCola;
        NodoVert auxRecuperar = (NodoVert) auxCola.get(puntero);
        while (!auxRecuperar.getElem().equals(dest)) {
            puntero = puntero - 1;
            auxRecuperar = (NodoVert) auxCola.get(puntero);
        }

        //A partir del último nodo destino encontrado, armar la lista con los anteriores a cada nodo        
        while (puntero != 0) {
            auxRecuperar = (NodoVert) auxCola.get(puntero);
            recorrido.add(1, auxRecuperar.getElem());
            puntero = puntero - (int) saltos.get(puntero);
        }
        return recorrido;
    }

    public LinkedList listarEnProfundidad() {
        //Método para listar en profundidad con el algoritmo definido para grafo
        LinkedList visitados = new LinkedList();
        //define un vértice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.indexOf(aux.getElem()) < 0) {
                //si el vértice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
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
    public LinkedList reAnchura(){
        //Método para listar en anchura
        LinkedList visitados=new LinkedList();        
        NodoVert aux=this.inicio;
        while(aux!=null){
            if(visitados.indexOf(aux)==-1){
                anchuraDesde(aux,visitados);
            }
            aux=aux.getSigVertice();
        }
        return visitados;
    }
    private void anchuraDesde(NodoVert aux,LinkedList visitados){
        //Método auxiliar para reAnchura()
        Queue cola=new LinkedList();
        visitados.add(aux);
        System.out.println(aux.getElem());
        cola.add(aux);
        
        NodoVert frente;
        NodoAdy auxAdy;
        while(!cola.isEmpty()){
            frente=(NodoVert)cola.poll();
            auxAdy=frente.getPrimerAdy();
            while(auxAdy!=null){
                if(visitados.indexOf(auxAdy.getVertice())==-1){
                    visitados.add(auxAdy.getVertice());                    
                    cola.add(auxAdy.getVertice());
                }
                auxAdy=auxAdy.getSigAdyacente();
            }
        }
        
    }
   
    @Override
    public Grafo clone() {
        //Método para clonar el grafo retornando un nuevo objeto
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

    public LinkedList<NodoVert> caminoMasCortoPorKm(Object origen, Object destino) {
        //Listar todos los posibles caminos y evaluar el más corto según la sumatoria
        //de los km que acumula cada nodo
        NodoVert or = null;
        NodoVert dest = null;
        NodoVert aux = this.inicio;
        //Si no existe camino retornar una lista vacía; si no existe algún elemento, retorna null
        LinkedList<NodoVert> camino = null;

        //Encontrar los vértices
        while (or == null && dest == null & aux != null) {
            if (aux.getElem().equals(origen)) {
                or = aux;
            }
            if (aux.getElem().equals(origen)) {
                dest = aux;
            }
        }
        //Vértices encontrados
        if (or != null && dest != null) {
            camino = new LinkedList<NodoVert>();
            LinkedList<LinkedList<NodoVert>> listaRecorridos = encontrarRecorridos(or, dest);
            //Si existe un camino entre ambos vértices
            if (!listaRecorridos.isEmpty()) {
                int longitud = listaRecorridos.size();
                LinkedList<NodoVert> camMasCorto = listaRecorridos.get(0);
                LinkedList<NodoVert> listaActual;
                for (int i = 1; i < longitud; i++) {
                    listaActual = listaRecorridos.get(i);
                    if (listaActual.size() < camMasCorto.size()) {
                        camMasCorto = listaActual;
                    }
                }
            }
        }
        return camino;
    }

    private LinkedList<LinkedList<NodoVert>> encontrarRecorridos(NodoVert origen, NodoVert dest) {
        LinkedList<LinkedList<NodoVert>> retornar = new LinkedList();
        LinkedList<NodoVert> visitados = new LinkedList();
        visitados.add(origen);
        //Listar desde todos los adyacentes
        NodoAdy ady = this.inicio.getPrimerAdy();
        while (ady != null) {
            if (ady.getVertice() != dest) {

            }
            ady = ady.getSigAdyacente();
        }

        return retornar;
    }

    public String toString() {
        //Formato:
        //Vertice1---> Adyacente1 (etiqueta1) - Adyacente1 (etiqueta2)...
        String cadena = "";
        NodoVert auxV = this.inicio;
        NodoAdy auxA;

        while (auxV != null) {
            cadena = cadena + auxV.getElem() + "--->";

            auxA = auxV.getPrimerAdy();
            while (auxA != null) {
                cadena = cadena + auxA.getVertice().getElem() + " (" + auxA.getEtiqueta() + ")" + " - ";
                auxA = auxA.getSigAdyacente();
            }
            cadena = cadena + "\n";
            auxV = auxV.getSigVertice();
        }
        return cadena;
    }

    public LinkedList<Object> getCaminoMasLargoPorCantNodos(Object origen, Object destino) {
        LinkedList<LinkedList> listaDeListas = getPosiblesCaminos(origen, destino);
        LinkedList<grafos.NodoAdy> caminoMasLargo = new LinkedList();
        int longitud = listaDeListas.size();
        int aux;
        int mayor = 0;
        LinkedList listaAux;

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
        for (int i = 0; i < mayor; i++) {
            retornar.add(caminoMasLargo.get(i).getVertice().getElem());
        }

        return retornar;
    }

    public LinkedList<Object> getCaminoMasLargoPorKm(Object origen, Object destino) {
        LinkedList<LinkedList> listaDeListas = getPosiblesCaminos(origen, destino);
        LinkedList<grafos.NodoAdy> caminoMasLargo = new LinkedList();
        int longitud1 = listaDeListas.size();
        int aux;
        int mayor = 0;
        LinkedList<grafos.NodoAdy> listaAux;
        int longitud2;
        for (int i = 0; i < longitud1; i++) {
            listaAux = listaDeListas.get(i);
            longitud2 = listaAux.size();
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
        //Método que retorna los posibles caminos de un nodo a otro
        LinkedList<NodoAdy> listaActual = new LinkedList();//Va acumulando los arcos cuando avanza y se borran los nodos cuando se retrocede, si llega a destino se clona y se suma a listaDeListas
        //Se almacenan los nodos adyacentes para tener luego acceso a su etiqueta
        LinkedList<NodoVert> visitados = new LinkedList();//Misma función que en listarEnProfundidad
        LinkedList<LinkedList> listaDeListas = new LinkedList();//Contiene todos los posibles caminos        

        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        NodoVert aux = this.inicio;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        //Buscar los nodos origen y destino
        while ((!encontradoOrigen || !encontradoDestino) && aux != null) {
            if (aux.getElem().equals(origen)) {
                nodoOrigen = aux;
                encontradoOrigen = true;
            } else if (aux.getElem().equals(destino)) {
                nodoDestino = aux;
                encontradoDestino = true;
            }
            aux = aux.getSigVertice();
        }
        //Si fueron encontrados, empieza la búsqueda de caminos
        if (encontradoOrigen && encontradoDestino) {
            getPosiblesCaminos(nodoOrigen, nodoDestino, listaActual, visitados, listaDeListas);
        }
        return listaDeListas;
    }

    private void getPosiblesCaminos(NodoVert n, NodoVert dest, LinkedList<NodoAdy> listaActual, LinkedList visitados, LinkedList listaDeListas) {
        //Método auxiliar para la recursión de getPosiblesCaminos. En el camino no se pueden repetir nodos
        //y si el camino no conduce al nodo buscado, en ningún momento se incorpora a listaDeListas
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
        //Método que retorna los posibles caminos de un nodo a otro
        LinkedList<NodoAdy> listaActual = new LinkedList();//Va acumulando los arcos cuando avanza y se borran los nodos cuando se retrocede, si llega a destino se clona y se suma a listaDeListas
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
        return listaDeListas;
    }

    private void getPosiblesCaminoSinPasarPor(NodoVert n, NodoVert dest, NodoVert nodoEvitar, LinkedList<NodoAdy> listaActual, LinkedList visitados, LinkedList listaDeListas) {
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
                    listaActual.add(ady);
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
        LinkedList<grafos.NodoVert> visitados = new LinkedList();//Misma función que en listarEnProfundidad        
        boolean existeCamino = false;

        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        NodoVert aux = this.inicio;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        //Buscar los nodos
        while ((!encontradoOrigen || !encontradoDestino) && aux != null) {
            if (aux.getElem().equals(origen)) {
                nodoOrigen = aux;
                encontradoOrigen = true;
            } else if (aux.getElem().equals(destino)) {
                nodoDestino = aux;
                encontradoDestino = true;
            }
            aux = aux.getSigVertice();
        }
        //Si fueron encontrados, empieza la búsqueda de caminos
        if (encontradoOrigen && encontradoDestino) {
            existeCamino = getPosiblesCaminosConMaxKm(nodoOrigen, nodoDestino, visitados, kmMax, 0);
        }
        return existeCamino;
    }

    private boolean getPosiblesCaminosConMaxKm(NodoVert n, NodoVert dest, LinkedList visitados, int cantKmMax, int sumatoria) {
        //Método auxiliar para la recursión de getPosiblesCaminos. En el camino no se pueden repetir nodos
        //y si el camino no conduce al nodo buscado, en ningún momento se incorpora a listaDeListas
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
