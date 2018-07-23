/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise;

/**
 *
 * @author zezedcarvalho
 */
public class Little {
    private int numeroEventos;
    private double somaAreas;
    private double tempoAnterior;
    
    public Little(){
        this.numeroEventos = 0;
        this.somaAreas = 0.0;
        this.tempoAnterior = 0.0;
    }
    
    public void adicionarArea(double area, double tempo, String tipo){
        this.somaAreas += area;
        this.tempoAnterior = tempo;
        if (tipo.equals("-"))
            this.numeroEventos--;
        else
            this.numeroEventos++;
    }

    public int getNumeroEventos() {
        return numeroEventos;
    }

    public double getSomaAreas() {
        return somaAreas;
    }

    public double getTempoAnterior() {
        return tempoAnterior;
    }
    
    
}
