/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

/**
 *
 * @author ulises
 */
public class Estacion {
    //Identificador 
    private String nombre;
    private String calle;
    private int numCalle;
    private String ciudad;
    private String cp;
    private int cantVias;
    private int cantPlataformas;    

    public Estacion(String nombre,String calle, int numCalle, String ciudad, String cp,int cantVias, int cantPlataformas) {
        this.nombre=nombre;
        this.calle = calle;
        this.numCalle = numCalle;
        this.ciudad = ciudad;
        this.cp = cp;
        this.cantPlataformas = cantPlataformas;
        this.cantVias = cantVias;
    }
    
    
}
