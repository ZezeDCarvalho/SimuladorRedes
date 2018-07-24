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
    private Little[] es = new Little[3];
    private double ocupacaoDesejada;
    private double larguraBanda;
    private double tempoSimulacao;
    private double intervaloPct;
    private double tempoAtual;
    private double ocupacao;
    private double throughput;
    private double goodput;
    private double ew;
    private double en;
    private double lambda;
    private final Random rnd;
    Queue <Pacote> filaPacotes = new PriorityQueue <> ();

    public Roteador(Random rnd, double larguraBanda, double ocupDesejada, double tempoSimulacao) {
        for (int i = 0; i < es.length; i++) {
            es[i] = new Little();
        }
        this.rnd = rnd;
        this.larguraBanda = larguraBanda;
        this.ocupacaoDesejada = ocupDesejada;
        this.tempoSimulacao = tempoSimulacao;
    }

    protected double aleatorio() {
        double u = rnd.nextDouble();
        u = 1.0 - u;
        return u;
    }

    private double minimo(double a, double b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }
    
    public void setE(Little e, String operacao){
        double  tempoAnterior = e.getTempoAnterior();
        double  eventos = e.getNumeroEventos();
        double  area = e.getSomaAreas() + (eventos * (tempoAtual - tempoAnterior));
        if (operacao.compareTo("-") == 0)
            e.setAtributos(area, --eventos, tempoAtual);
        else e.setAtributos(area, ++eventos, tempoAtual);
    }

    public void calcularMedidas() {
        intervaloPct = (ocupacaoDesejada * larguraBanda) / 441.0;
        double tempoChegada = (-1.0 / intervaloPct) * Math.log(this.aleatorio());
        double tempoSaida = 0.0;
        double tamPct;
        double tempoAnteriorEn; 
        double eventosEn;
        double areaEn = 0.0;
        double tempoAnteriorEwEntrada;
        double eventosEwEntrada;
        double areaEwEntrada = 0.0;
        double tempoAnteriorEwSaida ;
        double eventosEwSaida;
        double areaEwSaida = 0.0;

        while (tempoChegada <= tempoSimulacao) {
            if (filaPacotes.isEmpty()) {
                tempoAtual = tempoChegada;
            } else {
                tempoAtual = this.minimo(tempoChegada, tempoSaida);
            }

            if (tempoAtual == tempoChegada) {
                //chegada de pacote;
                Pacote pct = new Pacote();
                pct.setTempoChegada(tempoChegada);

                //se não tem fila ja entra sendo atendido
                if (filaPacotes.isEmpty()) {
                    tamPct = pct.getTamanhoPct(this.aleatorio());
                    tempoSaida = tempoAtual + tamPct / larguraBanda;
                    pct.setTempoSaida(tempoSaida);
                    throughput += tamPct;
                    goodput += tamPct - 40.0;
                    ocupacao += tempoSaida - tempoAtual;
                }
                // problema nesta linha exceção ClassCastException
                boolean add = filaPacotes.add(pct);
                if (add) System.out.println("pct OK");
                else System.out.println("pct ERRO");
                //en
                this.setE(es[0], "+");                
                //ew entrada
                this.setE(es[1], "+");
               
                tempoChegada = tempoAtual + (-1.0 / intervaloPct) * Math.log(this.aleatorio());
            }else{
                //se tem fila
                if (!filaPacotes.isEmpty()) {
                    Pacote pacoteFila = filaPacotes.poll();
                    tamPct = pacoteFila.getTamanhoPct(this.aleatorio());
                    tempoSaida = tempoAtual + tamPct / larguraBanda;
                    pacoteFila.setTempoSaida(tempoSaida);
                    throughput += tamPct;
                    goodput += tamPct - 40.0;
                    ocupacao += tempoSaida - tempoAtual;
                }
                //en 
                this.setE(es[0], "-");
                //ew saida
                this.setE(es[2], "+");
           }
        }   
        areaEn += es[0].getNumeroEventos()*(tempoAtual - es[0].getTempoAnterior());
        es[0].setAreaRestante(areaEn);
        areaEwEntrada += es[1].getNumeroEventos()*(tempoAtual - es[1].getTempoAnterior());
        es[0].setAreaRestante(areaEwEntrada);
        areaEwSaida += es[2].getNumeroEventos()*(tempoAtual - es[2].getTempoAnterior());
        es[0].setAreaRestante(areaEwSaida);
        
        en = es[0].getSomaAreas() / tempoAtual;
        ew = (es[1].getSomaAreas() - es[2].getSomaAreas())/es[1].getNumeroEventos();
        lambda = es[1].getNumeroEventos()/tempoAtual;
    }

    @Override
    public String toString() {
        String dados = "Largura banda: "+larguraBanda+"\n";
               dados += "Ocupação: "+ocupacao+"\n";
               dados +="Throughput: "+throughput+"\n";
               dados +="Goodput: "+goodput+"\n";
               dados +="EN: "+en+"\n";
               dados +="EW: "+ew+"\n";
               dados +="Lambda: "+lambda+"\n";
               dados +="Little: "+(en - (lambda * ew))+"\n";
               
        return dados;
    }

    
}
