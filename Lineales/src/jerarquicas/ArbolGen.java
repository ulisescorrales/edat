/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jerarquicas;

/**
 *
 * @author ulise
 */
public class ArbolGen {
    private NodoGen raiz;
    
    public ArbolGen(){
        this.raiz=null;
    }
    
    public boolean insertar(Object elem, Object padre){
        boolean exito=false;
        NodoGen nodoPadre;
        
        if(this.raiz==null){
            this.raiz= new NodoGen(elem,null,null);
        }else{
            nodoPadre=obtenerNodo(padre,this.raiz);
        }
        return exito;
    }
    private NodoGen obtenerNodo(Object buscado, NodoGen n){
        NodoGen retornar;
        NodoGen hermano;
        if(n.getHijoIzquierdo().getElem()==buscado){
            retornar=n;
        }else{
            hermano=n.getHijoIzquierdo();
            while(hermano!=null){
                
            }
        }
        return retornar;
    }
}
