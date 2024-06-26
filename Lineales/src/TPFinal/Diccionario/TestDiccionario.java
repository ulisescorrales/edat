/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TPFinal.Diccionario;

/**
 *
 * @author ulisescorrales
 */
public class TestDiccionario {

    public static void main(String[] args) {
        //Prueba de borrar un elemento
        prueba2();

    }

    public static void prueba2() {
        Diccionario numeros = new Diccionario();
        numeros.insertar(20, "");

        numeros.insertar(10, "");
        numeros.insertar(35, "");

        numeros.insertar(8, "");
        numeros.insertar(15, "");
        numeros.insertar(28, "");
        numeros.insertar(40, "");

        numeros.insertar(7, "");
        numeros.insertar(9, "");
        numeros.insertar(13, "");
        numeros.insertar(17, "");

        numeros.insertar(25, "");
        numeros.insertar(32, "");
        numeros.insertar(38, "");
        numeros.insertar(42, "");

        numeros.insertar(6, "");
        numeros.insertar(12, "");
        numeros.insertar(14, "");
        numeros.insertar(18, "");
        numeros.insertar(23, "");
        numeros.insertar(27, "");
        numeros.insertar(30, "");
        numeros.insertar(34, "");
        numeros.insertar(37, "");
        numeros.insertar(39, "");
        numeros.insertar(41, "");

        numeros.insertar(11, "");
        numeros.insertar(22, "");
        numeros.insertar(24, "");
        numeros.insertar(26, "");
        numeros.insertar(29, "");
        numeros.insertar(31, "");
        numeros.insertar(33, "");
        numeros.insertar(36, "");

        //numeros.insertar(41, "");
        //numeros.insertar(45, "");
        //numeros.insertar(42, "");
        //numeros.insertar(44, "");
        //numeros.insertar(46, "");
        //numeros.insertar(47, "");
        //numeros.insertar(21, "");
        System.out.println(numeros.getEstructura());
        System.out.println("--------------");
        System.out.println("Eliminar el 9");
        System.out.println("--------------");
        numeros.eliminar(9);
        System.out.println(numeros.getEstructura());
    }

    public static void prueba1() {
        Diccionario numeros = new Diccionario();
        numeros.insertar(10, "");
        numeros.insertar(8, "");
        numeros.insertar(15, "");
        numeros.insertar(7, "");
        numeros.insertar(9, "");
        numeros.insertar(13, "");
        numeros.insertar(17, "");
        numeros.insertar(6, "");
        numeros.insertar(12, "");
        numeros.insertar(14, "");
        numeros.insertar(16, "");
        numeros.insertar(18, "");
        numeros.insertar(11, "");

        System.out.println(numeros.getEstructura());
        numeros.eliminar(10);
        System.out.println("--------------");
        System.out.println("Eliminar el 10");
        System.out.println("--------------");
        System.out.println(numeros.getEstructura());
    }

    public static void prueba3() {
        Diccionario numeros = new Diccionario();
        numeros.insertar(3, "");
        numeros.insertar(2, "");
        numeros.insertar(4, "");
        numeros.insertar(1, "");
        System.out.println(numeros.getEstructura());
        
        numeros.eliminar(3);
        System.out.println("--------------");
        System.out.println("Eliminar el 3");
        System.out.println("--------------");
        System.out.println(numeros.getEstructura());
    }
}
