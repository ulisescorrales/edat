/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TPFinal;

/**
 *
 * @author ulisescorrales
 */
public class PruebaAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Diccionario dic=new Diccionario();
        
        dic.insertar(1, "dato");
        dic.insertar(2, "dato");
        dic.insertar(3, "dato");
        dic.insertar(4, "dato");
        dic.insertar(5, "dato");
        dic.insertar(6, "dato");
        
        System.out.println(dic.getEstructura());
    }
    
}
