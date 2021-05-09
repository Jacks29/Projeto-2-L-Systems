/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author arthu
 */
public class GerarSintaxe {
    public static String gerarString (int interacao, String inicial, String p1, String p2, char letraP1, char letraP2){
        for (int i = 0; i < interacao; i++){
            String next = "";
            for (int x = 0; x < inicial.length(); x++){
                char letra = inicial.charAt(x);
                if (letra == letraP1){
                    next += p1;
                } else if (letra == letraP2){
                    next += p2;
                } else {
                    next += letra;
                }
            }
            inicial = next;
        }
        return inicial;
    }
}
