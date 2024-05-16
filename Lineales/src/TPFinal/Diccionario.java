/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Corrales Ulises
 */
public class Diccionario {
//Clase Diccionario implementado con un árbol AVL

    private NodoAVLDicc raiz = null;

    public boolean insertar(Comparable id, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {//Si el árbol es vacío
            this.raiz = new NodoAVLDicc(id, 0, null, null, dato); //Setear la raíz
        } else {
            exito = insertarAux(this.raiz, id, dato);//Recorrer recursivamente
        }
        //        
        this.raiz = reacomodar(this.raiz);//Comprobar balance
        //
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc n, Comparable id, Object dato) {
        boolean exito = true;

        if ((id.compareTo(n.getClave()) == 0)) {//Si ya existe el elemento entonces retorna false
            exito = false;
        } else if (id.compareTo(n.getClave()) < 0) {//Si elemento es menor a n.getElem()
            if (n.getIzquierdo() != null) {//Si no es nulo,  bajar por la izquierda
                exito = insertarAux(n.getIzquierdo(), id, dato);
                if (exito) {
                    n.setIzquierdo(reacomodar(n.getIzquierdo()));
                    if (n.getDerecho() != null) {
                        n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                    } else {
                        n.setAltura(n.getIzquierdo().getAltura() + 1);//Evitar el getDerecho() de null
                    }
                    //
                }
            } else {//Si es nulo, crear el hijo izquierdo                
                n.setIzquierdo(new NodoAVLDicc(id, 0, null, null, dato));
                if (n.getDerecho() != null) {
                    //Como ya se asume altura cero en HI, se consulta el del HD
                    n.setAltura(n.getDerecho().getAltura() + 1);
                } else {
                    n.setAltura(1);
                }
            }
        } else if (n.getDerecho() != null) {//Si elemento es mayor a n.getElem(), bajar por la derecha
            exito = insertarAux(n.getDerecho(), id, dato);
            if (exito) {
                n.setDerecho(reacomodar(n.getDerecho()));
                if (n.getIzquierdo() != null) {
                    n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                } else {
                    n.setAltura(n.getDerecho().getAltura() + 1);
                }                
            }
        } else {//Si HD es nulo, crear el hijo derecho nuevo
            n.setDerecho(new NodoAVLDicc(id, 0, null, null, dato));
            if (n.getIzquierdo() != null) {
                //Cómo ya se asume altura 0 de HD, se consulta la altura del HI
                n.setAltura(n.getIzquierdo().getAltura() + 1);
            } else {
                n.setAltura(1);
            }
        }
        return exito;
    }

    private NodoAVLDicc reacomodar(NodoAVLDicc pivote) {
        //Método auxiliar para reordenar el árbol AVL, retorna el nuevo nodo superior si existe rotación
        int balanceRaiz = calcularBalance(pivote);//Obtener el balance        
        int balanceHI;
        int balanceHD;
        NodoAVLDicc retornar = pivote;//En caso de rotación, se retorna el nodo superior para enlazarlo en el nivel superior        

        //Calcular balances
        //Rotaciones      
        if (balanceRaiz == 2) {
            //Calcular balance HI
            if (pivote.getIzquierdo() != null) {
                balanceHI = calcularBalance(pivote.getIzquierdo());
            } else {
                balanceHI = 0;
            }
            //Realizar rotaciones
            if (balanceHI == 1 || balanceHI == 0) {//Mismo signo
                retornar = rotarDerecha(pivote);
            } else if (balanceHI == -1) {//Distinto signo                
                pivote.setIzquierdo(rotarIzquierda(pivote.getIzquierdo()));
                retornar = rotarDerecha(pivote);
            }
        } else if (balanceRaiz == -2) {
            //Calcular balance HD
            if (pivote.getDerecho() != null) {
                balanceHD = calcularBalance(pivote.getDerecho());
            } else {
                balanceHD = 0;
            }
            if (balanceHD == -1 || balanceHD == 0) {//Mismo signo
                retornar = rotarIzquierda(pivote);
            } else if (balanceHD == 1) {//Distinto signo
                pivote.setDerecho(rotarDerecha(pivote.getDerecho()));
                retornar = rotarIzquierda(pivote);
            }

        }

        return retornar;
    }

