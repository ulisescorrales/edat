/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal;

/**
 *
 * @author ulise
 */
public class Tren {
    
    private String propulsion;
    private int cantVagonesPasaj;
    private int cantVagonesCarga;
    private String linea;    
    private int idVia;//?

    public Tren(String propulsion, int cantVagonesPasaj, int cantVagonesCarga, String linea, int idVia) {
        this.propulsion = propulsion;
        this.cantVagonesPasaj = cantVagonesPasaj;
        this.cantVagonesCarga = cantVagonesCarga;
        this.linea = linea;
        this.idVia = idVia;
    }
    
    
}
