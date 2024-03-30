/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TPFinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
 * @author ulisescorrales
 */
public class TPFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Leer el archivo DatosTPFinal.txt y realizar la carga inicial del sistema
        StringBuilder datos = new StringBuilder();
        try {
            FileReader txt = new FileReader("src/TPFinal/DatosTPFINAL.txt");
            int i;
            i = txt.read();
            while (i != -1) {
                datos.append((char) i);
                i = txt.read();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileWriter logs = null;
        try {
            logs = new FileWriter("./src/TPFinal/logs.log");
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String datosString = datos.toString();

        //Separar los tokens por líneas
        StringTokenizer datosTok = new StringTokenizer(datosString, "\n");
        StringTokenizer objetoTok;
        String cabecera;
        //Contador de tokens usados para poder identificar errores        

        LinkedList<StringTokenizer> estacionesList = new LinkedList();
        LinkedList<StringTokenizer> trenesList = new LinkedList();
        LinkedList<StringTokenizer> rielesList = new LinkedList();
        Queue<StringTokenizer> lineasCola = new LinkedList();

        TrenesSA sistema = new TrenesSA();

        try {
            logs.write("---EMPIEZA CARGA INICIAL---\n");
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Analizar cada token
        while (datosTok.hasMoreTokens()) {
            objetoTok = new StringTokenizer(datosTok.nextToken(), ";");
            cabecera = objetoTok.nextToken();
            //La cabecera indica: 'E' para estación, 'L' para línea, 'T' para tren y 'R' para riel
            switch (cabecera) {
                case "E":
                    estacionesList.add(objetoTok);
                    break;
                case "L":

                    lineasCola.add(objetoTok);
                    break;
                case "T":
                    trenesList.addFirst(objetoTok);
                    break;
                case "R":
                    //Almacenar los rieles en una lista para que se carguen después de las estaciones
                    rielesList.add(objetoTok);
            }
        }

        /*Primero se cargaron las estaciones y trenes en una lista y luego aquí se cargan por orden aleatorio en
        el diccionario implementado con árbol AVL */
        cargarEstaciones(sistema, estacionesList, logs);
        cargarLineas(sistema, lineasCola, logs);
        cargarTrenes(sistema, trenesList, logs);
        cargarRieles(sistema, rielesList, logs);

        try {
            logs.write("---FIN CARGA INICIAL---\n");
            logs.flush();
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Interactuar con el menú hasta salir            
        mostrarMenuPrincipal(input, sistema, logs);
    }

    public static void mostrarMenuPrincipal(Scanner in, TrenesSA sist, FileWriter lg) {
        //Entrada al menú principal
        byte opcion;
        System.out.println("Bienvenidos al sistema de TrenesSA");        
        do {
            System.out.println("--------------------------------");
            System.out.println("Eliga que acción desea realizar:\n"
                    + "ESTACIONES:\n"
                    + "1-Consultar información\n"
                    + "2-Agregar información\n"
                    + "3-Eliminar información\n"
                    + "4-Modificar información"
                    + "-1-Salir");

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

    }

    public static void modificarInformacion(Scanner input, TrenesSA sistema, FileWriter logs) {
        System.out.println("Seleccione qué quiere modificar: ");
        System.out.println("1-Estacion");
        System.out.println("2-Tren");
        System.out.println("3-Linea");
        System.out.println("4-Riel");
        System.out.println("Ingrese -1 para salir");

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
                modificarEstacion(sistema, input, logs);
                break;
            case 4:
                modificarRiel(sistema, input, logs);
                break;
        }
    }

    public static void modificarRiel(TrenesSA sist, Scanner in, FileWriter lg) {
        System.out.println("Ingrese el nombre de una estación");
        String estacion1 = in.next();
        while (!sist.existeEstacion(estacion1)) {
            System.out.println("No existe estación, intente nuevamente");
            estacion1 = in.next();
        }
        System.out.println("Ingrese el nombre de la otra estación");
        String estacion2 = in.next();
        while (!sist.existeEstacion(estacion2)) {
            System.out.println("No existe estación, intente nuevamente");
            estacion2 = in.next();
        }
        if (sist.existeRiel(estacion1, estacion2)) {
            int distanciaAnterior = sist.getDistanciaRiel(estacion1, estacion2);
            System.out.println("Ingrese una nueva distancia entre rieles");
            int nuevaDistancia = in.nextInt();
            if (sist.modificarDistancia(estacion1, estacion2, nuevaDistancia)) {
                try {
                    lg.write("Modificado rieal " + estacion1 + " -" + estacion2 + ": " + distanciaAnterior + "->" + nuevaDistancia);
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("No existe riel entre las estaciones");
        }
    }

    public static void modificarLinea(TrenesSA sist, Scanner in, FileWriter lg) {
        //Método para modificar una línea, dado un nombre de línea
        System.out.println("Ingrese el número de línea, -1 para salir");
        String nombreLinea = in.next();
        LinkedList<String> linea = sist.getLinea(nombreLinea);
        while (linea.isEmpty() && !nombreLinea.equals("-1")) {
            System.out.println("No existe la línea, intente nuevamente o ingrese -1 para salir");
            linea = sist.getLinea(nombreLinea);
        }
        if (!nombreLinea.equals("-1")) {
            imprimirLinea(linea);
            //Vaciar la lista de la línea desde la última hasta la primera (la clave)
            while (linea.size() != 1) {
                linea.removeLast();
            }

            boolean salir = false;
            String estacion;
            String lineasString = nombreLinea;
            while (!salir) {
                //Ingresar el recorrido a partir de la segunda estación, solo admite estaciones cargadas al sistema
                System.out.println("Ingrese el nombre de una estación del recorrido, ingrese -1 para terminar");
                estacion = in.next();
                if (!in.equals("-1")) {
                    if (sist.getEstaciones().existeClave(estacion)) {
                        linea.add(estacion);
                        lineasString += " - " + estacion;
                    } else {
                        System.out.println("Estación no existe, vuelva a intentarlo");
                    }
                } else {
                    salir = true;
                }
            }
            System.out.println("Estación modificada: " + lineasString);
            //Escribir en el archivo log
            try {
                lg.write("Modificada línea " + nombreLinea + ": " + lineasString);
                lg.flush();
            } catch (IOException ex) {
                Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void modificarTren(TrenesSA sist, Scanner in, FileWriter lg) {
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
                    System.out.println("Ingrese propulsión, -1 para cancelar");
                    String propulsion = in.nextLine().toUpperCase();
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
                        try {
                            lg.write("Modificado propulsión de TREN " + idTren + ": " + propulsionAnterior + "->" + propulsion);
                            lg.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                        try {
                            lg.write("Modificado cant. pasajeros de TREN " + idTren + ": " + cantAnterior + "->" + cantVagones);
                            lg.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                        try {
                            lg.write("Modificado cant. vagones de carga de TREN " + idTren + ": " + cantAnterior + "->" + cantVagonesCarga);
                            lg.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Ingrese una línea cargada, -1 para salir");
                    String linea = in.next();
                    while (!sist.existeLinea(linea) && !linea.equals("-1")) {
                        System.out.println("No existe la línea, intente nuevamente o ingrese -1 para salir");
                    }
                    if (!linea.equals("-1")) {
                        String lineaAnterior = tren.getLinea();
                        try {
                            lg.write("Modificado línea de TREN " + idTren + ": " + lineaAnterior + "->" + linea);
                            lg.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

            }

        }
    }

    public static void modificarEstacion(TrenesSA sist, Scanner in, FileWriter lg) {
        //Submenú para modificar una estación dada
        System.out.println("Ingrese el nombre de la estación a modificar, -1 para salir");
        String est = in.next();
        byte opcion;
        Estacion estacion = sist.getEstacion(est);
        while (estacion == null && !est.equals("-1")) {
            System.out.println("No existe estación, ingrese otra o -1 para salir");
            est = in.next();
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
            System.out.println("Cualquier otro número para salir");

            opcion = in.nextByte();
            switch (opcion) {

                case 1:
                    System.out.println("Ingrese calle");
                    String calleAnterior = estacion.getCalle();
                    String calle = in.next();
                    estacion.setCalle(calle);
                     {
                        try {
                            lg.write("Modificado calle de ESTACIÓN " + calleAnterior + "->" + calle);
                            lg.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                case 2:
                    System.out.println("Ingrese número de calle");
                    int numCalleAnterior = estacion.getNumCalle();
                    int numCalle = in.nextInt();
                    estacion.setNumCalle(numCalle);
                    try {
                        lg.write("Modificado núm. calle de ESTACIÓN " + numCalleAnterior + "->" + numCalle);
                        lg.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                case 3:
                    System.out.println("Ingrese Ciudad");
                    String ciudadAnterior = estacion.getCiudad();
                    String ciudad = in.next();
                    estacion.setCiudad(ciudad);
                    try {
                        lg.write("Modificado ciudad de ESTACIÓN " + ciudadAnterior + "->" + ciudad);
                        lg.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
    }

    public static void cargarLineas(TrenesSA sist, Queue<StringTokenizer> tokensQueue, FileWriter lg) {
        //Método que carga las líneas de la carga inicial al sistema
        while (!tokensQueue.isEmpty()) {
            StringTokenizer linea = tokensQueue.poll();

            LinkedList listaRecorrido = new LinkedList();
            String lineas = "";
            String aux;
            while (linea.hasMoreTokens()) {
                aux = linea.nextToken();
                lineas += aux + "-";
                listaRecorrido.add(aux);
            }
            //El recorrido está construido como una secuencia de strings (los id de las estaciones)
            if (sist.agregarLinea(listaRecorrido)) {
                try {
                    lg.write("Agregada LÍNEA: " + lineas + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    lg.write("Error al agregar LÍNEA: " + lineas + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void cargarRieles(TrenesSA sist, LinkedList<StringTokenizer> tokens, FileWriter lg) {
        while (!tokens.isEmpty()) {
            cargarRiel(sist, tokens.getFirst(), lg);
            tokens.removeFirst();
        }
    }

    public static void cargarTrenes(TrenesSA sist, LinkedList<StringTokenizer> listaTrenes, FileWriter lg) {
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
            String linea = trenTk.nextToken();

            //Si no existe la línea indicada, se deja como no asignado
            if (!sist.existeLinea(linea)) {
                linea = "no asignado";
            }

            if (sist.agregarTren(idTren, propulsion, cantVagonesPasaj, cantVagonesCarga, linea)) {
                try {
                    lg.write("Agregado TREN:" + idTren + "\n"
                            + "   propulsión: " + propulsion + "\n"
                            + "   cant. vagones pasajeros :" + cantVagonesPasaj + "\n"
                            + "   cant. vagones carga: " + cantVagonesCarga + "\n"
                            + "   linea: " + linea + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    lg.write("Error cargando TREN:" + idTren + "\n"
                            + "   propulsión: " + propulsion + "\n"
                            + "   cant. vagones pasajeros :" + cantVagonesPasaj + "\n"
                            + "   cant. vagones carga: " + cantVagonesCarga + "\n"
                            + "   linea: " + linea + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            listaTrenes.remove(num);
        }
    }

    public static void cargarRiel(TrenesSA sist, StringTokenizer ot, FileWriter l) {
        String aux;
        byte paso = 0;
        String estacion1 = "";
        String estacion2 = "";
        int distancia = 0;
        while (ot.hasMoreTokens()) {
            aux = ot.nextToken();
            switch (paso) {
                case 0:
                    estacion1 = aux;
                    paso++;
                    break;
                case 1:
                    estacion2 = aux;
                    paso++;
                    break;
                case 2:
                    distancia = Integer.parseInt(aux);
                    break;
            }
        }
        if (!sist.agregarRiel(estacion1, estacion2, distancia)) {
            try {
                l.write("Error cargando RIEL:" + estacion1 + "-" + estacion2 + "-" + distancia + "\n");
                l.flush();
            } catch (IOException ex) {
                Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {                
                l.write("Agregado RIEL: " + estacion1 + "-" + estacion2 + " - " + distancia + "Km" + "\n");
                l.flush();
            } catch (IOException ex) {
                Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void cargarEstaciones(TrenesSA sist, LinkedList<StringTokenizer> listEst, FileWriter lg) {
        //Método para cargar estaciones de forma aleatoria desde la carga inicial a partir de la lista armada
        Random ran = new Random();
        int pos;
        while (!listEst.isEmpty()) {
            //Cargar aleatoriamente las estaciones sobre el diccionario
            pos = ran.nextInt(listEst.size());
            StringTokenizer estTok = listEst.get(pos);
            String nombre = estTok.nextToken();
            String calle = estTok.nextToken();
            int numCalle = Integer.parseInt(estTok.nextToken());
            String ciudad = estTok.nextToken();
            String cp = estTok.nextToken();
            int cantVias = Integer.parseInt(estTok.nextToken());
            int cantPlataformas = Integer.parseInt(estTok.nextToken());
            if (sist.agregarEstacion(nombre, calle, numCalle, ciudad, cp, cantVias, cantPlataformas)) {
                //Agregar al grafo de rieles y escribir en el archivo .log
                sist.getRieles().insertarVertice(nombre);
                try {
                    lg.write("Agregado ESTACIÓN: " + nombre + "\n"
                            + "   calle: " + calle + "\n"
                            + "   Nº calle: " + numCalle + "\n"
                            + "   ciudad: " + ciudad + "\n"
                            + "   cód. postal: " + cp + "\n"
                            + "   cantidad de vías: " + cantVias + "\n"
                            + "   cantidad de plataformas:" + cantPlataformas + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    lg.write("Error agregado ESTACIÓN: " + nombre + "\n"
                            + "   calle: " + calle + "\n"
                            + "   Nº calle: " + numCalle + "\n"
                            + "   ciudad: " + ciudad + "\n"
                            + "   cód. postal: " + cp + "\n"
                            + "   cantidad de vías: " + cantVias + "\n"
                            + "   cantidad de plataformas:" + cantPlataformas + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //            
            listEst.remove(pos);
        }
    }

    public static void eliminarInformacion(Scanner input, TrenesSA sistema, FileWriter logs) {
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
                System.out.println("Ingrese nombre de la estación, ingrese -1 para salir");
                String nombreEstacion = input.next();
                exito = sistema.eliminarEstacion(nombreEstacion);
                //Si no existe la estación, reintentar
                while (!exito && !input.equals("-1")) {
                    System.out.println("Error, no existe esa estación. Vuelva a intentarlo o ingrese -1 para salir");
                    nombreEstacion = input.next();
                    exito = sistema.eliminarEstacion(nombreEstacion);
                }
                if (exito) {
                    //Registrar en el archivo .log
                    try {
                        logs.write("Eliminado ESTACIÓN: " + nombreEstacion);
                        logs.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea, ingrese -1 para salir");
                String nombreLinea = input.next();
                exito = sistema.eliminarLinea(nombreLinea);
                //Si no existe la línea, reintentar
                while (!exito && !nombreLinea.equals("-1")) {
                    System.out.println("Error, no existe la línea. Vuelva a intentarlo o ingrese -1 para salir");
                    nombreLinea = input.next();
                    exito = sistema.eliminarLinea(nombreLinea);
                }
                //Registrar en el archivo .log
                if (exito) {
                    try {
                        logs.write("Eliminado LÍNEA " + nombreLinea);
                        logs.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el id del tren o ingrese -1 para salir");
                int idTren = input.nextInt();
                exito = sistema.eliminarTren(idTren);
                //Si no existe el tren, reintentar
                while (!exito || idTren != -1) {
                    System.out.println("Error, no existe el tren. Vuelva a intentarlo o ingrese -1 para salir");
                    idTren = input.nextInt();
                    exito = sistema.eliminarTren(idTren);
                }
                //Registrar en el archivo .log
                if (exito) {
                    try {
                        logs.write("Eliminado TREN: " + idTren);
                        logs.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 4:
                System.out.println("Ingrese una estación o ingrese -1 para salir");                    
                String estacion1 = input.next();                
                while (!sistema.existeEstacion(estacion1) && !estacion1.equals("-1")) {
                    System.out.println("Error, no existe estación. Intente nuevamente o presion -1 para salir");
                    estacion1 = input.next();
                }
                if (!estacion1.equals("-1")) {
                    System.out.println("Ingrese la otra estación");
                    String estacion2 = input.next();
                    while (!sistema.existeEstacion(estacion2) && !estacion2.equals("-1")) {
                        System.out.println("Error, no existe estación. Intente nuevamente o presion -1 para salir");
                        estacion2 = input.next();
                    }
                    if (!estacion2.equals("-1")) {
                        if (sistema.eliminarRiel(estacion1, estacion2)) {
                            try {
                                logs.write("Eliminado RIEL: " + estacion1 + " <-> " + estacion2);
                                logs.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{
                            System.out.println("Error. Las estaciones no están directamente unidas.");
                        }
                    }
                }
                break;
        }
    }

    public static void mostrarSubMenuAgregar(Scanner input, TrenesSA sist, FileWriter logs) {
        //Método que muestra el submenú para agregar información
        System.out.println("Eliga qué quiere agregar:");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Riel");
        System.out.println("-1-Salir");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                agregarEstacion(sist, logs);
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

    public static void agregarRiel(Scanner in, TrenesSA sistema, FileWriter lg) {
        //Método para agregar rieles a través de la terminal
        System.out.println("Ingrese el nombre de una estación o -1 para cancelar");
        String estacion1 = in.next();
        while (!sistema.existeEstacion(estacion1) && !estacion1.equals("-1")) {
            System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
            estacion1 = in.next();
        }
        if (!estacion1.equals("-1")) {
            System.out.println("Ingrese el nombre de otra estación");
            String estacion2 = in.next();
            while (!sistema.existeEstacion(estacion2) && !estacion2.equals("-1")) {
                System.out.println("No existe estación, intente nuevamente o ingrese -1 para salir");
                estacion2 = in.nextLine();
            }
            if (!estacion2.equals("-1")) {
                System.out.println("Ingrese distancia entre estaciones en km");
                int distancia = in.nextInt();
                while (distancia < 0) {
                    System.out.println("Distancia debe ser un número positivo");
                    distancia = in.nextInt();
                }
                if (sistema.agregarRiel(estacion2, estacion2, distancia)) {
                    try {
                        lg.write("Agregado RIEL: " + estacion1 + " - " + estacion2 + " - " + distancia +" km");
                        lg.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void agregarTren(Scanner in, TrenesSA sistema, FileWriter lg) {
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
                String linea = in.next();
                while (!sistema.existeLinea(linea)) {
                    System.out.println("No existe la línea indicada, vuelva a intentarlo");
                    linea = in.next();
                }
                //Agregar los datos al sistema
                if (sistema.agregarTren(id, propulsion, cantVagonesP, cantVagonesC, linea)) {
                    try {
                        //Registrar en el archivo .log en caso de éxito
                        lg.write("Agregado TREN:\n"
                                + "   Id: " + id + "\n"
                                + "   Propulsión: " + propulsion + "\n"
                                + "   Cantidad vagones pasajeros: " + cantVagonesP + "\n"
                                + "   Cantidad vagones carga: " + cantVagonesC + "\n"
                                + "   Línea: " + linea);
                        lg.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void agregarLinea(Scanner in, TrenesSA sistema, FileWriter lg) {
        //Método para agregar una línea desde el menú interactivo
        //El nombre del a línea es la primera estación del recorrido
        String estacion;
        String lineasString = "";
        System.out.println("Ingrese el nombre de la línea, ingrese -1 ");
        estacion = in.next();
        while (!estacion.equals("-1") && (sistema.existeLinea(estacion) == true || !sistema.existeEstacion(estacion))) {
            System.out.println("Error. Ya existe la línea o la estación no existe");
            estacion = in.nextLine();
        }
        if (!estacion.equals("-1")) {
            lineasString += estacion;
            LinkedList<String> recorrido = new LinkedList();
            while (!estacion.equals("-1")) {
                System.out.println("Ingrese el nombre de una estación del recorrido, ingrese -1 para terminar");
                estacion = in.nextLine();
                //Ingresar el recorrido, solo admite estaciones cargadas al sistema                               
                while (!sistema.getEstaciones().existeClave(estacion) && !estacion.equals("-1")) {
                    System.out.println("Estación no existe, vuelva a intentarlo");
                    estacion = in.nextLine();
                }
                if (!estacion.equals("-1")) {
                    lineasString += " -> " + estacion;
                    recorrido.add(estacion);
                }
            }
            //Agregar línea y escribir en el archivo log           
            if (sistema.agregarLinea(recorrido)) {
                try {
                    lg.write("agregado LíNEA: " + lineasString + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    lg.write("Error agregando LíNEA: " + lineasString + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void mostrarSubMenuConsulta(Scanner input, TrenesSA sist) {
        System.out.println("Eliga qué quiere consultar");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("Otro número-Salir");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                System.out.println("Ingrese nombre de la estación, -1 para salir");
                String nombreEstacion = input.next();
                Estacion consultaEst = sist.getEstacion(nombreEstacion);
                while (consultaEst == null && !nombreEstacion.equals("-1")) {
                    System.out.println("No existe estación, vuelva a colocar el nombre o presione -1 para salir");
                    nombreEstacion = input.next();
                }
                if (!nombreEstacion.equals("-1")) {
                    imprimirEstacion(input, consultaEst);
                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea");
                String nombreLinea = input.next();
                LinkedList<String> linea = sist.getLinea(nombreLinea);
                while (linea == null && !nombreLinea.equals("-1")) {
                    System.out.println("No existe la línea, vuelva a colocar el nombre o presione -1 para salir");
                    nombreLinea = input.next();
                }
                if (!nombreLinea.equals("-1")) {
                    imprimirLinea(linea);
                    System.out.println("Ingrese cualquier letra para continuar");
                    input.next();
                }
                break;
            case 3:
                System.out.println("Ingrese el id numérico del tren");
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
        }
    }

    public static void imprimirLinea(LinkedList<String> lineaList) {
        System.out.println("-------RESULTADO:--------");
        System.out.println("Recorrido: ");

        while (!lineaList.isEmpty()) {
            System.out.print(lineaList.getFirst() + " -> ");
            lineaList.removeFirst();
        }
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
        System.out.println("Ingrese cualquier letra para continuar");
        i.next();
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
        System.out.println("Ingrese cualquier letra para continuar");
        i.next();
    }

    public static void agregarEstacion(TrenesSA elSistema, FileWriter lg) {
        //Atributos para crear la estación
        String id;
        String calle;
        int numCalle;
        String ciudad;
        String cp;
        int cantVias;
        int cantPlataformas;
        //
        Scanner in = new Scanner(System.in);
        boolean existeClave;

        //Pedir los datos y enviárlo a la clase TrenesSA
        System.out.println("Ingrese nombre de la estación, -1 para salir");
        id = in.next();
        existeClave = elSistema.getEstaciones().existeClave(id);
        while (existeClave && !id.equals("-1")) {
            System.out.println("Nombre ya existe, ingrese otro nombre");
            id = in.next();
            existeClave = elSistema.getEstaciones().existeClave(id);
        }
        if (!id.equals("-1")) {
            System.out.println("Ingrese nombre de la calle");
            calle = in.next();
            System.out.println("Ingrese número de la calle");
            numCalle = in.nextInt();
            System.out.println("Ingrese ciudad");
            ciudad = in.next();
            System.out.println("Ingrese código postal");
            cp = in.next();
            System.out.println("Ingrese cantidad de vías");
            cantVias = in.nextInt();
            System.out.println("Ingrese cantidad de plataformas");
            cantPlataformas = in.nextInt();

            boolean exito = elSistema.agregarEstacion(id, calle, numCalle, ciudad, cp, cantVias, cantPlataformas);
            if (exito) {
                try {
                    lg.write("Agregado ESTACIÓN:\n"
                            + "   nombre: " + id + "\n"
                            + "   calle: " + calle + "\n"
                            + "   Nº calle: " + numCalle + "\n"
                            + "   ciudad: " + ciudad + "\n"
                            + "   cód. postal: " + cp + "\n"
                            + "   cantidad de vías: " + cantVias + "\n"
                            + "   cantidad de plataformas:" + cantPlataformas + "\n");
                    lg.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Error cargando estación");
            }
        }
    }
}
