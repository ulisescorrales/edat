/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

/**
 *
 * @author Corrales Ulises
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumCalle() {
        return numCalle;
    }

    public void setNumCalle(int numCalle) {
        this.numCalle = numCalle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getCantVias() {
        return cantVias;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }
    
    
}
