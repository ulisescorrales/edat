/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ulisescorrales
 */
public class Log { 
    //Clase que escribe sobre un archivo .log
    private final String path;

    public Log(String path) throws IOException{
        this.path = path;
        FileWriter file = new FileWriter(this.path);
        //Vaciar el archivo log anterior
        file.write("");
        file.close();
    }        
    
    
    public boolean escribir(String texto){
        //Abre, escribe y cierra el archivo
        boolean exito=true;        
        FileWriter file;        
        try {
            file = new FileWriter(this.path,true);
            file.write(texto);            
            file.close();
        } catch (IOException ex) {
            //Error en la apertura/escritura del archivo
            exito=false;
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }
}
