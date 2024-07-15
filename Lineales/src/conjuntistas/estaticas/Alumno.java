/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.estaticas;

/**
 *
 * @author ulises.corrales
 */
public class Alumno {
    private int dni;
    private String nombre;
    private String apellido;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.dni;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alumno other = (Alumno) obj;
        return true;
    }

    

    

    
    
    
}
