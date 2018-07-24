/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise;

import java.util.Random;

/**
 *
 * @author zezedcarvalho
 */
public class Pacote {
    
    private double tempoChegada;
    private double tamanhoPct;
    private double tempoSaida;
    
    Pacote(){
        this.tempoChegada = 0.0;
        this.tamanhoPct = 0.0;
        this.tempoSaida = 0.0;
    }

    public double getTamanhoPct(Double aleatorio){
        if (aleatorio <= 0.5){
            this.tamanhoPct = 550.0;
        }else if (aleatorio <= 0.9){
            this.tamanhoPct = 40.0;
        }else
            this.tamanhoPct = 1500.0;
        return this.tamanhoPct;
    }

    public double getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(double tempoChegada) {
        this.tempoChegada = tempoChegada;
    }
    
    public double getTempoSaida() {
        return tempoSaida;
    }

    public void setTempoSaida(double tempoSaida) {
        this.tempoSaida = tempoSaida;
    }
}
