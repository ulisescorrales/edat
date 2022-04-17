package tests.lineales;

import lineales.dinamicas.Lista;

public class TestingLista {

    public static void main(String[] arg) {
        int i;
        Lista listaPr = new Lista();
        Lista listaSec = new Lista();

        listaPr.insertar(1,1);
        listaPr.insertar(2,2);
        listaPr.insertar(3,3);
        listaPr.insertar(0,4);
        listaPr.insertar(1,5);
        listaPr.insertar(2,6);
        listaPr.insertar(3,7);
        listaPr.insertar(0,8);
        listaPr.insertar(3,9);
        listaPr.insertar(2,10);
        listaPr.insertar(1,11);
        
        System.out.println(comprobar(listaPr,"123"));

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
        cadena=cadena+"0"+cadena+"0"+rever;//Cadena objetivo        
        int longCadena=cadena.length();
        int longLista=lista.longitud();
        int i=0;
        boolean correcto=true;
        
        if(cadena.length()==longLista){
            while(correcto && i<longLista){
                if(cadena.charAt(i)-48!=(int)lista.recuperar(i+1)){
                    correcto=false;                    
                }
                i++;
            }
        }

        return correcto;
    }
}
