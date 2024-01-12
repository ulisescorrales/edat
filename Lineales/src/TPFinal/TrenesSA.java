/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import grafos.Grafo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ulise
 */
public class TrenesSA {

    private Diccionario estaciones;
    private Grafo rieles;
    private Diccionario trenes;
    private HashMap linea;

    public boolean agregarTren(int id, String propulsion, int cantVagonesPasaj, int cantVagonesCarga,String linea, int idVia) {
        return trenes.insertar(id, new Tren(propulsion,cantVagonesPasaj,cantVagonesCarga,linea,idVia));
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
        boolean existe=true;
        if(!estaciones.existeClave(id)){
            Estacion nuevo=new Estacion(id,calle,numCalle,ciudad,cp,cantVias,cantPlataformas);
            estaciones.insertar(id, nuevo);            
            existe=false;
        }
        return existe;
    }

    public Diccionario getEstaciones(){
        return this.estaciones;
    }
    public String eliminarEstacion(String id) {        
        String retornar="Estación no existe";
        if(estaciones.existeClave(id)){
            estaciones.eliminar(id);            
            rieles.eliminarVertice(id);
            retornar="Estación eliminada correctamente";
        }
        return retornar;
    }

    public Estacion modificarEstacion(String idEst) {
        //Retorna la estación
        Estacion laEstacion=null;        
        laEstacion=(Estacion)estaciones.obtenerDato(idEst);
        return laEstacion;
    }

    public boolean agregarLinea(String nombreLinea) {
        boolean exito = false;
        if (!linea.containsKey(nombreLinea)) {
            linea.put(nombreLinea, null);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarLinea(String nombreLinea) {
        return linea.remove(nombreLinea) != null;
    }

    public boolean quitarEstacionDeLinea(String nombreLinea, String nombreEstacion) {
        boolean exito = false;
        List lista = Arrays.asList((Object[]) linea.get(nombreLinea));
        if (lista.contains(nombreEstacion)) {
            lista.remove(nombreEstacion);
            linea.put(nombreLinea, lista);
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
}
