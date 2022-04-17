/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineales.estaticas;

/**
 *
 * @author ulise
 */
public class ArbolBin {
    private static final int TAM=10;
    private CeldaBin[] arbol;
    private int raiz;
    
    public ArbolBin(){
        int i;
        this.arbol= new CeldaBin[TAM];
        for(i=0;i<TAM;i++){
            this.arbol[i]=new CeldaBin(null,-1,-1);
        }
        this.raiz=-1;
    }
    public insertar;
}
