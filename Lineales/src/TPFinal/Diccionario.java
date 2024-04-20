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
public class Diccionario {
//Clase Diccionario implementado con un árbol AVL

    private NodoAVLDicc raiz = null;

    public boolean insertar(Comparable id, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {//Si el árbol es vacío
            this.raiz = new NodoAVLDicc(id, null, null, dato); //Setear la raíz
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
                    if (n.getDerecho() != null) {
                        n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                    } else {
                        n.setAltura(n.getIzquierdo().getAltura() + 1);//Evitar el getDerecho() de null
                    }
                    //-------------                                         
                    n.setIzquierdo(reacomodar(n.getIzquierdo()));
                    n.setAltura(n.getIzquierdo().getAltura() + 1);
                    //--------------
                }
            } else {//Si es nulo, crear el hijo izquierdo                
                n.setIzquierdo(new NodoAVLDicc(id, 0, null, null, dato));
                if (n.getDerecho() != null) {
                    //Como ya se asume altura cero en HD, se consulta el del HI
                    n.setAltura(n.getDerecho().getAltura() + 1);
                } else {
                    n.setAltura(1);
                }
            }
        } else if (n.getDerecho() != null) {//Si elemento es mayor a n.getElem(), bajar por la derecha
            exito = insertarAux(n.getDerecho(), id, dato);
            if (exito) {
                if (n.getIzquierdo() != null) {
                    n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                } else {
                    n.setAltura(n.getDerecho().getAltura() + 1);
                }
                //-------------                                         
                n.setDerecho(reacomodar(n.getDerecho()));
                n.setAltura(n.getDerecho().getAltura() + 1);
                //--------------
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
            if (balanceHI == 0 || balanceHI == 1) {//Mismo signo
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
            if (balanceHD == -1) {//Mismo signo
                retornar = rotarIzquierda(pivote);
            } else if (balanceHD == 0 || balanceHD == 1) {//Distinto signo
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
        //actualizar altura de r, el resto queda igual
        if (r.getDerecho() != null) {//Si el pivote quedó con algún hijo
            r.setAltura(r.getDerecho().getAltura() + 1);
        } else if (r.getIzquierdo() != null) {
            r.setAltura(r.getIzquierdo().getAltura() + 1);
        } else {
            r.setAltura(0);
        }

        h.setAltura(r.getAltura() + 1);
        if (h.getDerecho() != null || h.getIzquierdo() != null) {
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
        //actualizar alturas            
        if (r.getDerecho() != null || r.getIzquierdo() != null) {//Si el pivote quedó con algún hijo
            r.setAltura(r.getDerecho().getAltura() + 1);
        } else {
            r.setAltura(0);
        }

        h.setAltura(r.getAltura() + 1);
        if (h.getDerecho() != null || h.getIzquierdo() != null) {
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

                    NodoAVLDicc aux = buscarCandidatoA(n.getIzquierdo());//Usar el candidato A (aux): el elemento mayor del subárbol izquierdo 

                    //Setear los hijos del nuevo padre
                    aux.setDerecho(n.getDerecho());
                    if (n.getIzquierdo() != aux) {//Si aux itera aunque sea una vez hacia la derecha, se setea el HI
                        aux.setIzquierdo(n.getIzquierdo());
                    }
                    //Setear la altura del candidato A en su posición nueva
                    actualizarAltura(aux);
                    //setear el padre del padre
                    setPadre(n, aux, padre);
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
        if (n != null) {
            if (n.getClave().toString().contains(subs)) {
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
        System.out.println("Altura max "+alturaMax +" raíz: "+this.raiz.getClave());
        int filas = alturaMax + 1;
        //Filas a imprimir (uno por nivel)
        String[] retornar = new String[this.raiz.getAltura() + 1];
        for (int i = 0; i < this.raiz.getAltura() + 1; i++) {
            retornar[i] = "";

        }
        //conseguir el string del nivel 0
        int cantSeparador = separador(this.raiz.getAltura());
        String separadorVacio = "";
        for (int i = 0; i < cantSeparador; i++) {
            separadorVacio += " ";
        }
        retornar[alturaMax] += (separadorVacio + this.raiz.getClave());

        //Conseguir los strings de los otros niveles
        getEstructura(this.raiz.getIzquierdo(), retornar,
                false, alturaMax - 1, 0);
        getEstructura(this.raiz.getDerecho(), retornar,
                true, alturaMax - 1, 0);
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
        //Retorna la cantidad de dígitos de la clave que servirá para agregar separador a la izquierda o a la derecha
        int medio = 0;
        if (n != null) {
            System.out.println(n.getClave()+" - "+alturaAbs);
            int cantSeparador = separador(alturaAbs);
            String separador = "";
            String agregar = "";
            String separadorVacio = "";
            int agregarAux = 0;
            for (int i = 0; i < cantSeparador; i++) {
                separador += "─";
                separadorVacio += " ";
            }
            if (esDerecho) {
                agregar = ("┴" + separador + n.getClave() + separadorVacio + " ");//el último espacio es el que separa dos separadores vacíos
                agregarAux = ("┴" + separador).length() + 1;
                medio = (cadenas[alturaAbs]).length() + 1;
            } else {
                agregar = (separadorVacio + n.getClave() + separador);
                agregarAux = (separadorVacio).length() + 1;
                medio = cadenas[alturaAbs].length() + agregar.length() + 1;
            }
            //System.out.println(n.getClave()+" medio: "+medio);
            int posIn = (cadenas[alturaAbs]).length() + agregarAux;
            //System.out.println(n.getClave()+" posIn:"+posIn);
            int medioI = getEstructura(n.getIzquierdo(), cadenas, false, alturaAbs - 1, posIn);

            if (medio < primeraLetraPadre) {
                //System.out.println("letraPadre: "+ primeraLetraPadre);                    
                int dif = primeraLetraPadre - medio;
                System.out.println(n.getClave() + " dif:" + dif);
                String sepAux = "";
                for (int i = 0; i < dif; i++) {
                    sepAux += " ";
                }
                agregar = sepAux + agregar;
            }
            StringBuilder st=new StringBuilder(agregar);            
            //Se debe agregar a partir de la segunda posición
            if (esDerecho) {
                if (medioI > posIn) {
                    int dif2 = medioI - posIn;
                    String sepAux = "";
                    for (int i = 0; i < dif2; i++) {
                        sepAux += "─";
                    }
                    st.insert(1, sepAux);
                }
            }
            cadenas[alturaAbs] += st.toString();
//            System.out.println(n.getClave()+" medioHijoIzq:" +medioHijo);            
            //Siendo hijo
            /*if(esDerecho){
                
            }else{
                int medioPropio=(cadenas[alturaAbs]+agregar).length()+1;
                System.out.println(n.getClave()+ "medioPropio:"+medioPropio);
                if(medioPropio<medioHijo){                    
                    int dif=medioHijo-medioPropio;
                    System.out.println(n.getClave()+" "+dif);
                    String sepAux="";
                    for (int i = 0; i < dif; i++) {
                        sepAux+=" ";                        
                    }
                    agregar=sepAux+agregar;
                }
            }*/
            int medioDer = getEstructura(n.getDerecho(), cadenas, true, alturaAbs - 1, posIn);
            /*if (medioDer > posIn) {
                int dif2 = primeraLetraPadre - medio;                
                String sepAux = "";
                for (int i = 0; i < dif2; i++) {
                    sepAux += "─";
                }
                agregar = sepAux + agregar;
            }*/
        } else if (alturaAbs == 0 && cadenas[alturaAbs].length() > 0) {
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
        int retornar=0;
        if (altura == 0) {
            retornar = 1;            
        } else if(altura>0){
            retornar = separador(altura - 1) * 2 + 1;
        }
        return retornar;
    }

    public NodoAVLDicc obtenerClave(Comparable id) {
        NodoAVLDicc retornar = null;
        retornar = obtenerClave(this.raiz, id);
        return retornar;
    }

    public static void main(String[] args) {
        Diccionario dic = new Diccionario();
        /*dic.insertar("Hola", "");
        dic.insertar("Mar Del Plata", "");
        dic.insertar("San Luis", "");
        dic.insertar("San Clemente", "");
        dic.insertar("Bahía Blanca", "");
        dic.insertar("Villa María", "");
        dic.insertar("Neuquén", "");
        dic.insertar("Colón de Santa Fe", "");
        dic.insertar("Rosario", "");
        dic.insertar("River Plate", "");
        dic.insertar("Comahue", "");
        dic.insertar("Arrufó", "");
         */
        
        dic.insertar(11, "");
        dic.insertar(2, "");
        dic.insertar(23, "");
        dic.insertar(4, "");
        dic.insertar(5, "");
        dic.insertar(6, "");
        dic.insertar(77, "");
        dic.insertar(8, "");
        dic.insertar(9, "");
        dic.insertar(10, ""); 
        //System.out.println(dic.obtenerClave(8).getAltura());
        System.out.println(dic.obtenerClave(8).getIzquierdo().getIzquierdo().getIzquierdo().getClave());
        System.out.println(dic.getEstructura());

    }
}
