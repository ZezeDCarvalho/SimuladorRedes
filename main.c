/* 
 * File:   main.c
 * Author: flavio
 *
 * Created on 3 de Maio de 2018, 14:54
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
//struct para calculo do E[N]

typedef struct Info_ {
    unsigned long int numeroEventos; //numero de elementos
    long double somaAreas; //soma das areas
    long double tempoAnterior; //tempo do evento anterior
} Info;

//inicia a struct com 0 nos campos

void iniciaInfo(Info *info) {
    info->numeroEventos = 0;
    info->somaAreas = 0.0;
    info->tempoAnterior = 0.0;
}

//retorna um numero pseudo aleatorio entre (0,1]

long double aleatorio() {
    long double u = RAND_MAX - 1;
    //limitando entre 0 e RAND_MAX-1;
    u = rand() % RAND_MAX;
    //resultado sera algo entre [0,0.999999...] proximo a 1.0
    u = u / (long double) RAND_MAX;
    //limitando entre (0,1]
    u = 1.0 - u;
    return u;
}

//retorna o tamanho do pacote em Bytes
//seguindo a proporcao: 50% 550B, 40% 40B e 10% 1500B

long double retornaPct() {
    long double u = aleatorio();
    if (u <= 0.5)
        return 550.0;
    else if (u <= 0.9)
        return 40.0;
    else
        return 1500.0;
}

long double minimo(long double n1, long double n2) {
    if (n1 < n2)
        return n1;
    else
        return n2;
}

int main() {
    int i, j;
    //long double banda[] = {1250000, 12500000, 125000000, 1250000000};
    long double banda[] = {1250000};
    //long double ocupacaoDesejada[] = {0.6, 0.8, 0.9, 0.99};
    long double ocupacaoDesejada[] = {0.6};
    for (i = 0; i < 1; i++) {
        for (j = 0; j < 1; j++) {

            int semente;
            semente = time(NULL);
            srand(semente);

            //largura de banda em Bytes/seg
            long double larguraBanda = banda[i];
            //tempo de simulacao em segundos (2 dias)
            //long double tempoSimulacao = 172800.0;
            //duas horas em segundos
            long double tempoSimulacao = 300.0;
            
            //intervalo entre pacotes em segundos
            long double intervaloPct = (ocupacaoDesejada[j] * larguraBanda) / 441.0;

            //gerando o tempo de chegada do primeiro pct
            long double tempoChegada = (-1.0 / intervaloPct) * log(aleatorio());
            //printf("tempo de chegada do primeiro %lF\n", tempoChegada);
            //getchar();

            //tempo de saida do pacote em atendimento
            long double tempoSaida = 0.0;

            //fila de pacotes dentro do roteador
            //assume-se que a cabeca da fila é o
            //pacote em atendimento
            unsigned long int fila = 0;

            //tempo atual dentro da simulacao
            //que sera incrementado à medida que 
            //o "tempo passar" dentro do simulador
            long double tempoAtual = 0.0;
            long double ocupacao = 0.0;
            long double throughput = 0.0;
            long double goodput = 0.0;

            //variavel para o calculo de E[N]
            Info en;
            Info ewEntrada;
            Info ewSaida;
            iniciaInfo(&en);
            iniciaInfo(&ewEntrada);
            iniciaInfo(&ewSaida);

            while (tempoAtual <= tempoSimulacao) {
                if (!fila) {
                    tempoAtual = tempoChegada;
                } else {
                    tempoAtual = minimo(tempoChegada, tempoSaida);
                }

                //chegada de pacote
                if (tempoAtual == tempoChegada) {
                    //printf("tempo atual == chegada %lF\n", tempoAtual);
                    //getchar();
                    //roteador vazio!
                    //logo, o pacote ja entra sendo atendido
                    if (!fila) {
                        //gerar o tempo de atendimento
                        double tam = retornaPct();
                        throughput += tam;
                        goodput += tam - 40.0;

                        tempoSaida = tempoAtual + tam / larguraBanda;
                        ocupacao += tempoSaida - tempoAtual;
                    }

                    //insiro na fila
                    fila++;

                    //calculo do E[N]
                    en.somaAreas += en.numeroEventos * (tempoAtual - en.tempoAnterior);
                    en.tempoAnterior = tempoAtual;
                    en.numeroEventos++;
                    //calculo do E[W]
                    ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempoAtual - ewEntrada.tempoAnterior);
                    ewEntrada.tempoAnterior = tempoAtual;
                    ewEntrada.numeroEventos++;

                    //gero o tempo de chegada do proximo
                    tempoChegada = tempoAtual + (-1.0 / intervaloPct) * log(aleatorio());
                } else {//saida de pacote
                    //printf("tempo atual == saida %lF\n", tempoAtual);
                    //getchar();

                    fila--;

                    if (fila) {
                        //gerar o tempo de atendimento
                        //do pacote seguinte na fila
                        double tam = retornaPct();
                        throughput += tam;
                        goodput += tam - 40.0;
                        tempoSaida = tempoAtual + tam / larguraBanda;
                        ocupacao += tempoSaida - tempoAtual;
                    }

                    //calculo do E[N]
                    en.somaAreas += en.numeroEventos * (tempoAtual - en.tempoAnterior);
                    en.tempoAnterior = tempoAtual;
                    en.numeroEventos--;
                    //calculo do E[W]
                    ewSaida.somaAreas += ewSaida.numeroEventos * (tempoAtual - ewSaida.tempoAnterior);
                    ewSaida.tempoAnterior = tempoAtual;
                    ewSaida.numeroEventos++;

                }
                //printf("fila %lF\n", fila);
                //getchar();
            }
            en.somaAreas += en.numeroEventos * (tempoAtual - en.tempoAnterior);
            ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempoAtual - ewEntrada.tempoAnterior);
            ewSaida.somaAreas += ewSaida.numeroEventos * (tempoAtual - ewSaida.tempoAnterior);

            long double EN = en.somaAreas / tempoAtual;
            long double EW = (ewEntrada.somaAreas - ewSaida.somaAreas) / ewEntrada.numeroEventos;
            long double lambda = ewEntrada.numeroEventos / tempoAtual;

            printf("==================Cenario %d==================\n", i * 4 + j + 1);
            printf("Largura de banda: %.15Lf\n", banda[i]);
            printf("Ocupacao desejada: %.15Lf\n", ocupacaoDesejada[j]);
            printf("Ocupacao: %.15Lf\n", ocupacao / tempoAtual);
            printf("E[N]: %.15Lf\n", EN);
            printf("E[W]: %.15Lf\n", EW);
            printf("Lambda: %.15Lf\n", lambda);
            printf("Little: %.15Lf\n", EN - (lambda * EW));
            printf("Throughput: %.15Lf\n", throughput / tempoAtual);
            printf("Goodput: %.15Lf\n", goodput / tempoAtual);
            printf("==============================================\n");
        }
    }

    return (0);
}


