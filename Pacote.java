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
public final class Pacote {
    
    private double tamanhoPct;
    private double tempoChegada;
    private double tempoSaida;

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
