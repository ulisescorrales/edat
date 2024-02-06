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
        HashMap lineas=new HashMap();
        Diccionario estacionesDic=new Diccionario();
        Diccionario trenesDic=new Diccionario();
        try {
            FileReader txt=new FileReader("src/TPFinal/DatosTPFINAL.txt");
            int i;
            i=txt.read();
            while(i!=-1){                    
                datos.append((char)i);
                i=txt.read();                
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TPFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String datosString=datos.toString();        
        
        StringTokenizer datosTok=new StringTokenizer(datosString,"\n");
        StringTokenizer objetoTok;       
        String cabecera;
        
        LinkedList<Estacion> estacionesList=new LinkedList();
        LinkedList<Tren> trenesList=new LinkedList();
        LinkedList<String> recorrido=new LinkedList();
        while(datosTok.hasMoreTokens()){            
            objetoTok=new StringTokenizer(datosTok.nextToken(),";");
            cabecera=objetoTok.nextToken();
            //La cabecera indica: 'E' para estación, 'L' para línea y 'T' para tren
            switch(cabecera){
                case "E":
                    estacionesList.add(crearEstaciones(objetoTok));
                    break;
                case "L":                                        
                    String nombreLinea=objetoTok.nextToken();
                    //Crear la lista con los recorridos
                    recorrido.add(nombreLinea);
                    while(objetoTok.hasMoreTokens()){
                        recorrido.add(objetoTok.nextToken());
                    }
                    //El recorrido está construido como una secuencia de strings, los id de las estaciones
                    lineas.put(nombreLinea, recorrido);
                    break;
                case "T":
                    trenesList.addFirst(crearTren(objetoTok));
                    break;
            }            
        }                
        
        /*Primero se cargan las estaciones y trenes en una lista y luego se cargan por orden aleatorio en
        el diccionario implementado con árbol AVL */
        cargarEstaciones(estacionesDic,estacionesList);
        //Luego cargar el recorrido con sus objetos
        Random ran=new Random();
        int num;
        while(!trenesList.isEmpty()){
            num=ran.nextInt(trenesList.size());
            Tren trenAInsertar=trenesList.get(num);
            trenesDic.insertar(trenAInsertar.getIdTren(), trenAInsertar);
            trenesList.remove(num);
        }
        
        
        //Inicialización del menú
        TrenesSA sistema = new TrenesSA();
        byte opcion;                       
        //Recuperar datos guardados        
        //Interactuar con el menú hasta salir
        do {
            mostrarMenuPrincipal();
            opcion = input.nextByte();
            if(opcion!=99){
                mostrarSubMenuConsulta(opcion, sistema);
                opcion=input.nextByte();
                
            }
        } while (opcion != 99);
        
    }    
    public static Tren crearTren(StringTokenizer tk){
        Tren nuevoTren;        
        int idTren=Integer.parseInt(tk.nextToken());
        String propulsion=tk.nextToken();
        int cantVagonesPasaj=Integer.parseInt(tk.nextToken());
        int cantVagonesCarga=Integer.parseInt(tk.nextToken());
        String linea=tk.nextToken();        
        
        nuevoTren=new Tren(idTren,propulsion,cantVagonesPasaj,cantVagonesCarga,linea);
        
        return nuevoTren;
    }
    public static void cargarEstaciones(Diccionario dicEst,LinkedList<Estacion> listEst){        
        Random ran=new Random();
        int pos;
        while(!listEst.isEmpty()){
            pos=ran.nextInt(listEst.size());
            dicEst.insertar(listEst.get(pos).getNombre(),listEst.get(pos));
            listEst.remove(pos);
        }
    }
    public static Estacion crearEstaciones(StringTokenizer st){
        String nombre=st.nextToken();        
        String calle=st.nextToken();
        int numCalle=Integer.parseInt(st.nextToken());
        String ciudad=st.nextToken();
        String cp=st.nextToken();
        int cantVias=Integer.parseInt(st.nextToken());
        int cantPlataformas=Integer.parseInt(st.nextToken());
        
        Estacion retornar=new Estacion(nombre,calle,numCalle,ciudad,cp,cantVias,cantPlataformas);
        return retornar;
    }

    public static String mostrarMenuPrincipal() {
        String cadena = "Bienvenidos al sistema de TrenesSA\n"
                + "Eliga que acción desea realizar:\n"
                + "ESTACIONES:\n"
                + "1-Consultar información\n"
                + "2-Agregar información\n"
                + "3-Eliminar información\n"
                + "4-Modificar información"
                + "99-Salir";
        return cadena;
    }    

    public static void mostrarSubMenuConsulta(byte opcionElegida, TrenesSA unSistema) {
        switch (opcionElegida) {
            case 1:
                System.out.println("Eliga qué quiere consultar");
                System.out.println("1-Estaciones");
                System.out.println("2-Líneas");
                System.out.println("3-Trenes");                
                break;
            case 99:

                break;
        }
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
        while(existeClave){
            System.out.println("Nombre ya existe, ingrese otro nombre");
            id=in.next();
            existeClave = elSistema.getEstaciones().existeClave(id);
        }        
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
                
        elSistema.agregarEstacion(id, calle, numCalle, ciudad, cp, cantVias, cantPlataformas);
    }
}
