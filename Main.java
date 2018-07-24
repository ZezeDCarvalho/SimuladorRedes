package analise;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author zezedcarvalho
 */
public class Main {

    public static void main(String[] args) {
        Random rnd = new Random();
        Scanner scan = new Scanner(System.in);

        //numero de roteadores
        System.out.print("Número de roteadores: ");
        int numeroRoteadores = Integer.parseInt(scan.nextLine());
        System.out.print("Largura de banda: ");
        double largura = Double.parseDouble(scan.nextLine());
        System.out.print("Tempo de simulação: ");
        double tempoSimulacao = Double.parseDouble(scan.nextLine());
        System.out.print("Ocupação desejada: ");
        double ocupacao = Double.parseDouble(scan.nextLine());
        
        //instanciando cada posicao do vetor de roteadores
        Roteador[] roteadores = new Roteador[numeroRoteadores];
        for (int i = 0; i < roteadores.length; i++) {
            roteadores[i] = new Roteador(rnd, largura, ocupacao, tempoSimulacao);
            roteadores[i].calcularMedidas();
            System.out.println("Roteador "+(i+1));
            System.out.println(roteadores[i].toString());
        }
    }
}
