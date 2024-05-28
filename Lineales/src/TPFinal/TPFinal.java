/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TPFinal;

import TPFinal.Log.Log;
import TPFinal.Trenes.TrenesSA;
import TPFinal.Trenes.Estacion;
import TPFinal.Trenes.Tren;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corrales Ulises
 */
public class TPFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws  IOException {
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");

        //Leer el archivo DatosTPFinal.txt y realizar la carga inicial del sistema
        StringBuilder datos = new StringBuilder();
        try {
            FileReader txt = new FileReader("src/TPFinal/Datos/DatosTPFINAL.txt");
            int i;
            i = txt.read();
            while (i != -1) {
                datos.append((char) i);
                i = txt.read();
            }
            txt.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String datosString = datos.toString();

        Log logs = new Log("./src/TPFinal/logs.log");

        //Separar los tokens por líneas
        StringTokenizer lineasTok = new StringTokenizer(datosString, "\n");
        StringTokenizer atributosTok;
        String cabecera;

        TrenesSA sistema = new TrenesSA();

        logs.escribir("---EMPIEZA CARGA INICIAL---\n");
        //Separar cada línea de token y colocarlo en la lista correspondiente
        LinkedList<StringTokenizer> estacionesList = new LinkedList();
        LinkedList<StringTokenizer> trenesList = new LinkedList();
        LinkedList<StringTokenizer> rielesList = new LinkedList();
        LinkedList<StringTokenizer> lineasList = new LinkedList();
        while (lineasTok.hasMoreTokens()) {
            //Separar por ; y leer la cabecera
            atributosTok = new StringTokenizer(lineasTok.nextToken(), ";");
            cabecera = atributosTok.nextToken();
            //La cabecera indica: 'E' para estación, 'L' para línea, 'T' para tren y 'R' para riel
            switch (cabecera) {
                case "E":
                    estacionesList.add(atributosTok);
                    break;
                case "L":

                    lineasList.add(atributosTok);
                    break;
                case "T":
                    trenesList.addFirst(atributosTok);
                    break;
                case "R":
                    //Almacenar los rieles en una lista para que se carguen después de las estaciones
                    rielesList.add(atributosTok);
            }
        }

        /*Primero se cargaron las estaciones y trenes en una lista y luego aquí se cargan por orden aleatorio en
        el diccionario implementado con árbol AVL */
        cargarEstaciones(sistema, estacionesList, logs,false);
        cargarLineas(sistema, lineasList, logs);
        cargarTrenes(sistema, trenesList, logs);
        cargarRieles(sistema, rielesList, logs);

        logs.escribir("---FIN CARGA INICIAL---\n");
        //Interactuar con el menú hasta salir                                  
        mostrarMenuPrincipal(input, sistema, logs);
    }

    public static void mostrarMenuPrincipal(Scanner in, TrenesSA sist, Log lg) {
        //Entrada al menú principal
        byte opcion;
        System.out.println("Bienvenidos al sistema de TrenesSA");
        do {
            System.out.println("--------------------------------");
            System.out.println("MENÚ PRINCIPAL:");
            System.out.println("Eliga que acción desea realizar:\n"
                    + "1-Consultar información\n"
                    + "2-Agregar información\n"
                    + "3-Eliminar información\n"
                    + "4-Modificar información\n"
                    + "-1-Terminar");

            opcion = in.nextByte();
            switch (opcion) {
                case 1:
                    mostrarSubMenuConsulta(in, sist);
                    break;
                case 2:
                    mostrarSubMenuAgregar(in, sist, lg);
                    break;
                case 3:
                    eliminarInformacion(in, sist, lg);
                    break;
                case 4:
                    modificarInformacion(in, sist, lg);
                    break;
            }

        } while (opcion != -1);
        lg.escribir(getSistema(sist) + "FIN DEL PROGRAMA");
    }

    public static void modificarInformacion(Scanner input, TrenesSA sistema, Log logs) {

        //Mostrar submenú para modificar información
        System.out.println("------------------------------------");
        System.out.println("Seleccione qué quiere modificar: ");
        System.out.println("1-Estacion");
        System.out.println("2-Tren");
        System.out.println("3-Linea");
        System.out.println("4-Riel");
        System.out.println("-1-salir");

        byte opcion = input.nextByte();
        switch (opcion) {
            case 1:
                //Pedir el nombre de la estación
                modificarEstacion(sistema, input, logs);
                break;
            case 2:
                modificarTren(sistema, input, logs);
                break;
            case 3:
                modificarLinea(sistema, input, logs);
                break;
            case 4:
                modificarRiel(sistema, input, logs);
                break;
        }
    }

    public static String getSistema(TrenesSA sistema) {
        //Método que retorna en formato String todas las estructuras del sistema cargadas
        String retornar = "<--------------FIN DEL PROGRAMA-------------->\n";
        retornar += "ESTADO DEL SISTEMA:\n";
        retornar += "------------------------------\n";
        retornar += "Árbol AVL para estaciones (clave + altura)\n";
        retornar += sistema.getEstacionesEstructura();
        retornar += "------------------------------\n";
        retornar += "Árbol AVL para trenes (clave + altura)\n";
        retornar += sistema.getTrenesEstructura();
        retornar += "------------------------------\n";
        retornar += "Grafo para rieles\n";
        retornar += "[NodoVert1]-> [NodoAdy1 (etiqueta2)] - [NodoAdy2 (etiqueta2)] ... \n";
        retornar += sistema.getRielesEstructura();
        retornar += "------------------------------\n";
        retornar += "Tabla Hash para líneas\n";
        retornar += sistema.getLineasEstructura();
        return retornar;
    }