    private int calcularBalance(NodoAVLDicc raiz) {
        //Método que calcula el balance de un nodo realizando altura(HI)-altura(HD)
        int altD = 0;
        int altI = 0;
        if (raiz != null) {
            if (raiz.getIzquierdo() == null) {
                altI = -1;
            } else {
                altI = raiz.getIzquierdo().getAltura();
            }

            if (raiz.getDerecho() == null) {
                altD = -1;
            } else {
                altD = raiz.getDerecho().getAltura();
            }
        }

        return altI - altD;
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc r) {
        //Método que realiza una rotación hacia la izquierda
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();

        h.setIzquierdo(r);
        r.setDerecho(temp);
        if (this.raiz == r) {
            this.raiz = h;
        }
        //Actualizar altura del privote
        if (r.getDerecho() != null && r.getIzquierdo() != null) {
            r.setAltura(Math.max(r.getDerecho().getAltura(), r.getIzquierdo().getAltura()) + 1);
        } else if (r.getDerecho() != null) {
            r.setAltura(r.getDerecho().getAltura() + 1);
        } else if (r.getIzquierdo() != null) {
            r.setAltura(r.getIzquierdo().getAltura() + 1);
        } else {
            r.setAltura(0);
        }

        //Actualizar altura de h
        if (h.getDerecho() != null) {
            h.setAltura(Math.max(r.getAltura(), h.getDerecho().getAltura()) + 1);
        } else {
            h.setAltura(r.getAltura() + 1);
        }

        return h;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc r) {
        //Método que realiza una rotación hacia la derecha
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        if (this.raiz == r) {
            this.raiz = h;
        }
        //Actualizar altura del privote
        if (r.getDerecho() != null && r.getIzquierdo() != null) {
            r.setAltura(Math.max(r.getDerecho().getAltura(), r.getIzquierdo().getAltura()) + 1);
        } else if (r.getDerecho() != null) {
            r.setAltura(r.getDerecho().getAltura() + 1);
        } else if (r.getIzquierdo() != null) {
            r.setAltura(r.getIzquierdo().getAltura() + 1);
        } else {
            r.setAltura(0);
        }

        //Actualizar altura de h
        if (h.getIzquierdo() != null) {
            h.setAltura(Math.max(r.getAltura(), h.getIzquierdo().getAltura()) + 1);
        } else {
            h.setAltura(r.getAltura() + 1);
        }

        return h;
    }

    public boolean esVacio() {
        //Método que retorna si es vacío
        return this.raiz == null;
    }

    public boolean existeClave(Comparable clave) {
        //Método que verifica si existe una clave
        return existeClave(clave, this.raiz);
    }

    private boolean existeClave(Comparable clave, NodoAVLDicc n) {
        //Método auxiliar que verifica si existe una clave
        boolean existe = false;
        if (n != null) {
            int comparacion = clave.compareTo(n.getClave());
            if (comparacion == 0) {
                existe = true;
            } else if (comparacion < 0) {
                existe = existeClave(clave, n.getIzquierdo());
            } else {
                existe = existeClave(clave, n.getDerecho());
            }
        }
        return existe;
    }

    public boolean eliminar(Comparable elem) {
        //Método que busca el elimento a eliminar y reacomoda los nodos según el orden, retorna éxito si el elemento existe        
        boolean exito = eliminar(elem, this.raiz, null);
        if (exito) {
            this.raiz = reacomodar(this.raiz);//Comprobar balance
        }
        return exito;
    }

    private boolean eliminar(Comparable elem, NodoAVLDicc n, NodoAVLDicc padre) {
        //Método auxiliar de eliminar que se invoca de forma recursiva
        boolean exito = false;
        if (n != null) {//Si el elemento no es nulo
            if (n.getClave().equals(elem)) {
                exito = true;//Si elemento existe, la operación será exitosa
                if (n.getIzquierdo() != null && n.getDerecho() != null) {//CASO: Si posee ambos hijos                    

                    NodoAVLDicc candidato = buscarCandidatoA(n.getIzquierdo());//Usar el candidato A (aux): el elemento mayor del subárbol izquierdo 

                    //Setear los hijos del nuevo padre
                    candidato.setDerecho(n.getDerecho());
                    if (n.getIzquierdo() != candidato) {//Si aux itera aunque sea una vez hacia la derecha, se setea el HI
                        candidato.setIzquierdo(n.getIzquierdo());
                    }
                    //Setear la altura del candidato A en su posición nueva
                    actualizarAltura(candidato);
                    //setear el padre del padre
                    setPadre(n, candidato, padre);
                    //Setear la nueva altura del padre
                    if (padre != null) {
                        actualizarAltura(n);
                    }
                } else if (n.getIzquierdo() == null && n.getDerecho() != null) {//CASO: HD no es nulo, HI es nulo
                    setPadre(n, n.getDerecho(), padre);

                } else if (n.getDerecho() == null && n.getIzquierdo() != null) {//CASO: HD es nulo, HI no es nulo
                    setPadre(n, n.getIzquierdo(), padre);
                } else {//CASO: Es hoja entonces se elimina directamente                          
                    setPadre(n, null, padre);
                }
                //Si el elemento no se encontró entonces, buscar por la derecha o la izquierda según corresponda
            } else if (elem.compareTo(n.getClave()) < 0) {//Si elemento del nodo es mayor al elemento a eliminar                
                exito = eliminar(elem, n.getIzquierdo(), n);//Ir por la izquierda
                if (exito) {
                    n.setIzquierdo(reacomodar(n.getIzquierdo()));
                    actualizarAltura(n);
                }
            } else {//Si elemento del nodo es menor a elem               
                exito = eliminar(elem, n.getDerecho(), n);//Ir por la derecha
                if (exito) {
                    n.setDerecho(reacomodar(n.getDerecho()));
                    actualizarAltura(n);
                }
            }
        }
        return exito;
    }

