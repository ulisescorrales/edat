/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TPFinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
        HashMap lineas = new HashMap();
        Diccionario estacionesDic = new Diccionario();
        Diccionario trenesDic = new Diccionario();
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
        String datosString = datos.toString();

        StringTokenizer datosTok = new StringTokenizer(datosString, "\n");
        StringTokenizer objetoTok;
        String cabecera;

        LinkedList<Estacion> estacionesList = new LinkedList();
        LinkedList<Tren> trenesList = new LinkedList();

        LinkedList<String> listaRecorrido;
        LinkedList<StringTokenizer> listaRieles = new LinkedList<StringTokenizer>();
        //LinkedList<Queue> listaLineas=new LinkedList();
        while (datosTok.hasMoreTokens()) {
            objetoTok = new StringTokenizer(datosTok.nextToken(), ";");
            cabecera = objetoTok.nextToken();
            //La cabecera indica: 'E' para estación, 'L' para línea y 'T' para tren
            switch (cabecera) {
                case "E":
                    estacionesList.add(crearEstaciones(objetoTok));
                    break;
                case "L":
                    listaRecorrido = new LinkedList();
                    String nombreLinea = objetoTok.nextToken();
                    //Crear la lista con los recorridos                    
                    listaRecorrido.add(nombreLinea);
                    while (objetoTok.hasMoreTokens()) {
                        listaRecorrido.add(objetoTok.nextToken());
                    }
                    //El recorrido está construido como una secuencia de strings, los id de las estaciones
                    lineas.put(nombreLinea, listaRecorrido);
                    break;
                case "T":
                    trenesList.addFirst(crearTren(objetoTok));
                    break;
                case "R":
                    listaRieles.add(objetoTok);
            }
        }

        /*Primero se cargan las estaciones y trenes en una lista y luego se cargan por orden aleatorio en
        el diccionario implementado con árbol AVL */
        cargarEstaciones(estacionesDic, estacionesList);
        //Luego cargar el recorrido con sus objetos
        Random ran = new Random();
        int num;
        while (!trenesList.isEmpty()) {
            num = ran.nextInt(trenesList.size());
            Tren trenAInsertar = trenesList.get(num);
            trenesDic.insertar(trenAInsertar.getIdTren(), trenAInsertar);
            trenesList.remove(num);
        }

        /*LinkedList<Estacion> recorrido;
        while(!listaLineas.isEmpty()){
            recorrido = new LinkedList();
            colaRecorrido=listaLineas.get(0);
            listaLineas.remove(0);
            String nombreLinea=colaRecorrido.remove();
            recorrido.add((Estacion)estacionesDic.obtenerDato(nombreLinea));
            while(!colaRecorrido.isEmpty()){
                recorrido.add((Estacion)estacionesDic.obtenerDato(colaRecorrido.remove()));
            }
            lineas.put(nombreLinea, recorrido);
        }*/
        TrenesSA sistema = new TrenesSA(estacionesDic, trenesDic, lineas);

        StringTokenizer auxToken;
        while (!listaRieles.isEmpty()) {
            auxToken = listaRieles.getFirst();
            cargarRiel(auxToken, sistema);
            listaRieles.removeFirst();
        }

        //Interactuar con el menú hasta salir 
        //System.out.print(sistema.getEstaciones().getEstructura());
        mostrarMenuPrincipal(input, sistema);

    }

    public static void cargarRiel(StringTokenizer ot, TrenesSA sist) {
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
                    break;
                case 1:
                    estacion2 = aux;
                    break;
                case 2:
                    distancia = Integer.parseInt(aux);
                    break;
            }
        }
        sist.agregarRiel(estacion1, estacion2, distancia);
    }

    public static Tren crearTren(StringTokenizer tk) {
        Tren nuevoTren;
        int idTren = Integer.parseInt(tk.nextToken());
        String propulsion = tk.nextToken();
        int cantVagonesPasaj = Integer.parseInt(tk.nextToken());
        int cantVagonesCarga = Integer.parseInt(tk.nextToken());
        String linea = tk.nextToken();

        nuevoTren = new Tren(idTren, propulsion, cantVagonesPasaj, cantVagonesCarga, linea);

        return nuevoTren;
    }

    public static void cargarEstaciones(Diccionario dicEst, LinkedList<Estacion> listEst) {
        Random ran = new Random();
        int pos;
        while (!listEst.isEmpty()) {
            pos = ran.nextInt(listEst.size());
            dicEst.insertar(listEst.get(pos).getNombre(), listEst.get(pos));
            listEst.remove(pos);
        }
    }

    public static Estacion crearEstaciones(StringTokenizer st) {
        String nombre = st.nextToken();
        String calle = st.nextToken();
        int numCalle = Integer.parseInt(st.nextToken());
        String ciudad = st.nextToken();
        String cp = st.nextToken();
        int cantVias = Integer.parseInt(st.nextToken());
        int cantPlataformas = Integer.parseInt(st.nextToken());

        Estacion retornar = new Estacion(nombre, calle, numCalle, ciudad, cp, cantVias, cantPlataformas);
        return retornar;
    }

    public static void mostrarMenuPrincipal(Scanner in, TrenesSA sist) {
        byte opcion;
        System.out.println("Bienvenidos al sistema de TrenesSA");
        boolean resultado = false;
        do {
            System.out.println("Eliga que acción desea realizar:\n"
                    + "ESTACIONES:\n"
                    + "1-Consultar información\n"
                    + "2-Agregar información\n"
                    + "3-Eliminar información\n"
                    + "4-Modificar información"
                    + "99-Salir");
            do {
                opcion = in.nextByte();
                switch (opcion) {
                    case 1:
                        mostrarSubMenuConsulta(in, sist);
                        resultado = true;
                        break;
                    case 2:
                        mostrarSubMenuAgregar(in, sist);
                        resultado = true;
                    case 3:
                        eliminarInformacion(in, sist);
                        resultado = true;
                }
            } while (!resultado);
        } while (opcion != 99);

    }

    public static void eliminarInformacion(Scanner input, TrenesSA sistema) {
        System.out.println("Ingrese que tipo de información quiere eliminar:");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Riel");

        int opcion = input.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese nombre de la estación");
                String nombreEstacion = input.next();
                //Si no existe la estación, reintentar
                while (!sistema.eliminarEstacion(nombreEstacion)) {
                    System.out.println("Error, no existe esa estación. Vuelva a intentarlo:");
                    nombreEstacion = input.next();
                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea");
                String nombreLinea = input.next();
                //Si no existe la línea, reintentar
                while (!sistema.eliminarLinea(nombreLinea)) {
                    System.out.println("Error, no existe la línea. Vuelva a intentarlo:");
                    nombreLinea = input.next();
                }
                break;
            case 3:
                System.out.println("Ingrese el id del tren");
                int idTren = input.nextInt();
                //Si no existe el tren, reintentar
                while (!sistema.eliminarTren(idTren)) {
                    System.out.println("Error, no existe el tren. Vuelva a intentarlo:");
                    idTren = input.nextInt();
                }
                break;
        }
    }

    public static void mostrarSubMenuAgregar(Scanner input, TrenesSA sist) {
        System.out.println("Eliga qué quiere agregar:");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        System.out.println("4-Riel");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                agregarEstacion(sist);
                break;
            case 2:
                agregarLinea(input, sist);
                break;
            case 3:
                agregarTren(input, sist);
                break;
        }
    }

    public static void agregarTren(Scanner in, TrenesSA sistema) {
        System.out.println("Ingrese idTren");
        int id = in.nextInt();
        //Verificar si existe la clave o se introdujo un número negativo
        while (sistema.getTrenes().existeClave(id)||id<0) {
            System.out.println("Ya existe la clave, ingrese otra");
            id = in.nextInt();
        }
        System.out.println("Ingrese propulsión: electricidad, diesel, fuel oil, gasolina, híbrido");
        String propulsion = in.next().toUpperCase();
        while (!propulsion.equals("ELECTRICIDAD") || !propulsion.equals("DIESEL")
                || !propulsion.equals("FUEL OIL") || !propulsion.equals("GASOLINA")
                || !propulsion.equals("HÍBRIDO")) {
            System.out.println("Error, ingrese una opción válida");
            propulsion = in.next().toUpperCase();
        }

        System.out.println("Ingrese cantidad de vagones para pasajeros");
        int cantVagonesP = in.nextInt();
        System.out.println("Ingrese cantidad de vagones para carga");
        int cantVagonesC = in.nextInt();
        System.out.println("Ingrese línea o presione enter para continuar");
        String linea = in.next();

        if (linea.equals("") || sistema.existeLinea(linea)) {
            if (linea.equals("")) {
                linea = "no-asignado";
            }
            sistema.agregarTren(id, propulsion, cantVagonesP, cantVagonesC, linea);
        } else {
            System.out.println("No existe la línea, revise su elección");
            linea = in.next();
        }

    }

    public static void agregarLinea(Scanner in, TrenesSA sistema) {
        String estacion;
        System.out.println("Ingrese el nombre de la primera estación");
        estacion = in.next();
        while (sistema.existeLinea(estacion) == true) {
            System.out.println("Ya existe esa línea, ingrese otra estación");
            estacion = in.next();
        }
        LinkedList<String> recorrido = new LinkedList<String>();
        boolean salir = false;
        System.out.println("Nombre aceptado. Ingrese el recorrido:");
        while (!salir) {
            System.out.println("Ingrese el nombre de una estación, ingrese 99 para terminar");
            estacion = in.next();
            if (!in.equals("99")) {
                if (sistema.getEstaciones().existeClave(estacion)) {
                    recorrido.add(estacion);
                    System.out.println("");
                } else {
                    System.out.println("Estación no existe, vuelva a intentarlo");
                }
            } else {
                salir = true;
            }
        }
    }

    public static void mostrarSubMenuConsulta(Scanner input, TrenesSA sist) {
        System.out.println("Eliga qué quiere consultar");
        System.out.println("1-Estaciones");
        System.out.println("2-Líneas");
        System.out.println("3-Trenes");
        byte opcionElegida = input.nextByte();
        switch (opcionElegida) {
            case 1:
                System.out.println("Ingrese nombre de la estación");
                String nombreEstacion = input.next();
                Estacion consultaEst = sist.getEstacion(nombreEstacion);
                while (consultaEst == null && !nombreEstacion.equals("99")) {
                    System.out.println("No existe estación, vuelva a colocar el nombre o presione 99 para salir");
                    nombreEstacion = input.next();
                }
                if (!nombreEstacion.equals("99")) {
                    imprimirEstacion(input, consultaEst);
                }
                break;
            case 2:
                System.out.println("Ingrese nombre de la línea");
                String nombreLinea = input.next();
                LinkedList<String> linea = sist.getLinea(nombreLinea);
                while (linea == null && !nombreLinea.equals("99")) {
                    System.out.println("No existe la línea, vuelva a colocar el nombre o presione 99 para salir");
                    nombreLinea = input.next();
                }
                if (!nombreLinea.equals("99")) {
                    imprimirLinea(linea);
                }
                System.out.println("Ingrese cualquier letra para continuar");
                input.next();
                break;
            case 3:
                System.out.println("Ingrese el id numérico del tren");
                int id = input.nextInt();
                Tren consultaTren = sist.getTren(id);
                while (consultaTren == null && id != 99) {
                    System.out.println("No existe el tren, verifique id ingresado o presione 99 para salir");
                    id = input.nextInt();
                }
                if (id != 99) {
                    imprimirTren(input, consultaTren);
                }
                break;
            case 99:

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
        System.out.println("");
        System.out.println("---------------");
    }

    public static void imprimirTren(Scanner i, Tren tren) {
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

    public static void agregarEstacion(TrenesSA elSistema) {
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
        System.out.println("Ingrese nombre de la estación");
        id = in.next();
        existeClave = elSistema.getEstaciones().existeClave(id);
        while (existeClave && !id.equals("99")) {
            System.out.println("Nombre ya existe, ingrese otro nombre");
            id = in.next();
            existeClave = elSistema.getEstaciones().existeClave(id);
        }
        if (!id.equals("99")) {
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
                System.out.println("Estación agregada exitosamente");
            } else {
                System.out.println("Error");
            }
        }
    }
}
