//Projeto L-Systems
//--Alunos--
//Nome: Vinicius Silveira Bonicio RA: 082180002
//Nome: Jean Alves Gollmann       RA: 082180018
//Nome: Joao Vitor Luques Rocha   RA: 082180024
//Nome: Humberto Gonzaga          RA: 082180036
package l.system;

import Classes.GerarSintaxe;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author alves
 */
public class LSystem {
    private static int interacao;
    private static String inicial;
    private static double angulo;
    private static double PI = Math.PI;
    private static double anguloAtual = PI/2;
    private static String p1;
    private static String p2;
    private static String stringFinal;
    private static char letraP1;
    private static char letraP2;
    private static int foward = 50;
    private static ArrayList<String> grama = new ArrayList<String>();
    private static List<Double> saveX = new ArrayList<Double>();
    private static List<Double> saveY = new ArrayList<Double>();
    private static double translacaoX;
    private static double translacaoY;
    public static void main(String[] args) throws IOException {
       int count = 0;
       Scanner s = new Scanner(System.in);
       System.out.println("A gramatica já está pré estabelecida, caso queira mudar a gramatica, basta acessar"
               + "o arquivo GramaticaDaLinguagem.txt para mudar. Aperte o botão Enter para continuar");
       s.nextLine();
       
       Scanner ler = new Scanner(new FileReader("GramaticaDaLinguagem.txt"));
       
       while(ler.hasNextLine()){
           grama.add(ler.nextLine());
       }
       for (int i = 0; i < grama.size(); i++){
           String linha = grama.get(i);
           if (count == 0){
//               String tiraTudo = linha.replace(",", "").replace(":", "").replace("Σ", "");
               count++;
           } else if (count == 1){
               interacao = Integer.parseInt(linha.replace(":", "").replace("n", ""));
               count++;
           } else if (count == 2){
               inicial = linha.replace(":", "").replace("ω", "");
               count++;
           } else if (count == 3){
               int ang = Integer.parseInt(linha.replace(":", "").replace("δ", ""));
               angulo = (ang*PI)/180;
               count++;
           } else if (count == 4){
               p1 = linha.substring(linha.indexOf("→") + 1);
               letraP1 = linha.substring(linha.indexOf(":") + 1, linha.indexOf(":") + 2).charAt(0);
               count++;
           } else if (count == 5){
               p2 = linha.substring(linha.indexOf("→") + 1);
               letraP2 = linha.substring(linha.indexOf(":") + 1, linha.indexOf(":") + 2).charAt(0);
               count++;
           }
           
       }
       String strFinal = GerarSintaxe.gerarString(interacao, inicial, p1, p2, letraP1, letraP2);
       System.out.printf("A String Final é essa: %s.\nAperte o botão Enter para poder fazer o desenho.", strFinal);
       s.nextLine();
       desenhar(strFinal, letraP1, letraP2);
    }
    
    public static void desenhar(String strFinal, char letraP1, char letraP2) throws IOException {
        FileWriter arq = new FileWriter("L-SystemsDraw.txt");
        PrintWriter gravaArq = new PrintWriter(arq);
        translacaoX = 500;
        translacaoY = 500;
        gravaArq.println("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" height=\"1000\">");
        for (int i = 0; i < strFinal.length(); i++){
            char letter = strFinal.charAt(i);
            if (letter == letraP1){
                double origemLinhaX = translacaoX;
                double origemLinhaY = translacaoY;
                double destinoLinhaX = foward + origemLinhaX;
                double destinoLinhaY = foward + origemLinhaY;
                gravaArq.println("\t  <line x1 = \""+ origemLinhaX +"\" y1 = \""+ origemLinhaY +"\" x2 = \""+ destinoLinhaX +"\" y2 = \""+ destinoLinhaY +"\" stroke = \"black\" stroke-width = \"2\"/> \n");
                translacaoX = destinoLinhaX;
                translacaoY = destinoLinhaY;
            } else if (letter == letraP2) {
                double origemLinhaX = translacaoX;
                double origemLinhaY = translacaoY;
                double destinoLinhaX = (-(Math.cos(anguloAtual) * foward) + origemLinhaX);
                double destinoLinhaY = (-(Math.sin(anguloAtual) * foward) + origemLinhaY);
                gravaArq.println("\t  <line x1 = \""+ origemLinhaX +"\" y1 = \""+ origemLinhaY +"\" x2 = \""+ destinoLinhaX +"\" y2 = \""+ destinoLinhaY +"\" stroke = \"black\" stroke-width = \"2\"/> \n");
                translacaoX = destinoLinhaX;
                translacaoY = destinoLinhaY;
            } else if (letter == '+'){
                anguloAtual += angulo;
            } else if (letter == '-'){
                anguloAtual -= angulo;
            } else if (letter == '['){
                push();
            } else if (letter == ']'){
                pop();
            }
        }
        gravaArq.println("<\\svg> \n");
        
        arq.close();
        System.out.println("Deu tudo certo!");
    }
    
    public static void push(){
        saveX.add(translacaoX);
        saveY.add(translacaoY);
    }
    
    public static void pop(){
        translacaoX = saveX.remove(saveX.size()-1);
        translacaoY = saveY.remove(saveY.size()-1);
    }
}