    public static void modificarRiel(TrenesSA sist, Scanner in, Log lg) {
        //Método para modificar un riel desde el menú interactivo
        System.out.println("Ingrese el nombre de una estación, -1 para salir");
        String estacion1 = in.next().toUpperCase();
        while (!sist.existeEstacion(estacion1) && !estacion1.equals("-1")) {
            System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
            estacion1 = in.next().toUpperCase();
        }
        if (!estacion1.equals("-1")) {
            System.out.println("Ingrese el nombre de la otra estación, -1 para salir");
            String estacion2 = in.next().toUpperCase();
            while (!sist.existeEstacion(estacion2) && !estacion2.equals("-1")) {
                System.out.println("No existe estación, intente nuevamente");
                estacion2 = in.next().toUpperCase();
            }
            if (!estacion2.equals("-1")) {
                if (sist.existeRiel(estacion1, estacion2)) {
                    int distanciaAnterior = sist.getDistanciaRiel(estacion1, estacion2);
                    System.out.println("Ingrese una nueva distancia entre rieles");
                    int nuevaDistancia = in.nextInt();
                    if (sist.modificarDistancia(estacion1, estacion2, nuevaDistancia)) {
                        lg.escribir("Modificado riel " + estacion1 + " -" + estacion2 + ": " + distanciaAnterior + "->" + nuevaDistancia);
                    }
                } else {
                    System.out.println("No existe riel entre las estaciones");
                }
            }
        }
    }

    public static void modificarLinea(TrenesSA sist, Scanner in, Log lg) {
        //Método para modificar una línea, dado un nombre de línea
        System.out.println("Ingrese el número de línea, -1 para salir");
        String nombreLinea = in.next().toUpperCase();
        LinkedList<String> linea = sist.getLinea(nombreLinea);
        while (linea == null && !nombreLinea.equals("-1")) {
            System.out.println("No existe la línea, intente nuevamente o ingrese -1 para salir");
            nombreLinea = in.next().toUpperCase();
            linea = sist.getLinea(nombreLinea);
        }
        if (!nombreLinea.equals("-1")) {
            imprimirLinea(linea);
            //Vaciar la lista de la línea agregando solo su clave            
            linea.clear();
            linea.add(nombreLinea);

            boolean salir = false;
            String estacion;
            String lineasString = nombreLinea;
            String estacionAnterior = nombreLinea;
            while (!salir) {
                //Ingresar el recorrido a partir de la segunda estación, solo admite estaciones cargadas al sistema
                System.out.println("Ingrese el nombre de una estación del recorrido, ingrese -1 para terminar");
                estacion = in.next().toUpperCase();
                if (!estacion.equals("-1")) {
                    if (sist.getEstaciones().existeClave(estacion)) {
                        if (sist.existeRiel(estacion, estacionAnterior)) {
                            linea.add(estacion);
                            lineasString += " - " + estacion;
                            estacionAnterior = estacion;
                        } else {
                            System.out.println("No existe un riel entre las estaciones ingresadas");
                        }
                    } else {
                        System.out.println("Estación no existe, vuelva a intentarlo");
                    }
                } else {
                    salir = true;
                }
            }
            System.out.println("Estación modificada: " + lineasString);
            //Escribir en el archivo log            
            lg.escribir("Modificada línea " + nombreLinea + ": " + lineasString);
        }
    }

