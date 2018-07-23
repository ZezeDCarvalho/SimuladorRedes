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
        double largura = scan.nextDouble();
        System.out.print("Tempo de simulação: ");
        double tempoSimulacao = scan.nextDouble();
        System.out.print("Ocupação desejada: ");
        double ocupacao = scan.nextDouble();
        
        //instanciando cada posicao do vetor de roteadores
        Roteador[] roteadores = new Roteador[numeroRoteadores];
        for (Roteador roteador : roteadores) {
            roteador = new Roteador(rnd);
            roteador.setTempoSimulacao(tempoSimulacao);
            roteador.setOcupacaoDesejada(ocupacao);
            roteador.setLarguraBanda(largura);
        }
    }
}
