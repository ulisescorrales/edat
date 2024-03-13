/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import java.util.LinkedList;
import java.util.Queue;
import lineales.dinamicas.Lista;

/**
 *
 * @author ulise
 */
public class Diccionario {

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
        return this.raiz == null;
    }

    public boolean existeClave(Comparable clave) {
        return existeClave(clave, this.raiz);
    }

    private boolean existeClave(Comparable clave, NodoAVLDicc n) {
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
        LinkedList lis = new LinkedList();

        listarClavesAux(lis, this.raiz);

        return lis;
    }

    private void listarClavesAux(LinkedList lista, NodoAVLDicc n) {
        //Recorrido Inorden
        if (n != null) {
            listarClavesAux(lista, n.getIzquierdo());
            lista.add((int)n.getClave());
            listarClavesAux(lista, n.getDerecho());
        }

    }

    public LinkedList listarDatos() {
        LinkedList lis = new LinkedList();

        listarDatos(lis, this.raiz);

        return lis;
    }

    private void listarDatos(LinkedList lista, NodoAVLDicc n) {
        //Recorrido Inorden
        if (n != null) {
            listarDatos(lista, n.getIzquierdo());
            lista.add((int)n.getDato(), lista.size()+ 1);
            listarDatos(lista, n.getDerecho());
        }
    }

    public Object obtenerDato(Comparable id) {
        Object retornar = null;
        retornar = obtenerDato(this.raiz, id);
        return retornar;
    }

    private Object obtenerDato(NodoAVLDicc n, Comparable id) {
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
    public String getEstructura(){       
        int filas=this.raiz.getAltura()+1;
        String[] retornar=new String[this.raiz.getAltura()+1];
        for (int i = 0; i < this.raiz.getAltura()+1; i++) {
            retornar[i]="";
            
        }
        getEstructura(this.raiz,retornar,false);
        String estructura="";
        for (int i = filas-1; i >-1; i--) {            
            estructura+=retornar[i]+"\n";
        }
        return estructura;
    }
    private void getEstructura(NodoAVLDicc n,String[] cadenas,boolean esDerecho){
        if(n!=null){
            getEstructura(n.getIzquierdo(),cadenas,false);
            int cantSeparador=separador(n.getAltura());            
            String separador="";
            for (int i = 0; i < cantSeparador; i++) {
                separador+="-";
            }                                                   
            if(esDerecho){
                cadenas[n.getAltura()]+=(separador+n.getClave()+separador+"-");                        
            }else{
                cadenas[n.getAltura()]+=(separador+n.getClave()+separador+"|");                        
            }            
            getEstructura(n.getDerecho(),cadenas,true);
        }
    }
    
    private int separador(int altura){
        int retornar;
        if(altura==0){
            retornar=1;
        }else{
            retornar=separador(altura-1)*2+1;
        }
        return retornar;
    }
    public NodoAVLDicc obtenerClave(Comparable id) {
        NodoAVLDicc retornar = null;
        retornar = obtenerClave(this.raiz, id);
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
    
    
    public static void main(String[] args) {
        Diccionario dic=new Diccionario();
        dic.insertar(1, "");
        dic.insertar(2, "");
        dic.insertar(3, "");
        dic.insertar(4, "");
        dic.insertar(5, "");
        dic.insertar(6, "");
        dic.insertar(7, "");
        dic.insertar(8, "");
         
        //System.out.println(dic.obtenerClave(4).getIzquierdo().getClave());               
        //System.out.println(dic.obtenerClave(4).getDerecho().getClave());               
        System.out.println(dic.obtenerClave(1).getAltura());
        System.out.println(dic.getEstructura());
        System.out.println(dic.listarPorNiveles());
        /*LinkedList lista =dic.listarClaves();
        for(Object i:lista){
            System.out.print((int)i);
        }*/
    }
}
