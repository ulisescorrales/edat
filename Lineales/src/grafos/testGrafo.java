/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package grafos;

/**
 *
 * @author ulise
 */
public class testGrafo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grafo prueba=new Grafo();
        
        
        prueba.insertarVertice('B');
        prueba.insertarVertice('C');        
        prueba.insertarVertice('D');
        prueba.insertarVertice('E');
        prueba.insertarVertice('F');
        prueba.insertarVertice('G');
        prueba.insertarVertice('H');
        prueba.insertarVertice('A');
        
        prueba.insertarArco('A', 'B', "prueba");
        prueba.insertarArco('A', 'C', "prueba");
        prueba.insertarArco('A', 'D', "prueba");
        prueba.insertarArco('B', 'F', "prueba");
        prueba.insertarArco('C', 'G', "prueba");
        prueba.insertarArco('D', 'H', "prueba");                                                                       
        
        System.out.println(prueba.toString());
        
        System.out.println(prueba.listarEnProfundidad().toString());
        
    }
    
}
