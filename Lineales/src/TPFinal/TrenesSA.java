/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

    public TrenesSA() {
        this.estaciones = new Diccionario();
        this.trenes = new Diccionario();
        this.lineas = new HashMap();
        this.rieles = new Grafo();

    }

    public String getRielesEstructura() {
        return this.rieles.toString();
    }

    public String getTrenesEstructura() {
        return this.trenes.getEstructura();
    }

    public String getEstacionesEstructura() {
        return this.estaciones.getEstructura();
    }

    public String getLineasEstructura() {
        String retornar = "Tabla HashMap para líneas:\n";
        Iterator lineas = this.lineas.values().iterator();
        LinkedList recorrido;        
        while (lineas.hasNext()) {
            recorrido=(LinkedList)lineas.next();
            retornar += "Clave: "+recorrido.get(0)+", recorrido: "+getStringLista(recorrido) + "\n";
        }
        return retornar;
    }

    private String getStringLista(LinkedList lista) {
        String list="";
        int longitud=lista.size();
        for (int i = 0; i < longitud; i++) {
            list+=lista.get(i)+" ";
        }
        return list;
    }

    public LinkedList getLineaAsignada(int idTreb) {
        Tren tren = (Tren) this.trenes.obtenerDato(idTreb);

        return (LinkedList) this.lineas.get(tren.getLinea());
    }

    public Tren getTren(Comparable id) {
        return (Tren) trenes.obtenerDato(id);
    }

    public LinkedList getTrenesSubcadena(String subcadena) {
        return this.trenes.getSubstringList(subcadena);
    }

    public LinkedList getCaminoMasCortoPorEstaciones(String estacion1, String estacion2) {
        return this.rieles.caminoMasCortoPorCantNodos(estacion1, estacion2);
    }

    public LinkedList getCaminosPosiblesSinPasarPor(String estacion1, String estacion2, String estacion3) {
        return this.rieles.getPosiblesCaminosSinPasarPor(estacion1, estacion2, estacion3);
    }

    public LinkedList getCaminoMasCortoPorKm(String estacion1, String estacion2) {
        return this.rieles.caminoMasCortoPorKm(estacion1, estacion2);
    }

    public boolean existeCaminoConMaxKm(String estacion1, String estacion2, int kmMax) {
        return this.rieles.verificarCaminoConKmMax(estacion1, estacion2, kmMax);
    }

    public Diccionario getTrenes() {
        return this.trenes;
    }

    public Estacion getEstacion(String id) {
        return (Estacion) estaciones.obtenerDato(id);
    }

    public boolean agregarTren(int id, String propulsion, int cantVagonesPasaj, int cantVagonesCarga, String linea) {
        return trenes.insertar(id, new Tren(id, propulsion, cantVagonesPasaj, cantVagonesCarga, linea));
    }

    public boolean eliminarTren(int id) {
        return trenes.eliminar(id);
    }

    public boolean existeRiel(String estacion1, String estacion2) {
        boolean existe;

        existe = rieles.existeArco(estacion1, estacion2);

        return existe;
    }

    public Grafo getRieles() {
        return this.rieles;
    }

    public int getDistanciaRiel(String estacion1, String estacion2) {
        return (int) rieles.getEtiqueta(estacion1, estacion2);
    }

    public boolean modificarDistancia(String estacion1, String estacion2, int nuevaDist) {
        //Método que modifica la distancia entre estaciones conectadas

        return rieles.cambiarEtiqueta(estacion1, estacion2, nuevaDist);

    }

    public boolean eliminarRiel(String estacion1, String estacion2) {
        return rieles.eliminarArco(estacion1, estacion2);
    }

    public boolean agregarEstacion(String id, String calle, int numCalle, String ciudad, String cp, int cantVias, int cantPlataformas) {
        //Comprobar que no existe
        boolean exito = false;
        if (!estaciones.existeClave(id)) {
            Estacion nuevo = new Estacion(id, calle, numCalle, ciudad, cp, cantVias, cantPlataformas);
            estaciones.insertar(id, nuevo);
            exito = true;
        }
        return exito;
    }

    public Diccionario getEstaciones() {
        return this.estaciones;
    }

    public boolean eliminarEstacion(String id) {
        //Método para eliminar una estación del sistema
        boolean exito = false;
        if (estaciones.existeClave(id)) {
            estaciones.eliminar(id);
            //Actualizar grafo            
            rieles.eliminarVertice(id);
            exito = true;
        }
        return exito;
    }

    public LinkedList<String> getLinea(String nombreLinea) {
        return (LinkedList) lineas.get(nombreLinea);
    }

    public boolean existeEstacion(String nombreEst) {
        return this.estaciones.existeClave(nombreEst);
    }

    public boolean agregarLinea(LinkedList<String> lin) {
        /*Debe corroborarse que existan todas las estaciones del recorrido y que la primera estación
        no se repita en otra línea
         */
        boolean exito = false;
        String nombreLinea = lin.getFirst();

        //Comprobar que no exista una clave igual
        if (!lineas.containsKey(nombreLinea)) {
            int longitud = lin.size();
            int i = 0;
            exito = true;
            while (exito && i < longitud) {
                //Corroborar que existen las estaciones
                if (!existeEstacion(lin.get(i))) {
                    exito = false;
                }
                i++;
            }
            if (exito) {
                lineas.put(nombreLinea, lin);
            }
        }
        return exito;
    }

    public boolean eliminarLinea(String nombreLinea) {
        return lineas.remove(nombreLinea) != null;
    }

    public boolean existeLinea(String nombreLinea) {
        return lineas.containsKey(nombreLinea);
    }

    public boolean quitarEstacionDeLinea(String nombreLinea, String nombreEstacion) {
        boolean exito = false;
        LinkedList<String> lista = (LinkedList) lineas.get(nombreLinea);
        if (lista.contains(nombreEstacion)) {
            lista.remove(nombreEstacion);
            exito = true;
        }
        return exito;
    }

    public boolean agregarRiel(String unaEstacion, String otraEstacion, int km) {
        boolean exito = false;
        //Si existen las estaciones
        if (estaciones.existeClave(unaEstacion) && estaciones.existeClave(otraEstacion)) {
            exito = rieles.insertarArco(unaEstacion, otraEstacion, km);
        }
        return exito;
    }

    public LinkedList<String> getEstacionesConSubstring(String substring) {
        return this.estaciones.getSubstringList(substring);
    }

    public LinkedList<String> verificarLineaAsignada(int idTren) {
        Tren tren = (Tren) trenes.obtenerDato(idTren);
        LinkedList lista = null;
        System.out.println(tren);
        if (tren != null) {
            String nombreLinea = tren.getLinea();
            lista = (LinkedList) lineas.get(nombreLinea);
        }
        return lista;
    }

    public boolean existeLinea(int idTren, String linea) {
        return this.lineas.containsKey(linea);
    }

    public LinkedList verificarEstacionBorradaYLineas(String nombreEstacion) {
        //Cuando se borra una estación, se elimina una línea que lo contenía (se debe redefinir el recorrido)
        LinkedList lineasBorradas = new LinkedList();
        Iterator valores = this.lineas.values().iterator();
        while (valores.hasNext()) {
            LinkedList recorridoAux = (LinkedList) valores.next();
            if (recorridoAux.contains(nombreEstacion)) {
                String nombreLinea = (String) recorridoAux.getFirst();
                lineasBorradas.addFirst(nombreLinea);
                this.lineas.remove(nombreLinea);
            }
        }
        return lineasBorradas;
    }

    public LinkedList<Integer> verificarLineaBorradaYTrenes(String linea) {
        //Cuando se borra una línea, se actualiza la línea del tren como "no asignado"
        LinkedList<Tren> listaTrenes = this.trenes.listarDatos();
        int longitud = listaTrenes.size();
        LinkedList trenesModificados = new LinkedList();
        for (int i = 0; i < longitud; i++) {
            Tren unTren = listaTrenes.get(i);
            if (unTren.getLinea().equals(linea)) {
                unTren.setLinea("no asignado");
                trenesModificados.add(unTren.getIdTren());
            }
        }
        return trenesModificados;
    }

    public LinkedList verificarLineaRiel(String estacion1, String estacion2) {
        //Método que verifica si al eliminar un riel, se debe eliminar una línea que conectaba ambas estaciones
        Iterator valores = this.lineas.values().iterator();
        LinkedList lineasBorradas = new LinkedList();
        while (valores.hasNext()) {
            LinkedList recorrido = (LinkedList) valores.next();
            int longitud2 = recorrido.size() - 1;//Para el último elemento ya no habría un siguiente con quien comparar
            boolean estacionesConectadas = false;
            int i = 0;
            while (!estacionesConectadas && i < longitud2) {
                if (recorrido.get(i).equals(estacion1)) {
                    if (recorrido.get(i + 1).equals(estacion2)) {
                        estacionesConectadas = true;
                        //Agregar el nombre de la línea
                        lineasBorradas.add(recorrido.get(0));
                    } else {
                        i++;
                    }
                } else if (recorrido.get(i).equals(estacion2)) {
                    if (recorrido.get(i + 1).equals(estacion1)) {
                        estacionesConectadas = true;
                        //Agregar el nombre de la línea
                        lineasBorradas.add(recorrido.get(0));
                    } else {
                        i++;
                    }
                }
                i++;
            }
        }
        return lineasBorradas;
    }
}