    public static void modificarTren(TrenesSA sist, Scanner in, Log lg) {
        //Método para modificar un tren, dado un ID ingresado
        System.out.println("Ingrese el número de id del tren a modificar, -1 para salir");
        int idTren = in.nextInt();
        byte opcion;
        Tren tren = sist.getTren(idTren);
        while (tren == null && idTren != -1) {
            System.out.println("No existe el tren, ingrese otro número o -1 para salir");
            idTren = in.nextInt();
        }
        if (idTren != -1) {
            imprimirTren(in, tren);
            System.out.println("¿Qué desea modificar?");
            System.out.println("1-Propulsión");
            System.out.println("2-Cant. vagones para pasajeros");
            System.out.println("3-Cant. vagones para carga");
            System.out.println("4-Línea");
            opcion = in.nextByte();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese propulsión: electricidad, diesel, fuel oil, gasolina, híbrido o -1 para salir");
                    String propulsion = in.next().toUpperCase();
                    while (!propulsion.equals("ELECTRICIDAD") && !propulsion.equals("DIESEL")
                            && !propulsion.equals("FUEL OIL") && !propulsion.equals("GASOLINA")
                            && !propulsion.equals("HÍBRIDO") && !propulsion.equals("-1")) {
                        System.out.println("Error, ingrese una opción válida: electricidad, diesel, fuel oil, gasolina, híbrido o -1 para salir");
                        propulsion = in.next().toUpperCase();
                    }
                    if (!propulsion.equals("-1")) {
                        String propulsionAnterior = tren.getPropulsion();
                        tren.setPropulsion(propulsion);
                        System.out.println("Propulsión cambiada");
                        lg.escribir("Modificado propulsión de TREN " + idTren + ": " + propulsionAnterior + "->" + propulsion);
                    }
                    break;
                case 2:
                    System.out.println("Ingrese la nueva cantidad de vagones para pasajeros, -1 para cancelar");
                    int cantVagones = in.nextInt();
                    while (cantVagones < -1) {
                        System.out.println("Error, ingrese un número positivo");
                        cantVagones = in.nextInt();
                    }
                    if (cantVagones != -1) {
                        int cantAnterior = tren.getCantVagonesPasaj();
                        lg.escribir("Modificado cant. pasajeros de TREN " + idTren + ": " + cantAnterior + "->" + cantVagones);
                    }
                    break;
                case 3:
                    System.out.println("Ingrese la nueva cantidad de vagones para carga, -1 para cancelar");
                    int cantVagonesCarga = in.nextInt();
                    while (cantVagonesCarga < -1) {
                        System.out.println("Error, ingrese un número positivo");
                        cantVagones = in.nextInt();
                    }
                    if (cantVagonesCarga != -1) {
                        int cantAnterior = tren.getCantVagonesPasaj();
                        lg.escribir("Modificado cant. vagones de carga de TREN " + idTren + ": " + cantAnterior + "->" + cantVagonesCarga);
                    }
                    break;
                case 4:
                    System.out.println("Ingrese una línea cargada, -1 para salir");
                    String linea = in.next().toUpperCase();
                    while (!sist.existeLinea(linea) && !linea.equals("-1")) {
                        System.out.println("No existe la línea, intente nuevamente o ingrese -1 para salir");
                    }
                    if (!linea.equals("-1")) {
                        String lineaAnterior = tren.getLinea();
                        lg.escribir("Modificado línea de TREN " + idTren + ": " + lineaAnterior + "->" + linea);
                    }

            }

        }
    }

    public static void modificarEstacion(TrenesSA sist, Scanner in, Log lg) {
        //Submenú para modificar una estación dada
        System.out.println("Ingrese el nombre de la estación a modificar, -1 para salir");
        String est = in.next().toUpperCase();
        byte opcion;
        Estacion estacion = sist.getEstacion(est);
        while (estacion == null && !est.equals("-1")) {
            System.out.println("No existe estación, ingrese otra o -1 para salir");
            est = in.next().toUpperCase();
            estacion = sist.getEstacion(est);
        }
        if (!est.equals("-1")) {
            imprimirEstacion(in, estacion);
            System.out.println("¿Qué desea modificar?");
            System.out.println("1-Calle");
            System.out.println("2-Núm. Calle");
            System.out.println("3-Ciudad");
            System.out.println("4-Cód. Postal");
            System.out.println("5-Cantidad de plataformas");
            System.out.println("6-Cantidad de vías");
            System.out.println("Cualquier otro número para salir");

            opcion = in.nextByte();
            switch (opcion) {

                case 1:
                    System.out.println("Ingrese calle");
                    String calleAnterior = estacion.getCalle();
                    String calle = in.next().toUpperCase();
                    estacion.setCalle(calle);
                     {
                        lg.escribir("Modificado calle de ESTACIÓN " + est + ":" + calleAnterior + "->" + calle);
                    }
                    break;
                case 2:
                    System.out.println("Ingrese número de calle");
                    int numCalleAnterior = estacion.getNumCalle();
                    int numCalle = in.nextInt();
                    estacion.setNumCalle(numCalle);
                    lg.escribir("Modificado núm. calle de ESTACIÓN " + est + ":" + numCalleAnterior + "->" + numCalle);
                    break;
                case 3:
                    System.out.println("Ingrese Ciudad");
                    String ciudadAnterior = estacion.getCiudad();
                    String ciudad = in.next().toUpperCase();
                    estacion.setCiudad(ciudad);
                    lg.escribir("Modificado ciudad de ESTACIÓN " + est + ":" + ciudadAnterior + "->" + ciudad);
                    break;
                case 4:
                    System.out.println("Ingrese nuevo CP");
                    String cpAnterior = estacion.getCp();
                    String nuevoCP = in.next().toUpperCase();
                    estacion.setCp(est);
                    lg.escribir("Modificado CP de ESTACIÓN " + est + ":" + cpAnterior + "->" + nuevoCP);
                    break;
                case 5:
                    System.out.println("Ingrese cant. plataformas");
                    int cantAnterior = estacion.getCantPlataformas();
                    int nuevaCant = in.nextInt();
                    estacion.setCantPlataformas(nuevaCant);
                    lg.escribir("Modificado cant. plataformas de ESTACIÓN " + est + ":" + cantAnterior + "->" + nuevaCant);

                    break;
                case 6:
                    System.out.println("Ingrese cant. vías");
                    int cantViasAnt = estacion.getCantPlataformas();
                    int nuevaCantVias = in.nextInt();
                    estacion.setCantPlataformas(nuevaCantVias);
                    lg.escribir("Modificado cant. plataformas de ESTACIÓN " + est + ":" + cantViasAnt + "->" + nuevaCantVias);
                    break;
            }
        }
    }

    public static void cargarLineas(TrenesSA sist, Queue<StringTokenizer> tokensQueue, Log lg) {
        //Método que carga las líneas desde la carga inicial al sistema
        while (!tokensQueue.isEmpty()) {
            StringTokenizer linea = tokensQueue.poll();

            LinkedList listaRecorrido = new LinkedList();
            String lineaToken;
            while (linea.hasMoreTokens()) {
                lineaToken = linea.nextToken().toUpperCase();
                listaRecorrido.add(lineaToken);
            }
            //El recorrido está construido como una secuencia de strings (los id de las estaciones)
            //Si todas las estaciones existen, se carga
            if (sist.agregarLinea(listaRecorrido)) {
                lg.escribir("Agregada LÍNEA: " + getStringLista(listaRecorrido) + "\n");
            } else {
                lg.escribir("Error al agregar LÍNEA: " + getStringLista(listaRecorrido) + "\n");
            }
        }
    }

    public static void cargarRieles(TrenesSA sist, LinkedList<StringTokenizer> tokens, Log lg) {
        //Método para cargar rieles desde la carga inicial
        while (!tokens.isEmpty()) {
            cargarRiel(sist, tokens.getFirst(), lg);
            tokens.removeFirst();
        }
    }

    public static void cargarTrenes(TrenesSA sist, LinkedList<StringTokenizer> listaTrenes, Log lg) {
        //Método para ingresar los objetos Tren en el sistema
        Random ran = new Random();
        int num;
        while (!listaTrenes.isEmpty()) {
            //Ir insertando en forma aleatoria sobre el sistema
            num = ran.nextInt(listaTrenes.size());

            StringTokenizer trenTk = listaTrenes.get(num);

            int idTren = Integer.parseInt(trenTk.nextToken());
            String propulsion = trenTk.nextToken();
            int cantVagonesPasaj = Integer.parseInt(trenTk.nextToken());
            int cantVagonesCarga = Integer.parseInt(trenTk.nextToken());
            String linea = trenTk.nextToken().toUpperCase();

            //Si no existe la línea indicada, se deja como no asignado
            if (!sist.existeLinea(linea)) {
                linea = "no asignado";
            }

            if (sist.agregarTren(idTren, propulsion, cantVagonesPasaj, cantVagonesCarga, linea)) {
                lg.escribir("Agregado TREN:" + idTren + "\n"
                        + "   propulsión: " + propulsion + "\n"
                        + "   cant. vagones pasajeros :" + cantVagonesPasaj + "\n"
                        + "   cant. vagones carga: " + cantVagonesCarga + "\n"
                        + "   linea: " + linea + "\n");
            } else {
                lg.escribir("Error cargando TREN:" + idTren + "\n"
                        + "   propulsión: " + propulsion + "\n"
                        + "   cant. vagones pasajeros :" + cantVagonesPasaj + "\n"
                        + "   cant. vagones carga: " + cantVagonesCarga + "\n"
                        + "   linea: " + linea + "\n");
            }

            listaTrenes.remove(num);
        }
    }

    public static void cargarRiel(TrenesSA sist, StringTokenizer ot, Log lg) {
        //Método para agregar un riel desde la carga inicial
        String rielToken;
        byte paso = 0;
        String estacion1 = "";
        String estacion2 = "";
        int distancia = 0;
        while (ot.hasMoreTokens()) {
            rielToken = ot.nextToken().toUpperCase();
            switch (paso) {
                case 0:
                    estacion1 = rielToken;
                    paso++;
                    break;
                case 1:
                    estacion2 = rielToken;
                    paso++;
                    break;
                case 2:
                    distancia = Integer.parseInt(rielToken);
                    break;
            }
        }
        if (!sist.agregarRiel(estacion1, estacion2, distancia)) {
            lg.escribir("Error cargando RIEL:" + estacion1 + "-" + estacion2 + "-" + distancia + "\n");
        } else {
            lg.escribir("Agregado RIEL: " + estacion1 + "-" + estacion2 + " - " + distancia + "Km" + "\n");
        }
    }

    public static void cargarEstaciones(TrenesSA sist, LinkedList<StringTokenizer> listEst, Log lg,boolean random) {
        //Método para cargar estaciones de forma aleatoria desde la carga inicial a partir de la lista armada
        //La variable random es para hacer que los elementos se carguen en orden aleatorio o no
        Random ran = new Random();
        int pos;
        while (!listEst.isEmpty()) {
            StringTokenizer estTok;
            //Cargar aleatoriamente las estaciones sobre el diccionario
            if(random){
                pos = ran.nextInt(listEst.size());
                estTok = listEst.get(pos);
                listEst.remove(pos);
            }else{
                estTok=listEst.getFirst();
                listEst.removeFirst();
            }                     

            //Descomponer estTok
            String nombre = estTok.nextToken().toUpperCase();
            String calle = estTok.nextToken().toUpperCase();
            int numCalle = Integer.parseInt(estTok.nextToken());
            String ciudad = estTok.nextToken().toUpperCase();
            String cp = estTok.nextToken().toUpperCase();
            int cantVias = Integer.parseInt(estTok.nextToken());
            int cantPlataformas = Integer.parseInt(estTok.nextToken());
            if (sist.agregarEstacion(nombre, calle, numCalle, ciudad, cp, cantVias, cantPlataformas)) {
                //Agregar al grafo de rieles y escribir en el archivo .log
                sist.getRieles().insertarVertice(nombre);
                lg.escribir("Agregado ESTACIÓN: " + nombre + "\n"
                        + "   calle: " + calle + "\n"
                        + "   Nº calle: " + numCalle + "\n"
                        + "   ciudad: " + ciudad + "\n"
                        + "   cód. postal: " + cp + "\n"
                        + "   cantidad de vías: " + cantVias + "\n"
                        + "   cantidad de plataformas:" + cantPlataformas + "\n");
            } else {
                lg.escribir("Error agregado ESTACIÓN: " + nombre + "\n"
                        + "   calle: " + calle + "\n"
                        + "   Nº calle: " + numCalle + "\n"
                        + "   ciudad: " + ciudad + "\n"
                        + "   cód. postal: " + cp + "\n"
                        + "   cantidad de vías: " + cantVias + "\n"
                        + "   cantidad de plataformas:" + cantPlataformas + "\n");
            }                        
        }
    }

    public static void eliminarInformacion(Scanner input, TrenesSA sistema, Log logs) {
        //Método que muestra el submenú para eliminar información
        boolean exito;

        System.out.println("Ingrese que tipo de información quiere eliminar:");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Riel");

        int opcion = input.nextInt();

        switch (opcion) {
            case 1:
                String[] estacion = ingresarEstaciones(1, input, sistema);
                if (sistema.eliminarEstacion(estacion[0])) {
                    System.out.println("Estación eliminada");
                    logs.escribir("Eliminado ESTACIÓN: " + estacion[0] + "\n");
                    LinkedList<String> lineasBorradas = sistema.verificarEstacionBorradaYLineas(estacion[0]);
                    int longitud = lineasBorradas.size();
                    //Eliminar las líneas que contenián la estación eliminada (deben redefinirse)
                    for (int i = 0; i < longitud; i++) {
                        String nombreLinea = lineasBorradas.get(i);
                        logs.escribir("    Eliminado LINEA: " + nombreLinea + "\n");
                        //Modificar los trenes con la línea borrada
                        LinkedList<Integer> listaTrenesMod = sistema.verificarLineaBorradaYTrenes(nombreLinea);
                        int longitud2 = listaTrenesMod.size();
                        for (int j = 0; j < longitud2; j++) {
                            logs.escribir("    Modificado línea de TREN " + listaTrenesMod.get(j) + ": no asignado" + "\n");
                        }
                    }

                } else {
                    System.out.println("Error eliminando estación");
                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea, ingrese -1 para salir");
                String nombreLinea = input.next().toUpperCase();
                exito = sistema.eliminarLinea(nombreLinea);
                //Si no existe la línea, reintentar
                while (!exito && !nombreLinea.equals("-1")) {
                    System.out.println("Error, no existe la línea. Vuelva a intentarlo o ingrese -1 para salir");
                    nombreLinea = input.next().toUpperCase();
                    exito = sistema.eliminarLinea(nombreLinea);
                }
                //Registrar en el archivo .log
                if (exito) {
                    System.out.println("Línea eliminada");
                    logs.escribir("Eliminado LÍNEA " + nombreLinea + "\n");
                    //Modificar los trenes que tenían asignado la línea borrada
                    LinkedList listaTrenesMod = sistema.verificarLineaBorradaYTrenes(nombreLinea);
                    int longitud = listaTrenesMod.size();
                    int id;
                    for (int i = 0; i < longitud; i++) {
                        id = (int) listaTrenesMod.get(i);
                        logs.escribir("    Modificado línea de TREN " + id + ": no asignado" + "\n");
                    }
                } else {
                    System.out.println("Error eliminando línea");
                }
                break;
            case 3:
                System.out.println("Ingrese el id del tren o ingrese -1 para salir");
                int idTren = input.nextInt();
                exito = sistema.eliminarTren(idTren);
                //Si no existe el tren, reintentar
                while (!exito && idTren != -1) {
                    System.out.println("Error, no existe el tren. Vuelva a intentarlo o ingrese -1 para salir");
                    idTren = input.nextInt();
                    exito = sistema.eliminarTren(idTren);
                }
                //Registrar en el archivo .log
                if (exito) {
                    System.out.println("Tren Eliminado");
                    logs.escribir("Eliminado TREN: " + idTren);

                } else {
                    System.out.println("Error eliminando tren");
                }
                break;
            case 4:
                String estaciones[] = ingresarEstaciones(2, input, sistema);

                if (estaciones != null) {
                    if (sistema.eliminarRiel(estaciones[0], estaciones[1])) {
                        System.out.println("Riel eliminado");
                        logs.escribir("Eliminado RIEL: " + estaciones[0] + " <-> " + estaciones[1] + "\n");
                        //Verificar si existen líneas con estaciones conectadas
                        LinkedList lineasBorradas = sistema.verificarLineaRiel(estaciones[0], estaciones[1]);
                        int longitud = lineasBorradas.size();
                        for (int i = 0; i < longitud; i++) {
                            String lineaB = (String) lineasBorradas.get(i);
                            logs.escribir("    Eliminado LINEA " + lineaB + "\n");
                            LinkedList<Integer> trenesModificados = sistema.verificarLineaBorradaYTrenes(lineaB);
                            int longitud2 = trenesModificados.size();
                            for (int j = 0; j < longitud2; j++) {
                                logs.escribir("    Modificado línea de TREN " + trenesModificados.get(j) + ": no asignado" + "\n");
                            }
                        }
                    } else {
                        System.out.println("Error. Las estaciones no están directamente unidas.");
                    }
                    break;
                }
        }

    }

    public static void mostrarSubMenuAgregar(Scanner input, TrenesSA sist, Log logs) {
        //Método que muestra el submenú para agregar información
        System.out.println("-------------------------------");
        System.out.println("Eliga qué quiere agregar:");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Riel");
        System.out.println("-1-Salir");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                agregarEstacion(input, sist, logs);
                break;
            case 2:
                agregarLinea(input, sist, logs);
                break;
            case 3:
                agregarTren(input, sist, logs);
                break;
            case 4:
                agregarRiel(input, sist, logs);
                break;
        }
    }

    public static String[] ingresarEstaciones(int cantEstaciones, Scanner in, TrenesSA sist) {
        //Método para verificar que las estaciones ingresadas en los menúes existan, retorna un arreglo de 2 o 3 estaciones segun cantEstaciones        ;
        String[] retornar = new String[cantEstaciones];
        System.out.println("Ingrese el nombre de una estación o -1 para cancelar");
        String estacion = in.next().toUpperCase();
        int cont = 0;
        while (cont < cantEstaciones) {
            if (sist.existeEstacion(estacion)) {
                retornar[cont] = estacion;
                cont++;
                if (cont < cantEstaciones) {
                    System.out.println("Ingrese el nombre de otra estación o -1 para cancelar");
                    estacion = in.next().toUpperCase();
                }
            } else if (estacion.equals("-1")) {
                retornar = null;
                cont = cantEstaciones;
            } else {
                System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
                estacion = in.next().toUpperCase();
            }
        }

        return retornar;
    }

    public static void agregarRiel(Scanner in, TrenesSA sistema, Log lg) {
        //Método para agregar rieles a través de la terminal
        System.out.println("Ingrese el nombre de una estación o -1 para cancelar");
        String estacion1 = in.next().toUpperCase();
        while (!sistema.existeEstacion(estacion1) && !estacion1.equals("-1")) {
            System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
            estacion1 = in.next().toUpperCase();
        }
        if (!estacion1.equals("-1")) {
            System.out.println("Ingrese el nombre de otra estación");
            String estacion2 = in.next().toUpperCase();
            while (!sistema.existeEstacion(estacion2) && !estacion2.equals("-1")) {
                System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
                estacion2 = in.next().toUpperCase();
            }
            if (!estacion2.equals("-1")) {
                if (!sistema.existeRiel(estacion1, estacion2)) {
                    System.out.println("Ingrese distancia entre estaciones en km");
                    int distancia = in.nextInt();
                    while (distancia < 0) {
                        System.out.println("Distancia debe ser un número positivo");
                        distancia = in.nextInt();
                    }
                    if (sistema.agregarRiel(estacion1, estacion2, distancia)) {
                        lg.escribir("Agregado RIEL: " + estacion1 + " <-> " + estacion2 + " - " + distancia + " km");
                    } else {
                        System.out.println("Error cargando riel");
                    }
                } else {
                    System.out.println("Ya existe riel entre ambas estaciones");
                }
            }
        }
    }

    public static void agregarTren(Scanner in, TrenesSA sistema, Log lg) {
        //Método para agregar los trenes desde el menú interactivo
        System.out.println("Ingrese idTren o ingrese -1 para salir");
        int id = in.nextInt();
        boolean esPositivo = id > 0;
        boolean existeClave = sistema.getTrenes().existeClave(id);
        boolean exito = esPositivo && !existeClave;
        //Verificar si existe la clave o se introdujo un número negativo
        while (!exito && id != -1) {
            if (existeClave) {
                System.out.println("Ya existe la clave, ingrese otra");
            } else {
                System.out.println("Ingrese un valor positivo");
            }
            id = in.nextInt();
            esPositivo = id > 0;
            existeClave = sistema.getTrenes().existeClave(id);
            exito = esPositivo && !existeClave;
        }
        //Si no se ingresó la opción de salir
        if (id != -1) {
            System.out.println("Ingrese propulsión: electricidad, diesel, fuel oil, gasolina, híbrido");
            String propulsion = in.next().toUpperCase();
            System.out.println(propulsion);
            //Ingresar una opción válida
            while ((!propulsion.equals("ELECTRICIDAD") && !propulsion.equals("DIESEL")
                    && !propulsion.equals("FUEL OIL") && !propulsion.equals("GASOLINA")
                    && !propulsion.equals("HÍBRIDO")) && !propulsion.equals("-1")) {
                System.out.println("Error, ingrese una opción válida: electricidad, diesel, fuel oil, gasolina, híbrido o -1 para salir");
                propulsion = in.next().toUpperCase();
                System.out.println(propulsion);
            }
            if (!propulsion.equals("-1")) {
                System.out.println("Ingrese cantidad de vagones para pasajeros");
                int cantVagonesP = in.nextInt();
                System.out.println("Ingrese cantidad de vagones para carga");
                int cantVagonesC = in.nextInt();
                System.out.println("Ingrese línea o presione enter para continuar");
                String linea = in.next().toUpperCase();
                while (!sistema.existeLinea(linea)) {
                    System.out.println("No existe la línea indicada, vuelva a intentarlo");
                    linea = in.next().toUpperCase();
                }
                //Agregar los datos al sistema
                if (sistema.agregarTren(id, propulsion, cantVagonesP, cantVagonesC, linea)) {
                    //Registrar en el archivo .log en caso de éxito
                    lg.escribir("Agregado TREN:\n"
                            + "   Id: " + id + "\n"
                            + "   Propulsión: " + propulsion + "\n"
                            + "   Cantidad vagones pasajeros: " + cantVagonesP + "\n"
                            + "   Cantidad vagones carga: " + cantVagonesC + "\n"
                            + "   Línea: " + linea);

                }
            }
        }
    }

    public static void agregarLinea(Scanner in, TrenesSA sistema, Log lg) {
        //Método para agregar una línea desde el menú interactivo
        //El nombre del a línea es la primera estación del recorrido
        boolean existeEst = false;
        boolean existeRiel = false;
        String estAnt;
        String estacion;
        String lineasString = "";
        System.out.println("Ingrese el nombre de la línea, ingrese -1 para cancelar");
        estacion = in.next().toUpperCase();
        while (!estacion.equals("-1") && (sistema.existeLinea(estacion) == true || !sistema.existeEstacion(estacion))) {
            System.out.println("Error. Ya existe la línea o la estación no existe. Intente nuevamente o use -1 para cancelar");
            estacion = in.next().toUpperCase();
        }
        estAnt = estacion;
        if (!estacion.equals("-1")) {
            lineasString += estacion;
            LinkedList<String> recorrido = new LinkedList();
            //Insertar nombre de la estación
            recorrido.add(estacion.toUpperCase());
            while (!estacion.equals("-1")) {
                System.out.println("Ingrese el nombre de otra estación del recorrido, ingrese -1 para terminar de ingresar");
                estacion = in.next().toUpperCase();
                existeEst = sistema.getEstaciones().existeClave(estacion);
                existeRiel = sistema.existeRiel(estacion, estAnt);
                //Ingresar el recorrido, solo admite estaciones cargadas al sistema y que estén unidas por un riel
                while ((!existeEst || !existeRiel) && !estacion.equals("-1")) {
                    if (!existeEst) {
                        System.out.println("Estación no existe, vuelva a intentarlo");
                    } else {
                        System.out.println("No existe riel cargado con estación " + estAnt);
                    }
                    estacion = in.next().toUpperCase();
                    existeEst = sistema.existeEstacion(estacion);
                    existeRiel = sistema.existeRiel(estacion, estAnt);
                }
                if (!estacion.equals("-1")) {
                    lineasString += " -> " + estacion;
                    estAnt = estacion;
                    recorrido.add(estacion.toUpperCase());
                }
            }
            //Agregar línea y escribir en el archivo log           
            if (sistema.agregarLinea(recorrido)) {
                lg.escribir("agregado LíNEA: " + lineasString + "\n");

            } else {
                lg.escribir("Error agregando LíNEA: " + lineasString + "\n");
            }
        }
    }

    public static void mostrarSubMenuConsulta(Scanner input, TrenesSA sist) {
        //Mostrar el submenú para consulta
        System.out.println("-------------------------------");
        System.out.println("Eliga qué quiere consultar");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Viajes");
        System.out.println("5-Sistema");
        System.out.println("Otro número-Salir");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                System.out.println("-------------------------------");
                System.out.println("Ingrese qué quiere consultar sobre estaciones:");
                System.out.println("1-Información de una estación");
                System.out.println("2-Ver estaciones que comienzan con una subcadena");
                System.out.println("Otro número - salir");

                opcionElegida = input.nextByte();
                switch (opcionElegida) {
                    case 1:
                        System.out.println("Ingrese nombre de la estación, -1 para salir");
                        String nombreEstacion = input.next().toUpperCase();
                        Estacion consultaEst = sist.getEstacion(nombreEstacion);
                        while (consultaEst == null && !nombreEstacion.equals("-1")) {
                            System.out.println("No existe estación, vuelva a colocar el nombre o presione -1 para salir");
                            nombreEstacion = input.next().toUpperCase();
                        }
                        if (!nombreEstacion.equals("-1")) {
                            imprimirEstacion(input, consultaEst);
                        }
                        break;
                    case 2:
                        System.out.println("Ingrese una subcadena");
                        String subcadena = input.next().toUpperCase();
                        System.out.println("-------------------------------");
                        System.out.println("Resultado:");
                        System.out.println(getStringLista(sist.getEstacionesSubcadena(subcadena)));
                        break;

                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea, -1 para salir");
                String nombreLinea = input.next().toUpperCase();
                LinkedList<String> linea = sist.getLinea(nombreLinea);
                while (linea == null && !nombreLinea.equals("-1")) {
                    System.out.println("No existe la línea, vuelva a colocar el nombre o presione -1 para salir");
                    nombreLinea = input.next().toUpperCase();
                    linea = sist.getLinea(nombreLinea);
                }
                if (!nombreLinea.equals("-1")) {
                    imprimirLinea(linea);
                }
                break;
            case 3:
                System.out.println("-------------------------------");
                System.out.println("Ingrese que quiere consultar:");
                System.out.println("1-Información de un tren");
                System.out.println("2-Consultar línea asignada");
                System.out.println("Otro número-salir");

                opcionElegida = input.nextByte();
                switch (opcionElegida) {
                    case 1:
                        System.out.println("Ingrese el id numérico del tren, -1 para salir");
                        int id = input.nextInt();
                        Tren consultaTren = sist.getTren(id);
                        while (consultaTren == null && id != -1) {
                            System.out.println("No existe el tren, verifique id ingresado o presione -1 para salir");
                            id = input.nextInt();
                        }
                        if (id != -1) {
                            imprimirTren(input, consultaTren);
                        }
                        break;
                    case 2:
                        System.out.println("Ingrese el id numérico del tren, -1 para salir");
                        int unId = input.nextInt();
                        Tren unTren = sist.getTren(unId);
                        while (unTren == null && unId != -1) {
                            System.out.println("No existe el tren, verifique id ingresado o presione -1 para salir");
                            id = input.nextInt();
                        }
                        if (unId != -1) {
                            System.out.println("-----------------------------");
                            System.out.println("Línea asignada:");
                            System.out.println(getStringLista(sist.getLineaAsignada(unId)));
                        }
                        break;
                }

                break;
            case 4:
                //viajes
                System.out.println("-------------------------------");
                System.out.println("Eliga qué quiere consultar sobre viajes:");
                System.out.println("1-Camino A a B con menos estaciones");
                System.out.println("2-Camino A a B con menos km");
                System.out.println("3-Caminos posibles de A a B sin pasar por C");
                System.out.println("4-Verificar si existe camino de A a B con máxima cantidad de km");
                System.out.println("-1-Salir");
                opcionElegida = input.nextByte();
                String[] estaciones;
                switch (opcionElegida) {
                    case 1:
                        estaciones = ingresarEstaciones(2, input, sist);
                        //Si no se ha cancela la entrada
                        if (estaciones != null) {
                            System.out.println("-------------------------------");
                            System.out.println("Resultado:");
                            System.out.println(getStringLista(sist.getCaminoMasCortoPorEstaciones(estaciones[0], estaciones[1])));
                        }
                        break;
                    case 2:
                        estaciones = ingresarEstaciones(2, input, sist);
                        if (estaciones != null) {
                            System.out.println("-------------------------------");
                            System.out.println("Resultado:");
                            System.out.println(getStringLista(sist.getCaminoMasCortoPorKm(estaciones[0], estaciones[1])));
                        }
                        break;
                    case 3:
                        estaciones = ingresarEstaciones(2, input, sist);
                        System.out.println("Ingrese el nombre de una estación a evitar");
                        String estacionEvitar = input.next().toUpperCase();
                        while (!sist.existeEstacion(estacionEvitar)) {
                            System.out.println("No existe estación, intente nuevamente");
                            estacionEvitar = input.next().toUpperCase();
                        }
                        if (estaciones != null) {
                            System.out.println("-------------------------------");
                            System.out.println("Resultado:");
                            LinkedList<LinkedList> caminos = sist.getCaminosPosiblesSinPasarPor(estaciones[0], estaciones[1], estacionEvitar);
                            int longitud = caminos.size();
                            for (int i = 0; i < longitud; i++) {
                                System.out.println(getStringLista(caminos.get(i)));
                            }
                        }
                        break;
                    case 4:
                        estaciones = ingresarEstaciones(2, input, sist);
                        System.out.println("Ingrese cantidad máxima de km");
                        int kmMaximo = input.nextInt();
                        System.out.println("-------------------------------");
                        System.out.println("Resultado: " + sist.existeCaminoConMaxKm(estaciones[0], estaciones[1], kmMaximo));
                        break;
                }
                break;
            case 5:
                //Métodos para get estructura
                System.out.println("--------------------------------------------");
                System.out.println("Eliga qué estructura quisiera visualizar:");
                System.out.println("1-Estaciones");
                System.out.println("2-Trenes");
                System.out.println("3-Líneas");
                System.out.println("4-Rieles");
                System.out.println("-1-Salir");
                opcionElegida = input.nextByte();
                switch (opcionElegida) {
                    case 1:
                        System.out.println(sist.getEstacionesEstructura());
                        break;
                    case 2:
                        System.out.println(sist.getTrenesEstructura());
                        break;
                    case 3:
                        System.out.println(sist.getLineasEstructura());
                        break;
                    case 4:
                        System.out.println("[NodoVert --> NodoAdy(etiqueta) - ....]");
                        System.out.println(sist.getRielesEstructura());
                        break;

                }
                break;
        }
    }

    private static String getStringLista(LinkedList lista) {
        //Método auxiliar para conseguir el string de una lista e imprimirla por pantalla
        String retornar = "";
        if (lista.size() > 0) {
            retornar = getStringLista(lista, 0, lista.size() - 1);
        }
        return retornar;
    }

    private static String getStringLista(LinkedList lista, int n, int limite) {
        //Método 1iliar para hacer el llamado recursivo de getStringLista
        String resultado = "";
        if (n == limite) {
            resultado = lista.get(n).toString();
        } else {
            resultado = lista.get(n).toString() + " - " + getStringLista(lista, n + 1, limite);
        }
        return resultado;
    }

    public static void imprimirLinea(LinkedList<String> lineaList) {
        //Imprimir los datos de una línea
        System.out.println("-------RESULTADO:--------");
        System.out.println("Recorrido: ");

        System.out.println(getStringLista(lineaList));
        System.out.println("FIN");
        System.out.println("---------------");
    }

    public static void imprimirTren(Scanner i, Tren tren) {
        //Método para imprimir un objeto Tren
        System.out.println("-------RESULTADO:--------");
        System.out.println("Propulsion: " + tren.getPropulsion());
        System.out.println("Cantidad de vagones para pasajeros: " + tren.getCantVagonesPasaj());
        System.out.println("Cantidad de vagones para carga: " + tren.getCantVagonesCarga());
        System.out.println("Línea asignada: " + tren.getLinea());
        System.out.println("---------------");
    }

    public static void imprimirEstacion(Scanner i, Estacion est) {
        //Método para imprimir un objeto Estacion
        System.out.println("-------RESULTADO:--------");
        System.out.println("Nombre de la estación: " + est.getNombre());
        System.out.println("Domicilio: " + est.getCalle() + " " + est.getNumCalle());
        System.out.println("Ciudad: " + est.getCiudad());
        System.out.println("CP: " + est.getCp());
        System.out.println("Cantidad de vías: " + est.getCantVias());
        System.out.println("Cantidad de plataformas: " + est.getCantPlataformas());
        System.out.println("---------------");
    }

    public static void agregarEstacion(Scanner in, TrenesSA elSistema, Log lg) {
        //Método para agregar una estación al sistema
        //Atributos para crear la estación
        String id;
        String calle;
        int numCalle;
        String ciudad;
        String cp;
        int cantVias;
        int cantPlataformas;
        boolean existeClave;

        //Pedir los datos y enviárlo a la clase TrenesSA
        System.out.println("Ingrese nombre de la estación, -1 para salir");
        id = in.next().toUpperCase();
        existeClave = elSistema.getEstaciones().existeClave(id);
        while (existeClave && !id.equals("-1")) {
            System.out.println("Nombre ya existe, ingrese otro nombre o -1 para salir");
            id = in.next().toUpperCase();
            existeClave = elSistema.getEstaciones().existeClave(id);
        }
        if (!id.equals("-1")) {
            System.out.println("Ingrese nombre de la calle");
            calle = in.next().toUpperCase();
            System.out.println("Ingrese número de la calle");
            numCalle = in.nextInt();
            System.out.println("Ingrese ciudad");
            ciudad = in.next().toUpperCase();
            System.out.println("Ingrese código postal");
            cp = in.next().toUpperCase();
            System.out.println("Ingrese cantidad de vías");
            cantVias = in.nextInt();
            System.out.println("Ingrese cantidad de plataformas");
            cantPlataformas = in.nextInt();

            boolean exito = elSistema.agregarEstacion(id, calle, numCalle, ciudad, cp, cantVias, cantPlataformas);
            if (exito) {
                lg.escribir("Agregado ESTACIÓN:\n"
                        + "   nombre: " + id + "\n"
                        + "   calle: " + calle + "\n"
                        + "   Nº calle: " + numCalle + "\n"
                        + "   ciudad: " + ciudad + "\n"
                        + "   cód. postal: " + cp + "\n"
                        + "   cantidad de vías: " + cantVias + "\n"
                        + "   cantidad de plataformas:" + cantPlataformas + "\n");

            } else {
                System.out.println("Error cargando estación");
            }
        }
    }
}