    private NodoAVLDicc buscarCandidatoA(NodoAVLDicc n) {
        //Método que busca al candidato A, el mayor elemento del subárbol izquierdo, y que setea al padre del mismo en null
        //También va actualizando la altura de los nodos que recorre
        NodoAVLDicc retornar;
        if (n.getDerecho() == null) {
            retornar = n;
        } else {
            retornar = buscarCandidatoA(n.getDerecho());
            if (retornar == n.getDerecho()) {//Setear en null al HD del padre del candidato A
                if (retornar.getIzquierdo() != null) {//Si el máximo del subárbol izquierdo tiene hijo izquierdo, queda como
                    //hijo derecho del nodo superior
                    n.setDerecho(retornar.getIzquierdo());
                    if (n.getIzquierdo() != null) {
                        n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                    } else {
                        n.setAltura(n.getDerecho().getAltura() + 1);
                    }
                } else {
                    n.setDerecho(null);
                    if (n.getIzquierdo() == null) {
                        n.setAltura(0);
                    } else {
                        n.setAltura(n.getIzquierdo().getAltura() + 1);
                    }
                }

            } else if (n.getIzquierdo() != null) {
                n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
            } else {
                n.setAltura(n.getDerecho().getAltura() + 1);
            }
        }
        return retornar;
    }

