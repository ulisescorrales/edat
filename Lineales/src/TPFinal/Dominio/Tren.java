/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Dominio;

/**
 *
 * @author Corrales Ulises
 */
public class Tren {
    
    private int idTren;
    private String propulsion;
    private int cantVagonesPasaj;
    private int cantVagonesCarga;
    private String linea;       

    public Tren(int idTren, String propulsion, int cantVagonesPasaj, int cantVagonesCarga, String linea) {
        this.idTren = idTren;
        this.propulsion = propulsion;
        this.cantVagonesPasaj = cantVagonesPasaj;
        this.cantVagonesCarga = cantVagonesCarga;
        this.linea = linea;
    }

    public int getIdTren() {
        return idTren;
    }
    

    public String getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public int getCantVagonesPasaj() {
        return cantVagonesPasaj;
    }

    public void setCantVagonesPasaj(int cantVagonesPasaj) {
        this.cantVagonesPasaj = cantVagonesPasaj;
    }

    public int getCantVagonesCarga() {
        return cantVagonesCarga;
    }

    public void setCantVagonesCarga(int cantVagonesCarga) {
        this.cantVagonesCarga = cantVagonesCarga;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }
    
    
}
