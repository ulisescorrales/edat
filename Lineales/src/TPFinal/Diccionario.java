/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import lineales.dinamicas.Lista;


/**
 *
 * @author ulise
 */
public class Diccionario {
    private NodoAVLDicc raiz=null;
   
    public boolean insertar(Comparable id,Object dato){
        boolean exito = true;
        if (this.raiz == null) {//Si el árbol es vacío
            this.raiz = new NodoAVLDicc(id, null, null, dato); //Setear la raíz
        } else {
            exito = insertarAux(this.raiz,id, dato);//Recorrer recursivamente
        }
        //        
        this.raiz = reacomodar(this.raiz);//Comprobar balance
        //
        return exito;
    }
       private boolean insertarAux(NodoAVLDicc n,Comparable id, Object dato) {        
        boolean exito = true;

        if ((id.compareTo(n.getClave()) == 0)) {//Si ya existe el elemento entonces retorna false
            exito = false;
        } else if (id.compareTo(n.getClave()) < 0) {//Si elemento es menor a n.getElem()
            if (n.getIzquierdo() != null) {//Si no es nulo,  bajar por la izquierda
                exito = insertarAux(n.getIzquierdo(), id,dato);
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
                n.setIzquierdo(new NodoAVLDicc(id, 0,null, null,dato));
                if (n.getDerecho() != null) {
                    n.setAltura(Math.max(0, n.getDerecho().getAltura()) + 1);
                } else {
                    n.setAltura(1);
                }
            }
        } else if (n.getDerecho() != null) {//Si elemento es mayor a n.getElem(), bajar por la derecha
            exito = insertarAux(n.getDerecho(), id,dato);
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
            n.setDerecho(new NodoAVLDicc(id,0, null, null, dato));
            if (n.getIzquierdo() != null) {
                n.setAltura(Math.max(0, n.getIzquierdo().getAltura()) + 1);
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
      
    public boolean esVacio(){
        return this.raiz==null;
    }
    
    public boolean existeClave(Comparable clave){
        boolean existe=false;
        return existe;
    }
    public boolean eliminar(Comparable elem) {
        //Método que busca el elimento a eliminar y reacomoda los nodos según el orden, retorna éxito si el elemento existe        
        boolean exito = eliminar(elem, this.raiz, null);
        this.raiz = reacomodar(this.raiz);//Comprobar balance
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
                    if(n.getIzquierdo()!=null){
                        n.setAltura(Math.max(n.getIzquierdo().getAltura(), n.getDerecho().getAltura()) + 1);
                    }else{
                        n.setAltura(n.getDerecho().getAltura()+1);
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
    
   public Lista listarClaves() {
        Lista lis = new Lista();

        listarClavesAux(lis, this.raiz);

        return lis;
    }

    private void listarClavesAux(Lista lista, NodoAVLDicc n) {
        //Recorrido Inorden
        if (n != null) {
            listarClavesAux(lista, n.getIzquierdo());
            lista.insertar(n.getClave(), lista.longitud() + 1);
            listarClavesAux(lista, n.getDerecho());
        }

    }
   public Lista listarDatos() {
        Lista lis = new Lista();

        listarDatos(lis, this.raiz);

        return lis;
    }

    private void listarDatos(Lista lista, NodoAVLDicc n) {
        //Recorrido Inorden
        if (n != null) {
            listarDatos(lista, n.getIzquierdo());
            lista.insertar(n.getDato(), lista.longitud() + 1);
            listarDatos(lista, n.getDerecho());
        }
    }
   public Object obtenerDato(String id){
       Object retornar=null;
       retornar=obtenerDato(this.raiz,id);
       return retornar;
   }
   private Object obtenerDato(NodoAVLDicc n,String id){
       Object retornar=null;
       int comparacion;
       if(n!=null){
           comparacion=n.getClave().compareTo(id);
           //Si se encuentra la clave
           if(comparacion==0){
               retornar=n.getDato();
           }else if(comparacion<0){//Sino seguir buscando por izquierda
               retornar=obtenerDato(n.getIzquierdo(),id);
           }else{//O por derecha
               retornar=obtenerDato(n.getDerecho(),id);
           }
       }
       return retornar;
   }
}
