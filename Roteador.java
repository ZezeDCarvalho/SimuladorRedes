/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author zezedcarvalho
 */
public class Roteador {

    // 0 en, 1 ewEntrada, 2 ewSaida
    private Little[] little = new Little[3];
//    private Little en = new Little();
//    private Little ewEntrada = new Little();
//    private Little ewSaida = new Little();

    private double ocupacaoDesejada;
    private double larguraBanda;
    private double tempoSimulacao;
    private double intervaloPct;
    private double fila = 0.0;
    private double tempoAtual = 0.0;
    private double ocupacao = 0.0;
    private double throughput = 0.0;
    private double goodput = 0.0;
    private final Random rnd;
    Queue<Pacote> filaPacotes = new PriorityQueue<>();

    public Roteador(Random rnd) {
        for (Little obj : little) {
            obj = new Little();
        }
        this.rnd = rnd;
    }

    protected double aleatorio() {
        double u = rnd.nextDouble();
        u = 1.0 - u;
        return u;
    }

    public void setOcupacaoDesejada(double ocupDesejada) {
        this.ocupacaoDesejada = ocupDesejada;
    }

    public void setLarguraBanda(double larguraBanda) {
        this.larguraBanda = larguraBanda;
    }

    public void setTempoSimulacao(double tempoSimulacao) {
        this.tempoSimulacao = tempoSimulacao;
    }

    private double minimo(double a, double b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public void calcularMedidas() {
        this.intervaloPct = (this.ocupacaoDesejada * this.larguraBanda) / 441.0;
        double tempoChegada = (-1.0 / this.intervaloPct) * Math.log(this.aleatorio());
        double tempoSaida = 0.0;

        while (tempoChegada <= this.tempoSimulacao) {
            if (fila < 1) {
                this.tempoAtual = tempoChegada;
            } else {
                tempoAtual = this.minimo(tempoChegada, tempoSaida);
            }

            if (this.tempoAtual == tempoChegada) {
                //chegada de pacote;
                Pacote pct = new Pacote();
                pct.setTempoChegada(tempoChegada);

                //se não tem fila ja entra sendo atendido
                if (filaPacotes.isEmpty()) {
                    double tamPct = pct.getTamanhoPct(this.aleatorio());
                    tempoSaida = this.tempoAtual + tamPct / larguraBanda;
                    pct.setTempoSaida(tempoSaida);
                    this.throughput += tamPct;
                    this.goodput += tamPct - 40.0;
                    this.ocupacao += tempoSaida - this.tempoAtual;
                }

                this.fila++;
                filaPacotes.add(pct);

                little[0].getSomaAreas();
            }

        }

    }

    @Override
    public String toString() {
        return "";

    }
}
