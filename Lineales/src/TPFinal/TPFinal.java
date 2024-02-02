/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TPFinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
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
        //Scanner input = new Scanner(System.in);
        
        //Leer el archivo DatosTPFinal.txt y realizar la carga inicial del sistema
        StringBuilder datos = new StringBuilder();
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
        String temp;
        
        LinkedList<Estacion> estaciones=new LinkedList();        
        while(datosTok.hasMoreTokens()){            
            objetoTok=new StringTokenizer(datosTok.nextToken(),";");
            temp=objetoTok.nextToken();
            switch(temp){
                case "E":
                    estaciones.add(crearEstaciones(objetoTok));
                    break;
                case "L":
                    
            }            
        }
        System.out.println(estaciones.size());
        /*TrenesSA sistema = new TrenesSA();
        byte opcion;
        boolean salir = false;
        
        
        //Recuperar datos guardados        
        //Interactuar con el menú hasta salir
        do {
            mostrarMenuPrincipal();
            opcion = input.nextByte();
            mostrarSubMenu(opcion, sistema);
        } while (opcion != 99);
        //Guardar cambios*/
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
                + "1-Agregar estación\n"
                + "2-Modificar estación\n"
                + "3-Eliminar estación\n"
                + "4-Consultar estación"
                + "99-Salir";
        return cadena;
    }

    public static void mostrarSubMenu(byte opcionElegida, TrenesSA unSistema) {
        switch (opcionElegida) {
            case 1:
                agregarEstacion(unSistema);
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