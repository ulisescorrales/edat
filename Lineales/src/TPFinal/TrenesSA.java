/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import grafos.Grafo;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Ulises Corrales
 */
public class TrenesSA {

    private Diccionario estaciones;
    private Grafo rieles;
    private Diccionario trenes;
    private HashMap lineas;
    
    public TrenesSA(){        
    }
    public TrenesSA(Diccionario estaciones,Diccionario trenes,HashMap lineas){
        this.estaciones=estaciones;
        this.trenes=trenes;
        this.lineas=lineas;        
    }
    public Tren getTren(Comparable id){        
        return (Tren)trenes.obtenerDato(id);
    }
    public Diccionario getTrenes(){
        return this.trenes;
    }
    
    public Estacion getEstacion(String id){
        return (Estacion)estaciones.obtenerDato(id);
    }        

    public boolean agregarTren(int id, String propulsion, int cantVagonesPasaj, int cantVagonesCarga,String linea) {
        return trenes.insertar(id, new Tren(id,propulsion,cantVagonesPasaj,cantVagonesCarga,linea));
    }

    public boolean eliminarTren(int id) {
        return trenes.eliminar(id);
    }

    //public Tren modificarTren(int id) {
        //Buscar el tren en el diccionario y devolverlo para acceder a los setters
        
   // }

    //
    public boolean agregarEstacion(String id, String calle, int numCalle, String ciudad, String cp, int cantVias, int cantPlataformas) {
        //Comprobar que no existe
        boolean exito=false;
        if(!estaciones.existeClave(id)){
            Estacion nuevo=new Estacion(id,calle,numCalle,ciudad,cp,cantVias,cantPlataformas);
            estaciones.insertar(id, nuevo);            
            exito=true;
        }
        return exito;
    }

    public Diccionario getEstaciones(){
        return this.estaciones;
    }
    public boolean eliminarEstacion(String id) {        
        boolean exito=false;
        if(estaciones.existeClave(id)){
            estaciones.eliminar(id);            
            rieles.eliminarVertice(id);
            exito=true;
        }
        return exito;
    }

    public Estacion modificarEstacion(String idEst) {
        //Retorna la estación
        Estacion laEstacion=null;        
        laEstacion=(Estacion)estaciones.obtenerDato(idEst);
        return laEstacion;
    }
    public LinkedList<String> getLinea(String nombreLinea){
        return (LinkedList)lineas.get(nombreLinea);
    }
    public boolean agregarLinea(LinkedList<String> lin) {
        boolean exito = false;
        String nombreLinea=lin.getFirst();
        if (!lineas.containsKey(nombreLinea)) {
            lineas.put(nombreLinea, lin);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarLinea(String nombreLinea) {
        return lineas.remove(nombreLinea) != null;
    }
    public boolean existeLinea(String nombreLinea){
        return lineas.containsKey(nombreLinea);
    }

    public boolean quitarEstacionDeLinea(String nombreLinea, String nombreEstacion) {
        boolean exito = false;
        LinkedList<String> lista = (LinkedList)lineas.get(nombreLinea);
        if (lista.contains(nombreEstacion)) {
            lista.remove(nombreEstacion);            
            exito = true;
        }
        return exito;
    }

    public boolean agregarRiel(String unaEstacion, String otraEstacion, int km) {
        boolean exito = false;
        if (estaciones.existeClave(unaEstacion) && estaciones.existeClave(otraEstacion)) {
            exito = true;//Como existen entonces la operación será exitosa
            rieles.insertarArco(unaEstacion, otraEstacion, km);
        }
        return exito;
    }
    public LinkedList<String> getEstacionesConSubstring(String substring){
        return this.estaciones.getSubstringList(substring);
    }
    
    public LinkedList<String> verificarLineaAsignada(int idTren){
        Tren tren=(Tren)trenes.obtenerDato(idTren);
        LinkedList lista=null;
        System.out.println(tren);        
        if(tren!=null){
            String nombreLinea=tren.getLinea();            
            lista=(LinkedList)lineas.get(nombreLinea);
        }
        return lista;
    }
}
