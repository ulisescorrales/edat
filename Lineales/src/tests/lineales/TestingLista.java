package tests.lineales;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import jerarquicas.dinamicas.ArbolBin;
import lineales.estaticas.Cola;

public class TestingLista {

    public static void main(String[] arg) {
        /*int i;
        Lista listaPr = new Lista();
        Lista listaSec = new Lista();

        listaPr.insertar(1, 1);
        listaPr.insertar(2, 2);
        listaPr.insertar(3, 3);
        listaPr.insertar(4, 4);
        
        System.out.println(listaPr.toString());
        
        listaPr.invertir();
        
        System.out.println(listaPr.toString());
        */
        
     
        
        
        Cola q= new Cola();
        q.poner(1);
        q.poner(2);
        q.poner(3);
        q.poner(4);
        q.poner(5);
        q.poner(6);
        q.poner(7);
        q.poner(8);
        q.poner(9);
        q.sacar();
        System.out.println(q.poner(10));
        
        System.out.println(q.toString());
        
    }

    public static Lista concatenar(Lista lista1, Lista lista2) {
        Lista aux = lista1.clone();
        int long1 = lista1.longitud() + 1;
        int long2 = lista2.longitud(), i;

        for (i = 0; i < long2; i++) {
            aux.insertar(lista2.recuperar(i + 1), long1 + i);
        }
        
        return aux;
    }

    public static boolean comprobar(Lista lista, String cadena) {
        //Invertir cadena
        StringBuilder reverso = new StringBuilder(cadena);
        String rever = reverso.reverse().toString();
        cadena = cadena + "0" + cadena + "0" + rever;//Cadena objetivo        
        int longCadena = cadena.length();
        int longLista = lista.longitud();
        int i = 0;
        boolean correcto = true;

        if (cadena.length() == longLista) {
            while (correcto && i < longLista) {
                if (cadena.charAt(i) - 48 != (int) lista.recuperar(i + 1)) {
                    correcto = false;
                }
                i++;
            }
        }

        return correcto;
    }

    public static boolean comprobar2(Lista lista, String cadena) {
        boolean correcto = true;

        Pila auxPila = new Pila();
        Lista auxLista = new Lista();

        int i, longitud = lista.longitud(), longCadena = cadena.length();
        //conseguir porción invertida
        for (i = 0; i < longCadena; i++) {
            auxPila.apilar(cadena.charAt(i));
        }
        //generar la lista a comparar
        //longitud de esta lista=cadena.length()*3+2
        int longLista = cadena.length() * 3 + 2;
        if (lista.longitud() == auxLista.longitud()) {
            for (i = 1; i <= 2; i++) {
                for (i = 0; i < longCadena; i++) {
                    auxLista.insertar(cadena.charAt(i), auxLista.longitud() + 1);
                }
                auxLista.insertar(0, auxLista.longitud() + 1);
            }
            for (i = 0; i < longCadena; i++) {
                auxLista.insertar(auxPila.obtenerTope(), auxLista.longitud() + 1);
                auxPila.desapilar();
            }
            i = 1;
            while (correcto && i <= longLista) {
                if (auxLista.recuperar(i) != lista.recuperar(i)) {
                    correcto = false;
                }
                i++;
            }

        }
        return correcto;
    }
    public static void invertir(Lista lista){        
        Pila pilaAux= new Pila();        
        int i=1;
        
        while(lista.recuperar(1)!=null){
            pilaAux.apilar(lista.recuperar(1));
            lista.eliminar(1);
        }        
        while(pilaAux.obtenerTope()!=null){
            lista.insertar(pilaAux.obtenerTope(),i);
            pilaAux.desapilar();
            i++;
        }
    }
   
}