    private void actualizarAltura(NodoAVLDicc nodo) {
        //Módulo que actualiza la altura de un nodo recorrido por una inserción o eliminación
        if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
            nodo.setAltura(Math.max(nodo.getIzquierdo().getAltura(), nodo.getDerecho().getAltura()) + 1); //Si posee ambos hijos
        } else if (nodo.getIzquierdo() != null) {
            nodo.setAltura(nodo.getIzquierdo().getAltura() + 1);//Si solo tiene hijo izquierdo
        } else if (nodo.getDerecho() != null) {
            nodo.setAltura(nodo.getDerecho().getAltura() + 1); //Si solo tiene hijo derecho
        } else {
            nodo.setAltura(0);//Es hoja
        }
    }

    private void setPadre(NodoAVLDicc eliminar, NodoAVLDicc nuevoHijo, NodoAVLDicc padreSetear) {
        //Método que vincula el padre del padre a eliminar con su nuevo hijo
        //Caso 3: hoja o nodo interno con ambos hijos
        if (this.raiz != eliminar) {
            //Elemento a eliminar queda sin ser apuntado y se elimina
            if (padreSetear.getIzquierdo() == eliminar) {//Si el hijo es el izquierdo
                padreSetear.setIzquierdo(nuevoHijo);
            } else {//sino es el derecho
                padreSetear.setDerecho(nuevoHijo);
            }
            actualizarAltura(padreSetear);
        } else {//Si el eliminar a borrar es una raíz                       
            this.raiz = nuevoHijo;//Setear la nueva raíz
        }
    }

    public LinkedList listarClaves() {
        //Método que lista las claves del árbol en inorden
        LinkedList lis = new LinkedList();

        listarClavesAux(lis, this.raiz);

        return lis;
    }

    private void listarClavesAux(LinkedList lista, NodoAVLDicc n) {
        //Recorrido Inorden, método auxiliar para listarClaves
        if (n != null) {
            listarClavesAux(lista, n.getIzquierdo());
            lista.add(n.getClave());
            listarClavesAux(lista, n.getDerecho());
        }

    }

    public LinkedList listarDatos() {
        //Método para listar datos en inorden
        LinkedList lis = new LinkedList();

        listarDatos(lis, this.raiz);

        return lis;
    }

    private void listarDatos(LinkedList lista, NodoAVLDicc n) {
        //Recorrido Inorden, método auxiliar para listar datos
        if (n != null) {
            listarDatos(lista, n.getIzquierdo());
            lista.add(n.getDato());
            listarDatos(lista, n.getDerecho());
        }
    }

    public Object obtenerDato(Comparable id) {
        //Retorna el objeto que aloja un nodo dado una clave ingresada
        Object retornar = null;
        retornar = obtenerDato(this.raiz, id);
        return retornar;
    }

    private Object obtenerDato(NodoAVLDicc n, Comparable id) {
        //Método auxiliar para obtenerDato()
        Object retornar = null;
        int comparacion;
        if (n != null) {
            //comparacion=n.getClave().compareTo(id);
            comparacion = id.compareTo(n.getClave());
            //Si se encuentra la clave
            if (comparacion == 0) {
                retornar = n.getDato();
            } else if (comparacion < 0) {//Sino seguir buscando por izquierda               
                retornar = obtenerDato(n.getIzquierdo(), id);
            } else {//O por derecha
                retornar = obtenerDato(n.getDerecho(), id);
            }
        }
        return retornar;
    }

    public String listarPorNiveles() {
        //Método para listar por niveles
        String retornar = "";
        Queue<NodoAVLDicc> cola = new LinkedList();
        cola.add(this.raiz);
        NodoAVLDicc elemento;
        while (!cola.isEmpty()) {
            elemento = cola.poll();
            retornar += elemento.getClave();
            if (elemento.getIzquierdo() != null) {
                cola.add(elemento.getIzquierdo());
            }
            if (elemento.getDerecho() != null) {
                cola.add(elemento.getDerecho());
            }
        }
        return retornar;
    }

    private NodoAVLDicc obtenerClave(NodoAVLDicc n, Comparable id) {
        NodoAVLDicc retornar = null;
        int comparacion;
        if (n != null) {
            //comparacion=n.getClave().compareTo(id);
            comparacion = id.compareTo(n.getClave());
            //Si se encuentra la clave
            if (comparacion == 0) {
                retornar = n;
            } else if (comparacion < 0) {//Sino seguir buscando por izquierda               
                retornar = obtenerClave(n.getIzquierdo(), id);
            } else {//O por derecha
                retornar = obtenerClave(n.getDerecho(), id);
            }
        }
        return retornar;
    }

    public LinkedList<String> getSubstringList(String substring) {
        LinkedList<String> retornar = new LinkedList();
        getSubstring(this.raiz, substring, retornar);
        return retornar;
    }

    private void getSubstring(NodoAVLDicc n, String subs, LinkedList resultado) {
        //Método auxiliar para getSubstringList
        int longitudSubstring=subs.length();
        String substr;
        if (n != null) {
            //Comparar la parte inicial de la clave con subs
            substr=n.getClave().toString().substring(0, longitudSubstring);            
            if (substr.equals(subs)) {
                //Si contiene el substring, recorre en preorden 
                getSubstring(n.getIzquierdo(), subs, resultado);
                resultado.add(n.getClave());
                getSubstring(n.getDerecho(), subs, resultado);
            } else {
                int comparacion = subs.compareTo(n.getClave().toString());
                if (comparacion < 0) {
                    //Sino baja por la izquierda
                    getSubstring(n.getIzquierdo(), subs, resultado);

                } else {
                    //Sino baja por la derecha
                    getSubstring(n.getDerecho(), subs, resultado);
                }
            }
        }
    }

    public String getEstructura() {
        //Método que retorna en un String la estructura del árbol avl
        int alturaMax = this.raiz.getAltura();
        int filas = alturaMax + 1;
        //Filas a imprimir (uno por nivel)
        String[] retornar = new String[this.raiz.getAltura() + 1];
        for (int i = 0; i < this.raiz.getAltura() + 1; i++) {
            retornar[i] = "";

        }

        //Conseguir los strings de los otros niveles        
        int medioI = getEstructura(this.raiz.getIzquierdo(), retornar,
                false, alturaMax - 1, 0);
        getEstructura(this.raiz.getDerecho(), retornar,
                true, alturaMax - 1, 0);

        //conseguir el string del nivel 0     
        String agregarEspacio = "";
        medioI--;
        for (int i = 0; i < medioI; i++) {
            agregarEspacio += " ";
        }
        retornar[alturaMax] += agregarEspacio + this.raiz.getClave() + " (" + this.raiz.getAltura() + ")";

        //Recopilar la estructura
        String estructura = "";
        for (int i = filas - 1;
                i > -1; i--) {
            estructura += retornar[i] + "\n";
        }
        return estructura;
    }

    private int getEstructura(NodoAVLDicc n, String[] cadenas, boolean esDerecho, int alturaAbs, int primeraLetraPadre) {
        //Método auxiliar para imprimir la estructura del árbol avl
        //Recorrido inorden
        //alturaAbs es la altura del nodo en relación al último nivel
        //Retorna la posición de ┴ en el renglón para que el padre pueda posicionarse correctamente
        int medio = 0;
        if (n != null) {
            int cantSeparador = separador(alturaAbs);
            String separador = "";
            String agregar = "";
            String separadorVacio = "";
            int primeraLetraN;
            int agregarAux = 0;
            int longitudCadena = cadenas[alturaAbs].length();
            for (int i = 0; i < cantSeparador; i++) {
                separador += "─";
                separadorVacio += " ";
            }
            if (esDerecho) {
                String offset = "";
                if (longitudCadena > 0 && cadenas[alturaAbs].charAt(longitudCadena - 1) == '┴') {
                    int dif = primeraLetraPadre - longitudCadena - 1;
                    for (int i = 0; i < dif; i++) {
                        offset += " ";
                    }
                }
                agregar += (offset + "┴" + separador + n.getClave() + " (" + n.getAltura() + ")" + separadorVacio + " ");//el último espacio es el que separa dos separadores vacíos
                agregarAux = (offset + "┴" + separador).length() + 1;
                medio = longitudCadena + 1;
                primeraLetraN = (cadenas[alturaAbs] + "┴" + separador).length();
            } else {
                agregar = (separadorVacio + n.getClave() + " (" + n.getAltura() + ")" + separador);
                agregarAux = (separadorVacio).length() + 1;
                medio = cadenas[alturaAbs].length() + agregar.length() + 1;
                primeraLetraN = cadenas[alturaAbs].length() + separadorVacio.length() + 1;
            }
            int posIn = longitudCadena + agregarAux;
            
            StringBuilder st = new StringBuilder(agregar);
            if (!esDerecho && medio < primeraLetraPadre) {
                    //Agrega los espacios para que la división encaje con la primer letra del padre
                    int dif = primeraLetraPadre - medio;
                    String sepAux = "";                    
                    for (int i = 0; i < dif; i++) {
                        sepAux += " ";
                    }
                    st.insert(0, sepAux);
                    posIn += dif;                    
                }
            int medioI = getEstructura(n.getIzquierdo(), cadenas, false, alturaAbs - 1, posIn);
            
            //Se debe agregar a partir de la segunda posición
            if (esDerecho) {
                //Agrega las líneas - para que la primera letra encaje con la división de abajo
                if (medioI > posIn) {
                    int dif2 = medioI - posIn;
                    String sepAux = "";
                    for (int i = 0; i < dif2; i++) {
                        sepAux += "─";
                    }                    
                    st.insert(1, sepAux);
                }
                //Agregando
                if (longitudCadena == 0 || cadenas[alturaAbs].charAt(longitudCadena - 1) == ' ') {
                    int dif = primeraLetraPadre - medio;
                    String sepAux = "";
                    for (int i = 0; i < dif; i++) {
                        sepAux += " ";
                    }
                    st.insert(0, sepAux);
                    medio += dif;
                }
            } else {
                if (medioI > primeraLetraN) {
                    //agrega los espacios izquierdos para que la primera letra encaje con la división de abajo
                    int dif = medioI - primeraLetraN;                    
                    String sepAux = "";
                    for (int i = 0; i < dif; i++) {
                        sepAux += " ";
                    }
                    medio += dif;
                    st.insert(0, sepAux);
                }                
            }
            cadenas[alturaAbs] += st.toString();
            getEstructura(n.getDerecho(), cadenas, true, alturaAbs - 1, posIn);
        } else if (alturaAbs > -1 && cadenas[alturaAbs].length() > 0) {
            //Agregar la unión que falta desde el lado derecho
            int longitud = cadenas[alturaAbs].length();
            if (cadenas[alturaAbs].charAt(longitud - 1) == '─') {
                cadenas[alturaAbs] += "┴";
                medio = longitud + 1;
            }
        }
        return medio;
    }

    private int separador(int altura) {
        //Método auxiliar para conseguir la cantidad de espacios en blanco que necesita
        //de separación una clave en getEstructura() (suponiendo un dígito, para luego seguir agregando)
        int retornar = 0;
        if (altura == 0) {
            retornar = 1;
        } else if (altura > 0) {
            retornar = separador(altura - 1) * 2 + 1;
        }
        return retornar;
    }

    public NodoAVLDicc obtenerClave(Comparable id) {
        NodoAVLDicc retornar = null;
        retornar = obtenerClave(this.raiz, id);
        return retornar;
    }
   
}
